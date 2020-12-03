package Sweeper;

public class Game
{
    private Bomb bomb;
    private Flag flag;
    private GameState state;
    public GameState getState()
    {
        return state;
    }

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
        state = GameState.PLAYED;
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
        openBox (coord);
    }
    private void openBox (Coord coord)
    {
        switch (flag.get(coord))
        {
            case OPENED:return;
            case FLAGED:return;
            case CLOSED:
                switch (bomb.get (coord))
                {
                    case ZERO: openBoxesAround (coord);return;
                    case BOMB:return;
                    default: flag.setOpenedToBox(coord); return;
                }
        }
    }

    private void openBoxesAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord around: Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord)
    {
        flag.toggleFlagedToBox (coord);
    }
}
