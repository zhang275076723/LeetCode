package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2022/4/29 9:26
 * @Author zsy
 * @Description 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 * <p>
 * 输入：matrix = [["1","0","1","0","0"],
 * ["1","0","1","1","1"],
 * ["1","1","1","1","1"],
 * ["1","0","0","1","0"]]
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
        char[][] matrix = {{'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}};
        System.out.println(problem85.maximalRectangle(matrix));
        System.out.println(problem85.maximalRectangle2(matrix));
    }

    /**
     * 动态规划，时间复杂度O((m^2)n)，空间复杂度O(mn)，m为matrix的行，n为matrix的列
     * dp[i][j]：从matrix[i][j]开始往左连续1的个数
     * 以matrix[i][j]为矩阵右下角，计算dp[i][j]到dp[i][0]对应矩阵面积
     * 例如：
     * dp[2][4] = 3, dp[2][3] = 4, dp[2][2] = 1, dp[2][1] = 0, dp[2][0] = 2
     * area[2][4] = 1*3 = 3
     * area[2][3] = 2*min(3,4) = 6
     * area[2][2] = 3*min(3,1) = 3
     * area[2][1] = 4*min(1,0) = 0
     * area[2][0] = 5*min(0,2) = 0
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
                if (j > 0 && matrix[i][j] == '1') {
                    dp[i][j] = dp[i][j - 1] + 1;
                } else if (j == 0 && matrix[i][j] == '1') {
                    dp[i][j] = 1;
                }
            }
        }

        int maxArea = 0;
        int length;
        int width;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                length = dp[i][j];
                for (int k = i; k >= 0; k--) {
                    length = Math.min(length, dp[k][j]);
                    width = i - k + 1;
                    maxArea = Math.max(maxArea, length * width);
                }
            }
        }
        return maxArea;
    }

    /**
     * 单调递增栈，时间复杂度O(mn)，空间复杂度O(n)，m为matrix的行，n为matrix的列
     * 借助84题思想，每一行到最上面一行形成柱子的高度，从中找出柱子形成的最大矩形面积
     *
     * @param matrix
     * @return
     */
    public int maximalRectangle2(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int maxArea = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        //记录每行到最上面一行形成柱子的高度
        int[] heights = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] = heights[j] + 1;
                } else {
                    heights[j] = 0;
                }
            }
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    /**
     * 84题单调递增栈，求圆柱形成的最大矩形面积
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param heights
     * @return
     */
    private int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < heights.length; i++) {
            //不满足要求，则栈顶元素出栈
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                //矩形的高
                int height = heights[stack.pop()];
                //矩形的宽
                int width;

                //栈顶索引对应元素与当前矩形的高相等，则可以直接出栈
                while (!stack.isEmpty() && height == heights[stack.peek()]) {
                    stack.pop();
                }

                if (stack.isEmpty()) {
                    width = i;
                } else {
                    width = i - stack.peek() - 1;
                }
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            //矩形的高
            int height = heights[stack.pop()];
            //矩形的宽
            int width;

            //栈顶索引对应元素与当前矩形的高相等，则可以直接出栈
            while (!stack.isEmpty() && height == heights[stack.peek()]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                width = heights.length;
            } else {
                width = heights.length - stack.peek() - 1;
            }
            maxArea = Math.max(maxArea, height * width);
        }

        return maxArea;
    }
}
