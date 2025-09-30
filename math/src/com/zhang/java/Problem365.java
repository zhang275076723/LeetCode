package com.zhang.java;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @Date 2024/4/3 09:15
 * @Author zsy
 * @Description 水壶问题 美团面试题 dfs和bfs类比529 最大公因数和最小公倍数类比
 * 有两个水壶，容量分别为 x 和 y 升。水的供应是无限的。确定是否有可能使用这两个壶准确得到 target 升。
 * 你可以：
 * 装满任意一个水壶
 * 清空任意一个水壶
 * 将水从一个水壶倒入另一个水壶，直到接水壶已满，或倒水壶已空。
 * <p>
 * 输入: x = 3,y = 5,target = 4
 * 输出: true
 * 解释：
 * 按照以下步骤操作，以达到总共 4 升水：
 * 1. 装满 5 升的水壶(0, 5)。
 * 2. 把 5 升的水壶倒进 3 升的水壶，留下 2 升(3, 2)。
 * 3. 倒空 3 升的水壶(0, 2)。
 * 4. 把 2 升水从 5 升的水壶转移到 3 升的水壶(2, 0)。
 * 5. 再次加满 5 升的水壶(2, 5)。
 * 6. 从 5 升的水壶向 3 升的水壶倒水直到 3 升的水壶倒满。5 升的水壶里留下了 4 升水(3, 4)。
 * 7. 倒空 3 升的水壶。现在，5 升的水壶里正好有 4 升水(0, 4)。
 * 参考：来自著名的 "Die Hard"
 * <p>
 * 输入: x = 2, y = 6, target = 5
 * 输出: false
 * <p>
 * 输入: x = 1, y = 2, target = 3
 * 输出: true
 * 解释：同时倒满两个水壶。现在两个水壶中水的总量等于 3。
 * <p>
 * 1 <= x, y, target <= 10^3
 */
public class Problem365 {
    public static void main(String[] args) {
        Problem365 problem365 = new Problem365();
        int x = 3;
        int y = 5;
        int target = 4;
        System.out.println(problem365.canMeasureWater(x, y, target));
        System.out.println(problem365.canMeasureWater2(x, y, target));
        System.out.println(problem365.canMeasureWater3(x, y, target));
    }

    /**
     * dfs
     * x和y水壶中的水只有以下6种情况：
     * 1、x水壶中的水加满
     * 2、x水壶中的水倒空
     * 3、y水壶中的水加满
     * 4、y水壶中的水倒空
     * 5、x水壶中的水倒入y水壶，直至x水壶倒空或者y水壶加满
     * 6、y水壶中的水倒入x水壶，直至y水壶倒空或者x水壶加满
     * 时间复杂度O(xy)，空间复杂度O(xy) (共(x+1)(y+1)种状态，递归栈的深度为O(xy)，访问集合的空间复杂度为O(xy))
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    public boolean canMeasureWater(int x, int y, int target) {
        //x、y水壶存在等于target的水壶，则可以得到target，返回true
        if (x == target || y == target) {
            return true;
        }

        //x、y水壶都加满还小于target，则无法得到target，返回false
        if (x + y < target) {
            return false;
        }

        return dfs(0, 0, x, y, target, new HashSet<>());
    }

    /**
     * bfs
     * x和y水壶中的水只有以下6种情况：
     * 1、x水壶中的水加满
     * 2、x水壶中的水倒空
     * 3、y水壶中的水加满
     * 4、y水壶中的水倒空
     * 5、x水壶中的水倒入y水壶，直至x水壶倒空或者y水壶加满
     * 6、y水壶中的水倒入x水壶，直至y水壶倒空或者x水壶加满
     * 时间复杂度O(xy)，空间复杂度O(xy) (共(x+1)(y+1)种状态，队列的空间复杂度为O(xy)，访问集合的空间复杂度为O(xy))
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    public boolean canMeasureWater2(int x, int y, int target) {
        //x、y水壶存在等于target的水壶，则可以得到target，返回true
        if (x == target || y == target) {
            return true;
        }

        //x、y水壶都加满还小于target，则无法得到target，返回false
        if (x + y < target) {
            return false;
        }

        Queue<int[]> queue = new LinkedList<>();
        //访问集合，将(curX,curY)转换为long存储
        Set<Long> visitedSet = new HashSet<>();
        queue.offer(new int[]{0, 0});

        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            //x水壶当前大小
            int curX = arr[0];
            //y水壶当前大小
            int curY = arr[1];

            //当前情况(curX,curY)已访问，则从当前情况(curX,curY)继续bfs无法得到target，直接进行下次循环
            if (visitedSet.contains(convert(curX, curY))) {
                continue;
            }

            //当前x、y水壶中的水等于target，或者当前x、y水壶中的水相加等于target，则可以得到target，返回true
            if (curX == target || curY == target || curX + curY == target) {
                return true;
            }

            //当前情况(curX,curY)加入访问集合
            visitedSet.add(convert(curX, curY));

            //情况1：x水壶中的水加满
            queue.offer(new int[]{x, curY});

            //情况2：x水壶中的水倒空
            queue.offer(new int[]{0, curY});

            //情况3：y水壶中的水加满
            queue.offer(new int[]{curX, y});

            //情况4：y水壶中的水倒空
            queue.offer(new int[]{curX, 0});

            //x水壶中的水倒入y水壶之后，y水壶的大小
            int nextY1 = Math.min(curY + curX, y);
            //x水壶中的水倒入y水壶之后，x水壶的大小
            int nextX1 = curX - (nextY1 - curY);

            //情况5：x水壶中的水倒入y水壶，直至x水壶倒空或者y水壶加满
            queue.offer(new int[]{nextX1, nextY1});

            //y水壶中的水倒入x水壶之后，x水壶的大小
            int nextX2 = Math.min(curX + curY, x);
            //y水壶中的水倒入x水壶之后，y水壶的大小
            int nextY2 = curY - (nextX2 - curX);

            //情况6：y水壶中的水倒入x水壶，直至y水壶倒空或者x水壶加满
            queue.offer(new int[]{nextX2, nextY2});
        }

        return false;
    }

    /**
     * 数学，贝祖定理
     * 贝祖定理：ax+by=z有解，当且仅当z是x、y的最大公因数的倍数
     * x、y水壶不可能两个水壶同时有水且不满，如果两个水壶同时有水且不满，则其中一个水壶中的水倒入另一个水壶，总能得到其中一个水壶是空的或满的
     * 1、往有水且不满的水壶加水，如果另一个水壶是空的，相当于从初始状态往当前水壶加满水；如果另一个水壶是满的，相当于从初始状态往两个水壶加满水
     * 2、往有水且不满的水壶倒水，如果另一个水壶是空的，相当于回到初始状态；如果另一个水壶是满的，相当于从初始状态往另一个水壶加满水
     * 综上：每次加水或倒水只会有x或y的变化量，则ax+by=target有整数解a、b时，只通过x、y可以得到target
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param x
     * @param y
     * @param target
     * @return
     */
    public boolean canMeasureWater3(int x, int y, int target) {
        //x、y水壶存在等于target的水壶，则可以得到target，返回true
        if (x == target || y == target) {
            return true;
        }

        //x、y水壶都加满还小于target，则无法得到target，返回false
        if (x + y < target) {
            return false;
        }

        //贝祖定理：ax+by=target有解，当且仅当target是x、y的最大公因数的倍数
        return target % gcd(x, y) == 0;
    }

