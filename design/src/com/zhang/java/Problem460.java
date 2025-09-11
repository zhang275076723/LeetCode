package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/5/8 9:13
 * @Author zsy
 * @Description LFU 缓存 字节面试题 类比Problem146、Problem432、Problem895、Problem1756、Problem1797
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
 * 1 <= capacity <= 10^4
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
     * 哈希表+分段频率双向链表+最小访问频率计数器
     * 节点哈希表存储key和节点node的映射，频率哈希表存储访问频率和当前访问频率链表的映射
     * 根据访问频率将节点放到不同的链表中，每次移除最小访问频率链表中的尾结点，即为最不经常使用的节点
     */
    private static class LFUCache {
        //key：缓存key，value：缓存节点
        private final Map<Integer, Node> nodeMap;
        //key：访问频率，value：访问频率为key的双向链表
        private final Map<Integer, LinkedList> linkedListMap;
        //节点的最小访问频率
        private int minFrequency;
        //最大缓存大小
        private final int capacity;
        //当前缓存大小
        private int curSize;

        public LFUCache(int capacity) {
            nodeMap = new HashMap<>();
            linkedListMap = new HashMap<>();
            this.minFrequency = 0;
            this.capacity = capacity;
            this.curSize = 0;
        }

        /**
         * 1、nodeMap中不存在key，返回-1
         * 2、nodeMap中存在key，当前节点从对应的频率链表中删除，并判断对应的频率链表在linkedListMap中是否需要删除，
         * 当前节点加入当前节点访问频率加1的链表的头结点，更新当前节点访问频率，更新minFrequency，返回当前节点value
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param key
         * @return
         */
        public int get(int key) {
            //nodeMap中不存在key，返回-1
            if (!nodeMap.containsKey(key)) {
                return -1;
            }

            //当前节点
            Node node = nodeMap.get(key);
            //当前节点对应的频率链表
            LinkedList curLinkedList = linkedListMap.get(node.frequency);
            //当前节点从对应的频率链表中删除
            curLinkedList.remove(node);

            //删除之后，当前节点对应的频率链表为空，则linkedListMap中删除当前节点对应的频率链表
            if (curLinkedList.head.next == curLinkedList.tail) {
                linkedListMap.remove(node.frequency);

                //更新minFrequency
                if (node.frequency == minFrequency) {
                    minFrequency++;
                }
            }

            if (!linkedListMap.containsKey(node.frequency + 1)) {
                linkedListMap.put(node.frequency + 1, new LinkedList());
            }

            //当前节点访问频率加1对应的链表
            LinkedList nextLinkedList = linkedListMap.get(node.frequency + 1);
            //当前节点加入当前节点访问频率加1的链表的头结点
            nextLinkedList.addFirst(node);
            //当前节点访问频率加1
            node.frequency++;

            return node.value;
        }

        /**
         * 1、nodeMap中不存在key，当前节点加入访问频率为1的链表的头结点和nodeMap中，curSize加1，
         * 如果缓存已满，删除nodeMap和最小访问频率链表中的尾结点，并判断是否需要删除对应的频率链表，curSize减1，
         * 更新最小访问频率为1
         * 2、nodeMap中存在key，更新当前节点value，当前节点从对应的频率链表中删除，并判断是否需要删除对应的频率链表，
         * 并判断是否需要更新minFrequency，当前节点加入当前节点访问频率加1的链表的头结点，更新当前节点的访问频率
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param key
         * @param value
         */
        public void put(int key, int value) {
            //nodeMap中不存在key
            if (!nodeMap.containsKey(key)) {
                //当前节点
                Node node = new Node(key, value, 1);
                nodeMap.put(key, node);

                if (!linkedListMap.containsKey(1)) {
                    linkedListMap.put(1, new LinkedList());
                }

                //访问频率为1的链表
                LinkedList linkedList = linkedListMap.get(1);
                linkedList.addFirst(node);
                curSize++;

                //缓存已满，删除nodeMap和最小访问频率链表中的尾结点，并判断是否需要删除对应的频率链表，curSize减1
                if (curSize > capacity) {
                    //最小访问频率链表
                    LinkedList curLinkedList = linkedListMap.get(minFrequency);
                    Node deleteNode = curLinkedList.tail.pre;
                    nodeMap.remove(deleteNode.key);
                    curLinkedList.remove(deleteNode);

                    if (curLinkedList.head.next == curLinkedList.tail) {
                        linkedListMap.remove(deleteNode.frequency);
                    }

                    curSize--;
                }

                //更新最小访问频率为1
                minFrequency = 1;

                return;
            }

            //nodeMap中存在key，更新当前节点value，当前节点从对应的频率链表中删除，并判断是否需要删除对应的频率链表，
            //并判断是否需要更新minFrequency，当前节点加入当前节点访问频率加1的链表的头结点，更新当前节点的访问频率

            Node node = nodeMap.get(key);
            node.value = value;
            LinkedList linkedList = linkedListMap.get(node.frequency);
            linkedList.remove(node);

            if (linkedList.head.next == linkedList.tail) {
                linkedListMap.remove(node.frequency);

                //更新最小访问频率
                if (node.frequency == minFrequency) {
                    minFrequency++;
                }
            }

            if (!linkedListMap.containsKey(node.frequency + 1)) {
                linkedListMap.put(node.frequency + 1, new LinkedList());
            }

            LinkedList nextLinkedList = linkedListMap.get(node.frequency + 1);
            nextLinkedList.addFirst(node);

            node.frequency++;
        }

        /**
         * 缓存双向链表
         */
        private static class LinkedList {
            //链表头结点，避免非空判断
            private final Node head;
            //链表尾结点，避免非空判断
            private final Node tail;

            public LinkedList() {
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
         * 缓存节点，即双向链表节点
         */
        private static class Node {
            public int key;
            public int value;
            //当前节点访问频率
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
    }
}
