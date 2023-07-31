package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/3/18 08:14
 * @Author zsy
 * @Description 排列序列 全排列类比Problem46、Problem47 模拟类比Problem172、Problem233、Problem400、Offer43、Offer44
 * 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 * <p>
 * 输入：n = 3, k = 3
 * 输出："213"
 * <p>
 * 输入：n = 4, k = 9
 * 输出："2314"
 * <p>
 * 输入：n = 3, k = 1
 * 输出："123"
 * <p>
 * 1 <= n <= 9
 * 1 <= k <= n!
 */
public class Problem60 {
    /**
     * 第k个全排列表示的数
     */
    private String num;

    /**
     * 当前遍历到的全排列个数
     */
    private int count = 0;

    public static void main(String[] args) {
        Problem60 problem60 = new Problem60();
        int n = 4;
        int k = 15;
        System.out.println(problem60.getPermutation(n, k));
        System.out.println(problem60.getPermutation2(n, k));
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(n!)，空间复杂度O(n)
     *
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        if (n == 1) {
            return "1";
        }

        backtrack(0, n, k, new boolean[n], new StringBuilder());

        return num;
    }

    /**
     * 模拟
     * 由高位到低位确定每一位的值，例如n为4，则每一个第一位能够确定(4-1)!=6个全排列，每一个第二位能够确定(4-2)!=2个全排列
     * 时间复杂度O(n^2)，空间复杂度O(n) (list中每次移除一个元素时间复杂度O(n)，移除n次，时间复杂度O(n^2))
     * <p>
     * 例如：n=4，k=15
     * 首先k--，k=14
     * 每一个第一位能够确定3!=6个全排列，14/6=2，则第一位为list中下标索引为2的数字，即为3，list中移除索引为2的数字，k=k-6*2=2
     * 每一个第二位能够确定2!=2个全排列，2/2=1，则第二位为list中下标索引为1的数字，即为2，list中移除索引为1的数字，k=k-2*1=0
     * 每一个第三位能够确定1!=1个全排列，0/1=0，则第三位为list中下标索引为0的数字，即为1，list中移除索引为0的数字，k=k-2*1=0
     * 每一个第四位能够确定0!=1个全排列，0/1=0，则第四位为list中下标索引为0的数字，即为4，list中移除索引为0的数字，k=k-2*1=0
     * 最后得到n=4的第15个排列为3214
     *
     * @param n
     * @param k
     * @return
     */
    public String getPermutation2(int n, int k) {
        if (n == 1) {
            return "1";
        }

        //1-n的数字list集合，用于组成每一个全排列，每确定一位，当前元素从list中移除
        List<Integer> list = new ArrayList<>();
        //阶乘数组，由低位到高位每一位能够确定的全排列个数
        int[] factorial = new int[n];
        //0!=1，0的阶乘为1
        factorial[0] = 1;

        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        for (int i = 1; i < n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }

        StringBuilder sb = new StringBuilder();
        //k减1，保证k/x能够保证正确的映射
        k--;

        //由高位到低位确定每一位的值，每一个当前位够确定factorial[i]个全排列
        for (int i = n - 1; i >= 0; i--) {
            //当前位在list中的下标索引
            int index = k / factorial[i];
            //当前位的值，并且当前位在list中的下标索引index从list中移除
            int num = list.remove(index);
            sb.append(num);
            //k减去在k之前的factorial[i]*index个全排列
            k = k - factorial[i] * index;
        }

        return sb.toString();
    }

    private void backtrack(int t, int n, int k, boolean[] visited, StringBuilder sb) {
        //已经遍历到第k个全排列，直接剪枝
        if (count >= k) {
            return;
        }

        if (t == n) {
            count++;
            //遍历到第k个全排列，赋值给num，并返回
            if (count == k) {
                num = sb.toString();
            }
            return;
        }

        for (int i = 1; i <= n; i++) {
            //当前元素已被访问，直接进行下次循环
            if (visited[i - 1]) {
                continue;
            }

            visited[i - 1] = true;
            sb.append(i);

            backtrack(t + 1, n, k, visited, sb);

            sb.delete(sb.length() - 1, sb.length());
            visited[i - 1] = false;
        }
    }
}
