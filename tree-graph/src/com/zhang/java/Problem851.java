package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/5 08:26
 * @Author zsy
 * @Description 喧闹和富有 拓扑排序类比
 * 有一组 n 个人作为实验对象，从 0 到 n - 1 编号，其中每个人都有不同数目的钱，以及不同程度的安静值（quietness）。
 * 为了方便起见，我们将编号为 x 的人简称为 "person x "。
 * 给你一个数组 richer ，其中 richer[i] = [ai, bi] 表示 person ai 比 person bi 更有钱。
 * 另给你一个整数数组 quiet ，其中 quiet[i] 是 person i 的安静值。
 * richer 中所给出的数据 逻辑自洽
 * （也就是说，在 person x 比 person y 更有钱的同时，不会出现 person y 比 person x 更有钱的情况 ）。
 * 现在，返回一个整数数组 answer 作为答案，其中 answer[x] = y 的前提是，
 * 在所有拥有的钱肯定不少于 person x 的人中，person y 是最安静的人（也就是安静值 quiet[y] 最小的人）。
 * <p>
 * 输入：richer = [[1,0],[2,1],[3,1],[3,7],[4,3],[5,3],[6,3]], quiet = [3,2,5,4,6,1,7,0]
 * 输出：[5,5,2,5,4,5,6,7]
 * 解释：
 * answer[0] = 5，
 * person 5 比 person 3 有更多的钱，person 3 比 person 1 有更多的钱，person 1 比 person 0 有更多的钱。
 * 唯一较为安静（有较低的安静值 quiet[x]）的人是 person 7，
 * 但是目前还不清楚他是否比 person 0 更有钱。
 * answer[7] = 7，
 * 在所有拥有的钱肯定不少于 person 7 的人中（这可能包括 person 3，4，5，6 以及 7），
 * 最安静（有较低安静值 quiet[x]）的人是 person 7。
 * 其他的答案也可以用类似的推理来解释。
 * <p>
 * 输入：richer = [], quiet = [0]
 * 输出：[0]
 * <p>
 * n == quiet.length
 * 1 <= n <= 500
 * 0 <= quiet[i] < n
 * quiet 的所有值 互不相同
 * 0 <= richer.length <= n * (n - 1) / 2
 * 0 <= ai, bi < n
 * ai != bi
 * richer 中的所有数对 互不相同
 * 对 richer 的观察在逻辑上是一致的
 */
public class Problem851 {
    public static void main(String[] args) {
        Problem851 problem851 = new Problem851();
        int[][] richer = {{1, 0}, {2, 1}, {3, 1}, {3, 7}, {4, 3}, {5, 3}, {6, 3}};
        int[] quiet = {3, 2, 5, 4, 6, 1, 7, 0};
        System.out.println(Arrays.toString(problem851.loudAndRich(richer, quiet)));
    }

    /**
     * bfs拓扑排序
     * 找比当前节点更有钱的节点中安静值最小的节点
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (m=richer.length，n=quiet.length)
     *
     * @param richer
     * @param quiet
     * @return
     */
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        //节点的个数
        int n = quiet.length;

        //邻接表，有向图
        List<List<Integer>> edges = new ArrayList<>();
        //入度数组
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            edges.add(new ArrayList<>());
        }

        for (int i = 0; i < richer.length; i++) {
            int u = richer[i][0];
            int v = richer[i][1];

            edges.get(u).add(v);
            inDegree[v]++;
        }

        Queue<Integer> queue = new LinkedList<>();

        //入度为0的节点入队
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        //结果数组，比当前节点更有钱的节点中安静值最小的节点
        int[] result = new int[n];

        //节点i初始化，节点i是比当前节点i更有钱的节点中安静值最小的节点
        for (int i = 0; i < n; i++) {
            result[i] = i;
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();

            //节点u的邻接节点v
            for (int v : edges.get(u)) {
                //节点u到节点v的边中，存在比节点v更有钱的节点result[u]，并且result[u]安静值小于result[v]，则更新result[v]
                if (quiet[result[u]] < quiet[result[v]]) {
                    result[v] = result[u];
                }

                inDegree[v]--;

                //入度为0的节点入队
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        return result;
    }
}
