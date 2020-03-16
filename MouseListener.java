package sample;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class MouseListener implements java.awt.event.MouseListener {

    Controller main;
    JFrame frame;
    JTextArea area;

    public MouseListener(Controller main, JFrame frame, JTextArea area) {
        this.main = main;
        this.frame = frame;
        this.area = area;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(e.getX());
        System.out.println(e.getY());
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        area.setText("X: " + e.getX() + "\nY: " + e.getY());
        if (frame.getTitle().equalsIgnoreCase("bt1")) {
            main.x1 = e.getX();
            main.y1 = e.getY();
        } else if (frame.getTitle().equalsIgnoreCase("bt2")){
            main.x2 = e.getX();
            main.y2 = e.getY();
        } else {
            main.clickX = e.getX();
            main.clickY = e.getY();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
