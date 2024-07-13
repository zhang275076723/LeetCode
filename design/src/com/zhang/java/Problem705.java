package com.zhang.java;

/**
 * @Date 2023/4/7 08:11
 * @Author zsy
 * @Description 设计哈希集合 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem706、Problem707、Problem716、Problem1381、Problem1670、Problem2296、Offer9、Offer30、Offer59_2
 * 不使用任何内建的哈希表库设计一个哈希集合（HashSet）。
 * 实现 MyHashSet 类：
 * void add(key) 向哈希集合中插入值 key 。
 * bool contains(key) 返回哈希集合中是否存在这个值 key 。
 * void remove(key) 将给定值 key 从哈希集合中删除。如果哈希集合中没有这个值，什么也不做。
 * <p>
 * 输入：
 * ["MyHashSet", "add", "add", "contains", "contains", "add", "contains", "remove", "contains"]
 * [[], [1], [2], [1], [3], [2], [2], [2], [2]]
 * 输出：
 * [null, null, null, true, false, null, true, null, false]
 * <p>
 * 解释：
 * MyHashSet myHashSet = new MyHashSet();
 * myHashSet.add(1);      // set = [1]
 * myHashSet.add(2);      // set = [1, 2]
 * myHashSet.contains(1); // 返回 True
 * myHashSet.contains(3); // 返回 False ，（未找到）
 * myHashSet.add(2);      // set = [1, 2]
 * myHashSet.contains(2); // 返回 True
 * myHashSet.remove(2);   // set = [1]
 * myHashSet.contains(2); // 返回 False ，（已移除）
 * <p>
 * 0 <= key <= 10^6
 * 最多调用 10^4 次 add、remove 和 contains
 */
public class Problem705 {
    public static void main(String[] args) {
        MyHashSet myHashSet = new MyHashSet();
        // set = [1]
        myHashSet.add(1);
        // set = [1, 2]
        myHashSet.add(2);
        // 返回 True
        System.out.println(myHashSet.contains(1));
        // 返回 False ，（未找到）
        System.out.println(myHashSet.contains(3));
        // set = [1, 2]
        myHashSet.add(2);
        // 返回 True
        System.out.println(myHashSet.contains(2));
        // set = [1]
        myHashSet.remove(2);
        // 返回 False ，（已移除）
        System.out.println(myHashSet.contains(2));
    }

    /**
     * 数组+链表
     * 平均时间复杂度O(1)，最坏时间复杂度O(n)，空间复杂度O(1)
     */
    static class MyHashSet {
        //链表数组
        private final LinkedList[] arr;

        public MyHashSet() {
            //1009是大于1000的第一个质数，选择一个大质数，在hash运算时能够均匀的分散到各个链表中
            arr = new LinkedList[1009];

            //链表数组初始化
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new LinkedList();
            }
        }

        public void add(int key) {
            //当前key映射的链表下标索引
            int index = hash(key);
            //当前链表头结点
            LinkedList.Node node = arr[index].head;

            //找到末尾节点，尾插法
            while (node.next != null) {
                //当前链表中已经存在key，则直接返回
                if (node.next.key == key) {
                    return;
                }

                node = node.next;
            }

            //当前节点插入当前链表末尾
            node.next = new LinkedList.Node(key);
        }

        public void remove(int key) {
            //当前key映射的链表下标索引
            int index = hash(key);
            //当前链表头结点
            LinkedList.Node node = arr[index].head;

            while (node.next != null) {
                //找到要删除的key的前驱节点
                if (node.next.key == key) {
                    LinkedList.Node deleteNode = node.next;
                    node.next = deleteNode.next;
                    deleteNode.next = null;
                    return;
                }

                node = node.next;
            }
        }

        public boolean contains(int key) {
            //当前key映射的链表下标索引
            int index = hash(key);
            //当前链表头结点
            LinkedList.Node node = arr[index].head.next;

            while (node != null) {
                //当前节点值等于key，则返回true
                if (node.key == key) {
                    return true;
                }

                node = node.next;
            }

            //当前链表遍历完也没有找到key，则返回false
            return false;
        }

        /**
         * 获取key映射的链表下标索引
         *
         * @param key
         * @return
         */
        private int hash(int key) {
            return key % arr.length;
        }

        private static class LinkedList {
            //链表头节点
            private final Node head;

            public LinkedList() {
                head = new Node();
            }

            private static class Node {
                private int key;
                private Node next;

                public Node() {
                }

                public Node(int key) {
                    this.key = key;
                }
            }
        }
    }
}
