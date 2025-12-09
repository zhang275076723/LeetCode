package com.zhang.java;

import java.util.Random;

/**
 * @Date 2024/8/7 08:53
 * @Author zsy
 * @Description 最高频元素的频数 排序+滑动窗口类比Problem532、Problem632、Problem2009 类比Problem834、Problem3652、ElevatorSchedule
 * 元素的 频数 是该元素在一个数组中出现的次数。
 * 给你一个整数数组 nums 和一个整数 k 。
 * 在一步操作中，你可以选择 nums 的一个下标，并将该下标对应元素的值增加 1 。
 * 执行最多 k 次操作后，返回数组中最高频元素的 最大可能频数 。
 * <p>
 * 输入：nums = [1,2,4], k = 5
 * 输出：3
 * 解释：对第一个元素执行 3 次递增操作，对第二个元素执 2 次递增操作，此时 nums = [4,4,4] 。
 * 4 是数组中最高频元素，频数是 3 。
 * <p>
 * 输入：nums = [1,4,8,13], k = 5
 * 输出：2
 * 解释：存在多种最优解决方案：
 * - 对第一个元素执行 3 次递增操作，此时 nums = [4,4,8,13] 。4 是数组中最高频元素，频数是 2 。
 * - 对第二个元素执行 4 次递增操作，此时 nums = [1,8,8,13] 。8 是数组中最高频元素，频数是 2 。
 * - 对第三个元素执行 5 次递增操作，此时 nums = [1,4,13,13] 。13 是数组中最高频元素，频数是 2 。
 * <p>
 * 输入：nums = [3,9,6], k = 2
 * 输出：1
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^5
 * 1 <= k <= 10^5
 */
public class Problem1838 {
    public static void main(String[] args) {
        Problem1838 problem1838 = new Problem1838();
        int[] nums = {1, 4, 8, 13};
        int k = 5;
        System.out.println(problem1838.maxFrequency(nums, k));
        System.out.println(problem1838.maxFrequency2(nums, k));
    }

    /**
     * 暴力
     * nums中元素由小到大排序，nums[i]作为最多进行k次修改后数组中出现次数最多的元素，得到修改后nums[i]的频数
     * 时间复杂度O(n^2)，空间复杂度O(logn)
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxFrequency(int[] nums, int k) {
        //nums中元素由小到大排序
        quickSort(nums, 0, nums.length - 1);

        //最多进行k次修改后数组中出现次数最多的元素的出现次数
        int maxFrequency = 1;

        //nums[i]作为最多进行k次修改后数组中出现次数最多的元素
        for (int i = 0; i < nums.length; i++) {
            //剩余操作次数
            int count = k;
            int frequency = 1;
            int j = i - 1;

            //在nums[0]-nums[i-1]中找修改后等于nums[i]的个数
            while (j >= 0 && count - (nums[i] - nums[j]) >= 0) {
                count = count - (nums[i] - nums[j]);
                frequency++;
                j--;
            }

            maxFrequency = Math.max(maxFrequency, frequency);
        }

        return maxFrequency;
    }

    /**
     * 排序+滑动窗口
     * nums中元素由小到大排序，右边界右移一次，滑动窗口中元素都变为新的右边界元素需要的操作次数=上次滑动窗口需要的操作次数+
     * 上次滑动窗口中元素都变为右边界再变为新的右边界的操作次数((right-left)*(nums[right]-nums[right-1]))
     * 左边界右移一次，滑动窗口中元素都变为右边界元素需要的操作次数=上次滑动窗口需要的操作次数-
     * 上次滑动窗口左边界变为右边界的操作次数(nums[right]-nums[left])
     * 时间复杂度O(nlogn+n)=O(nlogn)，空间复杂度O(logn)
     * <p>
     * 例如：nums=[1,2,4,6]
     * 此时，left=1，right=3，此时滑动窗口中元素都变为nums[right-1]=4的操作次数为2，
     * right右移一位，新的滑动窗口中元素都变为nums[right]=6的操作次数为2+(right-left)*(nums[right]-nums[right-1])=2+(3-1)*(6-4)=6
     * left右移一位，新的滑动窗口中元素都变为nums[right]=6的操作次数为6-(nums[right]-nums[left])=6-(6-2)=2
     *
     * @param nums
     * @param k
     * @return
     */
    public int maxFrequency2(int[] nums, int k) {
        //nums中元素由小到大排序
        quickSort(nums, 0, nums.length - 1);

        //最多进行k次修改后数组中出现次数最多的元素的出现次数
        int maxFrequency = 1;
        //滑动窗口中元素都变为右边界需要的操作次数
        //使用long，避免nums中元素相加溢出
        long count = 0;
        int left = 0;
        //注意右边界从1开始遍历
        int right = 1;

        while (right < nums.length) {
            //[left,right-1]中元素都变为新的右边界元素nums[right]需要的操作次数=上次滑动窗口需要的操作次数count+
            //上次滑动窗口中元素都变为右边界再变为新的右边界的操作次数((right-left)*(nums[right]-nums[right-1]))
            count = count + (long) (right - left) * (nums[right] - nums[right - 1]);

            //[left,right]中元素都变为右边界元素nums[right]需要的操作次数=上次滑动窗口需要的操作次数count-
            //上次滑动窗口左边界nums[left]变为右边界nums[right]的操作次数(nums[right]-nums[left])
            while (count > k) {
                count = count - (nums[right] - nums[left]);
                left++;
            }

            maxFrequency = Math.max(maxFrequency, right - left + 1);
            right++;
        }

        return maxFrequency;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(nums, left, right);
        quickSort(nums, left, pivot - 1);
        quickSort(nums, pivot + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int randomIndex = new Random().nextInt(right - left + 1) + left;

        int value = nums[randomIndex];
        nums[randomIndex] = nums[left];
        nums[left] = value;

        int temp = nums[left];

        while (left < right) {
            while (left < right && nums[right] >= temp) {
                right--;
            }

            nums[left] = nums[right];

            while (left < right && nums[left] <= temp) {
                left++;
            }

            nums[right] = nums[left];
        }

        nums[left] = temp;

        return left;
    }
}
