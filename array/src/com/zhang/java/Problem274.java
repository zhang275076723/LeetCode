package com.zhang.java;

/**
 * @Date 2023/6/26 09:26
 * @Author zsy
 * @Description H 指数 类比Problem275
 * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。
 * 计算并返回该研究者的 h 指数。
 * 根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，
 * 并且每篇论文 至少 被引用 h 次。
 * 如果 h 有多种可能的值，h 指数 是其中最大的那个。
 * <p>
 * 输入：citations = [3,0,6,1,5]
 * 输出：3
 * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
 * 由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
 * <p>
 * 输入：citations = [1,3,1]
 * 输出：1
 * <p>
 * n == citations.length
 * 1 <= n <= 5000
 * 0 <= citations[i] <= 1000
 */
public class Problem274 {
    public static void main(String[] args) {
        Problem274 problem274 = new Problem274();
        int[] citations = {3, 0, 6, 1, 5};
        System.out.println(problem274.hIndex(citations));
    }

    /**
     * 模拟
     * 先对数组由小到大排序，从数组长度开始由大到小遍历，判断数组中是否存在引用次数超过当前大小个文献
     * 时间复杂度O(nlogn)，空间复杂度O(n) (归并排序的空间复杂度为O(n))
     *
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        mergeSort(citations, 0, citations.length - 1, new int[citations.length]);

        //从大到小找h指数，当前发表的论文数量i
        //注意：避免数组越界，最小只遍历到1
        for (int i = citations.length; i >= 1; i--) {
            //citations[citation.length-i]-citations[citation.length-1]引用次数都大于等于i，则h指数为i
            if (citations[citations.length - i] >= i) {
                return i;
            }
        }

        //没有找到，则h指数为0
        return 0;
    }

    private void mergeSort(int[] arr, int left, int right, int[] tempArr) {
        if (left >= right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        mergeSort(arr, left, mid, tempArr);
        mergeSort(arr, mid + 1, right, tempArr);
        merge(arr, left, mid, right, tempArr);
    }

    private void merge(int[] arr, int left, int mid, int right, int[] tempArr) {
        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                tempArr[k] = arr[i];
                i++;
                k++;
            } else {
                tempArr[k] = arr[j];
                j++;
                k++;
            }
        }

        while (i <= mid) {
            tempArr[k] = arr[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempArr[k] = arr[j];
            j++;
            k++;
        }

        for (k = left; k <= right; k++) {
            arr[k] = tempArr[k];
        }
    }
}
