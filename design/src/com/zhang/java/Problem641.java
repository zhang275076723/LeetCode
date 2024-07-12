package com.zhang.java;

/**
 * @Date 2023/4/6 09:12
 * @Author zsy
 * @Description 设计循环双端队列 类比Problem622、Problem705、Problem706、Problem707、Problem2296
 * 设计实现双端队列。
 * 实现 MyCircularDeque 类:
 * MyCircularDeque(int k) ：构造函数,双端队列最大为 k 。
 * boolean insertFront()：将一个元素添加到双端队列头部。 如果操作成功返回 true ，否则返回 false 。
 * boolean insertLast() ：将一个元素添加到双端队列尾部。如果操作成功返回 true ，否则返回 false 。
 * boolean deleteFront() ：从双端队列头部删除一个元素。 如果操作成功返回 true ，否则返回 false 。
 * boolean deleteLast() ：从双端队列尾部删除一个元素。如果操作成功返回 true ，否则返回 false 。
 * int getFront() ：从双端队列头部获得一个元素。如果双端队列为空，返回 -1 。
 * int getRear() ：获得双端队列的最后一个元素。 如果双端队列为空，返回 -1 。
 * boolean isEmpty() ：若双端队列为空，则返回 true ，否则返回 false  。
 * boolean isFull() ：若双端队列满了，则返回 true ，否则返回 false 。
 * <p>
 * 输入
 * ["MyCircularDeque", "insertLast", "insertLast", "insertFront", "insertFront", "getRear", "isFull", "deleteLast", "insertFront", "getFront"]
 * [[3], [1], [2], [3], [4], [], [], [], [4], []]
 * 输出
 * [null, true, true, true, false, 2, true, true, true, 4]
 * 解释
 * MyCircularDeque circularDeque = new MycircularDeque(3); // 设置容量大小为3
 * circularDeque.insertLast(1);			                   // 返回 true
 * circularDeque.insertLast(2);			                   // 返回 true
 * circularDeque.insertFront(3);			               // 返回 true
 * circularDeque.insertFront(4);			               // 已经满了，返回 false
 * circularDeque.getRear();  				               // 返回 2
 * circularDeque.isFull();				                   // 返回 true
 * circularDeque.deleteLast();			                   // 返回 true
 * circularDeque.insertFront(4);			               // 返回 true
 * circularDeque.getFront();				               // 返回 4
 * <p>
 * 1 <= k <= 1000
 * 0 <= value <= 1000
 * insertFront, insertLast, deleteFront, deleteLast, getFront, getRear, isEmpty, isFull 调用次数不大于 2000 次
 */
public class Problem641 {
    public static void main(String[] args) {
        // 设置容量大小为3
//        MyCircularDeque circularDeque = new MyCircularDeque(3);
        MyCircularDeque2 circularDeque = new MyCircularDeque2(3);
        // 返回 true
        System.out.println(circularDeque.insertLast(1));
        // 返回 true
        System.out.println(circularDeque.insertLast(2));
        // 返回 true
        System.out.println(circularDeque.insertFront(3));
        // 已经满了，返回 false
        System.out.println(circularDeque.insertFront(4));
        // 返回 2
        System.out.println(circularDeque.getRear());
        // 返回 true
        System.out.println(circularDeque.isFull());
        // 返回 true
        System.out.println(circularDeque.deleteLast());
        // 返回 true
        System.out.println(circularDeque.insertFront(4));
        // 返回 4
        System.out.println(circularDeque.getFront());
    }

    /**
     * 数组+首尾指针实现双向循环队列
     */
    static class MyCircularDeque {
        //双向循环队列数组，使用一个冗余位置判断队列满还是空，实际能存放的大小为arr.length-1
        private final int[] arr;
        //队首指针，指向队列中第一个元素下标索引
        private int front;
        //队尾指针，指向队列中最后一个元素下一个位置的下标索引
        private int rear;

        public MyCircularDeque(int k) {
            //多申请一个长度，用于判断队列满还是空
            arr = new int[k + 1];
            front = 0;
            rear = 0;
        }

        public boolean insertFront(int value) {
            //队列满了，直接返回false
            if (isFull()) {
                return false;
            }

            //首指针左移
            front = (front - 1 + arr.length) % arr.length;
            arr[front] = value;
            return true;
        }

        public boolean insertLast(int value) {
            //队列满了，直接返回false
            if (isFull()) {
                return false;
            }

            arr[rear] = value;
            //尾指针后移
            rear = (rear + 1) % arr.length;
            return true;
        }

        public boolean deleteFront() {
            //队列为空，直接返回false
            if (isEmpty()) {
                return false;
            }

            //首指针后移
            front = (front + 1) % arr.length;
            return true;
        }

        public boolean deleteLast() {
            //队列为空，直接返回false
            if (isEmpty()) {
                return false;
            }

            //尾指针左移
            rear = (rear - 1 + arr.length) % arr.length;
            return true;
        }

        public int getFront() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return -1;
            }

            return arr[front];
        }

        public int getRear() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return -1;
            }

            return arr[(rear - 1 + arr.length) % arr.length];
        }

        public boolean isEmpty() {
            return front == rear;
        }

        public boolean isFull() {
            //尾指针加1追上首指针，则队列满了
            return (rear + 1) % arr.length == front;
        }
    }

    /**
     * 双向链表实现双向循环队列
     */
    static class MyCircularDeque2 {
        private final LinkedList linkedList;
        //队列容量
        private final int capacity;

        public MyCircularDeque2(int k) {
            linkedList = new LinkedList();
            capacity = k;
        }

        public boolean insertFront(int value) {
            //队列满了，直接返回false
            if (isFull()) {
                return false;
            }

            linkedList.addFirst(value);
            return true;
        }

        public boolean insertLast(int value) {
            //队列满了，直接返回false
            if (isFull()) {
                return false;
            }

            linkedList.addLast(value);
            return true;
        }

        public boolean deleteFront() {
            //队列为空，直接返回false
            if (isEmpty()) {
                return false;
            }

            linkedList.removeFirst();
            return true;
        }

        public boolean deleteLast() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return false;
            }

            linkedList.removeLast();
            return true;
        }

        public int getFront() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return -1;
            }

            return linkedList.head.next.value;
        }

        public int getRear() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return -1;
            }

            return linkedList.tail.pre.value;
        }

        public boolean isEmpty() {
            return linkedList.count == 0;
        }

        public boolean isFull() {
            return linkedList.count == capacity;
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

            public void addFirst(int value) {
                Node node = new Node(value);
                node.pre = head;
                node.next = head.next;
                head.next.pre = node;
                head.next = node;
                count++;
            }

            public void addLast(int value) {
                Node node = new Node(value);
                node.pre = tail.pre;
                node.next = tail;
                tail.pre.next = node;
                tail.pre = node;
                count++;
            }

            public void removeFirst() {
                //链表为空，直接返回-1
                if (count == 0) {
                    return;
                }

                //头结点的下一个节点，即要删除的节点
                Node node = head.next;
                node.next.pre = head;
                head.next = node.next;
                node.pre = null;
                node.next = null;
                count--;
            }

            public void removeLast() {
                //链表为空，直接返回-1
                if (count == 0) {
                    return;
                }

                //尾节点的前驱节点，即要删除的节点
                Node node = tail.pre;
                node.pre.next = tail;
                tail.pre = node.pre;
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
