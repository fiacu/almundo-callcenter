package com.almundo.callcenter;

import java.util.concurrent.CountDownLatch;

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
    CallCenter callcenter = CallCenter.getInstance();
    Dispatcher dispatcher = Dispatcher.getInstance();
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
     *
     * @throws InterruptedException 
     */
    public void testApp() throws InterruptedException {
        int maxCalls = 10;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(maxCalls);
        InboundService inboundService = new InboundService(dispatcher);

        dispatcher.setCallcenter(callcenter);
        for(int i=0; i < maxCalls; i++) {
            callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        }
        for(int i=0; i < maxCalls; i++) {
            Call call = new Call();
            new Thread(new InboundTestThread(call, inboundService, startSignal, doneSignal)).start();
        }
        startSignal.countDown();
        doneSignal.await();
    }
}
