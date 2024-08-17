package com.zhang.java;

import java.util.Stack;

/**
 * @Date 2024/11/21 08:01
 * @Author zsy
 * @Description 股票价格跨度 股票类比Problem121、Problem122、Problem123、Problem188、Problem309、Problem714、Problem2034、Problem2110、Problem2291、Offer63 单调栈类比
 * 设计一个算法收集某些股票的每日报价，并返回该股票当日价格的 跨度 。
 * 当日股票价格的 跨度 被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。
 * 例如，如果未来 7 天股票的价格是 [100,80,60,70,60,75,85]，那么股票跨度将是 [1,1,1,2,1,4,6] 。
 * 实现 StockSpanner 类：
 * StockSpanner() 初始化类对象。
 * int next(int price) 给出今天的股价 price ，返回该股票当日价格的 跨度 。
 * <p>
 * 输入：
 * ["StockSpanner", "next", "next", "next", "next", "next", "next", "next"]
 * [[], [100], [80], [60], [70], [60], [75], [85]]
 * 输出：
 * [null, 1, 1, 1, 2, 1, 4, 6]
 * 解释：
 * StockSpanner stockSpanner = new StockSpanner();
 * stockSpanner.next(100); // 返回 1
 * stockSpanner.next(80);  // 返回 1
 * stockSpanner.next(60);  // 返回 1
 * stockSpanner.next(70);  // 返回 2
 * stockSpanner.next(60);  // 返回 1
 * stockSpanner.next(75);  // 返回 4 ，因为截至今天的最后 4 个股价 (包括今天的股价 75) 都小于或等于今天的股价。
 * stockSpanner.next(85);  // 返回 6
 * <p>
 * 1 <= price <= 10^5
 * 最多调用 next 方法 10^4 次
 */
public class Problem901 {
    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();
        // 返回 1
        System.out.println(stockSpanner.next(100));
        // 返回 1
        System.out.println(stockSpanner.next(80));
        // 返回 1
        System.out.println(stockSpanner.next(60));
        // 返回 2
        System.out.println(stockSpanner.next(70));
        // 返回 1
        System.out.println(stockSpanner.next(60));
        // 返回 4 ，因为截至今天的最后 4 个股价 (包括今天的股价 75) 都小于或等于今天的股价。
        System.out.println(stockSpanner.next(75));
        // 返回 6
        stockSpanner.next(85);
    }

    /**
     * 单调栈
     */
    static class StockSpanner {
        //单调递减栈
        //arr[0]：当前股票价格，arr[1]：当前股票价格下标索引
        private final Stack<int[]> stack;
        private int index;

        public StockSpanner() {
            stack = new Stack<>();
            index = 0;
        }

        public int next(int price) {
            while (!stack.isEmpty() && price >= stack.peek()[0]) {
                int[] arr = stack.pop();
            }

            int result;

            //栈为空，则[0,index]的价格都小于等于price
            if (stack.isEmpty()) {
                result = index + 1;
            } else {
                //栈不为空，则[栈顶元素下标索引+1,index]的价格都小于等于price
                result = index - stack.peek()[1];
            }

            stack.push(new int[]{price, index});
            index++;

            return result;
        }
    }
}
