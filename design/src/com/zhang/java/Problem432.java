package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/15 08:32
 * @Author zsy
 * @Description 全 O(1) 的数据结构 类比Problem146、Problem460、Problem895、Problem1756、Problem1797
 * 请你设计一个用于存储字符串计数的数据结构，并能够返回计数最小和最大的字符串。
 * 实现 AllOne 类：
 * AllOne() 初始化数据结构的对象。
 * inc(String key) 字符串 key 的计数增加 1 。如果数据结构中尚不存在 key ，那么插入计数为 1 的 key 。
 * dec(String key) 字符串 key 的计数减少 1 。如果 key 的计数在减少后为 0 ，那么需要将这个 key 从数据结构中删除。
 * 测试用例保证：在减少计数前，key 存在于数据结构中。
 * getMaxKey() 返回任意一个计数最大的字符串。如果没有元素存在，返回一个空字符串 "" 。
 * getMinKey() 返回任意一个计数最小的字符串。如果没有元素存在，返回一个空字符串 "" 。
 * 注意：每个函数都应当满足 O(1) 平均时间复杂度。
 * <p>
 * 输入
 * ["AllOne", "inc", "inc", "getMaxKey", "getMinKey", "inc", "getMaxKey", "getMinKey"]
 * [[], ["hello"], ["hello"], [], [], ["leet"], [], []]
 * 输出
 * [null, null, null, "hello", "hello", null, "hello", "leet"]
 * <p>
 * 解释
 * AllOne allOne = new AllOne();
 * allOne.inc("hello");
 * allOne.inc("hello");
 * allOne.getMaxKey(); // 返回 "hello"
 * allOne.getMinKey(); // 返回 "hello"
 * allOne.inc("leet");
 * allOne.getMaxKey(); // 返回 "hello"
 * allOne.getMinKey(); // 返回 "leet"
 * <p>
 * 1 <= key.length <= 10
 * key 由小写英文字母组成
 * 测试用例保证：在每次调用 dec 时，数据结构中总存在 key
 * 最多调用 inc、dec、getMaxKey 和 getMinKey 方法 5 * 10^4 次
 */
public class Problem432 {
    public static void main(String[] args) {
//        AllOne allOne = new AllOne();
        AllOne2 allOne = new AllOne2();
        allOne.inc("hello");
        allOne.inc("hello");
        // 返回 "hello"
        System.out.println(allOne.getMaxKey());
        // 返回 "hello"
        System.out.println(allOne.getMinKey());
        allOne.inc("leet");
        // 返回 "hello"
        System.out.println(allOne.getMaxKey());
        // 返回 "leet"
        System.out.println(allOne.getMinKey());
    }

    /**
     * 哈希表+双向链表 (lru)
     */
    static class AllOne {
        //key：字符串，value：key所在的node节点
        private final Map<String, Node> nodeMap;
        //计数次数相同的字符串构成的节点，计数次数由小到大的双向链表
        private final LinkedList linkedList;

        public AllOne() {
            nodeMap = new HashMap<>();
            linkedList = new LinkedList();
        }

        public void inc(String key) {
            //nodeMap中不存在key
            if (!nodeMap.containsKey(key)) {
                //双向链表中不存在计数次数为1的节点
                if (linkedList.head.next.count != 1) {
                    Node node = new Node(1);
                    linkedList.addFirst(node);
                }

                Node node = linkedList.head.next;
                node.set.add(key);
                nodeMap.put(key, node);

                return;
            }

            //当前节点
            Node node = nodeMap.get(key);
            node.set.remove(key);

            //当前节点的下一个节点的计数次数不等于当前节点计数次数加1
            if (node.next.count != node.count + 1) {
                Node nextNode = new Node(node.count + 1);
                linkedList.addAfter(nextNode, node);
            }

            Node nextNode = node.next;
            nextNode.set.add(key);
            nodeMap.put(key, nextNode);

            //当前节点中没有字符串，则链表中删除当前节点
            if (node.set.isEmpty()) {
                linkedList.remove(node);
            }
        }

