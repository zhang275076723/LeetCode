package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2023/12/8 08:39
 * @Author zsy
 * @Description 翻转游戏 II 类比Problem292、Problem293 状态压缩类比Problem187、Problem847 回溯+剪枝类比
 * 你和朋友玩一个叫做「翻转游戏」的游戏。游戏规则如下：
 * 给你一个字符串 currentState ，其中只含 '+' 和 '-' 。
 * 你和朋友轮流将 连续 的两个 "++" 反转成 "--" 。
 * 当一方无法进行有效的翻转时便意味着游戏结束，则另一方获胜。
 * 默认每个人都会采取最优策略。
 * 请你写出一个函数来判定起始玩家 是否存在必胜的方案 ：如果存在，返回 true ；否则，返回 false 。
 * <p>
 * 输入：currentState = "++++"
 * 输出：true
 * 解释：起始玩家可将中间的 "++" 翻转变为 "+--+" 从而得胜。
 * <p>
 * 输入：currentState = "+"
 * 输出：false
 * <p>
 * 1 <= currentState.length <= 60
 * currentState[i] 不是 '+' 就是 '-'
 */
public class Problem294 {
    public static void main(String[] args) {
        Problem294 problem294 = new Problem294();
        String currentState = "++++";
        System.out.println(problem294.canWin(currentState));
    }

    /**
     * 回溯+剪枝+二进制状态压缩
     * currentState长度不超过60，如果使用字符串存储currentState需要O(60)，将长度为60的字符串用二进制形式表示需要O(1)，
     * 每一位只有'+'、'-'2种情况，每一位只需要1bit就能表示，则长度为60的字符串需要60bit来表示，即long就能表示长度为60的序列
     * 时间复杂度O(n^n)，空间复杂度O(n)
     *
     * @param currentState
     * @return
     */
    public boolean canWin(String currentState) {
        //key：长度为60的字符串的二进制表示，value：当前玩家以当前字符串开始游戏是否获胜
        Map<Long, Boolean> map = new HashMap<>();
        //currentState的二进制表示，'+'：当前位为1，'-'：当前位为0
        long key = 0;

        for (int i = 0; i < currentState.length(); i++) {
            char c = currentState.charAt(i);

            if (c == '+') {
                key = (key << 1) + 1;
            } else {
                key = key << 1;
            }
        }

        return backtrack(key, currentState.length(), map);
    }

    private boolean backtrack(long key, int n, Map<Long, Boolean> map) {
        //已经得到了当前玩家以当前字符串开始游戏是否获胜，直接返回是否获胜
        if (map.containsKey(key)) {
            return map.get(key);
        }

        //key由低位到高位遍历
        for (int i = 0; i < n - 1; i++) {
            //第i位和第i+1位不是连续的"++"，则进行下次循环
            if ((key & (1L << i)) == 0 || (key & (1L << (i + 1))) == 0) {
                continue;
            }

            //key的第i位和第i+1位的1变为0，表示连续的"++"变为"--"
            long nextKey = key ^ (1L << i) ^ (1L << (i + 1));

            //对手以nextKey开始游戏失败，则自己以key开始游戏获胜
            if (!backtrack(nextKey, n, map)) {
                map.put(key, true);
                return true;
            }
        }

        //遍历结束都没有找到连续的"++"，则当前玩家以当前字符串开始游戏失败，返回false
        map.put(key, false);
        return false;
    }
}
