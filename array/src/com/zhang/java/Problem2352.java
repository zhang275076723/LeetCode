package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2025/4/8 08:02
 * @Author zsy
 * @Description 相等行列对 哈希表类比Problem1
 * 给你一个下标从 0 开始、大小为 n x n 的整数矩阵 grid ，返回满足 Ri 行和 Cj 列相等的行列对 (Ri, Cj) 的数目。
 * 如果行和列以相同的顺序包含相同的元素（即相等的数组），则认为二者是相等的。
 * <p>
 * 输入：grid = [[3,2,1],[1,7,6],[2,7,7]]
 * 输出：1
 * 解释：存在一对相等行列对：
 * - (第 2 行，第 1 列)：[2,7,7]
 * <p>
 * 输入：grid = [[3,1,2,2],[1,4,4,5],[2,4,2,2],[2,4,2,2]]
 * 输出：3
 * 解释：存在三对相等行列对：
 * - (第 0 行，第 0 列)：[3,1,2,2]
 * - (第 2 行, 第 2 列)：[2,4,2,2]
 * - (第 3 行, 第 2 列)：[2,4,2,2]
 * <p>
 * n == grid.length == grid[i].length
 * 1 <= n <= 200
 * 1 <= grid[i][j] <= 10^5
 */
public class Problem2352 {
    public static void main(String[] args) {
        Problem2352 problem2352 = new Problem2352();
        int[][] grid = {
                {3, 1, 2, 2},
                {1, 4, 4, 5},
                {2, 4, 2, 2},
                {2, 4, 2, 2}
        };
        System.out.println(problem2352.equalPairs(grid));
        System.out.println(problem2352.equalPairs2(grid));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param grid
     * @return
     */
    public int equalPairs(int[][] grid) {
        int n = grid.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //第i行和第j列包含的元素是否相等
                boolean flag = true;

                for (int k = 0; k < n; k++) {
                    if (grid[i][k] != grid[k][j]) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 哈希表
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param grid
     * @return
     */
    public int equalPairs2(int[][] grid) {
        int n = grid.length;
        //key：当前行元素构成的字符串，value：当前字符串出现的次数
        Map<String, Integer> map = new HashMap<>();

        //遍历第i行元素加入map中
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < n; j++) {
                //不同元素之间使用'-'间隔，避免不同元素构成相同字符串的情况
                sb.append(grid[i][j]).append('-');
            }

            map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
        }

        int count = 0;

        //遍历第j列元素，判断是否和map中元素相等
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < n; j++) {
                sb.append(grid[j][i]).append('-');
            }

            if (map.containsKey(sb.toString())) {
                count = count + map.get(sb.toString());
            }
        }

        return count;
    }
}
