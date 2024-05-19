package com.zhang.java;

/**
 * @Date 2024/5/5 08:19
 * @Author zsy
 * @Description 两个回文子字符串长度的最大乘积 华为面试题 马拉车类比Problem5、Problem647、Problem2472 中心扩散类比Problem5、Problem267、Problem647、Problem696 类比Problem132、Problem1278、Problem1745、Problem2472 回文类比
 * 给你一个下标从 0 开始的字符串 s ，你需要找到两个 不重叠的回文 子字符串，
 * 它们的长度都必须为 奇数 ，使得它们长度的乘积最大。
 * 更正式地，你想要选择四个整数 i ，j ，k ，l ，使得 0 <= i <= j < k <= l < s.length ，
 * 且子字符串 s[i...j] 和 s[k...l] 都是回文串且长度为奇数。
 * s[i...j] 表示下标从 i 到 j 且 包含 两端下标的子字符串。
 * 请你返回两个不重叠回文子字符串长度的 最大 乘积。
 * 回文字符串 指的是一个从前往后读和从后往前读一模一样的字符串。
 * 子字符串 指的是一个字符串中一段连续字符。
 * <p>
 * 输入：s = "ababbb"
 * 输出：9
 * 解释：子字符串 "aba" 和 "bbb" 为奇数长度的回文串。乘积为 3 * 3 = 9 。
 * <p>
 * 输入：s = "zaaaxbbby"
 * 输出：9
 * 解释：子字符串 "aaa" 和 "bbb" 为奇数长度的回文串。乘积为 3 * 3 = 9 。
 * <p>
 * 2 <= s.length <= 10^5
 * s 只包含小写英文字母。
 */
public class Problem1960 {
    public static void main(String[] args) {
        Problem1960 problem1960 = new Problem1960();
        String s = "xyzazyx";
        System.out.println(problem1960.maxProduct(s));
        System.out.println(problem1960.maxProduct2(s));
    }

    /**
     * 动态规划
     * dp1[i][j]：s[i]-s[j]是否是回文串
     * dp2[i][j]：s[i]-s[j]中长度为奇数的最长回文子串长度
     * dp1[i][j] = dp1[i+1][j-1] && (s[i] == s[j])
     * dp2[i][j] = max(dp2[i][j-1],dp2[i+1][j]) (s[i]-s[j]长度为偶数)
     * dp2[i][j] = j-i+1                        (s[i]-s[j]长度为奇数 && dp1[i][j])
     * dp2[i][j] = max(dp2[i][j-1],dp2[i+1][j]) (s[i]-s[j]长度为奇数 && !dp1[i][j])
     * 时间复杂度O(n^2)，空间复杂度O(n^2)
     *
     * @param s
     * @return
     */
    public long maxProduct(String s) {
        boolean[][] dp1 = new boolean[s.length()][s.length()];

        //只有一个字符是回文串的特殊情况
        for (int i = 0; i < s.length(); i++) {
            dp1[i][i] = true;
        }

        //s[i]-s[i+1]是回文串的特殊情况
        for (int i = 1; i < s.length(); i++) {
            dp1[i][i - 1] = true;
        }

        //当前字符串长度为i
        for (int i = 2; i <= s.length(); i++) {
            //当前字符串的起始下标索引为j
            for (int j = 0; j <= s.length() - i; j++) {
                dp1[j][j + i - 1] = dp1[j + 1][j + i - 2] && (s.charAt(j) == s.charAt(j + i - 1));
            }
        }

        int[][] dp2 = new int[s.length()][s.length()];

        //dp2初始化，s[i]-s[i]中长度为奇数的最长回文子串长度为1
        for (int i = 0; i < s.length(); i++) {
            dp2[i][i] = 1;
        }

        //dp2初始化，s[i]-s[i+1]中长度为奇数的最长回文子串长度为1
        for (int i = 0; i < s.length() - 1; i++) {
            dp2[i][i + 1] = 1;
        }

        //当前字符串的长度为i
        for (int i = 3; i <= s.length(); i++) {
            //当前字符串的起始下标索引为j
            for (int j = 0; j <= s.length() - i; j++) {
                //表示的字符串s[j]-s[j+i-1]
                if (i % 2 == 0) {
                    dp2[j][j + i - 1] = Math.max(dp2[j][j + i - 2], dp2[j + 1][j + i - 1]);
                } else {
                    if (dp1[j][j + i - 1]) {
                        dp2[j][j + i - 1] = i;
                    } else {
                        dp2[j][j + i - 1] = Math.max(dp2[j][j + i - 2], dp2[j + 1][j + i - 1]);
                    }
                }
            }
        }

        long max = 0;

        //s[0]-s[i]中长度为奇数的最长回文子串长度乘以s[i+1]-s[s.length()-1]中长度为奇数的最长回文子串长度
        for (int i = 0; i < s.length() - 1; i++) {
            max = Math.max(max, (long) dp2[0][i] * dp2[i + 1][s.length() - 1]);
        }

        return max;
    }

