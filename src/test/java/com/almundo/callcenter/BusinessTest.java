package com.almundo.callcenter;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.CallCenter;
import com.almundo.callcenter.model.Dispatcher;
import com.almundo.callcenter.model.Employee;

public class BusinessTest {
    private CallCenter callcenter = CallCenter.getInstance();
    private Dispatcher dispatcher = Dispatcher.getInstance();
    
    public BusinessTest() {
        dispatcher.setCallcenter(callcenter);
    }
    
    /**
     * Prueba para comprobación del orden de asignacion segun tipo de empleado
     * @throws InterruptedException
     */
    @Test
    public void testEmployeeOrderAssignation() throws InterruptedException {
        callcenter.unsubscribeAll();
        //SUBSCRIBE EMPLOYEES
        Employee director = new Employee(Employee.DIRECTOR);
        callcenter.subscribe(director);
        Employee supervisor = new Employee(Employee.SUPEVISOR);
        callcenter.subscribe(supervisor);
        Employee operator = new Employee(Employee.OPERATOR);
        callcenter.subscribe(operator);
        
        //TEST 1 - CALL ASSIGNED TO OPEARTOR
        Call call1 = new Call(1);
        dispatcher.dispatchCall(call1);
        assertTrue(call1.getEmployee().getType().equalsIgnoreCase(Employee.OPERATOR));
        
        //TEST 2 - OPERATOR DOWN, CALL ASSIGNED TO SUPERVISOR
        callcenter.unsubscribe(operator);
        Call call2 = new Call(1);
        dispatcher.dispatchCall(call2);
        assertTrue(call2.getEmployee().getType().equalsIgnoreCase(Employee.SUPEVISOR));
        
        //TEST 3 - SUPERVISOR DOWN, CALL ASSIGNED TO OPERATOR
        callcenter.subscribe(operator);
        callcenter.unsubscribe(supervisor);
        Call call3 = new Call(1);
        dispatcher.dispatchCall(call3);
        assertTrue(call3.getEmployee().getType().equalsIgnoreCase(Employee.OPERATOR));
        
        //TEST 4 - OPERATOR DOWN, CALL ASSIGNED TO DIRECTOR
        callcenter.unsubscribe(operator);
        Call call4 = new Call(1);
        dispatcher.dispatchCall(call4);
        assertTrue(call4.getEmployee().getType().equalsIgnoreCase(Employee.DIRECTOR));
    }

    /**
     * Prueba de duración de una llamada
     * @throws InterruptedException
     */
    @Test
    public void testCallDuration() throws InterruptedException {
        callcenter.unsubscribeAll();
        Employee director = new Employee(Employee.DIRECTOR);
        callcenter.subscribe(director);
        Call call = new Call();
        dispatcher.dispatchCall(call);
        
        long diffInMillies = Math.abs(call.getEndDate().getTime() - call.getStartDate().getTime());
        long diffInSeconds = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        System.out.println("Call duration: " + diffInSeconds);
        assertTrue(diffInSeconds >= 5 && diffInSeconds <= 10);
    }
}
