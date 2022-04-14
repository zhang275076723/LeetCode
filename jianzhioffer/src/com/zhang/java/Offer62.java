package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/4/8 8:33
 * @Author zsy
 * @Description 0, 1, ···, n-1这n个数字排成一个圆圈，从数字0开始，
 * 每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。
 * 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，
 * 则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
 * <p>
 * 输入: n = 5, m = 3
 * 输出: 3
 * <p>
 * 输入: n = 10, m = 17
 * 输出: 2
 * <p>
 * 1 <= n <= 10^5
 * 1 <= m <= 10^6
 */
public class Offer62 {
    public static void main(String[] args) {
        Offer62 offer62 = new Offer62();
        System.out.println(offer62.lastRemaining(5, 3));
        System.out.println(offer62.lastRemaining2(5, 3));
        System.out.println(offer62.lastRemaining3(5, 3));
        System.out.println(offer62.lastRemaining4(5, 3));
    }

    /**
     * 自己的解，超时，时间复杂度O(nm)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        int[] nums = new int[n];
        //当前数组元素的左指针
        int left = 0;
        //当前数组元素的右指针
        int right = n - 1;
        //当前数组元素的长度
        int curLenth = right - left + 1;
        //要删除的元素索引
        int curIndex = left;

        //数组元素赋初值
        for (int i = 0; i < n; i++) {
            nums[i] = i;
        }

        while (left < right) {
            curIndex = ((m % curLenth + curLenth - 1) % curLenth + curIndex - left) % curLenth + left;

            //数组元素较少的一端移动
            int mid = left + ((right - left) >> 1);
            if (curIndex < mid) {
                for (int i = curIndex - 1; i >= left; i--) {
                    nums[i + 1] = nums[i];
                }
                //指向下一个元素
                curIndex = curIndex + 1;
                left++;
            } else {
                for (int i = curIndex + 1; i <= right; i++) {
                    nums[i - 1] = nums[i];
                }
                //指向下一个元素
                if (curIndex == right) {
                    curIndex = left;
                }
                right--;
            }

            curLenth--;
        }

        return nums[left];
    }

    /**
     * 自己的解优化，使用集合作为动态数组替代原先数组，时间复杂度(nm)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining2(int n, int m) {
        //指定list大小
        List<Integer> list = new ArrayList<>(n);
        //要删除的元素索引
        int curIndex = 0;

        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        while (list.size() > 1) {
            curIndex = (curIndex + m - 1) % list.size();
            list.remove(curIndex);
        }

        return list.get(0);
    }

    /**
     * 约瑟夫环，数学方法，时间复杂度(n)，空间复杂度O(1)
     * <p>
     * 公式：第i轮元素索引 = (第i+1轮元素索引 + m) % 第i轮元素个数
     * 第一轮：0 1 2 3 4 取出2
     * 第二轮：3 4 0 1   取出0
     * 第三轮：1 3 4     取出4
     * 第四轮：1 3       取出1
     * 第五轮：3         取出3
     * 最后一轮元素索引为0，即为要求的元素，可以推出第四轮元素的索引为(0 + 3) % 2 = 1
     * 根据第四轮元素索引为1，可以推出第三轮元素的索引为(1 + 3) % 3 = 1
     * 根据第三轮元素索引为1，可以推出第二轮元素的索引为(1 + 3) % 4 = 0
     * 根据第二轮元素索引为1，可以推出第一轮元素的索引为(0 + 3) % 5 = 3
     * 根据第一轮元素索引为3，可以得到要求的元素为3
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining3(int n, int m) {
        int index = 0;
        for (int i = 2; i <= n; i++) {
            index = (index + m) % i;
        }
        return index;
    }

    /**
     * 迭代，约瑟夫环，数学方法，时间复杂度(n)，空间复杂度O(n)
     *
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining4(int n, int m) {
        return f(n, m);
    }

    public int f(int n, int m) {
        if (n == 1) {
            return 0;
        }
        int index = f(n - 1, m);
        return (index + m) % n;
    }

}
