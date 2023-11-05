package com.zhang.java;

/**
 * @Date 2023/11/13 08:51
 * @Author zsy
 * @Description RLE 迭代器 类比Problem271、Problem394、Problem443、Problem604 迭代器类比Problem173、Problem251、Problem281、Problem284、Problem341、Problem604、Problem1286、Problem1586
 * 我们可以使用游程编码(即 RLE )来编码一个整数序列。在偶数长度 encoding ( 从 0 开始 )的游程编码数组中，
 * 对于所有偶数 i ，encoding[i] 告诉我们非负整数 encoding[i + 1] 在序列中重复的次数。
 * 例如，序列 arr = [8,8,8,5,5] 可以被编码为 encoding =[3,8,2,5] 。
 * encoding =[3,8,0,9,2,5] 和 encoding =[2,8,1,8,2,5] 也是 arr 有效的 RLE 。
 * 给定一个游程长度的编码数组，设计一个迭代器来遍历它。
 * 实现 RLEIterator 类:
 * RLEIterator(int[] encoded) 用编码后的数组初始化对象。
 * int next(int n) 以这种方式耗尽后 n 个元素并返回最后一个耗尽的元素。如果没有剩余的元素要耗尽，则返回 -1 。
 * <p>
 * 输入：
 * ["RLEIterator","next","next","next","next"]
 * [[[3,8,0,9,2,5]],[2],[1],[1],[2]]
 * 输出：
 * [null,8,8,5,-1]
 * 解释：
 * RLEIterator rleIterator = new RLEIterator([3, 8, 0, 9, 2, 5]); // 这映射到序列 [8,8,8,5,5]。
 * rleIterator.next(2); // 耗去序列的 2 个项，返回 8。现在剩下的序列是 [8, 5, 5]。
 * rleIterator.next(1); // 耗去序列的 1 个项，返回 8。现在剩下的序列是 [5, 5]。
 * rleIterator.next(1); // 耗去序列的 1 个项，返回 5。现在剩下的序列是 [5]。
 * rleIterator.next(2); // 耗去序列的 2 个项，返回 -1。
 * 这是由于第一个被耗去的项是 5，但第二个项并不存在。由于最后一个要耗去的项不存在，我们返回 -1。
 * <p>
 * 2 <= encoding.length <= 1000
 * encoding.length 为偶
 * 0 <= encoding[i] <= 10^9
 * 1 <= n <= 10^9
 * 每个测试用例调用next 不高于 1000 次
 */
public class Problem900 {
    public static void main(String[] args) {
        int[] encoding = {3, 8, 0, 9, 2, 5};
        // 这映射到序列 [8,8,8,5,5]。
        RLEIterator rleIterator = new RLEIterator(encoding);
        // 耗去序列的 2 个项，返回 8。现在剩下的序列是 [8, 5, 5]。
        System.out.println(rleIterator.next(2));
        // 耗去序列的 1 个项，返回 8。现在剩下的序列是 [5, 5]。
        System.out.println(rleIterator.next(1));
        // 耗去序列的 1 个项，返回 5。现在剩下的序列是 [5]。
        System.out.println(rleIterator.next(1));
        // 耗去序列的 2 个项，返回 -1。 这是由于第一个被耗去的项是 5，但第二个项并不存在。由于最后一个要耗去的项不存在，我们返回 -1。
        System.out.println(rleIterator.next(2));
    }

    /**
     * 记录当前遍历到arr的下标索引index、当前遍历到的元素num和当前遍历到的元素num剩余出现的次数count
     */
    static class RLEIterator {
        //要遍历的数组
        private final int[] arr;
        //当前遍历到arr的下标索引
        private int index;
        //当前遍历到的元素
        private int num;
        //num剩余出现的次数
        private int count;

        public RLEIterator(int[] encoding) {
            arr = encoding;
            index = 0;
            //初始化当前遍历到的元素为-1
            num = -1;
            count = 0;
        }

        public int next(int n) {
            while (index < arr.length) {
                //num剩余出现的次数count大于等于n，则count减去n，返回num
                if (count >= n) {
                    count = count - n;
                    return num;
                } else {
                    //num剩余出现的次数count加上下一个元素出现次数小于n，则下一个元素不是第n个值，index指针右移
                    if (count + arr[index] < n) {
                        n = n - count - arr[index];
                        index = index + 2;
                        count = 0;
                    } else {
                        //num剩余出现的次数count加上下一个元素出现次数大于等于n，则下一个元素是第n个值，index指针右移，返回下一个元素
                        count = count + arr[index] - n;
                        num = arr[index + 1];
                        index = index + 2;
                        return num;
                    }
                }
            }

            //arr已经遍历完，最后一个元素num剩余出现的次数大于等于n，则最后一个元素num是第n个值，返回num
            if (count >= n) {
                count = count - n;
                return num;
            } else {
                //arr已经遍历完，最后一个元素num剩余出现的次数小于n，则最后一个元素num不是第n个值，返回-1
                count = 0;
                return -1;
            }
        }
    }
}
