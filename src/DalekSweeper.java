import Sweeper.*;
import Sweeper.Box;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

public class DalekSweeper extends JFrame {
    private Game game;
    private JPanel panel;
    private JLabel label;
    private int COLS;
    private int DALEKS;
    private int ROWS;
    private final int IMAGE_SIZE = 95;

    public static void main(String[] args) {
        new DalekSweeper();
    }

    public DalekSweeper() {
        fieldCustomize();
    }

    private void fieldCustomize() {
        JPanel startPanel = new JPanel(new FlowLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(800, 400, 270, 165);
        setTitle("DalekSweeper");
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

        button.addActionListener(e -> {

            try {
                COLS = Integer.parseInt(fieldWidthInput.getText());
                ROWS = Integer.parseInt(fieldHeightInput.getText());
                DALEKS = Integer.parseInt(numberOfDaleksInput.getText());
                game = new Game(COLS, ROWS, DALEKS);
                startPanel.removeAll();
                remove(startPanel);
                game.start();
                setImages();
                initLabel();
                initPanel();
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

        setIconImage(getImage("icon"));
        setImages();
        setVisible(true);
    }

    private void initLabel() {
        label = new JLabel("There are so many Daleks here... ");
        add(label, BorderLayout.SOUTH);

    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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
        JButton button = new JButton("!");
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(button, BorderLayout.LINE_END);
        label.add(bottomPanel, BorderLayout.PAGE_END);
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {

            case PLAYED:
                label.setForeground(Color.BLACK);
                return "There are so many Daleks here.. " + DALEKS + " daleks remaining";

            case EXTERMINATED:
                label.setForeground(Color.RED);
                try {
                    new Sound("exterminationSound");
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
                return "EXTERMINATE!! \n You were exterminated. ";

            case WINNER:
                label.setForeground(Color.BLUE);
                return "You escaped from all " + DALEKS + " daleks";

            default:
                return "";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("DalekSweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages() {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage(String name) {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(filename)));
        return icon.getImage();
    }
}