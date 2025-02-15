package com.zhang.java;

/**
 * @Date 2025/4/5 08:25
 * @Author zsy
 * @Description 交换字符使得字符串相同 类比Problem859、Problem1657、Problem1790、Problem2531
 * 有两个长度相同的字符串 s1 和 s2，且它们其中 只含有 字符 "x" 和 "y"，你需要通过「交换字符」的方式使这两个字符串相同。
 * 每次「交换字符」的时候，你都可以在两个字符串中各选一个字符进行交换。
 * 交换只能发生在两个不同的字符串之间，绝对不能发生在同一个字符串内部。
 * 也就是说，我们可以交换 s1[i] 和 s2[j]，但不能交换 s1[i] 和 s1[j]。
 * 最后，请你返回使 s1 和 s2 相同的最小交换次数，如果没有方法能够使得这两个字符串相同，则返回 -1 。
 * <p>
 * 输入：s1 = "xx", s2 = "yy"
 * 输出：1
 * 解释：
 * 交换 s1[0] 和 s2[1]，得到 s1 = "yx"，s2 = "yx"。
 * <p>
 * 输入：s1 = "xy", s2 = "yx"
 * 输出：2
 * 解释：
 * 交换 s1[0] 和 s2[0]，得到 s1 = "yy"，s2 = "xx" 。
 * 交换 s1[0] 和 s2[1]，得到 s1 = "xy"，s2 = "xy" 。
 * 注意，你不能交换 s1[0] 和 s1[1] 使得 s1 变成 "yx"，因为我们只能交换属于两个不同字符串的字符。
 * <p>
 * 输入：s1 = "xx", s2 = "xy"
 * 输出：-1
 * <p>
 * 1 <= s1.length, s2.length <= 1000
 * s1.length == s2.length
 * s1, s2 只包含 'x' 或 'y'。
 */
public class Problem1247 {
    public static void main(String[] args) {
        Problem1247 problem1247 = new Problem1247();
        String s1 = "xxxy";
        String s2 = "yyyx";
        System.out.println(problem1247.minimumSwap(s1, s2));
    }

    /**
     * 模拟
     * 统计s1[i]和s2[i]不相等出现的次数，xy表示s1[i]为'x'，s2[i]为'y'出现的次数，yx表示s1[i]为'y'，s2[i]为'x'出现的次数，
     * 优先交换2个xy或2个yx，即1次交换可以减少2个xy或2个yx，其次交换1个xy和1个yx，即2次交换可以减少1个xy和1个yx
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s1
     * @param s2
     * @return
     */
    public int minimumSwap(String s1, String s2) {
        //s1[i]为'x'，s2[i]为'y'出现的次数
        int xy = 0;
        //s1[i]为'y'，s2[i]为'x'出现的次数
        int yx = 0;

        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);

            if (c1 == 'x' && c2 == 'y') {
                xy++;
            } else if (c1 == 'y' && c2 == 'x') {
                yx++;
            }
        }

        //不管是交换2个xy或2个yx，还是交换1个xy和1个yx，则总会剩余1个单独的不相等的s1[i]和s2[i]，则返回-1
        if ((xy + yx) % 2 == 1) {
            return -1;
        }

        //优先交换2个xy或2个yx，即1次交换可以减少2个xy或2个yx，其次交换1个xy和1个yx，即2次交换可以减少1个xy和1个yx
        return xy / 2 + yx / 2 + 2 * Math.max(xy % 2, yx % 2);
    }
}
