package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/6/23 8:39
 * @Author zsy
 * @Description 分割回文串 动态规划预处理类比Problem140 回文类比Problem5、Problem9、Problem125、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1312、Problem1332
 * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是 回文串 。
 * 返回 s 所有可能的分割方案。
 * 回文串 是正着读和反着读都一样的字符串。
 * <p>
 * 输入：s = "aab"
 * 输出：[["a","a","b"],["aa","b"]]
 * <p>
 * 输入：s = "a"
 * 输出：[["a"]]
 * <p>
 * 1 <= s.length <= 16
 * s 仅由小写英文字母组成
 */
public class Problem131 {
    public static void main(String[] args) {
        Problem131 problem131 = new Problem131();
        String s = "aab";
        System.out.println(problem131.partition(s));
    }

    /**
     * 回溯+剪枝+动态规划预处理字符串s
     * dp[i][j]：s[i]-s[j]是否是回文串
     * dp[i][j] = false (s[i] != s[j])
     * dp[i][j] = true  (s[i] == s[j] && dp[i+1][j-1] == true)
     * 时间复杂度O(n*2^n)，空间复杂度O(n^2) (最坏有2^(n-1)种可能，每种可能需要O(n)放入结果集合，栈深度O(n)，dp数组O(n^2))
     *
     * @param s
     * @return
     */
    public List<List<String>> partition(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        boolean[][] dp = new boolean[s.length()][s.length()];

        for (int i = 0; i < s.length(); i++) {
            //只有一个字符也是回文串
            dp[i][i] = true;
        }

        for (int i = 1; i < s.length(); i++) {
            //用于s[i]-s[i+1]，即两个字符的情况
            dp[i][i - 1] = true;
        }

        //当前字符串长度
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串起始字符索引
            for (int j = 0; j <= s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i - 1) && dp[j + 1][j + i - 2]) {
                    dp[j][j + i - 1] = true;
                }
            }
        }

        List<List<String>> result = new ArrayList<>();

        backtrack(0, s, dp, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int t, String s, boolean[][] dp, List<String> list, List<List<String>> result) {
        if (t == s.length()) {
            //不能写为result.add(list)，因为传入的是引用，当list修改之后，result中的结果也会修改
            result.add(new ArrayList<>(list));
            return;
        }

        for (int i = t; i < s.length(); i++) {
            //当前s[t]-s[i]是回文串才继续遍历
            if (dp[t][i]) {
                list.add(s.substring(t, i + 1));
                backtrack(i + 1, s, dp, list, result);
                list.remove(list.size() - 1);
            }
        }
    }
}
