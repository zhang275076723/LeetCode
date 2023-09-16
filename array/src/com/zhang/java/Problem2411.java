package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2023/9/14 08:27
 * @Author zsy
 * @Description 按位或最大的最小子数组长度 类比Problem898、Problem2447、Problem2470 子序列和子数组类比
 * 给你一个长度为 n 下标从 0 开始的数组 nums ，数组中所有数字均为非负整数。
 * 对于 0 到 n - 1 之间的每一个下标 i ，你需要找出 nums 中一个 最小 非空子数组，它的起始位置为 i （包含这个位置），
 * 同时有 最大 的 按位或运算值 。
 * 换言之，令 Bij 表示子数组 nums[i...j] 的按位或运算的结果，你需要找到一个起始位置为 i 的最小子数组，
 * 这个子数组的按位或运算的结果等于 max(Bik) ，其中 i <= k <= n - 1 。
 * 一个数组的按位或运算值是这个数组里所有数字按位或运算的结果。
 * 请你返回一个大小为 n 的整数数组 answer，其中 answer[i]是开始位置为 i ，按位或运算结果最大，且 最短 子数组的长度。
 * 子数组 是数组里一段连续非空元素组成的序列。
 * <p>
 * 输入：nums = [1,0,2,1,3]
 * 输出：[3,3,2,2,1]
 * 解释：
 * 任何位置开始，最大按位或运算的结果都是 3 。
 * - 下标 0 处，能得到结果 3 的最短子数组是 [1,0,2] 。
 * - 下标 1 处，能得到结果 3 的最短子数组是 [0,2,1] 。
 * - 下标 2 处，能得到结果 3 的最短子数组是 [2,1] 。
 * - 下标 3 处，能得到结果 3 的最短子数组是 [1,3] 。
 * - 下标 4 处，能得到结果 3 的最短子数组是 [3] 。
 * 所以我们返回 [3,3,2,2,1] 。
 * <p>
 * 输入：nums = [1,2]
 * 输出：[2,1]
 * 解释：
 * 下标 0 处，能得到最大按位或运算值的最短子数组长度为 2 。
 * 下标 1 处，能得到最大按位或运算值的最短子数组长度为 1 。
 * 所以我们返回 [2,1] 。
 * <p>
 * n == nums.length
 * 1 <= n <= 10^5
 * 0 <= nums[i] <= 10^9
 */
public class Problem2411 {
    public static void main(String[] args) {
        Problem2411 problem2411 = new Problem2411();
        int[] nums = {1, 0, 2, 1, 3};
        System.out.println(Arrays.toString(problem2411.smallestSubarrays(nums)));
        System.out.println(Arrays.toString(problem2411.smallestSubarrays2(nums)));
        System.out.println(Arrays.toString(problem2411.smallestSubarrays3(nums)));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int[] smallestSubarrays(int[] nums) {
        int[] result = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]的最大或运算结果
            int maxOrResult = nums[i];
            //初始化以nums[i]起始的最大或运算结果的最小子数组长度为1
            result[i] = 1;

            for (int j = i; j < nums.length; j++) {
                if (maxOrResult != (maxOrResult | nums[j])) {
                    result[i] = j - i + 1;
                }
                maxOrResult = maxOrResult | nums[j];
            }
        }

