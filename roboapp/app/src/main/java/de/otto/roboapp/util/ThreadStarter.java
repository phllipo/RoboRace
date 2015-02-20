package de.otto.roboapp.util;


public class ThreadStarter {
    public static void processInNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }
}
