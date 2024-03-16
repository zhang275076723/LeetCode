package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/10/6 08:24
 * @Author zsy
 * @Description 回文排列 II 类比Problem266、Problem409 中心扩散类比Problem5、Problem647、Problem696 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1312、Problem1332 回溯+剪枝
 * 给定一个字符串 s ，返回 其重新排列组合后可能构成的所有回文字符串，并去除重复的组合 。
 * 你可以按 任意顺序 返回答案。如果 s 不能形成任何回文排列时，则返回一个空列表。
 * <p>
 * 输入: s = "aabb"
 * 输出: ["abba", "baab"]
 * <p>
 * 输入: s = "abc"
 * 输出: []
 * <p>
 * 1 <= s.length <= 16
 * s 仅由小写英文字母组成
 */
public class Problem267 {
    public static void main(String[] args) {
        Problem267 problem267 = new Problem267();
        String s = "aabb";
        System.out.println(problem267.generatePalindromes(s));
    }

    /**
     * 回溯+剪枝(中心扩散)
     * 统计字符串s中每个字符出现的次数，
     * 出现次数为偶数的字符都能构成回文串，出现次数为奇数的字符最多只能有1个，超过1个，则不能构成回文串
     * 以唯一一个出现次数为奇数的字符为中心，向两边扩散；如果不存在出现次数为奇数的字符，以""为中心，向两边扩散
     * 注意：只能使用数组不能用map，map回溯过程中无法删除字符，会报ConcurrentModificationException
     * 时间复杂度O(n*(n/2)!)，空间复杂度O(n)
     * (最多出现次数为偶数的字符有n/2种，最多构成(n/2)!种回文串，每种回文串需要O(n)添加到结果集合中)
     *
     * @param s
     * @return
     */
    public List<String> generatePalindromes(String s) {
        //统计数组，只能使用数组不能用map，map回溯过程中无法删除字符，会报ConcurrentModificationException
        int[] arr = new int[26];

        for (char c : s.toCharArray()) {
            arr[c - 'a']++;
        }

        //map中字符出现次数为奇数的标志位，出现次数为奇数的字符最多只能有1个，超过1个，则不能构成回文串
        boolean oddFlag = false;
        //唯一一个出现出现次数为奇数的字符
        char c = 'a';

        for (int i = 0; i < 26; i++) {
            //出现次数为奇数的字符最多只能有1个，超过1个，则不能构成回文串
            if (arr[i] % 2 == 1) {
                //之前已经出现过出现次数为奇数的字符，则不能构成回文串，返回空集合
                if (oddFlag) {
                    return new ArrayList<>();
                } else {
                    oddFlag = true;
                    c = (char) ('a' + i);
                }
            }
        }

        List<String> list = new ArrayList<>();

        //以唯一一个出现次数为奇数的字符为中心，向两边扩散
        if (oddFlag) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < arr[c - 'a']; i++) {
                sb.append(c);
            }

            arr[c - 'a'] = 0;

            backtrack(sb, s, arr, list);

            return list;
        } else {
            //不存在出现次数为奇数的字符，则以""空字符串为中心，向两边扩散
            backtrack(new StringBuilder(), s, arr, list);

            return list;
        }
    }

    private void backtrack(StringBuilder sb, String s, int[] arr, List<String> list) {
        if (sb.length() == s.length()) {
            list.add(sb.toString());
            return;
        }

        for (int i = 0; i < 26; i++) {
            if (arr[i] == 0) {
                continue;
            }

            StringBuilder tempSb = new StringBuilder();
            int count = arr[i];

            //拼接前半部分
            for (int j = 0; j < count / 2; j++) {
                tempSb.append((char) ('a' + i));
            }

            tempSb.append(sb);

            //拼接后半部分
            for (int j = 0; j < count / 2; j++) {
                tempSb.append((char) ('a' + i));
            }

            arr[i] = 0;
            backtrack(tempSb, s, arr, list);
            arr[i] = count;
        }
    }
}
