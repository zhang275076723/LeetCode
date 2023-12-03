package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2023/12/15 08:24
 * @Author zsy
 * @Description 大礼包 状态压缩类比Problem187、Problem294、Problem464、Problem473、Problem526、Problem698、Problem847、Problem1908 记忆化搜索类比
 * 在 LeetCode 商店中， 有 n 件在售的物品。
 * 每件物品都有对应的价格。然而，也有一些大礼包，每个大礼包以优惠的价格捆绑销售一组物品。
 * 给你一个整数数组 price 表示物品价格，其中 price[i] 是第 i 件物品的价格。
 * 另有一个整数数组 needs 表示购物清单，其中 needs[i] 是需要购买第 i 件物品的数量。
 * 还有一个数组 special 表示大礼包，special[i] 的长度为 n + 1 ，
 * 其中 special[i][j] 表示第 i 个大礼包中内含第 j 件物品的数量，
 * 且 special[i][n] （也就是数组中的最后一个整数）为第 i 个大礼包的价格。
 * 返回 确切 满足购物清单所需花费的最低价格，你可以充分利用大礼包的优惠活动。
 * 你不能购买超出购物清单指定数量的物品，即使那样会降低整体价格。
 * 任意大礼包可无限次购买。
 * <p>
 * 输入：price = [2,5], special = [[3,0,5],[1,2,10]], needs = [3,2]
 * 输出：14
 * 解释：有 A 和 B 两种物品，价格分别为 ¥2 和 ¥5 。
 * 大礼包 1 ，你可以以 ¥5 的价格购买 3A 和 0B 。
 * 大礼包 2 ，你可以以 ¥10 的价格购买 1A 和 2B 。
 * 需要购买 3 个 A 和 2 个 B ， 所以付 ¥10 购买 1A 和 2B（大礼包 2），以及 ¥4 购买 2A 。
 * <p>
 * 输入：price = [2,3,4], special = [[1,1,0,4],[2,2,1,9]], needs = [1,2,1]
 * 输出：11
 * 解释：A ，B ，C 的价格分别为 ¥2 ，¥3 ，¥4 。
 * 可以用 ¥4 购买 1A 和 1B ，也可以用 ¥9 购买 2A ，2B 和 1C 。
 * 需要买 1A ，2B 和 1C ，所以付 ¥4 买 1A 和 1B（大礼包 1），以及 ¥3 购买 1B ， ¥4 购买 1C 。
 * 不可以购买超出待购清单的物品，尽管购买大礼包 2 更加便宜。
 * <p>
 * n == price.length
 * n == needs.length
 * 1 <= n <= 6
 * 0 <= price[i] <= 10
 * 0 <= needs[i] <= 10
 * 1 <= special.length <= 100
 * special[i].length == n + 1
 * 0 <= special[i][j] <= 50
 */
public class Problem638 {
    public static void main(String[] args) {
        Problem638 problem638 = new Problem638();
        List<Integer> price = new ArrayList<Integer>() {{
            add(2);
            add(5);
        }};
        List<List<Integer>> special = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(3);
                add(0);
                add(5);
            }});
            add(new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(10);
            }});
        }};
        List<Integer> needs = new ArrayList<Integer>() {{
            add(3);
            add(2);
        }};
        System.out.println(problem638.shoppingOffers(price, special, needs));
    }

    /**
     * 记忆化搜索+二进制状态压缩
     * 不使用二进制状态压缩，存储当前物品购买个数需要O(6)；使用二进制状态压缩，物品的范围在0-10，每个物品只需要4bit就能表示，
     * 长度为6的物品，需要24bit表示，即int就能表示当前物品购买个数
     * 时间复杂度O(m*11^n)，空间复杂度O(11^n) (共11^n种状态，每种状态需要O(1)存储) (m=special.size()，即大礼包的个数)
     *
     * @param price
     * @param special
     * @param needs
     * @return
     */
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        //物品的个数
        int n = price.size();

        //过滤掉没有单独购买物品优惠的大礼包之后的大礼包集合
        List<List<Integer>> filterSpecial = new ArrayList<>();

        //过滤掉没有单独购买物品优惠的大礼包，即使用大礼包肯定比单独购买更优惠
        for (int i = 0; i < special.size(); i++) {
            //当前大礼包
            List<Integer> curSpecial = special.get(i);
            //当前大礼包的价格
            int specialPrice = curSpecial.get(n);
            //单独购买当前大礼包中物品的价格
            int soloPrice = 0;

            for (int j = 0; j < n; j++) {
                soloPrice = soloPrice + curSpecial.get(j) * price.get(j);
            }

            //大礼包的价格小于单独购买当前大礼包中物品的价格，则大礼包更优惠，加入filterSpecial中
            if (specialPrice < soloPrice) {
                filterSpecial.add(curSpecial);
            }
        }

        //key：购买物品的二进制状态，value：购买当前的物品需要的最低价格
        Map<Integer, Integer> map = new HashMap<>();
        //24位购买物品的二进制状态
        int state = 0;

        for (int i = 0; i < n; i++) {
            //物品的范围在0-10，共11种情况，每个物品只需要4bit就能表示
            state = (state << 4) + needs.get(i);
        }

        return dfs(state, n, price, filterSpecial, map);
    }

    private int dfs(int state, int n, List<Integer> price, List<List<Integer>> filterSpecial, Map<Integer, Integer> map) {
        //已经得到了购买当前的物品需要的最低价格，直接返回map.get(state)
        if (map.containsKey(state)) {
            return map.get(state);
        }

        //购买当前的物品需要的最低价格
        int minCost = 0;

        //不使用大礼包，单独购买当前的物品需要的价格
        for (int i = 0; i < n; i++) {
            //每个物品4bit表示
            minCost = minCost + price.get(i) * ((state >>> (4 * (n - i - 1))) & 0b1111);
        }

        //使用大礼包购买物品
        for (int i = 0; i < filterSpecial.size(); i++) {
            //当前大礼包
            List<Integer> curSpecial = filterSpecial.get(i);
            //当前大礼包的价格
            int specialPrice = curSpecial.get(n);
            //使用当前大礼包之后还需要购买物品的二进制状态
            int nextState = 0;
            //当前大礼包能否使用的标志位
            boolean flag = true;

            //减去大礼包中第j个物品的数量
            for (int j = 0; j < n; j++) {
                //大礼包中第j个物品的数量超过第j个物品还需要购买的数量，则当前大礼包不能使用，跳出循环，考虑下一个大礼包
                if (curSpecial.get(j) > ((state >>> (4 * (n - j - 1))) & 0b1111)) {
                    flag = false;
                    break;
                }

                //减去大礼包中第j个物品的数量
                nextState = nextState + ((((state >>> (4 * (n - j - 1))) & 0b1111) - curSpecial.get(j)) << (4 * (n - j - 1)));
            }

            //当前大礼包能使用，则继续dfs，求minCost
            if (flag) {
                minCost = Math.min(minCost, specialPrice + dfs(nextState, n, price, filterSpecial, map));
            }
        }

        //遍历结束，得到购买当前的物品需要的最低价格，返回minCost
        map.put(state, minCost);
        return minCost;
    }
}
