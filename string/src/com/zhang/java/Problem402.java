package com.zhang.java;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Date 2022/4/19 19:00
 * @Author zsy
 * @Description 移掉 K 位数字 华为面试题 字节面试题 单调栈类比Problem42、Problem84、Problem255、Problem316、Problem321、Problem456、Problem496、Problem503、Problem654、Problem739、Problem795、Problem907、Problem1019、Problem1856、Problem2104、Problem2454、Problem2487、Offer33、DoubleStackSort
 * 给你一个以字符串表示的非负整数 num 和一个整数 k ，
 * 移除这个数中的 k 位数字，使得剩下的数字最小。请你以字符串形式返回这个最小的数字。
 * <p>
 * 输入：num = "1432219", k = 3
 * 输出："1219"
 * 解释：移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219 。
 * <p>
 * 输入：num = "10200", k = 1
 * 输出："200"
 * 解释：移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
 * <p>
 * 输入：num = "10", k = 2
 * 输出："0"
 * 解释：从原数字移除所有的数字，剩余为空就是 0 。
 * <p>
 * 1 <= k <= num.length <= 10^5
 * num 仅由若干位数字（0 - 9）组成
 * 除了 0 本身之外，num 不含任何前导零
 */
public class Problem402 {
    public static void main(String[] args) {
        Problem402 problem402 = new Problem402();
        String num = "10200";
        int k = 1;
        System.out.println(problem402.removeKdigits(num, k));
        System.out.println(problem402.removeKdigits2(num, k));
    }

    /**
     * 暴力
     * 删除k个元素，即保留n-k个元素
     * 第一个元素：从num[0]-num[n-(n-k-1)-1]选出最小的元素num[i]，作为第一个元素
     * 第二个元素：从num[i+1]-num[n-(n-k-2)-1]选出最小的元素num[j]，作为第二个元素
     * ...
     * 直至n-k个元素都选出来，即为最小的元素
     * 例如：num = 1432219, k = 2
     * 第一个元素：从143中选出最小元素num[0]，1
     * 第二个元素：从432中选出最小元素num[3]，2
     * 第三个元素：从2中选出最小元素num[4]，2
     * 第四个元素：从1中选出最小元素num[5]，1
     * 第五个元素：从9中选出最小元素num[6]，9
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        if (num.length() == k) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        //要找的第一个元素的起始索引
        int l = 0;

        for (int i = 0; i < num.length() - k; i++) {
            int minIndex = l;
            //找当前元素的最小值
            for (int j = l; j <= k + i; j++) {
                if (num.charAt(j) < num.charAt(minIndex)) {
                    minIndex = j;
                }
            }
            sb.append(num.charAt(minIndex));
            l = minIndex + 1;
        }

        //去除前导0的情况
        while (sb.length() > 0 && sb.charAt(0) == '0') {
            sb.delete(0, 1);
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    /**
     * 单调栈 (求当前元素之后，比当前元素大或小的元素，就要想到单调栈)
     * 单调递增栈，移除k个不满足栈要求的元素，加上之后的元素，构成最小数字
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits2(String num, int k) {
        if (num.length() == k) {
            return "0";
        }

        //单调递增栈
        Deque<Character> stack = new LinkedList<>();

        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            //栈顶元素值大于当前元素值，且栈不为空，且k大于0，则栈顶元素出栈，k减1
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > c) {
                stack.pollLast();
                k--;
            }
            //当前元素下标索引入栈
            stack.offerLast(c);
        }

        //栈中元素依次递增的情况，例如"34567"，如果k>0说明删除的元素个数小于原生的k个，则还要删除栈顶剩下的k个元素
        while (!stack.isEmpty() && k > 0) {
            stack.pollLast();
            k--;
        }

        //栈中元素有前导0的情况
        while (!stack.isEmpty() && stack.peekFirst() == '0') {
            stack.pollFirst();
        }

        if (stack.isEmpty()) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append(stack.pollFirst());
        }

        return sb.toString();
    }
}
