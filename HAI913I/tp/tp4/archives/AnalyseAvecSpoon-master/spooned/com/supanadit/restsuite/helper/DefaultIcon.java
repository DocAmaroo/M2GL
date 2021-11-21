package com.supanadit.restsuite.helper;
import com.supanadit.restsuite.Main;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.*;
import javax.swing.*;
public class DefaultIcon extends ImageIcon {
    public DefaultIcon() {
        URL iconURL = Main.class.getClassLoader().getResource("icon/icon.png");
        assert iconURL != null;
        setImage(Toolkit.getDefaultToolkit().getImage(iconURL));
    }
}