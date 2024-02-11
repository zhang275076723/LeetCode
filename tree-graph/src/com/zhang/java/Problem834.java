package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2024/2/2 08:57
 * @Author zsy
 * @Description 树中距离之和 类比ElevatorSchedule
 * 给定一个无向、连通的树。树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。
 * 给定整数 n 和数组 edges ， edges[i] = [ai, bi]表示树中的节点 ai 和 bi 之间有一条边。
 * 返回长度为 n 的数组 answer ，其中 answer[i] 是树中第 i 个节点与所有其他节点之间的距离之和。
 * <p>
 * 输入: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
 * 输出: [8,12,6,10,10,10]
 * 解释: 树如图所示。
 * 我们可以计算出 dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
 * 也就是 1 + 1 + 2 + 2 + 2 = 8。 因此，answer[0] = 8，以此类推。
 * <p>
 * 输入: n = 1, edges = []
 * 输出: [0]
 * <p>
 * 输入: n = 2, edges = [[1,0]]
 * 输出: [1,1]
 * <p>
 * 1 <= n <= 3 * 10^4
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= ai, bi < n
 * ai != bi
 * 给定的输入保证为有效的树
 */
public class Problem834 {
    /**
     * f中所有点对的距离之和
     */
    private int sumDistance = 0;

    public static void main(String[] args) {
        Problem834 problem834 = new Problem834();
//        int n = 6;
//        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}};
        int n = 7;
        int[][] edges = {{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}, {5, 6}};
        //[8, 12, 6, 10, 10, 10]
        System.out.println(Arrays.toString(problem834.sumOfDistancesInTree(n, edges)));
        //[8, 12, 6, 10, 10, 10]
        System.out.println(Arrays.toString(problem834.sumOfDistancesInTree2(n, edges)));
        //56
        System.out.println(problem834.f(n, edges));
        //[14, 32, 8, 22, 22, 22]
        System.out.println(Arrays.toString(problem834.f2(n, edges)));
    }

    /**
     * 暴力dfs (超时)
     * 对每一个节点dfs，得到当前节点到其他节点的距离之和
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        //邻接表
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

        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = dfs(i, -1, 0, graph);
        }

        return result;
    }

    /**
     * dfs+动态规划
     * dp[i]：节点i到其他节点的距离之和
     * count[i]：节点i为根节点的子树中所有节点的个数 (注意：节点i为根节点的子树不能改变树的节点，即不能变换原树的根节点)
     * dp[u] = dp[v] - count[u] + n - count[u] (节点v为节点u的父节点，此时已经得到dp[v]，才能正确计算dp[u])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree2(int n, int[][] edges) {
        //邻接表
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

        //节点i到其他节点的距离之和
        int[] dp = new int[n];
        //节点i为根节点的子树中所有节点的个数
        //注意：节点i为根节点的子树不能改变树的节点，即不能变换原树的根节点
        int[] count = new int[n];

        //count初始化，节点i为根节点的子树中所有节点的个数为1
        for (int i = 0; i < n; i++) {
            count[i] = 1;
        }

        //得到节点0到其他节点的距离之和dp[0]，和每个节点i为根节点的子树中所有节点的个数count[i]
        //注意：通过当前dfs，只有dp[0]为节点0到其他节点的距离之和，其他dp[i]是节点i为根节点的树中到其他节点的距离之和
        dfs(0, -1, graph, dp, count);
        //注意：通过当前dfs，更新dp[i]，得到节点i到其他节点的距离之和dp[i]
        dfs(0, -1, n, graph, dp, count);

        return dp;
    }

    /**
     * 思考题：只计算所有点对的距离之和，你能想出一个只需要一次DFS的算法吗？
     * <p>
     * 计算每一条边对距离之和的贡献值，边(u,v)，v为u的子节点，节点v为根节点的树中所有节点对边(u,v)的贡献值为count[v]*(n-count[v])，
     * 同理，非节点v为根节点的树中节点对边(u,v)的贡献值为count[v]*(n-count[v])，即边(u,v)对距离的贡献值为count[v]*(n-count[v])*2
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param edges
     * @return
     */
    public int f(int n, int[][] edges) {
        //邻接表
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

        dfs(0, -1, graph, n);

        return sumDistance;
    }

