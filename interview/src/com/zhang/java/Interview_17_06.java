package com.zhang.java;

/**
 * @Date 2024/6/22 08:57
 * @Author zsy
 * @Description 2出现的次数 类比Problem60、Problem172、Problem233、Problem400、Offer43、Offer44
 * 编写一个方法，计算从 0 到 n (含 n) 中数字 2 出现的次数。
 * <p>
 * 输入: 25
 * 输出: 9
 * 解释: (2, 12, 20, 21, 22, 23, 24, 25)(注意 22 应该算作两次)
 * <p>
 * n <= 10^9
 */
public class Interview_17_06 {
    public static void main(String[] args) {
        Interview_17_06 interview_17_06 = new Interview_17_06();
        int n = 123;
        System.out.println(interview_17_06.numberOf2sInRange(n));
    }

    /**
     * 模拟
     * 计算n的每一位为2的情况
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     * <p>
     * 例如：n=123
     * 个位为2，00(2)-12(2)，共13个
     * 十位为2，0(2)0-0(2)9，1(2)0-1(2)3，共14个
     * 百位为2，共0个
     * 所以，共出现13+14+0=27次
     *
     * @param n
     * @return
     */
    public int numberOf2sInRange(int n) {
        if (n == 0) {
            return 0;
        }

        //2出现的次数
        int count = 0;
        //当前剩余数字，每次除以10，相当于判断n的最低位
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
            //当前cur小于2，高位值0到high-1，共high种取值，乘上低位0到9..9，共lowCount种取值
            if (cur < 2) {
                count = count + high * lowCount;
            } else if (cur == 2) {
                //当前cur为2，高位先取0到high-1，共high种取值，乘上低位0到9..9，共lowCount种取值；
                //再加上高位取high，乘上低位取0到low，共low+1种取值
                count = count + high * lowCount + low + 1;
            } else {
                //当前cur大于2，高位取0-high，共high+1种取值，乘上低位0到9..9，共lowCount种取值
                count = count + (high + 1) * lowCount;
            }

            lowCount = lowCount * 10;
            num = num / 10;
            cur = num % 10;
            low = n % lowCount;
            high = high / 10;
        }

        return count;
    }
}
