package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2022/8/18 9:07
 * @Author zsy
 * @Description 矩阵中的最长递增路径 类比Problem2328、Problem2371 回溯+预处理类比Problem131、Problem132、Problem139、Problem140、Problem403 记忆化搜索类比Problem62、Problem63、Problem64、Problem70、Problem509、Problem1340、Problem1388、Problem1444、Offer10、Offer10_2 拓扑排序类比Problem207、Problem210、Problem310
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
     * dp[i][j]：matrix[i][j]起始的最长递增路径长度
     * dp[i][j] = max(dp[i-1][j],dp[i+1][j],dp[i][j-1],dp[i][j+1])+1
     * (matrix[i][j] < matrix[i-1][j],matrix[i+1][j],matrix[i][j-1],matrix[i][j+1])
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxLen = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxLen = Math.max(maxLen, dfs(i, j, matrix, dp));
            }
        }

        return maxLen;
    }

    /**
     * bfs拓扑排序
     * matrix作为图，matrix中存在相邻节点的较小值节点指向较大值节点的边，
     * 入度为0的节点入队列，队列中节点出队，每次删除当前层中的所有节点相连的边，即对应节点的入度减1，
     * 如果存在新的入度为1的节点，则入队，直至队列为空，则bfs扩展的层数即为最长递增路径长度
     * 时间复杂度O(mn)，空间复杂度O(mn)
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath2(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        //入度数组
        int[][] inDegree = new int[m][n];
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        //图中节点入度初始化
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < direction.length; k++) {
                    int x = i + direction[k][0];
                    int y = j + direction[k][1];

                    if (x < 0 || x >= m || y < 0 || y >= n || matrix[i][j] <= matrix[x][y]) {
                        continue;
                    }

                    //matrix[i][j]大于matrix[x][y]，则存在节点(x,y)到节点(i,j)的边
                    inDegree[i][j]++;
                }
            }
        }

        //arr[0]：节点的行下标索引，arr[1]：节点的列下标索引
        Queue<int[]> queue = new LinkedList<>();

        //入度为0的节点入队
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (inDegree[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        //最长递增路径长度，即bfs扩展的层数
        int maxLen = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            //每次往外扩一层，每层删除当前层中的所有节点相连的边，即对应节点的入度减1，如果存在新的入度为1的节点，则入队
            for (int i = 0; i < size; i++) {
                int[] arr = queue.poll();
                int x1 = arr[0];
                int y1 = arr[1];

                for (int j = 0; j < direction.length; j++) {
                    int x2 = x1 + direction[j][0];
                    int y2 = y1 + direction[j][1];

                    if (x2 < 0 || x2 >= m || y2 < 0 || y2 >= n || matrix[x1][y1] >= matrix[x2][y2]) {
                        continue;
                    }

                    //matrix[x1][y1]小于matrix[x2][y2]，则存在节点(x1,y1)到节点(x2,y2)的边
                    inDegree[x2][y2]--;

                    //入度为0的节点(x2,y2)入队
                    if (inDegree[x2][y2] == 0) {
                        queue.offer(new int[]{x2, y2});
                    }
                }
            }

            //maxLen加1，表示bfs每次往外扩一层
            maxLen++;
        }

        return maxLen;
    }

    /**
     * 返回matrix[i][j]起始的最长递增路径长度
     *
     * @param i
     * @param j
     * @param matrix
     * @param dp
     * @return
     */
    private int dfs(int i, int j, int[][] matrix, int[][] dp) {
        //已经找到matrix[i][j]起始的最长递增路径长度，直接返回
        if (dp[i][j] != 0) {
            return dp[i][j];
        }

        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int m = matrix.length;
        int n = matrix[0].length;

        //初始化matrix[i][j]起始的最长递增路径长度为1
        dp[i][j] = 1;

        for (int k = 0; k < direction.length; k++) {
            int x = i + direction[k][0];
            int y = j + direction[k][1];

            if (x < 0 || x >= m || y < 0 || y >= n || matrix[i][j] >= matrix[x][y]) {
                continue;
            }

            dp[i][j] = Math.max(dp[i][j], dfs(x, y, matrix, dp) + 1);
        }

        return dp[i][j];
    }
}
