package com.zhang.java;

/**
 * @Date 2022/10/31 09:33
 * @Author zsy
 * @Description Z 字形变换 类比Problem281
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行Z 字形排列。
 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 * 请你实现这个将字符串进行指定行数变换的函数：
 * string convert(string s, int numRows);
 * <p>
 * 输入：s = "PAYPALISHIRING", numRows = 3
 * 输出："PAHNAPLSIIGYIR"
 * <p>
 * 输入：s = "PAYPALISHIRING", numRows = 4
 * 输出："PINALSIGYAHRPI"
 * 解释：
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * <p>
 * 输入：s = "A", numRows = 1
 * 输出："A"
 * <p>
 * 1 <= s.length <= 1000
 * s 由英文字母（小写和大写）、',' 和 '.' 组成
 * 1 <= numRows <= 1000
 */
public class Problem6 {
    public static void main(String[] args) {
        Problem6 problem6 = new Problem6();
        String s = "PAYPALISHIRING";
        int numRows = 4;
        System.out.println(problem6.convert(s, numRows));
    }

    /**
     * 模拟
     * 1、s的长度小于等于行数，或只有一行，直接返回s
     * 2、s的长度大于行数，创建行数大小的StringBuilder数组，使用标志位，确定当前是从上往下添加还是从下往上添加到StringBuilder中，
     * 最后拼接StringBuilder数组，得到最后结果
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }

        StringBuilder[] sbArr = new StringBuilder[numRows];

        for (int i = 0; i < numRows; i++) {
            sbArr[i] = new StringBuilder();
        }

        //遍历标志位，1：由上往下添加，-1：由下往上添加
        int flag = 1;
        //当前遍历到的sb数组索引下标
        int index = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (flag == 1) {
                sbArr[index].append(c);
                index++;

                //当遍历到最后一个sb时，改变遍历方向为由下到上
                if (index == numRows) {
                    index = numRows - 2;
                    flag = -1;
                }
            } else {
                sbArr[index].append(c);
                index--;

                //当遍历到第一个sb时，改变遍历方向为由上到下
                if (index == -1) {
                    index = 1;
                    flag = 1;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numRows; i++) {
            sb.append(sbArr[i]);
        }

        return sb.toString();
    }
}
