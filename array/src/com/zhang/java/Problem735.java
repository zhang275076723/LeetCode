package com.zhang.java;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Date 2025/2/17 08:56
 * @Author zsy
 * @Description 小行星碰撞 类比Problem2126 栈类比
 * 给定一个整数数组 asteroids，表示在同一行的小行星。
 * 数组中小行星的索引表示它们在空间中的相对位置。
 * 对于数组中的每一个元素，其绝对值表示小行星的大小，正负表示小行星的移动方向（正表示向右移动，负表示向左移动）。
 * 每一颗小行星以相同的速度移动。
 * 找出碰撞后剩下的所有小行星。
 * 碰撞规则：两个小行星相互碰撞，较小的小行星会爆炸。
 * 如果两颗小行星大小相同，则两颗小行星都会爆炸。
 * 两颗移动方向相同的小行星，永远不会发生碰撞。
 * <p>
 * 输入：asteroids = [5,10,-5]
 * 输出：[5,10]
 * 解释：10 和 -5 碰撞后只剩下 10 。 5 和 10 永远不会发生碰撞。
 * <p>
 * 输入：asteroids = [8,-8]
 * 输出：[]
 * 解释：8 和 -8 碰撞后，两者都发生爆炸。
 * <p>
 * 输入：asteroids = [10,2,-5]
 * 输出：[10]
 * 解释：2 和 -5 发生碰撞后剩下 -5 。10 和 -5 发生碰撞后剩下 10 。
 * <p>
 * 2 <= asteroids.length <= 10^4
 * -1000 <= asteroids[i] <= 1000
 * asteroids[i] != 0
 */
public class Problem735 {
    public static void main(String[] args) {
        Problem735 problem735 = new Problem735();
//        int[] asteroids = {10, 8, 5, 2, -8, -9, -12, -3, 1};
        int[] asteroids = {-2, 1, 1, -1};
        System.out.println(Arrays.toString(problem735.asteroidCollision(asteroids)));
    }

    /**
     * 栈
     * 栈顶行星向右移动，当前行星向左移动，才会碰撞
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param asteroids
     * @return
     */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < asteroids.length; i++) {
            //栈为空，或者当前行星向右移动，则不会和栈中行星碰撞，直接入栈
            if (stack.isEmpty() || asteroids[i] > 0) {
                stack.push(asteroids[i]);
                continue;
            }

            //当前行星和栈顶行星发生碰撞时，两个行星大小是否相等标志位
            boolean flag = false;

            //栈顶行星向右移动，当前行星向左移动，则会碰撞
            while (!stack.isEmpty() && stack.peek() > 0 && asteroids[i] < 0 && stack.peek() <= -asteroids[i]) {
                int peekAsteroid = stack.pop();

                //碰撞的当前行星和栈顶行星大小相等，直接跳出循环
                if (peekAsteroid == -asteroids[i]) {
                    flag = true;
                    break;
                }
            }

            //碰撞之后，碰撞的当前行星和栈顶行星大小不相等，并且栈为空，或者栈顶行星向左移动，则当前行星入栈
            if (!flag && (stack.isEmpty() || stack.peek() < 0)) {
                stack.push(asteroids[i]);
            }
        }

        int[] result = new int[stack.size()];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
}
