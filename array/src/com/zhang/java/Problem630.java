package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2023/10/19 08:12
 * @Author zsy
 * @Description 课程表 III 类比Problem207、Problem210、Problem1462 优先队列类比Problem253、Problem407 692 23 264 313 373 378 668 719 786 1508 871 973 2402 632 239 1424 502 659 621 767 358 355
 * 这里有 n 门不同的在线课程，按从 1 到 n 编号。
 * 给你一个数组 courses ，其中 courses[i] = [durationi, lastDayi] 表示第 i 门课将会 持续 上 durationi 天课，
 * 并且必须在不晚于 lastDayi 的时候完成。
 * 你的学期从第 1 天开始。
 * 且不能同时修读两门及两门以上的课程。
 * 返回你最多可以修读的课程数目。
 * <p>
 * 输入：courses = [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
 * 输出：3
 * 解释：
 * 这里一共有 4 门课程，但是你最多可以修 3 门：
 * 首先，修第 1 门课，耗费 100 天，在第 100 天完成，在第 101 天开始下门课。
 * 第二，修第 3 门课，耗费 1000 天，在第 1100 天完成，在第 1101 天开始下门课程。
 * 第三，修第 2 门课，耗时 200 天，在第 1300 天完成。
 * 第 4 门课现在不能修，因为将会在第 3300 天完成它，这已经超出了关闭日期。
 * <p>
 * 输入：courses = [[1,2]]
 * 输出：1
 * <p>
 * 输入：courses = [[3,2],[4,3]]
 * 输出：0
 * <p>
 * 1 <= courses.length <= 10^4
 * 1 <= durationi, lastDayi <= 10^4
 */
public class Problem630 {
    public static void main(String[] args) {
        Problem630 problem630 = new Problem630();
//        int[][] courses = {{100, 200}, {200, 1300}, {1000, 1250}, {2000, 3200}};
//        int[][] courses = {{5, 15}, {3, 19}, {6, 7}, {2, 10}, {5, 16}, {8, 14}, {10, 11}, {2, 19}};
        int[][] courses = {{7, 17}, {3, 12}, {10, 20}, {9, 10}, {5, 20}, {10, 19}, {4, 18}};
        System.out.println(problem630.scheduleCourse(courses));
    }

    /**
     * 排序+优先队列(大根堆)
     * 核心思想：加上当前课程的上课时间的总时间大于当前课程结束时间，并且大根堆堆顶课程需要的上课时间大于当前课程需要的上课时间，则当前课程需要的上课时间替换大根堆堆顶课程需要的上课时间
     * 按照课程结束时间courses[i][1]由小到大排序，将课程需要的上课时间courses[i][0]入堆，大根堆中元素个数即为最多可以上的课程数量
     * 计算加上当前课程的上课时间的总时间time，根据当前课程需要的上课时间、当前课程结束时间和time的关系，分为以下2种情况：
     * 1、time+courses[i][0]<=courses[i][1]，则当前课程可以学习，更新time=time+courses[i][0]，courses[i][0]入堆；
     * 2、time+courses[i][0]>courses[i][1]，则加上当前课程的上课时间的总时间超过课程结束时间，
     * 如果大根堆堆顶课程需要的上课时间peekTime大于当前课程需要的上课时间courses[i][0]，
     * 则当前课程需要的上课时间courses[i][0]替换大根堆堆顶课程需要的上课时间peekTime，使time变小，尽可能多的课程入大根堆，
     * 大根堆堆顶课程需要的上课时间peekTime出堆，time=time-peekTime+courses[i][0]，courses[i][0]入堆
     * 注意：此时替换之后加上当前课程的上课时间的总时间time小于课程结束时间courses[i][1]，即替换之后当前课程可以添加，
     * 因为peekTime>courses[i][0]，则替换之后time小于替换之前time，又因为课程是按照结束时间courses[i][1]由小到大排序，
     * 替换之前课程结束时间小于替换之后课程结束时间，替换之前time原本就小于等于替换之前课程结束时间，则替换之后time肯定小于替换之后课程结束时间
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param courses
     * @return
     */
    public int scheduleCourse(int[][] courses) {
        //按照课程结束时间courses[i][1]由小到大排序
        //用于当前课程需要的上课时间替换大根堆堆顶课程需要的上课时间后，总时间time小于课程结束时间，即替换之后当前课程可以添加
        heapSort(courses);

        //优先队列，大根堆，存放课程需要的上课时间courses[i][0]
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        //加上当前课程的上课时间的总时间
        int time = 0;

        for (int i = 0; i < courses.length; i++) {
            //time+courses[i][0]<=courses[i][1]，则当前课程可以学习，更新time=time+courses[i][0]，courses[i][0]入堆
            if (time + courses[i][0] <= courses[i][1]) {
                time = time + courses[i][0];
                priorityQueue.offer(courses[i][0]);
            } else {
                //time+courses[i][0]>courses[i][1]，则加上当前课程的上课时间的总时间超过课程结束时间，
                //如果大根堆堆顶课程需要的上课时间peekTime大于当前课程需要的上课时间courses[i][0]，
                //则当前课程需要的上课时间courses[i][0]替换大根堆堆顶课程需要的上课时间peekTime，使time变小，尽可能多的课程入大根堆，
                //大根堆堆顶课程需要的上课时间peekTime出堆，time=time-peekTime+courses[i][0]，courses[i][0]入堆
                //注意：此时替换之后加上当前课程的上课时间的总时间time小于课程结束时间courses[i][1]，即替换之后当前课程可以添加，
                //因为peekTime>courses[i][0]，则替换之后time小于替换之前time，又因为课程是按照结束时间courses[i][1]由小到大排序，
                //替换之前课程结束时间小于替换之后课程结束时间，替换之前time原本就小于等于替换之前课程结束时间，则替换之后time肯定小于替换之后课程结束时间
                if (!priorityQueue.isEmpty() && priorityQueue.peek() > courses[i][0]) {
                    //大根堆堆顶课程需要的上课时间
                    int peekTime = priorityQueue.poll();
                    //当前课程需要的上课时间courses[i][0]替换大根堆堆顶课程需要的上课时间peekTime，使time变小，尽可能多的课程入大根堆
                    time = time - peekTime + courses[i][0];
                    //当前课程需要的上课时间courses[i][0]入堆，替换大根堆堆顶课程需要的上课时间
                    priorityQueue.offer(courses[i][0]);
                }
            }
        }

        //大根堆中存放课程需要的上课时间之间互不冲突，即为最多可以上的课程数量
        return priorityQueue.size();
    }

    /**
     * 按照课程结束时间courses[i][1]由小到大排序
     *
     * @param arr
     */
    private void heapSort(int[][] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int[] temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[][] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[index][1] < arr[leftIndex][1]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[index][1] < arr[rightIndex][1]) {
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
