import javax.swing.*;
import java.awt.*;
import Sweeper.Box;
//импортируем созданный нами бокс
import Sweeper.Coord;
import Sweeper.Game;
import Sweeper.Ranges;


public class JavaSweep extends JFrame
    //[pablic, private, protected] - модификаторы доступа, управляет видимостью метода и любой переменной
        // class - создает новый класс, дажее в фиг скобках его описание
        // JFrame создает окно, тут добавляется расширение, ниже вызывается

{
    private Game game;
    private JPanel panel;
    private final int COLS = 9;
    private final int BOMBS = 9;
    private final int ROWS = 10;
    private final int IMAGE_SIZE = 50;


    public static void main(String[] args)
    //public означает что что метод main виден и доступен любому классу
            //static  объявляются методы и переменные класса, используемые для работы с классом в целом. Методы, в объявлении которых использовано ключевое слово static, могут непосредственно работать только с локальными и статическими переменными.
            //void
            //main
    {
        new JavaSweep();
        //.setVisible(true)
    }

    private JavaSweep ()
    {
        game = new Game(COLS, ROWS, BOMBS);
        //Ranges.setSize (new Coord (COLS, ROWS));
        game.start();
        setImages();
        initPanel();
        initFrame();
        //ВЫЗЫВАЕМ МЕТО
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
        panel.setPreferredSize (new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
         add (panel);
    }
    private void initFrame ()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle ("JavaSweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(getImage("icon"));
        pack ();
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