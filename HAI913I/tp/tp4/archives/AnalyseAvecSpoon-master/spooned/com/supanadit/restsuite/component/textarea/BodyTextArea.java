package com.supanadit.restsuite.component.textarea;
import SyntaxConstants.SYNTAX_STYLE_HTML;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import java.awt.*;
import javax.swing.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
public class BodyTextArea extends RSyntaxTextArea {
    public BodyTextArea() {
        Color background = UIManager.getColor("Table.background");
        Color lineColor = UIManager.getColor("Table.gridColor");
        Color fontColor = UIManager.getColor("FormattedTextField.foreground");
        Color selectionColor = UIManager.getColor("FormattedTextField.selectionBackground");
        setSyntaxEditingStyle(SYNTAX_STYLE_HTML);
        setCodeFoldingEnabled(true);
        setCurrentLineHighlightColor(background);
        setBackground(background);
        setTabLineColor(background);
        setBorder(BorderFactory.createLineBorder(lineColor));
        setForeground(fontColor);
        setSelectionColor(selectionColor);
        // This Code let selected text be white
        setUseSelectedTextColor(true);
        setSelectedTextColor(Color.white);
    }
}