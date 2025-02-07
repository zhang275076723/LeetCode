package com.zhang.java;

import java.util.LinkedList;
import java.util.List;

/**
 * @Date 2025/3/28 08:16
 * @Author zsy
 * @Description 二叉树寻路 类比Problem222、Problem919、Problem958
 * 在一棵无限的二叉树上，每个节点都有两个子节点，树中的节点 逐行 依次按 “之” 字形进行标记。
 * 如下图所示，在奇数行（即，第一行、第三行、第五行……）中，按从左到右的顺序进行标记；
 * 而偶数行（即，第二行、第四行、第六行……）中，按从右到左的顺序进行标记。
 * 给你树上某一个节点的标号 label，请你返回从根节点到该标号为 label 节点的路径，该路径是由途经的节点标号所组成的。
 * <p>
 * 输入：label = 14
 * 输出：[1,3,4,14]
 * <p>
 * 输入：label = 26
 * 输出：[1,2,6,10,26]
 * <p>
 * 1 <= label <= 10^6
 */
public class Problem1104 {
    public static void main(String[] args) {
        Problem1104 problem1104 = new Problem1104();
        int label = 26;
        System.out.println(problem1104.pathInZigZagTree(label));
    }

    /**
     * 模拟
     * 时间复杂度O(log(label))=O(1)，空间复杂度O(1)
     * <p>
     * 例如：label=26
     * <                                          1                                (从左往右标记)
     * <                               /                       \
     * <                             3                          2                  (从右往左标记)
     * <                        /          \                /          \
     * <                      4             5              6            7          (从左往右标记)
     * <                   /    \         /    \        /     \      /     \
     * <                 15     14      13      12     11     10     9       8     (从右往左标记)
     * <               /  \    /  \    /  \    /  \   /  \   /
     * <             16   17  18  19  20  21  22  23 24  25 26                     (从左往右标记)
     * <
     * < level=5，firstCount=16，lastCount=31，index=(31-26)/2=2，路径为[26]
     * < level=4，firstCount=8，lastCount=15，curNum=8+2=10，index=(15-10)/2=2，路径为[10,26]
     * < level=3，firstCount=4，lastCount=7，curNum=4+2=6，index=(7-6)/2=0，路径为[6,10,26]
     * < level=2，firstCount=2，lastCount=3，curNum=2+0=2，index=(3-2)/2=0，路径为[2,6,10,26]
     * < level=1，firstCount=1，lastCount=1，curNum=1+0=1，路径为[1,2,6,10,26]
     *
     * @param label
     * @return
     */
    public List<Integer> pathInZigZagTree(int label) {
        //label所在的层数，最高层为1
        int level = 1;
        //2^level
        int pow = 2;

        //确定label所在的层数，2^level-1即为level层满二叉树节点的个数
        while (pow - 1 < label) {
            level++;
            pow = pow * 2;
        }

        LinkedList<Integer> list = new LinkedList<>();
        //从下往上找，所以要首添加
        list.addFirst(label);
        //label已经添加，层数减1
        level--;

        //从下往上遍历过程中，当前层第一个节点
        int firstCount = pow / 2;
        //从下往上遍历过程中，当前层最后一个节点
        int lastCount = pow - 1;
        //从下往上遍历过程中，当前节点是上一层的第几个节点的父节点
        int index = (lastCount - label) / 2;

        //从最高层往低层找当前层节点的路径
        while (level > 0) {
            lastCount = firstCount - 1;
            firstCount = firstCount / 2;

            //路径中当前层节点
            int curNum = firstCount + index;
            //从下往上找，所以要首添加
            list.addFirst(curNum);

            index = (lastCount - curNum) / 2;
            level--;
        }

        return list;
    }
}
