package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/10/6 08:08
 * @Author zsy
 * @Description 回文排列 类比Problem267、Problem409 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1147、Problem1312、Problem1332
 * 给定一个字符串，判断该字符串中是否可以通过重新排列组合，形成一个回文字符串。
 * <p>
 * 输入: "code"
 * 输出: false
 * <p>
 * 输入: "aab"
 * 输出: true
 * <p>
 * 输入: "carerac"
 * 输出: true
 * <p>
 * 1 <= s.length <= 5000
 * s 只由小写 英文字母组成
 */
public class Problem266 {
    public static void main(String[] args) {
        Problem266 problem266 = new Problem266();
        String s = "carerac";
        System.out.println(problem266.canPermutePalindrome(s));
    }

    /**
     * 哈希表
     * 统计字符串s中每个字符出现的次数，
     * 出现次数为偶数的字符都能构成回文串，出现次数为奇数的字符最多只能有1个，超过1个，则不能构成回文串
     * 时间复杂度O(n)，空间复杂度O(1) (只包含小写字母，共26种情况)
     *
     * @param s
     * @return
     */
    public boolean canPermutePalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();

        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        //map中字符出现次数为奇数的标志位，出现次数为奇数的字符最多只能有1个，超过1个，则不能构成回文串
        boolean oddFlag = false;

        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            //出现次数为奇数的字符最多只能有1个，超过1个，则不能构成回文串
            if (entry.getValue() % 2 == 1) {
                //之前已经出现过出现次数为奇数的字符，直接返回false
                if (oddFlag) {
                    return false;
                } else {
                    oddFlag = true;
                }
            }
        }

        return true;
    }
}
