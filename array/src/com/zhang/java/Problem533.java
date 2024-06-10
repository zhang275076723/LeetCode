package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/6/5 08:36
 * @Author zsy
 * @Description 孤独像素 II 类比Problem531
 * 给你一个大小为 m x n 的二维字符数组 picture ，表示一张黑白图像，数组中的 'B' 表示黑色像素，'W' 表示白色像素。
 * 另给你一个整数 target ，请你找出并返回符合规则的 黑色 孤独像素的数量。
 * 黑色孤独像素是指位于某一特定位置 (r, c) 的字符 'B' ，其中：
 * 行 r 和列 c 中的黑色像素恰好有 target 个。
 * 列 c 中所有黑色像素所在的行必须和行 r 完全相同。
 * <p>
 * 输入：picture = [
 * ["W","B","W","B","B","W"],
 * ["W","B","W","B","B","W"],
 * ["W","B","W","B","B","W"],
 * ["W","W","B","W","B","W"]
 * ],
 * target = 3
 * 输出：6
 * 解释：所有绿色的 'B' 都是我们所求的像素(第 1 列和第 3 列的所有 'B' )
 * 以行 r = 0 和列 c = 1 的 'B' 为例：
 * - 规则 1 ，行 r = 0 和列 c = 1 都恰好有 target = 3 个黑色像素
 * - 规则 2 ，列 c = 1 的黑色像素分别位于行 0，行 1 和行 2。和行 r = 0 完全相同。
 * <p>
 * 输入：picture = [
 * ["W","W","B"],
 * ["W","W","B"],
 * ["W","W","B"]
 * ],
 * target = 1
 * 输出：0
 * <p>
 * m == picture.length
 * n == picture[i].length
 * 1 <= m, n <= 200
 * picture[i][j] 为 'W' 或 'B'
 * 1 <= target <= min(m, n)
 */
public class Problem533 {
    public static void main(String[] args) {
        Problem533 problem533 = new Problem533();
        char[][] picture = {
                {'W', 'B', 'W', 'B', 'B', 'W'},
                {'W', 'B', 'W', 'B', 'B', 'W'},
                {'W', 'B', 'W', 'B', 'B', 'W'},
                {'W', 'W', 'B', 'W', 'B', 'W'}
        };
        int target = 3;
        System.out.println(problem533.findBlackPixel(picture, target));
    }

    /**
     * 计数数组
     * row[i]：第i行黑色像素'B'的个数
     * colList.get(j)：第j列黑色像素'B'所在行的集合
     * 每次判断一列中的黑素像素是否是孤独元素，如果是则添加target个孤独元素
     * 时间复杂度O(mn^2)，空间复杂度O(mn) (m=picture.length，n=picture[0].length)
     *
     * @param picture
     * @param target
     * @return
     */
    public int findBlackPixel(char[][] picture, int target) {
        int m = picture.length;
        int n = picture[0].length;

        int[] row = new int[m];
        //存储每一列黑色像素'B'所在行的集合
        List<List<Integer>> colList = new ArrayList<>();

        for (int j = 0; j < n; j++) {
            colList.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    row[i]++;
                    colList.get(j).add(i);
                }
            }
        }

        int count = 0;

        for (int j = 0; j < n; j++) {
            //当前列中不存在黑素像素，或者当前列第一个黑素像素所在行的黑色像素个数不等于target，
            //或者当前列的黑色像素个数不等于target，则当前列中所有黑色像素都不是孤独像素，直接进行下次循环
            if (colList.get(j).isEmpty() || row[colList.get(j).get(0)] != target || colList.get(j).size() != target) {
                continue;
            }

            //当前列的黑色像素所在行是否都相等标志位
            boolean flag = true;
            //当前列第一个黑素像素所在行
            int row1 = colList.get(j).get(0);

            //判断row1行和row2行元素是否都相等
            for (int i = 1; i < colList.get(j).size(); i++) {
                //当前列要比较的黑素像素所在行
                int row2 = colList.get(j).get(i);

                for (int k = 0; k < n; k++) {
                    if (picture[row1][k] != picture[row2][k]) {
                        flag = false;
                        break;
                    }
                }

                if (!flag) {
                    break;
                }
            }

            //每次判断一列中的黑素像素是否是孤独元素，即每次添加target个孤独元素
            if (flag) {
                count = count + target;
            }
        }

        return count;
    }
}