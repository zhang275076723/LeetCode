package com.zhang.java;

/**
 * @Date 2023/9/10 08:40
 * @Author zsy
 * @Description 第 N 个神奇数字 最大公因数和最小公倍数类比 二分查找类比Problem4、Problem287、Problem378、Problem410、Problem644、Problem658、Problem1201、Problem1482、Problem2498、CutWood、FindMaxArrayMinAfterKMinus 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem1175、Problem1201、Problem1291、Offer10、Offer49
 * 一个正整数如果能被 a 或 b 整除，那么它是神奇的。
 * 给定三个整数 n , a , b ，返回第 n 个神奇的数字。
 * 因为答案可能很大，所以返回答案 对 10^9 + 7 取模 后的值。
 * <p>
 * 输入：n = 1, a = 2, b = 3
 * 输出：2
 * <p>
 * 输入：n = 4, a = 2, b = 3
 * 输出：6
 * <p>
 * 1 <= n <= 10^9
 * 2 <= a, b <= 4 * 10^4
 */
public class Problem878 {
    private final int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        Problem878 problem878 = new Problem878();
//        int n = 4;
//        int a = 2;
//        int b = 3;
        int n = 1000000000;
        int a = 40000;
        int b = 40000;
        System.out.println(problem878.nthMagicalNumber(n, a, b));
        System.out.println(problem878.nthMagicalNumber2(n, a, b));
    }

    /**
     * 暴力，双指针
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param a
     * @param b
     * @return
     */
    public int nthMagicalNumber(int n, int a, int b) {
        if (n <= 0) {
            return -1;
        }

        int result = 0;
        //a、b分别需要乘上的指针倍数
        int i = 1;
        int j = 1;

        for (int k = 1; k <= n; k++) {
            //使用long，避免int相乘溢出
            long temp1 = (long) a * i;
            long temp2 = (long) b * j;
            long tempResult = Math.min(temp1, temp2);
            result = (int) (tempResult % MOD);

            if (temp1 == tempResult) {
                i++;
            }

            if (temp2 == tempResult) {
                j++;
            }
        }

        return result;
    }

    /**
     * 二分查找，容斥原理
     * 对[left,right]进行二分查找，left为a、b中的较小值，right为n*left，统计小于等于mid的神奇数个数count，
     * 如果count小于n，则第n个神奇数在mid右边，left=mid+1；
     * 如果count大于等于n，则则第n个丑数在mid或mid左边，right=mid
     * 小于等于n能被a整除的神奇数记为A，小于等于n能被b整除的神奇数记为B，其中|A|=n/a，|B|=n/b，
     * 则小于等于n的神奇数个数为|A∪B|=|A|+|B|-|A∩B|=n/a+n/b-n/lcm(a,b) (容斥原理)
     * 时间复杂度O(log(right-left))=O(1)，空间复杂度O(1) (left:min(a,b)，right:n*left)
     *
     * @param n
     * @param a
     * @param b
     * @return
     */
    public int nthMagicalNumber2(int n, int a, int b) {
        //使用long，避免int相乘溢出
        long left = Math.min(a, b);
        long right = (long) n * left;
        long mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //a、b的最小公倍数，使用long避免int溢出
            long lcmAb = lcm(a, b);
            //统计小于等于mid的神奇数个数count
            int count = (int) (mid / a + mid / b - mid / lcmAb);

            if (count < n) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return (int) (left % MOD);
    }

    /**
     * 得到a和b的最小公倍数
     * a和b的最小公倍数=a*b/(a和b的最大公因数)
     * 返回long，避免int相乘溢出
     * 时间复杂度O(logn)=O(log32)=O(1)，空间复杂度O(1) (n：a、b的范围，a、b都在int范围内)
     *
     * @param a
     * @param b
     * @return
     */
    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数
     * 例如：a=36，b=24
     * 36%24=12 ----> a=24，b=12
     * 24%12=0  ----> a=12，b=0
     * 当b为0时，a即为最大公因数
     * 时间复杂度O(logn)=O(log32)=O(1)，空间复杂度O(1) (n：a、b的范围，a、b都在int范围内)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        //当b为0时，a即为最大公因数
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}