    /**
     * 思考题：把题目中的「距离之和」改成「距离的平方和」要怎么做？
     * 提示 1：换根，把「变化量的平方」这个式子展开。
     * 提示 2：除了计算子树大小，还需要计算子树中的每个节点的深度之和。
     * <p>
     * dp1[i]：节点i到其他节点的距离之和
     * dp2[i]：节点i到其他节点的距离的平方和
     * count[i]：节点i为根节点的子树中所有节点的个数 (注意：节点i为根节点的子树不能改变树的节点，即不能变换原树的根节点)
     * depth[i]：根节点到节点i的深度 (注意：确定树根节点之后，不能变换原树的根节点)
     * sumDepth[i]：根节点到节点i为根节点的子树中所有节点的深度之和 (注意：确定树根节点之后，不能变换原树的根节点)
     * dp1[u] = dp1[v] - count[u] + n - count[u] (节点v为节点u的父节点，此时已经得到dp1[v]，才能正确计算dp1[u])
     * dp2[u] = dp2[v] + n + dp1[v] - 4sumDepth[u] + 4count[u]*depth[v] (节点v为节点u的父节点，此时已经得到dp1[v]、dp2[v]、sumDepth[u]、count[u]、depth[v]，才能正确计算dp2[u])
     * 或者dp2[u] = dp2[v] - n + 2dp1[u] - 4sumDepth[u] + 4count[u]*depth[u]，也正确
     * 时间复杂度O(n)，空间复杂度O(n)
     * <p>
     * 节点v为节点u的父节点，此时已经得到dp2[v]=∑dis(i,v)^2(i为树中节点)，节点i分为以下2种情况：
     * 1、节点i为节点u为根节点的子树中节点，∑dis(i,u)^2(i为节点u为根节点的子树中节点)-∑dis(i,v)^2(i为节点u为根节点的子树中节点)
     * =∑(dis(i,v)-1)^2(i为节点u为根节点的子树中节点)-∑dis(i,v)^2(i为节点u为根节点的子树中节点)
     * =-2∑dis(i,v)(i为节点u为根节点的子树中节点)+∑(i为节点u为根节点的子树中节点)
     * =-2∑dis(i,v)(i为节点u为根节点的子树中节点)+count[u]
     * 2、节点i为非节点u为根节点的子树中节点，∑dis(i,u)^2(i为非节点u为根节点的子树中节点)-∑dis(i,v)^2(i为非节点u为根节点的子树中节点)
     * =∑(dis(i,v)+1)^2(i为非节点u为根节点的子树中节点)-∑dis(i,v)^2(i为非节点u为根节点的子树中节点)
     * =2∑dis(i,v)(i为非节点u为根节点的子树中节点)+n-count[u]
     * 上面2种情况相加得，∑dis(i,u)^2(i为节点u为根节点的子树中节点)-∑dis(i,v)^2(i为节点u为根节点的子树中节点)+∑dis(i,u)^2(i为非节点u为根节点的子树中节点)-∑dis(i,v)^2(i为非节点u为根节点的子树中节点)=-2∑dis(i,v)(i为节点u为根节点的子树中节点)+count[u]+2∑dis(i,v)(i为非节点u为根节点的子树中节点)+n-count[u]
     * dp2[u]-dp2[v]=-2∑dis(i,v)(i为节点u为根节点的子树中节点)+2∑dis(i,v)(i为非节点u为根节点的子树中节点)+n
     * dp2[u]=dp2[v]+n-2∑dis(i,v)(i为节点u为根节点的子树中节点)+2∑dis(i,v)(i为树中节点)-2∑dis(i,v)(i为节点u为根节点的子树中节点)
     * dp2[u]=dp2[v]+n+2∑dis(i,v)(i为树中节点)-4∑dis(i,v)(i为节点u为根节点的子树中节点)
     * dp2[u]=dp2[v]+n+2dp1[v]-4∑(depth[i]-depth[v])(i为节点u为根节点的子树中节点)
     * dp2[u]=dp2[v]+n+2dp1[v]-4∑depth[i](i为节点u为根节点的子树中节点)+4count[u]*depth[v]
     * dp2[u]=dp2[v]+n+2dp1[v]-4sumDepth[u]+4count[u]*depth[v]
     *
     * @param n
     * @param edges
     * @return
     */
    public int[] f2(int n, int[][] edges) {
        //邻接表
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

        //节点i到其他节点的距离之和
        int[] dp1 = new int[n];
        //节点i到其他节点的距离的平方和
        int[] dp2 = new int[n];
        //节点i为根节点的子树中所有节点的个数
        //注意：节点i为根节点的子树不能改变树的节点，即不能变换原树的根节点
        int[] count = new int[n];
        //根节点到节点i的深度
        //注意：确定树根节点之后，不能变换原树的根节点
        int[] depth = new int[n];
        //根节点到节点i为根节点的子树中所有节点的深度之和
        //注意：确定树根节点之后，不能变换原树的根节点
        int[] sumDepth = new int[n];

        //count初始化，节点i为根节点的子树中所有节点的个数为1
        for (int i = 0; i < n; i++) {
            count[i] = 1;
        }

        //得到节点i为根节点的子树中所有节点的个数count[i]，节点0为根节点到节点i的深度depth[i]，
        //节点0为根节点到节点i为根节点的子树中所有节点的深度之和sumDepth[i]
        dfs(0, -1, graph, count, depth, sumDepth);

        //dp1[0]初始化
        dp1[0] = sumDepth[0];

        //dp2[0]初始化
        for (int i = 0; i < n; i++) {
            dp2[0] = dp2[0] + depth[i] * depth[i];
        }

        //更新dp1[i]、dp2[i]，得到节点i到其他节点的距离之和dp1[i]，节点i到其他节点的距离的平方和dp2[i]
        dfs(0, -1, n, graph, dp1, dp2, count, depth, sumDepth);

        return dp2;
    }

