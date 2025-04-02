package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/11/8 08:13
 * @Author zsy
 * @Description 找出数组的第 K 大和 Amazon机试题 类比Problem78 类比Problem373、Problem378、Problem644、Problem668、Problem719、Problem786、Problem1439、Problem1508、Problem2040 优先队列类比 二分查找类比
 * 给你一个整数数组 nums 和一个 正 整数 k 。
 * 你可以选择数组的任一 子序列 并且对其全部元素求和。
 * 数组的 第 k 大和 定义为：可以获得的第 k 个 最大 子序列和（子序列和允许出现重复）
 * 返回数组的 第 k 大和 。
 * 子序列是一个可以由其他数组删除某些或不删除元素派生而来的数组，且派生过程不改变剩余元素的顺序。
 * 注意：空子序列的和视作 0 。
 * <p>
 * 输入：nums = [2,4,-2], k = 5
 * 输出：2
 * 解释：所有可能获得的子序列和列出如下，按递减顺序排列：
 * 6、4、4、2、2、0、0、-2
 * 数组的第 5 大和是 2 。
 * <p>
 * 输入：nums = [1,-2,3,4,-10,12], k = 16
 * 输出：10
 * 解释：数组的第 16 大和是 10 。
 * <p>
 * n == nums.length
 * 1 <= n <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 1 <= k <= min(2000, 2^n)
 */
public class Problem2386 {
    /**
     * 回溯+剪枝中nums中元素都取绝对值后数组的子序列之和小于等于mid的个数
     * 注意：count最大为k，如果大于k，则直接剪枝，如果不剪枝，则超时
     */
    private int count;

    public static void main(String[] args) {
        Problem2386 problem2386 = new Problem2386();
        int[] nums = {1, -2, 3, 4, -10, 12};
        int k = 16;
        System.out.println(problem2386.kSum(nums, k));
        //修改了原数组，nums需要重新赋值
        nums = new int[]{1, -2, 3, 4, -10, 12};
        System.out.println(problem2386.kSum2(nums, k));
    }

