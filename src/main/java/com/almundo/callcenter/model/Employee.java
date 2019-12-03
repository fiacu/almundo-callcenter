package com.almundo.callcenter.model;

public class Employee {
    public static final String OPERATOR = "Operator";
    public static final String SUPEVISOR = "Supervisor";
    public static final String DIRECTOR = "Director";

    private String type;

    public Employee(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String toString() {
        return hashCode() + " [" + type + "]";
    }
}
