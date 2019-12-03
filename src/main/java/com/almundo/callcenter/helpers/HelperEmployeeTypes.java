package com.almundo.callcenter.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.almundo.callcenter.model.Employee;

public class HelperEmployeeTypes {

    /**
     * Retorna la lista de tipos de empleados, ordenada segun nivel de operacion
     * Es a modo simulación se podria obtener de algun repositorio
     * @return List<String> Lista de tipos de empleados en el orden de prioridad de asignacion.
     */
    public static List<String> getEmployeeTypes() {
        List<String> employeeTypes = new ArrayList<String>();
        employeeTypes.add(Employee.OPERATOR);
        employeeTypes.add(Employee.SUPEVISOR);
        employeeTypes.add(Employee.DIRECTOR);
        return employeeTypes;
    }

    /**
     * Genera un tipo de empleado aleatorio, se usa para pruebas.
     * @return String, tipo de empleado.
     */
    public static String getRandomType() {
        if(getEmployeeTypes().size() > 0) {
            return getEmployeeTypes().get(new Random().nextInt(getEmployeeTypes().size()));
        }
        return "";
    }
}
