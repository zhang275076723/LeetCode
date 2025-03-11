package com.zhang.java;

/**
 * @Date 2023/5/4 09:18
 * @Author zsy
 * @Description 旋转字符串 kmp类比Problem28、Problem214、Problem459、Problem471、Problem686、Problem1392、Problem1408、Problem3029 旋转问题类比Problem61、Problem186、Problem189、Offer58_2
 * 给定两个字符串, s 和 goal。
 * 如果在若干次旋转操作之后，s 能变成 goal ，那么返回 true 。
 * s 的 旋转操作 就是将 s 最左边的字符移动到最右边。
 * 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 。
 * <p>
 * 输入: s = "abcde", goal = "cdeab"
 * 输出: true
 * <p>
 * 输入: s = "abcde", goal = "abced"
 * 输出: false
 * <p>
 * 1 <= s.length, goal.length <= 100
 * s 和 goal 由小写英文字母组成
 */
public class Problem796 {
    public static void main(String[] args) {
        Problem796 problem796 = new Problem796();
        String s = "abcde";
        String goal = "cdeab";
        System.out.println(problem796.rotateString2(s, goal));
        System.out.println(problem796.rotateString(s, goal));
    }

    /**
     * s拼接s，得到的新字符串str，str中包含了s旋转的各种情况，
     * 如果str中包含goal，则goal可以由s旋转得到
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param goal
     * @return
     */
    public boolean rotateString(String s, String goal) {
        //s和goal的长度不同，则goal不能由s旋转得到，返回false
        if (s.length() != goal.length()) {
            return false;
        }

        return (s + s).contains(goal);
    }

    /**
     * kmp
     * s拼接s，得到的新字符串str，str中包含了s旋转的各种情况，
     * 如果str中包含goal，则goal可以由s旋转得到
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param goal
     * @return
     */
    public boolean rotateString2(String s, String goal) {
        //s和goal的长度不同，则goal不能由s旋转得到，返回false
        if (s.length() != goal.length()) {
            return false;
        }

        //s拼接s，得到的新字符串str，str中包含了s旋转的各种情况
        String str = s + s;
        //next数组
        int[] next = getNext(goal);
        //模式串指针
        int j = 0;

        for (int i = 0; i < str.length(); i++) {
            //当前字符不匹配，j指针通过next数组前移
            while (j > 0 && str.charAt(i) != goal.charAt(j)) {
                j = next[j - 1];
            }

            //当前字符匹配，j指针后移
            if (str.charAt(i) == goal.charAt(j)) {
                j++;
            }

            //str中包含goal，则goal可以由s旋转得到，返回true
            if (j == goal.length()) {
                return true;
            }
        }

        //str中不包含goal，则goal不能由s旋转得到，返回false
        return false;
    }

    /**
     * 获取字符串s的next数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    private int[] getNext(String s) {
        int[] next = new int[s.length()];
        int j = 0;

        //注意：i从1开始遍历，因为s[0]-s[0]不存在公共前缀和后缀
        for (int i = 1; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            if (s.charAt(i) == s.charAt(j)) {
                j++;
            }

            next[i] = j;
        }

        return next;
    }
}
