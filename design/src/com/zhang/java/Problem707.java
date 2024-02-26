package com.zhang.java;

/**
 * @Date 2023/4/6 10:58
 * @Author zsy
 * @Description 设计链表 类比Problem622、Problem641、Problem705、Problem706
 * 你可以选择使用单链表或者双链表，设计并实现自己的链表。
 * 单链表中的节点应该具备两个属性：val 和 next 。
 * val 是当前节点的值，next 是指向下一个节点的指针/引用。
 * 如果是双向链表，则还需要属性 prev 以指示链表中的上一个节点。
 * 假设链表中的所有节点下标从 0 开始。
 * 实现 MyLinkedList 类：
 * MyLinkedList() 初始化 MyLinkedList 对象。
 * int get(int index) 获取链表中下标为 index 的节点的值。如果下标无效，则返回 -1 。
 * void addAtHead(int val) 将一个值为 val 的节点插入到链表中第一个元素之前。在插入完成后，新节点会成为链表的第一个节点。
 * void addAtTail(int val) 将一个值为 val 的节点追加到链表中作为链表的最后一个元素。
 * void addAtIndex(int index, int val) 将一个值为 val 的节点插入到链表中下标为 index 的节点之前。
 * 如果 index 等于链表的长度，那么该节点会被追加到链表的末尾。如果 index 比长度更大，该节点将 不会插入 到链表中。
 * void deleteAtIndex(int index) 如果下标有效，则删除链表中下标为 index 的节点。
 * <p>
 * 输入
 * ["MyLinkedList", "addAtHead", "addAtTail", "addAtIndex", "get", "deleteAtIndex", "get"]
 * [[], [1], [3], [1, 2], [1], [1], [1]]
 * 输出
 * [null, null, null, null, 2, null, 3]
 * 解释
 * MyLinkedList myLinkedList = new MyLinkedList();
 * myLinkedList.addAtHead(1);
 * myLinkedList.addAtTail(3);
 * myLinkedList.addAtIndex(1, 2);    // 链表变为 1->2->3
 * myLinkedList.get(1);              // 返回 2
 * myLinkedList.deleteAtIndex(1);    // 现在，链表变为 1->3
 * myLinkedList.get(1);              // 返回 3
 * <p>
 * 0 <= index, val <= 1000
 * 请不要使用内置的 LinkedList 库。
 * 调用 get、addAtHead、addAtTail、addAtIndex 和 deleteAtIndex 的次数不超过 2000 。
 */
public class Problem707 {
    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(1);
        myLinkedList.addAtTail(3);
        // 链表变为 1->2->3
        myLinkedList.addAtIndex(1, 2);
        // 返回 2
        System.out.println(myLinkedList.get(1));
        // 现在，链表变为 1->3
        myLinkedList.deleteAtIndex(1);
        // 返回 3
        System.out.println(myLinkedList.get(1));
    }

    /**
     * 手动实现双向链表
     */
    static class MyLinkedList {
        //双向链表
        private final LinkedList linkedList;

        public MyLinkedList() {
            linkedList = new LinkedList();
        }

        public int get(int index) {
            return linkedList.get(index);
        }

        public void addAtHead(int val) {
            linkedList.addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            linkedList.addAtIndex(linkedList.count, val);
        }

        public void addAtIndex(int index, int val) {
            linkedList.addAtIndex(index, val);
        }

        public void deleteAtIndex(int index) {
            linkedList.removeAtIndex(index);
        }

        /**
         * 双向链表
         */
        private static class LinkedList {
            //链表头指针
            private final Node head;
            //链表尾指针
            private final Node tail;
            //链表中元素个数
            private int count;

            public LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
                count = 0;
            }

            /**
             * 获取链表中下标索引为index的节点值，index不合法，返回-1
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param index
             * @return
             */
            private int get(int index) {
                //index索引无效，返回-1
                if (index < 0 || index >= count) {
                    return -1;
                }

                Node node = head.next;

                for (int i = 0; i < index; i++) {
                    node = node.next;
                }

                return node.value;
            }

            /**
             * 在链表下标索引index处添加节点，index不合法，直接返回
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param index
             * @param value
             */
            private void addAtIndex(int index, int value) {
                //index索引无效，直接返回
                if (index < 0 || index > count) {
                    return;
                }

                //要添加节点的前驱节点
                Node preNode = head;

                for (int i = 0; i < index; i++) {
                    preNode = preNode.next;
                }

                //要添加的节点
                Node node = new Node(value);
                node.pre = preNode;
                node.next = preNode.next;
                preNode.next.pre = node;
                preNode.next = node;
                count++;
            }

            /**
             * 删除链表下标索引index处节点，index不合法，返回-1
             * 时间复杂度O(n)，空间复杂度O(1)
             *
             * @param index
             */
            private void removeAtIndex(int index) {
                if (index < 0 || index >= count) {
                    return;
                }

                //要删除节点的前驱节点
                Node preNode = head;

                for (int i = 0; i < index; i++) {
                    preNode = preNode.next;
                }

                //要删除的节点
                Node node = preNode.next;
                node.next.pre = preNode;
                preNode.next = node.next;
                node.pre = null;
                node.next = null;
                count--;
            }

            /**
             * 双向链表节点
             */
            private static class Node {
                private int value;
                private Node pre;
                private Node next;

                public Node() {
                }

                public Node(int value) {
                    this.value = value;
                }
            }
        }
    }
}
