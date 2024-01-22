package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/1/28 10:10
 * @Author zsy
 * @Description 网格照明 对角线类比Problem51、Problem52、Problem498、Problem1329、Problem1424、Problem1572
 * 在大小为 n x n 的网格 grid 上，每个单元格都有一盏灯，最初灯都处于 关闭 状态。
 * 给你一个由灯的位置组成的二维数组 lamps ，其中 lamps[i] = [rowi, coli] 表示 打开 位于 grid[rowi][coli] 的灯。
 * 即便同一盏灯可能在 lamps 中多次列出，不会影响这盏灯处于 打开 状态。
 * 当一盏灯处于打开状态，它将会照亮 自身所在单元格 以及同一 行 、同一 列 和两条 对角线 上的 所有其他单元格 。
 * 另给你一个二维数组 queries ，其中 queries[j] = [rowj, colj] 。
 * 对于第 j 个查询，如果单元格 [rowj, colj] 是被照亮的，则查询结果为 1 ，否则为 0 。
 * 在第 j 次查询之后 [按照查询的顺序]，关闭 位于单元格 grid[rowj][colj] 上及相邻 8 个方向上
 * （与单元格 grid[rowi][coli] 共享角或边）的任何灯。
 * 返回一个整数数组 ans 作为答案， ans[j] 应等于第 j 次查询 queries[j] 的结果，1 表示照亮，0 表示未照亮。
 * <p>
 * 输入：n = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,0]]
 * 输出：[1,0]
 * 解释：最初所有灯都是关闭的。在执行查询之前，打开位于 [0, 0] 和 [4, 4] 的灯。
 * 第 0 次查询检查 grid[1][1] 是否被照亮（蓝色方框）。该单元格被照亮，所以 ans[0] = 1 。然后，关闭红色方框中的所有灯。
 * 第 1 次查询检查 grid[1][0] 是否被照亮（蓝色方框）。该单元格没有被照亮，所以 ans[1] = 0 。然后，关闭红色矩形中的所有灯。
 * <p>
 * 输入：n = 5, lamps = [[0,0],[4,4]], queries = [[1,1],[1,1]]
 * 输出：[1,1]
 * <p>
 * 输入：n = 5, lamps = [[0,0],[0,4]], queries = [[0,4],[0,1],[1,4]]
 * 输出：[1,1,0]
 * <p>
 * 1 <= n <= 10^9
 * 0 <= lamps.length <= 20000
 * 0 <= queries.length <= 20000
 * lamps[i].length == 2
 * 0 <= rowi, coli < n
 * queries[j].length == 2
 * 0 <= rowj, colj < n
 */
public class Problem1001 {
    public static void main(String[] args) {
        Problem1001 problem1001 = new Problem1001();
        int n = 5;
        int[][] lamps = {{0, 0}, {4, 4}};
        int[][] queries = {{1, 1}, {1, 0}};
        System.out.println(Arrays.toString(problem1001.gridIllumination(n, lamps, queries)));
    }

