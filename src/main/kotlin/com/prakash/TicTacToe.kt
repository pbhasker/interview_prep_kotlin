package com.prakash

// Tic tac toe
// 3x3 board

enum class TicTacToe {
    X,
    O,
    None,
}

class TicTacToeGame {
    var activePlayer = TicTacToe.X
    val board = arrayOf(
        arrayOf(TicTacToe.None, TicTacToe.None, TicTacToe.None),
        arrayOf(TicTacToe.None, TicTacToe.None, TicTacToe.None),
        arrayOf(TicTacToe.None, TicTacToe.None, TicTacToe.None)
    )

    fun main() {
        val (a, b) = readLine()!!.split(' ')
        println(a.toInt() + b.toInt())
    }

    fun play(x: Int, y: Int): TicTacToe {
        require(board[x][y] == TicTacToe.None) { "Tile is already populated" }

        // Place tile
        board[x][y] = activePlayer

        // Check winner
        val winner = isWinner()
        if (TicTacToe.None != winner) {
            return winner
        }

        // Check is complete
        if (isComplete()) {
            return TicTacToe.None
        }

        if (activePlayer == TicTacToe.O) {
            activePlayer = TicTacToe.X
        } else {
            activePlayer = TicTacToe.O
        }

        return TicTacToe.None
    }

    fun isWinner(): TicTacToe {
        for (i in board.indices) {
            val s = board[i].toSet()
            if (s.size > 1) {
                continue
            }
            if (s.contains(TicTacToe.O)) {
                return TicTacToe.O
            }
            if (s.contains(TicTacToe.X)) {
                return TicTacToe.X
            }
        }
        for (i in board.indices) {
            val s = emptySet<TicTacToe>().toMutableSet()
            for (j in board[i].indices) {
                s.add(board[j][i])
            }
            if (s.size > 1) {
                continue
            }
            if (s.contains(TicTacToe.O)) {
                return TicTacToe.O
            }
            if (s.contains(TicTacToe.X)) {
                return TicTacToe.X
            }
        }

        val s1 = setOf(board[0][0], board[1][1], board[2][2])
        if (s1.size == 1 && s1.contains(TicTacToe.O)) {
            return TicTacToe.O
        }
        if (s1.size == 1 && s1.contains(TicTacToe.X)) {
            return TicTacToe.X
        }
        val s2 = setOf(board[2][0], board[1][1], board[0][2])
        if (s2.size == 1 && s2.contains(TicTacToe.O)) {
            return TicTacToe.O
        }
        if (s2.size == 1 && s2.contains(TicTacToe.X)) {
            return TicTacToe.X
        }
        return TicTacToe.None
    }

    fun isComplete(): Boolean {
        for (i in board.indices) {
            for (j in board[i].indices) {
                if (board[i][j] == TicTacToe.None) {
                    return false
                }
            }
        }
        return true
    }
}