package com.zhang.java;

import java.util.Random;

/**
 * @Date 2023/4/5 08:17
 * @Author zsy
 * @Description 设计跳表 字节面试题
 * 不使用任何库函数，设计一个 跳表 。
 * 跳表 是在 O(log(n)) 时间内完成增加、删除、搜索操作的数据结构。
 * 跳表相比于树堆与红黑树，其功能与性能相当，并且跳表的代码长度相较下更短，其设计思想与链表相似。
 * 例如，一个跳表包含 [30, 40, 50, 60, 70, 90] ，然后增加 80、45 到跳表中，以下图的方式操作：
 * 跳表中有很多层，每一层是一个短的链表。在第一层的作用下，增加、删除和搜索操作的时间复杂度不超过 O(n)。
 * 跳表的每一个操作的平均时间复杂度是 O(log(n))，空间复杂度是 O(n)。
 * 在本题中，你的设计应该要包含这些函数：
 * bool search(int target) : 返回target是否存在于跳表中。
 * void add(int num): 插入一个元素到跳表。
 * bool erase(int num): 在跳表中删除一个值，如果 num 不存在，直接返回false. 如果存在多个 num ，删除其中任意一个即可。
 * 注意，跳表中可能存在多个相同的值，你的代码需要处理这种情况。
 * <p>
 * 输入
 * ["Skiplist", "add", "add", "add", "search", "add", "search", "erase", "erase", "search"]
 * [[], [1], [2], [3], [0], [4], [1], [0], [1], [1]]
 * 输出
 * [null, null, null, null, false, null, true, false, true, false]
 * 解释
 * Skiplist skiplist = new Skiplist();
 * skiplist.add(1);
 * skiplist.add(2);
 * skiplist.add(3);
 * skiplist.search(0);   // 返回 false
 * skiplist.add(4);
 * skiplist.search(1);   // 返回 true
 * skiplist.erase(0);    // 返回 false，0 不在跳表中
 * skiplist.erase(1);    // 返回 true
 * skiplist.search(1);   // 返回 false，1 已被擦除
 * <p>
 * 0 <= num, target <= 2 * 10^4
 * 调用search, add,  erase操作次数不大于 5 * 10^4
 */
public class Problem1206 {
    public static void main(String[] args) {
        Skiplist skiplist = new Skiplist();
        skiplist.add(1);
        skiplist.add(2);
        skiplist.add(3);
        // 返回 false
        System.out.println(skiplist.search(0));
        skiplist.add(4);
        // 返回 true
        System.out.println(skiplist.search(1));
        // 返回 false，0 不在跳表中
        System.out.println(skiplist.erase(0));
        // 返回 true
        System.out.println(skiplist.erase(1));
        // 返回 false，1 已被擦除
        System.out.println(skiplist.search(1));
    }

    /**
     * 跳表是多层有序链表，每一层都是一个有序链表，且满足每个位于第i层的节点有p的概率出现在第i+1层，其中p为常数。
     * 跳表在O(logn)时间复杂度内完成增加、删除、搜索操作，空间复杂度O(n)
     */
    static class Skiplist {
        //跳表头结点
        private final Node head;
        //跳表最大高度，redis中设置为32
        private final int maxLevel = 32;
        //确定每个跳表节点层次晋升的概率，每个节点有factor的概率晋升为当前的高一层中，redis中设置为25%
        private final double factor = 0.25;
        //跳表的高度
        private int level;
        //确定每个跳表节点的随机值
        private final Random random;

        public Skiplist() {
            //跳表头结点值要小于所有节点的值，保证是有序链表，并且设置跳表头结点高度为跳表最大高度
            head = new Node(Integer.MIN_VALUE, maxLevel);
            level = 0;
            random = new Random();
        }

        /**
         * 从跳表高层往低层找每一层是否存在target，如果存在直接返回true，如果不存在继续往下一层找，都没有找到返回false
         * 时间复杂度O(logn)，空间复杂度O(1)
         *
         * @param target
         * @return
         */
        public boolean search(int target) {
            Node node = head;

            //从跳表高层往低层找target所在位置，每次判断当前节点的下一个节点是否小于target，如果小于，当前节点指向下一个节点
            for (int i = level - 1; i >= 0; i--) {
                while (node.next[i] != null && node.next[i].val < target) {
                    node = node.next[i];
                }
            }

            //第一层中，当前节点的下一个节点不为空，且当前节点的下一个节点等于target，
            //则说明跳表中找到target，查找成功，返回true
            if (node.next[0] != null && node.next[0].val == target) {
                return true;
            }

            //第一层中，当前节点的下一个节点为空，或者当前节点的下一个节点不等于target，
            //则说明跳表中没有target，查找失败，返回false
            return false;
        }

