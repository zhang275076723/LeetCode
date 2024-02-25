package com.zhang.java;


/**
 * @Date 2022/5/22 9:19
 * @Author zsy
 * @Description 会议室 区间类比Problem56、Problem57、Problem163、Problem228、Problem253、Problem352、Problem406、Problem435、Problem436、Problem632、Problem763、Problem855、Problem986、Problem1288、Problem2402
 * 给定一个会议时间安排的数组 intervals ，
 * 每个会议时间都会包括开始和结束的时间 intervals[i] = [starti, endi] ，
 * 请你判断一个人是否能够参加这里面的全部会议。
 * <p>
 * 输入: intervals = [[0,30],[5,10],[15,20]]
 * 输出: false
 * 解释: 存在重叠区间，一个人在同一时刻只能参加一个会议。
 * <p>
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
     * 按照会议开始时间intervals[i][0]由小到大排序，
     * 如果当前会议的开始时间intervals[i][0]小于当前不重叠的区间最右边界end，则会议有重叠，返回false；
     * 如果当前会议的开始时间intervals[i][0]大于等于当前不重叠的区间最右边界end，则会议没有重叠，更新end为intervals[i][1]
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return true;
        }

        //按照会议开始时间intervals[i][0]由小到大排序
        mergeSort(intervals, 0, intervals.length - 1, new int[intervals.length][2]);

        //当前不重叠的区间最右边界
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            //当前会议的开始时间小于end，则会议有重叠，返回false
            if (intervals[i][0] < end) {
                return false;
            } else {
                //当前会议的开始时间大于等于end，则会议没有重叠，更新end
                end = intervals[i][1];
            }
        }

        return true;
    }

    /**
     * 按照会议开始时间intervals[i][0]由小到大排序
     *
     * @param arr
     * @param left
     * @param right
     * @param tempArr
     */
    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(arr, left, mid, tempArr);
            mergeSort(arr, mid + 1, right, tempArr);
            merge(arr, left, mid, right, tempArr);
        }
    }

    private void merge(int[][] arr, int left, int mid, int right, int[][] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i][0] < arr[j][0]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
