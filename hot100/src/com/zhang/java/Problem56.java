package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2022/4/22 15:22
 * @Author zsy
 * @Description 合并区间 字节面试题 区间类比Problem57、Problem228、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem763、Problem986、Problem1288
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
     * 按照左区间interval[i][0]由小到大排序，
     * 如果当前区间左边界intervals[i][0]小于等于要合并区间的右边界end，则当前区间可以合并，
     * 更新end为intervals[i][1]和end中较大值；
     * 如果当前区间左边界intervals[i][0]大于要合并区间的右边界end，则要合并区间[start,end]加入结果集合，
     * 并重新赋值start为intervals[i][0]，end为intervals[i][1]
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        //按照左区间interval[i][0]由小到大排序
        quickSort(intervals, 0, intervals.length - 1);

        List<int[]> list = new ArrayList<>();
        //要合并区间的左边界
        int start = intervals[0][0];
        //要合并区间的右边界
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            //当前区间左边界intervals[i][0]小于等于要合并区间的右边界end，则当前区间可以合并，更新end
            if (intervals[i][0] <= end) {
                //更新end
                end = Math.max(end, intervals[i][1]);
            } else {
                //当前区间左边界intervals[i][0]大于要合并区间的右边界end，则要合并区间[start,end]加入结果集合，并重新赋值要合并区间的左右边界

                //要合并区间的区间加入结果集合list
                list.add(new int[]{start, end});
                //重新赋值要合并区间的左右边界
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }

        //最后一个区间加入结果集合list
        list.add(new int[]{start, end});

        return list.toArray(new int[list.size()][]);
    }

    /**
     * 按照interval[i][0]由小到大排序
     *
     * @param arr
     * @param left
     * @param right
     */
    private void quickSort(int[][] arr, int left, int right) {
        if (left < right) {
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }

    private int partition(int[][] arr, int left, int right) {
        int[] temp = arr[left];

        while (left < right) {
            while (left < right && arr[right][0] >= temp[0]) {
                right--;
            }
            arr[left] = arr[right];

            while (left < right && arr[left][0] <= temp[0]) {
                left++;
            }
            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
