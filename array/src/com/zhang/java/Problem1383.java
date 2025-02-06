package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2025/3/26 08:41
 * @Author zsy
 * @Description 最大的团队表现值 类比Problem857、Problem2542 优先队列类比
 * 给定两个整数 n 和 k，以及两个长度为 n 的整数数组 speed 和 efficiency。
 * 现有 n 名工程师，编号从 1 到 n。
 * 其中 speed[i] 和 efficiency[i] 分别代表第 i 位工程师的速度和效率。
 * 从这 n 名工程师中最多选择 k 名不同的工程师，使其组成的团队具有最大的团队表现值。
 * 团队表现值 的定义为：一个团队中「所有工程师速度的和」乘以他们「效率值中的最小值」。
 * 请你返回该团队的最大团队表现值，由于答案可能很大，请你返回结果对 10^9 + 7 取余后的结果。
 * <p>
 * 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
 * 输出：60
 * 解释：
 * 我们选择工程师 2（speed=10 且 efficiency=4）和工程师 5（speed=5 且 efficiency=7）。
 * 他们的团队表现值为 performance = (10 + 5) * min(4, 7) = 60 。
 * <p>
 * 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
 * 输出：68
 * 解释：
 * 此示例与第一个示例相同，除了 k = 3 。我们可以选择工程师 1 ，工程师 2 和工程师 5 得到最大的团队表现值。
 * 表现值为 performance = (2 + 10 + 5) * min(5, 4, 7) = 68 。
 * <p>
 * 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 4
 * 输出：72
 * <p>
 * 1 <= k <= n <= 10^5
 * speed.length == n
 * efficiency.length == n
 * 1 <= speed[i] <= 10^5
 * 1 <= efficiency[i] <= 10^8
 */
public class Problem1383 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1383 problem1383 = new Problem1383();
        int n = 6;
        int[] speed = {2, 10, 3, 1, 5, 8};
        int[] efficiency = {5, 4, 3, 9, 7, 2};
        int k = 2;
        System.out.println(problem1383.maxPerformance(n, speed, efficiency, k));
    }

    /**
     * 排序+优先队列，小根堆
     * speed[i]和efficiency[i]构成二维数组，按照efficiency[i]由大到小排序，小根堆中保存arr排序后最多k名工程师的速度speed[i]，
     * 当前工程师i作为所选工程师效率的最小值minEfficiency，则通过小根堆得到所选工程师的最大速度之和sumSpeed，得到最多选择k名工程师的最大团队表现值
     * 时间复杂度O(nlogn+nlogk)，空间复杂度O(n+k)
     *
     * @param n
     * @param speed
     * @param efficiency
     * @param k
     * @return
     */
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        //arr[i][0]：speed[i]，arr[i][1]：efficiency[i]
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = speed[i];
            arr[i][1] = efficiency[i];
        }

        //按照efficiency[i]由大到小排序
        //当前工程师i作为所选工程师效率的最小值minEfficiency，则通过小根堆得到所选工程师的最大速度之和sumSpeed，得到最多选择k名工程师的最大团队表现值
        heapSort(arr);

        //优先队列，小根堆，保存arr排序后最多k名工程师的速度speed[i]
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return a - b;
            }
        });

        //最多选择k名工程师的最大团队表现值，即所选工程师速度之和sum(speed[i])和所选工程师效率的最小值min(efficiency[i])的乘积的最大值
        //使用long，避免int相乘溢出
        long result = 0;
        //所选工程师速度之和
        //使用long，避免int相加溢出
        long sumSpeed = 0;
        //所选工程师效率的最小值
        int minEfficiency;

        for (int i = 0; i < n; i++) {
            priorityQueue.offer(arr[i][0]);
            sumSpeed = sumSpeed + arr[i][0];
            //当前工程师i作为所选工程师效率的最小值minEfficiency
            minEfficiency = arr[i][1];

            result = Math.max(result, sumSpeed * minEfficiency);

            if (priorityQueue.size() == k) {
                //小根堆堆顶元素出堆，保证下次得到k名工程师速度之和最大
                int minSpeed = priorityQueue.poll();
                sumSpeed = sumSpeed - minSpeed;
            }
        }

        return (int) (result % MOD);
    }

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

        if (leftIndex < heapSize && arr[leftIndex][1] < arr[index][1]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex][1] < arr[index][1]) {
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
