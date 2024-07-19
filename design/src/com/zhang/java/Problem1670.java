package com.zhang.java;

/**
 * @Date 2024/9/7 08:25
 * @Author zsy
 * @Description 设计前中后队列 类比Problem295 类比Problem155、Problem225、Problem232、Problem622、Problem641、Problem705、Problem706、Problem707、Problem716、Problem1381、Problem2296、Offer9、Offer30、Offer59_2
 * 请你设计一个队列，支持在前，中，后三个位置的 push 和 pop 操作。
 * 请你完成 FrontMiddleBack 类：
 * FrontMiddleBack() 初始化队列。
 * void pushFront(int val) 将 val 添加到队列的 最前面 。
 * void pushMiddle(int val) 将 val 添加到队列的 正中间 。
 * void pushBack(int val) 将 val 添加到队里的 最后面 。
 * int popFront() 将 最前面 的元素从队列中删除并返回值，如果删除之前队列为空，那么返回 -1 。
 * int popMiddle() 将 正中间 的元素从队列中删除并返回值，如果删除之前队列为空，那么返回 -1 。
 * int popBack() 将 最后面 的元素从队列中删除并返回值，如果删除之前队列为空，那么返回 -1 。
 * 请注意当有 两个 中间位置的时候，选择靠前面的位置进行操作。比方说：
 * 将 6 添加到 [1, 2, 3, 4, 5] 的中间位置，结果数组为 [1, 2, 6, 3, 4, 5] 。
 * 从 [1, 2, 3, 4, 5, 6] 的中间位置弹出元素，返回 3 ，数组变为 [1, 2, 4, 5, 6] 。
 * <p>
 * 输入：
 * ["FrontMiddleBackQueue", "pushFront", "pushBack", "pushMiddle", "pushMiddle", "popFront", "popMiddle", "popMiddle", "popBack", "popFront"]
 * [[], [1], [2], [3], [4], [], [], [], [], []]
 * 输出：
 * [null, null, null, null, null, 1, 3, 4, 2, -1]
 * 解释：
 * FrontMiddleBackQueue q = new FrontMiddleBackQueue();
 * q.pushFront(1);   // [1]
 * q.pushBack(2);    // [1, 2]
 * q.pushMiddle(3);  // [1, 3, 2]
 * q.pushMiddle(4);  // [1, 4, 3, 2]
 * q.popFront();     // 返回 1 -> [4, 3, 2]
 * q.popMiddle();    // 返回 3 -> [4, 2]
 * q.popMiddle();    // 返回 4 -> [2]
 * q.popBack();      // 返回 2 -> []
 * q.popFront();     // 返回 -1 -> [] （队列为空）
 * <p>
 * 1 <= val <= 10^9
 * 最多调用 1000 次 pushFront， pushMiddle， pushBack， popFront， popMiddle 和 popBack 。
 */
public class Problem1670 {
    public static void main(String[] args) {
        FrontMiddleBackQueue q = new FrontMiddleBackQueue();
        // [1]
        q.pushFront(1);
        // [1, 2]
        q.pushBack(2);
        // [1, 3, 2]
        q.pushMiddle(3);
        // [1, 4, 3, 2]
        q.pushMiddle(4);
        // 返回 1 -> [4, 3, 2]
        System.out.println(q.popFront());
        // 返回 3 -> [4, 2]
        System.out.println(q.popMiddle());
        // 返回 4 -> [2]
        System.out.println(q.popMiddle());
        // 返回 2 -> []
        System.out.println(q.popBack());
        // 返回 -1 -> [] （队列为空）
        System.out.println(q.popFront());
    }

    /**
     * 2个双向链表
     * 中间元素的操作转换为对2个链表首尾元素的操作
     */
    static class FrontMiddleBackQueue {
        //存储队列中前一半元素的双向链表，末尾元素即为队列的中间元素
        private final LinkedList linkedList1;
        //存储队列中后一半元素的双向链表
        private final LinkedList linkedList2;

