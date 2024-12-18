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
     * 查询效率接近二分查找O(logn)，在空间复杂度上，假设每层元素都是上一层元素的一半，则n+n/2+4/n+...=O(n)
     * <p>
     * head                                                  tail
     * head                       9                   15     tail
     * head     1                 9                   15     tail
     * head     1     3           9                   15     tail
     * head     1     3     7     9     10            15     tail
     * head     1     3     7     9     10     14     15     tail
     */
    static class Skiplist {
        //跳表头结点
        private final Node head;
        //跳表的最大高度，redis中为32
        private final int maxLevel;
        //跳表的高度
        private int curLevel;
        //跳表节点从当前层晋升为高一层的概率，每个节点有factor的概率晋升为高一层的节点，redis中为25%
        private final double factor;
        //确定每个跳表节点高度的随机值
        private final Random random;

        public Skiplist() {
            maxLevel = 32;
            factor = 0.25;
            //跳表头结点值要小于所有节点的值，保证是有序链表，并且设置跳表头结点高度为跳表最大高度
            head = new Node(Integer.MIN_VALUE, maxLevel);
            curLevel = 0;
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
            for (int i = curLevel - 1; i >= 0; i--) {
                while (node.next[i] != null && node.next[i].value < target) {
                    node = node.next[i];
                }

                //当前层的下一个节点等于target，则说明跳表中存在target，返回true
                if (node.next[i] != null && node.next[i].value == target) {
                    return true;
                }
            }

            //从跳表高层往低层都没有找target，则说明跳表中没有target，返回false
            return false;
        }

        /**
         * 从跳表高层往低层找num所在位置，得到路径数组update，根据update将node加入到每一层有序链表中，同时要判断是否更新跳表的高度
         * 时间复杂度O(logn)，空间复杂度O(maxLevel)=O(1)
         *
         * @param num
         */
        public void add(int num) {
            //获取要加入跳表的跳表节点高度
            int nodeLevel = getLevel();
            //更新跳表的高度
            curLevel = Math.max(curLevel, nodeLevel);
            //要加入跳表的跳表节点
            Node addNode = new Node(num, nodeLevel);

            //从跳表高层往低层每层中的遍历节点路径数组update，确保加入到每一层的链表有序
            //redis中还记录了update数组中跳表节点在当前层中距离跳表头节点的距离数组rank
            //注意：查询高度要从curLevel开始遍历，执行时间15ms，如果从nodeLevel开始遍历，则执行时间270+ms，
            //因为有可能当前要加入的节点值比较大，如果从nodeLevel层开始遍历，则会顺序遍历，花费的时间长，
            //而如果从curLevel层开始遍历，最高层节点最少，越往下层节点越多，类似二分查询，花费的时间短
            Node[] update = new Node[curLevel];
            Node node = head;

            //从跳表高层往低层找num所在位置，得到路径数组update
            for (int i = curLevel - 1; i >= 0; i--) {
                while (node.next[i] != null && node.next[i].value < num) {
                    node = node.next[i];
                }

                update[i] = node;
            }

            //从addNode的最高层第开始往第一层，addNode加入到每一层有序链表中
            for (int i = nodeLevel - 1; i >= 0; i--) {
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
            //从跳表高层往低层每层中的遍历节点路径数组update，确保加入到每一层的链表有序
            //redis中还记录了update数组中跳表节点在当前层中距离跳表头节点的距离数组rank
            Node[] update = new Node[curLevel];
            Node node = head;

            //从跳表高层往低层找num所在位置，得到路径数组update
            for (int i = curLevel - 1; i >= 0; i--) {
                while (node.next[i] != null && node.next[i].value < num) {
                    node = node.next[i];
                }

                update[i] = node;
            }

            //要删除的节点，即为第一层中update数组中的下一个节点
            //也写成Node deleteNode = node.next[0];
            Node deleteNode = update[0].next[0];

            //deleteNode为空，或deleteNode节点值不等于num，则num不在跳表中，则返回false
            if (deleteNode == null || deleteNode.value != num) {
                return false;
            }

            //从deleteNode的最高层开始往第一层遍历，将deleteNode从当前层中删除
            for (int i = deleteNode.next.length - 1; i >= 0; i--) {
                //也写成update[i].next[i] = deleteNode.next[i];
                update[i].next[i] = update[i].next[i].next[i];
            }

            //更新跳表的高度，从当前跳表的高度开始往下遍历，如果当前层头结点的下一个节点为空，
            //即当前层删除deleteNode节点之后为空，跳表高度减1
            while (curLevel > 0 && head.next[curLevel - 1] == null) {
                curLevel--;
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

            //生成的[0,1)内随机数小于factor，则当前跳表节点的高度加1
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
            private int value;
            //当前跳表节在不同层中的下一个跳表节点数组，next数组的长度为当前跳表节的高度
            //next[1]：当前跳表节在第2层中的下一个跳表节点
            //next[4]：当前跳表节在第5层中的下一个跳表节点
            private Node[] next;
            //redis中还记录了当前跳表节点在不同层中距离下一个跳表节点的距离数组span

            public Node(int value, int level) {
                this.value = value;
                next = new Node[level];
            }
        }
    }
}
