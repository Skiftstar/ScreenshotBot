package sample;

import com.sun.java.accessibility.util.java.awt.ButtonTranslator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller extends JPanel implements ActionListener {

    JTextField area1, area2, area3;
    JTextArea field, delay;
    File folder;
    int x1 = -1, x2 = -1, y1 = -1, y2 = -1, clickX = -1, clickY = -1;

    public Controller() {
        JFrame frame = new JFrame("Screenshot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        delay = new JTextArea("Delay in MilliSeconds (Just delete this text)");
        JButton button = new JButton("XY One");
        button.addActionListener(new ButtonOneListener(this, "bt1"));
        JButton clickBt = new JButton("XY Click");
        clickBt.addActionListener(new ButtonOneListener(this, "bt3"));
        JButton start = new JButton("start");
        start.addActionListener(this);
        area1 = new JTextField("X:\nY:");
        area1.setEditable(false);
        area1.setVisible(true);
        area3 = new JTextField("X:\nY:");
        area3.setEditable(false);
        area1.setVisible(true);
        area2 = new JTextField("X:\nY:");
        area2.setEditable(false);
        area2.setVisible(true);
        JButton button2 = new JButton("XY Two");
        button2.addActionListener(new ButtonOneListener(this, "bt2"));
        JTextField FolderName = new JTextField("Please select Folder");
        FolderName.setEditable(false);
        JButton folderBt = new JButton("Folder");
        field = new JTextArea("Number of Screenshots (just delete this text here)");
        folderBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showOpenDialog(frame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    folder = file;
                    FolderName.setText("Folder Selected: " + file.getName());
                }else{
                    FolderName.setText("Open command canceled");
                }
            }
        });
        //Create and set up the content pane.
        JComponent newContentPane = this;
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        newContentPane.setOpaque(true); //content panes must be opaque
        newContentPane.add(delay);
        newContentPane.add(button);
        newContentPane.add(area1);
        newContentPane.add(button2);
        newContentPane.add(area2);
        newContentPane.add(clickBt);
        newContentPane.add(area3);
        newContentPane.add(folderBt);
        newContentPane.add(FolderName);
        newContentPane.add(field);
        newContentPane.add(start);
        frame.setContentPane(newContentPane);
        frame.setSize(200, 200);
        Dimension dim = new Dimension(200, 200);
        frame.setMinimumSize(dim);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void Click(int x, int y) {
        Robot bot = null;
        try {
            bot = new Robot();
            bot.mouseMove(x, y);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void changeAreaOne(String text) {
        area1.setText(text);
    }

    public void changeAreaTwo(String text) {
        area2.setText(text);
    }


    private void takeScreenshot(String filename, int x1, int x2, int y1, int y2, int amount, int delay) {
        for (int i = 0; i < amount; i++) {
            try {
                Robot robot = new Robot();

                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();
                x.add(x1);
                x.add(x2);
                y.add(y1);
                y.add(y2);
                Collections.sort(x);
                Collections.sort(y);

                int width = x.get(1) - x.get(0);
                int height = y.get(1) - y.get(0);

                Rectangle screenRect = new Rectangle(width, height);
                screenRect.setLocation(x.get(0), y.get(0));
                BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
                ImageIO.write(screenFullImage, "jpg", new File(folder, "Screenshot-" + i + ".jpg"));

                System.out.println("A full screenshot saved!");
                Click(clickX, clickY);
                Thread.sleep(delay);
            } catch (AWTException | IOException | InterruptedException ex) {
                System.err.println(ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (x1 != -1 && x2 != -1 && y1 != -1 && y2 != -1 && clickX != -1 && clickY != -1 && folder != null) {
            takeScreenshot("abc", x1, x2, y1, y2, Integer.parseInt(field.getText()), Integer.parseInt(delay.getText()));
        } else {
            System.out.println("fehlaa");
        }
    }
}
