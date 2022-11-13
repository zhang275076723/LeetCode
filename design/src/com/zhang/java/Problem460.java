package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/8 9:13
 * @Author zsy
 * @Description LFU 缓存 字节面试题 类比Problem146、Problem659
 * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
 * 实现 LFUCache 类：
 * LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
 * int get(int key) - 如果键 key 存在于缓存中，则获取键的值，否则返回 -1 。
 * void put(int key, int value) - 如果键 key 已存在，则变更其值；如果键不存在，请插入键值对。
 * 当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用的项。
 * 在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。
 * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
 * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。
 * 对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * 输入：
 * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
 * 输出：
 * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
 * 解释：
 * // cnt(x) = 键 x 的使用计数
 * // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
 * LFUCache lfu = new LFUCache(2);
 * lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
 * lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
 * lfu.get(1);      // 返回 1 // cache=[1,2], cnt(2)=1, cnt(1)=2
 * lfu.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小 // cache=[3,1], cnt(3)=1, cnt(1)=2
 * lfu.get(2);      // 返回 -1（未找到）
 * lfu.get(3);      // 返回 3 // cache=[3,1], cnt(3)=2, cnt(1)=2
 * lfu.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用 // cache=[4,3], cnt(4)=1, cnt(3)=2
 * lfu.get(1);      // 返回 -1（未找到）
 * lfu.get(3);      // 返回 3 // cache=[3,4], cnt(4)=1, cnt(3)=3
 * lfu.get(4);      // 返回 4 // cache=[3,4], cnt(4)=2, cnt(3)=3
 * <p>
 * 0 <= capacity <= 10^4
 * 0 <= key <= 10^5
 * 0 <= value <= 10^9
 * 最多调用 2 * 10^5 次 get 和 put 方法
 */
