import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;

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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(800, 400, 240, 165);

        JPanel panel = new JPanel(new FlowLayout());
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
                panel.removeAll();
                game = new Game(COLS, ROWS, DALEKS);
                game.start();
                setImages();
                initLabel();
                initPanel();
                initFrame();

            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(panel, "Incorrect data!");
            }
        });

        panel.add(fieldWidth);
        panel.add(fieldWidthInput);
        panel.add(fieldHeight);
        panel.add(fieldHeightInput);
        panel.add(numberOfDaleks);
        panel.add(numberOfDaleksInput);
        panel.add(button);
        panel.add(standardLabel);
        add(panel);
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
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
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
                return "There are so many Daleks here.. " + DALEKS + " daleks remaining";
            case EXTERMINATED:
                label.setForeground(Color.RED);
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
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}