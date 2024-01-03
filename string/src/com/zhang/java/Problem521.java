package com.zhang.java;

/**
 * @Date 2024/1/2 08:42
 * @Author zsy
 * @Description 最长特殊序列 I 类比Problem522
 * 给你两个字符串 a 和 b，请返回 这两个字符串中 最长的特殊序列  的长度。如果不存在，则返回 -1 。
 * 「最长特殊序列」 定义如下：该序列为 某字符串独有的最长子序列（即不能是其他字符串的子序列） 。
 * 字符串 s 的子序列是在从 s 中删除任意数量的字符后可以获得的字符串。
 * 例如，"abc" 是 "aebdc" 的子序列，因为删除 "aebdc" 中斜体加粗的字符可以得到 "abc" 。
 * "aebdc" 的子序列还包括 "aebdc" 、 "aeb" 和 "" (空字符串)。
 * <p>
 * 输入: a = "aba", b = "cdc"
 * 输出: 3
 * 解释: 最长特殊序列可为 "aba" (或 "cdc")，两者均为自身的子序列且不是对方的子序列。
 * <p>
 * 输入：a = "aaa", b = "bbb"
 * 输出：3
 * 解释: 最长特殊序列是 "aaa" 和 "bbb" 。
 * <p>
 * 输入：a = "aaa", b = "aaa"
 * 输出：-1
 * 解释: 字符串 a 的每个子序列也是字符串 b 的每个子序列。同样，字符串 b 的每个子序列也是字符串 a 的子序列。
 * <p>
 * 1 <= a.length, b.length <= 100
 * a 和 b 由小写英文字母组成
 */
public class Problem521 {
    public static void main(String[] args) {
        Problem521 problem521 = new Problem521();
        String a = "aba";
        String b = "cdc";
        System.out.println(problem521.findLUSlength(a, b));
    }

    /**
     * 模拟
     * a等于b，返回-1，否则a和b中较长的字符串长度
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    public int findLUSlength(String a, String b) {
        return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }
}