public class Problem460 {
    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(2);
        // cache=[1,_], cnt(1)=1
        lfuCache.put(1, 1);
        // cache=[2,1], cnt(2)=1, cnt(1)=1
        lfuCache.put(2, 2);
        // 返回 1 // cache=[1,2], cnt(2)=1, cnt(1)=2
        System.out.println(lfuCache.get(1));
        // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小 // cache=[3,1], cnt(3)=1, cnt(1)=2
        lfuCache.put(3, 3);
        // 返回 -1（未找到）
        System.out.println(lfuCache.get(2));
        // 返回 3 // cache=[3,1], cnt(3)=2, cnt(1)=2
        System.out.println(lfuCache.get(3));
        // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用 // cache=[4,3], cnt(4)=1, cnt(3)=2
        lfuCache.put(4, 4);
        // 返回 -1（未找到）
        System.out.println(lfuCache.get(1));
        // 返回 3 // cache=[3,4], cnt(4)=1, cnt(3)=3
        System.out.println(lfuCache.get(3));
        // 返回 4 // cache=[3,4], cnt(4)=2, cnt(3)=3
        System.out.println(lfuCache.get(4));
    }

    /**
     * 双哈希表+双向链表+使用频率计数器
     * 一个哈希表存储key和缓存节点node的映射
     * 另一个哈希表存储使用频率和当前使用频率链表
     */
    private static class LFUCache {
        //缓存容量
        private final int capacity;

        //当前缓存容量
        private int curSize;

        //当前缓存最少的使用频率
        private int minFrequency;

        //缓存map，在O(1)找到当前缓存节点，key为缓存关键字，value为数据节点
        private final Map<Integer, Node> cache;

        //缓存使用频率map，在O(1)找到当前节点使用频率链表，key为缓存使用频率，value为使用频率相等的双向链表
        private final Map<Integer, LinkedList> frequencyCache;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.curSize = 0;
            this.minFrequency = 0;
            cache = new HashMap<>(capacity);
            frequencyCache = new HashMap<>(capacity);
        }

        /**
         * 1、如果key不在cache中，直接返回-1
         * 2、如果key在cache，将当前节点从frequencyCache对应的链表中删除，并添加到当前节点frequency+1对应的链表的头，
         * 注意更新最少使用的频率，返回当前节点的value
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
            LinkedList linkedList = frequencyCache.get(node.frequency);
            //当前节点从当前频率链表中删除
            linkedList.delete(node);

            //当前频率链表为空，从frequencyCache中移除，更新minFrequency
            if (linkedList.head.next == linkedList.tail) {
                frequencyCache.remove(node.frequency);
                if (minFrequency == node.frequency) {
                    minFrequency++;
                }
            }

            node.frequency++;
            LinkedList newLinkedList = frequencyCache.getOrDefault(node.frequency, new LinkedList());
            //当前节点放到新频率链表的头
            newLinkedList.addFirst(node);
            frequencyCache.put(node.frequency, newLinkedList);

            return node.value;
        }

        /**
         * 1、如果key在cache中，将当前节点从frequencyCache对应的链表中删除，添加到当前节点frequency+1对应的链表头
         * 修改该节点的value和frequency，要注意链表删除和添加是否为空的情况
         * 2、如果key不在cache中，如果缓存已满，删除frequencyCache中minFrequency链表中尾结点的前一个节点，
         * 删除cache中的当前节点，添加当前节点到使用频率为1链表的头结点和chache中
         *
         * @param key
         * @param value
         */
        public void put(int key, int value) {
            //容量为空，直接返回
            if (capacity == 0) {
                return;
            }

            //当前节点在缓存map中
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.value = value;
                LinkedList linkedList = frequencyCache.get(node.frequency);
                //当前节点从当前频率链表中删除
                linkedList.delete(node);

                //当前频率链表为空，从frequencyCache中移除，更新minFrequency
                if (linkedList.head.next == linkedList.tail) {
                    frequencyCache.remove(node.frequency);
                    if (minFrequency == node.frequency) {
                        minFrequency++;
                    }
                }

                node.frequency++;
                LinkedList newLinkedList = frequencyCache.getOrDefault(node.frequency, new LinkedList());
                //当前节点放到新频率链表的头
                newLinkedList.addFirst(node);
                frequencyCache.put(node.frequency, newLinkedList);
            } else {
                //当前节点不在缓存map中

                //缓存map已满，最小频率链表和缓存map中移除末尾节点，当前节点插入频率为1的链表中，更新缓存map和频率链表map
                if (curSize == capacity) {
                    LinkedList minLinkedList = frequencyCache.get(minFrequency);
                    //要删除的节点
                    Node deleteNode = minLinkedList.tail.pre;
                    cache.remove(deleteNode.key);
                    minLinkedList.delete(deleteNode);
                    if (minLinkedList.head.next == minLinkedList.tail) {
                        frequencyCache.remove(minFrequency);
                    }
                    minFrequency = 1;

                    //当前要插入的节点
                    Node node = new Node(key, value, 1);
                    cache.put(key, node);
                    LinkedList newLinkedList = frequencyCache.getOrDefault(minFrequency, new LinkedList());
                    newLinkedList.addFirst(node);
                    frequencyCache.put(minFrequency, newLinkedList);
                } else {
                    //缓存map未满，当前节点放入频率为1的链表的头和缓存map中，更新频率链表map

                    Node node = new Node(key, value, 1);
                    minFrequency = 1;
                    curSize++;
                    cache.put(key, node);
                    LinkedList linkedList = frequencyCache.getOrDefault(minFrequency, new LinkedList());
                    linkedList.addFirst(node);
                    frequencyCache.put(minFrequency, linkedList);
                }
            }
        }

        /**
         * 数据节点
         */
        private static class Node {
            public int key;
            public int value;
            public int frequency;
            public Node pre;
            public Node next;

            public Node() {
            }

            public Node(int key, int value, int frequency) {
                this.key = key;
                this.value = value;
                this.frequency = frequency;
            }
        }

        /**
         * 使用频率相等的数据节点链表
         */
        private static class LinkedList {
            //头结点，避免非空判断
            public Node head;
            //尾结点，避免非空判断
            public Node tail;

            LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
            }

            public void addFirst(Node node) {
                //头结点的下一个节点
                Node next = head.next;
                head.next = node;
                next.pre = node;
                node.pre = head;
                node.next = next;
            }

            public void delete(Node node) {
                Node pre = node.pre;
                Node next = node.next;
                pre.next = next;
                next.pre = pre;
                node.pre = null;
                node.next = null;
            }
        }
    }
}
