package Sweeper;

class Dalek {
    private Matrix dalekMap;
    private int totalDaleks;

    Dalek(int totalDaleks) {
        this.totalDaleks = totalDaleks;
        fixDaleksCount();
    }

    void start() {
        dalekMap = new Matrix(Box.ZERO);
        for (int j = 0; j < totalDaleks; j++)
            placeDalek();
    }

    Box get(Coord coord) {
        return dalekMap.get(coord);
    }

    private void fixDaleksCount() {
        int maxDaleks = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalDaleks > maxDaleks)
            totalDaleks = maxDaleks;
    }

    private void placeDalek() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if (Box.DALEK == dalekMap.get(coord))
                continue;
            dalekMap.set(coord, Box.DALEK);
            incNumbersRoundDalek(coord);
            break;
        }
    }

    private void incNumbersRoundDalek(Coord coord) {
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.DALEK != dalekMap.get(around))
                dalekMap.set(around, dalekMap.get(around).getNextNumberBox());
    }

    int getTotalDaleks() {
        return totalDaleks;
    }

}
