package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/3/18 08:40
 * @Author zsy
 * @Description 每个元音包含偶数次的最长子字符串 元音类比Problem345、Problem824、Problem966、Problem1119、Problem1456、Problem1839 类比Problem1177、Problem1457、Problem1542、Problem1915、Problem2791 前缀和类比 状态压缩类比
 * 给你一个字符串 s ，请你返回满足以下条件的最长子字符串的长度：
 * 每个元音字母，即 'a'，'e'，'i'，'o'，'u' ，在子字符串中都恰好出现了偶数次。
 * <p>
 * 输入：s = "eleetminicoworoep"
 * 输出：13
 * 解释：最长子字符串是 "leetminicowor" ，它包含 e，i，o 各 2 个，以及 0 个 a，u 。
 * <p>
 * 输入：s = "leetcodeisgreat"
 * 输出：5
 * 解释：最长子字符串是 "leetc" ，其中包含 2 个 e 。
 * <p>
 * 输入：s = "bcbcbc"
 * 输出：6
 * 解释：这个示例中，字符串 "bcbcbc" 本身就是最长的，因为所有的元音 a，e，i，o，u 都出现了 0 次。
 * <p>
 * 1 <= s.length <= 5 * 10^5
 * s 只包含小写英文字母。
 */
public class Problem1371 {
    public static void main(String[] args) {
        Problem1371 problem1371 = new Problem1371();
        String s = "leetcodeisgreat";
        System.out.println(problem1371.findTheLongestSubstring(s));
        System.out.println(problem1371.findTheLongestSubstring2(s));
    }

    /**
     * 前缀和
     * preSum[i][j]：s[0]-s[i-1]中第j个元音出现的次数
     * 时间复杂度O(n^2*|Σ|)=O(n^2) ，空间复杂度O(n*|Σ|)=O(n) (n=s.length()，|Σ|=5，只包含5个小写元音)
     *
     * @param s
     * @return
     */
    public int findTheLongestSubstring(String s) {
        //前缀和数组
        int[][] preSum = new int[s.length() + 1][5];
        //5个小写元音字符的集合
        Map<Character, Integer> vowelMap = new HashMap<Character, Integer>() {{
            put('a', 0);
            put('e', 1);
            put('i', 2);
            put('o', 3);
            put('u', 4);
        }};

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            for (int j = 0; j < 5; j++) {
                if (vowelMap.containsKey(c) && vowelMap.get(c) == j) {
                    preSum[i + 1][j] = preSum[i][j] + 1;
                } else {
                    preSum[i + 1][j] = preSum[i][j];
                }
            }
        }

        //元音出现的次数都为偶数的最长子字符串长度
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            //s[i]-s[j]元音出现的次数都为偶数，则更新maxLen
            for (int j = i; j < s.length(); j++) {
                //s[i]-s[j]元音出现的次数是否都为偶数标志位
                boolean flag = true;

                for (int k = 0; k < 5; k++) {
                    //s[i]-s[j]中第k个元音出现的次数
                    int count = preSum[j + 1][k] - preSum[i][k];

                    //s[i]-s[j]中第k个元音出现的次数为奇数，则s[i]-s[j]不满足要求，flag置为false
                    if (count % 2 == 1) {
                        flag = false;
                        break;
                    }
                }

                //s[i]-s[j]元音出现的次数都为偶数，则更新maxLen
                if (flag) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    /**
     * 前缀和+哈希表+二进制状态压缩
     * preSum[i]：s[0]-s[i]中元音出现的奇偶次数二进制表示的数，0：当前字符出现偶数次，1：当前字符出现奇数次
     * preSum[i]^preSum[j]：s[i+1]-s[j]中元音出现的奇偶次数二进制表示的数
     * 时间复杂度O(n)，空间复杂度O(min(n,2^|Σ|)) (n=s.length()，|Σ|=5，只包含5个小写元音)
     * (a、e、i、o、u共5个元音，即最多只有2^|Σ|情况)
     * <p>
     * 例如：s="leetcodeisgreat"
     * 元音a出现1次，则从右往左第0位为1
     * 元音e出现4次，则从右往左第0位为0
     * 元音i出现1次，则从右往左第0位为1
     * 元音o出现1次，则从右往左第0位为1
     * 元音u出现0次，则从右往左第0位为0
     * 则s[0]-s[n-1]中元音出现的奇偶次数二进制表示的数为01101
     *
     * @param s
     * @return
     */
    public int findTheLongestSubstring2(String s) {
        //元音出现的次数都为偶数的最长子字符串长度
        int maxLen = 0;
        //key：已经遍历过从s[0]起始的子串中元音出现的奇偶次数二进制表示的数，value：key第一次出现的下标索引
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，空串元音出现的奇偶次数二进制表示的数为0，空串第一次出现的下标索引为-1
        map.put(0, -1);
        //s[0]-s[i]中元音出现的奇偶次数二进制表示的数
        int preSum = 0;

        //5个小写元音字符的集合
        Map<Character, Integer> vowelMap = new HashMap<Character, Integer>() {{
            put('a', 0);
            put('e', 1);
            put('i', 2);
            put('o', 3);
            put('u', 4);
        }};

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //当前字符为元音，更新preSum
            if (vowelMap.containsKey(c)) {
                //(1<<vowelMap.get(c))：当前元音c存储在二进制表示的从右往左第vowelMap.get(c)位
                //注意：异或操作可以立刻得到当前字符c在当前位的奇偶次数
                preSum = preSum ^ (1 << vowelMap.get(c));
            }

            //map中存在从s[0]起始的子串中元音出现的奇偶次数二进制表示的数preSum，
            //则s[map.get(preSum)+1]-s[i]中数字出现的次数均为偶数，则s[map.get(preSum)+1]-s[i]满足要求，更新maxLen
            if (map.containsKey(preSum)) {
                maxLen = Math.max(maxLen, i - map.get(preSum));
            } else {
                //map中不存在从s[0]起始的子串中元音出现的奇偶次数二进制表示的数preSum，则preSum加入map
                map.put(preSum, i);
            }
        }

        return maxLen;
    }
}
