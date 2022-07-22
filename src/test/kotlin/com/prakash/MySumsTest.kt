package com.prakash

import org.junit.Assert.assertEquals
import org.junit.Test

class MySumsTest {
    @Test
    fun testNothing() {
        val sums = MySums(4, 4)
        assertEquals(0, sums.get(0, 0))
        assertEquals(0, sums.get(1, 1))
        
        sums.put(0, 0, 1)
        
        assertEquals(1, sums.get(0, 0))
        assertEquals(1, sums.get(1, 1))
        
        sums.put(1, 1, 1)
        
        assertEquals(1, sums.get(0, 0))
        assertEquals(2, sums.get(2, 2))
        
        sums.put(3, 3, 10)
        assertEquals(1, sums.get(0, 0))
        assertEquals(2, sums.get(2, 2))
        assertEquals(12, sums.get(3, 3))
        
        sums.put(0, 3, 20)
        
        assertEquals(1, sums.get(0, 0))
        assertEquals(2, sums.get(2, 2))
        assertEquals(21, sums.get(0, 3))
        assertEquals(32, sums.get(3, 3))
        
        
        sums.put(0, 3, 40)
        
        assertEquals(1, sums.get(0, 0))
        assertEquals(2, sums.get(2, 2))
        assertEquals(41, sums.get(0, 3))
        assertEquals(52, sums.get(3, 3))
        
        sums.put(0, 3, 10)
        
        assertEquals(1, sums.get(0, 0))
        assertEquals(2, sums.get(2, 2))
        assertEquals(11, sums.get(0, 3))
        assertEquals(22, sums.get(3, 3))
    }
}