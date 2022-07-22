package com.prakash

import java.io.File

data class WordRecord(val word: String, val depth: Int)

object Solution {
    val words = File("words_alpha.txt").readLines().toSet()
    val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
    val bracketsDef = mapOf(
        ']' to '[',
        '}' to '{',
        ')' to '(',
    )
    val openingChars = bracketsDef.values
    val closingChars = bracketsDef.keys
    
    // ([][])
    fun validParens(str: String): Boolean {
        val stack = mutableListOf<Char>()
        for (char in str.toCharArray()) {
            if (char in openingChars) {
                stack.add(0, char)
            } else {
                val openingChar = stack.removeFirst()
                if (bracketsDef[char] != openingChar) {
                    return false
                }
            }
        }
        
        return stack.isEmpty()
    }
    
    fun islandsOfOne(ocean: List<List<Int>>): Int {
        var islandCount = 0
        val visited = mutableSetOf<Pair<Int, Int>>()
        
        for (i in ocean.indices) {
            for (j in ocean[i].indices) {
                if (ocean[i][j] == 0 || Pair(i, j) in visited) {
                    continue
                }
                
                islandCount++
                visited.addAll(exploreIsland(ocean, i, j))
            }
        }
        
        return islandCount
    }
    
    private fun exploreIsland(ocean: List<List<Int>>, x: Int, y: Int): Set<Pair<Int, Int>> {
        val island = mutableSetOf(Pair(x, y))
        val toVisit = mutableSetOf(Pair(x, y))
        
        while (toVisit.isNotEmpty()) {
            val point = toVisit.first()
            toVisit.remove(point)
            
            val currentX = point.first
            val currentY = point.second
            
            if (currentX + 1 in ocean.indices && ocean[currentX + 1][currentY] == 1) {
                val newPair = Pair(currentX + 1, currentY)
                if (newPair !in island) {
                    toVisit.add(newPair)
                }
                island.add(newPair)
            }
            if (currentX - 1 in ocean.indices && ocean[currentX - 1][currentY] == 1) {
                val newPair = Pair(currentX - 1, currentY)
                if (newPair !in island) {
                    toVisit.add(newPair)
                }
                island.add(newPair)
            }
            
            if (currentY + 1 in ocean[currentX].indices && ocean[currentX][currentY + 1] == 1) {
                val newPair = Pair(currentX, currentY + 1)
                if (newPair !in island) {
                    toVisit.add(newPair)
                }
                island.add(newPair)
            }
            
            if (currentY - 1 in ocean[currentX].indices && ocean[currentX][currentY - 1] == 1) {
                val newPair = Pair(currentX, currentY - 1)
                if (newPair !in island) {
                    toVisit.add(newPair)
                }
                island.add(newPair)
            }
        }
        
        return island
    }
    
    fun wordLadder(firstWord: String, endWord: String): Int {
        val visited = mutableSetOf<String>(firstWord)
        val toVisitList = mutableListOf<WordRecord>()
        val toVisitSet = mutableSetOf<String>()
        
        toVisitList.add(WordRecord(firstWord, 1))
        
        while (toVisitList.isNotEmpty()) {
            val currentWordRecord = toVisitList.removeFirst()
            val currentWord = currentWordRecord.word
            val currentDepth = currentWordRecord.depth
            
            if (endWord == currentWord) {
                return currentDepth
            }
            
            visited.add(currentWord)
            
            for (i in currentWord.indices) {
                for (letter in alphabet) {
                    val currentWordAsCharArray = currentWord.toCharArray()
                    currentWordAsCharArray[i] = letter
                    val mutatedWord = String(currentWordAsCharArray)
                    if (mutatedWord !in words) { // If the mutated word isn't a word, skip
                        continue
                    }
                    if (mutatedWord in visited || mutatedWord in toVisitSet) { // If we've already visited or are going to visit, skip
                        continue
                    }
                    toVisitList.add(WordRecord(mutatedWord, currentDepth + 1))
                    toVisitSet.add(mutatedWord)
                }
            }
        }
        
        return 0
    }
    
    fun <T> dijkstraGraphNode(graph: Collection<GraphNode<T>>, source: GraphNode<T>, target: GraphNode<T>): Int {
        val queue: MutableMap<GraphNode<T>, Int> = mutableMapOf()
        for (node in graph) {
            queue[node] = Int.MAX_VALUE
        }
        queue[source] = 0
        while (queue.isNotEmpty()) {
            val entry = removeMinItem(queue)
            val node = entry.key
            val distance = entry.value
            if (entry.value == Int.MAX_VALUE) {
                return -1
            }
            if (target == node) {
                return distance
            }
            
            for (edge in node.neighbors) {
                queue[edge.node]?.let {
                    queue[edge.node] = it.coerceAtMost(distance + edge.weight)
                }
            }
        }
        return -1
    }
    
    private fun <T> getMinItem(graph: MutableMap<T, Int>): Map.Entry<T, Int> {
        return graph.minBy { it.value }
    }
    
    fun <T> dijkstraAdjList(graph: Map<GraphNode<T>, List<Edge<T>>>, source: GraphNode<T>, target: GraphNode<T>): Int {
        val queue: MutableMap<GraphNode<T>, Int> = mutableMapOf()
        for ((node, _) in graph) {
            queue[node] = Int.MAX_VALUE
        }
        queue[source] = 0
        while (queue.isNotEmpty()) {
            val entry = removeMinItem(queue)
            val node = entry.key
            val distance = entry.value
            if (entry.value == Int.MAX_VALUE) {
                return -1
            }
            if (target == node) {
                return distance
            }
            
            for (edge in graph.getValue(node)) {
                queue[edge.node]?.let {
                    queue[edge.node] = it.coerceAtMost(distance + edge.weight)
                }
            }
        }
        return -1
    }
    
