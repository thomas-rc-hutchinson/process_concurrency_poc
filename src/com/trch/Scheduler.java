package com.trch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomashutchinson on 29/07/2017.
 */
public class Scheduler extends Thread {

    private List<IProcess> processes = new ArrayList<IProcess>();

    public void startProcess(IProcess process) {
        process.init();

        //lazy hack
        synchronized (processes) {
            processes.add(process);
        }
    }

    public void run() {
        while (true) {
            synchronized (processes) {
                for (IProcess process : processes) {
                    if (process.hasAnyMessages()) {
                        final IProcessMessage processMessage = process.takeProcessMessage();
                        process.handleMessage(processMessage);
                    }
                }
            }
        }
    }
}
