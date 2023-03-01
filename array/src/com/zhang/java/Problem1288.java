package com.zhang.java;

/**
 * @Date 2022/12/4 11:58
 * @Author zsy
 * @Description 删除被覆盖区间 区间类比Problem56、Problem57、Problem228、Problem252、Problem253、Problem406、Problem435、Problem436、Problem763、Problem986
 * 给你一个区间列表，请你删除列表中被其他区间所覆盖的区间。
 * 只有当 c <= a 且 b <= d 时，我们才认为区间 [a,b) 被区间 [c,d) 覆盖。
 * 在完成所有删除操作后，请你返回列表中剩余区间的数目。
 * <p>
 * 输入：intervals = [[1,4],[3,6],[2,8]]
 * 输出：2
 * 解释：区间 [3,6] 被区间 [2,8] 覆盖，所以它被删除了。
 * <p>
 * 1 <= intervals.length <= 1000
 * 0 <= intervals[i][0] < intervals[i][1] <= 10^5
 * 对于所有的 i != j：intervals[i] != intervals[j]
 */
public class Problem1288 {
    public static void main(String[] args) {
        Problem1288 problem1288 = new Problem1288();
        int[][] intervals = {{1, 4}, {3, 6}, {2, 8}};
        System.out.println(problem1288.removeCoveredIntervals(intervals));
    }

    /**
     * 按照左区间intervals[i][0]由小到大排序，再按照intervals[i][1]由大到小排序，根据当前区间删除之后能够覆盖的范围
     * 时间复杂度O(nlogn)，空间复杂度O(logn) (堆排序的空间复杂度为O(logn))
     *
     * @param intervals
     * @return
     */
    public int removeCoveredIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        //左区间intervals[i][0]由小到大排序，再按照intervals[i][1]由大到小排序
        heapSort(intervals);

        int count = intervals.length;
        int right = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            //right大于等于当前区间右边界，则当前区间被覆盖，可以删除
            if (right >= intervals[i][1]) {
                count--;
            } else {
                right = intervals[i][1];
            }
        }

        return count;
    }

    private void heapSort(int[][] arr) {
        //建堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        //堆顶元素和堆尾元素交换，在整堆，得到有序数组
        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    /**
     * 大根堆整堆
     * 一维按照由大到小排序，二维按照由小到大排序
     *
     * @param arr
     * @param i
     * @param heapSize
     */
    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;

        if (2 * i + 1 < heapSize && (arr[2 * i + 1][0] > arr[index][0] ||
                (arr[2 * i + 1][0] == arr[index][0] && arr[2 * i + 1][1] < arr[index][1]))) {
            index = 2 * i + 1;
        }

        if (2 * i + 2 < heapSize && (arr[2 * i + 2][0] > arr[index][0] ||
                (arr[2 * i + 2][0] == arr[index][0] && arr[2 * i + 2][1] < arr[index][1]))) {
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
