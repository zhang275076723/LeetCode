package com.zhang.java;


import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/3/17 09:11
 * @Author zsy
 * @Description 找出最长的超赞子字符串 类比Problem1177、Problem1371、Problem1457、Problem1915、Problem1930、Problem2791 前缀和类比 状态压缩类比 回文类比
 * 给你一个字符串 s 。
 * 请返回 s 中最长的 超赞子字符串 的长度。
 * 「超赞子字符串」需满足满足下述两个条件：
 * 该字符串是 s 的一个非空子字符串
 * 进行任意次数的字符交换后，该字符串可以变成一个回文字符串
 * <p>
 * 输入：s = "3242415"
 * 输出：5
 * 解释："24241" 是最长的超赞子字符串，交换其中的字符后，可以得到回文 "24142"
 * <p>
 * 输入：s = "12345678"
 * 输出：1
 * <p>
 * 输入：s = "213123"
 * 输出：6
 * 解释："213123" 是最长的超赞子字符串，交换其中的字符后，可以得到回文 "231132"
 * <p>
 * 输入：s = "00"
 * 输出：2
 * <p>
 * 1 <= s.length <= 10^5
 * s 仅由数字组成
 */
public class Problem1542 {
    public static void main(String[] args) {
        Problem1542 problem1542 = new Problem1542();
        String s = "3242415";
        System.out.println(problem1542.longestAwesome(s));
        System.out.println(problem1542.longestAwesome2(s));
    }

    /**
     * 前缀和
     * preSum[i][j]：s[0]-s[i-1]中数字j出现的次数
     * 子串重新排列后出现次数为奇数的数字个数小于等于1，则当前子串为超赞子字符串
     * 时间复杂度O(n^2*|Σ|)=O(n^2) ，空间复杂度O(n*|Σ|)=O(n) (n=s.length()，|Σ|=10，只包含0-9的数字)
     *
     * @param s
     * @return
     */
    public int longestAwesome(String s) {
        //前缀和数组
        int[][] preSum = new int[s.length() + 1][10];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            for (int j = 0; j < 10; j++) {
                if (c - '0' == j) {
                    preSum[i + 1][j] = preSum[i][j] + 1;
                } else {
                    preSum[i + 1][j] = preSum[i][j];
                }
            }
        }

        //最长超赞子字符串的长度，初始化为1
        int maxLen = 1;

        for (int i = 0; i < s.length(); i++) {
            //s[i]-s[j]重新排列后出现次数为奇数的数字个数小于等于1，则s[i]-s[j]为超赞子字符串
            //注意：j不需要从i开始，因为只有一个数字的字符串都为超赞子字符串
            for (int j = i + 1; j < s.length(); j++) {
                //s[i]-s[j]中出现次数为奇数的数字个数
                int oddCount = 0;

                for (int k = 0; k < 10; k++) {
                    //s[i]-s[j]中数字k出现的次数
                    int count = preSum[j + 1][k] - preSum[i][k];

                    if (count % 2 == 1) {
                        oddCount++;
                    }
                }

                //s[i]-s[j]重新排列后出现次数为奇数的数字个数小于等于1，则s[i]-s[j]为超赞子字符串
                if (oddCount <= 1) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    /**
     * 前缀和+哈希表+二进制状态压缩
     * preSum[i]：s[0]-s[i]中数字出现的奇偶次数二进制表示的数，0：当前字符出现偶数次，1：当前字符出现奇数次
     * preSum[i]^preSum[j]：s[i+1]-s[j]中数字出现的奇偶次数二进制表示的数
     * 子串重新排列后出现次数为奇数的数字个数小于等于1，则当前子串为超赞子字符串
     * 时间复杂度O(n*|Σ|)=O(n)，空间复杂度O(min(n,2^|Σ|)) (n=s.length()，|Σ|=10，只包含0-9的数字)
     * (0-9共10个数字，即最多只有2^|Σ|情况)
     * <p>
     * 例如：s="3242415"
     * 数字1出现1次，则从右往左第0位为1
     * 数字2出现2次，则从右往左第0位为0
     * 数字3出现1次，则从右往左第0位为1
     * 数字4出现2次，则从右往左第0位为0
     * 数字5出现1次，则从右往左第0位为1
     * 则s[0]-s[n-1]中数字出现的奇偶次数二进制表示的数为10101
     *
     * @param s
     * @return
     */
    public int longestAwesome2(String s) {
        //最长超赞子字符串的长度，初始化为1
        int maxLen = 1;
        //key：已经遍历过从s[0]起始的子串中数字出现的奇偶次数二进制表示的数，value：key第一次出现的下标索引
        Map<Integer, Integer> map = new HashMap<>();
        //初始化，空串二进制表示的数为0，空串第一次出现的下标索引为-1
        map.put(0, -1);
        //s[0]-s[i]中数字出现的奇偶次数二进制表示的数
        int preSum = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //(1<<(c-'0'))：当前数字c存储在二进制表示的从右往左第(c-'0')位
            //注意：异或操作可以立刻得到当前字符c在当前位的奇偶次数
            preSum = preSum ^ (1 << (c - '0'));

            //map中存在从s[0]起始的子串中数字出现的奇偶次数二进制表示的数preSum，
            //则s[map.get(preSum)+1]-s[i]中数字出现的次数均为偶数，则s[map.get(preSum)+1]-s[i]为超赞子字符串
            if (map.containsKey(preSum)) {
                maxLen = Math.max(maxLen, i - map.get(preSum));
            } else {
                //map中不存在从s[0]起始的子串中数字出现的奇偶次数二进制表示的数preSum，则preSum加入map
                map.put(preSum, i);
            }

            //map中存在从s[0]起始的子串中数字出现的奇偶次数二进制表示的数preSum^(1<<j)，即二进制表示的数preSum从右往左的第j位奇偶性取反，
            //则s[map.get(preSum^(1<<j))+1]-s[i]中数字出现的次数为奇数的数字只有1个，则s[map.get(preSum^(1<<j))+1]-s[i]为超赞子字符串
            for (int j = 0; j < 10; j++) {
                if (map.containsKey(preSum ^ (1 << j))) {
                    maxLen = Math.max(maxLen, i - map.get(preSum ^ (1 << j)));
                }
            }
        }

        return maxLen;
    }
}
