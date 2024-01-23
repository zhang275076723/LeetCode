package com.zhang.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/1/25 08:30
 * @Author zsy
 * @Description 对角线上不同值的数量差 二维前缀和类比Problem304、Problem1444 对角线类比Problem51、Problem52、Problem498、Problem1001、Problem1329、Problem1424、Problem1572、Problem2614、Problem3000
 * 给你一个下标从 0 开始、大小为 m x n 的二维矩阵 grid ，请你求解大小同样为 m x n 的答案矩阵 answer 。
 * 矩阵 answer 中每个单元格 (r, c) 的值可以按下述方式进行计算：
 * 令 topLeft[r][c] 为矩阵 grid 中单元格 (r, c) 左上角对角线上 不同值 的数量。
 * 令 bottomRight[r][c] 为矩阵 grid 中单元格 (r, c) 右下角对角线上 不同值 的数量。
 * 然后 answer[r][c] = |topLeft[r][c] - bottomRight[r][c]| 。
 * 返回矩阵 answer 。
 * 矩阵对角线 是从最顶行或最左列的某个单元格开始，向右下方向走到矩阵末尾的对角线。
 * 如果单元格 (r1, c1) 和单元格 (r, c) 属于同一条对角线且 r1 < r ，则单元格 (r1, c1) 属于单元格 (r, c) 的左上对角线。
 * 类似地，可以定义右下对角线。
 * <p>
 * 输入：grid = [[1,2,3],[3,1,5],[3,2,1]]
 * 输出：[[1,1,0],[1,0,1],[0,1,1]]
 * 解释：第 1 个图表示最初的矩阵 grid 。
 * 第 2 个图表示对单元格 (0,0) 计算，其中蓝色单元格是位于右下对角线的单元格。
 * 第 3 个图表示对单元格 (1,2) 计算，其中红色单元格是位于左上对角线的单元格。
 * 第 4 个图表示对单元格 (1,1) 计算，其中蓝色单元格是位于右下对角线的单元格，红色单元格是位于左上对角线的单元格。
 * - 单元格 (0,0) 的右下对角线包含 [1,1] ，而左上对角线包含 [] 。对应答案是 |1 - 0| = 1 。
 * - 单元格 (1,2) 的右下对角线包含 [] ，而左上对角线包含 [2] 。对应答案是 |0 - 1| = 1 。
 * - 单元格 (1,1) 的右下对角线包含 [1] ，而左上对角线包含 [1] 。对应答案是 |1 - 1| = 0 。
 * 其他单元格的对应答案也可以按照这样的流程进行计算。
 * <p>
 * 输入：grid = [[1]]
 * 输出：[[0]]
 * 解释：- 单元格 (0,0) 的右下对角线包含 [] ，左上对角线包含 [] 。对应答案是 |0 - 0| = 0 。
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n, grid[i][j] <= 50
 */
public class Problem2711 {
    public static void main(String[] args) {
        Problem2711 problem2711 = new Problem2711();
        int[][] grid = {
                {1, 2, 3},
                {3, 1, 5},
                {3, 2, 1}
        };
        System.out.println(Arrays.deepToString(problem2711.differenceOfDistinctValues(grid)));
        System.out.println(Arrays.deepToString(problem2711.differenceOfDistinctValues(grid)));
    }

    /**
     * 模拟
     * 左上到右下对角线上的元素的下标索引j-i相等
     * 时间复杂度O(mn*min(m,n))，空间复杂度O(min(m,n)) (m=grid.length，n=grid[0].length)
     *
     * @param grid
     * @return
     */
    public int[][] differenceOfDistinctValues(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] result = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Set<Integer> set = new HashSet<>();
                //左上到右下对角线从(i,j)往左上(不包含(i,j))不同元素的个数
                int count1;
                //左上到右下对角线从(i,j)往右下(不包含(i,j))不同元素的个数
                int count2;
                int x = i - 1;
                int y = j - 1;

                //左上到右下对角线从(i,j)往左上遍历
                while (x >= 0 && y >= 0) {
                    set.add(grid[x][y]);
                    x--;
                    y--;
                }

                count1 = set.size();
                set.clear();
                x = i + 1;
                y = j + 1;

                //左上到右下对角线从(i,j)往右下遍历
                while (x < m && y < n) {
                    set.add(grid[x][y]);
                    x++;
                    y++;
                }

                count2 = set.size();
                result[i][j] = Math.abs(count1 - count2);
            }
        }

        return result;
    }

    /**
     * 前缀和
     * 左上到右下对角线上的元素的下标索引j-i相等
     * prefix[i][j]：第j-i+m-1条左上到右下对角线从(i,j)往左上(不包含(i,j))不同元素的个数
     * suffix[i][j]：第j-i+m-1条左上到右下对角线从(i,j)往右下(不包含(i,j))不同元素的个数
     * result[i][j] = abs(prefix[i][j]-suffix[i][j])
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=grid.length，n=grid[0].length)
     *
     * @param grid
     * @return
     */
    public int[][] differenceOfDistinctValues2(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] result = new int[m][n];
        int[][] prefix = new int[m][n];
        int[][] suffix = new int[m][n];

        //共m+n-1条左上到右下对角线，每条对角线上的元素(x,y)满足y-x+m-1=i
        for (int i = 0; i < m + n - 1; i++) {
            Set<Integer> set = new HashSet<>();
            //第i条左上到右下对角线的最左上元素横坐标
            int x = Math.max(m - 1 - i, 0);
            //第i条左上到右下对角线的最左上元素纵坐标
            //第i条对角线满足y-x+m-1=i，即y=x+i-m+1
            int y = x + i - m + 1;

            while (x < m && y < n) {
                set.add(grid[x][y]);

                //避免溢出
                if (x + 1 < m && y + 1 < n) {
                    prefix[x + 1][y + 1] = set.size();
                }

                x++;
                y++;
            }

            set.clear();
            //第i条左上到右下对角线的最右下元素纵坐标
            y = Math.min(i, n - 1);
            //第i条左上到右下对角线的最右下元素横坐标
            x = y + m - 1 - i;

            while (x >= 0 && y >= 0) {
                set.add(grid[x][y]);

                //避免溢出
                if (x - 1 >= 0 && y - 1 >= 0) {
                    suffix[x - 1][y - 1] = set.size();
                }

                x--;
                y--;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = Math.abs(prefix[i][j] - suffix[i][j]);
            }
        }

        return result;
    }
}
