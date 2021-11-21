package com.supanadit.restsuite.renderer;
import com.supanadit.restsuite.model.BodyRawTypeModel;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.*;
import javax.swing.*;
public class RequestBodyRawTypeRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof BodyRawTypeModel) {
            value = ((BodyRawTypeModel) (value)).getName();
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}