    /**
     * 排序+优先队列，小根堆
     * 核心思想：nums中非负数之和为sum，即nums最大子序列之和为sum，sum减去nums中元素都取绝对值后数组的第k小子序列之和，则得到nums的第k大子序列之和
     * 对nums取绝对值，并且由小到大排序，求出absNums的第k小子序列之和，从而得到nums的第k大子序列之和
     * 时间复杂度O(nlogn+klogk)，空间复杂度O(logn+k) (堆排序空间需要O(logn)，小根堆空间需要O(k))
     * <p>
     * 例如：absNums=[1,2,3]
     * 初始化空子序列[]
     * []末尾添加absNums[0]，得到[1]
     * [1]末尾添加absNums[1]，得到[1,2]；也可以[1]删除末尾元素添加absNums[1]，得到[2]
     * [1,2]末尾添加absNums[2]，得到[1,2,3]；也可以[1,2]删除末尾元素添加absNums[2]，得到[1,3]
     * ...
     * 即每个子序列都可以添加absNums[i]得到新的子序列，或者删除当前子序列的末尾元素再添加absNums[i]得到新的子序列，
     * 得到的这2个子序列之和都大于当前子序列之和，也就和小根堆匹配，用来获取第k小的absNums子序列之和
     *
     * @param nums
     * @param k
     * @return
     */
    public long kSum(int[] nums, int k) {
        //nums中非负数之和
        //使用long，避免int溢出
        long sum = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                sum = sum + nums[i];
            } else {
                //负数nums[i]取绝对值，变为正数
                nums[i] = -nums[i];
            }
        }

        //必须由小到大排序，保证从前往后遍历每次得到的子序列之和逐渐增大，
        //这样才能通过k-1次循环得到nums中元素都取绝对值后数组的第k小子序列之和
        heapSort(nums);

        //注意：此时nums中元素都为非负数，并且已经由小到大排序，为了方便表示记为absNums

        //arr[0]：absNums[0]-absNums[arr[1]]中当前子序列之和，arr[1]：当前遍历到的absNums的下标索引
        PriorityQueue<long[]> priorityQueue = new PriorityQueue<>(new Comparator<long[]>() {
            @Override
            public int compare(long[] arr1, long[] arr2) {
                //不能写成return (int) (arr1[0] - arr2[0]);，因为long相减再转为int有可能在int范围溢出
                return Long.compare(arr1[0], arr2[0]);
            }
        });

        //空子序列加入小根堆
        priorityQueue.offer(new long[]{0, -1});

        //小根堆移除k-1个元素，堆顶元素即为nums中元素都取绝对值后数组的第k小子序列之和
        for (int i = 0; i < k - 1; i++) {
            long[] arr = priorityQueue.poll();

            if (arr[1] + 1 < nums.length) {
                //当前子序列添加nums[arr[1]+1]得到比当前子序列之和大的新的子序列，入小根堆
                priorityQueue.offer(new long[]{arr[0] + nums[(int) (arr[1] + 1)], arr[1] + 1});

                //当前子序列不是空子序列，则可以删除nums[arr[1]]
                if (arr[1] >= 0) {
                    //当前子序列删除nums[arr[1]]，再添加nums[arr[1]+1]得到比当前子序列之和大的新的子序列，入小根堆
                    priorityQueue.offer(new long[]{arr[0] - nums[(int) arr[1]] + nums[(int) (arr[1] + 1)], arr[1] + 1});
                }
            }
        }

        long[] arr = priorityQueue.poll();

        //nums第k大子序列之和等于sum减去nums中元素都取绝对值后数组的第k小子序列之和
        return sum - arr[0];
    }

    /**
     * 排序+二分查找+回溯+剪枝
     * 核心思想：nums中非负数之和为sum，即nums最大子序列之和为sum，sum减去nums中元素都取绝对值后数组的第k小子序列之和，则得到nums的第k大子序列之和
     * 对nums取绝对值，并且由小到大排序，求出absNums的第k小子序列之和，从而得到nums的第k大子序列之和
     * absNums由小到大排序，对[left,right]进行二分查找，left为0，right为sum(|nums[i]|)，统计nums中元素都取绝对值后数组的子序列之和小于等于mid的个数count，
     * 如果count小于k，则nums中元素都取绝对值后数组的第k小子序列之和在mid右边，left=mid+1；
     * 如果count大于等于k，则nums中元素都取绝对值后数组的第k小子序列之和在mid或mid左边，right=mid
     * 时间复杂度O(nlogn+k*log(sum(|nums[i]|)))，空间复杂度O(min(n,k)) (递归空间需要O(min(n,k)))
     *
     * @param nums
     * @param k
     * @return
     */
    public long kSum2(int[] nums, int k) {
        //nums中非负数之和
        //使用long，避免int溢出
        long sum = 0;
        //nums中元素都取绝对值后数组的元素之和
        long absSum = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                sum = sum + nums[i];
            } else {
                //负数nums[i]取绝对值，变为正数
                nums[i] = -nums[i];
            }

            absSum = absSum + nums[i];
        }

        //必须由小到大排序，保证从前往后遍历每次得到的子序列之和逐渐增大，
        //这样才能通过回溯+剪枝O(min(n,k))得到nums中元素都取绝对值后数组的子序列之和小于等于mid的个数
        heapSort(nums);

        //注意：此时nums中元素都为非负数，并且已经由小到大排序，为了方便表示记为absNums

        long left = 0;
        long right = absSum;
        long mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //nums中元素都取绝对值后数组的子序列之和小于等于mid的个数
            //初始化为1，因为backtrack方法未考虑空子序列
            //注意：count最大为k，如果大于k，则直接剪枝，如果不剪枝，则超时
            count = 1;

            backtrack(0, 0, nums, k, mid);

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        //nums第k大子序列之和等于sum减去nums中元素都取绝对值后数组的第k小子序列之和
        return sum - left;
    }

    /**
     * nums中元素都取绝对值后数组的子序列之和小于等于num的个数，如果个数大于等于k，则直接返回
     * 注意：当前方法不是在nums遍历到末尾时才判断得到的子序列之和是否小于等于num，而是在遍历到nums[t]时就判断，避免超时
     * 注意：当前方法未考虑空子序列，所以count初始化为1
     * 时间复杂度O(k)，空间复杂度O(min(n,k))
     *
     * @param t
     * @param sum  nums中元素都取绝对值后数组遍历到nums[t]之前的子序列之和
     * @param nums
     * @param k
     * @param num
     */
    private void backtrack(int t, long sum, int[] nums, int k, long num) {
        //已经得到k个nums中元素都取绝对值后数组的子序列之和小于等于num的子序列，或者已经遍历到nums末尾，
        //或者遍历到nums[t]的当前子序列之和大于num，则直接返回
        if (count >= k || t == nums.length || sum + nums[t] > num) {
            return;
        }

        //遍历到nums[t]的当前子序列之和小于等于num，则count++
        count++;

        //遍历到nums[t]的当前子序列之和加上nums[t]
        backtrack(t + 1, sum + nums[t], nums, k, num);
        //遍历到nums[t]的当前子序列之和不加nums[t]
        backtrack(t + 1, sum, nums, k, num);
    }

    private void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(int[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        if (leftIndex < heapSize && arr[leftIndex] > arr[index]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[rightIndex] > arr[index]) {
            index = rightIndex;
        }

        if (index != i) {
            int temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
