package com.zhang.java;

/**
 * @Date 2024/2/25 09:19
 * @Author zsy
 * @Description 删除回文子序列 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1177、Problem1147、Problem1312、Problem1328
 * 给你一个字符串 s，它仅由字母 'a' 和 'b' 组成。
 * 每一次删除操作都可以从 s 中删除一个回文 子序列。
 * 返回删除给定字符串中所有字符（字符串为空）的最小删除次数。
 * 「子序列」定义：如果一个字符串可以通过删除原字符串某些字符而不改变原字符顺序得到，那么这个字符串就是原字符串的一个子序列。
 * 「回文」定义：如果一个字符串向后和向前读是一致的，那么这个字符串就是一个回文。
 * <p>
 * 输入：s = "ababa"
 * 输出：1
 * 解释：字符串本身就是回文序列，只需要删除一次。
 * <p>
 * 输入：s = "abb"
 * 输出：2
 * 解释："abb" -> "bb" -> "".
 * 先删除回文子序列 "a"，然后再删除 "bb"。
 * <p>
 * 输入：s = "baabb"
 * 输出：2
 * 解释："baabb" -> "b" -> "".
 * 先删除回文子序列 "baab"，然后再删除 "b"。
 * <p>
 * 1 <= s.length <= 1000
 * s 仅包含字母 'a' 和 'b'
 */
public class Problem1332 {
    public static void main(String[] args) {
        Problem1332 problem1332 = new Problem1332();
        String s = "baabb";
        System.out.println(problem1332.removePalindromeSub(s));
    }

    /**
     * 模拟
     * s中只含有'a'和'b'，则第一次删除只包含'a'的子序列，第二次删除只包含'b'的子序列，即最多2次就能将s中所有字符删除
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int removePalindromeSub(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int left = 0;
        int right = s.length() - 1;
        //s是否是回文标志位
        boolean flag = true;

        while (left <= right) {
            if (s.charAt(left) != s.charAt(right)) {
                flag = false;
                break;
            } else {
                left++;
                right--;
            }
        }

        //s是回文，则需要删除1次；s不是回文，则需要删除2次
        return flag ? 1 : 2;
    }
}
