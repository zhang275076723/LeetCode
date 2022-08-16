package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/4/29 9:26
 * @Author zsy
 * @Description 柱状图中最大的矩形 类比Problem84、Problem221
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 * <p>
 * 输入：matrix = [
 * ["1","0","1","0","0"],
 * ["1","0","1","1","1"],
 * ["1","1","1","1","1"],
 * ["1","0","0","1","0"]
 * ]
 * 输出：6
 * <p>
 * 输入：matrix = []
 * 输出：0
 * <p>
 * 输入：matrix = [["0"]]
 * 输出：0
 * <p>
 * 输入：matrix = [["1"]]
 * 输出：1
 * <p>
 * 输入：matrix = [["0","0"]]
 * 输出：0
 * <p>
 * rows == matrix.length
 * cols == matrix[0].length
 * 1 <= row, cols <= 200
 * matrix[i][j] 为 '0' 或 '1'
 */
public class Problem85 {
    public static void main(String[] args) {
        Problem85 problem85 = new Problem85();
        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println(problem85.maximalRectangle(matrix));
        System.out.println(problem85.maximalRectangle2(matrix));
    }

    /**
     * 动态规划
     * dp[i][j]：matrix[i][j]左边连续1的个数
     * 以matrix[i][j]为矩阵右下角，min(dp[i][j])为长，i-k+1为宽，计算dp[i][j]到dp[0][j]对应矩阵面积
     * <p>
     * 例如：
     * dp[4][4] = 3, dp[3][4] = 4, dp[2][4] = 1, dp[1][4] = 0, dp[0][4] = 2
     * area[4][4] = 1*3 = 3
     * area[3][4] = 2*min(3,4) = 6
     * area[2][4] = 3*min(3,1) = 3
     * area[1][4] = 4*min(1,0) = 0
     * area[0][4] = 5*min(0,2) = 0
     * <p>
     * 时间复杂度O((m^2)n)，空间复杂度O(mn) (m为matrix的行，n为matrix的列)
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];

        //初始化dp
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1' && j == 0) {
                    dp[i][j] = 1;
                } else if (matrix[i][j] == '1' && j > 0) {
                    dp[i][j] = dp[i][j - 1] + 1;
                }
            }
        }

        int max = 0;
        //矩阵的长
        int l;
        //矩阵的宽
        int w;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                l = dp[i][j];

                for (int k = i; k >= 0; k--) {
                    l = Math.min(l, dp[k][j]);
                    w = i - k + 1;
                    max = Math.max(max, l * w);
                }
            }
        }

        return max;
    }

    /**
     * 单调栈
     * Problem84思想，每一行到最上面一行形成柱子的高度，从中找出柱子形成的最大矩形面积
     * 时间复杂度O(mn)，空间复杂度O(n) (m为matrix的行，n为matrix的列)
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle2(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max = 0;
        //记录每行到最上面一行形成柱子的高度
        int[] heights = new int[matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] = heights[j] + 1;
                } else {
                    heights[j] = 0;
                }
            }

            max = Math.max(max, largestRectangleArea(heights));
        }

        return max;
    }

    /**
     * 单调栈
     * 计算最大矩形面积
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param heights
     * @return
     */
    private int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int max = 0;
        //单调递增栈，存放元素下标索引
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < heights.length; i++) {
            //不满足单调递增栈，则栈顶元素出栈
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                //矩形的高
                int h = heights[stack.pop()];
                //矩形的宽
                int w;

                //栈顶索引对应元素与矩形的高相等，则直接出栈
                while (!stack.isEmpty() && h == heights[stack.peek()]) {
                    stack.pop();
                }

                if (stack.isEmpty()) {
                    w = i;
                } else {
                    w = i - stack.peek() - 1;
                }

                max = Math.max(max, h * w);
            }

            stack.push(i);
        }

        //栈不为空，说明栈中索引对应元素递增，需要分别计算对应面积
        while (!stack.isEmpty()) {
            //矩形的高
            int h = heights[stack.pop()];
            //矩形的宽
            int w;

            //栈顶索引对应元素与当前矩形的高相等，则可以直接出栈
            while (!stack.isEmpty() && h == heights[stack.peek()]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                w = heights.length;
            } else {
                w = heights.length - stack.peek() - 1;
            }

            max = Math.max(max, h * w);
        }

        return max;
    }
}
