package dev.dariakrylova.daleksweeper;

import dev.dariakrylova.daleksweeper.game.Box;
import dev.dariakrylova.daleksweeper.game.Game;
import dev.dariakrylova.daleksweeper.util.Coord;
import dev.dariakrylova.daleksweeper.util.Ranges;
import dev.dariakrylova.daleksweeper.util.Sound;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class DalekSweeper extends JFrame {

    private static final String GAME_NAME = "DalekSweeper";
    private static final String GAME_ICON = "icon";
    private static final int IMAGE_SIZE = 95;
    private Game game;
    private JPanel panel;
    private JLabel label;
    private int daleks;
    private int cols;
    private int rows;


    public static void main(String[] args) {
        try {
            new Sound("menusound");
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        new DalekSweeper();
    }

    public DalekSweeper() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(800, 400, 270, 165);
        setTitle(GAME_NAME);
        setResizable(false);

        JTextField fieldWidthInput = new JTextField(3);
        JTextField fieldHeightInput = new JTextField(3);
        JTextField numberOfDaleksInput = new JTextField(3);

        JLabel fieldWidth = new JLabel("Select field width: ");
        JLabel fieldHeight = new JLabel("Select field height: ");
        JLabel numberOfDaleks = new JLabel("Select the number of daleks: ");
        JLabel standardLabel = new JLabel("Standard 10,10,11");
        standardLabel.setForeground(Color.RED);
        JButton button = new JButton("Start");

        JPanel startPanel = new JPanel(new FlowLayout());
        button.addActionListener(e -> {
            try {
                cols = Integer.parseInt(fieldWidthInput.getText());
                rows = Integer.parseInt(fieldHeightInput.getText());
                daleks = Integer.parseInt(numberOfDaleksInput.getText());
                game = new Game(cols, rows, daleks);
                startPanel.removeAll();
                remove(startPanel);
                initLabel();
                setImages();
                initPanel();
                game.start();
                initFrame();

            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(startPanel, "Incorrect data!");
            }
        });

        startPanel.add(fieldWidth);
        startPanel.add(fieldWidthInput);
        startPanel.add(fieldHeight);
        startPanel.add(fieldHeightInput);
        startPanel.add(numberOfDaleks);
        startPanel.add(numberOfDaleksInput);
        startPanel.add(button);
        startPanel.add(standardLabel);
        add(startPanel);
        setIconImage(getImage(GAME_ICON));
        setImages();
        setVisible(true);
    }

    private void initLabel() {
        label = new JLabel("There are so many Daleks here... ");
        try {
            new Sound("startsound");
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                for (Coord coord : Ranges.getAllCoordinates()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    new Sound("openeggsound");
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException lineUnavailableException) {
                    lineUnavailableException.printStackTrace();
                }
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }
        });

        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE + 1,
                Ranges.getSize().y * IMAGE_SIZE + 1));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {

            case PLAYED:
                label.setForeground(Color.BLACK);
                return "There are so many Daleks here.. " + daleks + " daleks remaining";

            case EXTERMINATED:
                label.setForeground(Color.RED);
                try {
                    new Sound("exterminate");
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
                return "EXTERMINATE!! \n You were exterminated. ";

            case WINNER:
                label.setForeground(Color.BLUE);
                try {
                    new Sound("winsound");
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
                return "You escaped from all " + daleks + " daleks";

            default:
                return "";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(GAME_NAME);
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage(GAME_ICON));
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}