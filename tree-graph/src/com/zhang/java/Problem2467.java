package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2025/3/15 08:29
 * @Author zsy
 * @Description 树上最大得分和路径
 * 一个 n 个节点的无向树，节点编号为 0 到 n - 1 ，树的根结点是 0 号节点。
 * 给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] ，表示节点 ai 和 bi 在树中有一条边。
 * 在每一个节点 i 处有一扇门。同时给你一个都是偶数的数组 amount ，其中 amount[i] 表示：
 * 如果 amount[i] 的值是负数，那么它表示打开节点 i 处门扣除的分数。
 * 如果 amount[i] 的值是正数，那么它表示打开节点 i 处门加上的分数。
 * 游戏按照如下规则进行：
 * 一开始，Alice 在节点 0 处，Bob 在节点 bob 处。
 * 每一秒钟，Alice 和 Bob 分别 移动到相邻的节点。Alice 朝着某个 叶子结点 移动，Bob 朝着节点 0 移动。
 * 对于他们之间路径上的 每一个 节点，Alice 和 Bob 要么打开门并扣分，要么打开门并加分。注意：
 * 如果门 已经打开 （被另一个人打开），不会有额外加分也不会扣分。
 * 如果 Alice 和 Bob 同时 到达一个节点，他们会共享这个节点的加分或者扣分。
 * 换言之，如果打开这扇门扣 c 分，那么 Alice 和 Bob 分别扣 c / 2 分。如果这扇门的加分为 c ，那么他们分别加 c / 2 分。
 * 如果 Alice 到达了一个叶子结点，她会停止移动。类似的，如果 Bob 到达了节点 0 ，他也会停止移动。
 * 注意这些事件互相 独立 ，不会影响另一方移动。
 * 请你返回 Alice 朝最优叶子结点移动的 最大 净得分。
 * <p>
 * 输入：edges = [[0,1],[1,2],[1,3],[3,4]], bob = 3, amount = [-2,4,2,-4,6]
 * 输出：6
 * 解释：
 * 上图展示了输入给出的一棵树。游戏进行如下：
 * - Alice 一开始在节点 0 处，Bob 在节点 3 处。他们分别打开所在节点的门。
 * Alice 得分为 -2 。
 * - Alice 和 Bob 都移动到节点 1 。
 * 因为他们同时到达这个节点，他们一起打开门并平分得分。
 * Alice 的得分变为 -2 + (4 / 2) = 0 。
 * - Alice 移动到节点 3 。因为 Bob 已经打开了这扇门，Alice 得分不变。
 * Bob 移动到节点 0 ，并停止移动。
 * - Alice 移动到节点 4 并打开这个节点的门，她得分变为 0 + 6 = 6 。
 * 现在，Alice 和 Bob 都不能进行任何移动了，所以游戏结束。
 * Alice 无法得到更高分数。
 * <p>
 * 输入：edges = [[0,1]], bob = 1, amount = [-7280,2350]
 * 输出：-7280
 * 解释：
 * Alice 按照路径 0->1 移动，同时 Bob 按照路径 1->0 移动。
 * 所以 Alice 只打开节点 0 处的门，她的得分为 -7280 。
 * <p>
 * 2 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * ai != bi
 * edges 表示一棵有效的树。
 * 1 <= bob < n
 * amount.length == n
 * amount[i] 是范围 [-10^4, 10^4] 之间的一个 偶数 。
 */
public class Problem2467 {
    /**
     * 在bob的影响下，Alice从节点0出发到叶节点的最大分数
     */
    private int maxScore = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Problem2467 problem2467 = new Problem2467();
        int[][] edges = {{0, 1}, {1, 2}, {1, 3}, {3, 4}};
        int bob = 3;
        int[] amount = {-2, 4, 2, -4, 6};
        System.out.println(problem2467.mostProfitablePath(edges, bob, amount));
    }

    /**
     * dfs
     * 1、dfs得到节点bob到节点0的路径
     * 注意：只记录经过的路径中每个节点的时间，不记录没有经过的节点的时间
     * 2、dfs得到在bob的影响下，Alice从节点0出发到叶节点的最大分数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @param bob
     * @param amount
     * @return
     */
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        //节点的个数
        int n = amount.length;
        //邻接表，无向图
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];

            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //节点bob到节点0路径中，bob经过每个节点的时间
        int[] bobTimeArr = new int[n];

        for (int i = 0; i < n; i++) {
            bobTimeArr[i] = -1;
        }

        //dfs得到节点bob到节点0的路径
        //注意：只记录经过的路径中每个节点的时间，不记录没有经过的节点的时间
        dfs(bob, -1, 0, graph, bobTimeArr);
        //dfs得到在bob的影响下，Alice从节点0出发到叶节点的最大分数
        dfs(0, -1, 0, 0, graph, bobTimeArr, amount);

        return maxScore;
    }

    /**
     * 节点u是否在节点bob到节点0的路径
     *
     * @param u
     * @param parent
     * @param time
     * @param graph
     * @param bobTimeArr
     */
    private boolean dfs(int u, int parent, int time, List<List<Integer>> graph, int[] bobTimeArr) {
        //节点0是节点bob到节点0的路径中的节点，返回true
        if (u == 0) {
            bobTimeArr[u] = time;
            return true;
        }

        //节点u是否在节点bob到节点0的路径标志位
        boolean flag = false;

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            if (dfs(v, u, time + 1, graph, bobTimeArr)) {
                flag = true;
                break;
            }
        }

        //只记录经过的路径中每个节点的时间，不记录没有经过的节点的时间
        if (flag) {
            bobTimeArr[u] = time;
        }

        return flag;
    }

    private void dfs(int u, int parent, int time, int score, List<List<Integer>> graph, int[] bobTimeArr, int[] amount) {
        //当前节点u是否是叶节点标志位
        boolean flag = true;

        //bob无法访问到节点u，则Alice获取节点u的分数
        if (bobTimeArr[u] == -1) {
            score = score + amount[u];
        } else {
            //Alice比bob先访问到节点u，则Alice获取节点u的分数
            if (time < bobTimeArr[u]) {
                score = score + amount[u];
            } else if (time == bobTimeArr[u]) {
                //Alice和bob同时访问到节点u，则Alice获取节点u一半的分数
                score = score + amount[u] / 2;
            } else {
                //Alice比bob后访问到节点u，则Alice不能获取节点u的分数
            }
        }

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            flag = false;
            dfs(v, u, time + 1, score, graph, bobTimeArr, amount);
        }

        //节点u为叶节点，才更新maxScore
        if (flag) {
            maxScore = Math.max(maxScore, score);
        }
    }
}
