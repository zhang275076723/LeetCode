package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2023/10/16 08:29
 * @Author zsy
 * @Description 参加会议的最多员工数 拓扑排序类比 图类比
 * 一个公司准备组织一场会议，邀请名单上有 n 位员工。
 * 公司准备了一张 圆形 的桌子，可以坐下 任意数目 的员工。
 * 员工编号为 0 到 n - 1。
 * 每位员工都有一位 喜欢 的员工，每位员工 当且仅当 他被安排在喜欢员工的旁边，他才会参加会议。
 * 每位员工喜欢的员工 不会 是他自己。
 * 给你一个下标从 0 开始的整数数组 favorite ，其中 favorite[i] 表示第 i 位员工喜欢的员工。
 * 请你返回参加会议的 最多员工数目 。
 * <p>
 * 输入：favorite = [2,2,1,2]
 * 输出：3
 * 解释：
 * 上图展示了公司邀请员工 0，1 和 2 参加会议以及他们在圆桌上的座位。
 * 没办法邀请所有员工参与会议，因为员工 2 没办法同时坐在 0，1 和 3 员工的旁边。
 * 注意，公司也可以邀请员工 1，2 和 3 参加会议。
 * 所以最多参加会议的员工数目为 3 。
 * <p>
 * 输入：favorite = [1,2,0]
 * 输出：3
 * 解释：
 * 每个员工都至少是另一个员工喜欢的员工。所以公司邀请他们所有人参加会议的前提是所有人都参加了会议。
 * 座位安排同图 1 所示：
 * - 员工 0 坐在员工 2 和 1 之间。
 * - 员工 1 坐在员工 0 和 2 之间。
 * - 员工 2 坐在员工 1 和 0 之间。
 * 参与会议的最多员工数目为 3 。
 * <p>
 * 输入：favorite = [3,0,1,4,1]
 * 输出：4
 * 解释：
 * 上图展示了公司可以邀请员工 0，1，3 和 4 参加会议以及他们在圆桌上的座位。
 * 员工 2 无法参加，因为他喜欢的员工 0 旁边的座位已经被占领了。
 * 所以公司只能不邀请员工 2 。
 * 参加会议的最多员工数目为 4 。
 * <p>
 * n == favorite.length
 * 2 <= n <= 10^5
 * 0 <= favorite[i] <= n - 1
 * favorite[i] != i
 */
public class Problem2127 {
    public static void main(String[] args) {
        Problem2127 problem2127 = new Problem2127();
        int[] favorite = {1, 0, 0, 2, 2, 4, 1};
        System.out.println(problem2127.maximumInvitations(favorite));
    }

    /**
     * bfs拓扑排序+动态规划+dfs/bfs计算环中节点的个数
     * dp[i]：以节点i作为结尾节点的最长链的节点之和
     * dp[i] = max(dp[i],dp[j]+1) (存在节点j到节点i的边)
     * 基环树：n个节点n条边的无向连通图，基环树有且仅有一个环
     * 节点1喜欢节点2，则存在一条节点1到节点2的边，根据基环树中环的大小，分为以下2种情况：
     * 1、基环树中大小为2的环，则该基环树能组成圆桌的最大员工个数为以环上节点作为结尾节点的最长链的节点个数之和，即这两个最长链节点个数之和
     * 2、基环树中大小大于2的环，则该基环树能组成圆桌的最大员工个数为环上的节点个数
     * 则圆桌的最大员工个数为max(情况1之和,情况2的最大值)
     * bfs拓扑排序删除不是环的节点，并且在bfs拓扑排序过程中得到以当前节点作为结尾节点的最长链的节点之和，
     * dfs或bfs得到每个环中节点的个数
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param favorite
     * @return
     */
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        //入度数组
        int[] inDegree = new int[n];
        //以当前节点作为结尾节点的最长链的节点之和数组
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            inDegree[favorite[i]]++;
            //dp初始化
            dp[i] = 1;
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();
        //节点访问数组，bfs拓扑排序之后用于判断哪些节点是环中节点
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            visited[u] = true;
            //u的邻接顶点v，即存在u到v的边
            int v = favorite[u];
            inDegree[v]--;

            if (inDegree[v] == 0) {
                queue.offer(v);
            }

            //更新以节点v作为结尾节点的最长链的节点之和
            dp[v] = Math.max(dp[v], dp[u] + 1);
        }

        //情况1，即基环树中大小为2的环，以环上节点作为结尾节点的最长链的节点个数之和，即这两个最长链节点个数之和
        int count1 = 0;
        //情况2，即基环树中大小大于2的环，环上的节点个数的最大值
        int count2 = 0;

        for (int i = 0; i < n; i++) {
            //为访问的节点即为环中的节点
            if (!visited[i]) {
                //当前环中的节点集合
                List<Integer> list = new ArrayList<>();
                //当前环的大小
                int count = dfs(i, favorite, visited, list);
//                int count = bfs(i, favorite, visited, list);

                //情况1，环中只有2个节点
                if (count == 2) {
                    count1 = count1 + dp[list.get(0)] + dp[list.get(1)];
                } else if (count > 2) {
                    //情况2，环中节点个数超过2个
                    count2 = Math.max(count2, count);
                }
            }
        }

        //两种情况的较大值即为圆桌的最大员工个数
        return Math.max(count1, count2);
    }

    /**
     * dfs计算环中节点的个数，并将环中的节点保存到list中
     *
     * @param i
     * @param favorite
     * @param visited
     * @param list
     * @return
     */
    private int dfs(int i, int[] favorite, boolean[] visited, List<Integer> list) {
        //当前节点已经访问过，则已经找到了环中节点的个数，返回0
        if (visited[i]) {
            return 0;
        }

        int count = 1;
        visited[i] = true;
        list.add(i);

        //继续沿着邻接顶点找环中节点的个数
        count = count + dfs(favorite[i], favorite, visited, list);

        return count;
    }

    /**
     * bfs计算环中节点的个数，并将环中的节点保存到list中
     *
     * @param i
     * @param favorite
     * @param visited
     * @param list
     * @return
     */
    private int bfs(int i, int[] favorite, boolean[] visited, List<Integer> list) {
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);

        while (!queue.isEmpty()) {
            int j = queue.poll();

            if (visited[j]) {
                continue;
            }

            visited[j] = true;
            count++;
            list.add(j);

            queue.offer(favorite[j]);
        }

        return count;
    }
}
