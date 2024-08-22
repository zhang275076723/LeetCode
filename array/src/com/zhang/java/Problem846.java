package com.zhang.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/12/1 08:47
 * @Author zsy
 * @Description 一手顺子 类比Problem659、Offer61
 * Alice 手中有一把牌，她想要重新排列这些牌，分成若干组，使每一组的牌数都是 groupSize ，并且由 groupSize 张连续的牌组成。
 * 给你一个整数数组 hand 其中 hand[i] 是写在第 i 张牌上的数值。
 * 如果她可能重新排列这些牌，返回 true ；否则，返回 false 。
 * <p>
 * 输入：hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
 * 输出：true
 * 解释：Alice 手中的牌可以被重新排列为 [1,2,3]，[2,3,4]，[6,7,8]。
 * 示例 2：
 * <p>
 * 输入：hand = [1,2,3,4,5], groupSize = 4
 * 输出：false
 * 解释：Alice 手中的牌无法被重新排列成几个大小为 4 的组。
 * <p>
 * 1 <= hand.length <= 10^4
 * 0 <= hand[i] <= 10^9
 * 1 <= groupSize <= hand.length
 */
public class Problem846 {
    public static void main(String[] args) {
        Problem846 problem846 = new Problem846();
        int[] hand = {1, 2, 3, 6, 2, 3, 4, 7, 8};
        int groupSize = 3;
        System.out.println(problem846.isNStraightHand(hand, groupSize));
    }

    /**
     * 排序+哈希表
     * hand由小到大排序，hand中元素存入哈希表中，依次判断能否以hand[i]作为长度为groupSize的顺子
     * 时间复杂度O(nlogn)，空间复杂度O(n)
     *
     * @param hand
     * @param groupSize
     * @return
     */
    public boolean isNStraightHand(int[] hand, int groupSize) {
        //无法得到每组长度为groupSize的顺子，返回false
        if (hand.length % groupSize != 0) {
            return false;
        }

        //由小到大排序
        Arrays.sort(hand);

        //key：hand[i]，value：hand中hand[i]出现的次数
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : hand) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int i = 0; i < hand.length; i++) {
            //不存在hand[i]，则hand[i]已经作为顺子使用，直接进行下次循环
            if (!map.containsKey(hand[i])) {
                continue;
            }

            //hand[i]到hand[i]+groupSize-1的顺子
            for (int j = 0; j < groupSize; j++) {
                int num = hand[i] + j;

                //不存在num，则无法构成顺子，返回false
                if (!map.containsKey(num)) {
                    return false;
                }

                map.put(num, map.get(num) - 1);

                if (map.get(num) == 0) {
                    map.remove(num);
                }
            }
        }

        //遍历结束，则hand中元素都构成顺子，返回true
        return true;
    }
}
