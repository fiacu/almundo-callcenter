package com.almundo.callcenter.model;

public interface CallState {
    /**
     * Cuando una llamada se conecta
     */
    public void connect();
    /*
     * Cuando una llamada finaliza
     */
    public void disconnect();
    /*
     * Cuando una llamada esta en espera
     */
    public void holdon();
}
