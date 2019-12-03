package com.almundo.callcenter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.almundo.callcenter.helpers.HelperEmployeeTypes;

/**
 * Represeanta el callcenter, gestiona la lista de empleados disponibles. (Singleton)
 * @author fmoran
 *
 */
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
     * Subscribe en funcion del orden del tipo de empleado
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
    
    /**
     * Retorna un empleado disponble para asignarlo a una llamada segun orden
     * El orden de prioridad esta dado el orden de la lista, donde en la posicion [0] 
     *   se encuentra el empleado de menor rango.
     * Este metodo se invoca en un bloque sincronizado para evitar problemas de doble asignacion.
     * 
     * @return Employee, un empleado disponible para tomar la llamada
     */
    public synchronized Employee nextEmployee() {
        for(String employeeType : HelperEmployeeTypes.getEmployeeTypes()) {
            if(availableEmployees.get(employeeType).iterator().hasNext())
                return availableEmployees.get(employeeType).iterator().next();
        }
        return null;
    }
    
    /**
     * Determina si existe un empleado disponible para tomar una llamada
     * @return true si existe un empleado de cualquier tipo disponible.
     */
    public synchronized boolean hasNextEmployee() {
        for(String employeeType : HelperEmployeeTypes.getEmployeeTypes()) {
            if(availableEmployees.get(employeeType).iterator().hasNext())
                return true;
        }
        return false;
    }

}
