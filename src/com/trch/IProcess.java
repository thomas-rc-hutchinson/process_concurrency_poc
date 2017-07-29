package com.trch;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by thomashutchinson on 29/07/2017.
 */
public abstract class IProcess {

    private Queue<IProcessMessage> processMessages = new LinkedList<>();

    abstract void init();

    final void sendMessage(IProcessMessage processMessage){
        processMessages.add(processMessage);
    }

    final IProcessMessage takeProcessMessage(){
        return processMessages.remove();
    }

    final public boolean hasAnyMessages() {
        return !processMessages.isEmpty();
    }

    public abstract void handleMessage(IProcessMessage processMessage);
}
