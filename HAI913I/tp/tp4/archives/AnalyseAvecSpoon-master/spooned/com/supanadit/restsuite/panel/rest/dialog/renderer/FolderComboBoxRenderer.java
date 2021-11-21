package com.supanadit.restsuite.panel.rest.dialog.renderer;
import com.supanadit.restsuite.entity.CollectionStructureFolderEntity;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import java.awt.*;
import javax.swing.*;
public class FolderComboBoxRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof CollectionStructureFolderEntity) {
            value = ((CollectionStructureFolderEntity) (value)).getName();
        }
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        return this;
    }
}