        public void dec(String key) {
            //nodeMap中不存在key，直接返回
            if (!nodeMap.containsKey(key)) {
                return;
            }

            //当前节点
            Node node = nodeMap.get(key);
            node.set.remove(key);

            //当前节点的计数次数为1，减1之后为0，则删除当前节点
            if (node.count == 1) {
                nodeMap.remove(key);

                //当前节点中没有字符串，则链表中删除当前节点
                if (node.set.isEmpty()) {
                    linkedList.remove(node);
                }
            } else {
                //当前节点的前驱节点的计数次数不等于当前节点计数次数减1
                if (node.pre.count != node.count - 1) {
                    Node preNode = new Node(node.count - 1);
                    linkedList.addBefore(preNode, node);
                }

                Node preNode = node.pre;
                preNode.set.add(key);
                nodeMap.put(key, preNode);

                //当前节点中没有字符串，则链表中删除当前节点
                if (node.set.isEmpty()) {
                    linkedList.remove(node);
                }
            }
        }

        public String getMaxKey() {
            //链表为空，则没有元素存在，返回""
            if (linkedList.head.next == linkedList.tail) {
                return "";
            }

            return linkedList.tail.pre.set.iterator().next();
        }

        public String getMinKey() {
            //链表为空，则没有元素存在，返回""
            if (linkedList.head.next == linkedList.tail) {
                return "";
            }

            return linkedList.head.next.set.iterator().next();
        }

        /**
         * 计数次数相同的字符串构成的节点，计数次数由小到大的双向链表
         */
        private static class LinkedList {
            private final Node head;
            private final Node tail;

            public LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
            }

            public void addFirst(Node node) {
                Node nextNode = head.next;
                head.next = node;
                node.pre = head;
                node.next = nextNode;
                nextNode.pre = node;
            }

            /**
             * node加入baseNode的前驱节点
             *
             * @param node
             * @param baseNode
             */
            public void addBefore(Node node, Node baseNode) {
                Node preNode = baseNode.pre;
                preNode.next = node;
                node.pre = preNode;
                node.next = baseNode;
                baseNode.pre = node;
            }

            /**
             * node加入baseNode的后继节点
             *
             * @param node
             * @param baseNode
             */
            public void addAfter(Node node, Node baseNode) {
                Node nextNode = baseNode.next;
                baseNode.next = node;
                node.pre = baseNode;
                node.next = nextNode;
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
         * 计数次数相同的字符串构成的节点，即双向链表节点
         */
        private static class Node {
            private final Set<String> set;
            //当前节点计数次数
            private int count;
            private Node pre;
            private Node next;

            public Node() {
                set = new HashSet<>();
            }

            public Node(int count) {
                set = new HashSet<>();
                this.count = count;
            }
        }
    }

    /**
     * 哈希表+计数次数相同双向链表+分段计数次数双向链表 (lfu)
     * 链表1：计数次数相同的字符串构成的双向链表
     * 链表2：链表1作为链表2的节点，计数次数由小到大构成的双向链表
     */
    static class AllOne2 {
        //key：字符串，value：key所在的node节点
        private final Map<String, Node> nodeMap;
        //key：计数次数，value：计数次数为key的所有node节点构成的双向链表作为的countNode节点
        private final Map<Integer, CountNode> countNodeMap;
        //计数次数相同的字符串构成的双向链表作为节点，计数次数由小到大构成的双向链表
        private final CountLinkedList countLinkedList;

        public AllOne2() {
            nodeMap = new HashMap<>();
            countNodeMap = new HashMap<>();
            countLinkedList = new CountLinkedList();
        }

        public void inc(String key) {
            //nodeMap中不存在key
            if (!nodeMap.containsKey(key)) {
                Node node = new Node(key, 1);
                nodeMap.put(key, node);

                //node双向链表作为节点的双向链表中不存在计数次数为1的countNode节点
                if (!countNodeMap.containsKey(1)) {
                    CountNode countNode = new CountNode();
                    countNodeMap.put(1, countNode);
                    countLinkedList.addFirst(countNode);
                }

                CountNode countNode = countNodeMap.get(1);
                countNode.linkedList.addFirst(node);

                return;
            }

            Node node = nodeMap.get(key);
            CountNode countNode = countNodeMap.get(node.count);

            node.count++;
            countNode.linkedList.remove(node);

            //countNodeMap中不存在已经加1之后的node计数次数
            if (!countNodeMap.containsKey(node.count)) {
                CountNode nextCountNode = new CountNode();
                countNodeMap.put(node.count, nextCountNode);
                countLinkedList.addAfter(nextCountNode, countNode);
            }

            CountNode nextCountNode = countNodeMap.get(node.count);
            nextCountNode.linkedList.addFirst(node);

            //当前countNode节点中的双向链表为空，则删除当前countNode节点
            if (countNode.linkedList.head.next == countNode.linkedList.tail) {
                countLinkedList.remove(countNode);
                countNodeMap.remove(node.count - 1);
            }
        }