    /**
     * 起始节点到其他节点的距离之和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u        当前节点u
     * @param parent   节点u的父节点，记录节点u是从哪个节点遍历过来，避免重复遍历
     * @param distance 起始节点到当前节点u的距离
     * @param graph
     * @return
     */
    private int dfs(int u, int parent, int distance, List<List<Integer>> graph) {
        //当前节点u为根节点的树到起始节点的距离
        int curDistance = distance;

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            curDistance = curDistance + dfs(v, u, distance + 1, graph);
        }

        return curDistance;
    }

    /**
     * 得到节点0到其他节点的距离之和dp[0]，和每个节点i为根节点的子树中所有节点的个数count[i]
     * 注意：通过当前dfs，只有dp[0]为节点0到其他节点的距离之和，其他dp[i]是节点i为根节点的树中到其他节点的距离之和
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param parent
     * @param graph
     * @param dp
     * @param count
     */
    private void dfs(int u, int parent, List<List<Integer>> graph, int[] dp, int[] count) {
        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            //注意：要先dfs，才能更新count[u]和dp[u]
            dfs(v, u, graph, dp, count);

            count[u] = count[u] + count[v];
            //注意：要先得到count[u]，才能计算dp[u]
            dp[u] = dp[u] + dp[v] + count[v];
        }
    }

    /**
     * 更新dp[i]，得到节点i到其他节点的距离之和dp[i]
     * dp[u] = dp[v] - count[u] + n - count[u] (节点v为节点u的父节点，此时已经得到dp[v]，才能正确计算dp[u])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param parent
     * @param n
     * @param graph
     * @param dp
     * @param count
     */
    private void dfs(int u, int parent, int n, List<List<Integer>> graph, int[] dp, int[] count) {
        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            //从节点u到节点v，有count[v]个节点少走一步，有n-count[v]个节点多走一步
            //注意：要先更新dp[v]，才能dfs，此时已经得到dp[u]，才能正确计算dp[v]
            dp[v] = dp[u] - count[v] + n - count[v];

            dfs(v, u, n, graph, dp, count);
        }
    }

    /**
     * 返回节点u为根节点的树中所有节点的个数，同时计算当前边(u,v)对距离的贡献值
     * 计算每一条边对距离之和的贡献值，边(u,v)，v为u的子节点，节点v为根节点的树中所有节点对边(u,v)的贡献值为count[v]*(n-count[v])，
     * 同理，非节点v为根节点的树中节点对边(u,v)的贡献值为count[v]*(n-count[v])，即边(u,v)对距离的贡献值为count[v]*(n-count[v])*2
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param parent
     * @param graph
     * @param n
     * @return
     */
    private int dfs(int u, int parent, List<List<Integer>> graph, int n) {
        //节点u为根节点的树中所有节点的个数，初始化为1，即只有节点u一个节点的树
        int count = 1;

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            count = count + dfs(v, u, graph, n);
        }

        sumDistance = sumDistance + count * (n - count) * 2;

        return count;
    }

    /**
     * 得到节点i为根节点的子树中所有节点的个数count[i]，节点0为根节点到节点i的深度depth[i]，
     * 节点0为根节点到节点i为根节点的子树中所有节点的深度之和sumDepth[i]
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param parent
     * @param graph
     * @param count
     * @param depth
     * @param sumDepth
     */
    private void dfs(int u, int parent, List<List<Integer>> graph, int[] count, int[] depth, int[] sumDepth) {
        //节点u为根节点的子树中所有节点的深度之和，需要先加上节点u的深度
        sumDepth[u] = sumDepth[u] + depth[u];

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            //在dfs之前更新depth[v]
            depth[v] = depth[u] + 1;

            dfs(v, u, graph, count, depth, sumDepth);

            //在dfs之后更新count[u]和sumDepth[u]
            count[u] = count[u] + count[v];
            sumDepth[u] = sumDepth[u] + sumDepth[v];
        }
    }

    /**
     * 更新dp1[i]、dp2[i]，得到节点i到其他节点的距离之和dp1[i]，节点i到其他节点的距离的平方和dp2[i]
     * dp1[u] = dp1[v] - count[u] + n - count[u] (节点v为节点u的父节点，此时已经得到dp1[v]，才能正确计算dp1[u])
     * dp2[u] = dp2[v] + n + dp1[v] - 4sumDepth[u] + 4count[u]*depth[v] (节点v为节点u的父节点，此时已经得到dp1[v]、dp2[v]、sumDepth[u]、count[u]、depth[v]，才能正确计算dp2[u])
     * 或者dp2[u] = dp2[v] - n + 2dp1[u] - 4sumDepth[u] + 4count[u]*depth[u]，也正确
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param u
     * @param parent
     * @param n
     * @param graph
     * @param dp1
     * @param dp2
     * @param count
     * @param depth
     * @param sumDepth
     */
    private void dfs(int u, int parent, int n, List<List<Integer>> graph, int[] dp1, int[] dp2, int[] count, int[] depth, int[] sumDepth) {
        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            //在dfs之前更新dp1[v]和dp2[v]
            dp1[v] = dp1[u] - count[v] + n - count[v];
            //对∑dis(i,u)^2转换为∑(dis(i,v)±1)^2，则得到以下公式
            dp2[v] = dp2[u] + n + 2 * dp1[u] - 4 * sumDepth[v] + 4 * count[v] * depth[u];

//            //注意：对∑dis(i,v)^2转换为∑(dis(i,u)±1)^2，则得到以下公式，两者都正确
//            dp2[v] = dp2[u] - n + 2 * dp1[v] - 4 * sumDepth[v] + 4 * count[v] * depth[v];

            dfs(v, u, n, graph, dp1, dp2, count, depth, sumDepth);
        }
    }
}
