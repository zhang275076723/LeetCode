package com.zhang.java;

/**
 * @Date 2024/10/24 08:32
 * @Author zsy
 * @Description 割绳子 字节面试题 二分查找类比
 * 给定一个整数数组 ribbons 和一个整数 k，数组每项 ribbons[i] 表示第 i 条绳子的长度。
 * 对于每条绳子，你可以将任意切割成一系列长度为 正整数 的部分，或者选择不进行切割。
 * 例如，如果给你一条长度为 4 的绳子，你可以：
 * 保持绳子的长度为 4 不变；
 * 切割成一条长度为 3 和一条长度为 1 的绳子；
 * 切割成两条长度为 2 的绳子；
 * 切割成一条长度为 2 和两条长度为 1 的绳子；
 * 切割成四条长度为 1 的绳子。
 * 你的任务是找出最大 x 值，要求满足可以裁切出至少 k 条长度均为 x 的绳子。
 * 你可以丢弃裁切后剩余的任意长度的绳子。
 * 如果不可能切割出 k 条相同长度的绳子，返回 0。
 * <p>
 * 输入: ribbons = [9,7,5], k = 3
 * 输出: 5
 * 解释:
 * - 把第一条绳子切成两部分，一条长度为 5，一条长度为 4；
 * - 把第二条绳子切成两部分，一条长度为 5，一条长度为 2；
 * - 第三条绳子不进行切割；
 * 现在，你得到了 3 条长度为 5 的绳子。
 * <p>
 * 输入: ribbons = [7,5,9], k = 4
 * 输出: 4
 * 解释:
 * - 把第一条绳子切成两部分，一条长度为 4，一条长度为 3；
 * - 把第二条绳子切成两部分，一条长度为 4，一条长度为 1；
 * - 把第二条绳子切成三部分，一条长度为 4，一条长度为 4，还有一条长度为 1；
 * 现在，你得到了 4 条长度为 4 的绳子。
 * <p>
 * 输入: ribbons = [5,7,9], k = 22
 * 输出: 0
 * 解释: 由于绳子长度需要为正整数，你无法得到 22 条长度相同的绳子。
 * <p>
 * 1 <= ribbons.length <= 10^5
 * 1 <= ribbons[i] <= 10^5
 * 1 <= k <= 10^9
 */
public class Problem1891 {
    public static void main(String[] args) {
        Problem1891 problem1891 = new Problem1891();
//        int[] ribbons = {9, 7, 5};
//        int k = 3;
        int[] ribbons = {4, 7, 2, 10, 5};
        int k = 5;
        System.out.println(problem1891.maxLength(ribbons, k));
    }

    /**
     * 二分查找变形
     * 对[left,right]进行二分查找，left为1，right为ribbons最大值，统计数组中能够切割长度为mid的绳子个数count，
     * 如果count小于k，则能够切割为k个相同长度绳子的最大长度在mid左边，right=mid-1
     * 如果count大于等于k，则能够切割为k个相同长度绳子的最大长度在mid或mid右边，left=mid；
     * 时间复杂度O(n*log(max(ribbons[i])))=O(n)，空间复杂度O(1)
     *
     * @param ribbons
     * @param k
     * @return
     */
    public int maxLength(int[] ribbons, int k) {
        int max = ribbons[0];
        int sum = 0;

        for (int num : ribbons) {
            max = Math.max(max, num);
            sum = sum + num;
        }

        //最多只能切割为sum个长度为1的绳子，如果sum小于k，则无法切割为k个绳子，返回0
        if (sum < k) {
            return 0;
        }

        int left = 1;
        int right = max;
        int mid;

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            //数组中能够切割长度为mid的绳子个数
            int count = 0;

            for (int num : ribbons) {
                count = count + num / mid;
            }

            if (count < k) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }

        return left;
    }
}
