package com.zhang.java;

/**
 * @Date 2022/4/7 16:21
 * @Author zsy
 * @Description 扑克牌中的顺子 类比Problem659、Problem846
 * 从若干副扑克牌中随机抽 5 张牌，判断是不是一个顺子，即这5张牌是不是连续的。
 * 2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 * 数组长度为 5
 * 数组的数取值为 [0, 13]
 * <p>
 * 输入: [1,2,3,4,5]
 * 输出: True
 * <p>
 * 输入: [0,0,1,2,5]
 * 输出: True
 * <p>
 * 数组长度为 5
 * 数组的数取值为 [0, 13] .
 */
public class Offer61 {
    public static void main(String[] args) {
        Offer61 offer61 = new Offer61();
        int[] nums = {9, 10, 4, 0, 9};
        System.out.println(offer61.isStraight(nums));
    }

    /**
     * 排序+模拟
     * 由小到大排序，统计大小王个数，判断除了大小王之外，最大牌和最小牌之差是否小于5，如果小于5，则能构成顺子；
     * 如果除了大小王之外，存在重复的牌，则不能构成顺子
     * 时间复杂度O(nlogn)，空间复杂度O(n) (因为使用的排序算法是归并排序)
     *
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {
        //大小王牌的数量
        int joker = 0;

        mergeSort(nums, 0, nums.length - 1, new int[nums.length]);

        for (int i = 0; i < nums.length; i++) {
            //统计大小王个数
            if (nums[i] == 0) {
                joker++;
            } else {
                //当前牌和前一个牌相同，则不能构成顺子
                if (i != 0 && nums[i - 1] == nums[i]) {
                    return false;
                }
            }
        }

        //除了大小王之外，最大牌和最小牌之差小于5，即可构成顺子
        return nums[nums.length - 1] - nums[joker] < 5;
    }

    /**
     * 归并排序，时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param nums
     * @param left
     * @param right
     * @param tempArr
     */
    public void mergeSort(int[] nums, int left, int right, int[] tempArr) {
        if (left < right) {
            // >> 优先级小于 + ，所以需要在使用 >> 的时候添加括号
            int mid = left + ((right - left) >> 1);
            mergeSort(nums, left, mid, tempArr);
            mergeSort(nums, mid + 1, right, tempArr);
            merge(nums, left, mid, right, tempArr);
        }
    }

    public void merge(int[] nums, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = i;

        while (i <= mid && j <= right) {
            if (nums[i] < nums[j]) {
                tempArr[k] = nums[i];
                i++;
            } else {
                tempArr[k] = nums[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            tempArr[k] = nums[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = nums[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            nums[k] = tempArr[k];
        }
    }
}
