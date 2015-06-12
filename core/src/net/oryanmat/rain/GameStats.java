package net.oryanmat.rain;

public class GameStats {
    int rows = 0;
    int score = 0;

    public GameStats(int rows, int score) {
        this.rows = rows;
        this.score = score;
    }

    void update(int fullRows) {
        rows += fullRows;
        score += getScore(fullRows);
    }

    int getLevel() {
        return (int) Math.ceil(rows / 10 + 1);
    }

    private int getScore(int rows) {
        switch (rows) {
            default:
                return 0;
            case 1:
                return 40;
            case 2:
                return 100;
            case 3:
                return 300;
            case 4:
                return 1200;
        }
    }

    double getSpeed() {
        switch (getLevel()) {
            case 1:
                return 0.799;
            case 2:
                return 0.715;
            case 3:
                return 0.632;
            case 4:
                return 0.549;
            case 5:
                return 0.466;
            case 6:
                return 0.383;
            case 7:
                return 0.300;
            case 8:
                return 0.216;
            case 9:
                return 0.133;
            case 10:
                return 0.100;
            case 11:
            case 12:
            case 13:
                return 0.083;
            case 14:
            case 15:
            case 16:
            default:
                return 0.067;
        }
    }
}
