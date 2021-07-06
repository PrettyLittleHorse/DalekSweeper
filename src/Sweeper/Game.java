package Sweeper;

public class Game {
    private Dalek dalek;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int daleks) {
        Ranges.setSize(new Coord(cols, rows));
        dalek = new Dalek(daleks);
        flag = new Flag();
    }

    public void start() {
        dalek.start();
        flag.start();
        state = GameState.PLAYED;
    }


    public Box getBox(Coord coord) {
        if (flag.get(coord) == Box.OPENED)
            return dalek.get(coord);
        return flag.get(coord);
    }

    public void pressLeftButton(Coord coord) {
        if (gameOver()) return;
        openBox(coord);
        checkWinner();
    }

    private void checkWinner() {
        if (state == GameState.PLAYED)
            if (flag.getCountOfClosedBoxes() == dalek.getTotalDaleks())
                state = GameState.WINNER;
    }

    private void openBox(Coord coord) {
        switch (flag.get(coord)) {
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
                return;
            case FLAGED:
                return;
            case CLOSED:
                switch (dalek.get(coord)) {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case DALEK:
                        openDaleks(coord);
                        return;
                    default:
                        flag.setOpenedToBox(coord);
                }
        }
    }

    void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if (dalek.get(coord) != Box.DALEK)
            if (flag.getCountOfFlagedBoxesAround(coord) == dalek.get(coord).getNumber())
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openDaleks(Coord exterminated) {
        state = GameState.EXTERMINATED;
        flag.setShowedDaleksToBox(exterminated);
        for (Coord coord : Ranges.getAllCoords())
            if (dalek.get(coord) == Box.DALEK)
                flag.setOpenedToClosedDalekBox(coord);
            else
                flag.setNoDalekToFlagedSafeBox(coord);
    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}
