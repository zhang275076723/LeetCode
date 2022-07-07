package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2022/6/22 7:48
 * @Author zsy
 * @Description 复原 IP 地址 虾皮机试题 类比Problem468
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

        List<String> result = new ArrayList<>();

        backtrack(0, new ArrayList<>(), s, result);

        return result;
    }

    /**
     * 回溯+剪枝
     *
     * @param start  起始索引
     * @param list   当前每个ip段集合
     * @param s      字符串数字
     * @param result 结果集合
     */
    private void backtrack(int start, List<String> list, String s, List<String> result) {
        //找到合法的ip地址
        if (list.size() == 4 && start == s.length()) {
            StringBuilder sb = new StringBuilder();
            for (String str : list) {
                sb.append(str).append('.');
            }
            //删除最后一个'.'
            sb.delete(sb.length() - 1, sb.length());

            result.add(sb.toString());

            return;
        }

        //剩余字符串数字不能构成合法ip地址，则剪枝
        if (s.length() - start < 4 - list.size() || (s.length() - start) > 3 * (4 - list.size())) {
            return;
        }

        for (int i = start; i < start + 3; i++) {
            if (i >= s.length()) {
                return;
            }

            int num = isValid(s, start, i);

            //当前数字不是一个合法的ip段，则直接返回
            if (num == -1) {
                return;
            }

            list.add(num + "");

            backtrack(i + 1, list, s, result);

            list.remove(list.size() - 1);
        }
    }

    /**
     * 返回当前ip段数字，即在0-255之间，且没有前导0；如果不合法，返回-1
     *
     * @param s     字符串数字
     * @param start 起始索引
     * @param end   结束索引
     * @return
     */
    private int isValid(String s, int start, int end) {
        //当前字符串只有一位
        if (start == end) {
            return s.charAt(start) - '0';
        }

        //当前字符串长度超过3,
        if (end - start + 1 > 3) {
            return -1;
        }

        //当前字符串长度超过1，且有前导0
        if (start < end && s.charAt(start) == '0') {
            return -1;
        }

        int num = 0;

        for (int i = start; i <= end; i++) {
            num = num * 10 + s.charAt(i) - '0';
        }

        if (num > 255) {
            return -1;
        }

        return num;
    }
}
