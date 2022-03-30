package dev.dariakrylova.daleksweeper.game;

import dev.dariakrylova.daleksweeper.util.Coord;
import dev.dariakrylova.daleksweeper.util.Ranges;

class Matrix {
    private final Box[][] gameMatrix;

    Matrix(Box defaultBox) {
        gameMatrix = new Box[Ranges.getSize().x][Ranges.getSize().y];
        for (Coord coord : Ranges.getAllCoordinates())
            gameMatrix[coord.x][coord.y] = defaultBox;
    }

    Box get(Coord coord) {
        if (Ranges.inRange(coord))
            return gameMatrix[coord.x][coord.y];
        else return null;
    }

    void set(Coord coord, Box box) {
        if (Ranges.inRange(coord))
            gameMatrix[coord.x][coord.y] = box;
    }
}
