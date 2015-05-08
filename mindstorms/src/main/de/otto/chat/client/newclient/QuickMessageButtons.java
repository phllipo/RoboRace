package de.otto.chat.client.newclient;

import org.java_websocket.client.WebSocketClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by luca on 08.05.15.
 */
public class QuickMessageButtons extends JPanel implements ActionListener {
    private JTextField ta;
    private JButton connectApp;
    private JButton connectRobo;
    private JButton selectRobo;
    private JButton ready;
    private JButton deselectRobo;
    private JButton finish;
    public QuickMessageButtons(JTextField ta) {
        this.ta = ta;

        this.setLayout(new GridLayout(2, 6));
        connectApp = new JButton("connect App");
        connectApp.addActionListener(this);
        connectApp.setToolTipText("connect App");
        add(connectApp);
        connectRobo = new JButton("connect Robo");
        connectRobo.addActionListener(this);
        connectRobo.setToolTipText("connect Robo");
        add(connectRobo);
        selectRobo = new JButton("select Robo");
        selectRobo.addActionListener(this);
        selectRobo.setToolTipText("select Robo (App only)");
        add(selectRobo);
        ready = new JButton("ready");
        ready.addActionListener(this);
        ready.setToolTipText("ready (App only)");
        add(ready);
        deselectRobo = new JButton("deselect Robo");
        deselectRobo.addActionListener(this);
        deselectRobo.setToolTipText("deselect Robo (App only)");
        add(deselectRobo);
        finish = new JButton("finish");
        finish.addActionListener(this);
        finish.setToolTipText("finish (Robo only)");
        add(finish);



        setVisible(true);
    }
    public void actionPerformed( ActionEvent e ) {
        Object i = e.getSource();
        if (i.equals(connectApp)) {
            ta.setText("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"app\", \"name\": \"TestApp\", \"ready\": \"false\" }}");
        } else if (i.equals(connectRobo)) {
            ta.setText("{\"eventType\": \"connect\", \"data\": {\"clientType\": \"robo\", \"name\": \"TestRobo\", \"ready\": \"true\" }}");
        } else if (i.equals(selectRobo)) {
            ta.setText("{\"eventType\": \"selectRobo\", \"data\": { \"robo\": \"TestRobo\"}}");
        } else if (i.equals(ready)) {
            ta.setText("{\"eventType\": \"ready\", \"data\": { \"ready\": \"true\"}}");
        } else if (i.equals(deselectRobo)) {
            ta.setText("{\"eventType\": \"deselectRobo\"}");
        } else if (i.equals(finish)) {
            ta.setText("{\"eventType\": \"finish\"}");
        }
    }
}
