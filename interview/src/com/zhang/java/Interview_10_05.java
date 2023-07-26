package com.zhang.java;

/**
 * @Date 2023/5/13 08:13
 * @Author zsy
 * @Description 稀疏数组搜索 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem275、Problem540、Problem852、Problem1095、Problem1150、Offer11、Offer53、Offer53_2、Interview_10_03
 * 稀疏数组搜索。有个排好序的字符串数组，其中散布着一些空字符串，编写一种方法，找出给定字符串的位置。
 * <p>
 * 输入: words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""], s = "ta"
 * 输出：-1
 * 说明: 不存在返回-1。
 * <p>
 * 输入：words = ["at", "", "", "", "ball", "", "", "car", "", "","dad", "", ""], s = "ball"
 * 输出：4
 * <p>
 * words的长度在[1, 1000000]之间
 */
public class Interview_10_05 {
    public static void main(String[] args) {
        Interview_10_05 interview_10_05 = new Interview_10_05();
        String[] words = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};
        String s = "ball";
        System.out.println(interview_10_05.findString(words, s));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 注意：当不满足二分查找条件时，退化为线性查找
     * 时间复杂度O(mlogn)，空间复杂度O(1) (n：words数组的长度，m：字符串s的长度)
     *
     * @param words
     * @param s
     * @return
     */
    public int findString(String[] words, String s) {
        if (words == null || words.length == 0) {
            return -1;
        }

        int left = 0;
        int right = words.length - 1;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            //当words[mid]为""，即不满足二分查找条件时，退化为线性查找，words[left]和s进行比较
            if ("".equals(words[mid])) {
                //words[left]和s相等，则直接返回left
                if (words[left].equals(s)) {
                    return left;
                } else {
                    //words[left]和s不相等，left右移，直接进行下次循环
                    left++;
                    continue;
                }
            }

            //找到s，直接返回mid
            if (words[mid].compareTo(s) == 0) {
                return mid;
            } else if (words[mid].compareTo(s) > 0) {
                //words[mid]的顺序大于s的顺序，往mid左边找
                right = mid - 1;
            } else {
                //words[mid]的顺序小于s的顺序，往mid右边找
                left = mid + 1;
            }
        }

        //没有找到，返回-1
        return -1;
    }
}
