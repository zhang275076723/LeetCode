package com.zhang.java;

/**
 * @Date 2023/4/6 08:25
 * @Author zsy
 * @Description 设计循环队列 类比Problem155、Problem225、Problem232、Problem641、Problem705、Problem706、Problem707、Problem716、Problem895、Problem1172、Problem1381、Problem1670、Offer9、Offer30、Offer59_2
 * 设计你的循环队列实现。
 * 循环队列是一种线性数据结构，其操作表现基于 FIFO（先进先出）原则并且队尾被连接在队首之后以形成一个循环。
 * 它也被称为“环形缓冲器”。
 * 循环队列的一个好处是我们可以利用这个队列之前用过的空间。
 * 在一个普通队列里，一旦一个队列满了，我们就不能插入下一个元素，即使在队列前面仍有空间。
 * 但是使用循环队列，我们能使用这些空间去存储新的值。
 * 你的实现应该支持如下操作：
 * MyCircularQueue(k): 构造器，设置队列长度为 k 。
 * Front: 从队首获取元素。如果队列为空，返回 -1 。
 * Rear: 获取队尾元素。如果队列为空，返回 -1 。
 * enQueue(value): 向循环队列插入一个元素。如果成功插入则返回真。
 * deQueue(): 从循环队列中删除一个元素。如果成功删除则返回真。
 * isEmpty(): 检查循环队列是否为空。
 * isFull(): 检查循环队列是否已满。
 * <p>
 * MyCircularQueue circularQueue = new MyCircularQueue(3); // 设置长度为 3
 * circularQueue.enQueue(1);  // 返回 true
 * circularQueue.enQueue(2);  // 返回 true
 * circularQueue.enQueue(3);  // 返回 true
 * circularQueue.enQueue(4);  // 返回 false，队列已满
 * circularQueue.Rear();      // 返回 3
 * circularQueue.isFull();    // 返回 true
 * circularQueue.deQueue();   // 返回 true
 * circularQueue.enQueue(4);  // 返回 true
 * circularQueue.Rear();      // 返回 4
 * <p>
 * 所有的值都在 0 至 1000 的范围内；
 * 操作数将在 1 至 1000 的范围内；
 * 请不要使用内置的队列库。
 */
public class Problem622 {
    public static void main(String[] args) {
        // 设置长度为 3
//        MyCircularQueue circularQueue = new MyCircularQueue(3);
        MyCircularQueue2 circularQueue = new MyCircularQueue2(3);
        // 返回 true
        System.out.println(circularQueue.enQueue(1));
        // 返回 true
        System.out.println(circularQueue.enQueue(2));
        // 返回 true
        System.out.println(circularQueue.enQueue(3));
        // 返回 false，队列已满
        System.out.println(circularQueue.enQueue(4));
        // 返回 3
        System.out.println(circularQueue.Rear());
        // 返回 true
        System.out.println(circularQueue.isFull());
        // 返回 true
        System.out.println(circularQueue.deQueue());
        // 返回 true
        System.out.println(circularQueue.enQueue(4));
        // 返回 4
        System.out.println(circularQueue.Rear());
    }

    /**
     * 数组+首尾指针实现循环队列
     */
    static class MyCircularQueue {
        //循环队列数组，使用一个冗余位置判断队列满还是空，实际能存放的大小为arr.length-1
        private final int[] arr;
        //队首指针，指向队列中第一个元素下标索引
        private int front;
        //队尾指针，指向队列中最后一个元素下一个位置的下标索引
        private int rear;

        public MyCircularQueue(int k) {
            //多申请一个长度，用于判断队列满还是空
            arr = new int[k + 1];
            front = 0;
            rear = 0;
        }

        public boolean enQueue(int value) {
            //队列满了，直接返回false
            if (isFull()) {
                return false;
            }

            arr[rear] = value;
            //队尾指针后移
            rear = (rear + 1) % arr.length;
            return true;
        }

        public boolean deQueue() {
            //队列为空，直接返回false
            if (isEmpty()) {
                return false;
            }

            //队首指针后移
            front = (front + 1) % arr.length;
            return true;
        }

        public int Front() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return -1;
            }

            return arr[front];
        }

        public int Rear() {
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
     * 双向链表实现循环队列
     */
    static class MyCircularQueue2 {
        private final LinkedList linkedList;
        //队列容量
        private final int capacity;

        public MyCircularQueue2(int k) {
            linkedList = new LinkedList();
            capacity = k;
        }

        public boolean enQueue(int value) {
            //队列满了，直接返回false
            if (isFull()) {
                return false;
            }

            linkedList.addLast(value);
            return true;
        }

        public boolean deQueue() {
            //队列为空，直接返回false
            if (isEmpty()) {
                return false;
            }

            linkedList.removeFirst();
            return true;
        }

        public int Front() {
            //队列为空，直接返回-1
            if (isEmpty()) {
                return -1;
            }

            return linkedList.head.next.value;
        }

        public int Rear() {
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
            //链表中节点的个数
            private int count;

            public LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
                count = 0;
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
