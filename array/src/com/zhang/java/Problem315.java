package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/9/11 8:12
 * @Author zsy
 * @Description 计算右侧小于当前元素的个数 华为机试题 归并排序类比Problem23、Problem148、Problem327、Problem493、Offer51
 * 给你一个整数数组 nums ，按要求返回一个新数组 counts 。
 * 数组 counts 有该性质： counts[i] 的值是 nums[i] 右侧小于 nums[i] 的元素的数量。
 * <p>
 * 输入：nums = [5,2,6,1]
 * 输出：[2,1,1,0]
 * 解释：
 * 5 的右侧有 2 个更小的元素 (2 和 1)
 * 2 的右侧仅有 1 个更小的元素 (1)
 * 6 的右侧有 1 个更小的元素 (1)
 * 1 的右侧有 0 个更小的元素
 * <p>
 * 输入：nums = [-1]
 * 输出：[0]
 * <p>
 * 输入：nums = [-1,-1]
 * 输出：[0,0]
 * <p>
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
public class Problem315 {
    public static void main(String[] args) {
        Problem315 problem315 = new Problem315();
        int[] nums = {5, 2, 6, 1};
        System.out.println(problem315.countSmaller(nums));
        System.out.println(problem315.countSmaller2(nums));
    }

    /**
     * 暴力
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int count = 0;

            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    count++;
                }
            }

            list.add(count);
        }

        return list;
    }

    /**
     * 归并排序
     * 在合并时，如果左边数组的当前元素小于等于右边数组的当前元素，
     * 即左边数组的当前元素大于右边数组的第一个元素到右边数组的当前元素的前一个元素
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller2(int[] nums) {
        //每个元素大于之后元素的个数数组
        int[] resultArr = new int[nums.length];
        //当前元素索引下标在原数组nums中索引下标
        int[] indexArr = new int[nums.length];

        //对于元素索引下标初始化
        for (int i = 0; i < nums.length; i++) {
            indexArr[i] = i;
        }

        mergeSort(nums, 0, nums.length - 1, new int[nums.length], indexArr, new int[nums.length], resultArr);

        List<Integer> list = new ArrayList<>();

        for (int count : resultArr) {
            list.add(count);
        }

        return list;
    }

    private void mergeSort(int[] nums, int left, int right, int[] tempArr,
                           int[] indexArr, int[] tempIndexArr, int[] resultArr) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            mergeSort(nums, left, mid, tempArr, indexArr, tempIndexArr, resultArr);
            mergeSort(nums, mid + 1, right, tempArr, indexArr, tempIndexArr, resultArr);
            merge(nums, left, mid, right, tempArr, indexArr, tempIndexArr, resultArr);
        }
    }

    private void merge(int[] nums, int left, int mid, int right, int[] tempArr,
                       int[] indexArr, int[] tempIndexArr, int[] resultArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            //注意是小于等于，不能是小于，因为要保证nums[i]都大于nums[mid+1]-nums[j-1]，才能统计右侧小于nums[i]的元素个数
            if (nums[i] <= nums[j]) {
                //nums[i]都大于nums[mid+1]-nums[j-1]，统计nums[i]右侧小于nums[i]的元素个数
                resultArr[indexArr[i]] = resultArr[indexArr[i]] + j - mid - 1;
                //更新当前元素索引下标
                tempIndexArr[k] = indexArr[i];
                tempArr[k] = nums[i];
                i++;
                k++;
            } else {
                //更新当前元素索引下标
                tempIndexArr[k] = indexArr[j];
                tempArr[k] = nums[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            //左边数组的当前元素小于等于右边数组的当前元素，
            //即左边数组的当前元素大于右边数组的第一个元素到右边数组的当前元素的前一个元素
            resultArr[indexArr[i]] = resultArr[indexArr[i]] + j - mid - 1;
            //更新当前元素索引下标
            tempIndexArr[k] = indexArr[i];
            tempArr[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            //更新当前元素索引下标
            tempIndexArr[k] = indexArr[j];
            tempArr[k] = nums[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            //更新当前元素索引下标
            indexArr[k] = tempIndexArr[k];
            nums[k] = tempArr[k];
        }
    }
}
