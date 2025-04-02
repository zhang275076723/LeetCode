package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/10 08:03
 * @Author zsy
 * @Description 子数组和排序后的区间和 类比Problem373、Problem378、Problem644、Problem668、Problem719、Problem786、Problem1439、Problem2040、Problem2386 优先队列类比 二分查找类比 前缀和类比 子序列和子数组类比
 * 给你一个数组 nums ，它包含 n 个正整数。
 * 你需要计算所有非空连续子数组的和，并将它们按升序排序，得到一个新的包含 n * (n + 1) / 2 个数字的数组。
 * 请你返回在新数组中下标为 left 到 right （下标从 1 开始）的所有数字和（包括左右端点）。
 * 由于答案可能很大，请你将它对 10^9 + 7 取模后返回。
 * <p>
 * 输入：nums = [1,2,3,4], n = 4, left = 1, right = 5
 * 输出：13
 * 解释：所有的子数组和为 1, 3, 6, 10, 2, 5, 9, 3, 7, 4 。
 * 将它们升序排序后，我们得到新的数组 [1, 2, 3, 3, 4, 5, 6, 7, 9, 10] 。
 * 下标从 le = 1 到 ri = 5 的和为 1 + 2 + 3 + 3 + 4 = 13 。
 * <p>
 * 输入：nums = [1,2,3,4], n = 4, left = 3, right = 4
 * 输出：6
 * 解释：给定数组与示例 1 一样，所以新数组为 [1, 2, 3, 3, 4, 5, 6, 7, 9, 10] 。
 * 下标从 le = 3 到 ri = 4 的和为 3 + 3 = 6 。
 * <p>
 * 输入：nums = [1,2,3,4], n = 4, left = 1, right = 10
 * 输出：50
 * <p>
 * 1 <= nums.length <= 10^3
 * nums.length == n
 * 1 <= nums[i] <= 100
 * 1 <= left <= right <= n * (n + 1) / 2
 */
