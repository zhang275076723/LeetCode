package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/2/19 09:03
 * @Author zsy
 * @Description 最小时间差 类比Problem401、Problem635、Problem2224 类比Problem128、Problem506、Problem561、Problem628、Problem747
 * 给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
 * <p>
 * 输入：timePoints = ["23:59","00:00"]
 * 输出：1
 * <p>
 * 输入：timePoints = ["00:00","23:59","00:00"]
 * 输出：0
 * <p>
 * 2 <= timePoints.length <= 2 * 10^4
 * timePoints[i] 格式为 "HH:MM"
 */
public class Problem539 {
    public static void main(String[] args) {
        Problem539 problem539 = new Problem539();
        List<String> timePoints = new ArrayList<String>() {{
            add("00:00");
            add("23:59");
            add("00:00");
        }};
        System.out.println(problem539.findMinDifference(timePoints));
    }

    /**
     * 排序
     * 时间转化为分钟由小到大排序，相邻时间差和首尾时间差的最小值即为最小时间差
     * 时间复杂度O(nlogn)，空间复杂度O(n) (归并排序的空间复杂度O(n))
     *
     * @param timePoints
     * @return
     */
    public int findMinDifference(List<String> timePoints) {
        //优化：鸽巢原理，24小时共1440分钟，最极端情况有1440种不同的分钟数，
        //当时间个数大于1440时，肯定存在两个时间相等，即最小时间差为0
        if (timePoints.size() > 1440) {
            return 0;
        }

        int[] arr = new int[timePoints.size()];

        for (int i = 0; i < timePoints.size(); i++) {
            String time = timePoints.get(i);
            //当前时间对应的分钟数
            int minute = 0;
            minute = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
            arr[i] = minute;
        }

        mergeSort(arr, 0, arr.length - 1, new int[arr.length]);

        //最小时间差为相邻时间差和首尾时间差的最小值
        //注意：首尾时间差是第一个元素减去最后一个元素再加上24小时，即加上24*60=1440分钟
        int min = arr[0] - arr[arr.length - 1] + 1440;

        for (int i = 1; i < arr.length; i++) {
            min = Math.min(min, arr[i] - arr[i - 1]);
        }

        return min;
    }

    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);

        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
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
