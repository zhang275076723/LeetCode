package com.zhang.java;

/**
 * @Date 2025/4/6 08:17
 * @Author zsy
 * @Description 仅执行一次字符串交换能否使两个字符串相等 类比Problem859、Problem1247、Problem1657、Problem2531
 * 给你长度相等的两个字符串 s1 和 s2 。一次 字符串交换 操作的步骤如下：
 * 选出某个字符串中的两个下标（不必不同），并交换这两个下标所对应的字符。
 * 如果对 其中一个字符串 执行 最多一次字符串交换 就可以使两个字符串相等，返回 true ；否则，返回 false 。
 * <p>
 * 输入：s1 = "bank", s2 = "kanb"
 * 输出：true
 * 解释：例如，交换 s2 中的第一个和最后一个字符可以得到 "bank"
 * <p>
 * 输入：s1 = "attack", s2 = "defend"
 * 输出：false
 * 解释：一次字符串交换无法使两个字符串相等
 * <p>
 * 输入：s1 = "kelb", s2 = "kelb"
 * 输出：true
 * 解释：两个字符串已经相等，所以不需要进行字符串交换
 * <p>
 * 输入：s1 = "abcd", s2 = "dcba"
 * 输出：false
 * <p>
 * 1 <= s1.length, s2.length <= 100
 * s1.length == s2.length
 * s1 和 s2 仅由小写英文字母组成
 */
public class Problem1790 {
    public static void main(String[] args) {
        Problem1790 problem1790 = new Problem1790();
        String s1 = "bank";
        String s2 = "kanb";
        System.out.println(problem1790.areAlmostEqual(s1, s2));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean areAlmostEqual(String s1, String s2) {
        //s1[i]和s2[i]不相等的第一个下标索引
        int first = -1;
        //s1[i]和s2[i]不相等的第二个下标索引
        int second = -1;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (first == -1) {
                    first = i;
                } else if (second == -1) {
                    second = i;
                } else {
                    //s1和s2中不同的字符大于2个，则其中一个字符串最多执行1次交换不能使s1和s2相等，返回false
                    return false;
                }
            }
        }

        //s1和s2相等，或者不同的2个字符交换后s1和s2相等，则其中一个字符串最多执行1次交换使s1和s2相等，返回true
        if (first == -1 ||
                (second != -1 && s1.charAt(first) == s2.charAt(second) && s1.charAt(second) == s2.charAt(first))) {
            return true;
        }

        //遍历结束，返回false
        return false;
    }
}
