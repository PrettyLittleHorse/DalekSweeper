package dev.dariakrylova.daleksweeper.game;

import dev.dariakrylova.daleksweeper.util.Coord;
import dev.dariakrylova.daleksweeper.util.Ranges;

public class Game {
    private final Dalek dalek;
    private final Flag flag;
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
            if (flag.getCountOfFlaggedBoxesAround(coord) == dalek.get(coord).getNumber())
                for (Coord around : Ranges.getCoordinatesAround(coord))
                    if (flag.get(around) == Box.CLOSED)
                        openBox(around);
    }

    private void openDaleks(Coord exterminated) {
        state = GameState.EXTERMINATED;
        flag.setShowedDaleksToBox(exterminated);
        for (Coord coord : Ranges.getAllCoordinates())
            if (dalek.get(coord) == Box.DALEK)
                flag.setOpenedToClosedDalekBox(coord);
            else
                flag.setNoDalekToFlaggedSafeBox(coord);

    }

    private void openBoxesAround(Coord coord) {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordinatesAround(coord))
            openBox(around);
    }

    public void pressRightButton(Coord coord) {
        if (gameOver()) return;
        flag.toggleFlaggedToBox(coord);
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED)
            return false;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start();
        return true;
    }
}
