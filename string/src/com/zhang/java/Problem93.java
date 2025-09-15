package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/6/22 7:48
 * @Author zsy
 * @Description 复原 IP 地址 虾皮机试题 蔚来面试题 类比Problem468、Problem751、IPToInt 回溯+剪枝类比
 * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
 * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，
 * 但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
 * 给定一个只包含数字的字符串 s ，用以表示一个 IP 地址，返回所有可能的有效 IP 地址，
 * 这些地址可以通过在 s 中插入 '.' 来形成。你 不能 重新排序或删除 s 中的任何数字。
 * 你可以按 任何 顺序返回答案。
 * <p>
 * 输入：s = "25525511135"
 * 输出：["255.255.11.135","255.255.111.35"]
 * <p>
 * 输入：s = "0000"
 * 输出：["0.0.0.0"]
 * <p>
 * 输入：s = "101023"
 * 输出：["1.0.10.23","1.0.102.3","10.1.0.23","10.10.2.3","101.0.2.3"]
 * <p>
 * 1 <= s.length <= 20
 * s 仅由数字组成
 */
public class Problem93 {
    public static void main(String[] args) {
        Problem93 problem93 = new Problem93();
        String s = "101023";
        System.out.println(problem93.restoreIpAddresses(s));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(|segment|*3^4)，空间复杂度O(3*4=12) (|segment|：每个ip段的长度)
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        if (s == null || s.length() < 4 || s.length() > 12) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();

        backtrack(0, s, 0, list, new StringBuilder());

        return list;
    }

    private void backtrack(int t, String s, int count, List<String> list, StringBuilder sb) {
        //当找到4个ip段，且已经遍历到末尾时，即找到一个合法ip
        if (count == 4) {
            //遍历到末尾，才找到正确的ip
            if (t == s.length()) {
                //注意：删除末尾'.'
                list.add(sb.substring(0, sb.length() - 1).toString());
            }

            return;
        }

        //ip段的长度为1-3
        for (int i = 0; i < 3; i++) {
            //当前ip段已经超过字符串s长度，直接返回
            if (t + i >= s.length()) {
                return;
            }

            String ipSegment = getIpSegment(s, t, t + i);

            //当前ip段不是一个合法的ip段，直接返回
            if (ipSegment == null) {
                return;
            }

            sb.append(ipSegment).append('.');
            backtrack(t + i + 1, s, count + 1, list, sb);
            //需要额外减1是为了删除末尾'.'
            sb.delete(sb.length() - ipSegment.length() - 1, sb.length());
        }
    }

    /**
     * 返回当前ip段
     * 合法ip段在0-255之间，且没有前导0；如果不合法，返回null
     *
     * @param s
     * @param left
     * @param right
     * @return
     */
    private String getIpSegment(String s, int left, int right) {
        //当前ip段长度为1，直接返回
        if (left == right) {
            return s.substring(left, right + 1);
        }

        //当前ip段以0开头，且长度超过1，不是合法ip段
        if (s.charAt(left) == '0') {
            return null;
        }

        String ipSegment = s.substring(left, right + 1);

        //当前ip段超过255，不是合法ip段
        if (Integer.parseInt(ipSegment) > 255) {
            return null;
        }

        return ipSegment;
    }
}
