package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2025/1/6 08:21
 * @Author zsy
 * @Description 吃掉所有谷子的最短时间 二分查找类比
 * 一条线上有 n 只母鸡和 m 颗谷子。
 * 给定两个整数数组 hens 和 grains ，它们的大小分别为 n 和 m ，表示母鸡和谷子的初始位置。
 * 如果一只母鸡和一颗谷子在同一个位置，那么这只母鸡可以吃掉这颗谷子。
 * 吃掉一颗谷子的时间可以忽略不计。
 * 一只母鸡也可以吃掉多颗谷子。
 * 在 1 秒钟内，一只母鸡可以向左或向右移动 1 个单位。
 * 母鸡可以同时且独立地移动。
 * 如果母鸡行动得当，返回吃掉所有谷子的 最短 时间。
 * <p>
 * 输入：hens = [3,6,7], grains = [2,4,7,9]
 * 输出：2
 * 解释：
 * 母鸡吃掉所有谷子的一种方法如下：
 * - 第一只母鸡在 1 秒钟内吃掉位置 2 处的谷子。
 * - 第二只母鸡在 2 秒钟内吃掉位置 4 处的谷子。
 * - 第三只母鸡在 2 秒钟内吃掉位置 7 和 9 处的谷子。
 * 所以，需要的最长时间为2秒。
 * 可以证明，在2秒钟之前，母鸡不能吃掉所有谷子。
 * <p>
 * 输入：hens = [4,6,109,111,213,215], grains = [5,110,214]
 * 输出：1
 * 解释：
 * 母鸡吃掉所有谷子的一种方法如下：
 * - 第一只母鸡在 1 秒钟内吃掉位置 5 处的谷子。
 * - 第四只母鸡在 1 秒钟内吃掉位置 110 处的谷子。
 * - 第六只母鸡在 1 秒钟内吃掉位置 214 处的谷子。
 * - 其他母鸡不动。
 * 所以，需要的最长时间为 1 秒。
 * <p>
 * 1 <= hens.length, grains.length <= 2*10^4
 * 0 <= hens[i], grains[j] <= 10^9
 */
public class Problem2604 {
    public static void main(String[] args) {
        Problem2604 problem2604 = new Problem2604();
        int[] hens = {3, 6, 7};
        int[] grains = {2, 4, 7, 9};
//        int[] hens = {4, 6, 109, 111, 213, 215};
//        int[] grains = {5, 110, 214};
        System.out.println(problem2604.minimumTime(hens, grains));
    }

    /**
     * 排序+二分查找+双指针
     * hens和grains由小到大排序，对[left,right]进行二分查找，left为0，right为abs(hens最小值-grains最小值)+grains最大值-grains最小值，
     * 判断mid时间所有母鸡能否吃掉所有的谷物，
     * 如果mid时间所有母鸡不能吃掉所有的谷物，则所有母鸡吃掉所有的谷物的最短时间在mid右边，left=mid+1；
     * 如果mid时间所有母鸡能吃掉所有的谷物，则所有母鸡吃掉所有的谷物的最短时间在mid或mid左边，right=mid
     * 时间复杂度O(nlogn+mlogm+(m+n)*log(abs(min(hens[i])-min(grains[i]))+max(grains[i])-min(grains[i])))=O(nlogn+mlogm)，空间复杂度O(logn+logm)
     * (n=hens.length，m=grains.length)
     *
     * @param hens
     * @param grains
     * @return
     */
    public int minimumTime(int[] hens, int[] grains) {
        //hens由小到大排序
        Arrays.sort(hens);
        //grains由小到大排序
        Arrays.sort(grains);

        int left = 0;
        //右边界为最小位置的母鸡先吃最小位置的谷物，再按照谷物由小到大的顺序，挨个吃谷物
        int right = Math.abs(hens[0] - grains[0]) + grains[grains.length - 1] - grains[0];
        int mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (canEat(hens, grains, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * 双指针
     * 核心思想：当前位置的母鸡如果想吃到左边的谷物，可以先往左遍历到目标谷物，剩余时间再往右遍历到某个谷物，也可以先往右遍历到某个谷物，剩余时间恰好再往左遍历目标谷物
     * 判断time时间所有母鸡能否吃掉所有的谷物，hens和grains都已经由小到大排序，判断当前母鸡hens[i]和当前谷物grains[j]的大小关系，
     * 如果hens[i]<=grains[j]，则当前母鸡hens[i]向右最多能够吃到的谷物位置为hens[i]+time，j指针右移；
     * 如果hens[i]>grains[j]，则当前母鸡可以先往左遍历到grains[j]再往右遍历，也可以先往右遍历到某个谷物再往左遍历到grains[j]，
     * 取两者中向右能够遍历到最远谷物的情况，j指针右移，如果当前母鸡不能吃到当前谷物，则返回false
     * 遍历结束，如果grains都能访问到，则返回true；否则，返回false
     * 时间复杂度O(m+n)，空间复杂度O(1) (n=hens.length，m=grains.length)
     *
     * @param hens
     * @param grains
     * @param time
     * @return
     */
    private boolean canEat(int[] hens, int[] grains, int time) {
        int n = hens.length;
        int m = grains.length;

        int i = 0;
        int j = 0;

        while (i < n && j < m) {
            //当前母鸡
            int curHen = hens[i];
            //当前谷物
            int curGrain = grains[j];

            //当前母鸡向右吃当前谷物
            if (curHen <= curGrain) {
                //当前母鸡hens[i]向右最多能够吃到的谷物位置为hens[i]+time，j指针右移
                while (j < m && curHen + time >= grains[j]) {
                    j++;
                }

                i++;
            } else {
                //当前母鸡向左吃当前谷物

                //当前母鸡不能吃到当前谷物，则返回false
                if (curHen - curGrain > time) {
                    return false;
                }

                int j1 = j;
                int j2 = j;

                //先往左遍历到grains[j]再往右遍历
                while (j1 < m && 2 * (curHen - curGrain) + (grains[j1] - curHen) <= time) {
                    j1++;
                }

                //先往右遍历到某个谷物再往左遍历到grains[j]
                while (j2 < m && (curHen - curGrain) + 2 * (grains[j2] - curHen) <= time) {
                    j2++;
                }

                i++;
                //取两者中向右能够遍历到最远谷物的情况，j指针右移
                j = Math.max(j1, j2);
            }
        }

        return j == m;
    }
}
