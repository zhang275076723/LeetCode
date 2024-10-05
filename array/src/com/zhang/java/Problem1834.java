package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2025/1/9 08:49
 * @Author zsy
 * @Description 单线程 CPU 类比Problem621、Problem1882、Problem2402 优先队列类比
 * 给你一个二维数组 tasks ，用于表示 n 项从 0 到 n - 1 编号的任务。
 * 其中 tasks[i] = [enqueueTimei, processingTimei] 意味着第 i 项任务将会于 enqueueTimei 时进入任务队列，需要 processingTimei 的时长完成执行。
 * 现有一个单线程 CPU ，同一时间只能执行 最多一项 任务，该 CPU 将会按照下述方式运行：
 * 如果 CPU 空闲，且任务队列中没有需要执行的任务，则 CPU 保持空闲状态。
 * 如果 CPU 空闲，但任务队列中有需要执行的任务，则 CPU 将会选择 执行时间最短 的任务开始执行。
 * 如果多个任务具有同样的最短执行时间，则选择下标最小的任务开始执行。
 * 一旦某项任务开始执行，CPU 在 执行完整个任务 前都不会停止。
 * CPU 可以在完成一项任务后，立即开始执行一项新任务。
 * 返回 CPU 处理任务的顺序。
 * <p>
 * 输入：tasks = [[1,2],[2,4],[3,2],[4,1]]
 * 输出：[0,2,3,1]
 * 解释：事件按下述流程运行：
 * - time = 1 ，任务 0 进入任务队列，可执行任务项 = {0}
 * - 同样在 time = 1 ，空闲状态的 CPU 开始执行任务 0 ，可执行任务项 = {}
 * - time = 2 ，任务 1 进入任务队列，可执行任务项 = {1}
 * - time = 3 ，任务 2 进入任务队列，可执行任务项 = {1, 2}
 * - 同样在 time = 3 ，CPU 完成任务 0 并开始执行队列中用时最短的任务 2 ，可执行任务项 = {1}
 * - time = 4 ，任务 3 进入任务队列，可执行任务项 = {1, 3}
 * - time = 5 ，CPU 完成任务 2 并开始执行队列中用时最短的任务 3 ，可执行任务项 = {1}
 * - time = 6 ，CPU 完成任务 3 并开始执行任务 1 ，可执行任务项 = {}
 * - time = 10 ，CPU 完成任务 1 并进入空闲状态
 * <p>
 * 输入：tasks = [[7,10],[7,12],[7,5],[7,4],[7,2]]
 * 输出：[4,3,2,0,1]
 * 解释：事件按下述流程运行：
 * - time = 7 ，所有任务同时进入任务队列，可执行任务项  = {0,1,2,3,4}
 * - 同样在 time = 7 ，空闲状态的 CPU 开始执行任务 4 ，可执行任务项 = {0,1,2,3}
 * - time = 9 ，CPU 完成任务 4 并开始执行任务 3 ，可执行任务项 = {0,1,2}
 * - time = 13 ，CPU 完成任务 3 并开始执行任务 2 ，可执行任务项 = {0,1}
 * - time = 18 ，CPU 完成任务 2 并开始执行任务 0 ，可执行任务项 = {1}
 * - time = 28 ，CPU 完成任务 0 并开始执行任务 1 ，可执行任务项 = {}
 * - time = 40 ，CPU 完成任务 1 并进入空闲状态
 * <p>
 * tasks.length == n
 * 1 <= n <= 10^5
 * 1 <= enqueueTimei, processingTimei <= 10^9
 */
public class Problem1834 {
    public static void main(String[] args) {
        Problem1834 problem1834 = new Problem1834();
        int[][] tasks = {{1, 2}, {2, 4}, {3, 2}, {4, 1}};
        System.out.println(Arrays.toString(problem1834.getOrder(tasks)));
    }

    /**
     * 排序+优先队列，小根堆
     * 操作系统调度算法：短作业优先
     * tasks中任务按照任务进入任务队列的时间由小到大排序，当前时间可以执行的任务加入小根堆，
     * 如果小根堆为空，则不存在可以执行的任务，当前时间直接修改为arr中最小的任务进入任务队列的时间；
     * 如果小根堆不为空，则小根堆每次出堆执行时间最短的任务，如果执行时间相同，则出堆最小编号的任务
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param tasks
     * @return
     */
    public int[] getOrder(int[][] tasks) {
        //arr[0]：任务进入任务队列的时间，arr[1]：任务的执行时间，arr[2]：任务的编号
        int[][] arr = new int[tasks.length][3];

        for (int i = 0; i < tasks.length; i++) {
            arr[i] = new int[]{tasks[i][0], tasks[i][1], i};
        }

        //按照arr[0]由小到大排序
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[0] - arr2[0];
            }
        });

        //arr[0]：任务进入任务队列的时间，arr[1]：任务的执行时间，arr[2]：任务的编号
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //按照任务的执行时间由小到大排序，再按照任务的编号由小到大排序
                if (arr1[1] != arr2[1]) {
                    return arr1[1] - arr2[1];
                } else {
                    return arr1[2] - arr2[2];
                }
            }
        });

        int[] result = new int[tasks.length];
        //cpu当前的执行时间
        int time = 0;
        //result下标索引
        int index1 = 0;
        //arr下标索引
        int index2 = 0;

        while (index1 < result.length) {
            //当前时间可以执行的任务加入小根堆
            while (index2 < arr.length && arr[index2][0] <= time) {
                priorityQueue.offer(arr[index2]);
                index2++;
            }

            //小根堆为空，则不存在可以执行的任务，当前时间直接修改为arr中最小的任务进入任务队列的时间
            if (priorityQueue.isEmpty()) {
                time = arr[index2][0];
            } else {
                //小根堆不为空，则小根堆每次出堆执行时间最短的任务，如果执行时间相同，则出堆最小编号的任务
                int[] curTask = priorityQueue.poll();
                time = time + curTask[1];
                result[index1] = curTask[2];
                index1++;
            }
        }

        return result;
    }
}
