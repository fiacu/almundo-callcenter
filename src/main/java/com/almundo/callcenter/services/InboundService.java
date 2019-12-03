package com.almundo.callcenter.services;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.Dispatcher;

public class InboundService {
    private Dispatcher dispatcher;

    /**
     * Servicio que recibe una llamada
     * @param dispatcher dispatcher encargado de manejar las llamadas.
     */
    public InboundService(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    } 


    public void dispatchCall(Call call) throws InterruptedException {
        dispatcher.dispatchCall(call);
    }

}
