package com.zhang.java;

/**
 * @Date 2023/9/12 08:23
 * @Author zsy
 * @Description 计数二进制子串 类比Problem525 中心扩散类比Problem5、Problem267、Problem647、Problem1960 双指针类比
 * 给定一个字符串 s，统计并返回具有相同数量 0 和 1 的非空（连续）子字符串的数量，
 * 并且这些子字符串中的所有 0 和所有 1 都是成组连续的。
 * 重复出现（不同位置）的子串也要统计它们出现的次数。
 * <p>
 * 输入：s = "00110011"
 * 输出：6
 * 解释：6 个子串满足具有相同数量的连续 1 和 0 ："0011"、"01"、"1100"、"10"、"0011" 和 "01" 。
 * 注意，一些重复出现的子串（不同位置）要统计它们出现的次数。
 * 另外，"00110011" 不是有效的子串，因为所有的 0（还有 1 ）没有组合在一起。
 * <p>
 * 输入：s = "10101"
 * 输出：4
 * 解释：有 4 个子串："10"、"01"、"10"、"01" ，具有相同数量的连续 1 和 0 。
 * <p>
 * 1 <= s.length <= 10^5
 * s[i] 为 '0' 或 '1'
 */
public class Problem696 {
    public static void main(String[] args) {
        Problem696 problem696 = new Problem696();
        String s = "00110011";
        System.out.println(problem696.countBinarySubstrings(s));
        System.out.println(problem696.countBinarySubstrings2(s));
    }

    /**
     * 双指针，中心扩散
     * 找到两个相邻的不同元素，这两个元素作为中心向两边扩散
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < s.length() - 1; i++) {
            //找到两个相邻的不同元素，这两个元素作为中心向两边扩散
            if (s.charAt(i) != s.charAt(i + 1)) {
                count = count + centerExpand(s, i, i + 1);
            }
        }

        return count;
    }

    /**
     * 模拟
     * 统计相邻连续1和相邻连续0的个数，由相邻连续1和0组成相同个数字符串的个数为相邻连续1和0个数中的较小值
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int countBinarySubstrings2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int count = 0;
        int index = 0;
        //index之前，和s[index-1]是相同元素的连续元素个数
        int preCount = 0;

        while (index < s.length()) {
            //和s[index]是相同元素的连续元素个数，即s[index-1]和s[index]分别为0和1，或者分别为1和0
            int curCount = 0;
            int c = s.charAt(index);

            while (index < s.length() && s.charAt(index) == c) {
                curCount++;
                index++;
            }

            //由相邻连续1和0组成相同个数字符串的个数为相邻连续1和0个数中的较小值
            count = count + Math.min(preCount, curCount);
            //更新preCount
            preCount = curCount;
        }

        return count;
    }

    /**
     * 中心扩散
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private int centerExpand(String s, int left, int right) {
        int count = 0;
        char c1 = s.charAt(left);
        char c2 = s.charAt(right);

        while (left >= 0 && right < s.length() && s.charAt(left) == c1 && s.charAt(right) == c2) {
            left--;
            right++;
            count++;
        }

        return count;
    }
}
