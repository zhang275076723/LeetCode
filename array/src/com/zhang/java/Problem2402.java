package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @Date 2024/1/13 08:11
 * @Author zsy
 * @Description 会议室 III
 * 给你一个整数 n ，共有编号从 0 到 n - 1 的 n 个会议室。
 * 给你一个二维整数数组 meetings ，其中 meetings[i] = [starti, endi] 表示一场会议将会在 半闭 时间区间 [starti, endi) 举办。
 * 所有 starti 的值 互不相同 。
 * 会议将会按以下方式分配给会议室：
 * 每场会议都会在未占用且编号 最小 的会议室举办。
 * 如果没有可用的会议室，会议将会延期，直到存在空闲的会议室。延期会议的持续时间和原会议持续时间 相同 。
 * 当会议室处于未占用状态时，将会优先提供给原 开始 时间更早的会议。
 * 返回举办最多次会议的房间 编号 。如果存在多个房间满足此条件，则返回编号 最小 的房间。
 * 半闭区间 [a, b) 是 a 和 b 之间的区间，包括 a 但 不包括 b 。
 * <p>
 * 输入：n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]]
 * 输出：0
 * 解释：
 * - 在时间 0 ，两个会议室都未占用，第一场会议在会议室 0 举办。
 * - 在时间 1 ，只有会议室 1 未占用，第二场会议在会议室 1 举办。
 * - 在时间 2 ，两个会议室都被占用，第三场会议延期举办。
 * - 在时间 3 ，两个会议室都被占用，第四场会议延期举办。
 * - 在时间 5 ，会议室 1 的会议结束。第三场会议在会议室 1 举办，时间周期为 [5,10) 。
 * - 在时间 10 ，两个会议室的会议都结束。第四场会议在会议室 0 举办，时间周期为 [10,11) 。
 * 会议室 0 和会议室 1 都举办了 2 场会议，所以返回 0 。
 * <p>
 * 输入：n = 3, meetings = [[1,20],[2,10],[3,5],[4,9],[6,8]]
 * 输出：1
 * 解释：
 * - 在时间 1 ，所有三个会议室都未占用，第一场会议在会议室 0 举办。
 * - 在时间 2 ，会议室 1 和 2 未占用，第二场会议在会议室 1 举办。
 * - 在时间 3 ，只有会议室 2 未占用，第三场会议在会议室 2 举办。
 * - 在时间 4 ，所有三个会议室都被占用，第四场会议延期举办。
 * - 在时间 5 ，会议室 2 的会议结束。第四场会议在会议室 2 举办，时间周期为 [5,10) 。
 * - 在时间 6 ，所有三个会议室都被占用，第五场会议延期举办。
 * - 在时间 10 ，会议室 1 和 2 的会议结束。第五场会议在会议室 1 举办，时间周期为 [10,12) 。
 * 会议室 1 和会议室 2 都举办了 2 场会议，所以返回 1 。
 * <p>
 * 1 <= n <= 100
 * 1 <= meetings.length <= 10^5
 * meetings[i].length == 2
 * 0 <= starti < endi <= 5 * 10^5
 * starti 的所有值 互不相同
 */
public class Problem2402 {
    public static void main(String[] args) {
        Problem2402 problem2402 = new Problem2402();
//        int n = 2;
//        int[][] meetings = {{0, 10}, {1, 5}, {2, 7}, {3, 4}};
        int n = 3;
        int[][] meetings = {{1, 20}, {2, 10}, {3, 5}, {4, 9}, {6, 8}};
        System.out.println(problem2402.mostBooked(n, meetings));
    }

    /**
     * 排序+双优先队列(双小根堆)
     * 按照会议开始时间meetings[i][0]由小到大排序，
     * 小根堆priorityQueue1存放空闲会议室编号，小根堆priorityQueue2存放正在使用会议室的会议结束时间和会议室编号
     * 时间复杂度O(mlogm+nlogn+mlogn+n)，空间复杂度O(n) (m=meetings.length，即会议的个数，n为会议室的个数)
     * (快排时间复杂度O(mlogm)，初始化priorityQueue1的时间复杂度O(nlogn)，m个会议入堆出堆的时间复杂度O(mlogn)，
     * 统计使用次数最多且编号最小的会议室的时间复杂度O(n))
     *
     * @param n
     * @param meetings
     * @return
     */
    public int mostBooked(int n, int[][] meetings) {
        //按照会议开始时间meetings[i][0]由小到大排序
        quickSort(meetings, 0, meetings.length - 1);

        //优先队列，小根堆，存放空闲会议室编号
        PriorityQueue<Integer> priorityQueue1 = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });
        //优先队列，小根堆，存放正在使用会议室的会议结束时间和会议室编号
        //arr[0]：正在使用会议室的会议结束时间，arr[1]：会议室编号
        PriorityQueue<int[]> priorityQueue2 = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //会议结束时间不相等时，按照会议结束时间由小到大排序
                if (arr1[0] != arr2[0]) {
                    return arr1[0] - arr2[0];
                } else {
                    //会议结束时间相等时，按照会议室编号由小到大排序
                    return arr1[1] - arr2[1];
                }
            }
        });

        //每个会议室使用的次数数组
        int[] count = new int[n];

        //priorityQueue1初始化，所有会议室都是空闲的，都加入到小根堆priorityQueue1中
        for (int i = 0; i < n; i++) {
            priorityQueue1.offer(i);
        }

        for (int i = 0; i < meetings.length; i++) {
            //priorityQueue2中正在使用会议室的会议结束时间小于当前会议开始时间meeting[i][0]，
            //则priorityQueue2堆顶会议室出堆，入priorityQueue1中，作为空闲会议室
            while (!priorityQueue2.isEmpty() && priorityQueue2.peek()[0] <= meetings[i][0]) {
                priorityQueue1.offer(priorityQueue2.poll()[1]);
            }

            //当前会议开始时间meeting[i][0]时，priorityQueue1中没有空闲会议室，
            //则priorityQueue2堆顶会议室出堆，即结束时间最早的会议室作为当前会议的会议室，
            //更新当前会议室的会议结束时间，再重新入priorityQueue2
            if (priorityQueue1.isEmpty()) {
                int[] arr = priorityQueue2.poll();
                count[arr[1]]++;
                arr[0] = arr[0] + (meetings[i][1] - meetings[i][0]);
                priorityQueue2.offer(arr);
            } else {
                //当前会议开始时间meeting[i][0]时，priorityQueue1中有空闲会议室，
                //则priorityQueue1堆顶会议室出堆，即编号最小的空闲会议室作为当前会议的会议室，入priorityQueue2
                int index = priorityQueue1.poll();
                count[index]++;
                int[] arr = {meetings[i][1], index};
                priorityQueue2.offer(arr);
            }
        }

        //使用次数最多且编号最小的会议室
        int index = 0;

        for (int i = 0; i < n; i++) {
            if (count[i] > count[index]) {
                index = i;
            }
        }

        return index;
    }

    private void quickSort(int[][] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
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
}
