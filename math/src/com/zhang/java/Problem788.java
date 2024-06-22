package com.zhang.java;

/**
 * @Date 2024/6/18 08:48
 * @Author zsy
 * @Description 旋转数字 类比Problem357 各种数类比
 * 我们称一个数 X 为好数, 如果它的每位数字逐个地被旋转 180 度后，我们仍可以得到一个有效的，且和 X 不同的数。
 * 要求每位数字都要被旋转。
 * 如果一个数的每位数字被旋转以后仍然还是一个数字， 则这个数是有效的。
 * 0, 1, 和 8 被旋转后仍然是它们自己；
 * 2 和 5 可以互相旋转成对方（在这种情况下，它们以不同的方向旋转，换句话说，2 和 5 互为镜像）；
 * 6 和 9 同理，除了这些以外其他的数字旋转以后都不再是有效的数字。
 * 现在我们有一个正整数 N, 计算从 1 到 N 中有多少个数 X 是好数？
 * <p>
 * 输入: 10
 * 输出: 4
 * 解释:
 * 在[1, 10]中有四个好数： 2, 5, 6, 9。
 * 注意 1 和 10 不是好数, 因为他们在旋转之后不变。
 * <p>
 * N 的取值范围是 [1, 10000]。
 */
public class Problem788 {
    public static void main(String[] args) {
        Problem788 problem788 = new Problem788();
        int n = 10;
        System.out.println(problem788.rotatedDigits(n));
    }

    /**
     * 模拟
     * n每位旋转180度得到的数字和n不同，则必须有2、5、6、9中的数字，必须没有3、4、7中的数字，0、1、8可有可没有
     * 时间复杂度O(nlogn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int rotatedDigits(int n) {
        int count = 0;

        for (int i = 1; i <= n; i++) {
            int temp = i;
            //temp中是否出现2、5、6、9
            boolean flag1 = false;
            //temp中是否出现3、4、7
            boolean flag2 = false;

            //遍历temp的每一位，判断是否是好数
            while (temp != 0) {
                int cur = temp % 10;

                if (cur == 2 || cur == 5 || cur == 6 || cur == 9) {
                    flag1 = true;
                } else if (cur == 3 || cur == 4 || cur == 7) {
                    flag2 = true;
                    break;
                }

                temp = temp / 10;
            }

            if (flag1 && !flag2) {
                count++;
            }
        }

        return count;
    }
}
