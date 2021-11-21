package com.supanadit.restsuite.listener.socket;
import com.supanadit.restsuite.panel.socket.SocketIoPanel;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.*;
class SocketIOListenerMouseTableRowMenu extends JPopupMenu {
    public SocketIOListenerMouseTableRowMenu(SocketIoPanel panel) {
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(( e) -> {
            panel.deleteSelectedRowListener();
        });
        add(delete);
    }
}