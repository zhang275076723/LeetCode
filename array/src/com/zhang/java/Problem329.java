package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/8/18 9:07
 * @Author zsy
 * @Description 矩阵中的最长递增路径 回溯+预处理类比Problem131、Problem132、Problem139、Problem140、Problem403 拓扑排序类比Problem207、Problem210、Problem310、IsCircleDependency 图类比Problem133、Problem207、Problem210、Problem399、Problem785、Problem863 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem509、Problem1340、Problem1388、Problem1444、Offer10、Offer10_2
 * 给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
 * 对于每个单元格，你可以往上，下，左，右四个方向移动。
 * 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。
 * <p>
 * 输入：matrix = [[9,9,4],[6,6,8],[2,1,1]]
 * 输出：4
 * 解释：最长递增路径为 [1, 2, 6, 9]。
 * <p>
 * 输入：matrix = [[3,4,5],[3,2,6],[2,2,1]]
 * 输出：4
 * 解释：最长递增路径是 [3, 4, 5, 6]。注意不允许在对角线方向上移动。
 * <p>
 * 输入：matrix = [[1]]
 * 输出：1
 * <p>
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * 0 <= matrix[i][j] <= 2^31 - 1
 */
public class Problem329 {
    public static void main(String[] args) {
        Problem329 problem329 = new Problem329();
        int[][] matrix = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        System.out.println(problem329.longestIncreasingPath(matrix));
        System.out.println(problem329.longestIncreasingPath2(matrix));
    }

    /**
     * 递归+记忆化搜索
     * dp[i][j]：以matrix[i][j]起始的最长递增路径长度
     * dp[i][j] = max(dp[i-1][j],dp[i+1][j],dp[i][j-1],dp[i][j+1]) + 1 (dp[i][j] < dp[i-1][j],dp[i+1][j],dp[i][j-1],dp[i][j+1])
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        int maxLen = 1;
        int[][] dp = new int[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                maxLen = Math.max(maxLen, dfs(i, j, matrix, dp));
            }
        }

        return maxLen;
    }

    /**
     * 拓扑排序
     * 将matrix看做图，图中两个节点的指向是相邻节点中较小值指向较大值，
     * 入度为0的节点入队列，队列中节点出队，每次删除当前层中的所有节点相连的边，即对应节点的入度减1，
     * 如果存在新的入度为1的节点，则入队，直至队列为空，则bfs扩展的层数即为最长递增路径的长度
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath2(int[][] matrix) {
        //入度数组
        int[][] inDegree = new int[matrix.length][matrix[0].length];

        //图中节点入度赋值
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i - 1 >= 0 && matrix[i][j] > matrix[i - 1][j]) {
                    inDegree[i][j]++;
                }

                if (i + 1 < matrix.length && matrix[i][j] > matrix[i + 1][j]) {
                    inDegree[i][j]++;
                }

                if (j - 1 >= 0 && matrix[i][j] > matrix[i][j - 1]) {
                    inDegree[i][j]++;
                }

                if (j + 1 < matrix[0].length && matrix[i][j] > matrix[i][j + 1]) {
                    inDegree[i][j]++;
                }
            }
        }

        Queue<int[]> queue = new LinkedList<>();

        //入度为0的节点入队
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (inDegree[i][j] == 0) {
                    //arr[0]：当前行，arr[1]：当前列
                    queue.offer(new int[]{i, j});
                }
            }
        }

        //最长递增路径的长度
        int maxLen = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，每层删除当前层中的所有节点相连的边，即对应节点的入度减1，如果存在新的入度为1的节点，则入队
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();

                //当前节点往上找入度为0的节点加入队列中
                if (arr[0] - 1 >= 0 && matrix[arr[0]][arr[1]] < matrix[arr[0] - 1][arr[1]]) {
                    inDegree[arr[0] - 1][arr[1]]--;
                    if (inDegree[arr[0] - 1][arr[1]] == 0) {
                        queue.offer(new int[]{arr[0] - 1, arr[1]});
                    }
                }

                ///当前节点往下找入度为0的节点加入队列中
                if (arr[0] + 1 < matrix.length && matrix[arr[0]][arr[1]] < matrix[arr[0] + 1][arr[1]]) {
                    inDegree[arr[0] + 1][arr[1]]--;
                    if (inDegree[arr[0] + 1][arr[1]] == 0) {
                        queue.offer(new int[]{arr[0] + 1, arr[1]});
                    }
                }

                ///当前节点往左找入度为0的节点加入队列中
                if (arr[1] - 1 >= 0 && matrix[arr[0]][arr[1]] < matrix[arr[0]][arr[1] - 1]) {
                    inDegree[arr[0]][arr[1] - 1]--;
                    if (inDegree[arr[0]][arr[1] - 1] == 0) {
                        queue.offer(new int[]{arr[0], arr[1] - 1});
                    }
                }

                ///当前节点往右找入度为0的节点加入队列中
                if (arr[1] + 1 < matrix[0].length && matrix[arr[0]][arr[1]] < matrix[arr[0]][arr[1] + 1]) {
                    inDegree[arr[0]][arr[1] + 1]--;
                    if (inDegree[arr[0]][arr[1] + 1] == 0) {
                        queue.offer(new int[]{arr[0], arr[1] + 1});
                    }
                }
            }

            //maxLen加1，表示bfs每次往外扩一层
            maxLen++;
        }

        return maxLen;
    }

    /**
     * 返回以matrix[i][j]开始的最长递增路径的长度
     *
     * @param i
     * @param j
     * @param matrix
     * @param dp
     * @return
     */
    private int dfs(int i, int j, int[][] matrix, int[][] dp) {
        //matrix[i][j]超过数组范围，或已经找到以matrix[i][j]开始的最长递增路径长度，直接返回
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        //以matrix[i][j]开始的最长递增路径长度，赋初值为1
        dp[i][j] = 1;

        //往上找
        if (i - 1 >= 0 && matrix[i][j] < matrix[i - 1][j]) {
            dp[i][j] = Math.max(dp[i][j], dfs(i - 1, j, matrix, dp) + 1);
        }

        //往下找
        if (i + 1 < matrix.length && matrix[i][j] < matrix[i + 1][j]) {
            dp[i][j] = Math.max(dp[i][j], dfs(i + 1, j, matrix, dp) + 1);
        }

        //往左找
        if (j - 1 >= 0 && matrix[i][j] < matrix[i][j - 1]) {
            dp[i][j] = Math.max(dp[i][j], dfs(i, j - 1, matrix, dp) + 1);
        }

        //往右找
        if (j + 1 < matrix[0].length && matrix[i][j] < matrix[i][j + 1]) {
            dp[i][j] = Math.max(dp[i][j], dfs(i, j + 1, matrix, dp) + 1);
        }

        return dp[i][j];
    }
}
