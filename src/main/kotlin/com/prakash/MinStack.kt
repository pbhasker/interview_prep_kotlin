package com.prakash

import java.util.PriorityQueue

class MinStack {
    val stack = mutableListOf<Int>()
    val stackMins = mutableListOf<Int>()

    fun push(x: Int) {
        stack.add(0, x)
        if (stackMins.isEmpty()) {
            stackMins.add(0, x)
        } else {
            stackMins.add(0, Math.min(x, stackMins.first()))
        }
    }

    fun pop(): Int {
        stackMins.removeFirst()
        return stack.removeFirst()
    }

    fun getMin(): Int {
        return stackMins.first()
    }

    fun top(): Int {
        return stack.first()
    }
}