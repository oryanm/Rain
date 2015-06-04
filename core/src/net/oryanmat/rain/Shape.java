package net.oryanmat.rain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;


import static net.oryanmat.rain.TetraRain.COLUMNS;
import static net.oryanmat.rain.TetraRain.SHAPE_SIZE;
import static net.oryanmat.rain.Orientation.*;

public class Shape {
    public static Shape spawn() {
        return new Shape(getRandom(), 4, 17, getColor());
    }

    public static Shape empty() {
        return new Shape(Orientation.NULL, 0, 0, Color.CLEAR);
    }

    public static Orientation getRandom() {
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

    public static Color getColor() {
        switch (MathUtils.random(0, 2)) {
            default:
            case 0:
                return new Color(0xCC0000FF);
            case 1:
                return new Color(0x0099CCFF);
            case 2:
                return new Color(0x669900FF);
        }
    }


    Orientation orientation;
    int column = 0;
    int row = 0;
    Color color;

    public Shape(Orientation orientation, int column, int row, Color color) {
        this.orientation = orientation;
        this.column = column;
        this.row = row;
        this.color = color;
    }

    public boolean occupiedAt(int column, int row) {
        return orientation.apply(column, row);
    }

    public void rotate() {
        orientation = ROTATION.get(orientation);
    }

    public void drop() {
        row -= 1;
    }

    public void left() {
        column -= 1;
    }

    public void right() {
        column += 1;
    }

    public boolean isEmpty() {
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
