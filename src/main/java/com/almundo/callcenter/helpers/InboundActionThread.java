package com.almundo.callcenter.helpers;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.services.InboundService;

public class InboundActionThread implements Runnable {
    private InboundService service;

    /**
     * Simulador de creación de una llamada
     * @param servicio que consume el dispatcher
     */
    public InboundActionThread(InboundService service) {
        this.service = service;
    } 

    @Override
    public void run() {
        Call call = new Call();
        try {
            service.dispatchCall(call);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
