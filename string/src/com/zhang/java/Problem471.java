package com.zhang.java;

/**
 * @Date 2025/2/8 08:57
 * @Author zsy
 * @Description 编码最短长度的字符串 类比Problem394 类比Problem459 kmp类比Problem28、Problem214、Problem459、Problem686、Problem796、Problem1392、Problem1408、Problem3029、Problem3031
 * 给定一个 非空 字符串，将其编码为具有最短长度的字符串。
 * 编码规则是：k[encoded_string]，其中在方括号 encoded_string 中的内容重复 k 次。
 * 注：
 * k 为正整数
 * 如果编码的过程不能使字符串缩短，则不要对其进行编码。如果有多种编码方式，返回 任意一种 即可
 * <p>
 * 输入：s = "aaa"
 * 输出："aaa"
 * 解释：无法将其编码为更短的字符串，因此不进行编码。
 * <p>
 * 输入：s = "aaaaa"
 * 输出："5[a]"
 * 解释："5[a]" 比 "aaaaa" 短 1 个字符。
 * <p>
 * 输入：s = "aaaaaaaaaa"
 * 输出："10[a]"
 * 解释："a9[a]" 或 "9[a]a" 都是合法的编码，和 "10[a]" 一样长度都为 5。
 * <p>
 * 输入：s = "aabcaabcd"
 * 输出："2[aabc]d"
 * 解释："aabc" 出现两次，因此一种答案可以是 "2[aabc]d"。
 * <p>
 * 输入：s = "abbbabbbcabbbabbbc"
 * 输出："2[2[abbb]c]"
 * 解释："abbbabbbc" 出现两次，但是 "abbbabbbc" 可以编码为 "2[abbb]c"，因此一种答案可以是 "2[2[abbb]c]"。
 * <p>
 * 1 <= s.length <= 150
 * s 由小写英文字母组成
 */
public class Problem471 {
    public static void main(String[] args) {
        Problem471 problem471 = new Problem471();
        String s = "abbbabbbcabbbabbbc";
        System.out.println(problem471.encode(s));
    }

    /**
     * 动态规划+kmp
     * dp[i][j]：s[i]-s[j]编码得到的最短长度的字符串
     * dp[i][j] = min(dp[i][k]+dp[k+1][j]) (i <= k < j)
     * kmp获取s中重复多次构成s的子串
     * 时间复杂度O(n^3)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public String encode(String s) {
        String[][] dp = new String[s.length()][s.length()];

        //dp[i][i]初始化
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = s.charAt(i) + "";
        }

        //当前字符串长度i
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串的起始下标索引j
            for (int j = 0; j <= s.length() - i; j++) {
                //当前字符串s[j]-s[j+i-1]
                String curStr = s.substring(j, j + i);
                //dp[j][j+i-1]初始化为不能编码
                dp[j][j + i - 1] = curStr;
                //s[j]-s[j+i-1]中构成s[j]-s[j+i-1]长度最小的子串
                String repeatStr = getRepeatStr(curStr);

                //s[j]-s[j+i-1]整体进行编码来更新dp[j][j+i-1]
                if (repeatStr.length() < curStr.length()) {
                    StringBuilder sb = new StringBuilder();
                    //注意：sb中拼接的是repeatStr编码得到的长度最小的字符串dp[j][j+repeatStr.length()-1]，
                    //而不是拼接repeatStr，因为repeatStr有可能也存在最短长度的编码
                    sb.append(curStr.length() / repeatStr.length()).
                            append('[').append(dp[j][j + repeatStr.length() - 1]).append(']');

                    //更新dp[j][j+i-1]
                    if (sb.length() < dp[j][j + i - 1].length()) {
                        dp[j][j + i - 1] = sb.toString();
                    }
                }

                //s[j]-s[j+i-1]拆分为s[j]-s[k]和s[k+1]-s[j+i-1]两个字符串编码拼接来更新dp[j][j+i-1]
                for (int k = j; k <= j + i - 2; k++) {
                    if (dp[j][k].length() + dp[k + 1][j + i - 1].length() < dp[j][j + i - 1].length()) {
                        dp[j][j + i - 1] = dp[j][k] + dp[k + 1][j + i - 1];
                    }
                }
            }
        }

        return dp[0][s.length() - 1];
    }

    /**
     * kmp得到构成s长度最小的子串，如果不存在，则返回s
     * kmp判断s能否由其子串重复多次构成，即判断s拼接s，移除首尾字符得到的新字符串str，str中是否存在s，
     * 如果str中存在s，则s由长度最小重复的子字符串x构成，假设str中s[0]第一次出现的下标索引为index，
     * 则s[0]拼接str[0]-str[index-1]为构成s长度最小的子串，即s[0]-s[index]为构成s长度最小的子串
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    private String getRepeatStr(String s) {
        //s拼接s，再去除首尾字符，得到的新字符串str，如果str中存在s，则s由重复的子字符串构成
        String str = (s + s).substring(1, (s + s).length() - 1);
        //next数组
        int[] next = getNext(s);
        //模式串指针
        int j = 0;

        for (int i = 0; i < str.length(); i++) {
            while (j > 0 && str.charAt(i) != s.charAt(j)) {
                j = next[j - 1];
            }

            if (str.charAt(i) == s.charAt(j)) {
                j++;
            }

            //j遍历到末尾，则找到构成s长度最小的子串
            if (j == s.length()) {
                //str中s[0]第一次出现的下标索引
                int index = i - j + 1;
                //s[0]拼接str[0]-str[index-1]为构成s长度最小的子串，即s[0]-s[index]为构成s长度最小的子串
                return s.substring(0, index + 1);
            }
        }

        //遍历结束，则s中不存在构成s长度最小的子串，返回s
        return s;
    }

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
