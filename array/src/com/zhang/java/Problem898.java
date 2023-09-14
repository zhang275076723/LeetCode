package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/8/26 08:54
 * @Author zsy
 * @Description 子数组按位或操作 猿辅导机试题 类比Problem134 类比Problem2411、Problem2447、Problem2470 位运算类比Problem29、Problem136、Problem137、Problem190、Problem191、Problem201、Problem231、Problem260、Problem271、Problem326、Problem342、Problem371、Problem389、Problem405、Problem461、Problem477、Problem645、Problem1290、Offer15、Offer56、Offer56_2、Offer64、Offer65、IpToInt 子数组类比
 * 我们有一个非负整数数组 arr 。
 * 对于每个（连续的）子数组 sub = [arr[i], arr[i + 1], ..., arr[j]] （ i <= j），
 * 我们对 sub 中的每个元素进行按位或操作，获得结果 arr[i] | arr[i + 1] | ... | arr[j] 。
 * 返回可能结果的数量。
 * 多次出现的结果在最终答案中仅计算一次。
 * <p>
 * 输入：arr = [0]
 * 输出：1
 * 解释：
 * 只有一个可能的结果 0 。
 * <p>
 * 输入：arr = [1,1,2]
 * 输出：3
 * 解释：
 * 可能的子数组为 [1]，[1]，[2]，[1, 1]，[1, 2]，[1, 1, 2]。
 * 产生的结果为 1，1，2，1，3，3 。
 * 有三个唯一值，所以答案是 3 。
 * <p>
 * 输入：arr = [1,2,4]
 * 输出：6
 * 解释：
 * 可能的结果是 1，2，3，4，6，以及 7 。
 * <p>
 * 1 <= nums.length <= 5 * 10^4
 * 0 <= nums[i] <= 10^9
 */
public class Problem898 {
    public static void main(String[] args) {
        Problem898 problem898 = new Problem898();
//        int[] arr = {1, 1, 2};
        int[] arr = {1, 11, 6, 11};
        System.out.println(problem898.subarrayBitwiseORs(arr));
        System.out.println(problem898.subarrayBitwiseORs2(arr));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(nlogC)=O(nlog32)=O(n) (C：arr中的最大数，arr元素在int范围内)
     * (arr[i]与后面的元素进行或运算，结果只增不减，每个arr[i]最多只会有logC种不同的或运算结果，共nlogC种不同的或运算结果)
     *
     * @param arr
     * @return
     */
    public int subarrayBitwiseORs(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        //存放子数组不同的或运算结果
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            //nums[i]-nums[j]或运算结果
            int orResult = 0;

            for (int j = i; j < arr.length; j++) {
                orResult = orResult | arr[j];
                set.add(orResult);
            }
        }

        return set.size();
    }

    /**
     * 动态规划
     * dp[j]：从前往后遍历到arr[i]，此时dp[j]表示arr[j]-arr[i]或运算结果 (0 <= j < i)
     * 遍历到arr[i]，从后往前dp[j]分别和arr[i]进行或运算，此时dp[j]表示arr[j]-arr[i-1]或运算结果，
     * 如果dp[j]|arr[i] == dp[j]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1]或运算结果中出现过，
     * 则同样也在arr[j-1]-arr[i-1]、arr[j-2]-arr[i-1]...arr[0]-arr[i-1]或运算结果中出现过，因为随着数组长度变大，
     * 或运算结果只增不减，直接跳出循环，遍历下一个arr[i]；否则，更新dp[j]，此时dp[j]表示arr[j]-arr[i]或运算结果
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(nlogC)=O(nlog32)=O(n) (C：arr中的最大元素，arr元素在int范围内)
     * (arr[i]与后面的元素进行或运算，结果只增不减，每个arr[i]与其他元素最多只会有logC种不同的或运算结果，共nlogC种不同的或运算结果)
     *
     * @param arr
     * @return
     */
    public int subarrayBitwiseORs2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        //存放子数组不同的或运算结果
        Set<Integer> set = new HashSet<>();
        //dp[j]：从前往后遍历到arr[i]，此时dp[j]表示arr[j]-arr[i]或运算结果 (0 <= j < i)
        int[] dp = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            //arr[i]-arr[i]或运算结果为arr[i]
            dp[i] = arr[i];
            //当前arr[i]-arr[i]或运算结果加入set中
            set.add(arr[i]);

            //从后往前dp[j]分别和arr[i]进行或运算，此时dp[j]表示arr[j]-arr[i-1]或运算结果
            for (int j = i - 1; j >= 0; j--) {
                //如果dp[j]|arr[i] == dp[j]，则说明arr[i]表示的二进制数中1，都在arr[j]-arr[i-1]或运算结果中出现过，
                //则同样也在arr[j-1]-arr[i-1]、arr[j-2]-arr[i-1]...arr[0]-arr[i-1]或运算结果中出现过，因为随着数组长度变大，
                //或运算结果只增不减，直接跳出循环，遍历下一个arr[i]
                if ((dp[j] | arr[i]) == dp[j]) {
                    break;
                }

                //更新dp[j]，此时dp[j]表示arr[j]-arr[i]或运算结果
                dp[j] = dp[j] | arr[i];
                //当前dp[j]表示arr[j]-arr[i]或运算结果，没有出现过，加入set中
                set.add(dp[j]);
            }
        }

        return set.size();
    }
}
