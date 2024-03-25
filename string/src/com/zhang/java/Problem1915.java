package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/3/19 09:04
 * @Author zsy
 * @Description 最美子字符串的数目 类比Problem1177、Problem1371、Problem1457、Problem1542、Problem2791 前缀和类比 状态压缩类比
 * 如果某个字符串中 至多一个 字母出现 奇数 次，则称其为 最美 字符串。
 * 例如，"ccjjc" 和 "abab" 都是最美字符串，但 "ab" 不是。
 * 给你一个字符串 word ，该字符串由前十个小写英文字母组成（'a' 到 'j'）。
 * 请你返回 word 中 最美非空子字符串 的数目。
 * 如果同样的子字符串在 word 中出现多次，那么应当对 每次出现 分别计数。
 * 子字符串 是字符串中的一个连续字符序列。
 * <p>
 * 输入：word = "aba"
 * 输出：4
 * 解释：4 个最美子字符串如下所示：
 * - "aba" -> "a"
 * - "aba" -> "b"
 * - "aba" -> "a"
 * - "aba" -> "aba"
 * <p>
 * 输入：word = "aabb"
 * 输出：9
 * 解释：9 个最美子字符串如下所示：
 * - "aabb" -> "a"
 * - "aabb" -> "aa"
 * - "aabb" -> "aab"
 * - "aabb" -> "aabb"
 * - "aabb" -> "a"
 * - "aabb" -> "abb"
 * - "aabb" -> "b"
 * - "aabb" -> "bb"
 * - "aabb" -> "b"
 * <p>
 * 输入：word = "he"
 * 输出：2
 * 解释：2 个最美子字符串如下所示：
 * - "he" -> "h"
 * - "he" -> "e"
 * <p>
 * 1 <= word.length <= 10^5
 * word 由从 'a' 到 'j' 的小写英文字母组成
 */
public class Problem1915 {
    public static void main(String[] args) {
        Problem1915 problem1915 = new Problem1915();
        String word = "aabb";
        System.out.println(problem1915.wonderfulSubstrings(word));
        System.out.println(problem1915.wonderfulSubstrings2(word));
    }

    /**
     * 前缀和
     * preSum[i][j]：word[0]-word[i-1]中字符'a'+j出现的次数
     * 时间复杂度O(n^2*|Σ|)=O(n^2)，空间复杂度O(n*|Σ|)=O(n) (n=word.length()，|Σ|=10，只包含前10个小写字母)
     *
     * @param word
     * @return
     */
    public long wonderfulSubstrings(String word) {
        //前缀和数组，只包含前10个小写字母
        int[][] preSum = new int[word.length() + 1][10];

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            for (int j = 0; j < 10; j++) {
                if (c == 'a' + j) {
                    preSum[i + 1][j] = preSum[i][j] + 1;
                } else {
                    preSum[i + 1][j] = preSum[i][j];
                }
            }
        }

        //最美非空子字符串的个数，即word中出现次数为奇数的字符最多只能有1个
        long result = 0;

        for (int i = 0; i < word.length(); i++) {
            //s[i]-s[j]中出现次数为奇数的字符小于等于1，则s[i]-s[j]为最美非空子字符串
            for (int j = i; j < word.length(); j++) {
                //s[i]-s[j]中出现次数为奇数的字符个数
                int oddCount = 0;

                for (int k = 0; k < 10; k++) {
                    //s[i]-s[j]中字符'a'+k出现的次数
                    int count = preSum[j + 1][k] - preSum[i][k];

                    if (count % 2 == 1) {
                        oddCount++;
                    }
                }

                //s[i]-s[j]中出现次数为奇数的字符个数小于等于1，则s[i]-s[j]为最美非空子字符串
                if (oddCount <= 1) {
                    result++;
                }
            }
        }

        return result;
    }

    /**
     * 前缀和+哈希表+二进制状态压缩
     * preSum[i]：s[0]-s[i]中字符出现的奇偶次数二进制表示的数，0：当前字符出现偶数次，1：当前字符出现奇数次
     * preSum[i]^preSum[j]：s[i+1]-s[j]中字符出现的奇偶次数二进制表示的数
     * 时间复杂度O(n*|Σ|)=O(n)，空间复杂度O(min(n,2^|Σ|)) (n=s.length()，|Σ|=10，只包含前10个小写字母)
     * (前10个小写字母，即最多只有2^|Σ|情况)
     * <p>
     * 例如：word="aabb"
     * 'a'出现2次，则从右往左第0位为0
     * 'b'出现2次，则从右往左第0位为0
     * 则word[0]-word[n-1]中字符出现的奇偶次数二进制表示的数为00
     *
     * @param word
     * @return
     */
    public long wonderfulSubstrings2(String word) {
        //最美非空子字符串的个数，即word中出现次数为奇数的字符最多只能有1个
        long result = 0;
        //key：已经遍历过从s[0]起始的子串中字符出现的奇偶次数二进制表示的数，value：key出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，空串二进制表示的数为0，空串出现的次数为1
        map.put(0, 1);
        //s[0]-s[i]中字符出现的奇偶次数二进制表示的数
        int preSum = 0;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            //(1<<(c-'a'))：当前字符c存储在二进制表示的从右往左第(c-'a')位
            //注意：异或操作可以立刻得到当前字符c在当前位的奇偶次数
            preSum = preSum ^ (1 << (c - 'a'));

            //map中存在从s[0]起始的子串中字符出现的奇偶次数二进制表示的数preSum，
            //则s[map.get(preSum)+1]-s[i]中字符出现的次数均为偶数，则s[map.get(preSum)+1]-s[i]为最美非空子字符串
            if (map.containsKey(preSum)) {
                result = result + map.get(preSum);
            }

            //map中存在从s[0]起始的子串中字符出现的奇偶次数二进制表示的数preSum^(1<<j)，即二进制表示的数preSum从右往左的第j位奇偶性取反，
            //则s[map.get(preSum^(1<<j))+1]-s[i]中字符出现的次数为奇数的字符只有1个，则s[map.get(preSum^(1<<j))+1]-s[i]为超赞子字符串
            for (int j = 0; j < 10; j++) {
                if (map.containsKey(preSum ^ (1 << j))) {
                    result = result + map.get(preSum ^ (1 << j));
                }
            }

            //map中更新preSum
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return result;
    }
}
