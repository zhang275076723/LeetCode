package com.zhang.java;

/**
 * @Date 2024/1/7 09:18
 * @Author zsy
 * @Description 找出第 K 小的数对距离 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem644、Problem658、Problem668、Problem786、Problem878、Problem1201、Problem1482、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
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
 */
public class Problem719 {
    public static void main(String[] args) {
        Problem719 problem719 = new Problem719();
        int[] nums = {1, 3, 1};
        int k = 1;
        System.out.println(problem719.smallestDistancePair(nums, k));
    }

    /**
     * 排序+二分查找
     * 先排序，对[left,right]进行二分查找，left为0，right为nums[nums.length-1]-nums[0]，统计数对距离小于等于mid的个数count，
     * 如果count小于k，则第k小的数对距离在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小的数对距离在mid或mid左边，right=mid
     * 其中通过二分查找，往右找和每一个nums[i]构成数对距离小于等于mid的个数
     * 时间复杂度O(log(right-left)*nlogn)=O(nlogn)，空间复杂度O(n) (归并排序的空间复杂度O(n))
     *
     * @param nums
     * @param k
     * @return
     */
    public int smallestDistancePair(int[] nums, int k) {
        //由小到大排序
        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        int left = 0;
        int right = nums[nums.length - 1] - nums[0];
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //数对距离小于等于mid的个数
            int count = 0;

            //对每一个nums[i]往右边找和nums[i]构成数对距离小于等于mid的个数
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
     * 使用二分查找，在nums[left]-nums[right]范围内统计小于等于target的个数
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
