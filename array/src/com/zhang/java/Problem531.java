package com.zhang.java;

/**
 * @Date 2024/6/4 08:40
 * @Author zsy
 * @Description 孤独像素 I 类比Problem533
 * 给你一个大小为 m x n 的图像 picture ，图像由黑白像素组成，'B' 表示黑色像素，'W' 表示白色像素，
 * 请你统计并返回图像中 黑色 孤独像素的数量。
 * 黑色孤独像素 的定义为：如果黑色像素 'B' 所在的同一行和同一列不存在其他黑色像素，那么这个黑色像素就是黑色孤独像素。
 * <p>
 * 输入：picture = [
 * ["W","W","B"],
 * ["W","B","W"],
 * ["B","W","W"]
 * ]
 * 输出：3
 * 解释：全部三个 'B' 都是黑色的孤独像素
 * <p>
 * 输入：picture = [
 * ["B","B","B"],
 * ["B","B","W"],
 * ["B","B","B"]
 * ]
 * 输出：0
 * <p>
 * m == picture.length
 * n == picture[i].length
 * 1 <= m, n <= 500
 * picture[i][j] 为 'W' 或 'B'
 */
public class Problem531 {
    public static void main(String[] args) {
        Problem531 problem531 = new Problem531();
        char[][] picture = {
                {'W','W','B'},
                {'W','B','W'},
                {'B','W','W'}
        };
        System.out.println(problem531.findLonelyPixel(picture));
    }

    /**
     * 计数数组
     * row[i]：第i行黑色像素'B'的个数
     * col[j]：第j列黑色像素'B'的个数
     * 时间复杂度O(mn)，空间复杂度O(m+n) (m=picture.length，n=picture[0].length)
     *
     * @param picture
     * @return
     */
    public int findLonelyPixel(char[][] picture) {
        int m = picture.length;
        int n = picture[0].length;

        int[] row = new int[m];
        int[] col = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 'B') {
                    row[i]++;
                    col[j]++;
                }
            }
        }

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //当前黑色像素第i行第j列只有这一个黑色像素，则是孤独像素
                if (picture[i][j] == 'B' && row[i] == 1 && col[j] == 1) {
                    count++;
                }
            }
        }

        return count;
    }
}