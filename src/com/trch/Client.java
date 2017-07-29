package com.trch;

/**
 * Created by thomashutchinson on 29/07/2017.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        ProcessManager processManager = ProcessManager.getInstance();
        processManager.start();

        IProcess process1 = new EchoProcess("process 1");
        processManager.startProcess(process1);

        IProcess process2 = new EchoProcess("process 2");
        processManager.startProcess(process2);


        process1.sendMessage(new Echo(process2, "message from main method"));

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static class EchoProcess extends IProcess {

        private final String NAME;

        public EchoProcess(String name){
            this.NAME = name;
        }
        @Override
        public void init() {
            System.out.println("Starting echo process");
        }

        @Override
        public void handleMessage(IProcessMessage processMessage) {
            Echo echo = (Echo) processMessage;
            System.out.println("Received message:" + echo.toString());
            String message = echo.getMessage();
            echo.getProcess().sendMessage(new Echo(this, NAME));
        }
    }

    public static class Echo implements IProcessMessage {

        private final IProcess process;
        private String message;

        public Echo(IProcess process, String message) {
            this.process = process;
            this.message = message;
        }

        public IProcess getProcess() {
            return process;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "Echo{" +
                    "process=" + process +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
