package com.zhang.java;

/**
 * @Date 2023/2/8 08:34
 * @Author zsy
 * @Description 情侣牵手 并查集类比Problem130、Problem200、Problem399、Problem695、Problem827
 * n 对情侣坐在连续排列的 2n 个座位上，想要牵到对方的手。
 * 人和座位由一个整数数组 row 表示，其中 row[i] 是坐在第 i 个座位上的人的 ID。
 * 情侣们按顺序编号，第一对是 (0, 1)，第二对是 (2, 3)，以此类推，最后一对是 (2n-2, 2n-1)。
 * 返回 最少交换座位的次数，以便每对情侣可以并肩坐在一起。
 * 每次交换可选择任意两人，让他们站起来交换座位。
 * <p>
 * 输入: row = [0,2,1,3]
 * 输出: 1
 * 解释: 只需要交换row[1]和row[2]的位置即可。
 * <p>
 * 输入: row = [3,2,0,1]
 * 输出: 0
 * 解释: 无需交换座位，所有的情侣都已经可以手牵手了。
 * <p>
 * 2n == row.length
 * 2 <= n <= 30
 * n 是偶数
 * 0 <= row[i] < 2n
 * row 中所有元素均无重复
 */
public class Problem765 {
    public static void main(String[] args) {
        Problem765 problem765 = new Problem765();
        int[] row = {3, 2, 0, 1};
        System.out.println(problem765.minSwapsCouples(row));
    }

    /**
     * 并查集
     * n对情侣看做n个节点，第i对情侣中一人和第j对情侣中一人坐在一起，i和j之间相连，表示需要交换1次，
     * 即k对情侣k个节点之间相连，需要交换k-1次，图中每个连通分量中节点的个数减1，即为需要交换的次数，
     * 假设存在k个连通分量，每个连通中节点个数为n1、n2...nk，一共有N对情侣，N个节点，N=n1+n2+...+nk
     * 需要交换的次数=n1-1+n2-1+...+nk-1=N-k(需要交换次数=情侣对数-连通分量的个数)
     * 时间复杂度O(n*α(mn))=O(n)，空间复杂度O(n) (n=row.length) (find()和union()的时间复杂度为O(α(mn))，可视为常数O(1))
     *
     * @param row
     * @return
     */
    public int minSwapsCouples(int[] row) {
        //情侣对数
        int N = row.length / 2;
        UnionFind unionFind = new UnionFind(N);

        //(0,1)、(2,3)分别看做一对情侣，把(0,1)看做第0对情侣，把(2,3)看做第1对情侣
        for (int i = 0; i < row.length; i = i + 2) {
            int index1 = row[i] / 2;
            int index2 = row[i + 1] / 2;
            unionFind.union(index1, index2);
        }

        //需要交换次数=情侣对数-连通分量的个数
        return N - unionFind.count;
    }

    /**
     * 并查集(不相交数据集)类
     * 用数组的形式表示图
     */
    private static class UnionFind {
        //并查集中连通分量的个数
        private int count;
        //节点的父节点索引下标数组，从0开始存储
        private final int[] parent;
        //节点的权值数组(节点的高度)，只有一个节点的权值为1
        private final int[] weight;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            weight = new int[n];

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1;
            }
        }

        public void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI != rootJ) {
                if (weight[rootI] < weight[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (weight[rootI] > weight[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    weight[rootI]++;
                }

                //两个连通分量合并，并查集中连通分量的个数减1
                count--;
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }

            return parent[i];
        }

        public boolean isConnected(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            return rootI == rootJ;
        }
    }
}
