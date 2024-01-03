package com.zhang.java;

/**
 * @Date 2024/1/4 08:41
 * @Author zsy
 * @Description 相隔为 1 的编辑距离 类比Problem72
 * 给定两个字符串 s 和 t ，如果它们的编辑距离为 1 ，则返回 true ，否则返回 false 。
 * 字符串 s 和字符串 t 之间满足编辑距离等于 1 有三种可能的情形：
 * 往 s 中插入 恰好一个 字符得到 t
 * 从 s 中删除 恰好一个 字符得到 t
 * 在 s 中用 一个不同的字符 替换 恰好一个 字符得到 t
 * <p>
 * 输入: s = "ab", t = "acb"
 * 输出: true
 * 解释: 可以将 'c' 插入字符串 s 来得到 t。
 * <p>
 * 输入: s = "cab", t = "ad"
 * 输出: false
 * 解释: 无法通过 1 步操作使 s 变为 t。
 * <p>
 * 0 <= s.length, t.length <= 10^4
 * s 和 t 由小写字母，大写字母和数字组成
 */
public class Problem161 {
    public static void main(String[] args) {
        Problem161 problem161 = new Problem161();
        String s = "ab";
        String t = "acb";
        System.out.println(problem161.isOneEditDistance(s, t));
    }

    /**
     * 模拟
     * 1、s和t长度之差超过1，则s和t的编辑距离大于1，返回false
     * 2、s和t长度相等，s[i]和t[i]不相等，则判断s[i+1]-s[s.length()-1]和t[i+1]-t[t.length()-1]是否相等
     * 3、s长度等于t长度加1，s[i]和t[i]不相等，则判断s[i+1]-s[s.length()-1]和t[i]-t[t.length()-1]是否相等
     * 时间复杂度O(n)，空间复杂度O(n) (m=s.length()，n=t.length())
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length();
        int n = t.length();

        //s和t长度之差超过1，则s和t的编辑距离大于1，返回false
        if (Math.abs(m - n) > 1) {
            return false;
        }

        //始终保证s的长度大于t的长度
        if (m < n) {
            String temp = s;
            s = t;
            t = temp;
        }

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                //s和t长度相等，s[i]和t[i]不相等，则判断s[i+1]-s[s.length()-1]和t[i+1]-t[t.length()-1]是否相等
                if (m == n) {
                    return isEquals(i + 1, i + 1, s, t);
                } else {
                    //s长度等于t长度加1，s[i]和t[i]不相等，则判断s[i+1]-s[s.length()-1]和t[i]-t[t.length()-1]是否相等
                    return isEquals(i + 1, i, s, t);
                }
            }
        }

        //遍历结束，则说明s和t前n个元素都相等，如果s和t长度相等，则s和t的编辑距离为0，返回false；
        //如果s长度等于t长度加1，则s和t的编辑距离为1，返回true
        return m == n + 1;
    }

    /**
     * 判断从str1[i]开始的字符串和从str2[j]开始的字符串是否相等
     *
     * @param i
     * @param j
     * @param str1
     * @param str2
     * @return
     */
    private boolean isEquals(int i, int j, String str1, String str2) {
        while (i < str1.length() && j < str2.length()) {
            if (str1.charAt(i) != str2.charAt(j)) {
                return false;
            }

            i++;
            j++;
        }

        return i == str1.length() && j == str2.length();
    }
}
