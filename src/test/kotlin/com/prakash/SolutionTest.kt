package com.prakash

import com.prakash.Solution.dijkstraAdjList
import com.prakash.Solution.dijkstraGraphNode
import com.prakash.Solution.mergeSort
import com.prakash.Solution.selectionSort
import com.prakash.Solution.swap
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class SolutionTest {
    @Test
    fun testMyHashMap() {
        val map = MyHashMap<String, String>()
        assertEquals(0, map.size)
        val key0 = "qwer"
        val key1 = "vbnm"
        val value0 = "asdf"
        val value1 = "uip"
        map.put(key0, value0)
        assertEquals(1, map.size)
        assertEquals(value0, map.getValue(key0))
        assert(map.loadFactor() <= MyHashMap.LOAD_FACTOR_THRESHOLD)
        assertNull(map.getValue("zxcv"))
        map.put(key0, value1)
        assertEquals(1, map.size)
        assertEquals(value1, map.getValue(key0))
        map.put(key1, value1)
        assertEquals(2, map.size)
        for (i in 0..1000) {
            map.put("$i", "$i")
        }
        assertEquals(1003, map.size)
        assertEquals("10", map.getValue("10"))
        
        for (i in 0..1000) {
            map.put("$i", "$i$i")
        }
        
        assertEquals(1003, map.size)
    }
    
    @Test
    fun testMergeSort() {
        val list = listOf(7, 6, 1, 3, 4, 2, 5)
        val res = mergeSort(list)
        assertEquals(list.sortedBy { it }, res)
    }
    
    @Test
    fun testMergeSortRandom() {
        val list = (0..1000).shuffled()
        val res = mergeSort(list)
        assertEquals(list.sortedBy { it }, res)
    }
    
    @Test
    fun testSelectionSort() {
        val list = listOf(7, 6, 1, 3, 4, 2, 5)
        val res = selectionSort(list)
        assertEquals(list.sortedBy { it }, res)
    }
    
    @Test
    fun testSelectionSortRandom() {
        val list = (0..1000).shuffled()
        val res = selectionSort(list)
        assertEquals(list.sortedBy { it }, res)
    }
    
    @Test
    fun testSwap() {
        val list = mutableListOf(1, 2, 3, 4, 5, 6, 7)
        swap(list, 0, 1)
        assertEquals(listOf(2, 1, 3, 4, 5, 6, 7), list)
    }
    
    @Test
    fun testDijkstraGraphNode() {
        val node0 = GraphNode(10)
        val node1 = GraphNode(10)
        val node2 = GraphNode(10)
        val node3 = GraphNode(10)
        val node4 = GraphNode(10)
        val node5 = GraphNode(10)
        node0.neighbors = listOf(
            Edge(node0, 1),
            Edge(node1, 10),
            Edge(node2, 10),
            Edge(node3, 10),
            Edge(node4, 20)
        )
        node1.neighbors = listOf(
            Edge(node4, 1),
            Edge(node1, 1)
        )
        node2.neighbors = listOf(
            Edge(node4, 15)
        )
        node3.neighbors = listOf(
            Edge(node4, 15)
        )
        val graph = listOf(
            node0,
            node1,
            node2,
            node3,
            node4,
            node5
        )
        assertEquals(11, dijkstraGraphNode(graph, node0, node4))
        assertEquals(-1, dijkstraGraphNode(graph, node0, node5))
        assertEquals(0, dijkstraGraphNode(graph, node0, node0))
    }
    
    @Test
    fun testDijkstraAdjList() {
        val node0 = GraphNode(10)
        val node1 = GraphNode(10)
        val node2 = GraphNode(10)
        val node3 = GraphNode(10)
        val node4 = GraphNode(10)
        val node5 = GraphNode(10)
        
        val graph = mapOf(
            node0 to listOf(
                Edge(node0, 1),
                Edge(node1, 10),
                Edge(node2, 10),
                Edge(node3, 10),
                Edge(node4, 20)
            ),
            node1 to listOf(
                Edge(node4, 1),
                Edge(node1, 1)
            ),
            node2 to listOf(
                Edge(node4, 15)
            ),
            node3 to listOf(
                Edge(node4, 15)
            ),
            node4 to listOf(),
            node5 to listOf(),
        )
        assertEquals(11, dijkstraAdjList(graph, node0, node4))
        assertEquals(-1, dijkstraAdjList(graph, node0, node5))
        assertEquals(0, dijkstraAdjList(graph, node0, node0))
    }
    
    @Test
    fun testPriorityQueue() {
        val queue = PriorityQueue<Pair<Int, Int>>(compareBy { it.first })
        val pair0 = Pair(0, 0)
        val pair1 = Pair(1, 1)
        queue.add(pair0)
        queue.add(pair1)
        queue.remove(pair0)
    }
    
    @Test
    fun testMinStack() {
        val minStack = MinStack()
        minStack.push(-2)
        minStack.push(0)
        minStack.push(-3)
        assertEquals(-3, minStack.getMin())
        minStack.pop()
        assertEquals(0, minStack.top())
        assertEquals(-2, minStack.getMin())
    }
    
    @Test
    fun testMatchingParensEmptyStr() {
        assert(Solution.validParens(""))
    }
    
    @Test
    fun testMatchingParensSuccess() {
        assert(Solution.validParens("([][])"))
    }
    
    @Test
    fun testMatchingParensFail() {
        assertFalse(Solution.validParens("(]"))
    }
    
    @Test
    fun testMatchingParensExtraItems() {
        assertFalse(Solution.validParens("(([["))
    }
    
    @Test
    fun islandsOf1Success1() {
        val ocean = listOf(
            listOf(1, 1, 1, 1, 0),
            listOf(1, 1, 0, 1, 0),
            listOf(1, 1, 0, 0, 0),
            listOf(0, 0, 0, 0, 0),
        )
        assertEquals(1, Solution.islandsOfOne(ocean))
    }
    
    @Test
    fun islandsOf1Success2() {
        val ocean = listOf(
            listOf(1, 1, 0, 0, 0),
            listOf(1, 1, 0, 0, 0),
            listOf(0, 0, 1, 0, 0),
            listOf(0, 0, 0, 1, 1),
        )
        assertEquals(3, Solution.islandsOfOne(ocean))
    }
    
    @Test
    fun islandsOf1NoIslands() {
        val ocean = listOf(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0),
        )
        assertEquals(0, Solution.islandsOfOne(ocean))
    }
    
    @Test
    fun islandsOf11Island() {
        val ocean = listOf(
            listOf(1, 1, 1, 1, 1),
            listOf(1, 1, 1, 1, 1),
            listOf(1, 1, 1, 1, 1),
            listOf(1, 1, 1, 1, 1),
        )
        assertEquals(1, Solution.islandsOfOne(ocean))
    }
    
    @Test
    fun wordLadderSuccess() {
        assertEquals(4, Solution.wordLadder("hit", "cog"))
    }
    
    @Test
    fun wordLadderBaseSuccess() {
        assert(1 == Solution.wordLadder("hit", "hit"))
    }
    
    @Test
    fun wordLadderFail() {
        assert(0 == Solution.wordLadder("hit", "asd"))
    }
    
    @Test
    fun assertAlphaLength() {
        assert(26 == Solution.alphabet.size)
    }
    
    @Test
    fun testBST() {
        val preorderFixture = listOf(
            5,
            1,
            2,
            4,
            10,
            10,
            7,
            6,
            7,
            7,
            9,
            57,
            15,
            12,
            11,
            12,
            13,
            13,
            15,
            15,
            56,
            18,
            18,
            16,
            16,
            17,
            48,
            44,
            21,
            19,
            19,
            21,
            34,
            22,
            26,
            44,
            38,
            38,
            39,
            42,
            47,
            45,
            46,
            48,
            55,
            54,
            55,
            55,
            56,
            98,
            80,
            66,
            65,
            65,
            59,
            58,
            58,
            62,
            62,
            64,
            79,
            76,
            75,
            67,
            67,
            67,
            69,
            68,
            68,
            69,
            69,
            69,
            71,
            70,
            74,
            74,
            74,
            73,
            76,
            77,
            77,
            79,
            79,
            80,
            94,
            92,
            86,
            82,
            81,
            82,
            86,
            85,
            84,
            90,
            92,
            93,
            96,
            96,
            95,
            97,
            97
        )
        val postorderFixture = listOf(
            1,
            2,
            4,
            10,
            10,
            7,
            6,
            7,
            7,
            9,
            57,
            15,
            12,
            11,
            12,
            13,
            13,
            15,
            15,
            56,
            18,
            18,
            16,
            16,
            17,
            48,
            44,
            21,
            19,
            19,
            21,
            34,
            22,
            26,
            44,
            38,
            38,
            39,
            42,
            47,
            45,
            46,
            48,
            55,
            54,
            55,
            55,
            56,
            98,
            80,
            66,
            65,
            65,
            59,
            58,
            58,
            62,
            62,
            64,
            79,
            76,
            75,
            67,
            67,
            67,
            69,
            68,
            68,
            69,
            69,
            69,
            71,
            70,
            74,
            74,
            74,
            73,
            76,
            77,
            77,
            79,
            79,
            80,
            94,
            92,
            86,
            82,
            81,
            82,
            86,
            85,
            84,
            90,
            92,
            93,
            96,
            96,
            95,
            97,
            97,
            5
        )
        val inorderFixture = listOf(
            1,
            2,
            4,
            5,
            10,
            10,
            7,
            6,
            7,
            7,
            9,
            57,
            15,
            12,
            11,
            12,
            13,
            13,
            15,
            15,
            56,
            18,
            18,
            16,
            16,
            17,
            48,
            44,
            21,
            19,
            19,
            21,
            34,
            22,
            26,
            44,
            38,
            38,
            39,
            42,
            47,
            45,
            46,
            48,
            55,
            54,
            55,
            55,
            56,
            98,
            80,
            66,
            65,
            65,
            59,
            58,
            58,
            62,
            62,
            64,
            79,
            76,
            75,
            67,
            67,
            67,
            69,
            68,
            68,
            69,
            69,
            69,
            71,
            70,
            74,
            74,
            74,
            73,
            76,
            77,
            77,
            79,
            79,
            80,
            94,
            92,
            86,
            82,
            81,
            82,
            86,
            85,
            84,
            90,
            92,
            93,
            96,
            96,
            95,
            97,
            97
        )
        val input = """
            10	57	15	98	56
            10	18	80	66	94
            48	12	79	76	77
            92	44	75	47	18
            7	65	9	67	21
            69	68	86	11	71
            90	48	34	6	79
            77	12	82	16	65
            96	76	67	86	69
            70	1	59	62	13
            74	62	55	56	79
            96	15	69	74	17
            92	44	38	19	97
            58	54	45	97	67
            74	16	55	38	58
            22	64	7	26	21
            15	19	69	85	13
            93	55	95	7	39
            73	2	80	42	81
            82	4	46	68	84
        """.trimIndent()
        val numbers = input.split(Regex("\\s+")).map { it.toInt() }
        val root = TreeNode(5)
        for (number in numbers) {
            BST.add(root, number)
        }
        assertEquals(45, BST.get(root, 45))
        assertEquals(17, BST.get(root, 17))
        assertEquals(1, BST.getMin(root))
        assertEquals(98, BST.getMax(root))
        assertEquals(57, BST.getSuccessor(root, root, 56))
        assertEquals(10, BST.getSuccessor(root, root, 8))
        assertNull(BST.getSuccessor(root, root, 98))
        assertEquals(preorderFixture, BST.preorder((root)))
        assertEquals(postorderFixture, BST.postorder((root)))
        assertEquals(inorderFixture, BST.inorder((root)))
    }
}