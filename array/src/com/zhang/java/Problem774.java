package com.zhang.java;

/**
 * @Date 2025/9/12 11:24
 * @Author zsy
 * @Description 最小化去加油站的最大距离 类比Problem134、Problem871 类比Problem644 二分查找类比
 * 整数数组 stations 表示 水平数轴 上各个加油站的位置。给你一个整数 k 。
 * 请你在数轴上增设 k 个加油站，新增加油站可以位于 水平数轴 上的任意位置，而不必放在整数位置上。
 * 设 penalty() 是：增设 k 个新加油站后，相邻 两个加油站间的最大距离。
 * 请你返回 penalty() 可能的最小值。
 * 与实际答案误差在 10^-6 范围内的答案将被视作正确答案。
 * <p>
 * 输入：stations = [1,2,3,4,5,6,7,8,9,10], k = 9
 * 输出：0.50000
 * <p>
 * 输入：stations = [23,24,36,39,46,56,57,65,84,98], k = 1
 * 输出：14.00000
 * <p>
 * 10 <= stations.length <= 2000
 * 0 <= stations[i] <= 10^8
 * stations 按 严格递增 顺序排列
 * 1 <= k <= 10^6
 */
public class Problem774 {
    public static void main(String[] args) {
        Problem774 problem774 = new Problem774();
//        int[] stations = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
//        int k = 9;
        int[] stations = {23, 24, 36, 39, 46, 56, 57, 65, 84, 98};
        int k = 1;
        System.out.println(problem774.minmaxGasDist(stations, k));
    }

    /**
     * 二分查找变形，使...最大值尽可能小，就要想到二分查找
     * 对[left,right]进行二分查找，left为0，right为stations相邻元素距离的最大值，统计数组中相邻元素的距离小于等于mid插入的元素个数count，
     * 如果count大于k，则插入k个加油站的数组中相邻加油站距离的最大值的最小值在mid右边，left=mid；
     * 如果count小于等于k，则插入k个加油站的数组中相邻加油站距离的最大值的最小值在mid或mid左边，right=mid
     * 时间复杂度O(n*log(min(stations[i+1]-stations[i])))=O(n)，空间复杂度O(1)
     *
     * @param stations
     * @param k
     * @return
     */
    public double minmaxGasDist(int[] stations, int k) {
        //stations相邻元素距离的最大值
        int max = 0;

        for (int i = 0; i < stations.length - 1; i++) {
            max = Math.max(max, stations[i + 1] - stations[i]);
        }

        double left = 0;
        double right = max;
        double mid;

        //误差小于等于10^-6，则认为找到了相邻加油站距离的最大值的最小值
        while (right - left > 1e-6) {
            //注意：double类型不能使用>>来除以2
            mid = left + ((right - left) / 2);

            //数组中相邻元素的距离小于等于mid插入的元素个数
            int count = 0;

            //(stations[i+1]-stations[i])/(count+1)<=mid
            //count>=(stations[i+1]-stations[i])/mid-1
            //则stations[i]和stations[i+1]之间插入向上取整(stations[i+1]-stations[i])/mid-1个元素
            for (int i = 0; i < stations.length - 1; i++) {
                count = count + (int) Math.ceil((stations[i + 1] - stations[i]) / mid - 1);
            }

            if (count > k) {
                left = mid;
            } else {
                //注意：是找相邻元素距离的最大值的最小值，所以等于的情况下要往左边找
                right = mid;
            }
        }

        return left;
    }
}
