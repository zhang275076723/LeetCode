package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/3/28 9:26
 * @Author zsy
 * @Description 给定一个数字，我们按照如下规则把它翻译为字符串：
 * 0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。
 * 请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 * <p>
 * 输入: 12258
 * 输出: 5
 * 解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
 */
public class Offer46 {
    private int count;

    public static void main(String[] args) {
        Offer46 offer46 = new Offer46();
//        System.out.println(offer46.translateNum(12258));
        System.out.println(offer46.translateNum2(12258));
    }

    /**
     * 回溯，时间复杂度O(2^n)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        List<String> list = new ArrayList<>();
        backtrack(Integer.toString(num), 0, list, new StringBuilder());
        System.out.println(list);
        return count;
    }

    /**
     * 动态规划，时间复杂度O(n)，空间复杂度O(1)
     * dp[i]：以i下标索引结束的数字，翻译成字符串的方案数
     * dp[i] = dp[i-1] + dp[i-2] ((数字：nums[i-1] + nums[i]) <= 25)
     * dp[i] = dp[i-1] ((数字：nums[i-1] + nums[i]) > 25)
     *
     * @param num
     * @return
     */
    public int translateNum2(int num) {
        String strNum = Integer.toString(num);
        int p = 1;
        int q = 1;
        int r = 1;

        for (int i = 1; i < strNum.length(); i++) {
            //dp[i] = dp[i-1]
            r = q;

            //如果下标索引i-1的数字为0，则不能往后找两个，即有前导0的0x不满足要求
            if (strNum.charAt(i-1) != '0') {
                if (Integer.parseInt(strNum.substring(i - 1, i + 1)) <= 25) {
                    //dp[i] = dp[i-1] + dp[i-2]
                    r = p + q;
                }
            }

            int temp = q;
            q = r;
            p = temp;
        }

        return r;
    }

    /**
     * 像跳台阶，一次跳一个，或者一次跳两个
     *
     * @param strNum
     * @param t
     */
    public void backtrack(String strNum, int t, List<String> list, StringBuilder sb) {
        //找到最后，表示找到，则返回
        if (t >= strNum.length()) {
            list.add(sb.toString());
            count++;
            return;
        }

        //往后找一个
        if (t + 1 <= strNum.length()) {
            char c = (char) (strNum.charAt(t) - '0' + 'a');
            sb.append(c);
            backtrack(strNum, t + 1, list, sb);
            sb.deleteCharAt(sb.length() - 1);
        }

        //如果当前为0，则不能往后找两个，即有前导0的0x不满足要求
        if (strNum.charAt(t) != '0') {
            //往后找两个
            if (t + 2 <= strNum.length() && Integer.parseInt(strNum.substring(t, t + 2)) <= 25) {
                char c = (char) (Integer.parseInt(strNum.substring(t, t + 2)) + 'a');
                sb.append(c);
                backtrack(strNum, t + 2, list, sb);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}
