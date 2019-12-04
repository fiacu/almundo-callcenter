package com.almundo.callcenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.almundo.callcenter.helpers.HelperEmployeeTypes;
import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.CallCenter;
import com.almundo.callcenter.model.Dispatcher;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.services.InboundService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ConcurrentTest extends TestCase {
    
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public ConcurrentTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ConcurrentTest.class);
    }

    /**
     * Test unitario donde se simula la creación de 10 llamadas en simultaneo
     * @throws InterruptedException 
     */
    public void testTenConcurrentCalls() throws InterruptedException {
        CallCenter callcenter = CallCenter.getInstance();
        Dispatcher dispatcher = Dispatcher.getInstance();
        int maxCalls = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(maxCalls);
        InboundService inboundService = new InboundService(dispatcher);
        AtomicReference<AssertionError> failure = new AtomicReference<>();

        callcenter.unsubscribeAll();
        dispatcher.setCallcenter(callcenter);
        for(int i=0; i < maxCalls; i++) {
            callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        }
        for(int i=0; i < maxCalls; i++) {
            Call call = new Call();
            new Thread(new InboundTestThread(call, inboundService, startSignal, doneSignal, failure, true)).start();
        }
        startSignal.countDown();
        doneSignal.await();
        //SI HUBO ALGUN ERROR EN UN HILO
        if (failure.get() != null)
            throw failure.get();
    }
    
    /**
     * Prueba que un mismo empleado toma 2 llamadas, una tras otra dejando en espera la segunda.
     * @throws InterruptedException
     */
    public void testWaitingEmployee() throws InterruptedException {
        CallCenter callcenter = CallCenter.getInstance();
        Dispatcher dispatcher = Dispatcher.getInstance();
        int maxCalls = 2;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(maxCalls);
        InboundService inboundService = new InboundService(dispatcher);
        AtomicReference<AssertionError> failure = new AtomicReference<>();

        dispatcher.setCallcenter(callcenter);
        callcenter.unsubscribeAll();
        Employee employee = new Employee(Employee.OPERATOR);
        callcenter.subscribe(employee);
        
        Call call1 = new Call();
        new Thread(new InboundTestThread(call1, inboundService, startSignal, doneSignal, failure, true)).start();
        Call call2 = new Call();
        new Thread(new InboundTestThread(call2, inboundService, startSignal, doneSignal, failure, true)).start();
        
        startSignal.countDown();
        doneSignal.await();
        //SI HUBO ALGUN ERROR EN UN HILO
        if (failure.get() != null)
            throw failure.get();
        //El mismo empleado tomo ambas llamadas
        assertTrue(call1.getEmployee() == call2.getEmployee());
        //La fecha de inicio de la llamada 2 es igual o superior a la fecha de fin de la llamada 1.
        assertTrue(call2.getStartDate().compareTo(call1.getEndDate()) <= 0);
    }
    
    /**
     * Prueba que si hay mas de 10 llamadas, las siguientes se cancelan.
     * @throws InterruptedException
     */
    public void testRejectedCalls() throws InterruptedException {
        CallCenter callcenter = CallCenter.getInstance();
        Dispatcher dispatcher = Dispatcher.getInstance();
        int maxCalls = 10 + 2;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(maxCalls);
        InboundService inboundService = new InboundService(dispatcher);
        AtomicReference<AssertionError> failure = new AtomicReference<>();

        callcenter.unsubscribeAll();
        dispatcher.setCallcenter(callcenter);
        for(int i=0; i < maxCalls; i++) {
            callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        }

        List<Call> calls = new ArrayList<Call>();
        for(int i=0; i < maxCalls; i++) {
            calls.add(new Call());
        }
        
        for(int j=0; j < maxCalls; j++) {
            new Thread(new InboundTestThread(calls.get(j), inboundService, startSignal, doneSignal, failure, false)).start();
        }

        startSignal.countDown();
        doneSignal.await();
        //SI HUBO ALGUN ERROR EN UN HILO
        if (failure.get() != null)
            throw failure.get();
        
        //Todas las llamadas inicialmente fueron asignadas a un empleado
        //No se asigno empleado porque fue rechazada
        int counter = 0;
        for(Call call : calls) {
            if(call.getEmployee()==null)
                counter++;
        }
        //Dos calls son rechazadas
        assertEquals(2, counter);
    }
}
