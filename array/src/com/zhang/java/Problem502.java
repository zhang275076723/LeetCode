package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/2/7 08:14
 * @Author zsy
 * @Description IPO 优先队列类比
 * 假设 力扣（LeetCode）即将开始 IPO 。
 * 为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。
 * 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。
 * 帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。
 * 给你 n 个项目。
 * 对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
 * 最初，你的资本为 w 。
 * 当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
 * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
 * 答案保证在 32 位有符号整数范围内。
 * <p>
 * 输入：k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
 * 输出：4
 * 解释：
 * 由于你的初始资本为 0，你仅可以从 0 号项目开始。
 * 在完成后，你将获得 1 的利润，你的总资本将变为 1。
 * 此时你可以选择开始 1 号或 2 号项目。
 * 由于你最多可以选择两个项目，所以你需要完成 2 号项目以获得最大的资本。
 * 因此，输出最后最大化的资本，为 0 + 1 + 3 = 4。
 * <p>
 * 输入：k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
 * 输出：6
 * <p>
 * 1 <= k <= 10^5
 * 0 <= w <= 10^9
 * n == profits.length
 * n == capital.length
 * 1 <= n <= 10^5
 * 0 <= profits[i] <= 10^4
 * 0 <= capital[i] <= 10^9
 */
public class Problem502 {
    public static void main(String[] args) {
        Problem502 problem502 = new Problem502();
        int k = 2;
        int w = 0;
        int[] profits = {1, 2, 3};
        int[] capital = {0, 1, 1};
        System.out.println(problem502.findMaximizedCapital(k, w, profits, capital));
    }

    /**
     * 优先队列，大根堆
     * profits和capital组成二维数组，按照capital由小到大排序，将小于等于当前资本w的项目利润profits加入大根堆，
     * 每次大根堆堆顶元素出堆，即每次选择利润最大的项目，更新w
     * 时间复杂度O(nlogn+klogn)，空间复杂度O(n) (n=profits.length=capital.length)
     *
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int[][] arr = new int[profits.length][2];

        for (int i = 0; i < profits.length; i++) {
            arr[i] = new int[]{profits[i], capital[i]};
        }

        //按照capital由小到大排序
        mergeSort(arr, 0, profits.length - 1, new int[profits.length][2]);

        //优先队列，大根堆，存放小于等于当前资本w的项目利润profits
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        //arr下标索引
        int index = 0;

        //只能选择k个项目
        for (int i = 0; i < k; i++) {
            //将小于等于当前资本w的项目利润profits加入大根堆
            while (index < profits.length && arr[index][1] <= w) {
                priorityQueue.offer(arr[index][0]);
                index++;
            }

            //大根堆不为空才能选择项目
            if (!priorityQueue.isEmpty()) {
                //大根堆堆顶元素即为能选择的最大利润
                int maxProfit = priorityQueue.poll();
                w = w + maxProfit;
            } else {
                //大根堆为空，即不存在小于等于当前资本w的项目，直接跳出循环
                break;
            }
        }

        return w;
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
            if (arr[i][1] < arr[j][1]) {
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
