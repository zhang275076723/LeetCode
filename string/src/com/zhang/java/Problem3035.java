package com.zhang.java;

import java.util.Arrays;

/**
 * @Date 2024/4/17 08:26
 * @Author zsy
 * @Description 回文字符串的最大数量 类比Problem266、Problem267、Problem409、Problem2131、Problem2384 回文类比
 * 给你一个下标从 0 开始的字符串数组 words ，数组的长度为 n ，且包含下标从 0 开始的若干字符串。
 * 你可以执行以下操作 任意 次数（包括零次）：
 * 选择整数i、j、x和y，满足0 <= i, j < n，0 <= x < words[i].length，0 <= y < words[j].length，
 * 交换 字符 words[i][x] 和 words[j][y] 。
 * 返回一个整数，表示在执行一些操作后，words 中可以包含的回文串的 最大 数量。
 * 注意：在操作过程中，i 和 j 可以相等。
 * <p>
 * 输入：words = ["abbb","ba","aa"]
 * 输出：3
 * 解释：在这个例子中，获得最多回文字符串的一种方式是：
 * 选择 i = 0, j = 1, x = 0, y = 0，交换 words[0][0] 和 words[1][0] 。words 变成了 ["bbbb","aa","aa"] 。
 * words 中的所有字符串都是回文。
 * 因此，可实现的回文字符串的最大数量是 3 。
 * <p>
 * 输入：words = ["abc","ab"]
 * 输出：2
 * 解释：在这个例子中，获得最多回文字符串的一种方式是：
 * 选择 i = 0, j = 1, x = 1, y = 0，交换 words[0][1] 和 words[1][0] 。words 变成了 ["aac","bb"] 。
 * 选择 i = 0, j = 0, x = 1, y = 2，交换 words[0][1] 和 words[0][2] 。words 变成了 ["aca","bb"] 。
 * 两个字符串都是回文 。
 * 因此，可实现的回文字符串的最大数量是 2。
 * <p>
 * 输入：words = ["cd","ef","a"]
 * 输出：1
 * 解释：在这个例子中，没有必要执行任何操作。
 * words 中有一个回文 "a" 。
 * 可以证明，在执行任何次数操作后，无法得到更多回文。
 * 因此，答案是 1 。
 * <p>
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 100
 * words[i] 仅由小写英文字母组成。
 */
public class Problem3035 {
    public static void main(String[] args) {
        Problem3035 problem3035 = new Problem3035();
        String[] words = {"abc", "ab"};
//        String[] words = {"a", "b"};
        System.out.println(problem3035.maxPalindromesAfterOperations(words));
    }

    /**
     * 哈希表
     * 核心思想：words[i]、words[j]中字符可以随意交换
     * 统计words[i]每个字符出现的次数，words[i]按照长度由小到大排序，进行回文填充
     * 时间复杂度O(nlogn)，空间复杂度O(max(n,|Σ|)) (n=words.length，|Σ|=26，只包含小写字母)
     *
     * @param words
     * @return
     */
    public int maxPalindromesAfterOperations(String[] words) {
        //只包含26个小写字母的计数数组
        int[] countArr = new int[26];
        //words[i]长度数组
        int[] lengthArr = new int[words.length];

        for (int i = 0; i < words.length; i++) {
            lengthArr[i] = words[i].length();

            for (char c : words[i].toCharArray()) {
                countArr[c - 'a']++;
            }
        }

        //words中出现次数为奇数的字符个数
        int oddCount = 0;
        //words中出现次数为偶数的字符出现次数之和，加上words中出现次数为奇数的字符出现次数减1之和
        int evenCount = 0;

        for (int i = 0; i < 26; i++) {
            //当前字符'a'+i出现次数为奇数
            if (countArr[i] % 2 == 1) {
                oddCount++;
                evenCount = evenCount + countArr[i] - 1;
            } else {
                //当前字符'a'+i出现次数为偶数
                evenCount = evenCount + countArr[i];
            }
        }

        //words[i]长度由小到大排序
        Arrays.sort(lengthArr);

        int count = 0;

        //由小到大判断剩余oddCount和evenCount能否构成lengthArr[i]长度的回文串
        for (int i = 0; i < lengthArr.length; i++) {
            //lengthArr[i]长度为奇数
            if (lengthArr[i] % 2 == 1) {
                //剩余oddCount和evenCount不能构成lengthArr[i]长度的回文串，直接跳出循环
                if (evenCount < lengthArr[i] - 1) {
                    break;
                }

                //中间字符由oddCount组成，两边字符串由evenCount组成
                if (oddCount > 0) {
                    oddCount--;
                    evenCount = evenCount - (lengthArr[i] - 1);
                    count++;
                } else {
                    //中间字符和两边字符串都由evenCount组成，需要拆一对evenCount，则oddCount加1
                    oddCount++;
                    evenCount = evenCount - lengthArr[i];
                    count++;
                }
            } else {
                //lengthArr[i]长度为偶数

                //剩余oddCount和evenCount不能构成lengthArr[i]长度的回文串，直接跳出循环
                if (evenCount < lengthArr[i]) {
                    break;
                }

                evenCount = evenCount - lengthArr[i];
                count++;
            }
        }

        return count;
    }
}
