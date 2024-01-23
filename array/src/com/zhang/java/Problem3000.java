package com.zhang.java;

/**
 * @Date 2024/1/25 08:55
 * @Author zsy
 * @Description 对角线最长的矩形的面积 对角线类比Problem51、Problem52、Problem498、Problem1001、Problem1329、Problem1424、Problem1572、Problem2614、Problem2711
 * 给你一个下标从 0 开始的二维整数数组 dimensions。
 * 对于所有下标 i（0 <= i < dimensions.length），dimensions[i][0] 表示矩形 i 的长度，而 dimensions[i][1] 表示矩形 i 的宽度。
 * 返回对角线最 长 的矩形的 面积 。
 * 如果存在多个对角线长度相同的矩形，返回面积最 大 的矩形的面积。
 * <p>
 * 输入：dimensions = [[9,3],[8,6]]
 * 输出：48
 * 解释：
 * 下标 = 0，长度 = 9，宽度 = 3。对角线长度 = sqrt(9 * 9 + 3 * 3) = sqrt(90) ≈ 9.487。
 * 下标 = 1，长度 = 8，宽度 = 6。对角线长度 = sqrt(8 * 8 + 6 * 6) = sqrt(100) = 10。
 * 因此，下标为 1 的矩形对角线更长，所以返回面积 = 8 * 6 = 48。
 * <p>
 * 输入：dimensions = [[3,4],[4,3]]
 * 输出：12
 * 解释：两个矩形的对角线长度相同，为 5，所以最大面积 = 12。
 * <p>
 * 1 <= dimensions.length <= 100
 * dimensions[i].length == 2
 * 1 <= dimensions[i][0], dimensions[i][1] <= 100
 */
public class Problem3000 {
    public static void main(String[] args) {
        Problem3000 problem3000 = new Problem3000();
        int[][] dimensions = {{9, 3}, {8, 6}};
        System.out.println(problem3000.areaOfMaxDiagonal(dimensions));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param dimensions
     * @return
     */
    public int areaOfMaxDiagonal(int[][] dimensions) {
        //矩形的最长对角线平方
        int maxDiag = 0;
        //最长对角线矩形的最大面积
        int maxArea = 0;

        for (int i = 0; i < dimensions.length; i++) {
            int length = dimensions[i][0];
            int width = dimensions[i][1];
            //当前矩形对角线的平方
            int result = length * length + width * width;

            if (result > maxDiag || (result == maxDiag && length * width > maxArea)) {
                maxDiag = result;
                maxArea = length * width;
            }
        }

        return maxArea;
    }
}
