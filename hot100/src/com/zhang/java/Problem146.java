package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/7 9:43
 * @Author zsy
 * @Description LRU 缓存
 * 请你设计并实现一个满足 LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；
 * 如果不存在，则向缓存中插入该组key-value 。
 * 如果插入操作导致关键字数量超过capacity ，则应该 逐出 最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * 输入
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * 输出
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * 解释
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // 缓存是 {1=1}
 * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
 * lRUCache.get(1);    // 返回 1
 * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
 * lRUCache.get(2);    // 返回 -1 (未找到)
 * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
 * lRUCache.get(1);    // 返回 -1 (未找到)
 * lRUCache.get(3);    // 返回 3
 * lRUCache.get(4);    // 返回 4
 * <p>
 * 1 <= capacity <= 3000
 * 0 <= key <= 10000
 * 0 <= value <= 10^5
 * 最多调用 2 * 10^5 次 get 和 put
 */
public class Problem146 {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        // 缓存是 {1=1}
        lruCache.put(1, 1);
        // 缓存是 {1=1, 2=2}
        lruCache.put(2, 2);
        // 返回 1
        System.out.println(lruCache.get(1));
        // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lruCache.put(3, 3);
        // 返回 -1 (未找到)
        System.out.println(lruCache.get(2));
        // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lruCache.put(4, 4);
        // 返回 -1 (未找到)
        System.out.println(lruCache.get(1));
        // 返回 3
        System.out.println(lruCache.get(3));
        // 返回 4
        System.out.println(lruCache.get(4));
    }

    /**
     * 哈希表+双向链表
     * 当缓存容量满的时候，优先淘汰最近很少使用的数据，即链表尾节点
     * 1、新数据直接插入到链表头
     * 2、缓存数据被命中，则将数据放到链表头
     * 3、缓存已满，则移除链表尾数据
     */
    private static class LRUCache {
        //缓存容量
        private int capacity;

        //当前缓存容量
        private int curSize;

        //key为缓存关键字，value为数据节点
        private Map<Integer, LinkedListNode> cache;

        //链表头节点，设置链表头尾空节点，在添加节点和删除节点时，不需要进行非空判断
        private LinkedListNode head;

        //链表尾节点
        private LinkedListNode tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            curSize = 0;
            //包括头尾空节点
            cache = new HashMap<>(capacity);
            head = new LinkedListNode();
            tail = new LinkedListNode();
            head.next = tail;
            tail.pre = head;
        }

        /**
         * 如果key不在哈希表中，直接返回-1
         * 如果key在哈希表中，返回该节点的value，并将该节点放到链表的头
         *
         * @param key
         * @return
         */
        public int get(int key) {
            LinkedListNode node = cache.get(key);

            //不在缓存中
            if (node == null) {
                return -1;
            }

            //在缓存中
            //当前节点放到链表的头
            node.pre.next = node.next;
            node.next.pre = node.pre;
            node.pre = head;
            node.next = head.next;
            head.next.pre = node;
            head.next = node;
            return node.value;
        }

        /**
         * 如果key不在哈希表中且链表未满，则创建节点放到缓存中，并将该节点放到链表的头
         * 如果key不在哈希表中且链表已满，则创建节点放到缓存中，并将该节点放到链表的头，删除链表和缓存中的尾节点
         * 如果key在哈希表中，修改该节点的value，并将该节点放到链表的头
         *
         * @param key
         * @param value
         */
        public void put(int key, int value) {
            LinkedListNode node = cache.get(key);

            //在缓存中
            if (node != null) {
                //更新该节点的value
                node.value = value;
                //当前节点放到链表的头
                node.pre.next = node.next;
                node.next.pre = node.pre;
                node.pre = head;
                node.next = head.next;
                head.next.pre = node;
                head.next = node;
            } else { //不在缓存中
                node = new LinkedListNode(key, value);
                //当前节点放到缓存中
                cache.put(key, node);
                curSize++;
                //当前节点放到链表的头
                node.pre = head;
                node.next = head.next;
                head.next.pre = node;
                head.next = node;
                //如果当前缓存容量超过缓存容量，删除链表和缓存中的尾节点
                if (curSize > capacity) {
                    cache.remove(tail.pre.key);
                    tail.pre.pre.next = tail;
                    tail.pre = tail.pre.pre;
                    curSize--;
                }
            }
        }

        /**
         * 双向链表节点
         */
        private static class LinkedListNode {
            public LinkedListNode pre;
            public LinkedListNode next;
            public int key;
            public int value;

            public LinkedListNode() {
            }

            public LinkedListNode(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}
