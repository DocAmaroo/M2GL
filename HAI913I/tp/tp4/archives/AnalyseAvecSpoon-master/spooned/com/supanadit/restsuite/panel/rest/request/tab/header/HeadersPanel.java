package com.supanadit.restsuite.panel.rest.request.tab.header;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
public class HeadersPanel extends JPanel {
    public HeadersFormPanel headersFormPanel;

    public HeadersPanel() {
        super(new MigLayout());
        headersFormPanel = new HeadersFormPanel();
        add(headersFormPanel, "grow,push");
    }

    public HeadersFormPanel getHeadersFormPanel() {
        return headersFormPanel;
    }
}