    private fun <T> removeMinItem(graph: MutableMap<T, Int>): Map.Entry<T, Int> {
        val node = getMinItem(graph)
        graph.remove(node.key)
        return node
    }
    
    fun <T : Comparable<T>> selectionSort(list: List<T>): List<T> {
        val rtn = list.toMutableList()
        for (i in list.indices) {
            val minIdx = findMinIdx(rtn, i, list.size)
            swap(rtn, minIdx, i)
        }
        return rtn.toList()
    }
    
    private fun <T : Comparable<T>> findMinIdx(list: List<T>, startIdx: Int, endIdx: Int): Int {
        var minIdx = startIdx
        
        for (i in startIdx until endIdx) {
            if (list[i] < list[minIdx]) {
                minIdx = i
            }
        }
        
        return minIdx
    }
    
    fun <T : Comparable<T>> mergeSort(list: List<T>): List<T> {
        val mutableList = list.toMutableList()
        mergeSortInternal(mutableList, 0, list.size)
        return mutableList.toList()
    }
    
    private fun <T : Comparable<T>> mergeSortInternal(list: MutableList<T>, startIdx: Int, endIdx: Int) {
        if (endIdx - startIdx <= 2) {
            if (list[startIdx] > list[endIdx - 1]) {
                swap(list, startIdx, endIdx - 1)
            }
            return
        }
        
        val midIdx = startIdx + ((endIdx - startIdx) / 2)
        mergeSortInternal(list, startIdx, midIdx)
        mergeSortInternal(list, midIdx, endIdx)
        var leftIdx = startIdx
        var rightIdx = midIdx
        val sorted = MutableList((endIdx - startIdx)) { list[0] }
        for (ptrIdx in 0 until sorted.size) {
            if (rightIdx >= endIdx) {
                sorted[ptrIdx] = list[leftIdx]
                leftIdx++
            } else if (leftIdx >= midIdx) {
                sorted[ptrIdx] = list[rightIdx]
                rightIdx++
            } else if (list[leftIdx] < list[rightIdx]) {
                sorted[ptrIdx] = list[leftIdx]
                leftIdx++
            } else {
                sorted[ptrIdx] = list[rightIdx]
                rightIdx++
            }
        }
        for (ptrIdx in 0 until sorted.size) {
            list[startIdx + ptrIdx] = sorted[ptrIdx]
        }
    }
    
    fun <T> swap(list: MutableList<T>, aIdx: Int, bIdx: Int) {
        val temp = list[aIdx]
        list[aIdx] = list[bIdx]
        list[bIdx] = temp
    }
}

object BST {
    fun <T : Comparable<T>> add(root: TreeNode<T>, value: T) {
        if (value <= root.value) {
            if (null == root.left) {
                root.left = TreeNode(value = value)
            } else {
                add(root.left!!, value)
            }
        } else {
            if (null == root.right) {
                root.right = TreeNode(value = value)
            } else {
                add(root.right!!, value)
            }
        }
    }
    
    fun <T : Comparable<T>> get(root: TreeNode<T>, value: T): T? {
        if (root.value == value) {
            return root.value
        }
        return if (value <= root.value) {
            if (null == root.left) {
                null
            } else {
                get(root.left!!, value)
            }
        } else {
            if (null == root.right) {
                null
            } else {
                get(root.right!!, value)
            }
        }
    }
    
    fun <T : Comparable<T>> getMin(root: TreeNode<T>): T? {
        if (null == root.left) {
            return root.value
        }
        return getMin(root.left!!)
    }
    
    fun <T : Comparable<T>> getMax(root: TreeNode<T>): T? {
        if (null == root.right) {
            return root.value
        }
        return getMax(root.right!!)
    }
    
    fun <T : Comparable<T>> getSuccessor(root: TreeNode<T>, succ: TreeNode<T>, value: T): T? {
        var rtn = succ.value
        if (root.value == value) {
            if (root.right != null) {
                return getMin(root.right!!)
            }
            if (succ.value > root.value) {
                return succ.value
            }
            return null
        }
        
        if (value <= root.value) {
            if (root.left != null) {
                rtn = root.value
                return getSuccessor(root.left!!, root, value)
            }
            return succ.value
        } else if (value > root.value) {
            if (root.right != null) {
                return getSuccessor(root.right!!, succ, value)
            }
            return rtn
        }
        return rtn
    }
    
    fun <T : Comparable<T>> remove(root: TreeNode<T>, value: T): T? {
        TODO()
    }
    
    fun <T : Comparable<T>> preorder(root: TreeNode<T>?): List<T> {
        if (null == root) {
            return emptyList()
        }
        return listOf(root.value) + preorder(root.left) + preorder(root.right)
    }
    
    fun <T : Comparable<T>> inorder(root: TreeNode<T>?): List<T> {
        if (null == root) {
            return emptyList()
        }
        return preorder(root.left) + listOf(root.value) + preorder(root.right)
    }
    
    fun <T : Comparable<T>> postorder(root: TreeNode<T>?): List<T> {
        if (null == root) {
            return emptyList()
        }
        return preorder(root.left) + preorder(root.right) + listOf(root.value)
    }
}