package com.zhang.java;

/**
 * @Date 2024/8/29 08:51
 * @Author zsy
 * @Description 摆动序列 类比Problem280、Problem324 类比Problem300、Problem354、Problem491、Problem673、Problem674、Problem1143、Problem2407、Problem2771 数组中的动态规划类比 子序列和子数组类比
 * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 摆动序列 。
 * 第一个差（如果存在的话）可能是正数或负数。
 * 仅有一个元素或者含两个不等元素的序列也视作摆动序列。
 * 例如， [1, 7, 4, 9, 2, 5] 是一个 摆动序列 ，因为差值 (6, -3, 5, -7, 3) 是正负交替出现的。
 * 相反，[1, 4, 7, 2, 5] 和 [1, 7, 4, 5, 5] 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。
 * 子序列 可以通过从原始序列中删除一些（也可以不删除）元素来获得，剩下的元素保持其原始顺序。
 * 给你一个整数数组 nums ，返回 nums 中作为 摆动序列 的 最长子序列的长度 。
 * <p>
 * 输入：nums = [1,7,4,9,2,5]
 * 输出：6
 * 解释：整个序列均为摆动序列，各元素之间的差值为 (6, -3, 5, -7, 3) 。
 * <p>
 * 输入：nums = [1,17,5,10,13,15,10,5,16,8]
 * 输出：7
 * 解释：这个序列包含几个长度为 7 摆动序列。
 * 其中一个是 [1, 17, 10, 13, 10, 16, 8] ，各元素之间的差值为 (16, -7, 3, -3, 6, -8) 。
 * <p>
 * 输入：nums = [1,2,3,4,5,6,7,8,9]
 * 输出：2
 * <p>
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 1000
 */
public class Problem376 {
    public static void main(String[] args) {
        Problem376 problem376 = new Problem376();
        int[] nums = {1, 17, 5, 10, 13, 15, 10, 5, 16, 8};
        System.out.println(problem376.wiggleMaxLength(nums));
        System.out.println(problem376.wiggleMaxLength2(nums));
    }

    /**
     * 动态规划
     * dp1[i]：以nums[i]作为升序结尾的摆动序列的最长子序列长度
     * dp2[i]：以nums[i]作为降序结尾的摆动序列的最长子序列长度
     * dp1[i] = max(dp2[j]+1) (0 <= j < i && nums[j] < nums[i])
     * dp2[i] = max(dp1[j]+1) (0 <= j < i && nums[j] > nums[i])
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int wiggleMaxLength(int[] nums) {
        int[] dp1 = new int[nums.length];
        int[] dp2 = new int[nums.length];
        //dp1初始化，以nums[0]作为升序结尾的摆动序列的最长子序列长度为1
        dp1[0] = 1;
        //dp2初始化，以nums[0]作为降序结尾的摆动序列的最长子序列长度为1
        dp2[0] = 1;
        //最长摆动序列长度，初始化为1
        int maxLen = 1;

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp1[i] = Math.max(dp1[i], dp2[j] + 1);
                } else if (nums[j] > nums[i]) {
                    dp2[i] = Math.max(dp2[i], dp1[j] + 1);
                }
            }

            maxLen = Math.max(maxLen, Math.max(dp1[i], dp2[i]));
        }

        return maxLen;
    }

    /**
     * 动态规划2
     * dp1[i]：nums[0]-nums[i]中以升序结尾的最长摆动序列长度
     * dp2[i]：nums[0]-nums[i]中以降序结尾的最长摆动序列长度
     * dp1[i] = dp1[i-1]                 (nums[i-1] >= nums[i])
     * dp1[i] = max(dp1[i-1],dp2[i-1]+1) (nums[i-1] < nums[i])
     * dp2[i] = dp2[i-1]                 (nums[i-1] <= nums[i])
     * dp2[i] = max(dp2[i-1],dp1[i-1]+1) (nums[i-1] > nums[i])
     * 1、当nums[i-1]==nums[i]，不考虑nums[i]，只考虑nums[0]-nums[i-1]，则dp1[i]=dp1[i-1]
     * 2.1、当nums[i-1]>nums[i]，并且dp2[i-1]的最长摆动序列以nums[i-1]结尾，则dp1[i]=dp1[i-1]
     * 2.2、当nums[i-1]>nums[i]，并且dp1[i-1]的最长摆动序列不以nums[i-1]结尾，假设以nums[j]结尾
     * 2.2.1、当nums[j]>=nums[i]，则dp1[i]=dp1[i-1]
     * 2.2.2、当nums[j]<nums[i]，以nums[j]结尾的降序加上nums[i]变为升序，等于以nums[j]结尾的降序加上nums[i-1]变为升序，
     * 则dp1[i]=dp2[j]+1=dp1[i-1]
     * 3.1、当nums[i-1]<nums[i]，并且dp2[i-1]的最长摆动序列以nums[i-1]结尾，则dp1[i]=max(dp1[i-1],dp2[i-1]+1)
     * 3.2、当nums[i-1]<nums[i]，并且dp2[i-1]的最长摆动序列不以nums[i-1]结尾，假设以nums[j]结尾
     * 3.2.1、当nums[j]>=nums[i]，因为nums[j]>nums[i-1]，所以以nums[j]为结尾的降序可以替换为以nums[i-1]为结尾的降序，
     * 加上nums[i]变为升序，则dp1[i]=max(dp1[i-1],dp2[i-1]+1)
     * 3.2.2、当nums[j]<nums[i]，以nums[j]为结尾的降序加上nums[i]变为升序，则dp1[i]=max(dp1[i-1],dp2[i-1]+1)
     * dp2[i]推理的过程同理
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public int wiggleMaxLength2(int[] nums) {
        int[] dp1 = new int[nums.length];
        int[] dp2 = new int[nums.length];
        //dp1初始化，nums[0]-nums[i]中以升序结尾的最长摆动序列长度为1
        dp1[0] = 1;
        //dp2初始化，nums[0]-nums[i]中以降序结尾的最长摆动序列长度为1
        dp2[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                dp1[i] = Math.max(dp1[i - 1], dp2[i - 1] + 1);
                dp2[i] = dp2[i - 1];
            } else if (nums[i - 1] > nums[i]) {
                dp1[i] = dp1[i - 1];
                dp2[i] = Math.max(dp2[i - 1], dp1[i - 1] + 1);
            } else {
                dp1[i] = dp1[i - 1];
                dp2[i] = dp2[i - 1];
            }
        }

        return Math.max(dp1[nums.length - 1], dp2[nums.length - 1]);
    }
}
