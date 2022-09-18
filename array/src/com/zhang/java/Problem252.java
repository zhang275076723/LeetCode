package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Date 2022/5/22 9:19
 * @Author zsy
 * @Description 会议室 类比Problem56、Problem253、Problem406
 * 给定一个会议时间安排的数组 intervals ，
 * 每个会议时间都会包括开始和结束的时间 intervals[i] = [starti, endi] ，
 * 请你判断一个人是否能够参加这里面的全部会议。
 * <p>
 * 示例 1:
 * 输入: intervals = [[0,30],[5,10],[15,20]]
 * 输出: false
 * 解释: 存在重叠区间，一个人在同一时刻只能参加一个会议。
 * <p>
 * 示例 2:
 * 输入: intervals = [[7,10],[2,4]]
 * 输出: true
 * 解释: 不存在重叠区间。
 * <p>
 * 1 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 10^6
 */
public class Problem252 {
    public static void main(String[] args) {
        Problem252 problem252 = new Problem252();
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println(problem252.canAttendMeetings(intervals));
    }

    /**
     * 按照interval[i][0](会议开始时间)由小到大排序，end记录之前会议的结束时间，
     * 如果当前会议的开始时间小于之前记录的会议结束时间，说明两个会议有重叠，返回false
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0 || intervals[0].length == 0) {
            return true;
        }

        mergeSort(intervals, 0, intervals.length - 1, new int[intervals.length][2]);

        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            //当前会议开始时间小于end，说明两个会议时间相交
            if (intervals[i][0] < end) {
                return false;
            }

            end = intervals[i][1];
        }

        return true;
    }

    /**
     * 按照interval[i][0]由小到大排序
     *
     * @param intervals
     * @param left
     * @param right
     * @param tempArr
     */
    private void mergeSort(int[][] intervals, int left, int right, int[][] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(intervals, left, mid, tempArr);
            mergeSort(intervals, mid + 1, right, tempArr);
            merge(intervals, left, mid, right, tempArr);
        }
    }

    private void merge(int[][] intervals, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (intervals[i][0] < intervals[j][0]) {
                tempArr[k] = intervals[i];
                i++;
                k++;
            } else {
                tempArr[k] = intervals[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = intervals[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = intervals[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            intervals[k] = tempArr[k];
        }
    }
}
