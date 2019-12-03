package com.almundo.callcenter.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.almundo.callcenter.model.Employee;

public class HelperEmployeeTypes {

    /**
     * Retorna la lista de tipos de empleados, ordenada segun nivel de peracion
     * @return
     */
    public static List<String> getEmployeeTypes() {
        List<String> employeeTypes = new ArrayList<String>();
        employeeTypes.add(Employee.OPERATOR);
        employeeTypes.add(Employee.SUPEVISOR);
        employeeTypes.add(Employee.DIRECTOR);
        return employeeTypes;
    }

    public static String getRandomType() {
        if(getEmployeeTypes().size() > 0) {
            return getEmployeeTypes().get(new Random().nextInt(getEmployeeTypes().size()));
        }
        return "";
    }
}
