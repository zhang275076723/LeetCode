package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @Date 2025/3/24 08:14
 * @Author zsy
 * @Description 雇佣 K 名工人的最低成本 类比Problem857、Problem1383 优先队列类比
 * 有 n 名工人。
 * 给定两个数组 quality 和 wage ，其中，quality[i] 表示第 i 名工人的工作质量，其最低期望工资为 wage[i] 。
 * 现在我们想雇佣 k 名工人组成一个 工资组。
 * 在雇佣 一组 k 名工人时，我们必须按照下述规则向他们支付工资：
 * 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
 * 工资组中的每名工人至少应当得到他们的最低期望工资。
 * 给定整数 k ，返回 组成满足上述条件的付费群体所需的最小金额 。
 * 与实际答案误差相差在 10^-5 以内的答案将被接受。
 * <p>
 * 输入： quality = [10,20,5], wage = [70,50,30], k = 2
 * 输出： 105.00000
 * 解释： 我们向 0 号工人支付 70，向 2 号工人支付 35。
 * <p>
 * 输入： quality = [3,1,10,10,1], wage = [4,8,2,2,7], k = 3
 * 输出： 30.66667
 * 解释： 我们向 0 号工人支付 4，向 2 号和 3 号分别支付 13.33333。
 * <p>
 * n == quality.length == wage.length
 * 1 <= k <= n <= 10^4
 * 1 <= quality[i], wage[i] <= 10^4
 */
public class Problem857 {
    public static void main(String[] args) {
        Problem857 problem857 = new Problem857();
        int[] quality = {3, 1, 10, 10, 1};
        int[] wage = {4, 8, 2, 2, 7};
        int k = 3;
        System.out.println(problem857.mincostToHireWorkers(quality, wage, k));
    }

    /**
     * 排序+优先队列，大根堆
     * 假设选择的k个工人的总质量为sumQuality，支付的总金额为sumCost，要满足题目中的2个条件，
     * 则选择的工人i必须满足sumCost*(quality[i]/sumQuality)>=wage[i]，即sumCost>=sumQuality*(wage[i]/quality[i])，
     * 所以在sumQuality固定的条件下，只需要考虑max(wage[i]/quality[i])，就能得到最小sumCost
     * wage[i]和quality[i]构成二维数组，按照wage[i]/quality[i]由小到大排序，大根堆中保存arr排序后k个工人的质量quality[i]，
     * 当前工人i作为k个工人中最大权重wage[i]/quality[i]，则通过大根堆得到k个工人的最小总质量sumQuality，得到支付的最小金额
     * 时间复杂度O(nlogn+nlogk)，空间复杂度O(n+k) (n=quality.length=wage.length)
     *
     * @param quality
     * @param wage
     * @param k
     * @return
     */
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        //arr[i][0]：wage[i]，arr[i][1]：quality[i]
        int[][] arr = new int[quality.length][2];

        for (int i = 0; i < quality.length; i++) {
            arr[i][0] = wage[i];
            arr[i][1] = quality[i];
        }

        //按照wage[i]/quality[i]由小到大排序
        //当前工人i作为k个工人中最大权重wage[i]/quality[i]，则通过大根堆得到k个工人的最小总质量sumQuality，得到支付的最小金额
        quickSort(arr, 0, arr.length - 1);

        //优先队列，大根堆，保存arr排序后k个工人的质量quality[i]
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        //支付的最小金额，即k个工人的总质量sumQuality和k个工人中最大权重max(wage[i]/quality[i])乘积的最小值
        double result = Double.MAX_VALUE;
        //k个工人的总质量，即小根堆中元素之和
        int sumQuality = 0;

        for (int i = 0; i < k - 1; i++) {
            priorityQueue.offer(arr[i][1]);
            sumQuality = sumQuality + arr[i][1];
        }

        for (int i = k - 1; i < quality.length; i++) {
            priorityQueue.offer(arr[i][1]);
            sumQuality = sumQuality + arr[i][1];

            //当前工人i作为k个工人中最大权重wage[i]/quality[i]
            result = Math.min(result, (double) arr[i][0] / arr[i][1] * sumQuality);

            //大根堆堆顶元素出堆，保证下次得到的k个工人的总质量最小
            int minQuality = priorityQueue.poll();
            sumQuality = sumQuality - minQuality;
        }

        return result;
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
            while (left < right && arr[right][0] * temp[1] - arr[right][1] * temp[0] >= 0) {
                right--;
            }

            arr[left] = arr[right];

            while (left < right && arr[left][0] * temp[1] - arr[left][1] * temp[0] <= 0) {
                left++;
            }

            arr[right] = arr[left];
        }

        arr[left] = temp;

        return left;
    }
}
