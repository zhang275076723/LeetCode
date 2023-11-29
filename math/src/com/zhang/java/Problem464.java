package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/9 08:29
 * @Author zsy
 * @Description 我能赢吗 类比Problem292、Problem293、Problem294、Problem486、Problem1908 状态压缩类比Problem187、Problem294、Problem847、Problem1908 记忆化搜索类比
 * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，
 * 先使得累计整数和 达到或超过  100 的玩家，即为胜者。
 * 如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 * 给定两个整数 maxChoosableInteger （整数池中可选择的最大数）和 desiredTotal（累计和），
 * 若先出手的玩家能稳赢则返回 true ，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 * <p>
 * 输入：maxChoosableInteger = 10, desiredTotal = 11
 * 输出：false
 * 解释：
 * 无论第一个玩家选择哪个整数，他都会失败。
 * 第一个玩家可以选择从 1 到 10 的整数。
 * 如果第一个玩家选择 1，那么第二个玩家只能选择从 2 到 10 的整数。
 * 第二个玩家可以通过选择整数 10（那么累积和为 11 >= desiredTotal），从而取得胜利.
 * 同样地，第一个玩家选择任意其他整数，第二个玩家都会赢。
 * <p>
 * 输入：maxChoosableInteger = 10, desiredTotal = 0
 * 输出：true
 * <p>
 * 输入：maxChoosableInteger = 10, desiredTotal = 1
 * 输出：true
 * <p>
 * 1 <= maxChoosableInteger <= 20
 * 0 <= desiredTotal <= 300
 */
public class Problem464 {
    public static void main(String[] args) {
        Problem464 problem464 = new Problem464();
        int maxChoosableInteger = 20;
        int desiredTotal = 210;
        System.out.println(problem464.canIWin(maxChoosableInteger, desiredTotal));
    }

    /**
     * 记忆化搜索+二进制状态压缩
     * maxChoosableInteger不超过20，如果使用数组存储1-maxChoosableInteger访问状态需要O(20)，将长度为20的数组用二进制形式表示需要O(1)，
     * 每一位只有已访问、未访问2种情况，每一位只需要1bit就能表示，则长度为20的数组需要20bit来表示，即int就能表示长度为20的数组
     * 时间复杂度O(n*2^n)，空间复杂度O(2^n) (n=maxChoosableInteger) (共2^n种状态，每种状态需要O(1)存储)
     *
     * @param maxChoosableInteger
     * @param desiredTotal
     * @return
     */
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        //desiredTotal小于等于可选择的最大数，则先手玩家选择一个不小于desiredTotal的数肯定能赢，返回true
        if (desiredTotal <= maxChoosableInteger) {
            return true;
        }

        //1-maxChoosableInteger之和都小于desiredTotal，则不管怎么选择累计和都不会超过desiredTotal，肯定不能赢，返回false
        if ((1 + maxChoosableInteger) * maxChoosableInteger / 2 < desiredTotal) {
            return false;
        }

        //key：长度为maxChoosableInteger的1-maxChoosableInteger访问状态的二进制表示，
        //value：当前玩家以当前1-maxChoosableInteger访问状态开始游戏能否获胜
        Map<Integer, Boolean> map = new HashMap<>();

        return backtrack(0, 0, maxChoosableInteger, desiredTotal, map);
    }

    /**
     * @param key                 当前1-maxChoosableInteger访问状态的二进制表示，0表示当前位数字未访问，1表示当前位数字已访问
     * @param curTotal            当前累计和
     * @param maxChoosableInteger 1-maxChoosableInteger可以选择的数
     * @param desiredTotal        要到达的累计和
     * @param map                 1-maxChoosableInteger访问状态map
     * @return
     */
    private boolean backtrack(int key, int curTotal, int maxChoosableInteger, int desiredTotal, Map<Integer, Boolean> map) {
        if (map.containsKey(key)) {
            return map.get(key);
        }

        for (int i = 1; i <= maxChoosableInteger; i++) {
            //当前位数字i已访问，则进行下次循环
            if ((key & (1 << (i - 1))) != 0) {
                continue;
            }

            //当前玩家加上数字i大于等于desiredTotal，则胜利，返回true
            if (curTotal + i >= desiredTotal) {
                map.put(key, true);
                return true;
            }

            //key的第i-1位的0变为1，表示数字i已访问，得到下一个访问状态
            int nextKey = key ^ (1 << (i - 1));

            //对手以nextKey开始游戏失败，则自己以key开始游戏获胜，返回true
            if (!backtrack(nextKey, curTotal + i, maxChoosableInteger, desiredTotal, map)) {
                map.put(key, true);
                return true;
            }
        }

        //遍历结束当前玩家以key开始游戏失败，返回false
        map.put(key, false);
        return false;
    }
}
