package com.trch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomashutchinson on 29/07/2017.
 */
public class ProcessManager {
    private final static ProcessManager ourInstance = new ProcessManager();
    private final List<Scheduler> schedulers = new ArrayList<>();

    //used for balancing work between schedulers
    private int pos = 0;
    private final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static ProcessManager getInstance() {
        return ourInstance;
    }

    private ProcessManager() {
    }

    public static void start(){
        getInstance().doStart();
    }

    public void doStart(){
        for(int i = 0; i <= AVAILABLE_PROCESSORS; i++){
            final Scheduler scheduler = new Scheduler();
            schedulers.add(scheduler);
            scheduler.start();
        }
    }

    public static void startProcess(final IProcess process){
        getInstance().doStartProcess(process);
    }

    public void doStartProcess(final IProcess process){
        final Scheduler scheduler = schedulers.get(pos);

        if(pos == (AVAILABLE_PROCESSORS - 1)){
            pos = 0;
        }
        else {
            pos++;
        }

        scheduler.startProcess(process);
    }
}
