package com.prakash

class MyHashMap<K, V> {
    companion object {
        const val LOAD_FACTOR_THRESHOLD = 0.6
    }
    
    var size = 0
        private set
    private var buckets = listOf(mutableListOf(), mutableListOf<Pair<K, V>>())
    
    fun put(key: K, value: V) {
        put(key, value, buckets)
        
        if (loadFactor() > LOAD_FACTOR_THRESHOLD) {
            resize()
        }
    }
    
    private fun put(key: K, value: V, methodBuckets: List<MutableList<Pair<K, V>>>) {
        val idx = computeIdx(key, methodBuckets)
        val bucket = methodBuckets[idx]
        val removed = bucket.removeAll { it.first == key }
        bucket.add(Pair(key, value))
        if (!removed) {
            size++
        }
    }
    
    fun remove(key: K) {
        val idx = computeIdx(key, buckets)
        val bucket = buckets[idx]
        val existingItem = bucket.singleOrNull { it.first == key }
        if (null != existingItem) {
            bucket.remove(existingItem)
            size--
        }
    }
    
    fun getValue(key: K): V? {
        val idx = computeIdx(key, buckets)
        return buckets[idx].singleOrNull { it.first == key }?.second
    }
    
    private fun computeIdx(key: K, methodBuckets: List<MutableList<Pair<K, V>>>): Int {
        return key.hashCode() % methodBuckets.size
    }
    
    fun loadFactor(): Double {
        return size / buckets.size.toDouble()
    }
    
    private fun resize() {
        val newBuckets = List(buckets.size * 2) { mutableListOf<Pair<K, V>>() }
        size = 0
        
        for (bucket in buckets) {
            for (item in bucket) {
                put(item.first, item.second, newBuckets)
            }
        }
        
        buckets = newBuckets
    }
}