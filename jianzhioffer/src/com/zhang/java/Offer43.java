package com.zhang.java;

/**
 * @Date 2022/3/26 17:04
 * @Author zsy
 * @Description 输入一个整数 n ，求1～n这n个整数的十进制表示中1出现的次数。
 * 例如，输入12，1～12这些整数中包含1 的数字有1、10、11和12，1一共出现了5次。
 * <p>
 * 输入：n = 12
 * 输出：5
 * <p>
 * 输入：n = 13
 * 输出：6
 */
public class Offer43 {
    public static void main(String[] args) {
        Offer43 offer43 = new Offer43();
        System.out.println(offer43.countDigitOne(100));
    }

    public int countDigitOne(int n) {
        int count = 0;
        int k = 1;
        //当前位的值
        int cur = n % 10;
        //高位的值
        int high = n / 10;
        //低位的值
        int low = 0;

        while (n >= k) {
            if (cur == 0) {
                //如果当前cur为0，高位值0到high-1，共high种取值，乘上低位0到9..9，共k种取值
                count = count + high * k;
            } else if (cur == 1) {
                //如果当前cur为1，高位先取0到high-1，共high种取值，乘上低位0到9..9，共k种取值，
                //再加上高位取high，乘上低位取0到low
                count = count + high * k + low + 1;
            } else {
                //如果当前cur大于1，高位取0-high，共high+1种取值，乘上低位0到9..9，共k种取值
                count = count + (high + 1) * k;
            }

            low = low + cur * k;
            cur = high % 10;
            high = high / 10;
            k = k * 10;
        }

        return count;
    }
}
