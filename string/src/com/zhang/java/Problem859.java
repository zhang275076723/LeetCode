package com.zhang.java;

/**
 * @Date 2025/4/3 08:45
 * @Author zsy
 * @Description 亲密字符串 类比Problem1247、Problem1657、Problem1790、Problem2531
 * 给你两个字符串 s 和 goal ，只要我们可以通过交换 s 中的两个字母得到与 goal 相等的结果，就返回 true ；否则返回 false 。
 * 交换字母的定义是：取两个下标 i 和 j （下标从 0 开始）且满足 i != j ，接着交换 s[i] 和 s[j] 处的字符。
 * 例如，在 "abcd" 中交换下标 0 和下标 2 的元素可以生成 "cbad" 。
 * <p>
 * 输入：s = "ab", goal = "ba"
 * 输出：true
 * 解释：你可以交换 s[0] = 'a' 和 s[1] = 'b' 生成 "ba"，此时 s 和 goal 相等。
 * <p>
 * 输入：s = "ab", goal = "ab"
 * 输出：false
 * 解释：你只能交换 s[0] = 'a' 和 s[1] = 'b' 生成 "ba"，此时 s 和 goal 不相等。
 * <p>
 * 输入：s = "aa", goal = "aa"
 * 输出：true
 * 解释：你可以交换 s[0] = 'a' 和 s[1] = 'a' 生成 "aa"，此时 s 和 goal 相等。
 * <p>
 * 1 <= s.length, goal.length <= 2 * 10^4
 * s 和 goal 由小写英文字母组成
 */
public class Problem859 {
    public static void main(String[] args) {
        Problem859 problem859 = new Problem859();
        String s = "ab";
        String goal = "ab";
        System.out.println(problem859.buddyStrings(s, goal));
    }

    /**
     * 模拟
     * s和goal是亲密字符串：s和goal相等，存在出现次数大于1的字符；
     * s和goal不相等，但长度相同，s和goal中有且只有2个字符不同，不同的2个字符交换后s和goal相等
     * 注意：只能交换一次，不能交换多次
     * 时间复杂度O(n)，空间复杂度O(|Σ|)=O(1) (n=s.length()=goal.length()) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @param goal
     * @return
     */
    public boolean buddyStrings(String s, String goal) {
        //s和goal长度不相等，则不是亲密字符串，返回false
        if (s.length() != goal.length()) {
            return false;
        }

        //s和goal相等
        if (s.equals(goal)) {
            //s中字符出现的次数数组
            int[] count = new int[26];

            for (char c : s.toCharArray()) {
                count[c - 'a']++;

                //存在出现次数大于1的字符，则是亲密字符串，返回true
                if (count[c - 'a'] > 1) {
                    return true;
                }
            }

            //遍历结束，则不是亲密字符串，返回false
            return false;
        } else {
            //s和goal不相等

            //s[i]和goal[i]不相等的第一个下标索引
            int first = -1;
            //s[i]和goal[i]不相等的第二个下标索引
            int second = -1;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != goal.charAt(i)) {
                    if (first == -1) {
                        first = i;
                    } else if (second == -1) {
                        second = i;
                    } else {
                        //s和goal中不同的字符大于2个，则不是亲密字符串，返回false
                        return false;
                    }
                }
            }

            //s和goal中不同的字符不为2个，或者不同的2个字符交换后s和goal不相等，则不是亲密字符串，返回false
            if (first == -1 || second == -1 || s.charAt(first) != goal.charAt(second) ||
                    s.charAt(second) != goal.charAt(first)) {
                return false;
            }

            //遍历结束，则是亲密字符串，返回true
            return true;
        }
    }
}
