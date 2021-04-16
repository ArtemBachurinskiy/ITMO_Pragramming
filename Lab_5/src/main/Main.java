package main;

import application.Application;
import java.io.*;

/**
 * This is the Main class.
 * @author Artem Bachurinskiy
 **/
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Application application = new Application();
        application.start(args);
    }
}