package com.zhang.java;

/**
 * @Date 2023/6/26 09:31
 * @Author zsy
 * @Description H 指数 II 类比Problem274 类比Problem33、Problem34、Problem35、Problem81、Problem153、Problem154、Problem162、Problem540、Problem852、Problem1095、Problem1150、Offer11、Offer53、Offer53_2、Interview_10_03、Interview_10_05
 * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数，citations 已经按照 升序排列 。
 * 计算并返回该研究者的 h 指数。
 * h 指数的定义：h 代表“高引用次数”（high citations），
 * 一名科研人员的 h 指数是指他（她）的 （n 篇论文中）总共有 h 篇论文分别被引用了至少 h 次。
 * 请你设计并实现对数时间复杂度的算法解决此问题。
 * <p>
 * 输入：citations = [0,1,3,5,6]
 * 输出：3
 * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 0, 1, 3, 5, 6 次。
 * 由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3 。
 * <p>
 * 输入：citations = [1,2,100]
 * 输出：2
 * <p>
 * n == citations.length
 * 1 <= n <= 10^5
 * 0 <= citations[i] <= 1000
 * citations 按 升序排列
 */
public class Problem275 {
    public static void main(String[] args) {
        Problem275 problem275 = new Problem275();
        int[] citations = {0, 1, 3, 5, 6};
        System.out.println(problem275.hIndex(citations));
    }

    /**
     * 二分查找变形，看到有序数组，就要想到二分查找
     * 对数组大小进行二分查找，判断数组中是否存在引用次数超过当前大小个文献
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        //左边界，h指数最小为0，即发表的论文数量最小为0
        int left = 0;
        //右边界，h指数最大为数组长度，即发表的论文数量最大为citations.length
        int right = citations.length;
        int mid;

        while (left < right) {
            //mid往右偏移，因为转移条件是right=mid-1，避免无法跳出循环
            mid = left + ((right - left) >> 1) + 1;

            //citations[citation.length-mid]-citations[citation.length-1]引用次数都大于等于mid，则h指数为至少为mid
            if (citations[citations.length - mid] >= mid) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }
}
