package com.supanadit.restsuite.panel;
import com.supanadit.restsuite.panel.rest.RestPanel;
import com.supanadit.restsuite.panel.socket.SocketIoPanel;
import com.supanadit.restsuite.panel.sse.ServerSentEventPanel;
import com.supanadit.restsuite.panel.websocket.WebsocketPanel;
import java.io.IOException;
import javax.swing.JTabbedPane;
import org.apache.batik.transcoder.TranscoderException;
import javax.swing.*;
import org.apache.batik.transcoder.TranscoderException;
public class MainPanel extends JTabbedPane {
    private static final String restAPI = "Rest API";

    private static final String webSocket = "Websocket";

    private static final String sse = "SSE";

    private static final String socketIO = "Socket IO";

    public MainPanel() throws IOException, TranscoderException {
        add(restAPI, new RestPanel());
        add(webSocket, new WebsocketPanel());
        add(sse, new ServerSentEventPanel());
        add(socketIO, new SocketIoPanel());
    }
}