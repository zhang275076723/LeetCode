package com.zhang.java;

/**
 * @Date 2024/4/15 09:19
 * @Author zsy
 * @Description 字典序最小的美丽字符串 类比Problem1286 回文类比
 * 如果一个字符串满足以下条件，则称其为 美丽字符串 ：
 * 它由英语小写字母表的前 k 个字母组成。
 * 它不包含任何长度为 2 或更长的回文子字符串。
 * 给你一个长度为 n 的美丽字符串 s 和一个正整数 k 。
 * 请你找出并返回一个长度为 n 的美丽字符串，该字符串还满足：在字典序大于 s 的所有美丽字符串中字典序最小。
 * 如果不存在这样的字符串，则返回一个空字符串。
 * 对于长度相同的两个字符串 a 和 b ，如果字符串 a 在与字符串 b 不同的第一个位置上的字符字典序更大，
 * 则字符串 a 的字典序大于字符串 b 。
 * 例如，"abcd" 的字典序比 "abcc" 更大，因为在不同的第一个位置（第四个字符）上 d 的字典序大于 c 。
 * <p>
 * 输入：s = "abcz", k = 26
 * 输出："abda"
 * 解释：字符串 "abda" 既是美丽字符串，又满足字典序大于 "abcz" 。
 * 可以证明不存在字符串同时满足字典序大于 "abcz"、美丽字符串、字典序小于 "abda" 这三个条件。
 * <p>
 * 输入：s = "dc", k = 4
 * 输出：""
 * 解释：可以证明，不存在既是美丽字符串，又字典序大于 "dc" 的字符串。
 * <p>
 * 1 <= n == s.length <= 10^5
 * 4 <= k <= 26
 * s 是一个美丽字符串
 */
public class Problem2663 {
    public static void main(String[] args) {
        Problem2663 problem2663 = new Problem2663();
        String s = "abcz";
        int k = 26;
        System.out.println(problem2663.smallestBeautifulString(s, k));
    }

    /**
     * 模拟
     * s中任意s[i]!=s[i-1]&&s[i]!=s[i-2]，则s为美丽字符串
     * 从后往前遍历s，s[i]由小到大赋值，如果满足s[i]!=s[i-1]&&s[i]!=s[i-2]，则s[i]变大后，s[0]-s[i]为美丽字符串，
     * s[i+1]-s[s.length()-1]从'a'开始由小到大赋值，使其满足s[i]!=s[i-1]&&s[i]!=s[i-2]，得到字典序最小的美丽字符串
     * 时间复杂度O(nk)，空间复杂度O(n)
     *
     * @param s
     * @param k
     * @return
     */
    public String smallestBeautifulString(String s, int k) {
        char[] arr = s.toCharArray();
        //arr中从后往前，arr[i]由小到大赋值后，满足arr[i]!=arr[i-1]&&arr[i]!=arr[i-2]的下标索引，初始化为-1，表示不存在这样的i
        int i = -1;
        int j = s.length() - 1;

        while (j >= 0) {
            for (char c = (char) (arr[j] + 1); c < 'a' + k; c++) {
                if ((j - 1 < 0 || c != arr[j - 1]) && (j - 2 < 0 || c != arr[j - 2])) {
                    i = j;
                    //arr[i]赋值为c，arr[0]-arr[i]为美丽字符串
                    arr[i] = c;
                    break;
                }
            }

            if (i != -1) {
                break;
            }

            j--;
        }

        //s遍历结束，不存在字典序大于s的最小的美丽字符串，返回""
        if (i == -1) {
            return "";
        }

        //arr[i+1]-arr[arr.length-1]从'a'开始由小到大赋值，使其满足arr[i]!=arr[i-1]&&arr[i]!=arr[i-2]，得到字典序最小的美丽字符串
        for (i = i + 1; i < arr.length; i++) {
            for (char c = 'a'; c < 'a' + k; c++) {
                if ((i - 1 < 0 || c != arr[i - 1]) && (i - 2 < 0 || c != arr[i - 2])) {
                    //arr[i]赋值为c
                    arr[i] = c;
                    break;
                }
            }
        }

        return new String(arr);
    }
}
