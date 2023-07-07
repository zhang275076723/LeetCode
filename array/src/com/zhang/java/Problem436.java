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
     * 对每个区间，遍历所有区间，找左边界大于等于当前区间右边界，且距离当前区间右边界最近的区间下标索引
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
     * 按照区间左边界newIntervals[i][0]对newIntervals由小到大排序，因为每个区间的左边界不同，对排好序的newIntervals，
     * 每个intervals区间，对newIntervals二分查找大于等于当前区间右边界，且距离当前区间右边界最近区间的下标索引newIntervals[j][1]
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

        //按照区间左边界newIntervals[0]由小到大排序
        mergeSort(newIntervals, 0, newIntervals.length - 1, new int[newIntervals.length][2]);

        int[] result = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            int left = 0;
            int right = newIntervals.length - 1;
            int mid;
            int target = intervals[i][1];
            //大于等于当前区间右边界intervals[i][1]，且距离intervals[i][1]最近区间的下标索引
            //赋初值为-1，表示不存在大于等于当前区间右边界intervals[i][1]的区间
            int index = -1;

            //二分查找大于等于当前区间右边界intervals[i][1]，且距离intervals[i][1]最近区间的下标索引index
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                //当前二分查找的区间左边界小于当前区间右边界，继续往右边找
                if (newIntervals[mid][0] < target) {
                    left = mid + 1;
                } else {
                    //当前二分查找的区间左边界大于等于当前区间右边界，更新index，继续往左边找，是否能找到更小的index
                    index = newIntervals[mid][1];
                    right = mid - 1;
                }
            }

            result[i] = index;
        }

        return result;
    }

    private void mergeSort(int[][] arr, int left, int right, int[][] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
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
