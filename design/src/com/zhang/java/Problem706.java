package com.zhang.java;

/**
 * @Date 2023/4/7 08:54
 * @Author zsy
 * @Description 设计哈希映射 类比Problem622、Problem641、Problem705、Problem707
 * 不使用任何内建的哈希表库设计一个哈希映射（HashMap）。
 * 实现 MyHashMap 类：
 * MyHashMap() 用空映射初始化对象
 * void put(int key, int value) 向 HashMap 插入一个键值对 (key, value) 。
 * 如果 key 已经存在于映射中，则更新其对应的值 value 。
 * int get(int key) 返回特定的 key 所映射的 value ；如果映射中不包含 key 的映射，返回 -1 。
 * void remove(key) 如果映射中存在 key 的映射，则移除 key 和它所对应的 value 。
 * <p>
 * 输入：
 * ["MyHashMap", "put", "put", "get", "get", "put", "get", "remove", "get"]
 * [[], [1, 1], [2, 2], [1], [3], [2, 1], [2], [2], [2]]
 * 输出：
 * [null, null, null, 1, -1, null, 1, null, -1]
 * <p>
 * 解释：
 * MyHashMap myHashMap = new MyHashMap();
 * myHashMap.put(1, 1); // myHashMap 现在为 [[1,1]]
 * myHashMap.put(2, 2); // myHashMap 现在为 [[1,1], [2,2]]
 * myHashMap.get(1);    // 返回 1 ，myHashMap 现在为 [[1,1], [2,2]]
 * myHashMap.get(3);    // 返回 -1（未找到），myHashMap 现在为 [[1,1], [2,2]]
 * myHashMap.put(2, 1); // myHashMap 现在为 [[1,1], [2,1]]（更新已有的值）
 * myHashMap.get(2);    // 返回 1 ，myHashMap 现在为 [[1,1], [2,1]]
 * myHashMap.remove(2); // 删除键为 2 的数据，myHashMap 现在为 [[1,1]]
 * myHashMap.get(2);    // 返回 -1（未找到），myHashMap 现在为 [[1,1]]
 * <p>
 * 0 <= key, value <= 10^6
 * 最多调用 10^4 次 put、get 和 remove 方法
 */
public class Problem706 {
    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();
        // myHashMap 现在为 [[1,1]]
        myHashMap.put(1, 1);
        // myHashMap 现在为 [[1,1], [2,2]]
        myHashMap.put(2, 2);
        // 返回 1 ，myHashMap 现在为 [[1,1], [2,2]]
        System.out.println(myHashMap.get(1));
        // 返回 -1（未找到），myHashMap 现在为 [[1,1], [2,2]]
        System.out.println(myHashMap.get(3));
        // myHashMap 现在为 [[1,1], [2,1]]（更新已有的值）
        myHashMap.put(2, 1);
        // 返回 1 ，myHashMap 现在为 [[1,1], [2,1]]
        System.out.println(myHashMap.get(2));
        // 删除键为 2 的数据，myHashMap 现在为 [[1,1]]
        myHashMap.remove(2);
        // 返回 -1（未找到），myHashMap 现在为 [[1,1]]
        System.out.println(myHashMap.get(2));
    }

    /**
     * 数组+链表
     * 平均时间复杂度O(1)，最坏时间复杂度O(n)，空间复杂度O(1)
     */
    static class MyHashMap {
        //链表数组
        private final LinkedList[] arr;

        public MyHashMap() {
            //1009是大于1000的第一个质数，选择一个大质数，在hash运算时能够均匀的分散到各个链表中
            arr = new LinkedList[1009];

            //链表数组初始化
            for (int i = 0; i < arr.length; i++) {
                arr[i] = new LinkedList();
            }
        }

        public void put(int key, int value) {
            //当前key映射的链表下标索引
            int index = hash(key);
            //当前链表头结点
            LinkedList.Node node = arr[index].head;

            //找到末尾节点，尾插法
            while (node.next != null) {
                //当前链表中已经存在key，修改当前节点的value
                if (node.next.key == key) {
                    node.next.value = value;
                    return;
                }

                node = node.next;
            }

            //当前节点插入当前链表末尾
            node.next = new LinkedList.Node(key, value);
        }

        public int get(int key) {
            //当前key映射的链表下标索引
            int index = hash(key);
            //当前链表头结点
            LinkedList.Node node = arr[index].head.next;

            while (node != null) {
                if (node.key == key) {
                    return node.value;
                }

                node = node.next;
            }

            //当前链表中没有找到，返回-1
            return -1;
        }

        public void remove(int key) {
            //当前key映射的链表下标索引
            int index = hash(key);
            //当前链表头结点
            LinkedList.Node node = arr[index].head;

            while (node.next != null) {
                //找到要删除的key
                if (node.next.key == key) {
                    LinkedList.Node deleteNode = node.next;
                    node.next = deleteNode.next;
                    deleteNode.next = null;
                    return;
                }

                node = node.next;
            }
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
            //链表头结点
            private final Node head;

            public LinkedList() {
                head = new Node();
            }

            private static class Node {
                private int key;
                private int value;
                private Node next;

                public Node() {
                }

                public Node(int key, int value) {
                    this.key = key;
                    this.value = value;
                }
            }
        }
    }
}
