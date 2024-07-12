package com.zhang.java;

/**
 * @Date 2023/10/11 08:23
 * @Author zsy
 * @Description 搜寻名人 类比Problem134、Problem1386
 * 假设你是一个专业的狗仔，参加了一个 n 人派对，其中每个人被从 0 到 n - 1 标号。
 * 在这个派对人群当中可能存在一位 “名人”。
 * 所谓 “名人” 的定义是：其他所有 n - 1 个人都认识他/她，而他/她并不认识其他任何人。
 * 现在你想要确认这个 “名人” 是谁，或者确定这里没有 “名人”。
 * 而你唯一能做的就是问诸如 “A 你好呀，请问你认不认识 B呀？” 的问题，以确定 A 是否认识 B。
 * 你需要在（渐近意义上）尽可能少的问题内来确定这位 “名人” 是谁（或者确定这里没有 “名人”）。
 * 在本题中，你可以使用辅助函数 bool knows(a, b) 获取到 A 是否认识 B。
 * 请你来实现一个函数 int findCelebrity(n)。
 * 派对最多只会有一个 “名人” 参加。
 * 若 “名人” 存在，请返回他/她的编号；若 “名人” 不存在，请返回 -1。
 * <p>
 * 输入: graph = [
 * [1,1,0],
 * [0,1,0],
 * [1,1,1]
 * ]
 * 输出: 1
 * 解释: 有编号分别为 0、1 和 2 的三个人。graph[i][j] = 1 代表编号为 i 的人认识编号为 j 的人，
 * 而 graph[i][j] = 0 则代表编号为 i 的人不认识编号为 j 的人。
 * “名人” 是编号 1 的人，因为 0 和 2 均认识他/她，但 1 不认识任何人。
 * <p>
 * 输入: graph = [
 * [1,0,1],
 * [1,1,0],
 * [0,1,1]
 * ]
 * 输出: -1
 * 解释: 没有 “名人”
 * <p>
 * n == graph.length
 * n == graph[i].length
 * 2 <= n <= 100
 * graph[i][j] 是 0 或 1.
 * graph[i][i] == 1
 */
public class Problem277 {
    public static void main(String[] args) {
        Problem277 problem277 = new Problem277();
        int n = 3;
        int[][] graph = {{1, 1, 0}, {0, 1, 0}, {1, 1, 1}};
//        int[][] graph = {{1, 0, 1}, {1, 1, 0}, {0, 1, 1}};
        System.out.println(problem277.findCelebrity(n, graph));
        System.out.println(problem277.findCelebrity2(n, graph));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param n
     * @param graph
     * @return
     */
    public int findCelebrity(int n, int[][] graph) {
        for (int i = 0; i < n; i++) {
            //i是否是名人节点标志位
            boolean flag = true;

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    //j不认识i，或者i认识j，则i不是名人节点
                    if (!knows(j, i, graph) && knows(i, j, graph)) {
                        flag = false;
                        break;
                    }
                }
            }

            if (flag) {
                return i;
            }
        }

        //遍历结束没有找到名人节点，则不存在名人节点，返回-1
        return -1;
    }

    /**
     * 模拟
     * 核心思想：名人最多只能存在1个，先确定候选名人，在判断候选名人是否真的是名人
     * 从0到n-1遍历一遍，确定候选名人节点，判断其他节点是否都指向候选名人节点，并且候选名人节点不指向其他节点
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param graph
     * @return
     */
    public int findCelebrity2(int n, int[][] graph) {
        //候选名人节点
        int candidate = 0;

        //确定候选名人节点，当前候选名人认识节点i，则更新候选名人为节点i
        for (int i = 1; i < n; i++) {
            if (knows(candidate, i, graph)) {
                candidate = i;
            }
        }

        //判断候选名人节点是否是名人节点
        for (int i = 0; i < n; i++) {
            if (i != candidate) {
                //i不认识candidate，或者candidate认识i，则candidate不是名人节点，返回-1
                if (!knows(i, candidate, graph) || knows(candidate, i, graph)) {
                    return -1;
                }
            }
        }

        return candidate;
    }

    /**
     * 节点u的人是否认识节点v的人，即是否存在u到v的边
     *
     * @param u
     * @param v
     * @param graph
     * @return
     */
    private boolean knows(int u, int v, int[][] graph) {
        return graph[u][v] == 1;
    }
}