        /**
         * 从跳表高层往低层找num所在位置，得到路径数组update，根据update将node加入到每一层有序链表中，同时要判断是否更新跳表的高度
         * 时间复杂度O(logn)，空间复杂度O(maxLevel)=O(1)
         *
         * @param num
         */
        public void add(int num) {
            //从跳表高层往低层每层中的遍历节点路径数组，确保插入到每一层的链表有序
            Node[] update = new Node[maxLevel];

            //路径数组update赋初值为head
            for (int i = 0; i < maxLevel; i++) {
                update[i] = head;
            }

            Node node = head;

            //从跳表高层往低层找num所在位置，得到路径数组update
            for (int i = level - 1; i >= 0; i--) {
                while (node.next[i] != null && node.next[i].val < num) {
                    node = node.next[i];
                }
                update[i] = node;
            }

            //获取要插入跳表的跳表节点高度
            int nodeLevel = getLevel();
            //更新跳表的高度
            level = Math.max(level, nodeLevel);
            //要插入跳表的跳表节点
            Node addNode = new Node(num, nodeLevel);

            //从第一层开始，addNode加入到每一层有序链表中
            for (int i = 0; i < nodeLevel; i++) {
                addNode.next[i] = update[i].next[i];
                update[i].next[i] = addNode;
            }
        }

        /**
         * 从跳表高层往低层找num所在位置，得到路径数组update，判断num是否在跳表中，如果num不在跳表中，则直接返回false；
         * 如果num在跳表中，则从第一层往高层，删除num节点，同时要判断是否更新跳表的高度
         * 时间复杂度O(logn)，空间复杂度O(maxLevel)=O(1)
         *
         * @param num
         * @return
         */
        public boolean erase(int num) {
            //从跳表高层往低层每层中的遍历节点路径数组，确保插入到每一层的链表有序
            Node[] update = new Node[maxLevel];

            //路径数组update赋初值为head
            for (int i = 0; i < maxLevel; i++) {
                update[i] = head;
            }

            Node node = head;

            //从跳表高层往低层找num所在位置，得到路径数组update
            for (int i = level - 1; i >= 0; i--) {
                while (node.next[i] != null && node.next[i].val < num) {
                    node = node.next[i];
                }
                update[i] = node;
            }

            //要删除的节点，即为第一层中node节点的下一个节点
            Node deleteNode = node.next[0];

            //deleteNode为空，或deleteNode节点值不等于num，则num不在跳表中，则返回false
            if (deleteNode == null || node.next[0].val != num) {
                return false;
            }

            //从第一层到deleteNode的最高层，将deleteNode从当前层中删除
            for (int i = 0; i < deleteNode.next.length; i++) {
                update[i].next[i] = deleteNode.next[i];
            }

            //更新跳表的高度，由跳表的高度开始往下遍历，判断当前层头结点的下一个节点是否为空，如果为空，跳表高度减1
            while (level > 0 && head.next[level - 1] == null) {
                level--;
            }

            //num在跳表中，则返回ture
            return true;
        }

        /**
         * 根据factor获取当前跳表节点的高度
         *
         * @return
         */
        private int getLevel() {
            int level = 1;
            //生成的概率小于factor，则当期跳表节点的高度加1
            while (level < maxLevel && random.nextDouble() < factor) {
                level++;
            }
            return level;
        }

        /**
         * 跳表节点
         */
        private static class Node {
            //当前跳表节点的值
            private int val;
            //当前跳表节在不同层中的下一个跳表节点数组，next数组的长度为当前跳表节的高度
            //next[1]：当前跳表节在第2层中的下一个跳表节点
            //next[4]：当前跳表节在第5层中的下一个跳表节点
            private Node[] next;

            public Node(int val, int level) {
                this.val = val;
                next = new Node[level];
            }
        }
    }
}
