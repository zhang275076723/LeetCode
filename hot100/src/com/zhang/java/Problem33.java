package com.zhang.java;

/**
 * @Date 2022/4/18 8:21
 * @Author zsy
 * @Description 搜索旋转排序数组 类比Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem852、Offer11、Offer53、Offer53_2
 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，
 * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
 * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
 * 给你 旋转后 的数组 nums 和一个整数 target ，
 * 如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
 * <p>
 * 输入：nums = [4,5,6,7,0,1,2], target = 0
 * 输出：4
 * <p>
 * 输入：nums = [4,5,6,7,0,1,2], target = 3
 * 输出：-1
 * <p>
 * 输入：nums = [1], target = 0
 * 输出：-1
 * <p>
 * 1 <= nums.length <= 5000
 * -10^4 <= nums[i] <= 10^4
 * nums 中的每个值都 独一无二
 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
 * -10^4 <= target <= 10^4
 */
public class Problem33 {
    public static void main(String[] args) {
        Problem33 problem33 = new Problem33();
        int[] nums = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        System.out.println(problem33.search(nums, target));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            //nums[mid]和target相等，则找到，直接返回下标索引mid
            if (nums[mid] == target) {
                return mid;
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

        return -1;
    }
}
