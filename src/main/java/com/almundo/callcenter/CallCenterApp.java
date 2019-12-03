package com.almundo.callcenter;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.almundo.callcenter.helpers.HelperEmployeeTypes;
import com.almundo.callcenter.helpers.InboundActionThread;
import com.almundo.callcenter.model.CallCenter;
import com.almundo.callcenter.model.Dispatcher;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.services.InboundService;

public class CallCenterApp {
    private static Logger logger = null;
    
    static {
        InputStream stream = CallCenterApp.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            logger = Logger.getLogger(CallCenterApp.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Start app.");

        CallCenter callcenter = CallCenter.getInstance();
        Dispatcher dispatcher = Dispatcher.getInstance();
        InboundService inboundService = new InboundService(dispatcher);
        dispatcher.setCallcenter(callcenter);
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));
        callcenter.subscribe(new Employee(HelperEmployeeTypes.getRandomType()));

        int n = 20;
        for (int i=0; i<n; i++) 
            new Thread(new InboundActionThread(inboundService)).start();
        logger.info("End app.");
    }
}
