package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2025/1/13 08:16
 * @Author zsy
 * @Description 使用服务器处理任务 类比Problem621、Problem1834、Problem2402 优先队列类比
 * 给你两个 下标从 0 开始 的整数数组 servers 和 tasks ，长度分别为 n 和 m 。
 * servers[i] 是第 i 台服务器的 权重 ，而 tasks[j] 是处理第 j 项任务 所需要的时间（单位：秒）。
 * 你正在运行一个仿真系统，在处理完所有任务后，该系统将会关闭。
 * 每台服务器只能同时处理一项任务。
 * 第 0 项任务在第 0 秒可以开始处理，相应地，第 j 项任务在第 j 秒可以开始处理。
 * 处理第 j 项任务时，你需要为它分配一台 权重最小 的空闲服务器。
 * 如果存在多台相同权重的空闲服务器，请选择 下标最小 的服务器。
 * 如果一台空闲服务器在第 t 秒分配到第 j 项任务，那么在 t + tasks[j] 时它将恢复空闲状态。
 * 如果没有空闲服务器，则必须等待，直到出现一台空闲服务器，并 尽可能早 地处理剩余任务。
 * 如果有多项任务等待分配，则按照 下标递增 的顺序完成分配。
 * 如果同一时刻存在多台空闲服务器，可以同时将多项任务分别分配给它们。
 * 构建长度为 m 的答案数组 ans ，其中 ans[j] 是第 j 项任务分配的服务器的下标。
 * 返回答案数组 ans 。
 * <p>
 * 输入：servers = [3,3,2], tasks = [1,2,3,2,1,2]
 * 输出：[2,2,0,2,1,2]
 * 解释：事件按时间顺序如下：
 * - 0 秒时，第 0 项任务加入到任务队列，使用第 2 台服务器处理到 1 秒。
 * - 1 秒时，第 2 台服务器空闲，第 1 项任务加入到任务队列，使用第 2 台服务器处理到 3 秒。
 * - 2 秒时，第 2 项任务加入到任务队列，使用第 0 台服务器处理到 5 秒。
 * - 3 秒时，第 2 台服务器空闲，第 3 项任务加入到任务队列，使用第 2 台服务器处理到 5 秒。
 * - 4 秒时，第 4 项任务加入到任务队列，使用第 1 台服务器处理到 5 秒。
 * - 5 秒时，所有服务器都空闲，第 5 项任务加入到任务队列，使用第 2 台服务器处理到 7 秒。
 * <p>
 * 输入：servers = [5,1,4,3,2], tasks = [2,1,2,4,5,2,1]
 * 输出：[1,4,1,4,1,3,2]
 * 解释：事件按时间顺序如下：
 * - 0 秒时，第 0 项任务加入到任务队列，使用第 1 台服务器处理到 2 秒。
 * - 1 秒时，第 1 项任务加入到任务队列，使用第 4 台服务器处理到 2 秒。
 * - 2 秒时，第 1 台和第 4 台服务器空闲，第 2 项任务加入到任务队列，使用第 1 台服务器处理到 4 秒。
 * - 3 秒时，第 3 项任务加入到任务队列，使用第 4 台服务器处理到 7 秒。
 * - 4 秒时，第 1 台服务器空闲，第 4 项任务加入到任务队列，使用第 1 台服务器处理到 9 秒。
 * - 5 秒时，第 5 项任务加入到任务队列，使用第 3 台服务器处理到 7 秒。
 * - 6 秒时，第 6 项任务加入到任务队列，使用第 2 台服务器处理到 7 秒。
 * <p>
 * servers.length == n
 * tasks.length == m
 * 1 <= n, m <= 2 * 10^5
 * 1 <= servers[i], tasks[j] <= 2 * 10^5
 */
public class Problem1882 {
    public static void main(String[] args) {
        Problem1882 problem1882 = new Problem1882();
        int[] servers = {3, 3, 2};
        int[] tasks = {1, 2, 3, 2, 1, 2};
        System.out.println(Arrays.toString(problem1882.assignTasks(servers, tasks)));
    }

