package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2024/11/24 08:06
 * @Author zsy
 * @Description 使子数组元素和相等 类比Problem462、Problem1685、Problem2448、Problem2602、Problem2615、Problem2967、Problem3107
 * 给你一个下标从 0 开始的整数数组 arr 和一个整数 k 。数组 arr 是一个循环数组。
 * 换句话说，数组中的最后一个元素的下一个元素是数组中的第一个元素，数组中第一个元素的前一个元素是数组中的最后一个元素。
 * 你可以执行下述运算任意次：
 * 选中 arr 中任意一个元素，并使其值加上 1 或减去 1 。
 * 执行运算使每个长度为 k 的 子数组 的元素总和都相等，返回所需要的最少运算次数。
 * 子数组 是数组的一个连续部分。
 * <p>
 * 输入：arr = [1,4,1,3], k = 2
 * 输出：1
 * 解释：在下标为 1 的元素那里执行一次运算，使其等于 3 。
 * 执行运算后，数组变为 [1,3,1,3] 。
 * - 0 处起始的子数组为 [1, 3] ，元素总和为 4
 * - 1 处起始的子数组为 [3, 1] ，元素总和为 4
 * - 2 处起始的子数组为 [1, 3] ，元素总和为 4
 * - 3 处起始的子数组为 [3, 1] ，元素总和为 4
 * <p>
 * 输入：arr = [2,5,5,7], k = 3
 * 输出：5
 * 解释：在下标为 0 的元素那里执行三次运算，使其等于 5 。在下标为 3 的元素那里执行两次运算，使其等于 5 。
 * 执行运算后，数组变为 [5,5,5,5] 。
 * - 0 处起始的子数组为 [5, 5, 5] ，元素总和为 15
 * - 1 处起始的子数组为 [5, 5, 5] ，元素总和为 15
 * - 2 处起始的子数组为 [5, 5, 5] ，元素总和为 15
 * - 3 处起始的子数组为 [5, 5, 5] ，元素总和为 15
 * <p>
 * 1 <= k <= arr.length <= 10^5
 * 1 <= arr[i] <= 10^9
 */
public class Problem2607 {
    public static void main(String[] args) {
        Problem2607 problem2607 = new Problem2607();
        int[] arr = {1, 4, 1, 3};
        int k = 2;
        System.out.println(problem2607.makeSubKSumEqual(arr, k));
    }

    /**
     * 模拟+排序
     * arr[i]和arr[i+1]起始的长度为k的子数组元素之和相等，即arr[i]+arr[i+1]+...+arr[i+k-1]=arr[i+1]+...+arr[i+k-1]+arr[i+k]，
     * 则arr[i]=arr[i+k]，所以每隔k个元素为一组，如果i+k数组越界，则需要模arr.length进行分组
     * 每组元素都相等的最少运算次数，即每组元素都变为每组元素中位数的运算次数
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param arr
     * @param k
     * @return
     */
    public long makeSubKSumEqual(int[] arr, int k) {
        long count = 0;
        boolean[] visited = new boolean[arr.length];

        for (int i = 0; i < arr.length; i++) {
            if (visited[i]) {
                continue;
            }

            List<Integer> list = new ArrayList<>();
            list.add(arr[i]);
            visited[i] = true;

            //和arr[i]为一组中的下一个元素下标索引
            int index = (i + k) % arr.length;

            //每隔k个元素为一组
            while (index != i) {
                list.add(arr[index]);
                visited[index] = true;
                index = (index + k) % arr.length;
            }

            //由小到大排序
            list.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });

            //当前组的中位数
            int median = list.get(list.size() / 2);

            //每组元素都相等的最少运算次数，即每组元素都变为每组元素中位数的运算次数
            for (int num : list) {
                count = count + Math.abs(num - median);
            }
        }

        return count;
    }
}
