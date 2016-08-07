package net.oryanmat.rain

import net.oryanmat.rain.TetraRain.*

internal class Board {
    private val board = Array(COLUMNS, { Array(ROWS + 2, { Block() }) })

    fun occupiedAt(column: Int, row: Int) = board[column][row].empty.not()

    fun colorAt(column: Int, row: Int) = board[column][row].color

    fun occupiedOrOutOfBounds(column: Int, row: Int): Boolean {
        return column >= COLUMNS || column < 0 || row < 0 || occupiedAt(column, row)
    }

    private fun setInBoard(shape: Shape) {
        for (i in 0..SHAPE_SIZE - 1) {
            for (j in 0..SHAPE_SIZE - 1) {
                if (shape.occupiedAt(i, j)) {
                    val block = board[shape.column + i][shape.row + j]
                    block.empty = false
                    block.color = shape.color
                }
            }
        }
    }

    private fun checkForFullRow(): Int {
        var fullRows = 0

        var j = 0
        while (j < ROWS) {
            if (isRowFull(j)) {
                clearFullRow(j)
                dropBoard(j)
                fullRows++
                // move back one row, so we don't skip j + 1 which is now j
                j--
            }
            j++
        }

        return fullRows
    }

    fun setInBoardAndCheckFullRow(shape: Shape): Int {
        setInBoard(shape)
        return checkForFullRow()
    }

    private fun isRowFull(row: Int): Boolean {
        var full = true

        for (column in 0..COLUMNS - 1) {
            full = full and occupiedAt(column, row)
        }

        return full
    }

    private fun clearFullRow(row: Int) {
        for (i in 0..COLUMNS - 1) {
            board[i][row].empty = true
        }
    }

    private fun dropBoard(row: Int) {
        for (j in row + 1..ROWS - 1) {
            for (i in 0..COLUMNS - 1) {
                board[i][j - 1].empty = board[i][j].empty
                board[i][j - 1].color = board[i][j].color
            }
        }
    }
}
