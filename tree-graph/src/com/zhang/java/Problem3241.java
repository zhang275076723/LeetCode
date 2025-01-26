package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2025/3/14 08:26
 * @Author zsy
 * @Description 标记所有节点需要的时间 类比Problem104 类比Problem834、Problem1838、ElevatorSchedule
 * 给你一棵 无向 树，树中节点从 0 到 n - 1 编号。
 * 同时给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ui, vi] 表示节点 ui 和 vi 在树中有一条边。
 * 一开始，所有 节点都 未标记 。对于节点 i ：
 * 当 i 是奇数时，如果时刻 x - 1 该节点有 至少 一个相邻节点已经被标记了，那么节点 i 会在时刻 x 被标记。
 * 当 i 是偶数时，如果时刻 x - 2 该节点有 至少 一个相邻节点已经被标记了，那么节点 i 会在时刻 x 被标记。
 * 请你返回一个数组 times ，表示如果你在时刻 t = 0 标记节点 i ，那么时刻 times[i] 时，树中所有节点都会被标记。
 * 请注意，每个 times[i] 的答案都是独立的，即当你标记节点 i 时，所有其他节点都未标记。
 * <p>
 * 输入：edges = [[0,1],[0,2]]
 * 输出：[2,4,3]
 * 解释：
 * 对于 i = 0 ：
 * 节点 1 在时刻 t = 1 被标记，节点 2 在时刻 t = 2 被标记。
 * 对于 i = 1 ：
 * 节点 0 在时刻 t = 2 被标记，节点 2 在时刻 t = 4 被标记。
 * 对于 i = 2 ：
 * 节点 0 在时刻 t = 2 被标记，节点 1 在时刻 t = 3 被标记。
 * <p>
 * 输入：edges = [[0,1]]
 * 输出：[1,2]
 * 解释：
 * 对于 i = 0 ：
 * 节点 1 在时刻 t = 1 被标记。
 * 对于 i = 1 ：
 * 节点 0 在时刻 t = 2 被标记。
 * <p>
 * 输入：edges = [[2,4],[0,1],[2,3],[0,2]]
 * 输出：[4,6,3,5,5]
 * 解释：
 * 对于 i = 0 ：
 * 节点 1 在时刻 t = 1 被标记，节点 2 在时刻 t = 2 被标记，节点 3 在时刻 t = 3 被标记，节点 4 在时刻 t = 4 被标记。
 * 对于 i = 1 ：
 * 节点 0 在时刻 t = 2 被标记，节点 2 在时刻 t = 4 被标记，节点 3 在时刻 t = 5 被标记，节点 4 在时刻 t = 6 被标记。
 * 对于 i = 2 ：
 * 节点 0 在时刻 t = 2 被标记，节点 1 在时刻 t = 3 被标记，节点 3 在时刻 t = 1 被标记，节点 4 在时刻 t = 2 被标记。
 * 对于 i = 3 ：
 * 节点 2 在时刻 t = 2 被标记，节点 0 在时刻 t = 4 被标记，节点 1 在时刻 t = 5 被标记，节点 4 在时刻 t = 4 被标记。
 * 对于 i = 4 ：
 * 节点 2 在时刻 t = 2 被标记，节点 0 在时刻 t = 4 被标记，节点 1 在时刻 t = 5 被标记，节点 3 在时刻 t = 3 被标记。
 * <p>
 * 2 <= n <= 10^5
 * edges.length == n - 1
 * edges[i].length == 2
 * 0 <= edges[i][0], edges[i][1] <= n - 1
 * 输入保证 edges 表示一棵合法的树。
 */
public class Problem3241 {
    public static void main(String[] args) {
        Problem3241 problem3241 = new Problem3241();
        int[][] edges = {{2, 4}, {0, 1}, {2, 3}, {0, 2}};
        System.out.println(Arrays.toString(problem3241.timeTaken(edges)));
    }

