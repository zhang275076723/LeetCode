package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/9 08:49
 * @Author zsy
 * @Description 第 K 个最小的质数分数 类比Problem644 类比Problem373、Problem378、Problem644、Problem668、Problem719、Problem1439、Problem1508、Problem2040、Problem2386 优先队列类比 二分查找类比
 * 给你一个按递增顺序排序的数组 arr 和一个整数 k 。
 * 数组 arr 由 1 和若干 质数 组成，且其中所有整数互不相同。
 * 对于每对满足 0 <= i < j < arr.length 的 i 和 j ，可以得到分数 arr[i] / arr[j] 。
 * 那么第 k 个最小的分数是多少呢?
 * 以长度为 2 的整数数组返回你的答案, 这里 answer[0] == arr[i] 且 answer[1] == arr[j] 。
 * <p>
 * 输入：arr = [1,2,3,5], k = 3
 * 输出：[2,5]
 * 解释：已构造好的分数,排序后如下所示:
 * 1/5, 1/3, 2/5, 1/2, 3/5, 2/3
 * 很明显第三个最小的分数是 2/5
 * <p>
 * 输入：arr = [1,7], k = 1
 * 输出：[1,7]
 * <p>
 * 2 <= arr.length <= 1000
 * 1 <= arr[i] <= 3 * 10^4
 * arr[0] == 1
 * arr[i] 是一个 质数 ，i > 0
 * arr 中的所有数字 互不相同 ，且按 严格递增 排序
 * 1 <= k <= arr.length * (arr.length - 1) / 2
 */
public class Problem786 {
    public static void main(String[] args) {
        Problem786 problem786 = new Problem786();
        int[] arr = {1, 2, 3, 5};
        int k = 3;
        System.out.println(Arrays.toString(problem786.kthSmallestPrimeFraction(arr, k)));
        System.out.println(Arrays.toString(problem786.kthSmallestPrimeFraction2(arr, k)));
    }

    /**
     * 优先队列，小根堆，多路归并排序
     * 时间复杂度O(klogn)，空间复杂度O(n)
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        //小根堆，arr[0]：分数arr[i]/arr[j]中分子在arr中的下标索引i，arr[1]：分数arr[i]/arr[j]中分母在arr中的下标索引j
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //分数不好比较大小，则通分之后比较分子的大小
                //a/b和c/d比较大小，转换为(ad-bc)/bd，即比较分子ad-bc的大小
                return arr[arr1[0]] * arr[arr2[1]] - arr[arr1[1]] * arr[arr2[0]];
            }
        });

        //因为每列第一个分数是当前列最小的分数，所以每列的第一个分数入小根堆
        for (int i = 1; i < arr.length; i++) {
            priorityQueue.offer(new int[]{0, i});
        }

        //小根堆移除k-1个元素，堆顶元素即为第k小的分数
        for (int i = 0; i < k - 1; i++) {
            int[] tempArr = priorityQueue.poll();

            //将当前列的下一个分数入小根堆
            //tempArr[0]+1<tempArr[1]，保证分数arr[i]/arr[j]中i<j，是有效分数
            if (tempArr[0] + 1 < tempArr[1]) {
                priorityQueue.offer(new int[]{tempArr[0] + 1, tempArr[1]});
            }
        }

        int[] tempArr = priorityQueue.poll();

        return new int[]{arr[tempArr[0]], arr[tempArr[1]]};
    }

    /**
     * 二分查找+双指针
     * 对[left,right]进行二分查找，left为arr中构成的最小分数0，right为arr中构成的最大分数1，统计arr中构成的分数小于等于mid的个数count，
     * 如果count小于k，则第k小分数在mid右边，left=mid；
     * 如果count大于k，则第k小分数在mid左边，right=mid；
     * 如果count等于k，则第k小分数在统计数组中构成分数小于等于mid的个数count的同时，记录下了数组中小于等于mid的最大分数a/b，直接返回a和b
     * 其中通过双指针，对每一个arr[j]统计构成分数arr[i]/arr[j]小于等于mid的个数
     * 时间复杂度O(n*logC)=O(n)，空间复杂度O(1)
     * (arr中的最大值为3*10^4，则任意两个分数差的最小值大于1/(3*10^4)^2，每次二分区间长度减半，
     * 即需要log((3*10^4)^2)=30次二分，logC=30，区间长度小于1/(3*10^4)^2，即得到了第k小分数left)
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] kthSmallestPrimeFraction2(int[] arr, int k) {
        double left = 0;
        double right = 1;
        double mid;

        //数组中的最大值为3*10^4，则任意两个分数差的最小值大于1/(3*10^4)^2，每次二分区间长度减半，即需要log((3*10^4)^2)=30次二分
        while (true) {
            //注意：double类型不能使用>>来除以2
            mid = left + ((right - left) / 2);

            int[] tempArr = getLessEqualThanNumCount(arr, mid);
            //小于等于mid的个数
            int count = tempArr[0];
            //小于等于mid的最大分数a/b
            int a = tempArr[1];
            int b = tempArr[2];

            if (count < k) {
                left = mid;
            } else if (count > k) {
                right = mid;
            } else {
                //找到第k小分数a/b，直接返回
                return new int[]{a, b};
            }
        }
    }

    /**
     * 双指针获取arr中小于等于num的分数个数，并且记录小于等于num的最大分数a/b
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @param num
     * @return
     */
    private int[] getLessEqualThanNumCount(int[] arr, double num) {
        int count = 0;
        //分数arr[i]/arr[j]的下标索引，始终保持i<j
        int i = 0;
        int j = 1;
        //小于等于num的最大分数，用于求小于等于num的最大分数a/b
        double curMax = 0;
        //小于等于num的最大分数a/b
        int a = 0;
        int b = 0;

        //从左上往右下遍历，只遍历右上三角(i<j)
        while (i < arr.length - 1 && j < arr.length) {
            //始终保持i<j
            if (i >= j) {
                j = i + 1;
            }

            while (j < arr.length && (double) arr[i] / arr[j] > num) {
                j++;
            }

            //更新小于等于num的最大分数a/b
            if (j < arr.length && (double) arr[i] / arr[j] > curMax) {
                curMax = (double) arr[i] / arr[j];
                a = arr[i];
                b = arr[j];
            }

            //arr[i]/arr[j]到arr[i]/arr[arr.length-1]都小于等于num
            count = count + (arr.length - j);
            i++;
        }

        return new int[]{count, a, b};
    }
}