        public void dec(String key) {
            //nodeMap中不存在key，直接返回
            if (!nodeMap.containsKey(key)) {
                return;
            }

            Node node = nodeMap.get(key);
            CountNode countNode = countNodeMap.get(node.count);

            countNode.linkedList.remove(node);
            node.count--;

            //当前节点已经减1之后的计数次数为0，则删除当前节点
            if (node.count == 0) {
                nodeMap.remove(key);

                //当前countNode节点中的双向链表为空，则删除当前countNode节点
                if (countNode.linkedList.head.next == countNode.linkedList.tail) {
                    countLinkedList.remove(countNode);
                    countNodeMap.remove(node.count + 1);
                }
            } else {
                //countNodeMap中不存在已经减1之后的node计数次数
                if (!countNodeMap.containsKey(node.count)) {
                    CountNode preCountNode = new CountNode();
                    countNodeMap.put(node.count, preCountNode);
                    countLinkedList.addBefore(preCountNode, countNode);
                }

                CountNode preCountNode = countNodeMap.get(node.count);
                preCountNode.linkedList.addFirst(node);

                //当前countNode节点中的双向链表为空，则删除当前countNode节点
                if (countNode.linkedList.head.next == countNode.linkedList.tail) {
                    countLinkedList.remove(countNode);
                    countNodeMap.remove(node.count + 1);
                }
            }
        }

        public String getMaxKey() {
            //链表为空，则没有元素存在，返回""
            if (countLinkedList.head.next == countLinkedList.tail) {
                return "";
            }

            CountNode countNode = countLinkedList.tail.pre;

            return countNode.linkedList.head.next.key;
        }

        public String getMinKey() {
            //链表为空，则没有元素存在，返回""
            if (countLinkedList.head.next == countLinkedList.tail) {
                return "";
            }

            CountNode countNode = countLinkedList.head.next;

            return countNode.linkedList.head.next.key;
        }

        private static class CountLinkedList {
            private final CountNode head;
            private final CountNode tail;

            public CountLinkedList() {
                head = new CountNode();
                tail = new CountNode();
                head.next = tail;
                tail.pre = head;
            }

            public void addFirst(CountNode countNode) {
                CountNode nextCountNode = head.next;
                head.next = countNode;
                countNode.pre = head;
                countNode.next = nextCountNode;
                nextCountNode.pre = countNode;
            }

            /**
             * countNode加入baseCountNode的前驱节点
             *
             * @param countNode
             * @param baseCountNode
             */
            public void addBefore(CountNode countNode, CountNode baseCountNode) {
                CountNode preCountNode = baseCountNode.pre;
                preCountNode.next = countNode;
                countNode.pre = preCountNode;
                countNode.next = baseCountNode;
                baseCountNode.pre = countNode;
            }

            /**
             * countNode加入baseCountNode的后继节点
             *
             * @param countNode
             * @param baseCountNode
             */
            public void addAfter(CountNode countNode, CountNode baseCountNode) {
                CountNode nextCountNode = baseCountNode.next;
                baseCountNode.next = countNode;
                countNode.pre = baseCountNode;
                countNode.next = nextCountNode;
                nextCountNode.pre = countNode;
            }

            public void remove(CountNode countNode) {
                CountNode preCountNode = countNode.pre;
                CountNode nextCountNode = countNode.next;
                preCountNode.next = nextCountNode;
                nextCountNode.pre = preCountNode;
                countNode.pre = null;
                countNode.next = null;
            }
        }

        private static class CountNode {
            private final LinkedList linkedList;
            private CountNode pre;
            private CountNode next;

            public CountNode() {
                linkedList = new LinkedList();
            }
        }

        private static class LinkedList {
            private final Node head;
            private final Node tail;

            public LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
            }

            public void addFirst(Node node) {
                Node nextNode = head.next;
                head.next = node;
                node.pre = head;
                node.next = nextNode;
                nextNode.pre = node;
            }

            public void remove(Node node) {
                Node preNode = node.pre;
                Node nextNode = node.next;
                preNode.next = nextNode;
                nextNode.pre = preNode;
                node.pre = null;
                node.next = null;
            }
        }

        private static class Node {
            private String key;
            private int count;
            private Node pre;
            private Node next;

            public Node() {

            }

            public Node(String key, int count) {
                this.key = key;
                this.count = count;
            }
        }
    }
}
