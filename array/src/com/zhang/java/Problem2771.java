package com.zhang.java;

/**
 * @Date 2024/12/3 08:53
 * @Author zsy
 * @Description 构造最长非递减子数组 类比Problem300、Problem354、Problem376、Problem491、Problem673、Problem674、Problem1143、Problem2407 动态规划类比 子序列和子数组类比
 * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，长度均为 n 。
 * 让我们定义另一个下标从 0 开始、长度为 n 的整数数组，nums3 。
 * 对于范围 [0, n - 1] 的每个下标 i ，你可以将 nums1[i] 或 nums2[i] 的值赋给 nums3[i] 。
 * 你的任务是使用最优策略为 nums3 赋值，以最大化 nums3 中 最长非递减子数组 的长度。
 * 以整数形式表示并返回 nums3 中 最长非递减 子数组的长度。
 * 注意：子数组 是数组中的一个连续非空元素序列。
 * <p>
 * 输入：nums1 = [2,3,1], nums2 = [1,2,1]
 * 输出：2
 * 解释：构造 nums3 的方法之一是：
 * nums3 = [nums1[0], nums2[1], nums2[2]] => [2,2,1]
 * 从下标 0 开始到下标 1 结束，形成了一个长度为 2 的非递减子数组 [2,2] 。
 * 可以证明 2 是可达到的最大长度。
 * <p>
 * 输入：nums1 = [1,3,2,1], nums2 = [2,2,3,4]
 * 输出：4
 * 解释：构造 nums3 的方法之一是：
 * nums3 = [nums1[0], nums2[1], nums2[2], nums2[3]] => [1,2,3,4]
 * 整个数组形成了一个长度为 4 的非递减子数组，并且是可达到的最大长度。
 * <p>
 * 输入：nums1 = [1,1], nums2 = [2,2]
 * 输出：2
 * 解释：构造 nums3 的方法之一是：
 * nums3 = [nums1[0], nums1[1]] => [1,1]
 * 整个数组形成了一个长度为 2 的非递减子数组，并且是可达到的最大长度。
 * <p>
 * 1 <= nums1.length == nums2.length == n <= 10^5
 * 1 <= nums1[i], nums2[i] <= 10^9
 */
public class Problem2771 {
    public static void main(String[] args) {
        Problem2771 problem2771 = new Problem2771();
//        int[] nums1 = {1, 3, 2, 1};
//        int[] nums2 = {2, 2, 3, 4};
        int[] nums1 = {2, 3, 1};
        int[] nums2 = {1, 2, 1};
        System.out.println(problem2771.maxNonDecreasingLength(nums1, nums2));
        System.out.println(problem2771.maxNonDecreasingLengthLIS(nums1, nums2));
    }

    /**
     * 动态规划
     * dp1[i]：nums3[i]以nums1[i]结尾的最长非递减子数组的长度
     * dp2[i]：nums3[i]以nums2[i]结尾的最长非递减子数组的长度
     * dp1[i] = max(dp1[i-1]+1) (nums1[i-1] <= nums1[i])
     * dp1[i] = max(dp2[i-1]+1) (nums2[i-1] <= nums1[i])
     * dp2[i] = max(dp1[i-1]+1) (nums1[i-1] <= nums2[i])
     * dp2[i] = max(dp2[i-1]+1) (nums2[i-1] <= nums2[i])
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int maxNonDecreasingLength(int[] nums1, int[] nums2) {
        int[] dp1 = new int[nums1.length];
        int[] dp2 = new int[nums1.length];
        //dp初始化
        dp1[0] = 1;
        dp2[0] = 1;
        //nums3最长非递减子数组的长度
        int maxLen = 1;

        for (int i = 1; i < nums1.length; i++) {
            dp1[i] = 1;
            dp2[i] = 1;

            if (nums1[i - 1] <= nums1[i]) {
                dp1[i] = Math.max(dp1[1], dp1[i - 1] + 1);
            }

            if (nums2[i - 1] <= nums1[i]) {
                dp1[i] = Math.max(dp1[i], dp2[i - 1] + 1);
            }

            if (nums1[i - 1] <= nums2[i]) {
                dp2[i] = Math.max(dp2[i], dp1[i - 1] + 1);
            }

            if (nums2[i - 1] <= nums2[i]) {
                dp2[i] = Math.max(dp2[i], dp2[i - 1] + 1);
            }

            maxLen = Math.max(maxLen, Math.max(dp1[i], dp2[i]));
        }

        return maxLen;
    }

    /**
     * 思考题：求nums3中最长非递减子序列的长度
     * <p>
     * 动态规划
     * dp1[i]：nums3[i]以nums1[i]结尾的最长非递减子序列的长度
     * dp2[i]：nums3[i]以nums2[i]结尾的最长非递减子序列的长度
     * dp1[i] = max(max(dp1[j]+1)) (0 < j < i，nums1[j] <= nums1[i])
     * dp1[i] = max(max(dp2[j]+1)) (0 < j < i，nums2[j] <= nums1[i])
     * dp2[i] = max(max(dp1[j]+1)) (0 < j < i，nums1[j] <= nums2[i])
     * dp2[i] = max(max(dp2[j]+1)) (0 < j < i，nums2[j] <= nums2[i])
     * 时间复杂度O(n^2)，空间复杂度O(n) (可以通过二分查找优化时间复杂度O(nlogn))
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int maxNonDecreasingLengthLIS(int[] nums1, int[] nums2) {
        int[] dp1 = new int[nums1.length];
        int[] dp2 = new int[nums1.length];
        //dp初始化
        dp1[0] = 1;
        dp2[0] = 1;
        //nums3最长非递减子序列的长度
        int maxLen = 1;

        for (int i = 1; i < nums1.length; i++) {
            dp1[i] = 1;
            dp2[i] = 1;

            for (int j = 0; j < i; j++) {
                if (nums1[j] <= nums1[i]) {
                    dp1[i] = Math.max(dp1[i], dp1[j] + 1);
                }

                if (nums2[j] <= nums1[i]) {
                    dp1[i] = Math.max(dp1[i], dp2[j] + 1);
                }

                if (nums1[j] <= nums2[i]) {
                    dp2[i] = Math.max(dp2[i], dp1[j] + 1);
                }

                if (nums2[j] <= nums2[i]) {
                    dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, Math.max(dp1[i], dp2[i]));
        }

        return maxLen;
    }
}
