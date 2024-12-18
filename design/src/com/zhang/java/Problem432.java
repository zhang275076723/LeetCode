package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/15 08:32
 * @Author zsy
 * @Description 全 O(1) 的数据结构 类比Problem146、Problem460
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
        AllOne allOne = new AllOne();
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
     * 哈希表+双向链表 (lfu)
     */
    static class AllOne {
        //key：字符串，value：字符串所在字符串集合节点
        private final Map<String, Node> strMap;
        //计数次数相同的字符串集合双向链表
        private final LinkedList linkedList;

        public AllOne() {
            strMap = new HashMap<>();
            linkedList = new LinkedList();
        }

        public void inc(String key) {
            //strMap中不存在key
            if (!strMap.containsKey(key)) {
                //双向链表中存在计数次数为1的节点
                if (linkedList.head.next.frequency == 1) {
                    linkedList.head.next.set.add(key);
                    strMap.put(key, linkedList.head.next);
                } else {
                    //双向链表中不存在计数次数为1的节点
                    Node addNode = new Node(key, 1);
                    linkedList.addAfterNode(linkedList.head, addNode);
                    strMap.put(key, addNode);
                }
            } else {
                //strMap中存在key

                //当前节点
                Node node = strMap.get(key);
                node.set.remove(key);

                //当前节点下一个节点的计数次数不等于当前节点计数次数加1
                if (node.next.frequency != node.frequency + 1) {
                    //当前节点计数次数加1的节点
                    Node addNode = new Node(key, node.frequency + 1);
                    linkedList.addAfterNode(node, addNode);
                    strMap.put(key, addNode);
                } else {
                    //当前节点下一个节点的计数次数等于当前节点计数次数加1
                    node.next.set.add(key);
                    strMap.put(key, node.next);
                }

                //删除之后，当前节点集合为空，则当前节点从双向链表中删除
                if (node.set.isEmpty()) {
                    linkedList.remove(node);
                }
            }
        }

        public void dec(String key) {
            //strMap中不存在key，直接返回
            if (!strMap.containsKey(key)) {
                return;
            }

            //当前节点
            Node node = strMap.get(key);
            node.set.remove(key);

            if (node.pre == linkedList.head) {
                if (node.frequency != 1) {
                    Node addNode = new Node(key, node.frequency - 1);
                    linkedList.addAfterNode(linkedList.head, addNode);
                    strMap.put(key, addNode);
                } else {
                    strMap.remove(key);
                }
            } else {
                if (node.pre.frequency != node.frequency - 1) {
                    Node addNode = new Node(key, node.frequency - 1);
                    linkedList.addAfterNode(node.pre, addNode);
                    strMap.put(key, addNode);
                } else {
                    node.pre.set.add(key);
                    strMap.put(key, node.pre);
                }
            }

            //删除之后，当前节点集合为空，则当前节点从双向链表中删除
            if (node.set.isEmpty()) {
                linkedList.remove(node);
            }
        }

        public String getMaxKey() {
            //链表为空，则没有元素存在，返回""
            if (linkedList.head.next == linkedList.tail) {
                return "";
            } else {
                return linkedList.tail.pre.set.iterator().next();
            }
        }

        public String getMinKey() {
            //链表为空，则没有元素存在，返回""
            if (linkedList.head.next == linkedList.tail) {
                return "";
            } else {
                return linkedList.head.next.set.iterator().next();
            }
        }

        /**
         * 计数次数相同的字符串集合双向链表，节点按照计数次数由小到大排序
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

            /**
             * addNode节点加入node后面
             *
             * @param node
             * @param addNode
             */
            public void addAfterNode(Node node, Node addNode) {
                Node nextNode = node.next;
                node.next = addNode;
                addNode.pre = node;
                addNode.next = nextNode;
                nextNode.pre = addNode;
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
         * 计数次数相同的字符串集合节点，即双向链表节点
         */
        private static class Node {
            private final Set<String> set;
            //当前节点计数次数
            private int frequency;
            private Node pre;
            private Node next;

            public Node() {
                set = new HashSet<>();
            }

            public Node(String key, int frequency) {
                set = new HashSet<>();
                set.add(key);
                this.frequency = frequency;
            }
        }
    }
}
