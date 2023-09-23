package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/9/26 08:47
 * @Author zsy
 * @Description 按位与最大的最长子数组 类比problem898、Problem1521、Problem2411、Problem2444、Problem2447、Problem2470 位运算类比 子序列和子数组类比
 * 给你一个长度为 n 的整数数组 nums 。
 * 考虑 nums 中进行 按位与（bitwise AND）运算得到的值 最大 的 非空 子数组。
 * 换句话说，令 k 是 nums 任意 子数组执行按位与运算所能得到的最大值。
 * 那么，只需要考虑那些执行一次按位与运算后等于 k 的子数组。
 * 返回满足要求的 最长 子数组的长度。
 * 数组的按位与就是对数组中的所有数字进行按位与运算。
 * 子数组 是数组中的一个连续元素序列。
 * <p>
 * 输入：nums = [1,2,3,3,2,2]
 * 输出：2
 * 解释：
 * 子数组按位与运算的最大值是 3 。
 * 能得到此结果的最长子数组是 [3,3]，所以返回 2 。
 * <p>
 * 输入：nums = [1,2,3,4]
 * 输出：1
 * 解释：
 * 子数组按位与运算的最大值是 4 。
 * 能得到此结果的最长子数组是 [4]，所以返回 1 。
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^6
 */
