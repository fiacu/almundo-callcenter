package com.almundo.callcenter;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.services.InboundService;

public class InboundTestThread implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;
    private final InboundService service;
    private final Call call;
    AtomicReference<AssertionError> failure;

    public InboundTestThread(Call call, InboundService service,
            CountDownLatch startSignal, CountDownLatch doneSignal,
            AtomicReference<AssertionError> failure) {
        this.call = call;
        this.service = service;
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
        this.failure = failure;
    }
    public void run() {
        try {
            startSignal.await();
            service.dispatchCall(call);
            try {
                //Se asigno un empleado y finalizo la llamada
                assertTrue(call.getEmployee() != null);
                assertTrue(call.getEndDate() != null);
                assertTrue(call.getEndDate().after(call.getStartDate()));
            }
            catch (AssertionError e) {
                failure.set(e);
            }
            doneSignal.countDown();
        }
        catch (InterruptedException ex) {
        } // return;
    }
}
