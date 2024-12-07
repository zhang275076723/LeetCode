package com.zhang.java;

/**
 * @Date 2024/8/15 08:20
 * @Author zsy
 * @Description 将整数减少到零需要的最少操作数 类比Problem397、Problem453、Problem991、Problem1342、Problem1404、Problem2139 位运算类比
 * 给你一个正整数 n ，你可以执行下述操作 任意 次：
 * n 加上或减去 2 的某个 幂
 * 返回使 n 等于 0 需要执行的 最少 操作数。
 * 如果 x == 2^i 且其中 i >= 0 ，则数字 x 是 2 的幂。
 * <p>
 * 输入：n = 39
 * 输出：3
 * 解释：我们可以执行下述操作：
 * - n 加上 2^0 = 1 ，得到 n = 40 。
 * - n 减去 2^3 = 8 ，得到 n = 32 。
 * - n 减去 2^5 = 32 ，得到 n = 0 。
 * 可以证明使 n 等于 0 需要执行的最少操作数是 3 。
 * <p>
 * 输入：n = 54
 * 输出：3
 * 解释：我们可以执行下述操作：
 * - n 加上 2^1 = 2 ，得到 n = 56 。
 * - n 加上 2^3 = 8 ，得到 n = 64 。
 * - n 减去 2^6 = 64 ，得到 n = 0 。
 * 使 n 等于 0 需要执行的最少操作数是 3 。
 * <p>
 * 1 <= n <= 10^5
 */
public class Problem2571 {
    public static void main(String[] args) {
        Problem2571 problem2571 = new Problem2571();
        int n = 54;
        System.out.println(problem2571.minOperations(n));
        System.out.println(problem2571.minOperations2(n));
    }

    /**
     * 模拟
     * n的二进制表示从低位到高位连续1的个数只有1个，则减去2^k，k为当前位1的下标索引；
     * n的二进制表示从低位到高位连续1的个数大于1个，则加上2^k，k为从低位到高位连续1的最低位1的下标索引
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @return
     */
    public int minOperations(int n) {
        //n的二进制表示
        StringBuilder sb = new StringBuilder();
        int temp = n;

        while (temp != 0) {
            sb.append(temp & 1);
            temp = temp >>> 1;
        }

        //因为是从后往前遍历，反转之后得到n的二进制表示
        sb.reverse();

        //n变为0的最少操作次数
        int count = 0;
        int index = sb.length() - 1;

        while (index >= 0) {
            //sb当前位的值
            int cur = sb.charAt(index) - '0';

            //当前位的值为0，则当前位不用考虑，直接进行下次循环
            if (cur == 0) {
                index--;
                continue;
            }

            int i = index - 1;

            while (i >= 0 && sb.charAt(i) - '0' == 1) {
                i--;
            }

            //当前位到最高位都为1
            if (i < 0) {
                //连续1的个数大于1个，则加上2^k，再减去2^(k+1)，k为从低位到高位连续1的最高位1的下标索引，即需要2次
                if (index - i > 1) {
                    return count + 2;
                } else {
                    //连续1的个数等于1个，则减去2^k，k为从低位到高位连续1的最高位1的下标索引，即需要1次
                    return count + 1;
                }
            }

            //从低位到高位连续1的个数大于1个，则加上2^k，k为从低位到高位连续1的最高位1的下标索引，即第i位由0变为1
            if (index - i > 1) {
                sb.setCharAt(i, '1');
                count++;
                index = i;
            } else {
                //从低位到高位连续1的个数只有1个，则减去2^k，k为当前位1的下标索引，即第index位由1变为0
                count++;
                index = i;
            }
        }

        return count;
    }

    /**
     * 位运算
     * n&(-n)=n&(~n+1)：n表示的二进制数中最低位的1表示的数
     * n的二进制表示从低位到高位连续1的个数只有1个，则减去2^k，k为当前位1的下标索引；
     * n的二进制表示从低位到高位连续1的个数大于1个，则加上2^k，k为从低位到高位连续1的最低位1的下标索引
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int minOperations2(int n) {
        int count = 0;

        while (n != 0) {
            //n表示的二进制数中最低位的1表示的数
            int num = n & (-n);

            //从低位到高位连续1的个数大于1个，则加上2^k，k为从低位到高位连续1的最高位1的下标索引
            if ((n & (num << 1)) != 0) {
                n = n + num;
                count++;
            } else {
                //从低位到高位连续1的个数只有1个，则减去2^k，k为当前位1的下标索引
                n = n - num;
                count++;
            }
        }

        return count;
    }
}
