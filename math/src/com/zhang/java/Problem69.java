package com.zhang.java;

/**
 * @Date 2022/6/22 9:40
 * @Author zsy
 * @Description x 的平方根
 * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
 * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
 * 注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
 * <p>
 * 输入：x = 4
 * 输出：2
 * <p>
 * 输入：x = 8
 * 输出：2
 * 解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
 * <p>
 * 0 <= x <= 2^31 - 1
 */
public class Problem69 {
    public static void main(String[] args) {
        Problem69 problem69 = new Problem69();
        int x = 8;
        System.out.println(problem69.mySqrt(x));
        System.out.println(problem69.mySqrt2(x));
        System.out.println(problem69.mySqrt3(x));
    }

    /**
     * 使用对数函数ln
     * x^(1/2) = e^(1/2*lnx)
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }

        int result = (int) Math.exp(0.5 * Math.log(x));

        //因为浮点数的指数和对数运算存在误差，所以需要判断result和result+1哪个才是正确结果
        //使用long，避免溢出
        if ((long) (result + 1) * (result + 1) <= x) {
            return result + 1;
        } else {
            return result;
        }
    }

    /**
     * 二分查找
     * 时间复杂度O(logx)，空间复杂度O(1)
     *
     * @param x
     * @return
     */
    public int mySqrt2(int x) {
        if (x == 0) {
            return 0;
        }

        int left = 0;
        int right = x;
        int mid;
        int result = 0;

        while (left <= right) {
            mid = left + ((right - left) >> 1);

            if ((long) mid * mid <= x) {
                left = mid + 1;
                result = mid;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    /**
     * 牛顿迭代
     * 求y=x^2-C的零点
     * 给定一个初始点(x0,y0)，在此点做函数的切线，交x轴在函数上的点为(x1,y1)，
     * 重复此过程直至逼近函数与x轴的交点，差值小于10^(-6)或10^(-7)，即认为该点为函数的零点
     * 时间复杂度O(logx)，空间复杂度O(1)
     *
     * @param x
     * @return
     */
    public int mySqrt3(int x) {
        if (x == 0) {
            return 0;
        }

        double C = x;
        double x0 = x;
        double x1;

        while (true) {
            x1 = x0 * 0.5 + C / (x0 * 2);

            //两者之差小于10^(-7)，则认为找到了零点
            if (Math.abs(x1 - x0) < 1e-7) {
                break;
            }

            x0 = x1;
        }

        return (int) x1;
    }
}
