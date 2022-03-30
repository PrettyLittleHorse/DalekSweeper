package dev.dariakrylova.daleksweeper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ranges {

    private static Coord size;
    private static ArrayList<Coord> allCoordinates;
    private static final Random random = new Random();

    private Ranges() {
        throw new IllegalStateException("Utility class");
    }

    public static void setSize(Coord size) {
        Ranges.size = size;
        allCoordinates = new ArrayList<>();
        for (int y = 0; y < Ranges.size.y; y++)
            for (int x = 0; x < Ranges.size.x; x++)
                allCoordinates.add(new Coord(x, y));
    }

    public static Coord getSize() {
        return size;
    }

    public static List<Coord> getAllCoordinates() {
        return allCoordinates;
    }

    public static boolean inRange(Coord coord) {
        return coord.x >= 0 && coord.x < size.x &&
                coord.y >= 0 && coord.y < size.y;
    }

    public static Coord getRandomCoordinates() {
        return new Coord(random.nextInt(size.x), random.nextInt(size.y));
    }

    public static ArrayList<Coord> getCoordinatesAround(Coord coord) {
        Coord around;
        ArrayList<Coord> list = new ArrayList<>();
        for (int x = coord.x - 1; x <= coord.x + 1; x++)
            for (int y = coord.y - 1; y <= coord.y + 1; y++)
                if (inRange(around = new Coord(x, y)))
                    if (!around.equals(coord))
                        list.add(around);
        return list;
    }
}
