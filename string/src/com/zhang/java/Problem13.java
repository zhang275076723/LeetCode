package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/11/1 09:05
 * @Author zsy
 * @Description 罗马数字转整数 微软面试题 类比Problem8、Problem12、Problem168、Problem171、Problem273、Offer67、ChineseToInteger
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。
 * 12 写做 XII ，即为 X + II 。
 * 27 写做 XXVII, 即为 XX + V + II 。
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做IIII，而是IV
 * 。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为IX。
 * 这个特殊的规则只适用于以下六种情况：
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个罗马数字，将其转换成整数。
 * <p>
 * 输入: s = "III"
 * 输出: 3
 * <p>
 * 输入: s = "IV"
 * 输出: 4
 * <p>
 * 输入: s = "IX"
 * 输出: 9
 * <p>
 * 输入: s = "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 * <p>
 * 输入: s = "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * 1 <= s.length <= 15
 * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
 * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
 * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
 * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
 */
public class Problem13 {
    public static void main(String[] args) {
        Problem13 problem13 = new Problem13();
        String s = "MCMXCIV";
        System.out.println(problem13.romanToInt(s));
    }

    /**
     * 模拟
     * 当前罗马字符小于右边罗马字符时，减去当前罗马数字；当前罗马字符大于等于右边罗马字符时，加上当前罗马数字
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }

        //罗马数字和对应数字的映射map
        Map<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};

        int num = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //当前罗马字符表示的值
            int value = map.get(c);

            //当前罗马字符的值小于下一个罗马字符的值，则要减去当前罗马字符的值
            if (i + 1 < s.length() && value < map.get(s.charAt(i + 1))) {
                num = num - value;
            } else {
                //当前罗马字符的值大于等于下一个罗马字符的值，则要加上当前罗马字符的值
                num = num + value;
            }
        }

        return num;
    }
}
