package com.zhang.java;

import java.util.Arrays;
import java.util.Random;

/**
 * @Date 2023/7/1 10:09
 * @Author zsy
 * @Description 在圆内随机生成点
 * 给定圆的半径和圆心的位置，实现函数 randPoint ，在圆中产生均匀随机点。
 * 实现 Solution 类:
 * Solution(double radius, double x_center, double y_center) 用圆的半径 radius 和圆心的位置 (x_center, y_center) 初始化对象
 * randPoint() 返回圆内的一个随机点。圆周上的一点被认为在圆内。
 * 答案作为数组返回 [x, y] 。
 * <p>
 * 输入:
 * ["Solution","randPoint","randPoint","randPoint"]
 * [[1.0, 0.0, 0.0], [], [], []]
 * 输出: [null, [-0.02493, -0.38077], [0.82314, 0.38945], [0.36572, 0.17248]]
 * 解释:
 * Solution solution = new Solution(1.0, 0.0, 0.0);
 * solution.randPoint ();//返回[-0.02493，-0.38077]
 * solution.randPoint ();//返回[0.82314,0.38945]
 * solution.randPoint ();//返回[0.36572,0.17248]
 * <p>
 * 0 < radius <= 10^8
 * -10^7 <= x_center, y_center <= 10^7
 * randPoint 最多被调用 3 * 10^4 次
 */
public class Problem478 {
    public static void main(String[] args) {
        Solution solution = new Solution(1.0, 0.0, 0.0);
        //返回[-0.02493，-0.38077]
        System.out.println(Arrays.toString(solution.randPoint()));
        //返回[0.82314,0.38945]
        System.out.println(Arrays.toString(solution.randPoint()));
        //返回[0.36572,0.17248]
        System.out.println(Arrays.toString(solution.randPoint()));
    }

    static class Solution {
        private final double radius;
        private final double xCenter;
        private final double yCenter;
        private final Random random;

        public Solution(double radius, double xCenter, double yCenter) {
            this.radius = radius;
            this.xCenter = xCenter;
            this.yCenter = yCenter;
            random = new Random();
        }

        /**
         * 生成x坐标在(xCenter-radius,xCenter+radius)，y坐标在(yCenter-radius,yCenter+radius)的点，判断是否在圆内
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @return
         */
        public double[] randPoint() {
            while (true) {
                double x = random.nextDouble() * (2 * radius) + xCenter - radius;
                double y = random.nextDouble() * (2 * radius) + yCenter - radius;
                //判断生成的点是否在圆内
                if ((x - xCenter) * (x - xCenter) + (y - yCenter) * (y - yCenter) <= radius * radius) {
                    return new double[]{x, y};
                }
            }
        }
    }
}
