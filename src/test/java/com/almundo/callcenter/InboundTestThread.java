package com.almundo.callcenter;

import java.util.concurrent.CountDownLatch;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.services.InboundService;

public class InboundTestThread implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;
    private final InboundService service;
    private final Call call;

    public InboundTestThread(Call call, InboundService service,
            CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.call = call;
        this.service = service;
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }
    public void run() {
        try {
            startSignal.await();
            service.dispatchCall(call);
            doneSignal.countDown();
        }
        catch (InterruptedException ex) {
        } // return;
    }
}
