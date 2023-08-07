package com.zhang.java;

/**
 * @Date 2023/5/16 08:33
 * @Author zsy
 * @Description 判断一个点是否在三角形内 字节面试题 腾讯面试题 网易面试题 美团面试题 阿里面试题 类比Problem149
 * 在二维坐标系中，所有的值都是double类型，那么一个三角形可以由3个点来代表，
 * 给定3个点代表的三角形，再给定一个点(x, y)，判断(x, y)是否在三角形中。
 * <p>
 * 输入：p1 = [-1.00,0.00], p2 = [1.50,3.50], p3 = [2.73,-3.12], p = [1.23,0.23]
 * 输出：true
 * <p>
 * −2*10^10 ⩽ 输入的所有数值 ⩽ 2*10^10
 */
public class IsInTriangle {
    public static void main(String[] args) {
        IsInTriangle isInTriangle = new IsInTriangle();
        Point A = new Point(-1, 0.0);
        Point B = new Point(1.5, 3.5);
        Point C = new Point(2.73, -3.12);
        Point O = new Point(1.23, 0.23);
        System.out.println(isInTriangle.isInTriangle(A, B, C, O));
        System.out.println(isInTriangle.isInTriangle2(A, B, C, O));
    }

    /**
     * 一个点在三角形内部，则三角形面积等于该点将三角形分成的三个小三角形面试之和
     * 三角形面积公式：S=(p*(p-a)*(p-b)*(p-c))^(1/2) (p=(a+b+c)/2，a、b、c为三角形的边长)
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param A
     * @param B
     * @param C
     * @param O
     * @return
     */
    public boolean isInTriangle(Point A, Point B, Point C, Point O) {
        double s = getTriangleArea(A, B, C);
        double s1 = getTriangleArea(A, B, O);
        double s2 = getTriangleArea(B, C, O);
        double s3 = getTriangleArea(C, A, O);
        //因为double有精度问题，所以两者误差在10^(-5)认为两者相等
        return Math.abs(s - (s1 + s2 + s3)) < 1e-5;
    }

    /**
     * 向量叉乘(注意叉乘的前后顺序)
     * 两个点A(x1,y1),B(x2,y2)，要判断的点O(x,y)，通过向量AO(x-x1,y-y1),AB(x2-x1,y2-y1)叉乘判断O和AB的位置关系，
     * AO×AB结果大于0，则O在AB顺时针方向；AO×AB结果小于0，则O在AB逆时针方向；AO×AB结果等于0，则O在AB线上
     * 点O(x,y)对于一个三角形的任意一条边AB向量叉乘AO×AB都大于0或都小于0，即点O在三角形内
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param A
     * @param B
     * @param C
     * @param O
     * @return
     */
    public boolean isInTriangle2(Point A, Point B, Point C, Point O) {
        //O和三角形3条边进行叉乘结果大于0的次数，如果小于0，则次数减1
        int count = 0;
        //AO×AB结果
        double product1 = product(A, B, O);
        //BO×BC结果
        double product2 = product(B, C, O);
        //CO×CA结果
        double product3 = product(C, A, O);

        if (product1 > 0) {
            count++;
        } else if (product1 < 0) {
            count--;
        }

        if (product2 > 0) {
            count++;
        } else if (product2 < 0) {
            count--;
        }

        if (product3 > 0) {
            count++;
        } else if (product3 < 0) {
            count--;
        }

        //叉乘结果都大于0或都小于0，即p在三角形内
        return count == 3 || count == -3;
    }

    /**
     * 根据三角形三边得到三角形面积
     * 三角形面积公式：S=(p*(p-a)*(p-b)*(p-c))^(1/2) (p=(a+b+c)/2，a、b、c为三角形的边长)
     *
     * @param A
     * @param B
     * @param C
     * @return
     */
    private Double getTriangleArea(Point A, Point B, Point C) {
        double a = Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
        double b = Math.sqrt(Math.pow(B.x - C.x, 2) + Math.pow(B.y - C.y, 2));
        double c = Math.sqrt(Math.pow(C.x - A.x, 2) + Math.pow(C.y - A.y, 2));
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    /**
     * 向量AO和AB的叉乘(注意叉乘的前后顺序)
     * 点A(x1,y1),B(x2,y2),O(x,y)
     * 向量AO(x-x1,y-y1),AB(x2-x1,y2-y1)
     * AO×AB=(x-x1)*(y2-y1)-(y-y1)*(x2-x1)
     *
     * @param A
     * @param B
     * @param O
     * @return
     */
    private double product(Point A, Point B, Point O) {
        return (O.x - A.x) * (B.y - A.y) - (O.y - A.y) * (B.x - A.x);
    }

    /**
     * 二维坐标系中的点
     */
    private static class Point {
        //横轴
        private double x;
        //纵轴
        private double y;

        public Point() {
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
