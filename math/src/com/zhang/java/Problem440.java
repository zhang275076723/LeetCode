package com.zhang.java;

/**
 * @Date 2022/9/13 8:40
 * @Author zsy
 * @Description 字典序的第K小数字 字节面试题 类比Problem386 线段树类比Problem230、Problem307
 * 给定整数 n 和 k，返回  [1, n] 中字典序第 k 小的数字。
 * <p>
 * 输入: n = 13, k = 2
 * 输出: 10
 * 解释: 字典序的排列是 [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]，所以第二小的数字是 10。
 * <p>
 * 输入: n = 1, k = 1
 * 输出: 1
 * <p>
 * 1 <= k <= n <= 10^9
 */
public class Problem440 {
    /**
     * 回溯+剪枝中记录当前元素是第几小元素
     */
    private int count = 0;

    /**
     * 回溯+剪枝中第k小元素的值
     */
    private int val;

    public static void main(String[] args) {
        Problem440 problem440 = new Problem440();
//        int n = 100;
//        int k = 10;
        int n = 681692778;
        int k = 351251360;
        //416126219
//        System.out.println(problem440.findKthNumber(n, k));
//        System.out.println(problem440.findKthNumber2(n, k));
        System.out.println(problem440.findKthNumber3(n, k));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(k)，空间复杂度O(logn)
     *
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber(int n, int k) {
        if (k == 1) {
            return 1;
        }

        //设置每个数的起始值，从1-9
        for (int i = 1; i <= 9; i++) {
            backtrack(i, n, k);
        }

        return val;
    }

    /**
     * 迭代
     * 时间复杂度O(k)，空间复杂度O(1)
     *
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber2(int n, int k) {
        if (k == 1) {
            return 1;
        }

        //num为long，避免乘10，int溢出
        long num = 1;

        for (int i = 0; i < n; i++) {
            if (i + 1 == k) {
                return (int) num;
            }

            //当前num的下一个字典序为num*10
            if (num * 10 <= n) {
                num = num * 10;
            } else {
                //num大于等于n，或者num末尾为9，，说明num末尾值字典序已经查询完毕，查询num末尾位的前一位
                while (num >= n || num % 10 == 9) {
                    num = num / 10;
                }

                num++;
            }
        }

        return -1;
    }

    /**
     * 字典树思想
     * 找num为根节点小于等于n的字典序个数count，
     * 如果count小于k，第k小节点不在num为根节点的树中，找num+1为根节点小于等于n的字典序个数，找第k-count个节点，直至k为0，表示找到
     * 如果count大于等于k，第k小节点在i为根节点的树中，递归找num*10为根节点中子树小于等于n的字典序个数，直至k为0，表示找到
     * 时间复杂度O((logn)^2)，空间复杂度O(1)
     *
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber3(int n, int k) {
        if (k == 1) {
            return 1;
        }

        //t为long，避免乘10，int溢出
        long num = 1;
        //以num为根节点字典序小于等于n的个数
        int count = getCount(num, n);

        //当k为1时，当前num即为结果
        while (k != 1) {
            //当前以num为根节点字典序小于等于n的个数小于k，则不在以num为根节点中，找num+1为根节点字典序小于等于n的个数
            if (count < k) {
                k = k - count;
                num++;
            } else {
                //当前以num为根节点字典序小于等于n的个数大于等于k，则在以num为根节点中，递归找num*10为根节点字典序小于等于n的个数
                k--;
                num = num * 10;
            }

            count = getCount(num, n);
        }

        return (int) num;
    }

    private void backtrack(long t, int n, int k) {
        if (t > n || count >= k) {
            return;
        }

        count++;

        if (count == k) {
            //t为long，避免乘10，int溢出
            val = (int) t;
            return;
        }

        for (int i = 0; i <= 9; i++) {
            backtrack(t * 10 + i, n, k);
        }
    }

    /**
     * 返回以num为根节点字典序小于等于n的个数
     * 时间复杂度O(logn)，空间复杂度O(1)
     *
     * @param num
     * @param n
     * @return
     */
    private int getCount(long num, int n) {
        int count = 1;
        //当前层字典序的第一个元素
        long left = num * 10;
        //当前层字典序的最后个元素
        long right = Math.min(num * 10 + 9, n);

        while (left <= n) {
            count = (int) (count + right - left + 1);
            left = left * 10;
            //防止超过最大值n
            right = Math.min(right * 10 + 9, n);
        }

        return count;
    }
}
