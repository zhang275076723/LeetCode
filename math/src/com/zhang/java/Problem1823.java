package com.zhang.java;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Date 2023/12/6 08:28
 * @Author zsy
 * @Description 找出游戏的获胜者 华为面试题 字节面试题 类比Problem292 同Offer62
 * 共有 n 名小伙伴一起做游戏。小伙伴们围成一圈，按 顺时针顺序 从 1 到 n 编号。
 * 确切地说，从第 i 名小伙伴顺时针移动一位会到达第 (i+1) 名小伙伴的位置，其中 1 <= i < n ，
 * 从第 n 名小伙伴顺时针移动一位会回到第 1 名小伙伴的位置。
 * 游戏遵循如下规则：
 * 1、从第 1 名小伙伴所在位置 开始 。
 * 2、沿着顺时针方向数 k 名小伙伴，计数时需要 包含 起始时的那位小伙伴。逐个绕圈进行计数，一些小伙伴可能会被数过不止一次。
 * 3、你数到的最后一名小伙伴需要离开圈子，并视作输掉游戏。
 * 4、如果圈子中仍然有不止一名小伙伴，从刚刚输掉的小伙伴的 顺时针下一位 小伙伴 开始，回到步骤 2 继续执行。
 * 5、否则，圈子中最后一名小伙伴赢得游戏。
 * 给你参与游戏的小伙伴总数 n ，和一个整数 k ，返回游戏的获胜者。
 * <p>
 * 输入：n = 5, k = 2
 * 输出：3
 * 解释：游戏运行步骤如下：
 * 1) 从小伙伴 1 开始。
 * 2) 顺时针数 2 名小伙伴，也就是小伙伴 1 和 2 。
 * 3) 小伙伴 2 离开圈子。下一次从小伙伴 3 开始。
 * 4) 顺时针数 2 名小伙伴，也就是小伙伴 3 和 4 。
 * 5) 小伙伴 4 离开圈子。下一次从小伙伴 5 开始。
 * 6) 顺时针数 2 名小伙伴，也就是小伙伴 5 和 1 。
 * 7) 小伙伴 1 离开圈子。下一次从小伙伴 3 开始。
 * 8) 顺时针数 2 名小伙伴，也就是小伙伴 3 和 5 。
 * 9) 小伙伴 5 离开圈子。只剩下小伙伴 3 。所以小伙伴 3 是游戏的获胜者。
 * <p>
 * 输入：n = 6, k = 5
 * 输出：1
 * 解释：小伙伴离开圈子的顺序：5、4、6、2、3 。小伙伴 1 是游戏的获胜者。
 * <p>
 * 1 <= k <= n <= 500
 */
public class Problem1823 {
    public static void main(String[] args) {
        Problem1823 problem1823 = new Problem1823();
        int n = 5;
        int k = 2;
        System.out.println(problem1823.findTheWinner(n, k));
        System.out.println(problem1823.findTheWinner2(n, k));
        System.out.println(problem1823.findTheWinner3(n, k));
    }

    /**
     * 模拟
     * 时间复杂度O(nk)，空间复杂度O(n)
     *
     * @param n
     * @param k
     * @return
     */
    public int findTheWinner(int n, int k) {
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            queue.offer(i);
        }

        while (queue.size() != 1) {
            //每次移除第k个元素，则k-1个元素依次出队再入队，(k-1)%queue.size()保证一次遍历能够移除元素
            for (int i = 0; i < (k - 1) % queue.size(); i++) {
                queue.offer(queue.poll());
            }

            queue.poll();
        }

        return queue.peek();
    }

    /**
     * 数学，约瑟夫环
     * f(n,k)：n个元素每次移除第k个元素，最后剩下元素在原先n个元素中的下标索引
     * f(n,k) = (f(n-1,k) + k) % n
     * n个元素移除第k个元素，剩余n-1个元素，此时n-1个元素由原来n个元素整体左移k位得到，
     * 假设n个元素每次移除第k个元素，最后剩下元素在原先n个元素中的下标索引为A，则移除第k个元素，剩余n-1个元素，
     * 此时A也左移了k位，即f(n,k)左移k位得到f(n-1,k)，即f(n,k)=(f(n-1,k)+k)%n
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param n
     * @param k
     * @return
     */
    public int findTheWinner2(int n, int k) {
        //注意编号是从1开始的，所以得到元素的下标索引之后要加1
        return f(n, k) + 1;
    }

    /**
     * 数学，约瑟夫环的非递归形式
     * f(n,k)：n个元素每次移除第k个元素，最后剩下元素在原先n个元素中的下标索引
     * f(n,k) = (f(n-1,k) + k) % n
     * n个元素移除第k个元素，剩余n-1个元素，此时n-1个元素由原来n个元素整体左移k位得到，
     * 假设n个元素每次移除第k个元素，最后剩下元素在原先n个元素中的下标索引为A，则移除第k个元素，剩余n-1个元素，
     * 此时A也左移了k位，即f(n,k)左移k位得到f(n-1,k)，即f(n,k)=(f(n-1,k)+k)%n
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param n
     * @param k
     * @return
     */
    public int findTheWinner3(int n, int k) {
        //只有一个元素，该元素即为最后剩下的元素，下标索引为0
        int index = 0;

        //因为每次移除第k个元素，i-1个元素由原来i个元素整体左移k位得到，所以i-1个元素整体右移k位得到i个元素
        for (int i = 2; i <= n; i++) {
            index = (index + k) % i;
        }

        //注意编号是从1开始的，所以得到元素的下标索引之后要加1
        return index + 1;
    }

    private int f(int n, int k) {
        //只有一个元素，该元素即为最后剩下的元素，下标索引为0
        if (n == 1) {
            return 0;
        }

        return (f(n - 1, k) + k) % n;
    }
}
