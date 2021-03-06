package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/28 9:26
 * @Author zsy
 * @Description 把数字翻译成字符串 类比Offer10_2
 * 给定一个数字，我们按照如下规则把它翻译为字符串：
 * 0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。
 * 请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 * <p>
 * 输入: 12258
 * 输出: 5
 * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
 * <p>
 * 0 <= num < 2^31
 */
public class Offer46 {
    private int count = 0;

    public static void main(String[] args) {
        Offer46 offer46 = new Offer46();
        System.out.println(offer46.translateNum(12258));
        System.out.println(offer46.translateNum2(12258));
        System.out.println(offer46.translateNum3(12258));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(2^n)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        if (num >= 0 && num <= 9) {
            return 1;
        }

        List<String> list = new ArrayList<>();

        backtrack(String.valueOf(num), 0, list, new StringBuilder());

        System.out.println(list);

        return count;
    }

    /**
     * 动态规划
     * dp[i]：以num[i-1]结束的数字，翻译成字符串的方案数
     * dp[i] = dp[i-1] + dp[i-2] ((数字：nums[i-1] + nums[i]) < 26，且没有前导0)
     * dp[i] = dp[i-1] ((数字：nums[i-1] + nums[i]) >= 26)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int translateNum2(int num) {
        if (num >= 0 && num <= 9) {
            return 1;
        }

        String str = String.valueOf(num);
        int[] dp = new int[str.length() + 1];
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= str.length(); i++) {
            if (str.charAt(i - 2) != '0' && Integer.parseInt(str.substring(i - 2, i)) < 26) {
                dp[i] = dp[i - 1] + dp[i - 2];
            } else {
                dp[i] = dp[i - 1];
            }
        }

        return dp[str.length()];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public int translateNum3(int num) {
        if (num >= 0 && num <= 9) {
            return 1;
        }

        String str = String.valueOf(num);
        int p = 1;
        int q = 1;

        for (int i = 1; i < str.length(); i++) {
            int temp = q;
            if (str.charAt(i - 1) != '0' && Integer.parseInt(str.substring(i - 1, i + 1)) < 26) {
                q = p + q;
            }
            p = temp;
        }

        return q;
    }

    /**
     * 每次往后找1个或2个数字
     *
     * @param str
     * @param t
     * @param list
     * @param sb
     */
    public void backtrack(String str, int t, List<String> list, StringBuilder sb) {
        //找到最后，表示找到，则返回
        if (t == str.length()) {
            list.add(sb.toString());
            count++;
            return;
        }

        //往后找一个
        sb.append((char) (str.charAt(t) - '0' + 'a'));
        backtrack(str, t + 1, list, sb);
        sb.delete(sb.length() - 1, sb.length());

        //往后找两个，不能有前导0，且不能超过表示的字符'z'
        if (t + 2 <= str.length() && str.charAt(t) != '0'
                && Integer.parseInt(str.substring(t, t + 2)) < 26) {
            sb.append((char) (Integer.parseInt(str.substring(t, t + 2)) + 'a'));
            backtrack(str, t + 2, list, sb);
            sb.delete(sb.length() - 1, sb.length());
        }
    }
}
