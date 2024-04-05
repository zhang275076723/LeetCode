package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/7/15 08:50
 * @Author zsy
 * @Description 最接近原点的 K 个点 快排划分类比Problem215、Problem324、Problem347、Offer40 优先队列类比
 * 给定一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点，
 * 并且是一个整数 k ，返回离原点 (0,0) 最近的 k 个点。
 * 这里，平面上两点之间的距离是 欧几里德距离((x1-x2)^2+(y1-y2)^2)^(1/2)。
 * 你可以按 任何顺序 返回答案。
 * 除了点坐标的顺序之外，答案 确保 是 唯一 的。
 * <p>
 * 输入：points = [[1,3],[-2,2]], k = 1
 * 输出：[[-2,2]]
 * 解释：
 * (1, 3) 和原点之间的距离为 sqrt(10)，
 * (-2, 2) 和原点之间的距离为 sqrt(8)，
 * 由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。
 * 我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
 * <p>
 * 输入：points = [[3,3],[5,-1],[-2,4]], k = 2
 * 输出：[[3,3],[-2,4]]
 * （答案 [[-2,4],[3,3]] 也会被接受。）
 * <p>
 * 1 <= k <= points.length <= 10^4
 * -10^4 < xi, yi < 10^4
 */
public class Problem973 {
    public static void main(String[] args) {
        Problem973 problem973 = new Problem973();
        int[][] points = {{3, 3}, {5, -1}, {-2, 4}};
        int k = 2;
        System.out.println(Arrays.deepToString(problem973.kClosest(points, k)));
        System.out.println(Arrays.deepToString(problem973.kClosest2(points, k)));
        System.out.println(Arrays.deepToString(problem973.kClosest3(points, k)));
    }

    /**
     * 快排划分
     * 左边都小于划分值，右边都大于划分值，即一次划分可以确定数组前k小元素的值
     * 期望时间复杂度O(n)，期望空间复杂度O(logn)
     * 最坏时间复杂度O(n^2)，最坏空间复杂度O(n)
     *
     * @param points
     * @param k
     * @return
     */
    public int[][] kClosest(int[][] points, int k) {
        //arr[i][0]：当前点距离原点距离的平方，arr[i][1]：当前点在points数组中的下标索引
        int[][] arr = new int[points.length][2];

        for (int i = 0; i < points.length; i++) {
            arr[i] = new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i};
        }

        int left = 0;
        int right = arr.length - 1;
        int pivot = partition(arr, left, right);

        while (pivot + 1 != k) {
            //基准pivot在前k小元素之前
            if (pivot + 1 < k) {
                left = pivot + 1;
                pivot = partition(arr, left, right);
            } else {
                //基准pivot在前k小元素之后
                right = pivot - 1;
                pivot = partition(arr, left, right);
            }
        }

        int[][] result = new int[k][2];

        for (int i = 0; i < k; i++) {
            //当前点在points数组中的下标索引
            int index = arr[i][1];
            result[i] = new int[]{points[index][0], points[index][1]};
        }

        return result;
    }

    /**
     * 大根堆
     * 时间复杂度O(nlogk)，空间复杂度O(k)
     *
     * @param points
     * @param k
     * @return
     */
    public int[][] kClosest2(int[][] points, int k) {
        //大根堆，使用数组存放元素，int[0]：当前点距离原点距离的平方，int[1]：当前点在points数组中的下标索引
        Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr2[0] - arr1[0];
            }
        });

        for (int i = 0; i < points.length; i++) {
            queue.offer(new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i});
            //大根堆大小超过k时，堆顶元素出堆，保证大根堆中保存距离原点最近的k个节点
            if (queue.size() > k) {
                queue.poll();
            }
        }

        int[][] result = new int[k][2];

        for (int i = 0; i < k; i++) {
            //当前点在points数组中的下标索引
            int index = queue.poll()[1];
            result[i] = new int[]{points[index][0], points[index][1]};
        }

        return result;
    }

    /**
     * 手动实现大根堆
     * 时间复杂度O(nlogk)，空间复杂度O(k)
     *
     * @param points
     * @param k
     * @return
     */
    public int[][] kClosest3(int[][] points, int k) {
        //大小为k的大根堆，arr[i][0]：当前点距离原点距离的平方，arr[i][1]：当前点在points数组中的下标索引
        int[][] arr = new int[k][2];

        for (int i = 0; i < k; i++) {
            arr[i] = new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i};
        }

        //建立大小为k的大根堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = k; i < points.length; i++) {
            //当前点距离原点距离的平方小于大根堆堆顶点距离原点距离的平方，
            //说明堆顶元素不是距离原点最近的k个节点，替换堆顶元素，再进行整堆
            if (points[i][0] * points[i][0] + points[i][1] * points[i][1] < arr[0][0]) {
                arr[0] = new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i};
                heapify(arr, 0, arr.length);
            }
        }

        int[][] result = new int[k][2];

        for (int i = 0; i < k; i++) {
            //当前点在points数组中的下标索引
            int index = arr[i][1];
            result[i] = new int[]{points[index][0], points[index][1]};
        }

        return result;
    }

    private int partition(int[][] arr, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int[] value = arr[left];
        arr[left] = arr[randomIndex];
        arr[randomIndex] = value;

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

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[leftIndex][0] > arr[index][0]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex][0] > arr[index][0]) {
            index = rightIndex;
        }

        if (index != i) {
            int[] temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
