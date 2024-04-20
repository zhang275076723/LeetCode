package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/4/4 08:20
 * @Author zsy
 * @Description 长度为 3 的不同回文子序列 类比Problem1177、Problem1371、Problem1457、Problem1542、Problem1915、Problem2484、Problem2791 前缀和类比 状态压缩类比 回文类比
 * 给你一个字符串 s ，返回 s 中 长度为 3 的不同回文子序列 的个数。
 * 即便存在多种方法来构建相同的子序列，但相同的子序列只计数一次。
 * 回文 是正着读和反着读一样的字符串。
 * 子序列 是由原字符串删除其中部分字符（也可以不删除）且不改变剩余字符之间相对顺序形成的一个新字符串。
 * 例如，"ace" 是 "abcde" 的一个子序列。
 * <p>
 * 输入：s = "aabca"
 * 输出：3
 * 解释：长度为 3 的 3 个回文子序列分别是：
 * - "aba" ("aabca" 的子序列)
 * - "aaa" ("aabca" 的子序列)
 * - "aca" ("aabca" 的子序列)
 * <p>
 * 输入：s = "adc"
 * 输出：0
 * 解释："adc" 不存在长度为 3 的回文子序列。
 * <p>
 * 输入：s = "bbcbaba"
 * 输出：4
 * 解释：长度为 3 的 4 个回文子序列分别是：
 * - "bbb" ("bbcbaba" 的子序列)
 * - "bcb" ("bbcbaba" 的子序列)
 * - "bab" ("bbcbaba" 的子序列)
 * - "aba" ("bbcbaba" 的子序列)
 * <p>
 * 3 <= s.length <= 10^5
 * s 仅由小写英文字母组成
 */
public class Problem1930 {
    public static void main(String[] args) {
        Problem1930 problem1930 = new Problem1930();
        String s = "bbcbaba";
        System.out.println(problem1930.countPalindromicSubsequence(s));
        System.out.println(problem1930.countPalindromicSubsequence2(s));
        System.out.println(problem1930.countPalindromicSubsequence3(s));
    }

    /**
     * 模拟
     * 遍历所有的小写字母c，找字符串s中c第一次出现的下标索引i和最后一次出现的下标索引j，
     * 则s[i]、s[i+1]-s[j-1]中不同类型的字符、s[j]构成长度为3的回文子序列
     * 时间复杂度O(n*|Σ|)，空间复杂度O(|Σ|) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @return
     */
    public int countPalindromicSubsequence(String s) {
        int count = 0;

        //字符串s中字符只能是小写字母
        for (char c = 'a'; c <= 'z'; c++) {
            //字符串s中c第一次出现的下标索引
            int i = 0;
            //字符串s中c最后一次出现的下标索引
            int j = s.length() - 1;

            while (i < s.length() && s.charAt(i) != c) {
                i++;
            }

            while (j >= 0 && s.charAt(j) != c) {
                j--;
            }

            //s[i]和s[j]长度大于等于3，才能构成长度为3的回文子序列
            if (j - i >= 2) {
                //存储s[i+1]-s[j-1]中不同类型的字符
                Set<Character> set = new HashSet<>();

                for (int k = i + 1; k < j; k++) {
                    set.add(s.charAt(k));
                }

                //s[i]、s[i+1]-s[j-1]中不同类型的字符、s[j]构成长度为3的回文子序列
                count = count + set.size();
            }
        }

        return count;
    }

    /**
     * 前缀和
     * preSum[i][j]：s[0]-s[i-1]中字符'a'+j是否存在
     * sufSum[i][j]：s[i+1]-s[s.length()-1]中字符'a'+j是否存在
     * flag[i][j]：s中长度为3的回文子序列('a'+j)('a'+i)('a'+j)是否存在
     * 时间复杂度O(n*|Σ|+|Σ|^2)，空间复杂度O(n*|Σ|) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @return
     */
    public int countPalindromicSubsequence2(String s) {
        //s只包含26个小写字母
        boolean[][] preSum = new boolean[s.length() + 1][26];
        //后缀数组长度不需要加1
        boolean[][] sufSum = new boolean[s.length()][26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            for (int j = 0; j < 26; j++) {
                if (c == 'a' + j) {
                    preSum[i + 1][j] = true;
                } else {
                    preSum[i + 1][j] = preSum[i][j];
                }
            }
        }

        //注意：sufSum对应的是i>0，而不是i>=0
        for (int i = s.length() - 1; i > 0; i--) {
            char c = s.charAt(i);

            for (int j = 0; j < 26; j++) {
                if (c == 'a' + j) {
                    sufSum[i - 1][j] = true;
                } else {
                    sufSum[i - 1][j] = sufSum[i][j];
                }
            }
        }

        //flag[i][j]：s中长度为3的回文子序列('a'+j)('a'+i)('a'+j)是否存在
        boolean[][] flag = new boolean[26][26];

        //s中长度为3的回文子序列不需要从0开始，也不需要从n-1结束
        for (int i = 1; i < s.length() - 1; i++) {
            char c = s.charAt(i);

            for (int j = 0; j < 26; j++) {
                if (preSum[i][j] && sufSum[i][j]) {
                    flag[c - 'a'][j] = true;
                }
            }
        }

        int count = 0;

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (flag[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 前缀和+二进制状态压缩
     * preSum[i]：s[0]-s[i-1]中字符是否出现二进制表示的数，0：当前字符没有出现，1：当前字符出现
     * sufSum[i]：s[i+1]-s[s.length()-1]中字符是否出现二进制表示的数，0：当前字符没有出现，1：当前字符出现
     * result[i]：字符'a'+i作为长度为3的中间字符的不同回文字符串的个数
     * preSum[i] = preSum[i-1] | (1 << (s[i-1] - 'a'))
     * sufSum[i] = sufSum[i+1] | (1 << (s[i+1) - 'a'))
     * result[i] = result[i] | (preSum[j] & sufSum[j]) ('a'+i == s[j]) (不同位置的相同子序列只计数一次，所以要或运算)
     * 时间复杂度O(n+|Σ|)，空间复杂度O(|Σ|) (|Σ|=26，只包含小写字母)
     *
     * @param s
     * @return
     */
    public int countPalindromicSubsequence3(String s) {
        int[] preSum = new int[s.length() + 1];
        //后缀数组长度不需要加1
        int[] sufSum = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            preSum[i + 1] = preSum[i] | (1 << (c - 'a'));
        }

        //注意：sufSum对应的是i>0，而不是i>=0
        for (int i = s.length() - 1; i > 0; i--) {
            char c = s.charAt(i);
            sufSum[i - 1] = sufSum[i] | (1 << (c - 'a'));
        }

        //字符串s只包含小写字母，所以长度为26
        int[] result = new int[26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //不同位置的相同子序列只计数一次，所以要或运算
            result[c - 'a'] = result[c - 'a'] | (preSum[i] & sufSum[i]);
        }

        int count = 0;

        //统计字符'a'+i作为长度为3的中间字符的不同回文字符串的个数，即result[i]中二进制表示的数中1出现的次数
        for (int i = 0; i < 26; i++) {
            //result[i]中二进制表示的数中1出现的次数
            int curCount = 0;
            int x = result[i];

            //统计result[i]中二进制表示的数中1出现的次数
            while (x != 0) {
                curCount = curCount + (x & 1);
                x = x >>> 1;
            }

            count = count + curCount;
        }

        return count;
    }
}
