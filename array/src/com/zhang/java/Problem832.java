package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/6/21 08:55
 * @Author zsy
 * @Description 翻转图像 位运算类比
 * 给定一个 n x n 的二进制矩阵 image ，先 水平 翻转图像，然后 反转 图像并返回 结果 。
 * 水平翻转图片就是将图片的每一行都进行翻转，即逆序。
 * 例如，水平翻转 [1,1,0] 的结果是 [0,1,1]。
 * 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。
 * 例如，反转 [0,1,1] 的结果是 [1,0,0]。
 * <p>
 * 输入：image = [[1,1,0],[1,0,1],[0,0,0]]
 * 输出：[[1,0,0],[0,1,0],[1,1,1]]
 * 解释：首先翻转每一行: [[0,1,1],[1,0,1],[0,0,0]]；
 * 然后反转图片: [[1,0,0],[0,1,0],[1,1,1]]
 * <p>
 * 输入：image = [[1,1,0,0],[1,0,0,1],[0,1,1,1],[1,0,1,0]]
 * 输出：[[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
 * 解释：首先翻转每一行: [[0,0,1,1],[1,0,0,1],[1,1,1,0],[0,1,0,1]]；
 * 然后反转图片: [[1,1,0,0],[0,1,1,0],[0,0,0,1],[1,0,1,0]]
 * <p>
 * n == image.length
 * n == image[i].length
 * 1 <= n <= 20
 * images[i][j] == 0 或 1.
 */
public class Problem832 {
    public static void main(String[] args) {
        Problem832 problem832 = new Problem832();
        int[][] image = {
                {1, 1, 0, 0},
                {1, 0, 0, 1},
                {0, 1, 1, 1},
                {1, 0, 1, 0}
        };
        System.out.println(Arrays.deepToString(problem832.flipAndInvertImage(image)));
        //因为对image修改，下次遍历时image需要重新赋值
        image = new int[][]{
                {1, 1, 0, 0},
                {1, 0, 0, 1},
                {0, 1, 1, 1},
                {1, 0, 1, 0}
        };
        System.out.println(Arrays.deepToString(problem832.flipAndInvertImage2(image)));
    }

    /**
     * 模拟 (两次遍历)
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param image
     * @return
     */
    public int[][] flipAndInvertImage(int[][] image) {
        for (int i = 0; i < image.length; i++) {
            int left = 0;
            int right = image[i].length - 1;

            while (left < right) {
                int temp = image[i][left];
                image[i][left] = image[i][right];
                image[i][right] = temp;

                left++;
                right--;
            }
        }

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                //每个元素异或取反，即1变0，0变1
                image[i][j] = image[i][j] ^ 1;
            }
        }

        return image;
    }

    /**
     * 位运算 (一次遍历)
     * image[i][j]、image[i][k]为要交换的两个元素，如果image[i][j]、image[i][k]不相等，则先翻转再反转后，元素值不变；
     * 如果image[i][j]、image[i][k]相等，则先翻转再反转后，元素值变为相反数，即1变0，0变1
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param image
     * @return
     */
    public int[][] flipAndInvertImage2(int[][] image) {
        for (int i = 0; i < image.length; i++) {
            int left = 0;
            int right = image[i].length - 1;

            while (left <= right) {
                //要交换的两个元素相等，则每个元素异或取反，即1变0，0变1
                if (image[i][left] == image[i][right]) {
                    image[i][left] = image[i][left] ^ 1;
                    //注意：不能写成image[i][right] = image[i][right] ^ 1;，因为当left==right时，中间元素经过两次异或1仍然不变
                    image[i][right] = image[i][left];
                }

                left++;
                right--;
            }
        }

        return image;
    }
}
