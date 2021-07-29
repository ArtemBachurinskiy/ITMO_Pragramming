package server.main;

import general.application.Application;
import server.application.ServerApplication;

public class ServerMain {
    public static void main(String[] args) {
        Application application = new ServerApplication();
        application.start(args);
    }
}
