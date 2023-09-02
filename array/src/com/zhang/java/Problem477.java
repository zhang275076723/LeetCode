package com.zhang.java;

/**
 * @Date 2023/7/18 08:25
 * @Author zsy
 * @Description 汉明距离总和 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem645、Problem898、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt
 * 两个整数的 汉明距离 指的是这两个数字的二进制数对应位不同的数量。
 * 给你一个整数数组 nums，请你计算并返回 nums 中任意两个数之间 汉明距离的总和 。
 * <p>
 * 输入：nums = [4,14,2]
 * 输出：6
 * 解释：在二进制表示中，4 表示为 0100 ，14 表示为 1110 ，2表示为 0010 。（这样表示是为了体现后四位之间关系）
 * 所以答案为：HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6
 * <p>
 * 输入：nums = [4,14,4]
 * 输出：4
 * <p>
 * 1 <= nums.length <= 10^4
 * 0 <= nums[i] <= 10^9
 * 给定输入的对应答案符合 32-bit 整数范围
 */
public class Problem477 {
    public static void main(String[] args) {
        Problem477 problem477 = new Problem477();
        int[] nums = {4, 12, 2};
        System.out.println(problem477.totalHammingDistance(nums));
        System.out.println(problem477.totalHammingDistance2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int totalHammingDistance(int[] nums) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                //nums[i]和nums[j]异或结果，统计其中1的个数，即为这两个数的汉明距离
                int num = nums[i] ^ nums[j];

                while (num != 0) {
                    count = count + (num & 1);
                    num = num >>> 1;
                }
            }
        }

        return count;
    }

    /**
     * 模拟
     * 如果一个数二进制的某一位为1，则其他数和当前数当前位的汉明距离为其他数当前位为0的个数；
     * 如果一个数二进制的某一位为0，则其他数和当前数当前位的汉明距离为其他数当前位为1的个数，
     * 所有数某一位的汉明距离之和为，当前位为1的个数乘以当前位为0的个数
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int totalHammingDistance2(int[] nums) {
        int count = 0;

        //数组中元素都是int，范围为32位
        for (int i = 0; i < 32; i++) {
            //数组中元素当前位为0的个数
            int count0 = 0;
            //数组中元素当前位为1的个数
            int count1 = 0;

            for (int num : nums) {
                if (((num >>> i) & 1) == 0) {
                    count0++;
                } else {
                    count1++;
                }
            }

            //当前位汉明距离之和为，当前位为1的个数乘以当前位为0的个数
            count = count + count0 * count1;
        }

        return count;
    }
}