        return result;
    }

    /**
     * 动态规划
     * dp[j]：从前往后遍历到nums[i]，此时dp[j]表示nums[j]-nums[i]或运算结果 (0 <= j < i)
     * 遍历到nums[i]，从后往前dp[j]和nums[i]进行或运算，此时dp[j]表示nums[j]-nums[i-1]或运算结果，
     * 如果dp[j] == dp[j]|nums[i]，则说明nums[i]表示的二进制数中1，都在nums[j]-nums[i-1]或运算结果中出现过，
     * 则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]或运算结果都不会变，
     * 因为随着数组长度变大，或运算结果只增不减，直接跳出循环，遍历下一个nums[i]；
     * 否则，更新dp[j]和result[j]，此时dp[j]表示nums[j]-nums[i]或运算结果
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(n) (C：nums中的最大元素，nums元素在int范围内)
     * (以nums[i]结尾的子数组与下一个元素求或运算结果，结果只增不减，每个以nums[i]结尾的子数组最多只会有logC种不同的或运算结果，共nlogC种不同的或运算结果)
     *
     * @param nums
     * @return
     */
    public int[] smallestSubarrays2(int[] nums) {
        int[] result = new int[nums.length];
        //dp[j]：从前往后遍历到nums[i]，此时dp[j]表示nums[j]-nums[i]或运算结果 (0 <= j < i)
        int[] dp = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            //初始化以nums[i]起始的最大或运算结果的最小子数组长度为1
            result[i] = 1;
            //nums[i]-nums[i]或运算结果为nums[i]
            dp[i] = nums[i];

            //从后往前dp[j]和nums[i]进行或运算，此时dp[j]表示nums[j]-nums[i-1]或运算结果
            for (int j = i - 1; j >= 0; j--) {
                //如果dp[j] == dp[j]|nums[i]，则说明nums[i]表示的二进制数中1，都在nums[j]-nums[i-1]或运算结果中出现过，
                //则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]或运算结果都不会变，
                //因为随着数组长度变大，或运算结果只增不减，直接跳出循环，遍历下一个nums[i]
                if (dp[j] == (dp[j] | nums[i])) {
                    break;
                }

                //更新dp[j]，此时dp[j]表示nums[j]-nums[i]或运算结果
                dp[j] = dp[j] | nums[i];
                //更新result[j]，即以nums[j]起始的最大或运算结果的最小子数组长度为i-j+1
                result[j] = i - j + 1;
            }
        }

        return result;
    }

    /**
     * 动态规划
     * arr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]或运算结果 (arr[1] <= j <= arr[2])
     * arr[1]：nums[j]-nums[i]或运算结果为arr[0]的第一个下标索引j
     * arr[2]：nums[j]-nums[i]或运算结果为arr[0]的最后一个下标索引j
     * 遍历到nums[i]，从后往前遍历list中数组，arr[0]和nums[i]求或运算结果，此时arr[0]表示nums[j]-nums[i-1]或运算结果(arr[1] <= j <= arr[2])
     * 如果arr[0] == arr[0]|num[i]，则说明nums[i]表示的二进制数中1，都在nums[j]-nums[i-1](arr[1] <= j <= arr[2])或运算结果中出现过，
     * 则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]或运算结果都不会变，
     * 因为随着数组长度变大，或运算结果只增不减，直接跳出循环，遍历下一个nums[i]；
     * 否则，更新arr[0]，此时arr[0]表示nums[j]-nums[i]或运算结果(arr[1] <= j <= arr[2])，
     * 更新以nums[k]起始的最大或运算结果的最小子数组长度(arr[1] <= k <= arr[2])
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(logC)=O(log32)=O(1) (C：nums中的最大元素，nums元素在int范围内)
     * (以nums[i]结尾的子数组与下一个元素求或运算结果，结果只增不减，每个以nums[i]结尾的子数组最多只会有logC种不同的或运算结果，共nlogC种不同的或运算结果)
     *
     * @param nums
     * @return
     */
    public int[] smallestSubarrays3(int[] nums) {
        int[] result = new int[nums.length];
        //arr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]或运算结果 (arr[1] <= j <= arr[2])
        //arr[1]：nums[j]-nums[i]或运算结果为arr[0]的第一个下标索引j
        //arr[2]：nums[j]-nums[i]或运算结果为arr[0]的最后一个下标索引j
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //初始化以nums[i]起始的最大或运算结果的最小子数组长度为1
            result[i] = 1;

            //需要和nums[i]求或运算结果的arr个数
            int size = list.size();
            //nums[i]-nums[i]或运算结果为nums[i]，或运算结果为nums[i]的第一个和最后一个下标索引j都为i，作为新的arr加入list集合中
            list.add(new int[]{nums[i], i, i});
            //从后往前遍历到的arr数组的前一个arr数组，用于合并两个arr
            int[] preArr = list.get(list.size() - 1);

            //从后往前遍历list中数组，arr[0]和nums[i]求或运算结果，此时arr[0]表示nums[j]-nums[i-1]或运算结果(arr[1] <= j <= arr[2])
            for (int j = size - 1; j >= 0; j--) {
                //当前arr数组
                int[] arr = list.get(j);

                //如果arr[0] == arr[0]|num[i]，则说明nums[i]表示的二进制数中1，都在nums[j]-nums[i-1](arr[1] <= j <= arr[2])或运算结果中出现过，
                //则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]或运算结果都不会变，
                //因为随着数组长度变大，或运算结果只增不减，直接跳出循环，遍历下一个nums[i]
                if (arr[0] == (arr[0] | nums[i])) {
                    //如果当前数组或运算结果arr[0]和前一个数组或运算结果preArr[0]相等，则合并两个arr
                    if (arr[0] == preArr[0]) {
                        arr[2] = preArr[2];
                        list.remove(j + 1);
                    }

                    break;
                }

                //更新arr[0]，此时arr[0]表示nums[j]-nums[i]或运算结果(arr[1] <= j <= arr[2])
                arr[0] = arr[0] | nums[i];

                //更新以nums[k]起始的最大或运算结果的最小子数组长度(arr[1] <= k <= arr[2])
                for (int k = arr[1]; k <= arr[2]; k++) {
                    result[k] = i - k + 1;
                }

                //如果当前数组或运算结果arr[0]和前一个数组或运算结果preArr[0]相等，则合并两个arr
                if (arr[0] == preArr[0]) {
                    arr[2] = preArr[2];
                    list.remove(j + 1);
                }

                //更新前一个数组preArr，用于下次循环
                preArr = arr;
            }
        }

        return result;
    }
}
