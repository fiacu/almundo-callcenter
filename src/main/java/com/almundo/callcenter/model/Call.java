package com.almundo.callcenter.model;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.almundo.callcenter.model.callstates.FinishedState;
import com.almundo.callcenter.model.callstates.InitialState;
import com.almundo.callcenter.model.callstates.OncallState;
import com.almundo.callcenter.model.callstates.WaitingState;

/**
 * Clase que representa una llamada telefonica
 * @author fmoran
 *
 */
public class Call {
    private OncallState onCallState;
    private WaitingState waitingState;
    private FinishedState finishedState;
    
    private final int duration;
    private Date creationDate;
    private Date startDate;
    private Date endDate;
    private Employee employee;
    private CallState state;
    
    private void initCall() {
        this.creationDate = new Date();
        //Defino un parametro para pruebas 
        this.state = new InitialState(this);
        this.onCallState = new OncallState(this);
        this.waitingState = new WaitingState(this);
        this.finishedState = new FinishedState(this);
    }
    
    public Call() {
        super();
        this.duration = getRandomDuration();
        initCall();
    }

    public Call(int duration) {
        super();
        this.duration = duration;
        initCall();
    }

    private int getRandomDuration() {
        return new Random().nextInt(6)+5;
    }

    public int getDuration() {
        return duration;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public CallState getState() {
        return state;
    }
    public void setState(CallState state) {
        this.state = state;
    }
    /**
     * Inicia una llamada telefonica
     * Setea fecha inicial al momento de ejeucion y asigna empleado.
     * @param employee
     */
    public void connect(Employee employee) {
        setStartDate(new Date());
        if(employee != null) {
            this.employee = employee;
            state.connect();
        }
        else {
            System.out.println("No employee available to take call.");
            //TODO: SE DESCONECTA PERO DEBERIA VOLVER A PONERSE EN COLA DE ESPERA
            state.disconnect();
        }
    }
    /**
     * Finaliza una llamada
     * Cambia a su estado a finalizada.
     */
    public void disconnect() {
        setEndDate(new Date());
        state.disconnect();
    }
    /**
     * Actualiza el estado de una llamada a en espera
     */
    public void holdon() {
        state.holdon();
    }
    public OncallState getOnCallState() {
        return onCallState;
    }
    public WaitingState getWaitingState() {
        return waitingState;
    }
    public FinishedState getFinishedState() {
        return finishedState;
    }
    public void executeCall() {
        try {
            TimeUnit.SECONDS.sleep(this.duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        disconnect();
    }
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("{");
        result.append("CreationDate: " + creationDate.toString());
        result.append(", StartDate: " + (startDate != null ? startDate.toString() : ""));
        result.append(", EndDate: " + (endDate != null ? endDate.toString() : ""));
        if(employee != null)
            result.append(", Employee: " + employee.toString());
        result.append(", State: " + state + "}");
        return result.toString();
    }
}
