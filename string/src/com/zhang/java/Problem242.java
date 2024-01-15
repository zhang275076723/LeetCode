package com.zhang.java;


import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/1/10 08:53
 * @Author zsy
 * @Description 有效的字母异位词 字母异位词类比Problem49、Problem438 哈希表类比Problem1、Problem187、Problem205、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem554、Problem763、Problem1640、Offer50
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 注意：若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
 * <p>
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * <p>
 * 输入: s = "rat", t = "car"
 * 输出: false
 * <p>
 * 1 <= s.length, t.length <= 5 * 10^4
 * s 和 t 仅包含小写字母
 */
public class Problem242 {
    public static void main(String[] args) {
        Problem242 problem242 = new Problem242();
        System.out.println(problem242.isAnagram("anagram", "nagaram"));
        System.out.println(problem242.isAnagram2("anagram", "nagaram"));
    }

    /**
     * 排序
     * 时间复杂度O(nlogn)，空间复杂度O(logn)
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) {
            return false;
        }

        if (s.length() != t.length()) {
            return false;
        }

        char[] arrS = s.toCharArray();
        char[] arrT = t.toCharArray();

        heapSort(arrS);
        heapSort(arrT);

        for (int i = 0; i < arrS.length; i++) {
            //排序之后，当前元素不相同，则不是字母异位词，返回false
            if (arrS[i] != arrT[i]) {
                return false;
            }
        }

        //遍历之后，元素都相同，则是字母异位词，返回true
        return true;
    }

    /**
     * 哈希表
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (s和t仅包含小写字母，所以|Σ|=26)
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram2(String s, String t) {
        if (s == null || t == null) {
            return false;
        }

        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        for (char c : t.toCharArray()) {
            //map中没有c，或者map中c的个数为0，则不是字母异位词，返回false
            if (!map.containsKey(c) || map.get(c) == 0) {
                return false;
            }

            //当前字符c的次数减1
            map.put(c, map.get(c) - 1);
        }

        return true;
    }

    private void heapSort(char[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        for (int i = arr.length - 1; i > 0; i--) {
            char temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i);
        }
    }

    private void heapify(char[] arr, int i, int heapSize) {
        int index = i;
        int leftIndex = i * 2 + 1;
        int rightIndex = i * 2 + 2;

        if (leftIndex < heapSize && arr[index] < arr[leftIndex]) {
            index = leftIndex;
        }

        if (rightIndex < heapSize && arr[index] < arr[rightIndex]) {
            index = rightIndex;
        }

        if (index != i) {
            char temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;

            heapify(arr, index, heapSize);
        }
    }
}
