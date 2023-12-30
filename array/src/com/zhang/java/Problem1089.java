package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2023/12/31 09:03
 * @Author zsy
 * @Description 复写零
 * 给你一个长度固定的整数数组 arr ，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
 * 注意：请不要在超过该数组长度的位置写入元素。
 * 请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
 * <p>
 * 输入：arr = [1,0,2,3,0,4,5,0]
 * 输出：[1,0,0,2,3,0,0,4]
 * 解释：调用函数后，输入的数组将被修改为：[1,0,0,2,3,0,0,4]
 * <p>
 * 输入：arr = [1,2,3]
 * 输出：[1,2,3]
 * 解释：调用函数后，输入的数组将被修改为：[1,2,3]
 * <p>
 * 1 <= arr.length <= 10^4
 * 0 <= arr[i] <= 9
 */
public class Problem1089 {
    public static void main(String[] args) {
        Problem1089 problem1089 = new Problem1089();
        int[] arr = {1, 0, 2, 3, 0, 4, 5, 0};
//        int[] arr = {0, 0, 0, 0, 0, 0, 0};
        problem1089.duplicateZeros(arr);
        System.out.println(Arrays.toString(arr));
        //因为是对原数组arr修改，arr需要重新赋值
        arr = new int[]{1, 0, 2, 3, 0, 4, 5, 0};
        problem1089.duplicateZeros2(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 暴力
     * arr[i]为0，则arr[i+1]赋值为0，arr[i+2]-arr[arr.length-2]整体后移1位
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param arr
     */
    public void duplicateZeros(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            //arr[i]为0，则arr[i+1]赋值为0，arr[i+2]-arr[arr.length-2]整体后移1位
            if (arr[i] == 0) {
                if (i + 1 < arr.length) {
                    for (int j = arr.length - 2; j > i; j--) {
                        arr[j + 1] = arr[j];
                    }

                    arr[i + 1] = 0;
                    //因为多复写一个零，i右移1位，每次循环i都右移1位，相当于右移2位
                    i++;
                }
            }
        }
    }

    /**
     * 双指针
     * 指针i指向原arr数组，指针j指向修改后的arr数组，当arr[i]为0，则需要多复写一个零，j需要移动2位，
     * 从前往后遍历，当j指向arr末尾时，得到arr[0]-arr[i]即为需要重新写入的元素，再从后往前遍历，将arr[0]-arr[i]写回arr
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param arr
     */
    public void duplicateZeros2(int[] arr) {
        //原arr数组指针
        int i = 0;
        //修改后的arr数组指针，当arr[i]为0，则需要多复写一个零，j需要移动2位
        int j = 0;

        while (j < arr.length) {
            //arr[i]为0，则需要多复写一个零，j右移2位
            if (arr[i] == 0) {
                i++;
                j = j + 2;
            } else {
                i++;
                j++;
            }
        }

        //使i和j在数组范围之内，开始从后往前写回
        i--;
        j--;

        while (i >= 0) {
            //arr[i]为0，则需要多复写一个零，j左移2位
            if (arr[i] == 0) {
                //arr[i]为0，但已经遍历到数组末尾，不能多复写一个零的特殊情况
                if (j == arr.length) {
                    arr[j - 1] = 0;
                    i--;
                    j = j - 2;
                } else {
                    arr[j] = 0;
                    arr[j - 1] = 0;
                    i--;
                    j = j - 2;
                }
            } else {
                arr[j] = arr[i];
                i--;
                j--;
            }
        }
    }
}
