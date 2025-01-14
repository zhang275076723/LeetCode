package com.zhang.java;

/**
 * @Date 2025/2/16 08:58
 * @Author zsy
 * @Description 种花问题 类比Problem849、Problem855、Problem1437
 * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。
 * 可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
 * 给你一个整数数组 flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。
 * 另有一个数 n ，能否在不打破种植规则的情况下种入 n 朵花？能则返回 true ，不能则返回 false 。
 * <p>
 * 输入：flowerbed = [1,0,0,0,1], n = 1
 * 输出：true
 * <p>
 * 输入：flowerbed = [1,0,0,0,1], n = 2
 * 输出：false
 * <p>
 * 1 <= flowerbed.length <= 2 * 10^4
 * flowerbed[i] 为 0 或 1
 * flowerbed 中不存在相邻的两朵花
 * 0 <= n <= flowerbed.length
 */
public class Problem605 {
    public static void main(String[] args) {
        Problem605 problem605 = new Problem605();
//        int[] flowerbed = {1, 0, 0, 0, 0, 1};
//        int n = 2;
//        int[] flowerbed = {1, 0, 0, 0, 1, 0, 0};
//        int n = 2;
        int[] flowerbed = {0};
        int n = 1;
        System.out.println(problem605.canPlaceFlowers(flowerbed, n));
    }

    /**
     * 双指针
     * 最近的两个1之间有a个0，则能够放置(a-1)/2个1
     * 时间复杂度O(flowerbed.length)，空间复杂度O(1)
     *
     * @param flowerbed
     * @param n
     * @return
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (flowerbed == null || flowerbed.length == 0 || n == 0) {
            return true;
        }

        //当前可以放置1的个数
        int count = 0;
        //当前为1的下标索引
        int left = 0;

        while (left < flowerbed.length && flowerbed[left] != 1) {
            left++;
        }

        //数组中元素均为0，则最多能够放置(flowerbed.length+1)/2个1
        if (left == flowerbed.length) {
            return (flowerbed.length + 1) / 2 >= n;
        }

        //起始位置到flowerbed[left]之间能够放置left/2个1
        count = count + left / 2;
        //下一个为1的下标索引
        int right = left + 1;

        while (right < flowerbed.length) {
            while (right < flowerbed.length && flowerbed[right] != 1) {
                right++;
            }

            //flowerbed[left]到末尾之间能够放置(flowerbed.length-left-1)/2个1
            if (right == flowerbed.length) {
                count = count + (flowerbed.length - left - 1) / 2;
            } else {
                //flowerbed[left]-flowerbed[right]之间能够放置(right-left-2)/2个1
                count = count + (right - left - 2) / 2;
            }

            if (count >= n) {
                return true;
            }

            left = right;
            right = left + 1;
        }

        //遍历结束，则判断可以放置1的个数是否大于等于n
        return count >= n;
    }
}
