package com.zhang.java;


/**
 * @Date 2022/4/13 9:32
 * @Author zsy
 * @Description 最长回文子串 腾讯面试题 类比Problem516 马拉车类比Problem647、Problem1960、Problem2472 中心扩散类比Problem267、Problem647、Problem696、Problem1960 回文类比Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem564、Problem647、Problem680、Problem866、Problem1147、Problem1177、Problem1312、Problem1328、Problem1332、Problem1400、Problem1457、Problem1542、Problem1616、Problem1930、Problem2002、Problem2108、Problem2131、Problem2217、Problem2384、Problem2396、Problem2484、Problem2490、Problem2663、Problem2697、Problem2791、Problem3035
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * <p>
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * <p>
 * 输入：s = "cbbd"
 * 输出："bb"
 * <p>
 * 1 <= s.length <= 1000
 * s 仅由数字和英文字母组成
 */
public class Problem5 {
    public static void main(String[] args) {
        Problem5 problem5 = new Problem5();
        String s = "babad";
        System.out.println(problem5.longestPalindrome(s));
        System.out.println(problem5.longestPalindrome2(s));
        System.out.println(problem5.longestPalindrome3(s));
        System.out.println(problem5.longestPalindrome4(s));
    }

    /**
     * 暴力
     * 时间复杂度O(n^3)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        if (s.length() == 1) {
            return s;
        }

        int start = 0;
        int maxLen = 1;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                //判断s[left]-s[right]是否是回文串
                int left = i;
                int right = j;
                boolean isPalindrome = true;

                while (left <= right) {
                    if (s.charAt(left) != s.charAt(right)) {
                        isPalindrome = false;
                        break;
                    }

                    left++;
                    right--;
                }

                if (isPalindrome && j - i + 1 > maxLen) {
                    start = i;
                    maxLen = j - i + 1;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    /**
     * 动态规划
     * dp[i][j]：s[i]-s[j]是否是回文串
     * dp[i][j] = dp[i+1][j-1] && (s[i] == s[j])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        if (s.length() == 1) {
            return s;
        }

        boolean[][] dp = new boolean[s.length()][s.length()];

        //只有一个字符是回文串的特殊情况
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }

        //s[i]-s[i+1]是回文串的特殊情况
        for (int i = 1; i < s.length(); i++) {
            dp[i][i - 1] = true;
        }

        //最长回文的起始下标索引
        int left = 0;
        //最长回文的末尾下标索引
        int right = 0;

        //当前字符串长度
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串起始字符索引
            for (int j = 0; j <= s.length() - i; j++) {
                if (s.charAt(j) == s.charAt(j + i - 1) && dp[j + 1][j + i - 2]) {
                    dp[j][j + i - 1] = true;

                    //更新最长回文串
                    if (i > right - left + 1) {
                        left = j;
                        right = j + i - 1;
                    }
                }
            }
        }

        return s.substring(left, right + 1);
    }

    /**
     * 双指针，中心扩散
     * 以一个字符作为中心向两边扩散，找最长回文串
     * 以两个字符作为中心向两边扩散，找最长回文串
     * 时间复杂度O(n^2)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        if (s.length() == 1) {
            return s;
        }

        //最长回文的起始下标索引
        int left = 0;
        //最长回文的末尾下标索引
        int right = 0;

        for (int i = 0; i < s.length(); i++) {
            //一个字符作为中心向两边扩散
            int[] arr1 = centerExpand(s, i, i);

            //两个字符作为中心向两边扩散
            int[] arr2 = null;

            if (i + 1 < s.length() && s.charAt(i) == s.charAt(i + 1)) {
                arr2 = centerExpand(s, i, i + 1);
            }

            //只考虑一个字符的中心扩散情况
            if (arr2 == null) {
                if (arr1[1] - arr1[0] > right - left) {
                    right = arr1[1];
                    left = arr1[0];
                }
            } else {
                //考虑一个字符、两个字符的中心扩散情况
                if (arr1[1] - arr1[0] > arr2[1] - arr2[0]) {
                    if (arr1[1] - arr1[0] > right - left) {
                        right = arr1[1];
                        left = arr1[0];
                    }
                } else {
                    if (arr2[1] - arr2[0] > right - left) {
                        right = arr2[1];
                        left = arr2[0];
                    }
                }
            }
        }

        return s.substring(left, right + 1);
    }

    /**
     * 马拉车(Manacher)
     * 臂长：s[i]作为回文中心向外扩展的长度，如果臂长为n，则s[i]作为回文中心向外扩展得到的回文长度为2n+1
     * i为当前向外扩展的下标索引，j为已经遍历过的下标索引作为中心得到最大最右边的回文中心下标索引，length为j的臂长，
     * 2j-i为i关于j的对称点，length2为2j-i的臂长，
     * 如果j+length>=i，则从min(length2,length-(i-j))开始找i的臂长；否则，从0开始找i的臂长
     * 注意：s中首尾和每两个字符之间插入'#'，这样只需要考虑一个字符作为中心向外扩展的情况
     * 时间复杂度O(n)，空间复杂度O(n)
     * <p>
     * 例如：s="babad"，
     * 插入'#'得到的字符串                                        #  b  #  a  #  b  #  a  #  d  #
     * 臂长从几开始遍历                                           0  0  0  0  0  1  0  1  0  0  0
     * 臂长                                                      0  1  0  3  0  3  0  1  0  1  0
     * 已经遍历过的下标索引作为中心得到最大最右边的回文中心下标索引j -1  1  1  3  3  5  5  5  5  9  9
     * j的臂长length                                             0  1  1  3  3  3  3  3  3  1  1
     * 下标索引i                                                 0  1  2  3  4  5  6  7  8  9  10
     *
     * @param s
     * @return
     */
    public String longestPalindrome4(String s) {
        StringBuilder sb = new StringBuilder();
        //首添加'#'
        sb.append('#');

        for (char c : s.toCharArray()) {
            sb.append(c).append('#');
        }

        s = sb.toString();

        //添加'#'后字符串s每个位置的臂长数组
        int[] armArr = new int[s.length()];
        //已经遍历过的下标索引作为中心得到最大最右边的回文中心下标索引
        int j = -1;
        //j的臂长
        int length = 0;

        //最长回文的起始下标索引
        int left = 0;
        //最长回文的末尾下标索引
        int right = 0;

        for (int i = 0; i < s.length(); i++) {
            //s[i]作为回文中心的臂长
            int curArm;

            //j+length>=i，则从min(length2,length-(i-j))开始找i的臂长
            if (j + length >= i) {
                //i关于j的对称点
                int symmetry = 2 * j - i;
                //symmetry的臂长
                int length2 = armArr[symmetry];
                //i的臂长至少为min(length2,length-(i-j))
                int startArm = Math.min(length2, length - (i - j));
                //从startArm开始找i的臂长
                curArm = getArmByCenterExpand(s, i - startArm, i + startArm);
            } else {
                //从0开始找i的臂长
                curArm = getArmByCenterExpand(s, i, i);
            }

            armArr[i] = curArm;

            //更新j和length
            if (i + curArm > j + length) {
                j = i;
                length = curArm;
            }

            //更新最长回文串
            if (2 * curArm + 1 > right - left + 1) {
                left = i - curArm;
                right = i + curArm;
            }
        }

        StringBuilder sb2 = new StringBuilder();

        //去除之前添加的'#'
        for (int i = left; i <= right; i++) {
            char c = s.charAt(i);

            if (c != '#') {
                sb2.append(c);
            }
        }

        return sb2.toString();
    }

    /**
     * 中心扩散
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s     原字符串
     * @param left  扩散起始的左指针
     * @param right 扩散起始的右指针
     * @return 当前最长回文串的左右指针数组
     */
    private int[] centerExpand(String s, int left, int right) {
        while (left - 1 >= 0 && right + 1 < s.length() && s.charAt(left - 1) == s.charAt(right + 1)) {
            left--;
            right++;
        }

        return new int[]{left, right};
    }

    /**
     * 从s[left]和s[right]中心扩散，返回中心回文的臂长
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private int getArmByCenterExpand(String s, int left, int right) {
        while (left - 1 >= 0 && right + 1 < s.length() && s.charAt(left - 1) == s.charAt(right + 1)) {
            left--;
            right++;
        }

        //注意：返回的是臂长
        return (right - left) / 2;
    }
}
