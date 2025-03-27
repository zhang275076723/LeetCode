package com.zhang.java;

/**
 * @Date 2025/1/7 08:40
 * @Author zsy
 * @Description 单面值组合的第 K 小金额 容斥原理类比Problem878、Problem1201、Problem2513 二分查找类比
 * 给你一个整数数组 coins 表示不同面额的硬币，另给你一个整数 k 。
 * 你有无限量的每种面额的硬币。
 * 但是，你 不能 组合使用不同面额的硬币。
 * 返回使用这些硬币能制造的 第 kth 小 金额。
 * <p>
 * 输入： coins = [3,6,9], k = 3
 * 输出： 9
 * 解释：给定的硬币可以制造以下金额：
 * 3元硬币产生3的倍数：3, 6, 9, 12, 15等。
 * 6元硬币产生6的倍数：6, 12, 18, 24等。
 * 9元硬币产生9的倍数：9, 18, 27, 36等。
 * 所有硬币合起来可以产生：3, 6, 9, 12, 15等。
 * <p>
 * 输入：coins = [5,2], k = 7
 * 输出：12
 * 解释：给定的硬币可以制造以下金额：
 * 5元硬币产生5的倍数：5, 10, 15, 20等。
 * 2元硬币产生2的倍数：2, 4, 6, 8, 10, 12等。
 * 所有硬币合起来可以产生：2, 4, 5, 6, 8, 10, 12, 14, 15等。
 * <p>
 * 1 <= coins.length <= 15
 * 1 <= coins[i] <= 25
 * 1 <= k <= 2 * 10^9
 * coins 包含两两不同的整数。
 */
public class Problem3116 {
    public static void main(String[] args) {
        Problem3116 problem3116 = new Problem3116();
//        int[] coins = {3, 6, 9};
//        int k = 3;
        int[] coins = {5, 2};
        int k = 7;
        System.out.println(problem3116.findKthSmallest(coins, k));
        System.out.println(problem3116.findKthSmallest2(coins, k));
    }

    /**
     * 暴力，多指针
     * 时间复杂度O(kn)，空间复杂度O(n)
     *
     * @param coins
     * @param k
     * @return
     */
    public long findKthSmallest(int[] coins, int k) {
        //arr[i]：coins[i]乘上的数，即通过min(arr[i]*coins[i])得到下一个单面值组合的金额
        int[] arr = new int[coins.length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        long result = 0;

        for (int i = 0; i < k; i++) {
            long temp = Long.MAX_VALUE;

            for (int j = 0; j < coins.length; j++) {
                temp = Math.min(temp, (long) arr[j] * coins[j]);
            }

            result = temp;

            for (int j = 0; j < coins.length; j++) {
                if (temp == (long) arr[j] * coins[j]) {
                    arr[j]++;
                }
            }
        }

        return result;
    }

    /**
     * 二分查找+容斥原理+二进制状态压缩
     * 对[left,right]进行二分查找，left为coins最小值，right为k*left，统计单面值硬币产生的金额小于等于mid的个数count，
     * 如果count小于k，则第k小金额在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小金额在mid或mid左边，right=mid
     * 小于等于n单面值硬币a产生的金额个数记为A，小于等于n单面值硬币b产生的金额个数记为B，小于等于单面值硬币c产生的金额个数记为C，
     * 其中|A|=n/a，|B|=n/b，|C|=n/c，则小于等于n单面值硬币a或b或c产生的金额个数为|A∪B∪C|=|A|+|B|+|C|-|A∩B|-|B∩C|-|A∩C|+|A∩B∩C|=
     * n/a+n/b+n/c-n/lcm(a,b)-n/lcm(b,c)-n/lcm(a,c)+n/lcm(a,b,c) (容斥原理)
     * |A∪B∪C∪...|=|A|+|B|+|C|-|A∩B|-|B∩C|-|A∩C|+...+(-1)^(k-1)|A∩B∩...| (k为|A∩B∩...|的大小)
     * coins长度不超过15，则可以使用int表示是否选择当前硬币，第i位为0，则不选择coins[i]；第i位为1，则选择coins[i]
     * 时间复杂度O(n*2^n*log(k*min(coins[i])-min(coins[i])))=O(n*2^n*logk)，空间复杂度O(1)
     *
     * @param coins
     * @param k
     * @return
     */
    public long findKthSmallest2(int[] coins, int k) {
        int min = coins[0];

        for (int num : coins) {
            min = Math.min(min, num);
        }

        long left = min;
        long right = k * left;
        long mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (getLessEqualThanNumCount(coins, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    /**
     * 获取coins中单面值硬币产生的金额小于等于num的个数
     * 注意：由于coins长度不确定，所以通过二进制状态压缩[1,(2^coins.length)-1]得到每个状态在容斥原理中对应的|A∩...∩X∩...|
     * 时间复杂度O(n*2^n)，空间复杂度O(1)
     *
     * @param coins
     * @param num
     * @return
     */
    private long getLessEqualThanNumCount(int[] coins, long num) {
        long count = 0;

        //遍历coins中所有的二进制状态，计算当前状态i单面值硬币产生的金额小于等于num的个数
        //二进制状态0表示没有选择任何硬币，所以从二进制状态1开始遍历
        //例如：coins=[3,6,9]，i=6，二进制为110，即得到小于等于num被coins[1]并且被coins[2]整除的金额个数，
        //计算完[1,2^3-1]所有的状态，得到小于等于num被coins[0]或被coins[1]或被coins[2]整除的金额个数
        for (int i = 1; i < (1 << coins.length); i++) {
            //当前状态i选择硬币的lcm
            long lcm = 1;

            //得到当前状态i对应的lcm
            for (int j = 0; j < coins.length; j++) {
                if (((i >>> j) & 1) == 1) {
                    lcm = lcm(lcm, coins[j]);
                }
            }

            //当前状态i二进制表示中1的个数，即选择硬币种类的个数
            int bitCount = 0;
            int temp = i;

            while (temp != 0) {
                bitCount++;
                temp = temp & (temp - 1);
            }

            //|A∪B∪C∪...|=|A|+|B|+|C|-|A∩B|-|B∩C|-|A∩C|+...+(-1)^(k-1)|A∩B∩...| (k为|A∩B∩...|的大小)
            //例如：|A∩B|、|B∩C|、|A∩C|符号为负，|A∩B∩C|符号为正
            if (bitCount % 2 == 0) {
                count = count - num / lcm;
            } else {
                count = count + num / lcm;
            }
        }

        return count;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
}
