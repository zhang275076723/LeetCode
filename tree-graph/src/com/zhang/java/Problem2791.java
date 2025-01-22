package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/3/15 09:06
 * @Author zsy
 * @Description 树中可以形成回文的路径数 类比Problem437 类比Problem1177、Problem1371、Problem1457、Problem1542、Problem1915、Problem1930、Problem2484 前缀和类比 状态压缩类比 回文类比
 * 给你一棵 树（即，一个连通、无向且无环的图），根 节点为 0 ，由编号从 0 到 n - 1 的 n 个节点组成。
 * 这棵树用一个长度为 n 、下标从 0 开始的数组 parent 表示，其中 parent[i] 为节点 i 的父节点，
 * 由于节点 0 为根节点，所以 parent[0] == -1 。
 * 另给你一个长度为 n 的字符串 s ，其中 s[i] 是分配给 i 和 parent[i] 之间的边的字符。
 * s[0] 可以忽略。
 * 找出满足 u < v ，且从 u 到 v 的路径上分配的字符可以 重新排列 形成 回文 的所有节点对 (u, v) ，并返回节点对的数目。
 * 如果一个字符串正着读和反着读都相同，那么这个字符串就是一个 回文 。
 * <p>
 * 输入：parent = [-1,0,0,1,1,2], s = "acaabc"
 * 输出：8
 * 解释：符合题目要求的节点对分别是：
 * - (0,1)、(0,2)、(1,3)、(1,4) 和 (2,5) ，路径上只有一个字符，满足回文定义。
 * - (2,3)，路径上字符形成的字符串是 "aca" ，满足回文定义。
 * - (1,5)，路径上字符形成的字符串是 "cac" ，满足回文定义。
 * - (3,5)，路径上字符形成的字符串是 "acac" ，可以重排形成回文 "acca" 。
 * <p>
 * 输入：parent = [-1,0,0,0,0], s = "aaaaa"
 * 输出：10
 * 解释：任何满足 u < v 的节点对 (u,v) 都符合题目要求。
 * <p>
 * n == parent.length == s.length
 * 1 <= n <= 10^5
 * 对于所有 i >= 1 ，0 <= parent[i] <= n - 1 均成立
 * parent[0] == -1
 * parent 表示一棵有效的树
 * s 仅由小写英文字母组成
 */
public class Problem2791 {
    public static void main(String[] args) {
        Problem2791 problem2791 = new Problem2791();
        List<Integer> parent = new ArrayList<Integer>() {{
            add(-1);
            add(0);
            add(0);
            add(1);
            add(1);
            add(2);
        }};
        String s = "acaabc";
        System.out.println(problem2791.countPalindromePaths(parent, s));
    }

    /**
     * dfs+前缀和+哈希表+二进制状态压缩
     * preSum[i]：根节点到当前节点i的路径中字符出现的奇偶次数二进制表示的数，0：当前字符出现偶数次，1：当前字符出现奇数次
     * preSum[u]^preSum[v]：节点u到节点v的路径中字符出现的奇偶次数二进制表示的数(节点u<节点v，保证相同的路径只统计一次)
     * 节点u到节点v的路径中出现次数为奇数的字符个数小于等于1，则当前路径重新排列后形成回文
     * 时间复杂度O(n)，空间复杂度O(n) (n=parent.size()，即树中节点的个数) (|Σ|=26，只包含小写字母)
     *
     * @param parent
     * @param s
     * @return
     */
    public long countPalindromePaths(List<Integer> parent, String s) {
        //邻接表
        List<List<Integer>> graph = new ArrayList<>();
        int n = parent.size();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        //i从1开始遍历，不需要考虑节点0的父节点
        for (int i = 1; i < n; i++) {
            graph.get(parent.get(i)).add(i);
        }

        //key：dfs已经遍历过的路径中字符出现的奇偶次数二进制表示的数，value：key出现的次数
        //用于统计已经遍历过的节点到当前节点的路径能否重新排列后形成回文
        Map<Integer, Integer> map = new HashMap<>();
//        //初始化，空路径中字符出现的奇偶次数二进制表示的数为0，0出现的次数为1
//        //用于根节点到当前节点的路径重新排列后形成回文的情况
//        //注意：和437题不同，权值在边上，所以不能初始化
//        map.put(0, 1);

        return dfs(0, 0, s, graph, map);
    }

    /**
     * @param u      当前遍历到的节点u
     * @param preSum 根节点到当前节点u的路径中字符出现的奇偶次数二进制表示的数
     * @param s      当前节点u和父节点的边的字符
     * @param graph  邻接表
     * @param map    存储dfs已经遍历过的路径中字符出现的奇偶次数二进制表示的数，用于统计已经遍历过的节点到当前节点的路径能否重新排列后形成回文
     * @return
     */
    private long dfs(int u, int preSum, String s, List<List<Integer>> graph, Map<Integer, Integer> map) {
        long result = 0;

        //根节点0没有和父节点的边
        if (u != 0) {
            //当前节点u和父节点的边的字符
            char c = s.charAt(u);
            //(1<<(c-'a'))：当前字符c存储在二进制表示的从右往左第(c-'a')位
            //注意：异或操作可以立刻得到当前字符c在当前位的奇偶次数
            preSum = preSum ^ (1 << (c - 'a'));

            //map中存在已经遍历过的路径中字符出现的奇偶次数二进制表示的数preSum，
            //则map中的节点和当前节点u的路径中字符出现的次数均为偶数，即当前路径重新排列后形成回文
            if (map.containsKey(preSum)) {
                result = result + map.get(preSum);
            }

            //map中存在已经遍历过的路径中字符出现的奇偶次数二进制表示的数preSum^(1<<i)，即二进制表示的数preSum从右往左的第i位奇偶性取反，
            //则map中的节点和当前节点u的路径中字符出现的次数为奇数的字符只有1个，即当前路径重新排列后形成回文
            for (int i = 0; i < 26; i++) {
                if (map.containsKey(preSum ^ (1 << i))) {
                    result = result + map.get(preSum ^ (1 << i));
                }
            }
        }

        //根节点到当前节点这条路径加入map，用于dfs
        map.put(preSum, map.getOrDefault(preSum, 0) + 1);

        //节点u的子节点v
        for (int v : graph.get(u)) {
            result = result + dfs(v, preSum, s, graph, map);
        }

        //注意：dfs结束map不需要减去根节点到当前节点这条路径，因为和437题不同，没有要求必须是从父节点到子节点的路径

        return result;
    }
}
