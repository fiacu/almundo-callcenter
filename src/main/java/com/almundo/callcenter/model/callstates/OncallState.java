package com.almundo.callcenter.model.callstates;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.CallState;

/**
 * Estado de una llamada en ejecución
 * @author fmoran
 *
 */
public class OncallState implements CallState {
    private Call call;

    public OncallState(Call call) {
        this.call = call;
    }

    @Override
    public void connect() {
        System.out.println("Call " + call.toString() + " already connected.");
    }

    @Override
    public void disconnect() {
        System.out.println("Call " + call.toString() + " disconnected.");
        call.setState(call.getFinishedState());
    }

    @Override
    public void holdon() {
        System.out.println("Call " + call.toString() + " waiting.");
        call.setState(call.getWaitingState());
    }
}