public class Problem1508 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem1508 problem1508 = new Problem1508();
        int[] nums = {1, 2, 3, 4};
        int n = 4;
        int left = 1;
        int right = 5;
        System.out.println(problem1508.rangeSum(nums, n, left, right));
        System.out.println(problem1508.rangeSum2(nums, n, left, right));
        System.out.println(problem1508.rangeSum3(nums, n, left, right));
    }

    /**
     * 暴力
     * 先获取nums中所有的n(n+1)/2个子数组区间和，再由小到大排序，求排序后第left小到第right小子数组区间和之和
     * 时间复杂度O(n^2*logn)，空间复杂度O(n^2)
     *
     * @param nums
     * @param n
     * @param left
     * @param right
     * @return
     */
    public int rangeSum(int[] nums, int n, int left, int right) {
        //存储n(n+1)/2个区间和
        int[] arr = new int[n * (n + 1) / 2];
        int index = 0;

        for (int i = 0; i < nums.length; i++) {
            //nums[i]-nums[j]子数组区间和
            int sum = 0;

            for (int j = i; j < nums.length; j++) {
                sum = sum + nums[j];
                arr[index] = sum;
                index++;
            }
        }

        //由小到大排序
        Arrays.sort(arr);

        int result = 0;

        for (int i = left - 1; i < right; i++) {
            result = (result + arr[i]) % MOD;
        }

        return result;
    }

    /**
     * 优先队列，小根堆，多路归并排序
     * 时间复杂度O(n^2*logn)，空间复杂度O(n)
     *
     * @param nums
     * @param n
     * @param left
     * @param right
     * @return
     */
    public int rangeSum2(int[] nums, int n, int left, int right) {
        //小根堆，arr[0]：当前子数组区间和，arr[1]：当前子数组的右边界下标索引
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return arr1[0] - arr2[0];
            }
        });

        //nums[i]作为初始子数组区间和，i作为初始子数组的右边界下标索引
        for (int i = 0; i < n; i++) {
            priorityQueue.offer(new int[]{nums[i], i});
        }

        int result = 0;

        for (int i = 0; i < right; i++) {
            int[] arr = priorityQueue.poll();

            //获取第left小到第right小子数组区间和之和
            if (i >= left - 1) {
                result = (result + arr[0]) % MOD;
            }

            if (arr[1] + 1 < n) {
                priorityQueue.offer(new int[]{arr[0] + nums[arr[1] + 1], arr[1] + 1});
            }
        }

        return result;
    }

    /**
     * 二分查找+双指针+前缀和
     * 前right小子数组区间和之和减去前left-1小子数组区间和之和，得到排序后第left小到第right小子数组区间和之和
     * 时间复杂度O(n+n*log(preSum[n]-preSum[0]-min(nums[i])))=O(n)，空间复杂度O(n)
     *
     * @param nums
     * @param n
     * @param left
     * @param right
     * @return
     */
    public int rangeSum3(int[] nums, int n, int left, int right) {
        //nums前缀和数组，用于求nums子数组区间和
        int[] preSum = new int[n + 1];
        //preSum前缀和数组，用于求preSum[i]-preSum[j]元素之和
        int[] prePreSum = new int[n + 2];

        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        for (int i = 2; i <= n + 1; i++) {
            prePreSum[i] = prePreSum[i - 1] + preSum[i - 1];
        }

        //f[k]：前k小nums子数组区间和之和
        return (f(preSum, prePreSum, n, right) - f(preSum, prePreSum, n, left - 1)) % MOD;
    }

    /**
     * 双指针获取前k小nums子数组区间和之和
     * 先通过二分查找获取第k小nums子数组区间和minK，再通过preSum和prePreSum，求当前列小于minK的nums子数组区间和之和，
     * 获取小于minK的nums子数组区间和之和sum和小于minK的nums子数组区间和的个数count，即f[k]=sum+(k-count)*minK
     * 时间复杂度O(n+n*log(preSum[n]-preSum[0]-min(nums[i])))=O(n)，空间复杂度O(1)
     *
     * @param preSum
     * @param prePreSum
     * @param n
     * @param k
     * @return
     */
    private int f(int[] preSum, int[] prePreSum, int n, int k) {
        //二分查找获取第k小nums子数组区间和
        int minK = getMinKSum(preSum, n, k);
        //小于minK的nums子数组区间和之和
        int sum = 0;
        //小于minK的nums子数组区间和的个数
        int count = 0;
        int i = 0;
        int j = 0;

        //从左上往右下遍历，只遍历右上三角(i<=j)
        while (i < n && j < n) {
            //注意：这里统计的是小于minK的nums子数组区间和之和，所以是大于等于，而不是大于
            while (i <= j && preSum[j + 1] - preSum[i] >= minK) {
                i++;
            }

            //nums[i]-nums[j]到nums[j]-nums[j]的子数组区间和之和为：
            //(preSum[j+1]-preSum[i])+(preSum[j+1]-preSum[i+1])+...+(preSum[j+1]-preSum[j-1])+(preSum[j+1]-preSum[j])
            //=preSum[j+1]*(j-i+1)-(preSum[i]+preSum[i+1]+...+preSum[j-1]+preSum[j])
            //=preSum[j+1]*(j-i+1)-(prePreSum[j+1]-prePreSum[i])
            sum = (sum + preSum[j + 1] * (j - i + 1) - (prePreSum[j + 1] - prePreSum[i])) % MOD;
            //nums[i]-nums[j]到nums[j]-nums[j]的子数组区间和都小于minK
            count = count + (j - i + 1);
            j++;
        }

        //小于minK的nums子数组区间和之和加上等于(k-count)个minK的nums子数组区间和之和，得到前k小nums子数组区间和之和
        return (sum + (k - count) * minK) % MOD;
    }

    /**
     * 二分查找获取第k小nums子数组区间和
     * 对[left,right]进行二分查找，left为0，right为nums子数组区间和的最大值，统计nums子数组区间和小于等于mid的个数count，
     * 如果count小于k，则第k小nums子数组区间和在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小nums子数组区间和在mid或mid左边，right=mid
     * 时间复杂度O(n*log(preSum[n]-preSum[0]-min(nums[i])))=O(n)，空间复杂度O(1)
     *
     * @param preSum
     * @param n
     * @param k
     * @return
     */
    private int getMinKSum(int[] preSum, int n, int k) {
        int left = 0;
        int right = preSum[n] - preSum[0];
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (getLessEqualThanNumCount(preSum, n, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * 双指针获取nums子数组区间和小于等于num的个数
     * 时间复杂度O(n)，空间复杂度O(1)
     * <p>
     * 例如：nums=[1,2,3,4]，preSum=[0,1,3,6,10]
     * <     0  1  2  3
     * < 0   1  3  6  10
     * < 1      2  5  9
     * < 2         3  7
     * < 3            4
     * < 其中(i,j)为nums[i]-nums[j]之和，即preSum[j+1]-preSum[i]，则通过双指针得到nums子数组区间和小于等于num的个数
     *
     * @param preSum
     * @param n
     * @param num
     * @return
     */
    private int getLessEqualThanNumCount(int[] preSum, int n, int num) {
        int count = 0;
        //nums[i]-nums[j]子数组区间和，即preSum[j+1]-preSum[i]
        int i = 0;
        int j = 0;

        //从左上往右下遍历，只遍历右上三角(i<=j)
        while (i < n && j < n) {
            while (i <= j && preSum[j + 1] - preSum[i] > num) {
                i++;
            }

            //nums[i]-nums[j]到nums[j]-nums[j]的子数组区间和都小于等于num
            count = count + (j - i + 1);
            j++;
        }

        return count;
    }
}
