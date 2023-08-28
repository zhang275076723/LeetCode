package com.zhang.java;

/**
 * @Date 2023/8/1 08:48
 * @Author zsy
 * @Description 数字转换为十六进制数 位运算类比Problem29、Problem190、Problem191、Problem201、Problem231、Problem271、Problem326、Problem342、Problem371、Problem461、Problem477、Problem898、Problem1290、Offer15、Offer64、Offer65、IpToInt
 * 给定一个整数，编写一个算法将这个数转换为十六进制数。对于负整数，我们通常使用 补码运算 方法。
 * 注意:
 * 1、十六进制中所有字母(a-f)都必须是小写。
 * 2、十六进制字符串中不能包含多余的前导零。如果要转化的数为0，那么以单个字符'0'来表示；
 * 对于其他情况，十六进制字符串中的第一个字符将不会是0字符。
 * 3、给定的数确保在32位有符号整数范围内。
 * 4、不能使用任何由库提供的将数字直接转换或格式化为十六进制的方法。
 * <p>
 * 输入:
 * 26
 * 输出:
 * "1a"
 * <p>
 * 输入:
 * -1
 * 输出:
 * "ffffffff"
 */
public class Problem405 {
    public static void main(String[] args) {
        Problem405 problem405 = new Problem405();
        int num = -1;
        System.out.println(problem405.toHex(num));
    }

    /**
     * 模拟
     * 每4位二进制数对应1位十六进制数，num每次和0xf进行与运算，得到当前位的十六进制数
     * 时间复杂度O(1)，空间复杂度(1)
     *
     * @param num
     * @return
     */
    public String toHex(int num) {
        if (num == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (num != 0) {
            //当前位的十六进制数
            int cur = num & 0xf;

            if (cur < 10) {
                sb.append(cur);
            } else {
                sb.append((char)(cur + 'a' - 10));
            }

            //num有可能为负数，所以要无符号右移4位
            num = num >>> 4;
        }

        return sb.reverse().toString();
    }
}
