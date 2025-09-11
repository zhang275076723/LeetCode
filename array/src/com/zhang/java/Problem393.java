package com.zhang.java;

/**
 * @Date 2024/6/16 08:23
 * @Author zsy
 * @Description UTF-8 编码验证 类比Problem271 位运算类比
 * 给定一个表示数据的整数数组 data ，返回它是否为有效的 UTF-8 编码。
 * UTF-8 中的一个字符可能的长度为 1 到 4 字节，遵循以下的规则：
 * 对于 1 字节 的字符，字节的第一位设为 0 ，后面 7 位为这个符号的 unicode 码。
 * 对于 n 字节 的字符 (n > 1)，第一个字节的前 n 位都设为1，第 n+1 位设为 0 ，后面字节的前两位一律设为 10 。
 * 剩下的没有提及的二进制位，全部为这个符号的 unicode 码。
 * 这是 UTF-8 编码的工作方式：
 * <      Number of Bytes  |        UTF-8 octet sequence
 * <                       |              (binary)
 * <   --------------------+---------------------------------------------
 * <            1          | 0xxxxxxx
 * <            2          | 110xxxxx 10xxxxxx
 * <            3          | 1110xxxx 10xxxxxx 10xxxxxx
 * <            4          | 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
 * x 表示二进制形式的一位，可以是 0 或 1。
 * 注意：输入是整数数组。只有每个整数的 最低 8 个有效位 用来存储数据。这意味着每个整数只表示 1 字节的数据。
 * <p>
 * 输入：data = [197,130,1]
 * 输出：true
 * 解释：数据表示字节序列:11000101 10000010 00000001。
 * 这是有效的 utf-8 编码，为一个 2 字节字符，跟着一个 1 字节字符。
 * <p>
 * 输入：data = [235,140,4]
 * 输出：false
 * 解释：数据表示 8 位的序列: 11101011 10001100 00000100.
 * 前 3 位都是 1 ，第 4 位为 0 表示它是一个 3 字节字符。
 * 下一个字节是开头为 10 的延续字节，这是正确的。
 * 但第二个延续字节不以 10 开头，所以是不符合规则的。
 * <p>
 * 1 <= data.length <= 2 * 10^4
 * 0 <= data[i] <= 255
 */
public class Problem393 {
    public static void main(String[] args) {
        Problem393 problem393 = new Problem393();
//        int[] data = {197, 130, 1};
        int[] data = {235, 140, 4};
        System.out.println(problem393.validUtf8(data));
    }

    /**
     * 模拟
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param data
     * @return
     */
    public boolean validUtf8(int[] data) {
        int index = 0;

        while (index < data.length) {
            //当前字符最高位为0，长度为1个字节
            if (((data[index] >>> 7) & 1) == 0) {
                index++;
            } else {
                //当前字符最高位为1，则长度为2-4个字节

                //当前字符的长度
                int count = 0;

                //data[index]从最高位连续1的个数
                for (int i = 7; i >= 0; i--) {
                    if (((data[index] >>> i) & 1) == 1) {
                        count++;
                    } else {
                        break;
                    }
                }

                //当前字符的长度只能是2-4字节
                if (count < 2 || count > 4) {
                    return false;
                }

                //data[index+i]必须是10开头
                for (int i = 1; i < count; i++) {
                    if (index + i >= data.length || ((data[index + i] >>> 6) & 0b11) != 0b10) {
                        return false;
                    }
                }

                index = index + count;
            }
        }

        //遍历结束，则是有效utf8编码，返回true
        return true;
    }
}
