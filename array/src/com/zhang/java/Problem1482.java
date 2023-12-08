package com.zhang.java;

/**
 * @Date 2022/9/8 16:25
 * @Author zsy
 * @Description 制作 m 束花所需的最少天数 二分查找类比Problem4、Problem287、Problem378、Problem410、Problem644、Problem658、Problem1201、Problem1723、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 给你一个整数数组 bloomDay，以及两个整数 m 和 k 。
 * 现需要制作 m 束花。制作花束时，需要使用花园中 相邻的 k 朵花 。
 * 花园中有 n 朵花，第 i 朵花会在 bloomDay[i] 时盛开，恰好 可以用于 一束 花中。
 * 请你返回从花园中摘 m 束花需要等待的最少的天数。如果不能摘到 m 束花则返回 -1 。
 * <p>
 * 输入：bloomDay = [1,10,3,10,2], m = 3, k = 1
 * 输出：3
 * 解释：让我们一起观察这三天的花开过程，x 表示花开，而 _ 表示花还未开。
 * 现在需要制作 3 束花，每束只需要 1 朵。
 * 1 天后：[x, _, _, _, _]   // 只能制作 1 束花
 * 2 天后：[x, _, _, _, x]   // 只能制作 2 束花
 * 3 天后：[x, _, x, _, x]   // 可以制作 3 束花，答案为 3
 * <p>
 * 输入：bloomDay = [1,10,3,10,2], m = 3, k = 2
 * 输出：-1
 * 解释：要制作 3 束花，每束需要 2 朵花，也就是一共需要 6 朵花。而花园中只有 5 朵花，无法满足制作要求，返回 -1 。
 * <p>
 * 输入：bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
 * 输出：12
 * 解释：要制作 2 束花，每束需要 3 朵。
 * 花园在 7 天后和 12 天后的情况如下：
 * 7 天后：[x, x, x, x, _, x, x]
 * 可以用前 3 朵盛开的花制作第一束花。但不能使用后 3 朵盛开的花，因为它们不相邻。
 * 12 天后：[x, x, x, x, x, x, x]
 * 显然，我们可以用不同的方式制作两束花。
 * <p>
 * 输入：bloomDay = [1000000000,1000000000], m = 1, k = 1
 * 输出：1000000000
 * 解释：需要等 1000000000 天才能采到花来制作花束
 * <p>
 * 输入：bloomDay = [1,10,2,9,3,8,4,7,5,6], m = 4, k = 2
 * 输出：9
 * <p>
 * bloomDay.length == n
 * 1 <= n <= 10^5
 * 1 <= bloomDay[i] <= 10^9
 * 1 <= m <= 10^6
 * 1 <= k <= n
 */
public class Problem1482 {
    public static void main(String[] args) {
        Problem1482 problem1482 = new Problem1482();
        int[] bloomDay = {1, 10, 2, 9, 3, 8, 4, 7, 5, 6};
        int m = 4;
        int k = 2;
        System.out.println(problem1482.minDays(bloomDay, m, k));
    }

    /**
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为数组中最小值，right为数组中最大值，统计在mid那天能够制作花的数量flower，
     * 如果flower小于m，则制作m朵花的最小天数在mid右边，left=mid+1；
     * 如果flower大于等于m，则制作m朵花的最小天数在mid或mid左边，right=mid
     * 时间复杂度O(n*log(right-left))=O(n)，空间复杂度O(1) (n:bloomDay长度，left:bloomDay中最小值，right:bloomDay中最大值)
     *
     * @param bloomDay
     * @param m
     * @param k
     * @return
     */
    public int minDays(int[] bloomDay, int m, int k) {
        //至少需要m*k朵花，使用long避免m*k在int范围内溢出
        if (bloomDay == null || bloomDay.length == 0 || (long) m * k > bloomDay.length) {
            return -1;
        }

        //二分查找左边界，初始化为数组中最小天数
        int left = bloomDay[0];
        //二分查找右边界，初始化为数组中最大天数
        int right = bloomDay[0];
        int mid;

        for (int day : bloomDay) {
            left = Math.min(left, day);
            right = Math.max(right, day);
        }

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //当前天数mid能够制作花的数量
            int flower = 0;
            //当前天数mid中连续盛开花的数量
            int count = 0;

            for (int day : bloomDay) {
                if (day <= mid) {
                    count++;
                } else {
                    count = 0;
                }

                //count等于k，则能够制作一朵花
                if (count == k) {
                    flower++;
                    count = 0;
                }
            }

            if (flower < m) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
