package dev.dariakrylova.daleksweeper.game;

import dev.dariakrylova.daleksweeper.util.Coord;
import dev.dariakrylova.daleksweeper.util.Ranges;

public class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get(Coord coord) {
        return flagMap.get(coord);
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Box.OPENED);
        countOfClosedBoxes--;
    }

    void toggleFlaggedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED:
                setClosedToBox(coord);
                break;
            case CLOSED:
                setFlagedToBox(coord);
                break;
        }
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Box.CLOSED);
    }

    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Box.FLAGED);
    }

    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    public void setShowedDaleksToBox(Coord coord) {
        flagMap.set(coord, Box.EXTERMINATED);
    }

    void setOpenedToClosedDalekBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    void setNoDalekToFlaggedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord, Box.NODALEK);
    }


    int getCountOfFlaggedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around : Ranges.getCoordinatesAround(coord))
            if (flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }

}
