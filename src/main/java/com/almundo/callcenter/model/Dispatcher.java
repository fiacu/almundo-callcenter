package com.almundo.callcenter.model;

import java.util.logging.Logger;

import com.almundo.callcenter.CallCenterApp;
import com.almundo.callcenter.helpers.SyncCounter;

public class Dispatcher {
    private volatile static Dispatcher uniqueInstance;
    private static final Logger logger = Logger
            .getLogger(CallCenterApp.class.getName());
    private CallCenter callCenter;
    SyncCounter inboundCallsCounter = new SyncCounter();

    private Dispatcher() {
    }

    public static synchronized Dispatcher getInstance() {
        if (uniqueInstance == null) {
            synchronized (Dispatcher.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new Dispatcher();
                }
            }
        }
        return uniqueInstance;
    }
    
    public void dispatchCall(Call call) throws InterruptedException {
        Employee employee = null;
        logger.info("Incoming Call : " + call.toString() + ".");
        synchronized (callCenter.getAvailableEmployees()) {
            if(isOverloaded()) {
                rejectCall(call);
                return;
            }
            inboundCallsCounter.increment();
            while (!callCenter.hasNextEmployee()) {
                onholdCall(call);
                callCenter.getAvailableEmployees().wait();
            }
            employee = callCenter.nextEmployee();
            callCenter.unsubscribe(employee);
        }
        call.connect(employee);
        inboundCallsCounter.decrement();
        callCenter.subscribe(employee);
    }

    private void onholdCall(Call call) {
        logger.info("Call : " + call.toString()
        + " - No Employee available, please hold on.");
        call.holdon();
    }

    private void rejectCall(Call call) {
        logger.info("Dispatcher overloaded, try again later.");
        call.disconnect();
    }

    private boolean isOverloaded() {
        return inboundCallsCounter.getValue() > 9;
    }
    
    public void setCallcenter(CallCenter callcenter) {
        this.callCenter = callcenter;
    }

    public int getQueueCalls() {
        return inboundCallsCounter.getValue();
    }

}
