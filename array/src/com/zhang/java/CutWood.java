package com.zhang.java;

/**
 * @Date 2023/5/14 09:14
 * @Author zsy
 * @Description 木头切割问题 字节面试题 二分查找类比Problem4、Problem287、Problem378、Problem410、Problem658、Problem1482、FindMaxArrayMinAfterKMinus
 * 给定长度为n的数组，每个元素代表一个木头的长度，木头可以任意截断，
 * 从这堆木头中截出至少k个相同长度的木块，每个木块长度为m，求m的最大值。
 * <p>
 * 输入：arr = [4,7,2,10,5], k = 5
 * 输出：4
 * 解释：
 * arr[0]：切割为4
 * arr[1]：切割为4、3
 * arr[2]：切割为2
 * arr[3]：切割为4、4、2
 * arr[4]：切割为4、1
 * 所以，最多可以把它分成k=5段长度为m=4的木头
 */
public class CutWood {
    public static void main(String[] args) {
        CutWood cutWood = new CutWood();
        int[] arr = {4, 7, 2, 10, 5};
        int k = 5;
        System.out.println(cutWood.cut(arr, k));
        System.out.println(cutWood.cut2(arr, k));
    }

    /**
     * 暴力
     * m的范围为[1,maxLen]，m从小到大遍历，判断能否截出k个长度为m的木块
     * 时间复杂度O(n*maxLen)，空间复杂度O(1) (maxLen为数组中的最大值)
     *
     * @param arr
     * @param k
     * @return
     */
    public int cut(int[] arr, int k) {
        //数组中的最大值
        int maxLen = arr[0];

        for (int i = 0; i < arr.length; i++) {
            maxLen = Math.max(maxLen, arr[i]);
        }

        int m = 1;

        for (int i = 1; i <= maxLen; i++) {
            //截出长度为i的木块数量
            int count = 0;

            for (int j = 0; j < arr.length; j++) {
                count = count + arr[j] / i;
            }

            //count大于等于k，能够截出k个长度为i的木块，更新m
            if (count >= k) {
                m = i;
            }
        }

        return m;
    }

    /**
     * 二分查找变形，使...尽可能大，就要想到二分查找
     * 对[left,right]进行二分查找，left为1，right为数组中最大值，统计数组中能够截出长度为mid的木块数量count，
     * 如果count大于等于k，则能够截出k个长度为m的木块的最大值在mid或mid右边，left=mid；
     * 如果count小于k，则能够截出k个长度为m的木块的最大值在mid左边，right=mid-1
     * 时间复杂度O(n*log(maxLen))=O(n)，空间复杂度O(1) (maxLen为数组中的最大值)
     *
     * @param arr
     * @param k
     * @return
     */
    public int cut2(int[] arr, int k) {
        //1作为左边界
        int left = 1;
        //数组中最大值作为右边界
        int right = arr[0];
        int mid;

        for (int i = 0; i < arr.length; i++) {
            right = Math.max(right, arr[i]);
        }

        while (left < right) {
            //二分往右偏移，因为转移条件是right=mid-1，如果转移条件是left=mid+1，则二分要往左边偏移
            mid = left + ((right - left) >> 1) + 1;
            //截出长度为mid的木块数量
            int count = 0;

            for (int i = 0; i < arr.length; i++) {
                count = count + arr[i] / mid;
            }

            if (count >= k) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}
