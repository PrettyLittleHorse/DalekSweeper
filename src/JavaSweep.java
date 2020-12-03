import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Sweeper.Box;
//импортируем созданный нами бокс
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;


public class JavaSweep extends JFrame
    //[pablic, private, protected] - модификаторы доступа, управляет видимостью метода и любой переменной
        // class - создает новый класс, дажее в фиг скобках его описание, название главного публичного класса должно совпадать с названием файла (JavaSweep.java)
        // JFrame создает окно, тут добавляется расширение, ниже вызывается

{
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int BOMBS = 10;
    private final int ROWS = 9;
    private final int IMAGE_SIZE = 50;


    public static void main(String[] args)
    //public означает что что метод main виден и доступен любому классу
    //static Методы, в объявлении которых использовано ключевое слово static, могут непосредственно работать только с локальными и статическими переменными.
    //void
    //main обязательный метод в приложении. содержит сет инструкций, объясняющиъ как решать нужную задачу
    {
        new JavaSweep();
        //.setVisible(true)
        //в методе main содержатся инструкции (statements;) , запускаемые во время включения программы
    }

    private JavaSweep ()
    {
        game = new Game(COLS, ROWS, BOMBS);
        //Ranges.setSize (new Coord (COLS, ROWS));
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
        //ВЫЗЫВАЕМ МЕТО
    }

    private void initLabel ()
    {
        label = new JLabel("Welcome!");
        add (label, BorderLayout.SOUTH);
    }
    private void initPanel ()
    {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent (Graphics g)
            {

                super.paintComponents(g);
                for (Coord coord : Ranges.getAllCoords())
                {
                    //Coord coord = new Coord(box.ordinal() * IMAGE_SIZE, 0);
                    g.drawImage((Image) game.getBox(coord).image, coord.x * IMAGE_SIZE, coord.y * IMAGE_SIZE, this);
                    //Box.values() [(coord.x + coord.y) % Box.values().length]
                }
            }
        };
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton (coord);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start ();
                label.setText(getMessage ());
                panel.repaint();
            }
        });
        panel.setPreferredSize (new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
         add (panel);
    }

    private String getMessage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Thitk twice!";
            case BOMBED: return "You lose BABAX!!";
            case WINNER:return  "CONGRATULATIUNS";
            default: return "Welcome";
        }
    }

    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle ("JavaSweeper");
        setResizable(false);
        setVisible(true);
        pack ();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImages()
    {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }

    private Image getImage (String name)
    {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon (getClass() .getResource(filename));
        return icon.getImage();
    }
// ghjdthrf
}