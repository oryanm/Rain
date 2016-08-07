package net.oryanmat.rain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

import static net.oryanmat.rain.Orientation.*;
import static net.oryanmat.rain.TetraRain.COLUMNS;
import static net.oryanmat.rain.TetraRain.SHAPE_SIZE;

public class Shape {
    static Shape spawn() {
        return new Shape(getRandom(), 4, 17, Colors.getColor(1));
    }

    static Shape spawn(int level) {
        return new Shape(getRandom(), 4, 17, Colors.getColor(level));
    }

    static Shape empty() {
        return new Shape(Orientation.NULL, 0, 0, Color.CLEAR);
    }

    private static Orientation getRandom() {
        switch (MathUtils.random(0, 6)) {
            default:
            case 0:
                return O;
            case 1:
                return I_HORIZONTAL;
            case 2:
                return T_DOWN;
            case 3:
                return J_DOWN;
            case 4:
                return L_DOWN;
            case 5:
                return Z_HORIZONTAL;
            case 6:
                return S_HORIZONTAL;
        }
    }

    Orientation orientation;
    int column = 0;
    int row = 0;
    Color color;

    private Shape(Orientation orientation, int column, int row, Color color) {
        this.orientation = orientation;
        this.column = column;
        this.row = row;
        this.color = color;
    }

    public boolean occupiedAt(int column, int row) {
        return orientation.apply(column, row);
    }

    void rotate() {
        orientation = ROTATION.get(orientation);
    }

    void drop() {
        row -= 1;
    }

    void left() {
        column -= 1;
    }

    void right() {
        column += 1;
    }

    boolean isEmpty() {
        return orientation.equals(Orientation.NULL);
    }

    boolean isOverlapping(Board board) {
        for (int i = 0; i < SHAPE_SIZE; i++) {
            for (int j = 0; j < SHAPE_SIZE; j++) {
                int column = this.column + i;
                int row = this.row + j;

                if (occupiedAt(i, j) && board.occupiedOrOutOfBounds(column, row)) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean isBlocked(Board board) {
        for (int i = 0; i < SHAPE_SIZE; i++) {
            for (int j = 0; j < SHAPE_SIZE; j++) {
                int row = this.row + j - 1;

                if (this.occupiedAt(i, j) &&
                        (row < 0 || board.occupiedAt(this.column + i, row))) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean isBlockedRight(Board board) {
        for (int i = 0; i < SHAPE_SIZE; i++) {
            for (int j = 0; j < SHAPE_SIZE; j++) {
                int column = this.column + i + 1;

                if (this.occupiedAt(i, j) &&
                        (column >= COLUMNS || board.occupiedAt(column, this.row + j))) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean isBlockedLeft(Board board) {
        for (int i = 0; i < SHAPE_SIZE; i++) {
            for (int j = 0; j < SHAPE_SIZE; j++) {
                int column = this.column + i - 1;

                if (this.occupiedAt(i, j) &&
                        (column < 0 || board.occupiedAt(column, this.row + j))) {
                    return true;
                }
            }
        }

        return false;
    }
}