    /**
     * 从当前情况(curX,curY)dfs能否得到target
     *
     * @param curX       x水壶当前大小
     * @param curY       x水壶当前大小
     * @param capacityX  x水壶的容量
     * @param capacityY  y水壶的容量
     * @param target     通过x、y水壶得到的目标大小
     * @param visitedSet 访问集合，将(curX,curY)转换为long存储
     * @return
     */
    private boolean dfs(int curX, int curY, int capacityX, int capacityY, int target, Set<Long> visitedSet) {
        //当前情况(curX,curY)已访问，则从当前情况(curX,curY)继续dfs无法得到target，返回false
        if (visitedSet.contains(convert(curX, curY))) {
            return false;
        }

        //当前x、y水壶中的水等于target，或者当前x、y水壶中的水相加等于target，则可以得到target，返回true
        if (curX == target || curY == target || curX + curY == target) {
            return true;
        }

        //当前情况(curX,curY)加入访问集合
        visitedSet.add(convert(curX, curY));

        //情况1：x水壶中的水加满
        if (dfs(capacityX, curY, capacityX, capacityY, target, visitedSet)) {
            return true;
        }

        //情况2：x水壶中的水倒空
        if (dfs(0, curY, capacityX, capacityY, target, visitedSet)) {
            return true;
        }

        //情况3：y水壶中的水加满
        if (dfs(curX, capacityY, capacityX, capacityY, target, visitedSet)) {
            return true;
        }

        //情况4：y水壶中的水倒空
        if (dfs(curX, 0, capacityX, capacityY, target, visitedSet)) {
            return true;
        }

        //x水壶中的水倒入y水壶之后，y水壶的大小
        int nextY1 = Math.min(curY + curX, capacityY);
        //x水壶中的水倒入y水壶之后，x水壶的大小
        int nextX1 = curX - (nextY1 - curY);

        //情况5：x水壶中的水倒入y水壶，直至x水壶倒空或者y水壶加满
        if (dfs(nextX1, nextY1, capacityX, capacityY, target, visitedSet)) {
            return true;
        }

        //y水壶中的水倒入x水壶之后，x水壶的大小
        int nextX2 = Math.min(curX + curY, capacityX);
        //y水壶中的水倒入x水壶之后，y水壶的大小
        int nextY2 = curY - (nextX2 - curX);

        //情况6：y水壶中的水倒入x水壶，直至y水壶倒空或者x水壶加满
        if (dfs(nextX2, nextY2, capacityX, capacityY, target, visitedSet)) {
            return true;
        }

        //6种情况dfs结束都无法得到target，返回false
        return false;
    }

    /**
     * 非递归，辗转相除法得到a和b的最大公因数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd(int a, int b) {
        //当b为0时，a即为最大公因数
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    /**
     * 递归，辗转相除法得到a和b的最大公因数
     * 时间复杂度O(logn)=O(1)，空间复杂度O(1)
     *
     * @param a
     * @param b
     * @return
     */
    private int gcd2(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd2(b, a % b);
        }
    }

    /**
     * x左移32位加上低32位y，作为long范围的唯一访问标识
     *
     * @param x
     * @param y
     * @return
     */
    private long convert(int x, int y) {
        return ((long) x << 32) | y;
    }
}
