package com.almundo.callcenter.model.callstates;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.CallState;

/**
 * Estado final de una llamada, haya sido rechazadas o finalizada por tiempo.
 * @author fmoran
 *
 */
public class FinishedState implements CallState {
    private Call call;
    public FinishedState(Call call) {
        this.call = call;
    }

    @Override
    public void connect() {
        System.out.println("Call allready disconneted. " + call.toString());
    }

    @Override
    public void disconnect() {
        System.out.println("Call allready disconneted. " + call.toString());
    }

    @Override
    public void holdon() {
        System.out.println("Call allready disconneted. " + call.toString());
    }
}
