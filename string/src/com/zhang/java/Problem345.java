package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2023/12/27 08:34
 * @Author zsy
 * @Description 反转字符串中的元音字母 类比Problem58、Problem151、Problem186、Problem344、Problem541、Problem557、Offer58、Offer58_2 元音类比Problem824、Problem966、Problem1119、Problem1220、Problem1371、Problem1456、Problem1641、Problem1704、Problem1839、Problem2062、Problem2063、Problem2559、Problem2586、Problem2785
 * 给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
 * 元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现不止一次。
 * <p>
 * 输入：s = "hello"
 * 输出："holle"
 * <p>
 * 输入：s = "leetcode"
 * 输出："leotcede"
 */
public class Problem345 {
    public static void main(String[] args) {
        Problem345 problem345 = new Problem345();
        String s = "leetcode";
        System.out.println(problem345.reverseVowels(s));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        //大写和小写共10个元音字符的集合
        Set<Character> vowelSet = new HashSet<Character>() {{
            add('a');
            add('e');
            add('i');
            add('o');
            add('u');
            add('A');
            add('E');
            add('I');
            add('O');
            add('U');
        }};

        char[] arr = s.toCharArray();
        int i = 0;
        int j = arr.length - 1;

        while (i < j) {
            //从前往后找第一个元音
            while (i < j && !vowelSet.contains(arr[i])) {
                i++;
            }

            //从后往前找最后一个元音
            while (i < j && !vowelSet.contains(arr[j])) {
                j--;
            }

            //交换元音
            swap(arr, i, j);

            i++;
            j--;
        }

        return new String(arr);
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