        public FrontMiddleBackQueue() {
            linkedList1 = new LinkedList();
            linkedList2 = new LinkedList();
        }

        public void pushFront(int val) {
            linkedList1.addFirst(val);

            //linkedList1末尾元素移动到linkedList2首位置，保证linkedList1末尾元素即为队列的中间元素
            if (linkedList1.count == linkedList2.count + 2) {
                int value = linkedList1.removeLast();
                linkedList2.addFirst(value);
            }
        }

        public void pushMiddle(int val) {
            //val插入到linkedList1尾位置，作为中间元素
            if (linkedList1.count == linkedList2.count) {
                linkedList1.addLast(val);
            } else {
                //linkedList1末尾元素移动到linkedList2首位置，val插入到linkedList1尾位置，作为中间元素
                int value = linkedList1.removeLast();
                linkedList2.addFirst(value);
                linkedList1.addLast(val);
            }
        }

        public void pushBack(int val) {
            linkedList2.addLast(val);

            //linkedList2首元素移动到linkedList1末尾位置，保证linkedList1末尾元素即为队列的中间元素
            if (linkedList1.count + 1 == linkedList2.count) {
                int value = linkedList2.removeFirst();
                linkedList1.addLast(value);
            }
        }

        public int popFront() {
            //队列为空，则直接返回-1
            if (linkedList1.count == 0) {
                return -1;
            }

            int result = linkedList1.removeFirst();

            //linkedList2首元素移动到linkedList1末尾位置，保证linkedList1末尾元素即为队列的中间元素
            if (linkedList1.count + 1 == linkedList2.count) {
                int value = linkedList2.removeFirst();
                linkedList1.addLast(value);
            }

            return result;
        }

        public int popMiddle() {
            //队列为空，则直接返回-1
            if (linkedList1.count == 0) {
                return -1;
            }

            int result = linkedList1.removeLast();

            //linkedList2首元素移动到linkedList1末尾位置，保证linkedList1末尾元素即为队列的中间元素
            if (linkedList1.count + 1 == linkedList2.count) {
                int value = linkedList2.removeFirst();
                linkedList1.addLast(value);
            }

            return result;
        }

        public int popBack() {
            //队列为空，则直接返回-1
            if (linkedList1.count == 0) {
                return -1;
            }

            //linkedList2为空，则linkedList1末尾元素即为队尾元素
            if (linkedList2.count == 0) {
                return linkedList1.removeLast();
            }

            int result = linkedList2.removeLast();

            //linkedList1末尾元素移动到linkedList2首位置，保证linkedList1末尾元素即为队列的中间元素
            if (linkedList1.count == linkedList2.count + 2) {
                int value = linkedList1.removeLast();
                linkedList2.addFirst(value);
            }

            return result;
        }

        /**
         * 双向链表
         */
        private static class LinkedList {
            private final Node head;
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

            public void addFirst(int value) {
                Node node = new Node(value);
                node.next = head.next;
                head.next.pre = node;
                node.pre = head;
                head.next = node;
                count++;
            }

            public void addLast(int value) {
                Node node = new Node(value);
                node.pre = tail.pre;
                tail.pre.next = node;
                node.next = tail;
                tail.pre = node;
                count++;
            }

            public int removeFirst() {
                //链表为空，则直接返回-1
                if (count == 0) {
                    return -1;
                }

                //头结点的前下一个节点即为要删除的节点
                Node deleteNode = head.next;
                deleteNode.next.pre = head;
                head.next = deleteNode.next;
                deleteNode.pre = null;
                deleteNode.next = null;
                count--;

                return deleteNode.value;
            }

            public int removeLast() {
                //链表为空，则直接返回-1
                if (count == 0) {
                    return -1;
                }

                //尾结点的前一个节点即为要删除的节点
                Node deleteNode = tail.pre;
                deleteNode.pre.next = tail;
                tail.pre = deleteNode.pre;
                deleteNode.pre = null;
                deleteNode.next = null;
                count--;

                return deleteNode.value;
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
