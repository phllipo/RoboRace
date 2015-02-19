
    package de.otto.roboappOld;
    import android.os.Bundle;
    import android.support.v7.app.ActionBarActivity;
    import org.java_websocket.client.WebSocketClient;
    /**
     * Created by luca on 06.02.15.
     */
    public class Controller extends ActionBarActivity {
        public ServerController sc;
        public WebSocketClient wsc;
        private String serverIp;
        private String serverPort;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            sc = new ServerController(serverIp, serverPort);
        }
    }

