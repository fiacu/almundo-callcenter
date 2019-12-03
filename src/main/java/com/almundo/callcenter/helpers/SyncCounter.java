package com.almundo.callcenter.helpers;

/**
 * Contador sincronico para garantizar que el dispatcher se se sobrecargue.
 * @author fmoran
 *
 */
public class SyncCounter {
    private int counter = 0;
    public synchronized void increment() {
        counter++;
    }
    public synchronized void decrement() {
        counter--;
    }
    public synchronized int getValue() {
        return counter;
    }
}
