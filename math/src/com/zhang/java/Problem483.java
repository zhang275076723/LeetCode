package com.zhang.java;

/**
 * @Date 2024/4/12 08:23
 * @Author zsy
 * @Description 最小好进制 类比Problem1689、Problem2396
 * 以字符串的形式给出 n , 以字符串的形式返回 n 的最小 好进制  。
 * 如果 n 的  k(k>=2) 进制数的所有数位全为1，则称 k(k>=2) 是 n 的一个 好进制 。
 * <p>
 * 输入：n = "13"
 * 输出："3"
 * 解释：13 的 3 进制是 111。
 * <p>
 * 输入：n = "4681"
 * 输出："8"
 * 解释：4681 的 8 进制是 11111。
 * <p>
 * 输入：n = "1000000000000000000"
 * 输出："999999999999999999"
 * 解释：1000000000000000000 的 999999999999999999 进制是 11。
 * <p>
 * n 的取值范围是 [3, 10^18]
 * n 没有前导 0
 */
public class Problem483 {
    public static void main(String[] args) {
        Problem483 problem483 = new Problem483();
//        //8
//        String n = "4681";
//        //502
//        String n = "16035713712910627";
        //686286299
        String n = "470988884881403701";
        System.out.println(problem483.smallestGoodBase(n));
    }

    /**
     * 数学
     * 假设k进制m位全为1的值为n，即1+k+k^2+...+k^(m-1)=n，得到k^(m-1)<n，
     * 根据二项式定理1+C(1,m-1)*k+...+k^(m-1)=(1+k)^(m-1)，得到(1+k)^(m-1)>1+k+k^2+...+k^(m-1)=n，即(k+1)^(m-1)>n，
     * 综上，得到k^(m-1)<n<(k+1)^(m-1)，即k<n^(1/(m-1))<k+1，则对于确定的m，就能确定唯一的k=n^(1/(m-1))
     * 1+k+k^2+...+k^(m-1)=n，得到(k^m-1)/(k-1)=n，当k=2时，2^m-1=n，m=log(n+1)/log2，即得到m的上限，m的下限为n-1进制2位
     * 同时要得到最小的k进制，需要m越大越好，所以m从上限开始遍历，判断当前m位k进制全1的值是否为n，如果为n，则直接返回当前k
     * 注意：n的n-1进制数为11，m遍历结束没有符合的k，则返回n-1进制
     * 时间复杂度O((logn)^2)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public String smallestGoodBase(String n) {
        //字符串n转为整形long
        long num = Long.parseLong(n);

        //m位k进制全1的值是否为num
        //k=2时，得到m的上限，m=log(num+1)/log2，换底公式
        for (int m = (int) (Math.log(num + 1) / Math.log(2)); m >= 2; m--) {
            //k<num^(1/(m-1))<k+1，得到k=num^(1/(m-1))
            int k = (int) Math.pow(num, 1.0 / (m - 1));

            //k进制小于等于1，则不能构成全1的值，直接进行下次循环
            if (k <= 1) {
                continue;
            }

//            //注意：不能通过Math.pow()计算result，因为Math.pow()中参数为double类型，有精度问题
//            //1+k+k^2+...+k^(m-1)=result，result=(k^m-1)/(k-1)
//            long result = (long) (Math.pow(k, m) - 1) / (k - 1);

            //使用long，避免int相乘溢出
            long result = 0;
            long temp = 1;

            //1+k+k^2+...+k^(m-1)=result
            //注意：不能通过result=(k^m-1)/(k-1)求result，只能通过相加求result，避免long溢出
            for (int i = 0; i < m; i++) {
                result = result + temp;
                temp = temp * k;
            }

            //m位k进制全1的值等于num，则返回k进制
            if (result == num) {
                return k + "";
            }
        }

        //num的num-1进制数为11，m遍历结束没有符合的k，则返回num-1进制
        return String.valueOf(num - 1);
    }
}
