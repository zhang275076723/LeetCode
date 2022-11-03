package com.zhang.java;

/**
 * @Date 2022/11/1 09:05
 * @Author zsy
 * @Description 罗马数字转整数 类比Problem8、Problem12、Offer67、CharacterToInteger
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

        int num = 0;
        int index = 0;

        while (index < s.length()) {
            char c = s.charAt(index);
            int value = getRomanValue(c);

            //如果当前罗马字符小于下一个罗马字符，则说明要减去当前罗马字符
            if (index + 1 < s.length() && value < getRomanValue(s.charAt(index + 1))) {
                num = num - value;
            } else {
                //如果当前罗马字符大于等于下一个罗马字符，则说明要加上当前罗马字符
                num = num + value;
            }

            index++;
        }

        return num;
    }

    private int getRomanValue(char c) {
        if (c == 'I') {
            return 1;
        } else if (c == 'V') {
            return 5;
        } else if (c == 'X') {
            return 10;
        } else if (c == 'L') {
            return 50;
        } else if (c == 'C') {
            return 100;
        } else if (c == 'D') {
            return 500;
        } else if (c == 'M') {
            return 1000;
        }

        return -1;
    }
}
