package com.zhang.java;

/**
 * @Date 2023/9/7 08:00
 * @Author zsy
 * @Description 丑数 III 三指针类比Problem75、Problem264、Problem313、Problem324、Offer49 最大公因数和最小公倍数类比 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem441、Problem644、Problem658、Problem668、Problem719、Problem786、Problem878、Problem1482、Problem1508、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus 各种数类比Problem202、Problem204、Problem263、Problem264、Problem306、Problem313、Problem507、Problem509、Problem728、Problem842、Problem878、Problem1175、Problem1291、Offer10、Offer49
 * 给你四个整数：n 、a 、b 、c ，请你设计一个算法来找出第 n 个丑数。
 * 丑数是可以被 a 或 b 或 c 整除的 正整数 。
 * <p>
 * 输入：n = 3, a = 2, b = 3, c = 5
 * 输出：4
 * 解释：丑数序列为 2, 3, 4, 5, 6, 8, 9, 10... 其中第 3 个是 4。
 * <p>
 * 输入：n = 4, a = 2, b = 3, c = 4
 * 输出：6
 * 解释：丑数序列为 2, 3, 4, 6, 8, 9, 10, 12... 其中第 4 个是 6。
 * <p>
 * 输入：n = 5, a = 2, b = 11, c = 13
 * 输出：10
 * 解释：丑数序列为 2, 4, 6, 8, 10, 11, 12, 13... 其中第 5 个是 10。
 * <p>
 * 输入：n = 1000000000, a = 2, b = 217983653, c = 336916467
 * 输出：1999999984
 * <p>
 * 1 <= n, a, b, c <= 10^9
 * 1 <= a * b * c <= 10^18
 * 本题结果在 [1, 2 * 10^9] 的范围内
 */
public class Problem1201 {
    public static void main(String[] args) {
        Problem1201 problem1201 = new Problem1201();
//        int n = 3;
//        int a = 2;
//        int b = 3;
//        int c = 5;
        int n = 1000000000;
        int a = 2;
        int b = 217983653;
        int c = 336916467;
        System.out.println(problem1201.nthUglyNumber(n, a, b, c));
        System.out.println(problem1201.nthUglyNumber2(n, a, b, c));
    }

    /**
     * 暴力，三指针
     * 注意：这里的丑数是能被a或b或c整除的数，而不是只含有因子a、b、c的丑数
     * 例如：a=2，b=3，c=5，8在这里是丑数，因为8能整除2；但在其他地方，8不是丑数，因为8/2=4，4不是因子2、3、5
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int nthUglyNumber(int n, int a, int b, int c) {
        if (n <= 0) {
            return -1;
        }

        int result = 0;
        //a、b、c需要乘上的倍数指针
        int i = 1;
        int j = 1;
        int k = 1;

        //通过三指针乘以a、b、c得到下一个丑数
        for (int m = 1; m <= n; m++) {
            //使用long，避免int相乘溢出
            long temp1 = (long) a * i;
            long temp2 = (long) b * j;
            long temp3 = (long) c * k;
            //三者中最小值作为当前丑数
            long curUglyNum = Math.min(temp1, Math.min(temp2, temp3));
            result = (int) curUglyNum;

            //i指针后移
            if (curUglyNum == temp1) {
                i++;
            }

            //j指针后移
            if (curUglyNum == temp2) {
                j++;
            }

            //k指针后移
            if (curUglyNum == temp3) {
                k++;
            }
        }

        return result;
    }

    /**
     * 二分查找，容斥原理
     * 注意：这里的丑数是能被a或b或c整除的数，而不是只含有因子a、b、c的丑数
     * 对[left,right]进行二分查找，left为a、b、c中的最小值，right为n*left，统计小于等于mid的丑数个数count，
     * 如果count小于n，则第n个丑数在mid右边，left=mid+1；
     * 如果count大于等于n，则则第n个丑数在mid或mid左边，right=mid
     * 小于等于n能被a整除的丑数记为A，小于等于n能被b整除的丑数记为B，小于等于n能被c整除的丑数记为C，
     * 其中|A|=n/a，|B|=n/b，|C|=n/c，则小于等于n的丑数个数为|A∪B∪C|=|A|+|B|+|C|-|A∩B|-|B∩C|-|A∩C|+|A∩B∩C|=
     * n/a+n/b+n/c-n/lcm(a,b)-n/lcm(b,c)-n/lcm(a,c)+n/lcm(a,b,c) (容斥原理)
     * 时间复杂度O(log(right-left))=O(1)，空间复杂度O(1) (left:min(a,b,c)，right:n*left)
     *
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int nthUglyNumber2(int n, int a, int b, int c) {
        if (n <= 0) {
            return -1;
        }

        //二分查找左边界，初始化为a、b、c中的最小值
        int left = Math.min(a, Math.min(b, c));
        //二分查找右边界，初始化为n*left
        int right = n * left;
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //统计小于等于mid的丑数个数count
            int count = 0;
            //a、b的最小公倍数，使用long避免int溢出
            long lcmAb = lcm(a, b);
            //b、c的最小公倍数，使用long避免int溢出
            long lcmBc = lcm(b, c);
            //a、c的最小公倍数，使用long避免int溢出
            long lcmAc = lcm(a, c);
            //a、b、c的最小公倍数，使用long避免int溢出
            long lcmAbc = lcm(a, lcm(b, c));

            count = (int) (mid / a + mid / b + mid / c - mid / lcmAb - mid / lcmBc - mid / lcmAc + mid / lcmAbc);

            if (count < n) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * 得到a和b的最小公倍数 (lcm：least common multiple)
     * a和b的最小公倍数=a*b/(a和b的最大公因数)
     * 时间复杂度O(logn)=O(log32)=O(1)，空间复杂度O(1) (n：a、b的范围，a、b都在int范围内)
     *
     * @param a
     * @param b
     * @return
     */
    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数 (gcd：greatest common divisor)
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
    private long gcd(long a, long b) {
        //当b为0时，a即为最大公因数
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }
}
