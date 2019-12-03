package com.almundo.callcenter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.almundo.callcenter.helpers.HelperEmployeeTypes;

public class CallCenter {
    private volatile static CallCenter uniqueInstance;
    private LinkedHashMap<String, List<Employee>> availableEmployees = new LinkedHashMap<String, List<Employee>>();
    
    /**
     * Existe una unica intancia del callcenter que contiene la lista de empleados
     * Los empleados se agrupan segun su tipo.
     */
    private CallCenter() {
        for(String employeeType : HelperEmployeeTypes.getEmployeeTypes()) {
            availableEmployees.put(employeeType, Collections.synchronizedList(new ArrayList<Employee>()));
        }
    }
    
    public LinkedHashMap<String, List<Employee>> getAvailableEmployees() {
        return availableEmployees;
    }

    public static synchronized CallCenter getInstance() {
        if (uniqueInstance == null) {
            synchronized (CallCenter.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new CallCenter();
                }
            }
        }
        return uniqueInstance;
    }
    
    /**
     * Subscribe un empleado a la lista de empleados disponibles para tomar llamadas
     * @param employee
     */
    public void subscribe(Employee employee) {
        synchronized (availableEmployees) {
            if(availableEmployees.get(employee.getType()) != null) {
                availableEmployees.get(employee.getType()).add(employee);
                availableEmployees.notify();
            }
        }
    }
    
    /**
     * Da de baja un empleado disponible cuando toma la llamada (o finaliza su turno)
     * @param employee
     */
    public void unsubscribe(Employee employee) {
        if(availableEmployees.get(employee.getType()) != null)
            availableEmployees.get(employee.getType()).remove(employee);
    }
    
    public synchronized Employee nextEmployee() {
        for(String employeeType : HelperEmployeeTypes.getEmployeeTypes()) {
            if(availableEmployees.get(employeeType).iterator().hasNext())
                return availableEmployees.get(employeeType).iterator().next();
        }
        return null;
    }
    
    public synchronized boolean hasNextEmployee() {
        for(String employeeType : HelperEmployeeTypes.getEmployeeTypes()) {
            if(availableEmployees.get(employeeType).iterator().hasNext())
                return true;
        }
        return false;
    }

}
