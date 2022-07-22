package com.prakash

data class TreeNode<T>(val value: T, var left: TreeNode<T>? = null, var right: TreeNode<T>? = null)

data class ListNode<T>(var next: ListNode<T>? = null, var value: T)

data class DoubleListNode<T>(var next: DoubleListNode<T>? = null, var previous: DoubleListNode<T>? = null, val value: T)

class GraphNode<T>(val value: T, var neighbors: Collection<Edge<T>> = listOf())

data class Edge<T>(val node: GraphNode<T>, val weight: Int)