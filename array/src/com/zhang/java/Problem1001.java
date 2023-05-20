package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/1/28 10:10
 * @Author zsy
 * @Description 网格照明 类比Problem36、Problem37、Problem51、Problem52 对角线处理类比Problem498
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
     * 统计每盏灯(i,j)照亮的行(第i行)、列(第j列)、左上右下对角线(第j-i+n-1个对角线)、左下右上对角线(第i+j个对角线)
     * 遍历queries，当前位置(i,j)对应的行、列、左上右下的对角线、左下右上的对角线，如果存在灯时，则被照亮，
     * 并关闭(i,j)周围9个位置存在的灯照亮的行、列、左上右下对角线、左下右上对角线
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
        Map<Integer, Integer> columnMap = new HashMap<>();
        //灯照亮的对角线(左上右下对角线(j-i+n-1))，value：灯照亮当前左上右下对角线的次数
        Map<Integer, Integer> diagMap = new HashMap<>();
        //灯照亮的反对角线(左下右上对角线(i+j))，value：灯照亮当前左下右上对角线的次数
        Map<Integer, Integer> antiDiagMap = new HashMap<>();

        //遍历每盏灯，确定每盏灯照亮的行、列、对角线(上右下对角线)、反对角线(左下右上对角线)的个数
        for (int[] arr : lamps) {
            int i = arr[0];
            int j = arr[1];

            //lightSet中已经存在当前灯，则当前灯照亮的行、列、对角线、反对角线已经加入相应map中，直接进行下次循环
            if (lightSet.contains((long) i * n + j)) {
                continue;
            }

            //当前灯加入lightSet中
            lightSet.add((long) i * n + j);

            //当前灯照亮的行、列、对角线、反对角线加入对应map中
            rowMap.put(i, rowMap.getOrDefault(i, 0) + 1);
            columnMap.put(j, columnMap.getOrDefault(j, 0) + 1);
            diagMap.put(j - i + n - 1, diagMap.getOrDefault(j - i + n - 1, 0) + 1);
            antiDiagMap.put(i + j, antiDiagMap.getOrDefault(i + j, 0) + 1);
        }


        int[] result = new int[queries.length];
        //当前位置周围9个相邻位置(包含当前位置本身)
        int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 0}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        //判断当前位置(x,y)是否被照亮，并关闭当前位置(x,y)周围9盏灯(如果周围存在灯的话)
        for (int i = 0; i < queries.length; i++) {
            int x = queries[i][0];
            int y = queries[i][1];

            //当前位置(x,y)被照亮，不管是行、列、对角线、反对角线，只要有一个满足要求，就能被照亮
            if (rowMap.containsKey(x) || columnMap.containsKey(y) ||
                    diagMap.containsKey(y - x + n - 1) || antiDiagMap.containsKey(x + y)) {
                result[i] = 1;

                //当前位置(x,y)被照亮，关闭当前位置(x,y)周围9个位置存在的灯，
                //如果当前位置(x,y)没被照亮，则周围9个位置肯定不存在灯
                for (int[] arr : direction) {
                    //当前位置(x,y)周围的9个位置(x2,y2)
                    int x2 = x + arr[0];
                    int y2 = y + arr[1];

                    //位置(x2,y2)不在网格之内，进行下次循环
                    if (x2 < 0 || x2 >= n || y2 < 0 || y2 >= n) {
                        continue;
                    }

                    //位置(x2,y2)是灯，灯(x2,y2)从lightSet中移除，
                    //并且关闭灯(x2,y2)照亮的行、列、左上右下对角线(y2-x2+n-1)、左下右上对角线(i+j)
                    if (lightSet.contains((long) x2 * n + y2)) {
                        //灯(x2,y2)从set中移除
                        lightSet.remove((long) x2 * n + y2);

                        //关闭灯(x2,y2)照亮的行
                        rowMap.put(x2, rowMap.get(x2) - 1);
                        //关闭灯(x2,y2)照亮的行之后，对应行的照亮次数为0，从map中移除
                        if (rowMap.get(x2) == 0) {
                            rowMap.remove(x2);
                        }

                        //关闭灯(x2,y2)照亮的列
                        columnMap.put(y2, columnMap.get(y2) - 1);
                        //关闭灯(x2,y2)照亮的列之后，对应列的照亮次数为0，从map中移除
                        if (columnMap.get(y2) == 0) {
                            columnMap.remove(y2);
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
            }
        }

        return result;
    }
}
