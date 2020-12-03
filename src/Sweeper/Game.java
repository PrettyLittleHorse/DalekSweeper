package Sweeper;

public class Game
{
    private Bomb bomb;
    private Flag flag;

    public Game (int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coord (cols,rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start ()
    {
        bomb.start();
        flag.start();
    }


    public Box getBox (Coord coord)
    {
        if (flag.get(coord) == Box.OPENED )
            return bomb.get(coord);
        return flag.get(coord);
        //return bomb.get(coord); - раньше мы возвращали верхний слой, теперь нижний
                //Box.values() [(coord.x + coord.y) % Box.values().length];

    }

    public void pressLeftButton(Coord coord)
    {
        flag.setOpenedToBox (coord);
    }
    public void pressRightButton(Coord coord)
    {
        flag.toggleFlagedToBox (coord);
    }
}
