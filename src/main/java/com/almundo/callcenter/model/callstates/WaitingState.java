package com.almundo.callcenter.model.callstates;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.CallState;

/**
 * Estado de una llamada en espera de ser asignada a un empleado
 * @author fmoran
 *
 */
public class WaitingState implements CallState {
    private Call call;
    public WaitingState(Call call) {
        this.call = call;
    }

    @Override
    public void connect() {
        System.out.println("Call " + call.toString() + " connected.");
        call.setState(call.getOnCallState());
        call.executeCall();
    }

    @Override
    public void disconnect() {
        System.out.println("Call " + call.toString() + " disconnected.");
        call.setState(call.getFinishedState());
    }

    @Override
    public void holdon() {
        System.out.println("Call already waiting.");
    }
}
