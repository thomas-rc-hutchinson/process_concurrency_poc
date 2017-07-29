package com.trch;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Created by thomashutchinson on 29/07/2017.
 */
public abstract class IProcess {

    private Queue<IProcessMessage> processMessages = new LinkedList<>();

    abstract void init();

    final void sendMessage(IProcessMessage processMessage){
        //lazy hack
        synchronized (processMessage){
            processMessages.add(processMessage);
        }
    }

    final Optional<IProcessMessage> takeProcessMessage(){
        //lazy hack
        synchronized (processMessages){
        if(hasAnyMessages()){
            return Optional.of(processMessages.remove());
        }
        return Optional.empty();
        }
    }

    final private boolean hasAnyMessages() {
        return !processMessages.isEmpty();
    }

    public abstract void handleMessage(IProcessMessage processMessage);
}
