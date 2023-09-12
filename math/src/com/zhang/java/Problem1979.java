package com.zhang.java;

/**
 * @Date 2023/8/9 08:13
 * @Author zsy
 * @Description 找出数组的最大公约数 最大公因数和最小公倍数类比Problem149
 * 给你一个整数数组 nums ，返回数组中最大数和最小数的 最大公约数 。
 * 两个数的 最大公约数 是能够被两个数整除的最大正整数。
 * <p>
 * 输入：nums = [2,5,6,9,10]
 * 输出：2
 * 解释：
 * nums 中最小的数是 2
 * nums 中最大的数是 10
 * 2 和 10 的最大公约数是 2
 * <p>
 * 输入：nums = [7,5,6,8,3]
 * 输出：1
 * 解释：
 * nums 中最小的数是 3
 * nums 中最大的数是 8
 * 3 和 8 的最大公约数是 1
 * <p>
 * 输入：nums = [3,3]
 * 输出：3
 * 解释：
 * nums 中最小的数是 3
 * nums 中最大的数是 3
 * 3 和 3 的最大公约数是 3
 * <p>
 * 2 <= nums.length <= 1000
 * 1 <= nums[i] <= 1000
 */
public class Problem1979 {
    public static void main(String[] args) {
        Problem1979 problem1979 = new Problem1979();
        int[] nums = {2, 5, 6, 9, 10};
        System.out.println(problem1979.findGCD(nums));
    }

    /**
     * 数学，辗转相除法
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int findGCD(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int max = nums[0];
        int min = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }

        return gcd(max, min);
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数
     * 例如：a=36，b=24
     * 36%24=12 ----> a=24，b=12
     * 24%12=0  ----> a=12，b=0
     * 当b为0时，a即为最大公因数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1) (n：a、b的范围)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        //当b为0时，a即为最大公因数
        while (b != 0) {
            //a/b的余数
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    /**
     * 递归，辗转相除法得到a和b的最大公因数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(logn)=O(1) (n：a、b的范围)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd2(int a, int b) {
        //当b为0时，a即为最大公因数，直接返回
        if (b == 0) {
            return a;
        } else {
            return gcd2(b, a % b);
        }
    }
}
