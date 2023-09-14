package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/8/7 08:24
 * @Author zsy
 * @Description 直线上最多的点数 类比IsInTriangle 最大公因数和最小公倍数类比Problem1979
 * 给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。
 * 求最多有多少个点在同一条直线上。
 * <p>
 * 输入：points = [[1,1],[2,2],[3,3]]
 * 输出：3
 * <p>
 * 输入：points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
 * 输出：4
 * <p>
 * 1 <= points.length <= 300
 * points[i].length == 2
 * -10^4 <= xi, yi <= 10^4
 * points 中的所有点 互不相同
 */
public class Problem149 {
    public static void main(String[] args) {
        Problem149 problem149 = new Problem149();
        int[][] points = {{1, 1}, {3, 2}, {5, 3}, {4, 1}, {2, 3}, {1, 4}};
        System.out.println(problem149.maxPoints(points));
    }

    /**
     * 数学
     * 每次确定一个点，往后遍历其他点，如果k相同，则说明这些点在同一条直线上
     * 直线方程：y=kx+b，任意两个不同的点(x1,y1)、(x2,y2)就能确定一条直线，k=(y1-y2)/(x1-x2)，b=(x1y2-x2y1)/(x1-x2)
     * 注意：k可能为小数，作为key不精确，所以k用分数表示，分数需要进行约分，通过辗转相除法得到最大公因数
     * 时间复杂度O(n^2*logn)，空间复杂度O(n) (辗转相除法时间复杂度O(logn))
     *
     * @param points
     * @return
     */
    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        if (points.length == 1 || points.length == 2) {
            return points.length;
        }

        //最多有多少个点在同一条直线上，初始化为2，因为任意两点都能构成一条直线
        int max = 2;

        for (int i = 0; i < points.length - 1; i++) {
            //key：(x1,y1)、(x2,y2)确定直线的斜率k，避免小数精度问题，使用分数表示
            //value：k出现的次数，有多少个点都在斜率为k的直线上，value个数需要加1
            Map<String, Integer> map = new HashMap<>();
            int x1 = points[i][0];
            int y1 = points[i][1];

            for (int j = i + 1; j < points.length; j++) {
                int x2 = points[j][0];
                int y2 = points[j][1];

                int k1 = y1 - y2;
                int k2 = x1 - x2;
                //k1和k2的最大公因数
                int gcd = gcd(k1, k2);

                //除以最大公因数，这样不管分子分母是多少，相同分数最后的表示形式都一样
                k1 = k1 / gcd;
                k2 = k2 / gcd;

                //k可能为小数，作为key不精确，所以用分数表示
                String key = k1 + "/" + k2;
                map.put(key, map.getOrDefault(key, 0) + 1);
            }

            //统计(x1,y1)和其他点中最多有多少个点在同一条直线上
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                max = Math.max(max, entry.getValue() + 1);
            }
        }

        return max;
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
            //a/b的余数
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    /**
     * 递归，辗转相除法得到a和b的最大公因数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(logn)=O(1) (n：a、b的范围)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd2(int a, int b) {
        //当b为0时，a即为最大公因数，直接返回
        if (b == 0) {
            return a;
        } else {
            return gcd2(b, a % b);
        }
    }
}
