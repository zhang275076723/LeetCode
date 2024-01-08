package com.zhang.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Date 2024/1/6 08:18
 * @Author zsy
 * @Description 查找和最小的 K 对数字 优先队列类比 二分查找类比Problem4、Problem287、Problem378、Problem410、Problem644、Problem658、Problem668、Problem719、Problem786、Problem878、Problem1201、Problem1482、Problem1723、Problem2305、Problem2498、CutWood、FindMaxArrayMinAfterKMinus
 * 给定两个以 非递减顺序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。
 * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
 * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
 * <p>
 * 输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
 * 输出: [1,2],[1,4],[1,6]
 * 解释: 返回序列中的前 3 对数：
 * [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
 * <p>
 * 输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
 * 输出: [1,1],[1,1]
 * 解释: 返回序列中的前 2 对数：
 * [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
 * <p>
 * 输入: nums1 = [1,2], nums2 = [3], k = 3
 * 输出: [1,3],[2,3]
 * 解释: 也可能序列中所有的数对都被返回:[1,3],[2,3]
 * <p>
 * 1 <= nums1.length, nums2.length <= 10^5
 * -10^9 <= nums1[i], nums2[i] <= 10^9
 * nums1 和 nums2 均为 升序排列
 * 1 <= k <= 10^4
 * k <= nums1.length * nums2.length
 */
public class Problem373 {
    public static void main(String[] args) {
        Problem373 problem373 = new Problem373();
//        int[] nums1 = {1, 7, 11};
//        int[] nums2 = {2, 4, 6};
//        int k = 3;
        int[] nums1 = {-10, -4, 0, 0, 6};
        int[] nums2 = {3, 5, 6, 7, 8, 100};
        int k = 10;
        System.out.println(problem373.kSmallestPairs(nums1, nums2, k));
        System.out.println(problem373.kSmallestPairs2(nums1, nums2, k));
    }

    /**
     * 小根堆，优先队列，k路归并排序
     * 时间复杂度O(klogm)，空间复杂度O(m) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        //小根堆，arr[0]：nums1中下标索引，arr[1]：nums2中下标索引
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                return (nums1[arr1[0]] + nums2[arr1[1]]) - (nums1[arr2[0]] + nums2[arr2[1]]);
            }
        });

        //nums1中每个下标索引和nums2中下标索引0组成的arr作为二维数组入小根堆
        //即每行第一个元素入堆
        for (int i = 0; i < nums1.length; i++) {
            priorityQueue.offer(new int[]{i, 0});
        }

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            //k比较大，此时小根堆中所有元素都已经遍历完，直接跳出循环
            if (priorityQueue.isEmpty()) {
                break;
            }

            int[] arr = priorityQueue.poll();

            List<Integer> list = new ArrayList<>();
            list.add(nums1[arr[0]]);
            list.add(nums2[arr[1]]);
            result.add(list);

            //arr[1]后面还有nums2的下标索引，arr[0]和arr[1]的下一个下标索引组成的arr入小根堆
            //即当前行的下一个元素入堆
            if (arr[1] + 1 < nums2.length) {
                priorityQueue.offer(new int[]{arr[0], arr[1] + 1});
            }
        }

        return result;
    }

    /**
     * 二分查找变形
     * 对[left,right]进行二分查找，left为nums1[i]+nums2[j]最小值，right为nums1[i]+nums2[j]最大值，统计nums1[i]+nums2[j]小于等于mid的个数count，
     * 如果count小于k，则第k小nums1[i]+nums2[j]在mid右边，left=mid+1；
     * 如果count大于等于k，则第k小nums1[i]+nums2[j]在mid或mid左边，right=mid
     * 当left==right时，则找到第k小nums1[i]+nums2[j]的最小值left，根据left，将每列小于left的nums1[i]+nums2[j]加入结果集合，
     * 再根据结果集合中元素的个数，将每列等于left的nums1[i]+nums2[j]加入结果集合，使结果集合中有k个元素
     * 注意：不能一次性将小于和等于left的nums1[i]+nums2[j]一起加入结果集合，因为小于left的nums1[i]+nums2[j]是前k小元素，
     * 但加上全部等于left的nums1[i]+nums2[j]有可能大于前k小元素，所以只需要加上分部等于left的nums1[i]+nums2[j]得到前k小元素
     * 时间复杂度O((m+n)*log(right-left)+max(nlogm,k))=O(m+n+max(nlogm,k))，空间复杂度O(1)
     * (m=nums1.length，n=nums2.length，left=nums1[0]+nums2[0]，right=nums1[m-1]+nums2[n-1])
     * (找等于sum的nums1[i]+nums2[j]加入结果集合要通过二分查找，时间复杂度O(max(nlogm,k)))
     *
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs2(int[] nums1, int[] nums2, int k) {
        //二分左边界，初始化为nums1[i]+nums2[j]最小值
        //使用long，避免int溢出
        //注意：如果不使用long，会超时
        long left = nums1[0] + nums2[0];
        //二分右边界，初始化为nums1[i]+nums2[j]最大值
        long right = nums1[nums1.length - 1] + nums2[nums2.length - 1];
        long mid;

        while (left < right) {
            mid = left + ((right - left) >> 1);

            //nums1[i]+nums2[j]小于等于mid的个数小于k，说明第k小nums1[i]+nums2[j]在mid右边
            if (getLessEqualThanNumCount(nums1, nums2, mid) < k) {
                left = mid + 1;
            } else {
                //nums1[i]+nums2[j]小于等于mid的个数大于等于k，说明第k小nums1[i]+nums2[j]在mid或mid左边
                right = mid;
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        //第k小nums1[i]+nums2[j]的最小值
        int sum = (int) left;
        //从左下到右上遍历的下标索引
        int i = nums1.length - 1;
        int j = 0;

        //将每列小于sum的nums1[i]+nums2[j]加入结果集合
        while (i >= 0 && j < nums2.length) {
            while (i >= 0 && nums1[i] + nums2[j] >= sum) {
                i--;
            }

            for (int m = 0; m <= i; m++) {
                List<Integer> list = new ArrayList<>();
                list.add(nums1[m]);
                list.add(nums2[j]);
                result.add(list);
            }

            j++;
        }

        //nums1[i]重新初始化
        i = nums1.length - 1;

        //将每列等于sum的nums1[i]+nums2[j]加入结果集合，得到有k个元素的结果集合
        //注意：找等于sum的nums1[i]+nums2[j]加入结果集合要通过二分查找，如果线性查找会超时
        for (j = 0; j < nums2.length; j++) {
            //已经找到前k小元素，直接跳出循环
            if (result.size() >= k) {
                break;
            }

            //当前j列第一个等于sum的nums1[i]+nums2[j]的nums1下标索引
            int first = -1;
            //当前j列最后一个等于sum的nums1[i]+nums2[j]的nums1下标索引
            int last = -1;
            left = 0;
            right = i;

            //找第一个等于sum的nums1[i]+nums2[j]的nums1下标索引first
            while (left <= right) {
                mid = left + ((right - left >> 1));

                if (nums1[(int) mid] + nums2[j] == sum) {
                    first = (int) mid;
                    right = mid - 1;
                } else if (nums1[(int) mid] + nums2[j] > sum) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            //当前j列不存在等于sum的nums1[i]+nums2[j]，直接进行下次循环
            if (first == -1) {
                continue;
            }

            last = first;
            left = first + 1;
            right = i;

            //找最后一个等于sum的nums1[i]+nums2[j]的nums1下标索引last
            while (left <= right) {
                mid = left + ((right - left >> 1));

                if (nums1[(int) mid] + nums2[j] == sum) {
                    last = (int) mid;
                    left = mid + 1;
                } else if (nums1[(int) mid] + nums2[j] > sum) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            for (int m = first; m <= last; m++) {
                //已经找到前k小元素，直接跳出循环
                if (result.size() >= k) {
                    break;
                }

                List<Integer> list = new ArrayList<>();
                list.add(nums1[m]);
                list.add(nums2[j]);
                result.add(list);
            }
        }

        return result;
    }

    /**
     * 获取nums1[i]+nums2[j]小于等于num的元素个数
     * nums1和nums2选择到的元素下标索引(i,j)作为二维数组，从左下往右上移动，根据num将二维数组分为左上和右下两部分，
     * 左边都小于num，右边都大于num
     * 时间复杂度O(m+n)，空间复杂度O(1) (m=nums1.length，n=nums2.length)
     *
     * @param nums1
     * @param nums2
     * @param num
     * @return
     */
    private long getLessEqualThanNumCount(int[] nums1, int[] nums2, long num) {
        //使用long，避免int溢出
        long count = 0;
        int i = nums1.length - 1;
        int j = 0;

        while (i >= 0 && j < nums2.length) {
            while (i >= 0 && nums1[i] + nums2[j] > num) {
                i--;
            }

            //统计当前列nums1[i]+nums2[j]小于等于num的元素个数
            count = count + i + 1;
            j++;
        }

        return count;
    }
}
