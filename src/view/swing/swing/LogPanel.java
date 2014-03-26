package view.swing.swing;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {

    private JTextArea logText;

    public LogPanel() {
        setBackground(Color.white);
    }

    public void setLogText() {
        logText = new JTextArea();
        logText.setEditable(true);
        logText.setFocusable(true);
        JScrollPane logScrollPane = new JScrollPane(logText);
        logScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(logScrollPane);
    }

    public void writeLog(String text) {
        logText.setText(logText.getText() + text);
    }

    public void cleanLog() {
        logText.setText("");
    }


}
