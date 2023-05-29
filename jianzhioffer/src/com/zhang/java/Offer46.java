package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/28 9:26
 * @Author zsy
 * @Description 把数字翻译成字符串 类比Problem91、Problem93、Problem468 类比Problem70、Problem509、Problem746、Problem1137、Offer10、Offer10_2
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
    public static void main(String[] args) {
        Offer46 offer46 = new Offer46();
        System.out.println(offer46.translateNum(12258));
        System.out.println(offer46.translateNum2(12258));
        System.out.println(offer46.translateNum3(12258));
    }

    /**
     * 动态规划
     * dp[i]：以num[i-1]结束的数字，翻译成字符串的方案数
     * dp[i] = dp[i-1] + dp[i-2] (nums[i-2] != '0'，且(数字：nums[i-2]-nums[i-1]) < 26)
     * dp[i] = dp[i-1]           (nums[i-2] == '0'，或(数字：nums[i-2]-nums[i-1]) >= 26)
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        if (num >= 0 && num <= 9) {
            return 1;
        }

        String str = String.valueOf(num);
        int[] dp = new int[str.length() + 1];
        //dp初始化，没有数字或只有一个数字，翻译成字符串的方案数为1
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= str.length(); i++) {
            //当前字符
            char c = str.charAt(i - 1);
            //字符c的前一个字符
            char c2 = str.charAt(i - 2);
            //c2和c组成的数字
            int number = (c2 - '0') * 10 + (c - '0');

            //不存在前导0，且number小于26
            if (c2 != '0' && number < 26) {
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
    public int translateNum2(int num) {
        if (num >= 0 && num <= 9) {
            return 1;
        }

        String str = String.valueOf(num);
        int p = 1;
        int q = 1;

        for (int i = 2; i <= str.length(); i++) {
            //当前字符
            char c = str.charAt(i - 1);
            //字符c的前一个字符
            char c2 = str.charAt(i - 2);
            //c2和c组成的数字
            int number = (c2 - '0') * 10 + (c - '0');

            //不存在前导0，且number小于26
            if (c2 != '0' && number < 26) {
                int temp = q;
                q = p + q;
                p = temp;
            } else {
                p = q;
            }
        }

        return q;
    }


    /**
     * 回溯+剪枝
     * 时间复杂度O(2^n)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int translateNum3(int num) {
        if (num >= 0 && num <= 9) {
            return 1;
        }

        List<String> list = new ArrayList<>();

        int count = backtrack(0, num + "", list, new StringBuilder());

        System.out.println(list);

        return count;
    }

    private int backtrack(int t, String num, List<String> list, StringBuilder sb) {
        //找到最后，表示找到，则返回
        if (t == num.length()) {
            list.add(sb.toString());
            return 1;
        }

        int count = 0;

        //往后找一个字符
        sb.append((char) (num.charAt(t) - '0' + 'a'));
        count = count + backtrack(t + 1, num, list, sb);
        sb.delete(sb.length() - 1, sb.length());

        //往后找两个字符，不能有前导0，且num[t]-num[t+1]于26
        if (t + 2 <= num.length() && num.charAt(t) != '0' && Integer.parseInt(num.substring(t, t + 2)) < 26) {
            sb.append((char) (Integer.parseInt(num.substring(t, t + 2)) + 'a'));
            count = count + backtrack(t + 2, num, list, sb);
            sb.delete(sb.length() - 1, sb.length());
        }

        return count;
    }
}