public class Problem2419 {
    public static void main(String[] args) {
        Problem2419 problem2419 = new Problem2419();
        int[] nums = {1, 2, 3, 3, 2, 2};
        System.out.println(problem2419.longestSubarray(nums));
        System.out.println(problem2419.longestSubarray2(nums));
        System.out.println(problem2419.longestSubarray3(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int longestSubarray(int[] nums) {
        //子数组与运算的最大值
        int maxAndResult = nums[0];
        //子数组与运算的最大值的最大长度
        int maxLen = 1;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]与运算结果
            int curAndResult = nums[i];
            //nums[i]-nums[j]与运算结果的长度
            int curLen = 0;

            for (int j = i; j < nums.length; j++) {
                //与运算结果只减不增，curAndResult等于curAndResult和nums[j]与运算结果，
                //则可以继续往后找与运算结果为curAndResult的最长子数组
                if (curAndResult == (curAndResult & nums[j])) {
                    if (curAndResult < maxAndResult) {
                        break;
                    } else if (curAndResult == maxAndResult) {
                        curLen++;
                        maxLen = Math.max(maxLen, curLen);
                    } else {
                        maxAndResult = curAndResult;
                        curLen++;
                        maxLen = curLen;
                    }
                } else {
                    //与运算结果只减不增，curAndResult大于curAndResult和nums[j]与运算结果，
                    //则nums[i]-nums[j]后面与运算结果都小于curAndResult，直接跳出循环，遍历下一个arr[i]
                    break;
                }
            }
        }

        return maxLen;
    }

    /**
     * 动态规划
     * curArr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]与运算结果 (curArr[1] <= j <= curArr[2])
     * curArr[1]：nums[j]-nums[i]与运算结果为curArr[0]的第一个下标索引j
     * curArr[2]：nums[j]-nums[i]与运算结果为curArr[0]的最后一个下标索引j
     * 遍历到nums[i]，从后往前遍历list中数组，curArr[0]和nums[i]求与运算结果，此时curArr[0]表示nums[j]-nums[i-1]与运算结果(curArr[1] <= j <= curArr[2])
     * 如果curArr[0] == curArr[0]&nums[i]，则说明nums[i]表示的二进制数中1，都在nums[j]-nums[i-1](curArr[1] <= j <= curArr[2])与运算结果中出现过，
     * 则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]与运算结果都不会变，
     * 因为随着数组长度变大，与运算结果只减不增，直接跳出循环，遍历下一个nums[i]；
     * 否则，更新curArr[0]，此时curArr[0]表示nums[j]-nums[i]与运算结果(curArr[1] <= j <= curArr[2])，
     * 当前nums[i]遍历结束，通过list中最后一个curArr更新maxLen
     * 时间复杂度O(nlogC)=O(nlog32)=O(n)，空间复杂度O(logC)=O(log32)=O(1) (C：nums中的最大元素，nums元素在int范围内)
     * (以nums[i]结尾的子数组与下一个元素求与运算结果，结果只减不增，每个以nums[i]结尾的子数组最多只会有logC种不同的与运算结果，共nlogC种不同的与运算结果)
     *
     * @param nums
     * @return
     */
    public int longestSubarray2(int[] nums) {
        //子数组与运算的最大值
        int maxAndResult = nums[0];
        //子数组与运算的最大值的最大长度
        int maxLen = 1;
        //curArr[0]：从前往后遍历到nums[i]，nums[j]-nums[i]与运算结果 (curArr[1] <= j <= curArr[2])
        //curArr[1]：nums[j]-nums[i]与运算结果为curArr[0]的第一个下标索引j
        //curArr[2]：nums[j]-nums[i]与运算结果为curArr[0]的最后一个下标索引j
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            //需要和nums[i]求与运算结果的curArr个数
            int size = list.size();
            //nums[i]-nums[i]与运算结果为nums[i]，与运算结果为nums[i]的第一个和最后一个下标索引j都为i，作为新的curArr加入list集合中
            list.add(new int[]{nums[i], i, i});
            //从后往前遍历到的curArr数组的前一个curArr数组，用于合并两个curArr
            int[] preArr = list.get(list.size() - 1);

            //从后往前遍历list中数组，curArr[0]和nums[i]求与运算结果，此时curArr[0]表示nums[j]-nums[i-1]与运算结果(curArr[1] <= j <= curArr[2])
            for (int j = size - 1; j >= 0; j--) {
                //list中当前数组
                int[] curArr = list.get(j);

                //如果curArr[0] == curArr[0]&nums[i]，则说明nums[i]表示的二进制数中1，都在nums[j]-nums[i-1](curArr[1] <= j <= curArr[2])与运算结果中出现过，
                //则nums[0]-nums[i-1]、nums[1]-nums[i-1]...nums[j]-nums[i-1]和nums[i]与运算结果都不会变，
                //因为随着数组长度变大，与运算结果只减不增，直接跳出循环，遍历下一个nums[i]
                if (curArr[0] == (curArr[0] & nums[i])) {
                    //如果当前数组与运算结果curArr[0]和前一个数组与运算结果preArr[0]相等，则合并两个curArr
                    if (curArr[0] == preArr[0]) {
                        curArr[2] = preArr[2];
                        list.remove(j + 1);
                    }

                    break;
                }

                //更新curArr[0]，此时curArr[0]表示nums[j]-nums[i]与运算结果(curArr[1] <= j <= curArr[2])
                curArr[0] = curArr[0] & nums[i];

                //如果当前数组与运算结果curArr[0]和前一个数组与运算结果preArr[0]相等，则合并两个curArr
                if (curArr[0] == preArr[0]) {
                    curArr[2] = preArr[2];
                    list.remove(j + 1);
                }

                //更新前一个数组preArr，用于下次循环
                preArr = curArr;
            }

            //以nums[i]结尾的子数组的最大与运算结果
            int[] curArr = list.get(list.size() - 1);

            //更新子数组与运算的最大值和子数组与运算的最大值的最大长度
            if (maxAndResult < curArr[0]) {
                maxAndResult = curArr[0];
                maxLen = curArr[2] - curArr[1] + 1;
            } else if (maxAndResult == curArr[0]) {
                //更新子数组与运算的最大值的最大长度
                maxLen = Math.max(maxLen, curArr[2] - curArr[1] + 1);
            }
        }

        return maxLen;
    }

    /**
     * 模拟
     * 子数组与运算的最大值即为数组中最大元素组成的连续数组的最大长度
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public int longestSubarray3(int[] nums) {
        //数组中的最大元素
        int max = nums[0];

        for (int num : nums) {
            max = Math.max(max, num);
        }

        //最大元素组成的连续数组的最大长度
        int maxLen = 1;
        int curLen = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == max) {
                curLen++;
                maxLen = Math.max(maxLen, curLen);
            } else {
                curLen = 0;
            }
        }

        return maxLen;
    }
}
