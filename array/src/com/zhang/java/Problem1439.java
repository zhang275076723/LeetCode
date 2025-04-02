package com.zhang.java;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/11/6 08:42
 * @Author zsy
 * @Description 有序矩阵中的第 k 个最小数组和 华为机试题 类比Problem373 类比Problem373、Problem378、Problem644、Problem668、Problem719、Problem786、Problem1508、Problem2040、Problem2386 优先队列类比 二分查找类比
 * 给你一个 m * n 的矩阵 mat，以及一个整数 k ，矩阵中的每一行都以非递减的顺序排列。
 * 你可以从每一行中选出 1 个元素形成一个数组。
 * 返回所有可能数组中的第 k 个 最小 数组和。
 * <p>
 * 输入：mat = [[1,3,11],[2,4,6]], k = 5
 * 输出：7
 * 解释：从每一行中选出一个元素，前 k 个和最小的数组分别是：
 * [1,2], [1,4], [3,2], [3,4], [1,6]。其中第 5 个的和是 7 。
 * <p>
 * 输入：mat = [[1,3,11],[2,4,6]], k = 9
 * 输出：17
 * <p>
 * 输入：mat = [[1,10,10],[1,4,5],[2,3,6]], k = 7
 * 输出：9
 * 解释：从每一行中选出一个元素，前 k 个和最小的数组分别是：
 * [1,1,2], [1,1,3], [1,4,2], [1,4,3], [1,1,6], [1,5,2], [1,5,3]。其中第 7 个的和是 9 。
 * <p>
 * 输入：mat = [[1,1,10],[2,2,9]], k = 7
 * 输出：12
 * <p>
 * m == mat.length
 * n == mat.length[i]
 * 1 <= m, n <= 40
 * 1 <= k <= min(200, n ^ m)
 * 1 <= mat[i][j] <= 5000
 * mat[i] 是一个非递减数组
 */
public class Problem1439 {
    public static void main(String[] args) {
        Problem1439 problem1439 = new Problem1439();
        int[][] mat = {{1, 10, 10}, {1, 4, 5}, {2, 3, 6}};
//        int k = 7;
//        int k = 14;
        int k = 27;
        System.out.println(problem1439.kthSmallest(mat, k));
        System.out.println(problem1439.kthSmallest2(mat, k));
    }

    /**
     * 优先队列，小根堆，多路归并排序
     * 两个数组通过小根堆找前k个最小数组和(同373题)，最后得到m个数组的前k个最小数组和
     * 时间复杂度O(mklogn)，空间复杂度O(n) (m=mat.length，n=mat[0].length)
     *
     * @param mat
     * @param k
     * @return
     */
    public int kthSmallest(int[][] mat, int k) {
        //mat[0]-mat[i]的前k个最小数组和
        int[] result = mat[0];

        for (int i = 1; i < mat.length; i++) {
            result = getMinKSumArr(result, mat[i], k);
        }

        return result[k - 1];
    }

    /**
     * 二分查找+双指针
     * 两个数组通过二分查找+双指针找前k个最小数组和(同373题)，最后得到m个数组的前k个最小数组和
     * 时间复杂度O(m(m+n+klogk))，空间复杂度O(k) (m=mat.length，n=mat[0].length)
     *
     * @param mat
     * @param k
     * @return
     */
    public int kthSmallest2(int[][] mat, int k) {
        //mat[0]-mat[i]的前k个最小数组和
        int[] result = mat[0];

        for (int i = 1; i < mat.length; i++) {
            result = getMinKSumArr2(result, mat[i], k);
        }

        return result[k - 1];
    }

