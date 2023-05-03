package com.zhang.java;

/**
 * @Date 2023/5/1 08:26
 * @Author zsy
 * @Description 有效三角形的个数 双指针类比Problem15、Problem16、Problem18、Problem456
 * 给定一个包含非负整数的数组 nums ，返回其中可以组成三角形三条边的三元组个数。
 * <p>
 * 输入: nums = [2,2,3,4]
 * 输出: 3
 * 解释:有效的组合是:
 * 2,3,4 (使用第一个 2)
 * 2,3,4 (使用第二个 2)
 * 2,2,3
 * <p>
 * 输入: nums = [4,2,3,4]
 * 输出: 4
 * <p>
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 1000
 */
public class Problem611 {
    public static void main(String[] args) {
        Problem611 problem611 = new Problem611();
        int[] nums = {4, 2, 3, 4};
        System.out.println(problem611.triangleNumber(nums));
    }

    /**
     * 排序+双指针
     * 先排序，从大到小每次确定最长的一条边nums[i]，判断nums[left]+nums[right]和nums[i]大小关系，
     * nums[left]+nums[right]>nums[i]，则nums[left]-nums[right-1]+nums[right]都大于nums[i]，
     * 有right-left个满足要求的三角形，right--
     * nums[left]+nums[right]<=nums[i]，则left++
     * 时间复杂度O(n^2)，空间复杂度O(n) (归并排序使用的额外数组O(n))
     *
     * @param nums
     * @return
     */
    public int triangleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        int count = 0;

        //从大到小每次确定最长的一条边nums[i]
        for (int i = nums.length - 1; i >= 2; i--) {
            int left = 0;
            int right = i - 1;

            while (left < right) {
                //nums[left]+nums[right]>nums[i]，则nums[left]-nums[right-1]+nums[right]都大于nums[i]，
                //有right-left个满足要求的三角形，right--
                if (nums[left] + nums[right] > nums[i]) {
                    count = count + right - left;
                    right--;
                } else {
                    //nums[left]+nums[right]<=nums[i]，则left++
                    left++;
                }
            }
        }

        return count;
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
