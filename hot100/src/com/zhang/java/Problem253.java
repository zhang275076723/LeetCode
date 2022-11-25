package com.zhang.java;

import java.util.PriorityQueue;

/**
 * @Date 2022/5/22 9:02
 * @Author zsy
 * @Description 会议室 II 类比Problem56、Problem57、Problem252、Problem406、Problem435、Problem763
 * 给定一个会议时间安排的数组，
 * 每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],…] (si < ei)，
 * 为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。
 * <p>
 * 输入: [[0, 30],[5, 10],[15, 20]]
 * 输出: 2
 * <p>
 * 输入: [[7,10],[2,4]]
 * 输出: 1
 * <p>
 * 1 <= intervals.length <= 10^4
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 10^6
 */
public class Problem253 {
    public static void main(String[] args) {
        Problem253 problem253 = new Problem253();
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println(problem253.minMeetingRooms(intervals));
        System.out.println(problem253.minMeetingRooms2(intervals));
    }

    /**
     * 小根堆，优先队列
     * 按照会议开始时间intervals[i][0]由小到大排序，依次将会议的结束时间放入小根堆，小根堆存放每个会议的结束时间
     * 如果当前会议的开始时间 >= 小根堆堆顶会议的结束时间，则说明可以共享会议室，堆顶会议结束时间出堆，当前会议结束时间入堆；
     * 否则，则说明不能共享会议室，当前会议结束时间入堆
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0 || intervals[0].length == 0) {
            return 0;
        }

        //按照会议开始时间intervals[i][0]由小到大排序
        quickSort(intervals, 0, intervals.length - 1);

        //优先队列，小根堆，存放会议的结束时间
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> o1 - o2);
        //将当前会议的结束时间入小根堆
        priorityQueue.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; ++i) {
            //当前会议的开始时间 >= 小根堆堆顶的会议结束时间，说明可以共享会议室，堆顶元素出堆
            if (intervals[i][0] >= priorityQueue.peek()) {
                priorityQueue.poll();
            }

            //当前会议的结束时间入堆
            priorityQueue.offer(intervals[i][1]);
        }

        return priorityQueue.size();
    }

    /**
     * 按照会议开始时间interval[i][0]由小到大排序，统计不重叠会议个数，
     * 当存在重叠会议时，更新会议右边界为两者中最小值，保证距离下一个会议最远，尽可能和下一个会议不重叠
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms2(int[][] intervals) {
        //按照会议开始时间intervals[i][0]由小到大排序
        quickSort(intervals, 0, intervals.length - 1);

        //不重叠会议的个数
        int count = 1;
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) {
                end = Math.min(end, intervals[i][1]);
            } else {
                count++;
                end = intervals[i][1];
            }
        }

        return intervals.length - count + 1;
    }

    /**
     * 按照interval[i][0]由小到大排序
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
