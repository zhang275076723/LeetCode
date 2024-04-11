package com.zhang.java;

/**
 * @Date 2024/4/6 08:52
 * @Author zsy
 * @Description 找出数组中的第一个回文字符串 回文类比
 * 给你一个字符串数组 words ，找出并返回数组中的 第一个回文字符串 。
 * 如果不存在满足要求的字符串，返回一个 空字符串 "" 。
 * 回文字符串 的定义为：如果一个字符串正着读和反着读一样，那么该字符串就是一个 回文字符串 。
 * <p>
 * 输入：words = ["abc","car","ada","racecar","cool"]
 * 输出："ada"
 * 解释：第一个回文字符串是 "ada" 。
 * 注意，"racecar" 也是回文字符串，但它不是第一个。
 * <p>
 * 输入：words = ["notapalindrome","racecar"]
 * 输出："racecar"
 * 解释：第一个也是唯一一个回文字符串是 "racecar" 。
 * <p>
 * 输入：words = ["def","ghi"]
 * 输出：""
 * 解释：不存在回文字符串，所以返回一个空字符串。
 * <p>
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 100
 * words[i] 仅由小写英文字母组成
 */
public class Problem2108 {
    public static void main(String[] args) {
        Problem2108 problem2108 = new Problem2108();
        String[] words = {"abc", "car", "ada", "racecar", "cool"};
        System.out.println(problem2108.firstPalindrome(words));
    }

    /**
     * 模拟
     * 时间复杂度O(nm)，空间复杂度O(1) (n=words.length，m=words[i]的平均长度)
     *
     * @param words
     * @return
     */
    public String firstPalindrome(String[] words) {
        for (int i = 0; i < words.length; i++) {
            if (isPalindrome(words[i])) {
                return words[i];
            }
        }

        //遍历结束没有找到回文，返回""
        return "";
    }

    private boolean isPalindrome(String word) {
        int left = 0;
        int right = word.length() - 1;

        while (left < right) {
            if (word.charAt(left) != word.charAt(right)) {
                return false;
            } else {
                left++;
                right--;
            }
        }

        return true;
    }
}
