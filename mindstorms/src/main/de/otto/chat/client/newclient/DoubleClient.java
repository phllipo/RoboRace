package de.otto.chat.client.newclient;

import de.otto.chat.client.SimpleClient;
import org.java_websocket.WebSocketImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Created by luca on 08.05.15.
 */
public class DoubleClient {
    public static void main(String[] args) {
        WebSocketImpl.DEBUG = true;
        String location;
        if( args.length != 0 ) {
            location = args[ 0 ];
            System.out.println( "Default server url specified: \'" + location + "\'" );
        } else {
            location = "ws://localhost:8888";
            System.out.println( "Default server url not specified: defaulting to \'" + location + "\'" );
        }
        //new SimpleClient( location );
        new MainWindow(location);
    }

}

