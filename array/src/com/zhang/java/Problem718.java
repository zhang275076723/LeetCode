package com.zhang.java;

/**
 * @Date 2022/6/30 11:20
 * @Author zsy
 * @Description 最长重复子数组 子序列和子数组类比Problem300、Problem1143
 * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
 * <p>
 * 输入：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
 * 输出：3
 * 解释：长度最长的公共子数组是 [3,2,1] 。
 * <p>
 * 输入：nums1 = [0,0,0,0,0], nums2 = [0,0,0,0,0]
 * 输出：5
 * <p>
 * 1 <= nums1.length, nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 100
 */
public class Problem718 {
    public static void main(String[] args) {
        Problem718 problem718 = new Problem718();
        int[] num1 = {1, 2, 3, 2, 1};
        int[] num2 = {3, 2, 1, 4, 7};
        System.out.println(problem718.findLength(num1, num2));
        System.out.println(problem718.findLength2(num1, num2));
        System.out.println(problem718.findLength3(num1, num2));
        System.out.println(problem718.findLength4(num1, num2));
    }

    /**
     * 暴力，对nums1[i]和nums2[j]查找最长公共子数组长度
     * 时间复杂度O(mn*min(m,n))，空间复杂度O(1) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return 0;
        }

        int max = 0;

        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int index1 = i;
                int index2 = j;
                int curMax = 0;

                while (index1 < nums1.length && index2 < nums2.length && nums1[index1] == nums2[index2]) {
                    index1++;
                    index2++;
                    curMax++;
                }

                if (curMax > max) {
                    max = curMax;
                }
            }
        }

        return max;
    }

    /**
     * 动态规划
     * dp[i][j]：以nums1[i-1]结尾的数组和以nums2[j-1]结尾的数组，两个数组的最长公共子数组长度
     * dp[i][j] = dp[i-1][j-1] + 1 (nums1[i-1] == nums2[j-1])
     * dp[i][j] = 0                (nums1[i-1] != nums2[j-1])
     * 时间复杂度O(mn)，空间复杂度O(mn) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return 0;
        }

        int max = 0;
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];

        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }

                max = Math.max(max, dp[i][j]);
            }
        }

        return max;
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(mn)，空间复杂度O(n) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return 0;
        }

        int max = 0;
        int[] dp = new int[nums2.length + 1];

        for (int i = 1; i <= nums1.length; i++) {
            for (int j = nums2.length; j >= 1; j--) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[j] = dp[j - 1] + 1;
                } else {
                    dp[j] = 0;
                }

                max = Math.max(max, dp[j]);
            }
        }

        return max;
    }

    /**
     * 滑动窗口
     * 固定一个数组，另一个数组从前往后滑动，找最长公共子数组长度
     * nums1：        [1,2,3,2,1]
     * nums2：[3,2,1,4,7]
     * max=0
     * <p>
     * nums1：      [1,2,3,2,1]
     * nums2：[3,2,1,4,7]
     * max=0
     * <p>
     * nums1：    [1,2,3,2,1]
     * nums2：[3,2,1,4,7]
     * max=1
     * <p>
     * nums1：  [1,2,3,2,1]
     * nums2：[3,2,1,4,7]
     * max=0
     * <p>
     * nums1：[1,2,3,2,1]
     * nums2：[3,2,1,4,7]
     * max=1
     * <p>
     * nums1：[1,2,3,2,1]
     * nums2：  [3,2,1,4,7]
     * max=0
     * <p>
     * nums1：[1,2,3,2,1]
     * nums2：    [3,2,1,4,7]
     * max=3
     * <p>
     * <p>
     * nums1：[1,2,3,2,1]
     * nums2：      [3,2,1,4,7]
     * max=0
     * <p>
     * nums1：[1,2,3,2,1]
     * nums2：        [3,2,1,4,7]
     * max=0
     * 时间复杂度O((m+n)*min(m,n))，空间复杂度O(1) (m=nums1.length, n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int findLength4(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return 0;
        }

        int max = 0;
        int curMax;

        //nums1开始索引为0，nums2开始位索引从nums2.length-1到0
        for (int j = nums2.length - 1; j >= 0; j--) {
            curMax = maxLength(nums1, nums2, 0, j);
            if (curMax > max) {
                max = curMax;
            }
        }

        //nums1开始位置从1到nums1.length-1，nums2开始位索引为0
        for (int i = 0; i < nums1.length; i++) {
            curMax = maxLength(nums1, nums2, i, 0);
            if (curMax > max) {
                max = curMax;
            }
        }

        return max;
    }

    /**
     * 从指定位置开始找nums1和nums2的最长公共子数组长度
     *
     * @param nums1
     * @param nums2
     * @param i
     * @param j
     * @return
     */
    private int maxLength(int[] nums1, int[] nums2, int i, int j) {
        int max = 0;
        int curMax = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                curMax++;
            } else {
                curMax = 0;
            }

            if (curMax > max) {
                max = curMax;
            }

            i++;
            j++;
        }

        return max;
    }
}
