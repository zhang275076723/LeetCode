package com.zhang.java;

/**
 * @Date 2022/11/14 10:29
 * @Author zsy
 * @Description 有效的完全平方数 类比Problem69
 * 给定一个 正整数 num ，编写一个函数，如果 num 是一个完全平方数，则返回 true ，否则返回 false 。
 * 进阶：不要 使用任何内置的库函数，如 sqrt 。
 * <p>
 * 输入：num = 16
 * 输出：true
 * <p>
 * 输入：num = 14
 * 输出：false
 * <p>
 * 1 <= num <= 2^31 - 1
 */
public class Problem367 {
    public static void main(String[] args) {
        Problem367 problem367 = new Problem367();
        int num = 14;
        System.out.println(problem367.isPerfectSquare(num));
        System.out.println(problem367.isPerfectSquare2(num));
        System.out.println(problem367.isPerfectSquare3(num));
    }

    /**
     * 暴力
     * 从1开始遍历，判断当前数的平方是否等于num
     * 时间复杂度O(n^(1/2))，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public boolean isPerfectSquare(int num) {
        //使用long，避免相乘溢出
        for (int i = 1; (long) i * i <= num; i++) {
            if (i * i == num) {
                return true;
            }
        }

        return false;
    }

    /**
     * 二分查找
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public boolean isPerfectSquare2(int num) {
        int left = 1;
        int right = num;
        int mid;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            //使用long，避免相乘int溢出
            if ((long) mid * mid == num) {
                return true;
            } else if ((long) mid * mid < num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * 牛顿迭代
     * 求y=x^2-C的零点
     * 在y=x^2-C确定一个初始点(x0,y0)，在此点做函数的切线，交x轴在函数上的点为(x1,y1)，
     * 重复此过程直至两个切线与x轴的交点x0和x1，之间的差值小于10^(-6)或10^(-7)，则认为找到函数的零点，即开根号值
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param num
     * @return
     */
    public boolean isPerfectSquare3(int num) {
        //初始值，y = x^2 + C，找零点
        int C = num;
        //第一个切线与x轴交点
        double x0 = num;
        //第二个切线与x轴交点
        double x1;

        while (true) {
            x1 = 0.5 * x0 + C / (2 * x0);

            //x0和x1两者之差小于10^(-7)，则认为两者无线接近，找到了零点
            if (Math.abs(x1 - x0) < 1e-6) {
                break;
            }

            x0 = x1;
        }

        //y = x^2 + C与x轴交点即为开根值，相乘判断num是否是完全平方数
        return (int) x1 * (int) x1 == num;
    }
}
