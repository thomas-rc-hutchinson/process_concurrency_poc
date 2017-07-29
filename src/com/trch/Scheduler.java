package com.trch;

import java.util.*;

/**
 * Created by thomashutchinson on 29/07/2017.
 */
public class Scheduler extends Thread {

    private List<IProcess> unitialisedProcesses = new ArrayList<>();
    private List<IProcess> processes = new ArrayList<>();

    public void startProcess(IProcess process) {
        //lazy hack
        synchronized (processes) {
            unitialisedProcesses.add(process);
        }
    }

    public void run() {
        while (true) {
            handleMessages();
            initialiseProcesses();
        }
    }

    private void handleMessages() {
        synchronized (processes) {
            for (IProcess process : processes) {
                process.takeProcessMessage().ifPresent(processMessage -> {
                    process.handleMessage(processMessage);
                });
            }
        }
    }

    private void initialiseProcesses(){
        //lazy hack
        synchronized (unitialisedProcesses){
           final Iterator<IProcess> iterator = unitialisedProcesses.iterator();
           while(iterator.hasNext()){
               IProcess process = iterator.next();
               process.init();

               //process now initialised, move to process list
               processes.add(process);
               iterator.remove();
           }
        }
    }
}
