package com.zhang.java;

/**
 * @Date 2024/1/19 08:42
 * @Author zsy
 * @Description 神奇字符串 类比Problem38、Problem667、Problem717
 * 神奇字符串 s 仅由 '1' 和 '2' 组成，并需要遵守下面的规则：
 * 神奇字符串 s 的神奇之处在于，串联字符串中 '1' 和 '2' 的连续出现次数可以生成该字符串。
 * s 的前几个元素是 s = "1221121221221121122……" 。
 * 如果将 s 中连续的若干 1 和 2 进行分组，可以得到 "1 22 11 2 1 22 1 22 11 2 11 22 ......" 。
 * 每组中 1 或者 2 的出现次数分别是 "1 2 2 1 1 2 1 2 2 1 2 2 ......" 。上面的出现次数正是 s 自身。
 * 给你一个整数 n ，返回在神奇字符串 s 的前 n 个数字中 1 的数目。
 * <p>
 * 输入：n = 6
 * 输出：3
 * 解释：神奇字符串 s 的前 6 个元素是 “122112”，它包含三个 1，因此返回 3 。
 * <p>
 * 输入：n = 1
 * 输出：1
 * <p>
 * 1 <= n <= 10^5
 */
public class Problem481 {
    public static void main(String[] args) {
        Problem481 problem481 = new Problem481();
        int n = 6;
        System.out.println(problem481.magicalString(n));
    }

    /**
     * 模拟
     * 难点：如何初始化神奇字符串为"122"
     * 神奇字符串前两个数字为"12"，要满足连续1的个数出现1次，连续2的个数出现2次，则神奇字符串前三个数字为"122"，
     * 指针index指向下一个要添加的数字出现的次数的下标索引
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int magicalString(int n) {
        if (n <= 3) {
            return 1;
        }

        //神奇字符串，初始化为"122"
        StringBuilder sb = new StringBuilder("122");
        //指向下一个要添加的数字出现的次数的下标索引
        int index = 2;
        //下一个要添加的数字标志位，flag为1，添加1，flag为-1，添加2
        int flag = 1;

        while (sb.length() < n) {
            //当前添加数字出现的次数
            int count = sb.charAt(index) - '0';

            if (flag == 1) {
                for (int k = 0; k < count; k++) {
                    sb.append(1);
                }
            } else {
                for (int k = 0; k < count; k++) {
                    sb.append(2);
                }
            }

            index++;
            flag = -flag;
        }

        int count = 0;

        //注意：最后一次添加数字有可能添加2个数字，导致神奇字符串的长度为n+1，
        //所以统计神奇字符串中1的个数条件不能是sb.length()，而应该是n
        for (int i = 0; i < n; i++) {
            if (sb.charAt(i) == '1') {
                count++;
            }
        }

        return count;
    }
}
