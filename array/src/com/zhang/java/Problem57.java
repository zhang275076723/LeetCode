package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Date 2022/11/8 09:04
 * @Author zsy
 * @Description 插入区间 区间类比Problem56、Problem228、Problem252、Problem253、Problem406、Problem435、Problem436、Problem763、Problem986、Problem1288
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
     * 比newInterval小，即在newInterval左边的区间先加入结果集合，
     * 之后的区间如果和newInterval重叠，则重叠部分合并之后加入结果集合
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

        //newInterval之前的区间，即区间右边界intervals[i][1]小于要插入的区间左边界newInterval[0]，这些区间加入结果集合list
        while (i < intervals.length && intervals[i][1] < newInterval[0]) {
            list.add(intervals[i]);
            i++;
        }

        //要合并区间的左边界
        int start;
        //要合并区间的右边界
        int end = newInterval[1];

        //newInterval后面还有区间，start为newInterval[0]、intervals[i][0]两者中较小值
        if (i < intervals.length) {
            start = Math.min(newInterval[0], intervals[i][0]);
        } else {
            //newInterval后面没有区间，start为intervals[i][0]
            start = newInterval[0];
        }

        while (i < intervals.length) {
            //当前区间左边界intervals[i][0]小于等于要合并区间的右边界end，则当前区间可以合并，更新end
            if (intervals[i][0] <= end) {
                end = Math.max(end, intervals[i][1]);
            } else {
                //当前区间左边界intervals[i][0]大于要合并区间的右边界end，则要合并区间[start,end]加入结果集合，并重新赋值要合并区间的左右边界
                list.add(new int[]{start, end});
                start = intervals[i][0];
                end = intervals[i][1];
            }

            i++;
        }

        list.add(new int[]{start, end});

        return list.toArray(new int[list.size()][]);
    }
}
