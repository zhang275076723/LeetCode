package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/7 09:18
 * @Author zsy
 * @Description 找出第 K 小的数对距离 优先队列类比 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem441、Problem644、Problem658、Problem668、Problem786、Problem878、Problem1201、Problem1482、Problem1508、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
 * 给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length 。
 * 返回 所有数对距离中 第 k 小的数对距离。
 * <p>
 * 输入：nums = [1,3,1], k = 1
 * 输出：0
 * 解释：数对和对应的距离如下：
 * (1,3) -> 2
 * (1,1) -> 0
 * (3,1) -> 2
 * 距离第 1 小的数对是 (1,1) ，距离为 0 。
 * <p>
 * 输入：nums = [1,1,1], k = 2
 * 输出：0
 * <p>
 * 输入：nums = [1,6,1], k = 3
 * 输出：5
 * <p>
 * n == nums.length
 * 2 <= n <= 10^4
 * 0 <= nums[i] <= 10^6
 * 1 <= k <= n * (n - 1) / 2
 */
public class Problem719 {
    public static void main(String[] args) {
        Problem719 problem719 = new Problem719();
        int[] nums = {1, 3, 1};
        int k = 1;
        System.out.println(problem719.smallestDistancePair(nums, k));
        System.out.println(problem719.smallestDistancePair2(nums, k));
    }

    /**
     * 排序+小根堆，优先队列，多路归并排序 (超时)
     * 先排序，排序后每个nums[i]和后面nums[j]作为数对距离加入小根堆，
     * 小根堆堆顶元素出堆，如果arr[1]后面还有元素，则nums[arr[0]]和nums[arr[1]+1]作为下一个数对距离加入小根堆
     * 时间复杂度O(nlogn+klogn)，空间复杂度O(n) (归并排序的空间复杂度O(n)，小根堆中最多存放n个arr)
     *
     * @param nums
     * @param k
     * @return
     */
    public int smallestDistancePair(int[] nums, int k) {
        //由小到大排序
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        //小根堆，arr[0]：nums[i]的下标索引，arr[1]：nums[i]后面nums[j]的下标索引，arr[2]：nums[arr[1]]+nums[arr[0]]
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //nums[arr[1]]-nums[arr[0]]由小到大排序
                return arr1[2] - arr2[2];
            }
        });

        //排序后每个nums[i]和后面nums[j]作为数对距离加入小根堆
        for (int i = 0; i < nums.length - 1; i++) {
            priorityQueue.offer(new int[]{i, i + 1, nums[i + 1] - nums[i]});
        }

        for (int i = 0; i < k - 1; i++) {
            int[] arr = priorityQueue.poll();

            //nums[i]后面nums[j]的下标索引不越界，则nums[arr[0]]和nums[arr[1]+1]作为下一个数对距离加入小根堆
            if (arr[1] + 1 < nums.length) {
                priorityQueue.offer(new int[]{arr[0], arr[1] + 1, nums[arr[1] + 1] - nums[arr[0]]});
            }
        }

        int[] arr = priorityQueue.poll();

        return arr[2];
    }

    /**
     * 排序+二分查找
     * 先由小到大排序，对[left,right]进行二分查找，left为0，right为nums[nums.length-1]-nums[0]，统计数对距离小于等于mid的个数count，
     * 如果count小于k，则第k小的数对距离在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小的数对距离在mid或mid左边，right=mid
     * 通过二分查找，往右找和nums[i]构成数对距离小于等于mid的个数
     * 时间复杂度O(nlogn+log(right-left)*nlogn)=O(nlogn)，空间复杂度O(n) (归并排序的空间复杂度O(n))
     *
     * @param nums
     * @param k
     * @return
     */
    public int smallestDistancePair2(int[] nums, int k) {
        //由小到大排序
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        int left = 0;
        int right = nums[nums.length - 1] - nums[0];
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //数对距离小于等于mid的个数
            int count = 0;

            //通过二分查找，往右找和nums[i]构成数对距离小于等于mid的个数
            for (int i = 0; i < nums.length; i++) {
                count = count + binarySearch(nums, i + 1, nums.length - 1, mid + nums[i]);
            }

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * 通过二分查找，在nums[left]-nums[right]范围内统计小于等于target的个数
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param left
     * @param right
     * @param target
     * @return
     */
    private int binarySearch(int[] nums, int left, int right, int target) {
        if (left > right || nums[left] > target) {
            return 0;
        }

        //第一个小于等于target元素的下标索引
        int first = left;
        //最后一个小于等于target元素的下标索引
        int last = left;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if (nums[mid] <= target) {
                last = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return last - first + 1;
    }

    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
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
