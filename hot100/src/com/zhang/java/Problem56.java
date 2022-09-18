package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2022/4/22 15:22
 * @Author zsy
 * @Description 合并区间 类比Problem252、Problem253、Problem406 字节面试题
 * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
 * <p>
 * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出：[[1,6],[8,10],[15,18]]
 * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * <p>
 * 输入：intervals = [[1,4],[4,5]]
 * 输出：[[1,5]]
 * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * <p>
 * 1 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 10^4
 */
public class Problem56 {
    public static void main(String[] args) {
        Problem56 problem56 = new Problem56();
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        System.out.println(Arrays.deepToString(problem56.merge(intervals)));
    }

    /**
     * 先按照interval[i][0]由小到大排序，然后再合并相交区间
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][2];
        }

        if (intervals.length == 1) {
            return intervals;
        }

        quickSort(intervals, 0, intervals.length - 1);

        List<int[]> list = new ArrayList<>();

        int i = 0;

        //排序后，找相交区间，进行合并
        while (i < intervals.length) {
            //当前区间左端点
            int left = intervals[i][0];
            //当前区间右端点
            int right = intervals[i][1];

            //找相交区间，更新右端点
            while (i + 1 < intervals.length && intervals[i + 1][0] <= right) {
                right = Math.max(right, intervals[i + 1][1]);
                i++;
            }

            list.add(new int[]{left, right});
            i++;
        }

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 按照interval[i][0]排序
     *
     * @param intervals
     * @param left
     * @param right
     */
    private void quickSort(int[][] intervals, int left, int right) {
        if (left < right) {
            int pivot = partition(intervals, left, right);
            quickSort(intervals, left, pivot - 1);
            quickSort(intervals, pivot + 1, right);
        }
    }

    private int partition(int[][] intervals, int left, int right) {
        int[] temp = intervals[left];

        while (left < right) {
            while (left < right && intervals[right][0] >= temp[0]) {
                right--;
            }
            intervals[left] = intervals[right];

            while (left < right && intervals[left][0] <= temp[0]) {
                left++;
            }
            intervals[right] = intervals[left];
        }

        intervals[left] = temp;

        return left;
    }
}
