package com.zhang.java;

/**
 * @Date 2022/11/1 09:05
 * @Author zsy
 * @Description 整数转罗马数字 类比Problem8、Problem13、Problem168、Problem171、Problem273、Offer67、ChineseToInteger
 * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。
 * 12 写做 XII ，即为 X + II 。
 * 27 写做 XXVII, 即为 XX + V + II 。
 * 通常情况下，罗马数字中小的数字在大的数字的右边。
 * 但也存在特例，例如 4 不写做 IIII，而是IV。
 * 数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
 * 同样地，数字 9 表示为IX。这个特殊的规则只适用于以下六种情况：
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给你一个整数，将其转为罗马数字。
 * <p>
 * 输入: num = 3
 * 输出: "III"
 * <p>
 * 输入: num = 4
 * 输出: "IV"
 * <p>
 * 输入: num = 9
 * 输出: "IX"
 * <p>
 * 输入: num = 58
 * 输出: "LVIII"
 * 解释: L = 50, V = 5, III = 3.
 * <p>
 * 输入: num = 1994
 * 输出: "MCMXCIV"
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 * <p>
 * 1 <= num <= 3999
 */
public class Problem12 {
    public static void main(String[] args) {
        Problem12 problem12 = new Problem12();
        int num = 3994;
        System.out.println(problem12.intToRoman(num));
    }

    /**
     * 模拟
     * 创建罗马字符和对应值的数组，由大到小遍历数字，优先减去较大的数字
     * 时间复杂度O(1)，空间复杂度O(1) (romans中每个罗马字符出现次数不会超过3次，所以循环有一个上限)
     *
     * @param num
     * @return
     */
    public String intToRoman(int num) {
        String[] romans = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder sb = new StringBuilder();

        //由大到小遍历数字，优先减去较大的数字
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num = num - values[i];
                sb.append(romans[i]);
            }
        }

        return sb.toString();
    }
}
