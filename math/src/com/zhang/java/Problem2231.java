package com.zhang.java;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Date 2024/8/23 08:19
 * @Author zsy
 * @Description 按奇偶性交换后的最大数字 类比Problem31、Problem556、Problem670、Problem738、Problem1323、Problem1328、Problem1842、Problem1850 优先队列类比
 * 给你一个正整数 num 。
 * 你可以交换 num 中 奇偶性 相同的任意两位数字（即，都是奇数或者偶数）。
 * 返回交换 任意 次之后 num 的 最大 可能值。
 * <p>
 * 输入：num = 1234
 * 输出：3412
 * 解释：交换数字 3 和数字 1 ，结果得到 3214 。
 * 交换数字 2 和数字 4 ，结果得到 3412 。
 * 注意，可能存在其他交换序列，但是可以证明 3412 是最大可能值。
 * 注意，不能交换数字 4 和数字 1 ，因为它们奇偶性不同。
 * <p>
 * 输入：num = 65875
 * 输出：87655
 * 解释：交换数字 8 和数字 6 ，结果得到 85675 。
 * 交换数字 5 和数字 7 ，结果得到 87655 。
 * 注意，可能存在其他交换序列，但是可以证明 87655 是最大可能值。
 * <p>
 * 1 <= num <= 10^9
 */
public class Problem2231 {
    public static void main(String[] args) {
        Problem2231 problem2231 = new Problem2231();
        int num = 1234;
        System.out.println(problem2231.largestInteger(num));
        System.out.println(problem2231.largestInteger2(num));
    }

    /**
     * 模拟
     * num转化为char数组，通过选择排序分别将奇数位和偶数位的元素由大到小排序，得到最大数字
     * 时间复杂度O(n^2)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int largestInteger(int num) {
        char[] numArr = (num + "").toCharArray();

        //选择排序
        for (int i = 0; i < numArr.length - 1; i++) {
            int index = i;

            for (int j = i + 1; j < numArr.length; j++) {
                //numArr[j]和numArr[i]奇偶性相同，并且找比numArr[i]大的最大值numArr[index]
                //注意：char不需要转换为int比较奇偶性，直接比较char类型的numArr[i]奇偶性即可
                if ((numArr[i] % 2 == numArr[j] % 2) && numArr[j] > numArr[index]) {
                    index = j;
                }
            }

            if (index != i) {
                char temp = numArr[index];
                numArr[index] = numArr[i];
                numArr[i] = temp;
            }
        }

        return Integer.parseInt(new String(numArr));
    }

    /**
     * 优先队列，大根堆
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param num
     * @return
     */
    public int largestInteger2(int num) {
        char[] numArr = (num + "").toCharArray();

        //存储偶数元素的大根堆
        PriorityQueue<Integer> priorityQueue1 = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });
        //存储奇数元素的大根堆
        PriorityQueue<Integer> priorityQueue2 = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });

        for (int i = 0; i < numArr.length; i++) {
            int cur = numArr[i] - '0';

            if (cur % 2 == 0) {
                priorityQueue1.offer(cur);
            } else {
                priorityQueue2.offer(cur);
            }
        }

        for (int i = 0; i < numArr.length; i++) {
            int cur = numArr[i] - '0';

            if (cur % 2 == 0) {
                numArr[i] = (char) (priorityQueue1.poll() + '0');
            } else {
                numArr[i] = (char) (priorityQueue2.poll() + '0');
            }
        }

        int max = 0;

        for (int i = 0; i < numArr.length; i++) {
            max = max * 10 + (numArr[i] - '0');
        }

        return max;
    }
}
