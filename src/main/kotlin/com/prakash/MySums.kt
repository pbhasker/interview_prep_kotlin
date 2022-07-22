package com.prakash

/**
 * 1 0 0
 * 0 1 0
 * 0 0 0
 */
class MySums(val x: Int, val y: Int) {
    val board = Array(x) { IntArray(y) { 0 } }
    
    fun get(x: Int, y: Int): Int {
        if (x < 0) {
            return 0
        }
        if (y < 0) {
            return 0
        }
        
        return board[x][y]
    }
    
    fun put(row: Int, col: Int, value: Int) {
        val current = get(row, col)
        
        val currentValue = current - (get(row, col - 1) - get(row - 1, col - 1)) - get(row - 1, col)
        val diff = value - currentValue
        for (i in row until x) {
            for (j in col until y) {
                board[i][j] += diff
            }
        }
    }
}