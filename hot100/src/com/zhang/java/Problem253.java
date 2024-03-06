package com.zhang.java;

import java.util.PriorityQueue;

/**
 * @Date 2022/5/22 9:02
 * @Author zsy
 * @Description 会议室 II 携程机试题 差分数组类比Problem370、Problem1109 优先队列类比Problem407、Problem630 区间类比Problem56、Problem57、Problem163、Problem228、Problem252、Problem352、Problem406、Problem435、Problem436、Problem632、Problem763、Problem855、Problem986、Problem1288、Problem2402
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
//        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        int[][] intervals = {{8, 12}, {1, 10}, {11, 30}, {3, 19}, {2, 7}, {10, 20}};
        System.out.println(problem253.minMeetingRooms(intervals));
        System.out.println(problem253.minMeetingRooms2(intervals));
    }

    /**
     * 差分数组 (差分数组在O(1)进行区间修改，但区间查询需要O(n)；前缀数组在O(1)进行区间查询，但区间修改需要O(n)，两者互为逆操作)
     * diff[i] = arr[i] - arr[i-1]
     * arr[i]=diff[0]+diff[1]+...+diff[i-1]+diff[i]
     * 区间[left,right]每个元素加上value，则diff[left]=diff[left]+value，diff[right]=diff[right]-value
     * 时间复杂度O(n)，空间复杂度O(max(intervals[i][1]))
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        //会议的最大结束时间
        int max = 0;

        for (int i = 0; i < intervals.length; i++) {
            max = Math.max(max, intervals[i][1]);
        }

        //差分数组
        int[] diff = new int[max];

        for (int i = 0; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            diff[start]++;

            if (end < diff.length) {
                diff[end]--;
            }
        }

        //所需的最少会议室个数，初始化为时间0需要的会议室个数
        int count = diff[0];
        //diff[0]-diff[i]之和，即为当前时间i需要的会议室个数
        int sum = 0;

        for (int i = 1; i < max; i++) {
            sum = sum + diff[i];
            count = Math.max(count, sum);
        }

        return count;
    }

    /**
     * 排序+优先队列(小根堆)
     * 按照会议开始时间intervals[i][0]由小到大排序，将会议的结束时间放入小根堆，小根堆中元素个数即为所需的最少会议室
     * 根据当前会议的开始时间和堆顶会议的结束时间的关系，分为以下2种情况：
     * 1、当前会议的开始时间大于等于堆顶会议的结束时间，则当前会议和堆顶会议可以共享会议室，堆顶会议结束时间出堆，当前会议结束时间入堆；
     * 2、当前会议的开始时间小于堆顶会议的结束时间，则当前会议和堆顶会议不能共享会议室，当前会议需要一个新的会议室，当前会议结束时间入堆
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms2(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        //按照会议开始时间intervals[i][0]由小到大排序
        quickSort(intervals, 0, intervals.length - 1);

        //优先队列，小根堆，存放会议的结束时间intervals[i][1]
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((a, b) -> a - b);
        //当前会议的结束时间入小根堆
        priorityQueue.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            //当前会议的开始时间大于等于堆顶会议的结束时间，则当前会议和堆顶会议可以共享会议室，堆顶会议结束时间出堆，当前会议结束时间入堆
            if (intervals[i][0] >= priorityQueue.peek()) {
                priorityQueue.poll();
                priorityQueue.offer(intervals[i][1]);
            } else {
                //当前会议的开始时间小于堆顶会议的结束时间，则当前会议和堆顶会议不能共享会议室，当前会议需要一个新的会议室，当前会议结束时间入堆
                priorityQueue.offer(intervals[i][1]);
            }
        }

        //小根堆中存放的会议结束时间之间互不冲突，个数即为所需的最少会议室
        return priorityQueue.size();
    }

    /**
     * 按照会议开始时间interval[i][0]由小到大排序
     *
     * @param arr
     * @param left
     * @param right
     */
    private void quickSort(int[][] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
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