    /**
     * 模拟
     * 核心思想：左上到右下对角线上的元素的下标索引j-i相等，左下到右上对角线上的元素下标索引i+j相等
     * 统计每盏灯(i,j)照亮的行(第i行)、列(第j列)、左上到右下对角线(第j-i+n-1个对角线)、左下到右上对角线(第i+j个对角线)
     * 遍历queries，当前位置(i,j)对应的行、列、左上到右下的对角线、左下到右上的对角线，如果存在灯时，则被照亮，
     * 并关闭(i,j)周围9个位置存在的灯照亮的行、列、左上到右下对角线、左下到右上对角线
     * 时间复杂度O(n+m)，空间复杂度O(n) (n=lamps.length, m=queries.length)
     *
     * @param n
     * @param lamps
     * @param queries
     * @return
     */
    public int[] gridIllumination(int n, int[][] lamps, int[][] queries) {
        //当前灯(i,j)所在位置的set集合，用于去重，key：i*n+j，使用long，避免int溢出
        Set<Long> lightSet = new HashSet<>();
        //key：灯照亮的行，value：灯照亮当前行的次数
        Map<Integer, Integer> rowMap = new HashMap<>();
        //灯照亮的列，value：灯照亮当前列的次数
        Map<Integer, Integer> colMap = new HashMap<>();
        //灯照亮的对角线(左上到右下对角线(j-i+n-1))，value：灯照亮当前左上到右下对角线的次数
        Map<Integer, Integer> diagMap = new HashMap<>();
        //灯照亮的反对角线(左下到右上对角线(i+j))，value：灯照亮当前左下到右上对角线的次数
        Map<Integer, Integer> antiDiagMap = new HashMap<>();

        //遍历每盏灯，确定每盏灯照亮的行、列、对角线(上右下对角线)、反对角线(左下到右上对角线)的个数
        for (int i = 0; i < lamps.length; i++) {
            int x = lamps[i][0];
            int y = lamps[i][1];

            //lightSet中已经存在当前灯，则当前灯照亮的行、列、对角线、反对角线已经加入相应map中，直接进行下次循环
            if (lightSet.contains((long) x * n + y)) {
                continue;
            }

            //当前灯加入lightSet中
            lightSet.add((long) x * n + y);

            //当前灯照亮的行、列、对角线、反对角线加入对应map中
            rowMap.put(x, rowMap.getOrDefault(x, 0) + 1);
            colMap.put(y, colMap.getOrDefault(y, 0) + 1);
            diagMap.put(y - x + n - 1, diagMap.getOrDefault(y - x + n - 1, 0) + 1);
            antiDiagMap.put(x + y, antiDiagMap.getOrDefault(x + y, 0) + 1);
        }

        int[] result = new int[queries.length];
        //当前位置周围9个相邻位置(包含当前位置本身)
        int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        //判断当前位置(x,y)是否被照亮，并关闭当前位置(x,y)周围9盏灯(如果周围存在灯的话)
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0];
            int y = queries[i][1];

            //当前位置(x,y)未被照亮，则当前位置(x,y)周围的9个位置都不存在灯，直接进行下次循环
            if (!rowMap.containsKey(x) && !colMap.containsKey(y) &&
                    !diagMap.containsKey(x + y) && !antiDiagMap.containsKey(y - x + n - 1)) {
                continue;
            }

            result[i] = 1;

            //当前位置(x,y)被照亮，关闭当前位置(x,y)周围9个位置存在的灯(x2,y2)，
            //并且关闭灯(x2,y2)照亮的行、列、对角线、反对角线
            for (int k = 0; k < direction.length; k++) {
                //当前位置(x,y)周围的9个位置(x2,y2)
                int x2 = x + direction[k][0];
                int y2 = y + direction[k][1];

                //位置(x2,y2)不在网格之内，或者位置(x2,y2)不是灯，直接进行下次循环
                if (x2 < 0 || x2 >= n || y2 < 0 || y2 >= n ||
                        !lightSet.contains((long) x2 * n + y2)) {
                    continue;
                }

                //灯(x2,y2)从set中移除
                lightSet.remove((long) x2 * n + y2);

                //关闭灯(x2,y2)照亮的行
                rowMap.put(x2, rowMap.get(x2) - 1);
                //关闭灯(x2,y2)照亮的行之后，对应行的照亮次数为0，从map中移除
                if (rowMap.get(x2) == 0) {
                    rowMap.remove(x2);
                }

                //关闭灯(x2,y2)照亮的列
                colMap.put(y2, colMap.get(y2) - 1);
                //关闭灯(x2,y2)照亮的列之后，对应列的照亮次数为0，从map中移除
                if (colMap.get(y2) == 0) {
                    colMap.remove(y2);
                }

                //关闭灯(x2,y2)照亮的对角线(y2-x2+n-1)
                diagMap.put(y2 - x2 + n - 1, diagMap.get(y2 - x2 + n - 1) - 1);
                //关闭灯(x2,y2)照亮的对角线之后，对应对角线的照亮次数为0，从map中移除
                if (diagMap.get(y2 - x2 + n - 1) == 0) {
                    diagMap.remove(y2 - x2 + n - 1);
                }

                //关闭灯(x2,y2)照亮的反对角线(i+j)
                antiDiagMap.put(x2 + y2, antiDiagMap.get(x2 + y2) - 1);
                //关闭灯(x2,y2)照亮的反对角线(i+j)，对应反对角线的照亮次数为0，从map中移除
                if (antiDiagMap.get(x2 + y2) == 0) {
                    antiDiagMap.remove(x2 + y2);
                }
            }
        }

        return result;
    }
}
