package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/3/8 08:13
 * @Author zsy
 * @Description 构建回文串检测 类比Problem392 前缀和类比 状态压缩类比 回文类比Problem5、Problem9、Problem125、Problem131、Problem132、Problem214、Problem234、Problem266、Problem267、Problem336、Problem409、Problem479、Problem516、Problem647、Problem680、Problem866、Problem1147、Problem1312、Problem1328、Problem1332、Problem1400
 * 给你一个字符串 s，请你对 s 的子串进行检测。
 * 每次检测，待检子串都可以表示为 queries[i] = [left, right, k]。
 * 我们可以 重新排列 子串 s[left], ..., s[right]，并从中选择 最多 k 项替换成任何小写英文字母。
 * 如果在上述检测过程中，子串可以变成回文形式的字符串，那么检测结果为 true，否则结果为 false。
 * 返回答案数组 answer[]，其中 answer[i] 是第 i 个待检子串 queries[i] 的检测结果。
 * 注意：在替换时，子串中的每个字母都必须作为 独立的 项进行计数，也就是说，
 * 如果 s[left..right] = "aaa" 且 k = 2，我们只能替换其中的两个字母。
 * （另外，任何检测都不会修改原始字符串 s，可以认为每次检测都是独立的）
 * <p>
 * 输入：s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
 * 输出：[true,false,false,true,true]
 * 解释：
 * queries[0] : 子串 = "d"，回文。
 * queries[1] : 子串 = "bc"，不是回文。
 * queries[2] : 子串 = "abcd"，只替换 1 个字符是变不成回文串的。
 * queries[3] : 子串 = "abcd"，可以变成回文的 "abba"。 也可以变成 "baab"，先重新排序变成 "bacd"，然后把 "cd" 替换为 "ab"。
 * queries[4] : 子串 = "abcda"，可以变成回文的 "abcba"。
 * <p>
 * 1 <= s.length, queries.length <= 10^5
 * 0 <= queries[i][0] <= queries[i][1] < s.length
 * 0 <= queries[i][2] <= s.length
 * s 中只有小写英文字母
 */
public class Problem1177 {
    public static void main(String[] args) {
        Problem1177 problem1177 = new Problem1177();
        String s = "abcda";
        int[][] queries = {{3, 3, 0}, {1, 2, 0}, {0, 3, 1}, {0, 3, 2}, {0, 4, 1}};
        System.out.println(problem1177.canMakePaliQueries(s, queries));
        System.out.println(problem1177.canMakePaliQueries2(s, queries));
    }

