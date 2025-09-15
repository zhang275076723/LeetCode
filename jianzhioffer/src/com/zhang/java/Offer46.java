package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/28 9:26
 * @Author zsy
 * @Description 把数字翻译成字符串 类比Problem91
 * 给定一个数字，我们按照如下规则把它翻译为字符串：
 * 0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。
 * 一个数字可能有多个翻译。
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
        int num = 12258;
        System.out.println(offer46.translateNum(num));
        System.out.println(offer46.translateNum2(num));
        System.out.println(offer46.translateNum3(num));
    }

    /**
     * 动态规划
     * dp[i]：以num[i-1]结束的数字，翻译成字符串的方案数
     * dp[i] = dp[i-1] + dp[i-2] (nums[i-2] != '0'，且nums[i-2]和nums[i-1]构成的数字小于等于25)
     * dp[i] = dp[i-1]           (nums[i-2] == '0'，或nums[i-2]和nums[i-1]构成的数字大于25)
     * 时间复杂度O(log(num))，空间复杂度O(log(num))
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
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

            //不存在前导0，且number小于等于25
            if (c2 != '0' && number <= 25) {
                dp[i] = dp[i - 1] + dp[i - 2];
            } else {
                dp[i] = dp[i - 1];
            }
        }

        return dp[str.length()];
    }

    /**
     * 动态规划优化，使用滚动数组
     * 时间复杂度O(log(num))，空间复杂度O(log(num))
     *
     * @param num
     * @return
     */
    public int translateNum2(int num) {
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

            //不存在前导0，且number小于等于25
            if (c2 != '0' && number <= 25) {
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
     * 时间复杂度O(2^(log(num)))，空间复杂度O(log(num))
     *
     * @param num
     * @return
     */
    public int translateNum3(int num) {
        List<String> list = new ArrayList<>();

        int count = backtrack(0, num + "", list, new StringBuilder());

        System.out.println(list);

        return count;
    }

    private int backtrack(int t, String str, List<String> list, StringBuilder sb) {
        //遍历到末尾，则找到1种结果，返回1
        if (t == str.length()) {
            list.add(sb.toString());
            return 1;
        }

        int count = 0;
        //当前字符
        char c = str.charAt(t);

        //往后找一个字符
        sb.append((char) (c - '0' + 'a'));
        count = count + backtrack(t + 1, str, list, sb);
        sb.delete(sb.length() - 1, sb.length());

        //往后找两个字符
        if (t + 1 < str.length()) {
            //字符c的后一个字符
            char c2 = str.charAt(t + 1);
            //c和c2组成的数字
            int number = (c - '0') * 10 + (c2 - '0');

            //不存在前导0，且number小于等于25
            if (c != '0' && number <= 25) {
                sb.append((char) (number + 'a'));
                count = count + backtrack(t + 2, str, list, sb);
                sb.delete(sb.length() - 1, sb.length());
            }
        }

        return count;
    }
}
