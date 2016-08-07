package net.oryanmat.rain;

import com.badlogic.gdx.graphics.Color;

import static net.oryanmat.rain.TetraRain.*;

class Board {
    private Block[][] board = new Block[COLUMNS][ROWS + 2];

    Board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Block();
            }
        }
    }

    boolean occupiedAt(int column, int row) {
        return !board[column][row].getEmpty();
    }

    boolean occupiedOrOutOfBounds(int column, int row) {
        return column >= COLUMNS || column < 0 || row < 0 || occupiedAt(column, row);
    }

    Color colorAt(int column, int row) {
        return board[column][row].getColor();
    }

    private void setInBoard(Shape shape) {
        for (int i = 0; i < SHAPE_SIZE; i++) {
            for (int j = 0; j < SHAPE_SIZE; j++) {
                if (shape.occupiedAt(i, j)) {
                    Block block = board[shape.column + i][shape.row + j];
                    block.setEmpty(false);
                    block.setColor(shape.color);
                }
            }
        }
    }

    private int checkForFullRow() {
        int fullRows = 0;

        for (int j = 0; j < ROWS; j++) {
            if (isRowFull(j)) {
                clearFullRow(j);
                dropBoard(j);
                fullRows++;
                // move back one row, so we don't skip j + 1 which is now j
                j--;
            }
        }

        return fullRows;
    }

    int setInBoardAndCheckFullRow(Shape shape) {
        setInBoard(shape);
        return checkForFullRow();
    }

    private boolean isRowFull(int row) {
        boolean full = true;

        for (int column = 0; column < COLUMNS; column++) {
            full &= occupiedAt(column, row);
        }

        return full;
    }

    private void clearFullRow(int row) {
        for (int i = 0; i < COLUMNS; i++) {
            board[i][row].setEmpty(true);
        }
    }

    private void dropBoard(int row) {
        for (int j = row + 1; j < ROWS; j++) {
            for (int i = 0; i < COLUMNS; i++) {
                board[i][j - 1].setEmpty(board[i][j].getEmpty());
                board[i][j - 1].setColor(board[i][j].getColor());
            }
        }
    }
}
