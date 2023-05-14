package com.zhang.java;

/**
 * @Date 2023/4/26 10:14
 * @Author zsy
 * @Description Excel表列序号 进制类比Problem12、Problem13、Problem168、Add36Strings
 * 给你一个字符串 columnTitle ，表示 Excel 表格中的列名称。返回 该列名称对应的列序号 。
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * <p>
 * 输入: columnTitle = "A"
 * 输出: 1
 * <p>
 * 输入: columnTitle = "AB"
 * 输出: 28
 * <p>
 * 输入: columnTitle = "ZY"
 * 输出: 701
 * <p>
 * 1 <= columnTitle.length <= 7
 * columnTitle 仅由大写英文组成
 * columnTitle 在范围 ["A", "FXSHRXW"] 内
 */
public class Problem171 {
    public static void main(String[] args) {
        Problem171 problem171 = new Problem171();
        String columnTitle = "ZY";
        System.out.println(problem171.titleToNumber(columnTitle));
    }

    /**
     * 模拟
     * A-Z是26进制数，从左往右遍历columnTitle，每次需要乘26再加上当前字符对应数字加1
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param columnTitle
     * @return
     */
    public int titleToNumber(String columnTitle) {
        int num = 0;

        for (char c : columnTitle.toCharArray()) {
            //因为A对应1，所以当前字符对应数字要加1
            num = num * 26 + (c - 'A' + 1);
        }

        return num;
    }
}