    /**
     * 通过小根堆获取nums1和nums2的前k个最小数组和
     * 注意：nums1和nums2最多只有前m*n个最小数组和，即k不能超过m*n
     * 时间复杂度O(klogm)，空间复杂度O(m) (m=nums1.length)
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    private int[] getMinKSumArr(int[] nums1, int[] nums2, int k) {
        //小根堆，arr[0]：nums1中下标索引，arr[1]：nums2中下标索引，arr[2]：nums1[arr[0]]+nums2[arr[1]]
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //nums1[i]+nums2[j]由小到大排序
                return arr1[2] - arr2[2];
            }
        });

        //nums1中每个下标索引i和nums2中下标索引0组成的数组加入小根堆
        for (int i = 0; i < nums1.length; i++) {
            priorityQueue.offer(new int[]{i, 0, nums1[i] + nums2[0]});
        }

        //注意：nums1和nums2最多只有前m*n个最小数组和，即k不能超过m*n
        k = Math.min(k, nums1.length * nums2.length);

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            int[] arr = priorityQueue.poll();
            result[i] = arr[2];

            //arr[1]后面还有nums2的下标索引，arr[0]和arr[1]的下一个下标索引组成的数组加入小根堆
            if (arr[1] + 1 < nums2.length) {
                priorityQueue.offer(new int[]{arr[0], arr[1] + 1, nums1[arr[0]] + nums2[arr[1] + 1]});
            }
        }

        return result;
    }

    /**
     * 对[left,right]进行二分查找，left为nums1最小值+nums2最小值，right为nums1最大值+nums2最大值，统计nums1[i]+nums2[j]小于等于mid的个数count，
     * 如果count小于k，则第k小nums1[i]+nums2[j]在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小nums1[i]+nums2[j]在mid或mid左边，right=mid
     * 当left==right时，则找到第k小nums1[i]+nums2[j]的sum，先将小于sum的nums1[i]+nums2[j]加入结果集合，
     * 再根据结果集合中元素的个数，再将等于sum的nums1[i]+nums2[j]加入结果集合，最终得到有k个元素的结果集合
     * 注意：不能一次性将小于等于sum的nums1[i]+nums2[j]一起加入结果集合，因为小于sum的nums1[i]+nums2[j]一定是前k小元素，
     * 但加上全部等于sum的nums1[i]+nums2[j]有可能大于前k小元素，所以只需要加上分部等于sum的nums1[i]+nums2[j]即得到前k小元素
     * 注意：nums1和nums2最多只有前m*n个最小数组和，即k不能超过m*n
     * 时间复杂度O((m+n)*log((nums1[m-1]+nums2[n-1])-(nums1[0]+nums2[0]))+k+klogk)=O(m+n+klogk)，空间复杂度O(k) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    private int[] getMinKSumArr2(int[] nums1, int[] nums2, int k) {
        int left = nums1[0] + nums2[0];
        int right = nums1[nums1.length - 1] + nums2[nums2.length - 1];
        int mid;

        //注意：nums1和nums2最多只有前m*n个最小数组和，即k不能超过m*n
        k = Math.min(k, nums1.length * nums2.length);

        while (left < right) {
            mid = left + ((right - left) >> 1);

            if (getLessEqualThanNumCount(nums1, nums2, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        int[] result = new int[k];
        int index = 0;
        //第k小nums1[i]+nums2[j]
        int sum = left;

        //将小于sum的nums1[i]+nums2[j]加入结果集合
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                if (nums1[i] + nums2[j] < sum) {
                    result[index] = nums1[i] + nums2[j];
                    index++;
                } else {
                    break;
                }
            }
        }

        //再将等于sum的nums1[i]+nums2[j]加入结果集合，最终得到有k个元素的结果集合
        while (index < k) {
            result[index] = sum;
            index++;
        }

        //注意：得到的result无序，需要nums1[i]+nums2[j]前k小元素由小到大排序，用于下一次两个数组求前k小数组和
        Arrays.sort(result);

        return result;
    }

    /**
     * 双指针获取nums1[i]+nums2[j]小于等于num的元素个数
     * nums1和nums2选择到的元素下标索引(i,j)作为二维数组，从左下往右上移动，根据num将二维数组分为左上和右下两部分，
     * 左边都小于num，右边都大于num
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param num
     * @return
     */
    private int getLessEqualThanNumCount(int[] nums1, int[] nums2, int num) {
        int count = 0;
        int i = nums1.length - 1;
        int j = 0;

        //从左下往右上遍历
        while (i >= 0 && j < nums2.length) {
            while (i >= 0 && nums1[i] + nums2[j] > num) {
                i--;
            }

            //nums1[0]+nums[j]到nums1[i]+nums2[j]都小于等于num
            count = count + i + 1;
            j++;
        }

        return count;
    }
}