    /**
     * 前缀和
     * preSum[i][j]：s[0]-s[i-1]中字符'a'+j出现的次数
     * 当前子串出现次数为偶数的字符有a种，则这a种字符都可以构成回文；
     * 当前子串出现次数为奇数的字符有b种，需要修改b/2个字符，则这b种字符都可以构成回文
     * 注意：子串可以重新排列
     * 时间复杂度O(n*|Σ|+m*|Σ|)=O(n+m)，空间复杂度O(n*|Σ|)=O(n) (n=s.length()，m=queries.length，|Σ|=26，只包含小写字母)
     * <p>
     * 例如：如果当前子串出现次数为奇数的字符只有a，即修改1/2=0个字符，则不需要修改字符；
     * 如果当前子串出现次数为奇数的字符有a、b，即修改2/2=1个字符，则只需要将一个b修改成a就可以构成回文；
     * 如果当前子串出现次数为奇数的字符有a、b、c，即修改3/2=1个字符，则只需要将一个b修改成a就可以构成回文；
     * 如果当前子串出现次数为奇数的字符有a、b、c、d，即修改4/2=2个字符，则只需要将一个b修改成a，一个d修改成c就可以构成回文；
     *
     * @param s
     * @param queries
     * @return
     */
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        //前缀和数组
        int[][] preSum = new int[s.length() + 1][26];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            for (int j = 0; j < 26; j++) {
                //c等于字符'a'+j，则s[0]-s[i]中字符'a'+j出现的次数等于s[0]-s[i-1]中字符'a'+j出现的次数加1
                if (c == 'a' + j) {
                    preSum[i + 1][j] = preSum[i][j] + 1;
                } else {
                    //c不等于字符'a'+j，则s[0]-s[i]中字符'a'+j出现的次数等于s[0]-s[i-1]中字符'a'+j出现的次数
                    preSum[i + 1][j] = preSum[i][j];
                }
            }
        }

        List<Boolean> list = new ArrayList<>();

        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            //可以修改的字符个数
            int k = queries[i][2];
            //s[left]-s[right]中出现次数为奇数的字符个数
            int oddCount = 0;

            for (int j = 0; j < 26; j++) {
                //s[left]-s[right]中字符'a'+j出现的次数
                int count = preSum[right + 1][j] - preSum[left][j];

                if (count % 2 == 1) {
                    oddCount++;
                }
            }

            //可以修改的字符个数大于等于oddCount/2，则s[left]-s[right]经过重新排序和修改后可以构成回文，list添加true
            if (k >= oddCount / 2) {
                list.add(true);
            } else {
                list.add(false);
            }
        }

        return list;
    }

    /**
     * 前缀和+二进制状态压缩
     * preSum[i]：s[0]-s[i-1]中字符出现的奇偶次数二进制表示的数，0：当前字符出现偶数次，1：当前字符出现奇数次
     * 当前子串出现次数为偶数的字符有a种，则这a种字符都可以构成回文；
     * 当前子串出现次数为奇数的字符有b种，需要修改b/2个字符，则这b种字符都可以构成回文
     * 注意：子串可以重新排列
     * 时间复杂度O(n+m)，空间复杂度O(n) (n=s.length()，m=queries.length，|Σ|=26，只包含小写字母)
     * <p>
     * 例如：如果当前子串出现次数为奇数的字符只有a，即修改1/2=0个字符，则不需要修改字符；
     * 如果当前子串出现次数为奇数的字符有a、b，即修改2/2=1个字符，则只需要将一个b修改成a就可以构成回文；
     * 如果当前子串出现次数为奇数的字符有a、b、c，即修改3/2=1个字符，则只需要将一个b修改成a就可以构成回文；
     * 如果当前子串出现次数为奇数的字符有a、b、c、d，即修改4/2=2个字符，则只需要将一个b修改成a，一个d修改成c就可以构成回文；
     * <p>
     * 例如：s=abcda
     * preSum[1]=0001(a出现1次，则二进制表示的从右往左第0位为1)
     * preSum[2]=0011(b出现1次，则二进制表示的从右往左第1位为1)
     * preSum[3]=0111(c出现1次，则二进制表示的从右往左第2位为1)
     * preSum[4]=1111(d出现1次，则二进制表示的从右往左第3位为1)
     * preSum[5]=1110(a出现2次，则二进制表示的从右往左第0位为0)
     *
     * @param s
     * @param queries
     * @return
     */
    public List<Boolean> canMakePaliQueries2(String s, int[][] queries) {
        //前缀和数组
        //s[0]-s[i-1]中字符出现的奇偶次数二进制表示的数，0：当前字符出现偶数次，1：当前字符出现奇数次
        int[] preSum = new int[s.length() + 1];

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            //(1<<(c-'a'))：当前字符c存储在二进制表示的从右往左第(c-'a')位
            //注意：异或操作可以立刻得到当前字符c在当前位的奇偶次数
            preSum[i + 1] = preSum[i] ^ (1 << (c - 'a'));
        }

        List<Boolean> list = new ArrayList<>();

        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            //可以修改的字符个数
            int k = queries[i][2];
            //s[left]-s[right]中出现次数为奇数的字符个数
            int oddCount = 0;
            //s[left]-s[right]中字符出现的奇偶次数二进制表示的数
            //例如：value=10010，即表示字符b和字符e出现次数为奇数
            int value = preSum[right + 1] ^ preSum[left];

            //统计value二进制表示的数中出现次数为奇数的字符个数
            while (value != 0) {
                oddCount = oddCount + (value & 1);
                value = value >>> 1;
            }

            //可以修改的字符个数大于等于oddCount/2，则s[left]-s[right]经过重新排序和修改后可以构成回文，list添加true
            if (k >= oddCount / 2) {
                list.add(true);
            } else {
                list.add(false);
            }
        }

        return list;
    }
}
