package com.zhang.java;

/**
 * @Date 2024/3/6 09:07
 * @Author zsy
 * @Description 回文质数 质数类比Problem204、Problem952、Problem1175、Problem1998、Problem2523、Problem2614 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem1312、Problem1332
 * 给你一个整数 n ，返回大于或等于 n 的最小回文质数。
 * 一个整数如果恰好有两个除数：1 和它本身，那么它是 质数 。
 * 注意，1 不是质数。
 * 例如，2、3、5、7、11 和 13 都是质数。
 * 一个整数如果从左向右读和从右向左读是相同的，那么它是 回文数 。
 * 例如，101 和 12321 都是回文数。
 * 测试用例保证答案总是存在，并且在 [2, 2 * 10^8] 范围内。
 * <p>
 * 输入：n = 6
 * 输出：7
 * <p>
 * 输入：n = 8
 * 输出：11
 * <p>
 * 输入：n = 13
 * 输出：101
 * <p>
 * 1 <= n <= 10^8
 */
public class Problem866 {
    public static void main(String[] args) {
        Problem866 problem866 = new Problem866();
        int n = 13;
        System.out.println(problem866.primePalindrome(n));
        System.out.println(problem866.primePalindrome2(n));
    }

    /**
     * 暴力
     * 时间复杂度O(n*n^(1/2)*logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int primePalindrome(int n) {
        while (true) {
            if (isPrime(n) && isPalindrome(n)) {
                return n;
            }

            n++;
        }
    }

    /**
     * 暴力优化
     * 判断回文数的时间复杂度O(logn)小于判断质数的时间复杂度O(n^(1/2))，所以先判断回文数，再判断质数，
     * 在判断回文数时，不是一个数一个数挨着判断，而是根据当前数得到大于等于当前数的下一个回文数
     * 时间复杂度O(logn*n^(1/2))，空间复杂度O(logn)
     *
     * @param n
     * @return
     */
    public int primePalindrome2(int n) {
        while (true) {
            //大于等于n的下一个回文数
            n = getNextPalindrome(n);

            if (isPrime(n)) {
                return n;
            }

            //避免大于等于n的下一个回文数为n，导致死循环的情况
            n++;
        }
    }

    /**
     * 返回大于等于n的下一个回文数
     * n的前半部分拼接反转n的前半部分得到大于等于n的下一个回文数，
     * 如果得到的回文数小于n，则n的前半部分加1再拼接反转n的前半部分，最终得到大于等于n的下一个回文数
     * 时间复杂度O(logn)，空间复杂度O(logn)
     * <p>
     * 例如：n=321，n的下一个回文数为323
     * n=123，n的下一个回文数为131
     * n=999，n的下一个回文数为1001
     *
     * @param n
     * @return
     */
    private int getNextPalindrome(int n) {
        //n的下一个回文数数组
        char[] arr = String.valueOf(n).toCharArray();

        //n的前半部分拼接反转n的前半部分得到n的下一个回文数
        for (int i = arr.length / 2; i < arr.length; i++) {
            arr[i] = arr[arr.length - 1 - i];
        }

        //n的下一个回文数
        int nextPalindrome = Integer.parseInt(String.valueOf(arr));

        //nextPalindrome大于等于n，则nextPalindrome为大于等于n的下一个回文数
        if (nextPalindrome >= n) {
            return nextPalindrome;
        } else {
            //nextPalindrome小于n，则nextPalindrome为n的前半部分加1再拼接反转n的前半部分

            //n中间位置的下标索引
            int index;

            if (arr.length % 2 == 0) {
                index = arr.length / 2 - 1;
            } else {
                index = arr.length / 2;
            }

            while (index >= 0 && arr[index] == '9') {
                arr[index] = '0';
                index--;
            }

            //n的前半部分都为9的特殊情况，例如：n=999，n的下一个回文数为1001
            if (index == -1) {
                //n的下一个回文数数组，长度为arr.length+1
                char[] arr2 = new char[arr.length + 1];

                //首尾为1
                arr2[0] = '1';
                arr2[arr2.length - 1] = '1';

                //中间都为0
                //注意：当前不能省略，因为char数组默认值为空，转换为int时会报错
                for (int i = 1; i < arr2.length - 1; i++) {
                    arr2[i] = '0';
                }

                return Integer.parseInt(new String(arr2));
            } else {
                //当前位加1
                arr[index]++;

                //拼接反转n的前半部分得到n的下一个回文数
                for (int i = arr.length / 2; i < arr.length; i++) {
                    arr[i] = arr[arr.length - 1 - i];
                }

                return Integer.parseInt(new String(arr));
            }
        }
    }

    /**
     * 判断正整数n是否是质数
     * 时间复杂度O(n^(1/2))，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    private boolean isPrime(int n) {
        //1不是质数
        if (n == 1) {
            return false;
        }

        //只要n能够整除[2,n^(1/2)]中任意一个数，则n不是质数
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断正整数n是否是回文数
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    private boolean isPalindrome(int n) {
        //n反转之后的值
        int reverse = 0;
        int num = n;

        while (num != 0) {
            //回文数逆序不会溢出，不需要考虑溢出的情况
            reverse = reverse * 10 + num % 10;
            num = num / 10;
        }

        //判断反转之后是否和n相等
        return n == reverse;
    }
}