    /**
     * dfs+动态规划
     * result[i]：从节点i出发，所有节点都被标记的时间，即节点i到其他节点的最大深度
     * 通过第一次的dfs，得到每个节点为根节点的树的最大深度、第二大深度、每个节点为根节点的树的最大深度通过哪个子节点得到
     * 注意：树中的边权不都为1，例如：节点x到奇数节点y的边权为1，节点x到偶数节点y的边权为2
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param edges
     * @return
     */
    public int[] timeTaken(int[][] edges) {
        //节点的个数
        int n = edges.length + 1;
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

        //从节点i出发，所有节点都被标记的时间，即节点i到其他节点的最大深度
        int[] result = new int[n];
        //arr[i][0]：节点i为根节点的树的最大深度，arr[i][1]：节点i为根节点的树的第二大深度，
        //arr[i][2]：节点i为根节点的树的最大深度通过哪个子节点得到
        //注意：第二大深度和最大深度不在同一个子树中
        //注意：节点i为根节点的树不能改变树的结构，即不能变换原树的根节点
        int[][] maxDepthArr = new int[n][3];

        //得到每个节点为根节点的树的最大深度、第二大深度、每个节点为根节点的树的最大深度通过哪个子节点得到
        dfs(0, -1, graph, maxDepthArr);
        //得到从每个节点出发，所有节点都被标记的时间result[i]
        dfs(0, -1, 0, graph, maxDepthArr, result);

        return result;
    }

    private void dfs(int u, int parent, List<List<Integer>> graph, int[][] maxDepthArr) {
        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            dfs(v, u, graph, maxDepthArr);

            //节点u到节点v为根节点的树的最大深度
            int curDepth = maxDepthArr[v][0];

            //节点u到偶数节点v的边权为2
            if (v % 2 == 0) {
                curDepth = curDepth + 2;
            } else {
                //节点u到奇数节点v的边权为1
                curDepth++;
            }

            //更新最大深度和第二大深度
            if (curDepth > maxDepthArr[u][0]) {
                maxDepthArr[u][1] = maxDepthArr[u][0];
                maxDepthArr[u][0] = curDepth;
                maxDepthArr[u][2] = v;
            } else if (curDepth > maxDepthArr[u][1]) {
                //更新第二大深度
                maxDepthArr[u][1] = curDepth;
            }
        }
    }

    /**
     * 节点u到其他节点的最大深度为节点u为根节点的树的最大深度和节点u到节点u为根节点的树中的节点之外的节点的最大深度两者中的较大值
     * 例如：在计算result遍历到节点u时，已经得到节点u为根节点的树的最大深度maxDepthArr[u][0]，
     * 节点u到节点u为根节点的树中的节点之外的节点的最大深度exceptMaxDepth，
     * 则result[u]=max(maxDepthArr[u][0],exceptMaxDepth)，
     * 从节点u向子节点v遍历时，需要根据节点v是否是节点u最大深度的子节点，
     * 更新节点v到节点v为根节点的树中的节点之外的节点的最大深度nextExceptMaxDepth
     * <                       a
     * <                  /         \
     * <                 b            c
     * <               /   \       /     \
     * <              d     e     f       u
     * <             / \   / \   / \   /  |  \
     * <             ...   ...   ...  g   h   v
     * <                             / \ / \ / \
     * <                             ... ... ...
     *
     * @param u
     * @param parent
     * @param exceptMaxDepth 节点u到节点u为根节点的树中的节点之外的节点的最大深度
     * @param graph
     * @param maxDepthArr
     * @param result
     */
    private void dfs(int u, int parent, int exceptMaxDepth, List<List<Integer>> graph, int[][] maxDepthArr, int[] result) {
        //节点u到其他节点的最大深度为节点u为根节点的树的最大深度maxDepthArr[u][0]和节点u到节点u为根节点的树中的节点之外的节点的最大深度exceptMaxDepth两者中的较大值
        result[u] = Math.max(maxDepthArr[u][0], exceptMaxDepth);

        for (int v : graph.get(u)) {
            if (v == parent) {
                continue;
            }

            //节点v到节点v为根节点的树中的节点之外的节点的最大深度
            int nextExceptMaxDepth;

            //节点v为节点u最大深度的子节点，则考虑节点u到其他节点的第二大深度
            if (v == maxDepthArr[u][2]) {
                nextExceptMaxDepth = Math.max(exceptMaxDepth, maxDepthArr[u][1]);
            } else {
                //节点v不是节点u最大深度的子节点，则考虑节点u到其他节点的最大深度
                nextExceptMaxDepth = Math.max(exceptMaxDepth, maxDepthArr[u][0]);
            }

            //注意：是节点v到节点u的边，则考虑节点u的奇偶性
            //节点v到偶数节点u的边权为2
            if (u % 2 == 0) {
                nextExceptMaxDepth = nextExceptMaxDepth + 2;
            } else {
                //节点v到奇数节点u的边权为1
                nextExceptMaxDepth++;
            }

            dfs(v, u, nextExceptMaxDepth, graph, maxDepthArr, result);
        }
    }
}
