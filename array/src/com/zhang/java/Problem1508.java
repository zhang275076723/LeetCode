package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/10 08:03
 * @Author zsy
 * @Description 子数组和排序后的区间和 优先队列类比 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem441、Problem644、Problem658、Problem668、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus 前缀和类比 子序列和子数组类比
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
     * 先得到n(n+1)/2个区间和，再进行排序，取[left,right]的元素之和
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
     * 小根堆，优先队列，多路归并排序
     * 每个nums[i]和下标索引i作为初始子数组的区间和和右边界加入小根堆，小根堆堆顶元素出堆，
     * 如果i后面还有元素，则累加当前子数组区间和作为下一个元素加入小根堆
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

        //求排序后[left-1,right-1]的子数组区间和
        for (int i = 0; i < right; i++) {
            int[] arr = priorityQueue.poll();

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
     * 对[left,right]进行二分查找，left为nums子数组区间和的最小值，right为nums子数组区间和的最大值，统计nums子数组区间和小于等于mid的个数count，
     * 如果count小于k，则第k小nums子数组区间和在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小nums子数组区间和在mid或mid左边，right=mid
     * preSum[i]：nums[0]-nums[i-1]元素之和
     * prePreSum[i]：preSum[0]-preSum[i]元素之和
     * 注意：prePreSum[i]是到preSum[0]-preSum[i]而不是preSum[0]-preSum[i-1]元素之和
     * f[i]：nums子数组前i小区间和之和
     * 在求f[k]时，二分查找获取第k小nums子数组区间和为x，则类比getLessEqualThanNumCount()，
     * 通过prePreSum和preSum求小于x的子数组区间和的个数count和小于x的子数组区间和之和sum，
     * 则还需要加上等于x的子数组区间和的个数k-count，即f[k]=sum+(k-count)*x
     * 通过preSum求nums子数组区间和，通过prePreSum求nums子数组区间和之和，f[right]-f[left-1]得到最终结果
     * 时间复杂度O(n+n*log(right-left))=O(n)，空间复杂度O(n) (left=min(nums)，right=sum(nums)=preSum[n]-preSum[0])
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
        //preSum前缀和数组，用于求nums子数组区间和的区间和
        //注意：prePreSum[i]和preSum[i]不同，prePreSum[i]是到preSum[0]-preSum[i]而不是preSum[0]-preSum[i-1]元素之和
        int[] prePreSum = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }

        for (int i = 1; i <= n; i++) {
            prePreSum[i] = prePreSum[i - 1] + preSum[i];
        }

        //前right小子数组区间和之和减去前left-1小子数组区间和之和，得到排序后第left小到第right小子数组区间和之和
        return (f(nums, preSum, prePreSum, n, right) - f(nums, preSum, prePreSum, n, left - 1)) % MOD;
    }

    /**
     * f[i]：nums前i小子数组区间和之和
     * 在求f[k]时，二分查找获取第k小nums子数组区间和为x，则类比getLessEqualThanNumCount()，
     * 通过prePreSum和preSum求小于x的子数组区间和的个数count和小于x的子数组区间和之和sum，
     * 则还需要加上等于x的子数组区间和的个数k-count，即f[k]=sum+(k-count)*x
     * 通过preSum求nums子数组区间和，通过prePreSum求nums子数组区间和之和，f[right]-f[left-1]得到最终结果
     * 时间复杂度O(n+n*log(right-left))=O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param preSum
     * @param prePreSum
     * @param n
     * @param k
     * @return
     */
    private int f(int[] nums, int[] preSum, int[] prePreSum, int n, int k) {
        //二分查找获取第k小nums子数组区间和
        int x = getMinK(nums, preSum, n, k);
        //通过prePreSum求小于x的子数组区间和之和
        int sum = 0;
        //小于x的子数组区间和的个数
        int count = 0;
        //当前行
        int i = 0;
        //当前列
        int j = 0;

        //从左上往右下遍历preSum右上三角，即不同的prePreSum
        //类比getLessEqualThanNumCount()，通过prePreSum和preSum求出小于x的子数组区间和的个数count和小于x的子数组区间和之和sum
        while (i <= n) {
            while (j + 1 <= n && preSum[j + 1] - preSum[i] < x) {
                j++;
            }

            //当前行从nums[i]起始的子数组区间和到nums[j-1]结束，共j-i个子数组区间和小于x
            count = count + j - i;
            //当前行从nums[i]起始的子数组区间和到nums[j-1]结束，小于x的子数组区间和之和为prePreSum[j]-prePreSum[i]-(j-i)*preSum[i]
            //prePreSum[j]-prePreSum[i]为preSum[i+1]到preSum[j]之和，其中preSum[i]为nums[0]-nums[i-1]之和，
            //要得到从nums[i]起始的子数组区间和，还需要减去j-i个preSum[i]，即减去j-i个nums[0]-nums[i-1]之和
            sum = (sum + prePreSum[j] - prePreSum[i] - (j - i) * preSum[i]) % MOD;
            i++;
        }

        //小于x的子数组区间和之和加上等于x的子数组区间和之和，得到前k小子数组区间和之和
        return (sum + (k - count) * x) % MOD;
    }

    /**
     * 二分查找获取第k小nums子数组区间和
     * 对[left,right]进行二分查找，left为nums子数组区间和的最小值，right为nums子数组区间和的最大值，统计nums子数组区间和小于等于mid的个数count，
     * 如果count小于k，则第k小nums子数组区间和在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小nums子数组区间和在mid或mid左边，right=mid
     * 时间复杂度O(n*log(right-left))=O(n)，空间复杂度O(1) (left=min(nums)，right=sum(nums)=preSum[n]-preSum[0])
     *
     * @param nums
     * @param preSum
     * @param n
     * @param k
     * @return
     */
    private int getMinK(int[] nums, int[] preSum, int n, int k) {
        //二分左边界，初始化为nums[i]的最小值
        int left = nums[0];
        //二分右边界，初始化为nums[0]-nums[n-1]元素之和
        int right = preSum[n] - preSum[0];
        int mid;

        for (int i = 0; i < n; i++) {
            left = Math.min(left, nums[i]);
        }

        while (left < right) {
            mid = left + ((right - left) >> 1);

            int count = getLessEqualThanNumCount(preSum, n, mid);

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * 双指针获取nums子数组区间和小于等于num的个数
     * 将nums数组看成二维数组(i,j)，表示nums[i]到nums[j]子数组区间和，即preSum[j+1]-preSum[i]，
     * 则只需要遍历右上三角(始终保持i<=j)，就能遍历到所有的子数组区间和
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param preSum
     * @param n
     * @param num
     * @return
     */
    private int getLessEqualThanNumCount(int[] preSum, int n, int num) {
        int count = 0;
        //当前行
        int i = 0;
        //当前列，初始化为0，表示当前行不存在小于等于num的子数组区间和
        int j = 0;

        //从左上往右下遍历nums右上三角，即不同的preSum
        while (i < n) {
            while (j + 1 <= n && preSum[j + 1] - preSum[i] <= num) {
                j++;
            }

            //当前行从nums[i]起始的子数组区间和到nums[j-1]结束，共j-i个子数组区间和小于等于num
            count = count + j - i;
            i++;
        }

        return count;
    }
}
