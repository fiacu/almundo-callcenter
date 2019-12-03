package com.almundo.callcenter.model.callstates;

import com.almundo.callcenter.model.Call;
import com.almundo.callcenter.model.CallState;

public class InitialState implements CallState {
    private Call call;

    public InitialState(Call call) {
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
        System.out.println("Call " + call.toString() + " rejected.");
        call.setState(call.getFinishedState());
    }

    @Override
    public void holdon() {
        System.out.println("Call " + call.toString() + " waiting.");
        call.setState(call.getWaitingState());
    }

}
