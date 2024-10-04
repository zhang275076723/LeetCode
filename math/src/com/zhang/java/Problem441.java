package com.zhang.java;

/**
 * @Date 2023/6/25 10:33
 * @Author zsy
 * @Description 排列硬币 类比Problem69、Problem367 二分查找类比Problem4、Problem287、Problem373、Problem378、Problem410、Problem644、Problem658、Problem668、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1508、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 你总共有 n 枚硬币，并计划将它们按阶梯状排列。
 * 对于一个由 k 行组成的阶梯，其第 i 行必须正好有 i 枚硬币。
 * 阶梯的最后一行 可能 是不完整的。
 * 给你一个数字 n ，计算并返回可形成 完整阶梯行 的总行数。
 * <p>
 * 输入：n = 5
 * 输出：2
 * 解释：因为第三行不完整，所以返回 2 。
 * <p>
 * 输入：n = 8
 * 输出：3
 * 解释：因为第四行不完整，所以返回 3 。
 * <p>
 * 1 <= n <= 2^31 - 1
 */
public class Problem441 {
    public static void main(String[] args) {
        Problem441 problem441 = new Problem441();
        int n = 2;
        System.out.println(problem441.arrangeCoins(n));
        System.out.println(problem441.arrangeCoins2(n));
    }

    /**
     * 数学
     * 假设共x行，1+2+3+...+x=n，x^2+x-2n=0，x=(-1+(1+8n)^(1/2))/2
     * 时间复杂度O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int arrangeCoins(int n) {
        //使用long，避免int溢出
        return (int) ((-1 + Math.sqrt(1 + 8 * (long) n)) / 2);
    }

    /**
     * 二分查找
     * 对[left,right]进行二分查找，left为1，right为n，统计前mid行放满所需的硬币个数count，
     * 如果count小于等于n，则n个硬币能够放满的行数在mid或mid右边，left=mid；
     * 如果count大于n，则n个硬币能够放满的行数在mid左边，right=mid-1
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param n
     * @return
     */
    public int arrangeCoins2(int n) {
        int left = 1;
        int right = n;
        int mid;

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            //前mid行放满所需的硬币个数
            //使用long，避免int溢出
            long count = (long) mid * (mid + 1) / 2;

            if (count > n) {
                right = mid - 1;
            } else {
                left = mid;
            }
        }

        return left;
    }
}