    /**
     * 马拉车(Manacher)+前缀和
     * left[i]：s[0]-s[i]中长度为奇数的最长回文子串长度
     * right[i]：s[i]-s[s.length()-1]中长度为奇数的最长回文子串长度
     * 1、left[i] = max(2*armArr[j]+1)          (j+armArr[j] == i)
     * 2、left[i] = max(left[i],left[i-1])
     * 3、left[i] = max(left[i],left[i+1]-2)
     * 1、right[i] = max(2*armArr[j]+1)         (j-armArr[j] == i)
     * 2、right[i] = max(right[i],right[i+1])
     * 3、right[i] = max(right[i],right[i-1]-2)
     * 如果s="xyzazyx"，第2步之后，left = [1,1,1,1,1,1,7]，但left[5]应该为5，left[4]应该为3，第3步从后往前遍历，
     * left[5]=max(left[5],left[6]-2)，如果left[6]长度为奇数的最长回文子串大于left[5]，则left[6]首尾各去掉一个字符，
     * 得到left[6]-2的回文子串
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @return
     */
    public long maxProduct2(String s) {
        //s[i]作为回文中心得到奇数长度回文的臂长数组
        int[] armArr = manacher(s);

        int[] left = new int[s.length()];
        int[] right = new int[s.length()];

        //1、left[i]和right[i]初始化
        for (int i = 0; i < s.length(); i++) {
            left[i + armArr[i]] = Math.max(left[i + armArr[i]], 2 * armArr[i] + 1);
            right[i - armArr[i]] = Math.max(right[i - armArr[i]], 2 * armArr[i] + 1);
        }

        //left的第2步
        for (int i = 1; i < s.length(); i++) {
            left[i] = Math.max(left[i], left[i - 1]);
        }

        //left的第3步
        for (int i = s.length() - 2; i >= 0; i--) {
            left[i] = Math.max(left[i], left[i + 1] - 2);
        }

        //right的第2步
        for (int i = s.length() - 2; i >= 0; i--) {
            right[i] = Math.max(right[i], right[i + 1]);
        }

        //right的第3步
        for (int i = 1; i < s.length(); i++) {
            right[i] = Math.max(right[i], right[i - 1] - 2);
        }

        long max = 0;

        //s[0]-s[i]中长度为奇数的最长回文子串长度乘以s[i+1]-s[s.length()-1]中长度为奇数的最长回文子串长度
        for (int i = 0; i < s.length() - 1; i++) {
            max = Math.max(max, (long) left[i] * right[i + 1]);
        }

        return max;
    }

    /**
     * 返回s[i]作为回文中心得到奇数长度回文的臂长数组
     * 注意：因为求奇数回文，所以s字符之间不需要添加'#'
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    private int[] manacher(String s) {
        //s每个位置的臂长数组
        //注意：因为求奇数回文，所以s字符之间不需要添加'#'
        int[] armArr = new int[s.length()];
        //已经遍历过的下标索引作为中心得到最大最右边的回文中心下标索引
        int j = -1;
        //j的臂长
        int length = 0;

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
        }

        return armArr;
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
