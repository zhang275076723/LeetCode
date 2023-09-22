package com.zhang.java;

/**
 * @Date 2023/9/6 08:21
 * @Author zsy
 * @Description 删除最短的子数组使剩余数组有序 阿里机试题 双指针类比Problem88、Problem977、DifferentSquareCount 子序列和子数组类比
 * 给你一个整数数组 arr ，请你删除一个子数组（可以为空），使得 arr 中剩下的元素是 非递减 的。
 * 一个子数组指的是原数组中连续的一个子序列。
 * 请你返回满足题目要求的最短子数组的长度。
 * <p>
 * 输入：arr = [1,2,3,10,4,2,3,5]
 * 输出：3
 * 解释：我们需要删除的最短子数组是 [10,4,2] ，长度为 3 。剩余元素形成非递减数组 [1,2,3,3,5] 。
 * 另一个正确的解为删除子数组 [3,10,4] 。
 * <p>
 * 输入：arr = [5,4,3,2,1]
 * 输出：4
 * 解释：由于数组是严格递减的，我们只能保留一个元素。所以我们需要删除长度为 4 的子数组，要么删除 [5,4,3,2]，要么删除 [4,3,2,1]。
 * <p>
 * 输入：arr = [1,2,3]
 * 输出：0
 * 解释：数组已经是非递减的了，我们不需要删除任何元素。
 * <p>
 * 输入：arr = [1]
 * 输出：0
 * <p>
 * 1 <= arr.length <= 10^5
 * 0 <= arr[i] <= 10^9
 */
public class Problem1574 {
    public static void main(String[] args) {
        Problem1574 problem1574 = new Problem1574();
        int[] arr = {1, 2, 3, 10, 4, 2, 3, 5};
        System.out.println(problem1574.findLengthOfShortestSubarray(arr));
        System.out.println(problem1574.findLengthOfShortestSubarray2(arr));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int findLengthOfShortestSubarray(int[] arr) {
        if (arr == null || arr.length == 0 || arr.length == 1) {
            return 0;
        }

        //数组是否单调不减
        boolean isNotDescend = true;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                isNotDescend = false;
                break;
            }
        }

        //数组单调不减，则不需要删除元素，直接返回0
        if (isNotDescend) {
            return 0;
        }

        //删除数组中子数组的最小长度，删除之后原数组单调不减
        int min = arr.length - 1;

        //删除区间[i,j]，判断剩下的元素是否单调不减
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                //arr[0]-arr[i-1]和arr[j+1]-arr[arr.length-1]是否单调不减
                boolean flag = true;

                //判断arr[0]-arr[i-1]是否单调不减
                for (int k = 1; k < i; k++) {
                    if (arr[k - 1] > arr[k]) {
                        flag = false;
                        break;
                    }
                }

                //arr[0]-arr[i-1]和arr[j+1]-arr[arr.length-1]不满足单调不减，直接跳出循环
                if (!flag) {
                    break;
                }

                //两个边界元素arr[i-1]和arr[j+1]，是否单调不减
                if (i - 1 >= 0 && j + 1 < arr.length && arr[i - 1] > arr[j + 1]) {
                    continue;
                }

                //判断[j+1]-arr[arr.length-1]是否单调不减
                for (int k = j + 1; k < arr.length - 1; k++) {
                    if (arr[k] > arr[k + 1]) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    min = Math.min(min, j - i + 1);
                }
            }
        }

        return min;
    }

    /**
     * 双指针
     * 删除一个子数组使剩余数组有序，有以下3种情况：
     * 1、删除以arr[0]起始的子数组，得到以arr[arr.length-1]结尾的最长不减子数组，即为删除子数组后剩下的不减元素
     * 2、删除以arr[arr.length-1]结尾的子数组，得到以arr[0]起始的最长不减子数组，即为删除子数组后剩下的不减元素
     * 3、删除arr中间的子数组，通过情况1、2，得到最长不减前缀数组和最长不减后缀数组，
     * 则由最长不减前缀数组和最长不减后缀数组构成最长不减子数组，双指针确定要删除的长度
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     * @return
     */
    public int findLengthOfShortestSubarray2(int[] arr) {
        if (arr == null || arr.length == 0 || arr.length == 1) {
            return 0;
        }

        //最长不减前缀的末尾元素下标索引
        int i = 0;
        //最长不减后缀的首元素下标索引
        int j = arr.length - 1;

        //从前往后找以arr[0]起始的最长不减前缀
        while (i + 1 < arr.length && arr[i] <= arr[i + 1]) {
            i++;
        }

        //从后往前找以arr[arr.length-1]结尾的最长不减后缀
        while (j - 1 >= 0 && arr[j - 1] <= arr[j]) {
            j--;
        }

        //arr数组单调不减，则不需要删除元素，直接返回0
        if (i >= j) {
            return 0;
        }

        //要删除的子数组左右指针，即arr[left+1]-arr[right-1]为要删除的子数组
        int left = 0;
        int right = j;
        //删除数组中子数组的最小长度，初始化保留最长不减前缀或保留最长不减后缀，删除子数组中的较小值，
        //即arr[0]-arr[i]或arr[j]-arr[arr.length-1]
        int min = Math.min(arr.length - i - 1, j);

        //arr[0]-arr[i]和arr[j]-arr[arr.length-1]都单调不减，即只需要减去arr[left+1]-arr[right-1]的子数组，则剩余元素单调不减
        while (left <= i && right < arr.length) {
            //右指针右移，保证arr[left]和arr[right]两个元素保证单调不减，
            //保证arr[0]-arr[left]，arr[right]-arr[arr.length-1]整体单调不减
            while (right < arr.length && arr[left] > arr[right]) {
                right++;
            }

            //arr[left+1]-arr[right-1]即为要删除的子数组，删除之后剩余元素单调不减
            min = Math.min(min, right - left - 1);
            //左指针右移
            left++;
        }

        return min;
    }
}