    /**
     * 双优先队列，双小根堆
     * 小根堆idlePriorityQueue存放空闲服务器，小根堆busyPriorityQueue存放运行中服务器
     * 当不存在空闲服务器运行当前任务时，运行中服务器的小根堆出堆堆顶服务器运行当前任务
     * 时间复杂度O(mlogm+nlogm)，空间复杂度O(m) (m=servers.length，n=tasks.length)
     *
     * @param servers
     * @param tasks
     * @return
     */
    public int[] assignTasks(int[] servers, int[] tasks) {
        //小根堆，存放空闲服务器
        //arr[0]：服务器的权重，arr[1]：服务器在servers中的下标索引
        //使用long，避免int溢出
        PriorityQueue<long[]> idlePriorityQueue = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] arr1, long[] arr2) {
                //按照权重由小到大排序，再按照服务器下标索引由小到大排序
                if (arr1[0] != arr2[0]) {
                    //不能写成return (int) (arr1[0] - arr2[0]);，因为long相减再转为int有可能在int范围溢出
                    return Long.compare(arr1[0], arr2[0]);
                } else {
                    return Long.compare(arr1[1], arr2[1]);
                }
            }
        });

        //小根堆，存放运行中服务器
        //arr[0]：服务器的处理任务的结束时间，arr[1]：服务器的权重，arr[2]：服务器在servers中的下标索引
        //注意：运行中服务器的小根堆必须存放服务器的权重，因为当存在多个结束时间相等的服务器时，优先出堆权重小的服务器运行当前任务
        //使用long，避免int溢出
        PriorityQueue<long[]> busyPriorityQueue = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] arr1, long[] arr2) {
                //按照结束时间由小到大排序，再按照服务器的权重由小到大排序，最后按照服务器下标索引由小到大排序
                if (arr1[0] != arr2[0]) {
                    //不能写成return (int) (arr1[0] - arr2[0]);，因为long相减再转为int有可能在int范围溢出
                    return Long.compare(arr1[0], arr2[0]);
                } else if (arr1[1] != arr2[1]) {
                    return Long.compare(arr1[1], arr2[1]);
                } else {
                    return Long.compare(arr1[2], arr2[2]);
                }
            }
        });

        //idlePriorityQueue初始化，所有服务器都是空间的，都加入到小根堆idlePriorityQueue中
        for (int i = 0; i < servers.length; i++) {
            idlePriorityQueue.offer(new long[]{servers[i], i});
        }

        int[] ans = new int[tasks.length];

        for (int i = 0; i < tasks.length; i++) {
            //运行中服务器的小根堆堆顶结束时间小于等于当前时间i，则当前服务器空闲，加入空闲服务器小根堆中
            while (!busyPriorityQueue.isEmpty() && busyPriorityQueue.peek()[0] <= i) {
                long[] arr = busyPriorityQueue.poll();
                idlePriorityQueue.offer(new long[]{arr[1], arr[2]});
            }

            //当前时间i时，空闲服务器小根堆为空，则运行中服务器的小根堆出堆堆顶服务器运行当前任务tasks[i]，
            //当前服务器再重新入堆运行中服务器的小根堆
            if (idlePriorityQueue.isEmpty()) {
                long[] arr = busyPriorityQueue.poll();
                ans[i] = (int) arr[2];
                busyPriorityQueue.offer(new long[]{arr[0] + tasks[i], arr[1], arr[2]});
            } else {
                //当前时间i时，空闲服务器小根堆不为空，则空闲服务器的小根堆出堆堆顶服务器运行当前任务tasks[i]，
                //当前服务器入堆运行中服务器的小根堆
                long[] arr = idlePriorityQueue.poll();
                ans[i] = (int) arr[1];
                busyPriorityQueue.offer(new long[]{i + tasks[i], arr[0], arr[1]});
            }
        }

        return ans;
    }
}
