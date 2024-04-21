package com.zhang.java;

/**
 * @Date 2024/4/16 08:40
 * @Author zsy
 * @Description 字典序最小回文串 回文类比 双指针类比
 * 给你一个由 小写英文字母 组成的字符串 s ，你可以对其执行一些操作。
 * 在一步操作中，你可以用其他小写英文字母 替换  s 中的一个字符。
 * 请你执行 尽可能少的操作 ，使 s 变成一个 回文串 。
 * 如果执行 最少 操作次数的方案不止一种，则只需选取 字典序最小 的方案。
 * 对于两个长度相同的字符串 a 和 b ，在 a 和 b 出现不同的第一个位置，
 * 如果该位置上 a 中对应字母比 b 中对应字母在字母表中出现顺序更早，则认为 a 的字典序比 b 的字典序要小。
 * 返回最终的回文字符串。
 * <p>
 * 输入：s = "egcfe"
 * 输出："efcfe"
 * 解释：将 "egcfe" 变成回文字符串的最小操作次数为 1 ，修改 1 次得到的字典序最小回文字符串是 "efcfe"，只需将 'g' 改为 'f' 。
 * <p>
 * 输入：s = "abcd"
 * 输出："abba"
 * 解释：将 "abcd" 变成回文字符串的最小操作次数为 2 ，修改 2 次得到的字典序最小回文字符串是 "abba" 。
 * <p>
 * 输入：s = "seven"
 * 输出："neven"
 * 解释：将 "seven" 变成回文字符串的最小操作次数为 1 ，修改 1 次得到的字典序最小回文字符串是 "neven" 。
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由小写英文字母组成
 */
public class Problem2697 {
    public static void main(String[] args) {
        Problem2697 problem2697 = new Problem2697();
        String s = "abcd";
        System.out.println(problem2697.makeSmallestPalindrome(s));
    }

    /**
     * 双指针
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public String makeSmallestPalindrome(String s) {
        char[] arr = s.toCharArray();
        int i = 0;
        int j = arr.length - 1;

        while (i < j) {
            //arr[j]赋值为arr[i]，保证得到的回文字典序最小
            if (arr[i] < arr[j]) {
                arr[j] = arr[i];
            } else if (arr[i] > arr[j]) {
                //arr[i]赋值为arr[j]，保证得到的回文字典序最小
                arr[i] = arr[j];
            }

            i++;
            j--;
        }

        return new String(arr);
    }
}
