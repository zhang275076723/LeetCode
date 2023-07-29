package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/7 9:43
 * @Author zsy
 * @Description LRU 缓存 百度面试题 字节面试题 虾皮面试题 类比Problem460
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
     * 哈希表存储key和缓存节点node的映射
     * 当缓存容量满的时候，优先淘汰最近最少使用的数据，即链表尾节点
     * 1、新数据直接插入到链表头和缓存中
     * 2、缓存数据被命中，则将数据放到链表头并修改缓存中数据
     * 3、缓存已满，则移除链表尾和缓存中数据
     */
    private static class LRUCache {
        //缓存容量
        private final int capacity;

        //当前缓存容量
        private int curSize;

        //缓存map，在O(1)找到当前缓存节点，key：缓存关键字，value：数据节点
        private final Map<Integer, Node> cache;

        //双向链表
        private final LinkedList linkedList;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            curSize = 0;
            cache = new HashMap<>(capacity);
            linkedList = new LinkedList();
        }

        /**
         * 1、如果key不在缓存map中，直接返回-1
         * 2、如果key在缓存map中，将该节点放到链表的头，并返回该节点的value
         *
         * @param key
         * @return
         */
        public int get(int key) {
            //当前节点不在缓存map中，返回-1
            if (!cache.containsKey(key)) {
                return -1;
            }

            Node node = cache.get(key);
            //node从链表尾移除，加入链表头，作为最新访问节点
            linkedList.remove(node);
            linkedList.addFirst(node);
            return node.value;
        }

        /**
         * 1、如果key不在哈希表中且链表未满，创建节点放到缓存map和链表的头
         * 2、如果key不在哈希表中且链表已满，删除缓存map和链表中的尾节点，创建节点放到缓存map和链表的头
         * 3、如果key在缓存map中，修改该节点的value，并将该节点放到链表的头
         *
         * @param key
         * @param value
         */
        public void put(int key, int value) {
            //当前节点在缓存map中
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                //更新当前节点的value
                node.value = value;
                //node从链表尾移除，加入链表头，作为最新访问节点
                linkedList.remove(node);
                linkedList.addFirst(node);
            } else {
                //当前节点不在缓存map中
                //要加入cache和linkedList中的节点
                Node node = new Node(key, value);

                //缓存map已满，链表和缓存map中移除末尾节点，当前节点放到链表的头和缓存中，作为最新访问节点
                if (curSize == capacity) {
                    //末尾节点，即最近最久未访问的节点
                    Node deleteNode = linkedList.tail.pre;
                    //deleteNode从链表中移除
                    linkedList.remove(deleteNode);
                    //deleteNode从cache中移除
                    cache.remove(deleteNode.key);
                    //node加入链表头
                    linkedList.addFirst(node);
                    //node加入cache
                    cache.put(key, node);
                } else {
                    //缓存map未满，当前节点放到链表的头和缓存中，作为最新访问节点
                    linkedList.addFirst(node);
                    cache.put(key, node);
                    curSize++;
                }
            }
        }

        /**
         * 双向链表
         */
        private static class LinkedList {
            //链表头结点，避免非空判断
            public Node head;
            //链表尾结点，避免非空判断
            public Node tail;

            LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
            }

            public void addFirst(Node node) {
                Node nextNode = head.next;
                node.pre = head;
                node.next = nextNode;
                head.next = node;
                nextNode.pre = node;
            }

            public void remove(Node node) {
                //当前节点的前驱节点
                Node preNode = node.pre;
                //当前节点的后继节点
                Node nextNode = node.next;
                preNode.next = nextNode;
                nextNode.pre = preNode;
                node.pre = null;
                node.next = null;
            }
        }

        /**
         * 双向链表节点，也是cache中存放的节点
         */
        private static class Node {
            public int key;
            public int value;
            public Node pre;
            public Node next;

            public Node() {
            }

            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }
}
