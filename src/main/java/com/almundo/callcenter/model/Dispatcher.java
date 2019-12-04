package com.almundo.callcenter.model;

import java.util.logging.Logger;

import com.almundo.callcenter.CallCenterApp;
import com.almundo.callcenter.helpers.SyncCounter;

/***
 * Clase encargada de gestionar las llamadas (Singleton)
 * @author fmoran
 *
 */
public class Dispatcher {
    private final int MAX_CAPACITY = 10;
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
    
    /**
     * Asigna la llamada a un empleado y ejecutarla.
     * Controla la sobrecarga {@link Dispatcher#isOverloaded()} del dispather y rechaza la llamada en caso de estarlo.
     * Aguarda a que un empleado se subscriba para ejecutar la llamada 
     * @see CallCenter#subscribe(Employee)
     * @param call
     * @throws InterruptedException
     */
    public void dispatchCall(Call call) throws InterruptedException {
        Employee employee = null;
        logger.info("Incoming Call : " + call.toString() + ".");
        if(isOverloaded()) {
            rejectCall(call);
        }
        else {
            synchronized (callCenter.getAvailableEmployees()) {
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
    }

    /**
     * En caso de no existir empleados disponibles, la llamada queda en espera
     * @param call
     */
    private void onholdCall(Call call) {
        logger.info("Call : " + call.toString()
        + " - No Employee available, please hold on.");
        call.holdon();
    }

    /**
     * Rechaza una llamada si el dispatcher esta sobrecargado
     * @param call
     */
    private void rejectCall(Call call) {
        logger.info("Dispatcher overloaded, try again later.");
        call.disconnect();
    }

    /**
     * Resuelve si el despatcher esta sobrecargado
     * @return true si el dispatcher esta sobrecargado
     */
    private boolean isOverloaded() {
        return inboundCallsCounter.getValue() >= MAX_CAPACITY;
    }
    
    public void setCallcenter(CallCenter callcenter) {
        this.callCenter = callcenter;
    }

    public int getQueueCalls() {
        return inboundCallsCounter.getValue();
    }

}
