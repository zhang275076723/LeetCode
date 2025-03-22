package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Date 2024/11/25 08:23
 * @Author zsy
 * @Description 使数组相等的最小开销 类比Problem462、Problem1685、Problem2602、Problem2607、Problem2615、Problem2967、Problem3107
 * 给你两个下标从 0 开始的数组 nums 和 cost ，分别包含 n 个 正 整数。
 * 你可以执行下面操作 任意 次：
 * 将 nums 中 任意 元素增加或者减小 1 。
 * 对第 i 个元素执行一次操作的开销是 cost[i] 。
 * 请你返回使 nums 中所有元素 相等 的 最少 总开销。
 * <p>
 * 输入：nums = [1,3,5,2], cost = [2,3,1,14]
 * 输出：8
 * 解释：我们可以执行以下操作使所有元素变为 2 ：
 * - 增加第 0 个元素 1 次，开销为 2 。
 * - 减小第 1 个元素 1 次，开销为 3 。
 * - 减小第 2 个元素 3 次，开销为 1 + 1 + 1 = 3 。
 * 总开销为 2 + 3 + 3 = 8 。
 * 这是最小开销。
 * <p>
 * 输入：nums = [2,2,2,2,2], cost = [4,2,8,1,3]
 * 输出：0
 * 解释：数组中所有元素已经全部相等，不需要执行额外的操作。
 * <p>
 * n == nums.length == cost.length
 * 1 <= n <= 10^5
 * 1 <= nums[i], cost[i] <= 10^6
 * 测试用例确保输出不超过 2^53-1。
 */
public class Problem2448 {
    public static void main(String[] args) {
        Problem2448 problem2448 = new Problem2448();
        int[] nums = {1, 3, 5, 2};
        int[] cost = {2, 3, 1, 14};
        System.out.println(problem2448.minCost(nums, cost));
    }

    /**
     * 排序+模拟
     * 核心思想：将nums[i]每次操作的开销cost[i]转换为cost[i]个nums[i]每次操作的开销
     * arr先按照arr[0]由小到大排序，再按照arr[1]由小到大排序，转换后的数组中元素都变为中位数的操作次数即为nums中所有元素相等需要的最小开销
     * arr[0]：nums[i]，arr[1]：cost[i]，相当于转换后的数组中nums[i]出现的次数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     * <p>
     * 例如：nums = [1,3,5,2], cost = [2,3,1,14]
     * 原数组转换为2个1,3个3,1个5,14个2构成的长度为20的数组，找新数组的中位数2，即这20个元素都变为2即得到最小开销
     *
     * @param nums
     * @param cost
     * @return
     */
    public long minCost(int[] nums, int[] cost) {
        //arr[0]：nums[i]，arr[1]：cost[i]，相当于转换后的数组中nums[i]出现的次数
        int[][] arr = new int[nums.length][2];
        //转换后的数组的长度，即为cost[i]元素之和
        //使用long，避免int溢出
        long length = 0;

        for (int i = 0; i < nums.length; i++) {
            arr[i] = new int[]{nums[i], cost[i]};
            length = length + cost[i];
        }

        //先按照arr[0]由小到大排序，再按照arr[1]由小到大排序
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                if (arr1[0] != arr2[0]) {
                    return arr1[0] - arr2[0];
                } else {
                    return arr1[1] - arr2[1];
                }
            }
        });

        //遍历到当前转换后的数组中元素的个数
        //使用long，避免int溢出
        long curCount = 0;
        //转换后的数组的中位数
        int median = arr[0][0];

        for (int i = 0; i < arr.length; i++) {
            curCount = curCount + arr[i][1];

            //遍历到当前转换后的数组中元素的个数大于，则找到了转换后的数组的中位数
            if (curCount > length / 2) {
                median = arr[i][0];
                break;
            }
        }

        long result = 0;

        for (int i = 0; i < nums.length; i++) {
            result = result + (long) Math.abs(nums[i] - median) * cost[i];
        }

        return result;
    }
}
