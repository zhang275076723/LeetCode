package com.zhang.java;

/**
 * @Date 2023/4/26 09:51
 * @Author zsy
 * @Description Excel表列名称 类比Problem171、Problem631 类比Problem8、Problem12、Problem13、Problem171、Problem273、Offer67、ChineseToInteger
 * 给你一个整数 columnNumber ，返回它在 Excel 表中相对应的列名称。
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28
 * <p>
 * 输入：columnNumber = 1
 * 输出："A"
 * <p>
 * 输入：columnNumber = 28
 * 输出："AB"
 * <p>
 * 输入：columnNumber = 701
 * 输出："ZY"
 * <p>
 * 输入：columnNumber = 2147483647
 * 输出："FXSHRXW"
 * <p>
 * 1 <= columnNumber <= 2^31 - 1
 */
public class Problem168 {
    public static void main(String[] args) {
        Problem168 problem168 = new Problem168();
        //ZZ
        int columnNumber = 702;
        System.out.println(problem168.convertToTitle(columnNumber));
    }

    /**
     * 模拟
     * A-Z是26进制数，columnNumber每次先减1，再除以26，得到的余数对应的字符再逆序即为转化后的结果
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param columnNumber
     * @return
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();

        while (columnNumber != 0) {
            //因为A对应1，而不是正常进制数对应0，所以每次要先减1
            columnNumber--;
            sb.append((char) (columnNumber % 26 + 'A'));
            columnNumber = columnNumber / 26;
        }

        //逆序得到最终结果
        return sb.reverse().toString();
    }
}
