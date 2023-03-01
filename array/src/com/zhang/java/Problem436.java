package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2022/12/3 12:03
 * @Author zsy
 * @Description 寻找右区间 区间类比Problem56、Problem57、Problem228、Problem252、Problem253、Problem406、Problem435、Problem763、Problem986、Problem1288
 * 给你一个区间数组 intervals ，其中 intervals[i] = [starti, endi] ，且每个 starti 都 不同 。
 * 区间 i 的 右侧区间 可以记作区间 j ，并满足 startj >= endi ，且 startj 最小化 。
 * 返回一个由每个区间 i 的 右侧区间 在 intervals 中对应下标组成的数组。
 * 如果某个区间 i 不存在对应的 右侧区间 ，则下标 i 处的值设为 -1 。
 * <p>
 * 输入：intervals = [[1,2]]
 * 输出：[-1]
 * 解释：集合中只有一个区间，所以输出-1。
 * <p>
 * 输入：intervals = [[3,4],[2,3],[1,2]]
 * 输出：[-1,0,1]
 * 解释：对于 [3,4] ，没有满足条件的“右侧”区间。
 * 对于 [2,3] ，区间[3,4]具有最小的“右”起点;
 * 对于 [1,2] ，区间[2,3]具有最小的“右”起点。
 * <p>
 * 输入：intervals = [[1,4],[2,3],[3,4]]
 * 输出：[-1,2,-1]
 * 解释：对于区间 [1,4] 和 [3,4] ，没有满足条件的“右侧”区间。
 * 对于 [2,3] ，区间 [3,4] 有最小的“右”起点。
 * <p>
 * 1 <= intervals.length <= 2 * 10^4
 * intervals[i].length == 2
 * -10^6 <= starti <= endi <= 10^6
 * 每个间隔的起点都 不相同
 */
public class Problem436 {
    public static void main(String[] args) {
        Problem436 problem436 = new Problem436();
        int[][] intervals = {{3, 4}, {2, 3}, {1, 2}};
        System.out.println(Arrays.toString(problem436.findRightInterval(intervals)));
        System.out.println(Arrays.toString(problem436.findRightInterval2(intervals)));
    }

    /**
     * 暴力
     * 对每个区间，找所有区间中左边界大于等于当前区间右边界，且距离当前区间右边界最近的区间下标索引
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param intervals
     * @return
     */
    public int[] findRightInterval(int[][] intervals) {
        int[] result = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            int index = -1;

            //找所有区间中左边界大于等于当前区间右边界，且距离当前区间右边界最近的区间下标索引
            for (int j = 0; j < intervals.length; j++) {
                if (intervals[i][1] <= intervals[j][0]) {
                    if (index == -1) {
                        index = j;
                    } else {
                        if (intervals[j][0] < intervals[index][0]) {
                            index = j;
                        }
                    }
                }
            }

            result[i] = index;
        }

        return result;
    }

    /**
     * 暴力优化，二分查找
     * 创建新的二维数组newIntervals，newIntervals[i][0]：区间左边界，newIntervals[i][1]：区间在intervals中的下标索引
     * 按照newIntervals[i][0]对newIntervals由小到大排序，
     * 对每个区间，二分查找newIntervals中左边界大于等于当前区间右边界，且距离当前区间右边界最近的区间下标索引
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param intervals
     * @return
     */
    public int[] findRightInterval2(int[][] intervals) {
        //newIntervals[i][0]：区间左边界，newIntervals[i][1]：区间在intervals中的下标索引
        int[][] newIntervals = new int[intervals.length][2];

        for (int i = 0; i < intervals.length; i++) {
            newIntervals[i][0] = intervals[i][0];
            newIntervals[i][1] = i;
        }

        //根据newIntervals[0]由小到大排序
        heapSort(newIntervals);

        int[] result = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            int left = 0;
            int right = intervals.length - 1;
            int mid;
            //赋初值为-1，在没有找到大于等于当前区间右边界的区间时，赋值为-1
            int target = -1;

            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (newIntervals[mid][0] < intervals[i][1]) {
                    left = mid + 1;
                } else {
                    //newIntervals中左边界大于等于当前区间右边界，更新target
                    target = newIntervals[mid][1];
                    right = mid - 1;
                }
            }

            result[i] = target;
        }

        return result;
    }

    private void heapSort(int[][] arr) {
        //建堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        //堆顶元素和最后一个元素交换，再整堆，得到升序数组
        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;

        if (2 * i + 1 < heapSize && arr[2 * i + 1][0] > arr[index][0]) {
            index = 2 * i + 1;
        }

        if (2 * i + 2 < heapSize && arr[2 * i + 2][0] > arr[index][0]) {
            index = 2 * i + 2;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
