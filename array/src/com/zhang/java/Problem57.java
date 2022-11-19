package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/11/8 09:04
 * @Author zsy
 * @Description 插入区间 类比Problem56、Problem252、Problem253、Problem406、Problem435、Problem763
 * 给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
 * 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
 * <p>
 * 输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 输出：[[1,5],[6,9]]
 * <p>
 * 输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * 输出：[[1,2],[3,10],[12,16]]
 * 解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10]重叠。
 * <p>
 * 输入：intervals = [], newInterval = [5,7]
 * 输出：[[5,7]]
 * <p>
 * 输入：intervals = [[1,5]], newInterval = [2,3]
 * 输出：[[1,5]]
 * <p>
 * 输入：intervals = [[1,5]], newInterval = [2,3]
 * 输出：[[1,5]]
 * <p>
 * 0 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= intervals[i][0] <= intervals[i][1] <= 10^5
 * intervals 根据 intervals[i][0] 按 升序 排列
 * newInterval.length == 2
 * 0 <= newInterval[0] <= newInterval[1] <= 10^5
 */
public class Problem57 {
    public static void main(String[] args) {
        Problem57 problem57 = new Problem57();
        int[][] intervals = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newIntervals = {4, 8};
        System.out.println(Arrays.deepToString(problem57.insert(intervals, newIntervals)));
    }

    /**
     * 先找到要插入区间newInterval在原区间数组中的位置，如果能进行合并则往右进行合并
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals == null || intervals.length == 0) {
            return new int[][]{newInterval};
        }

        List<int[]> list = new ArrayList<>();

        int i = 0;

        //newInterval之前的区间
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            list.add(intervals[i]);
            i++;
        }

        int left = newInterval[0];
        int right = newInterval[1];

        //找到newInterval可以合并的区间
        while (i < intervals.length && intervals[i][0] <= right) {
            left = Math.min(left, intervals[i][0]);
            right = Math.max(right, intervals[i][1]);
            i++;
        }

        list.add(new int[]{left, right});

        //newInterval之后的区间
        while (i < intervals.length) {
            list.add(intervals[i]);
            i++;
        }

        return list.toArray(new int[list.size()][]);
    }
}
