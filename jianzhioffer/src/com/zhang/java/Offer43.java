package com.zhang.java;

/**
 * @Date 2022/3/26 17:04
 * @Author zsy
 * @Description 1～n 整数中 1 出现的次数 类比Offer44、Problem400 同Problem233
 * 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
 * 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。
 * <p>
 * 输入：n = 12
 * 输出：5
 * <p>
 * 输入：n = 13
 * 输出：6
 * <p>
 * 1 <= n < 2^31
 */
public class Offer43 {
    public static void main(String[] args) {
        Offer43 offer43 = new Offer43();
        System.out.println(offer43.countDigitOne(123));
    }

    /**
     * 模拟
     * 计算n的每一位为1的情况
     * 时间复杂度O(logn)，空间复杂度O(1)
     * <p>
     * 例如：n=123
     * 个位为1,00(1)-12(1)，共13个
     * 十位为1，0(1)0-0(1)9，1(1)0-1(1)9，共20个
     * 百位为1，(1)00-(1)23，共24个
     * 所以，共出现13+20+24=57次
     *
     * @param n
     * @return
     */
    public int countDigitOne(int n) {
        if (n == 0) {
            return 0;
        }

        //1出现的次数
        int result = 0;
        //当前剩余数字
        int num = n;
        //当前位低位所能表示数字的个数
        int lowCount = 1;
        //当前位低位的值
        int low = 0;
        //当前位的值
        int cur = n % 10;
        //当前位高位的值
        int high = n / 10;

        while (num != 0) {
            if (cur == 0) {
                //当前cur为0，高位值0到high-1，共high种取值，乘上低位0到9..9，共lowCount种取值
                result = result + high * lowCount;
            } else if (cur == 1) {
                //当前cur为1，高位先取0到high-1，共high种取值，乘上低位0到9..9，共lowCount种取值；再加上高位取high，乘上低位取0到low
                result = result + high * lowCount + low + 1;
            } else {
                //当前cur大于1，高位取0-high，共high+1种取值，乘上低位0到9..9，共lowCount种取值
                result = result + (high + 1) * lowCount;
            }

            lowCount = lowCount * 10;
            num = num / 10;
            cur = num % 10;
            low = n % lowCount;
            high = high / 10;
        }

        return result;
    }
}
