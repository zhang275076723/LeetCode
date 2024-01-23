package com.zhang.java;

/**
 * @Date 2024/1/25 08:20
 * @Author zsy
 * @Description 对角线上的质数 对角线类比Problem51、Problem52、Problem498、Problem1001、Problem1329、Problem1424、Problem1572、Problem2711、Problem3000 质数类比Problem204、Problem952、Problem1175、Problem1998、Problem2523
 * 给你一个下标从 0 开始的二维整数数组 nums 。
 * 返回位于 nums 至少一条 对角线 上的最大 质数 。
 * 如果任一对角线上均不存在质数，返回 0 。
 * 注意：
 * 如果某个整数大于 1 ，且不存在除 1 和自身之外的正整数因子，则认为该整数是一个质数。
 * 如果存在整数 i ，使得 nums[i][i] = val 或者 nums[i][nums.length - i - 1]= val ，则认为整数 val 位于 nums 的一条对角线上。
 * <p>
 * 输入：nums = [[1,2,3],[5,6,7],[9,10,11]]
 * 输出：11
 * 解释：数字 1、3、6、9 和 11 是所有 "位于至少一条对角线上" 的数字。由于 11 是最大的质数，故返回 11 。
 * <p>
 * 输入：nums = [[1,2,3],[5,17,7],[9,11,10]]
 * 输出：17
 * 解释：数字 1、3、9、10 和 17 是所有满足"位于至少一条对角线上"的数字。由于 17 是最大的质数，故返回 17 。
 * <p>
 * 1 <= nums.length <= 300
 * nums.length == numsi.length
 * 1 <= nums[i][j] <= 4*10^6
 */
public class Problem2614 {
    public static void main(String[] args) {
        Problem2614 problem2614 = new Problem2614();
        int[][] nums = {
                {1, 2, 3},
                {5, 17, 7},
                {9, 11, 10}};
        System.out.println(problem2614.diagonalPrime(nums));
    }

    /**
     * 模拟
     * 左上到右下对角线上的元素的下标索引j-i相等，左下到右上对角线上的元素下标索引i+j相等
     * 时间复杂度O(n*C^(1/2))，空间复杂度O(1) (C：两条对角线上的最大值)
     *
     * @param nums
     * @return
     */
    public int diagonalPrime(int[][] nums) {
        //对角线上的最大质数，初始化为0，表示对角线上不存在质数
        int maxPrime = 0;
        int n = nums.length;
        int i = 0;
        int j = 0;

        //左上到右下对角线上的质数
        while (i < n && j < n) {
            if (isPrime(nums[i][j])) {
                maxPrime = Math.max(maxPrime, nums[i][j]);
            }

            i++;
            j++;
        }

        i = n - 1;
        j = 0;

        //左下到右上对角线上的质数
        while (i >= 0 && j < n) {
            if (isPrime(nums[i][j])) {
                maxPrime = Math.max(maxPrime, nums[i][j]);
            }

            i--;
            j++;
        }

        return maxPrime;
    }

    /**
     * 判断正整数n是否为质数
     * 时间复杂度O(n^(1/2))，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    private boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }

        //只要n整除[2,n^(1/2)]中任意一个数，则n不是质数
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
