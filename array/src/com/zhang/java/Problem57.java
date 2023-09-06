package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/11/8 09:04
 * @Author zsy
 * @Description 插入区间 区间类比Problem56、Problem228、Problem252、Problem253、Problem352、Problem406、Problem435、Problem436、Problem763、Problem986、Problem1288
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
     * 分三部分处理
     * 第一部分：小于newIntervals的区间直接加入结果集合
     * 第二部分：和newIntervals相交的区间，合并之后再加入结果集合
     * 第三部分：大于newIntervals的区间直接加入结果集合
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

        //第一部分：小于newIntervals的区间直接加入结果集合
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            list.add(intervals[i]);
            i++;
        }

        //和newIntervals相交区间的左右边界
        int start = newInterval[0];
        int end = newInterval[1];

        //第二部分：和newIntervals相交的区间，合并之后再加入结果集合
        while (i < intervals.length && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }

        list.add(new int[]{start, end});

        //第三部分：大于newIntervals的区间直接加入结果集合
        while (i < intervals.length) {
            list.add(intervals[i]);
            i++;
        }

        return list.toArray(new int[list.size()][]);
    }
}
