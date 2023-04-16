package com.zhang.java;

/**
 * @Date 2022/9/20 11:41
 * @Author zsy
 * @Description 搜索旋转排序数组 II 类比Problem33、Problem34、Problem35、Problem153、Problem154、Problem162、Problem852、Offer11、Offer53、Offer53_2
 * 已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能变为 [4,5,6,6,7,0,1,2,4,4] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。
 * 如果 nums 中存在这个目标值 target ，则返回 true ，否则返回 false 。
 * 你必须尽可能减少整个操作步骤。
 * <p>
 * 输入：nums = [2,5,6,0,0,1,2], target = 0
 * 输出：true
 * <p>
 * 输入：nums = [2,5,6,0,0,1,2], target = 3
 * 输出：false
 * <p>
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 */
public class Problem81 {
    public static void main(String[] args) {
        Problem81 problem81 = new Problem81();
        int[] nums = {2, 5, 6, 0, 0, 1, 2};
        int target = 0;
        System.out.println(problem81.search(nums, target));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 平均时间复杂度O(logn)，最坏时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left <= right) {
            //left去重
            while (left + 1 <= right && nums[left] == nums[left + 1]) {
                left++;
            }

            //right去重
            while (left <= right - 1 && nums[right - 1] == nums[right]) {
                right--;
            }

            mid = left + ((right - left) >> 1);

            //nums[mid]和target相等，则找到，直接返回true
            if (nums[mid] == target) {
                return true;
            } else {
                //nums[mid]和target不相等，则根据nums[left]-nums[mid]和nums[mid]-nums[right]哪边是单调的，
                //单调部分再和target比较，判断往左找还是往右找

                //nums[mid]-nums[right]单调递增
                if (nums[mid] < nums[right]) {
                    //target在nums[mid+1]-nums[right]之中
                    if (nums[mid] < target && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        //target在nums[left]-nums[mid-1]之中
                        right = mid - 1;
                    }
                } else {
                    //nums[left]-nums[mid]单调递增

                    //target在nums[left]-nums[mid-1]之中
                    if (nums[left] <= target && target < nums[mid]) {
                        right = mid - 1;
                    } else {
                        //target在nums[mid+1]-nums[right]之中
                        left = mid + 1;
                    }
                }
            }
        }

        return false;
    }
}
