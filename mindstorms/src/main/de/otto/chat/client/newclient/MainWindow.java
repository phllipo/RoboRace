package de.otto.chat.client.newclient;

import javax.swing.*;
import java.awt.*;

/**
 * Created by luca on 08.05.15.
 */
class MainWindow extends JFrame {
    public MainWindow(String location) {
        super( "WebSocket Chat Clients" );
        Container c = getContentPane();
        c.setLayout( new GridLayout(1,2) );

        JPanel panel_left = new de.otto.chat.client.newclient.SimpleClient(location, this);
        JPanel panel_right = new de.otto.chat.client.newclient.SimpleClient(location, this);

        c.add(panel_left);
        c.add(panel_right);

        java.awt.Dimension d = new java.awt.Dimension( 600, 500 );
        setPreferredSize( d );
        setSize( d );

        setVisible(true);
    }
}