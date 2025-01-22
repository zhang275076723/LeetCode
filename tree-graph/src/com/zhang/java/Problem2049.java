package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/3/10 08:24
 * @Author zsy
 * @Description 统计最高分的节点数目 类比Problem450、Problem1110
 * 给你一棵根节点为 0 的 二叉树 ，它总共有 n 个节点，节点编号为 0 到 n - 1 。
 * 同时给你一个下标从 0 开始的整数数组 parents 表示这棵树，其中 parents[i] 是节点 i 的父节点。
 * 由于节点 0 是根，所以 parents[0] == -1 。
 * 一个子树的 大小 为这个子树内节点的数目。
 * 每个节点都有一个与之关联的 分数 。
 * 求出某个节点分数的方法是，将这个节点和与它相连的边全部 删除 ，剩余部分是若干个 非空 子树，
 * 这个节点的 分数 为所有这些子树 大小的乘积 。
 * 请你返回有 最高得分 节点的 数目 。
 * <p>
 * 输入：parents = [-1,2,0,2,0]
 * 输出：3
 * 解释：
 * - 节点 0 的分数为：3 * 1 = 3
 * - 节点 1 的分数为：4 = 4
 * - 节点 2 的分数为：1 * 1 * 2 = 2
 * - 节点 3 的分数为：4 = 4
 * - 节点 4 的分数为：4 = 4
 * 最高得分为 4 ，有三个节点得分为 4 （分别是节点 1，3 和 4 ）。
 * <p>
 * 输入：parents = [-1,2,0]
 * 输出：2
 * 解释：
 * - 节点 0 的分数为：2 = 2
 * - 节点 1 的分数为：2 = 2
 * - 节点 2 的分数为：1 * 1 = 1
 * 最高分数为 2 ，有两个节点分数为 2 （分别为节点 0 和 1 ）。
 * <p>
 * n == parents.length
 * 2 <= n <= 10^5
 * parents[0] == -1
 * 对于 i != 0 ，有 0 <= parents[i] <= n - 1
 * parents 表示一棵二叉树。
 */
public class Problem2049 {
    /**
     * 树中节点的最大分数
     * 使用long，避免int相乘溢出
     */
    private long maxScore = 0;

    /**
     * 树中节点的最大分数的个数
     */
    private int maxScoreCount = 0;

    public static void main(String[] args) {
        Problem2049 problem2049 = new Problem2049();
        int[] parents = {-1, 2, 0, 2, 0};
        System.out.println(problem2049.countHighestScoreNodes(parents));
    }

    /**
     * dfs
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param parents
     * @return
     */
    public int countHighestScoreNodes(int[] parents) {
        //节点的个数
        int n = parents.length;
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        //i从1开始遍历，不需要考虑节点0的父节点
        for (int i = 1; i < n; i++) {
            graph.get(parents[i]).add(i);
        }

        //节点i为根节点的树中所有节点的个数
        int[] count = new int[n];

        dfs(0, graph, count);

        return maxScoreCount;
    }

    private int dfs(int node, List<List<Integer>> graph, int[] count) {
        //当前节点的分数，即删除和当前节点相连的边，得到的非空子树中节点的个数乘积
        //使用long，避免int相乘溢出
        long score = 1;
        //count初始化
        count[node] = 1;

        for (int child : graph.get(node)) {
            count[node] = count[node] + dfs(child, graph, count);
            //删除当前节点和子节点的边，count[child]即为非空子树中节点的个数
            score = score * count[child];
        }

        //删除当前节点和父节点的边，(n-count[node])>0，则存在非空父节点所在子树，(n-count[node])即为非空父节点所在子树中节点的个数
        if (count.length - count[node] > 0) {
            score = score * (count.length - count[node]);
        }

        if (score > maxScore) {
            maxScore = score;
            maxScoreCount = 1;
        } else if (score == maxScore) {
            maxScoreCount++;
        }

        return count[node];
    }
}
