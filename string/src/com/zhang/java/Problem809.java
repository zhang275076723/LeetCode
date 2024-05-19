package com.zhang.java;

/**
 * @Date 2024/5/10 08:46
 * @Author zsy
 * @Description 情感丰富的文字 双指针类比
 * 有时候人们会用重复写一些字母来表示额外的感受，比如 "hello" -> "heeellooo", "hi" -> "hiii"。
 * 我们将相邻字母都相同的一串字符定义为相同字母组，例如："h", "eee", "ll", "ooo"。
 * 对于一个给定的字符串 S ，如果另一个单词能够通过将一些字母组扩张从而使其和 S 相同，我们将这个单词定义为可扩张的（stretchy）。
 * 扩张操作定义如下：选择一个字母组（包含字母 c ），然后往其中添加相同的字母 c 使其长度达到 3 或以上。
 * 例如，以 "hello" 为例，我们可以对字母组 "o" 扩张得到 "hellooo"，
 * 但是无法以同样的方法得到 "helloo" 因为字母组 "oo" 长度小于 3。
 * 此外，我们可以进行另一种扩张 "ll" -> "lllll" 以获得 "helllllooo"。
 * 如果 s = "helllllooo"，那么查询词 "hello" 是可扩张的，
 * 因为可以对它执行这两种扩张操作使得 query = "hello" -> "hellooo" -> "helllllooo" = s。
 * 输入一组查询单词，输出其中可扩张的单词数量。
 * <p>
 * 输入：
 * s = "heeellooo"
 * words = ["hello", "hi", "helo"]
 * 输出：1
 * 解释：
 * 我们能通过扩张 "hello" 的 "e" 和 "o" 来得到 "heeellooo"。
 * 我们不能通过扩张 "helo" 来得到 "heeellooo" 因为 "ll" 的长度小于 3 。
 * <p>
 * 1 <= s.length, words.length <= 100
 * 1 <= words[i].length <= 100
 * s 和所有在 words 中的单词都只由小写字母组成。
 */
public class Problem809 {
    public static void main(String[] args) {
        Problem809 problem809 = new Problem809();
//        String s = "heeellooo";
//        String[] words = {"hello", "hi", "helo"};
        String s = "aaa";
        String[] words = {"aaaa"};
        System.out.println(problem809.expressiveWords(s, words));
    }

    /**
     * 双指针
     * 如果字符c要扩展，扩展之后相同字符c的长度必须大于等于3
     * 时间复杂度O(m(n+l))，空间复杂度O(1) (n=s.length()，m=words.length，l=words[i]的平均长度)
     *
     * @param s
     * @param words
     * @return
     */
    public int expressiveWords(String s, String[] words) {
        int count = 0;

        for (String word : words) {
            //s的下标索引
            int i = 0;
            //word的下标索引
            int j = 0;
            //已经遍历过的i、j，word能否扩张为s标志位
            boolean flag = true;

            while (i < s.length() && j < word.length()) {
                //s[i]和word[j]不相等，则word无法扩张为s，直接跳出循环
                if (s.charAt(i) != word.charAt(j)) {
                    flag = false;
                    break;
                }

                char c = s.charAt(i);
                //从s[i]开始相同字符c出现的次数
                int count1 = 0;
                //从word[j]开始相同字符c出现的次数
                int count2 = 0;

                while (i < s.length() && c == s.charAt(i)) {
                    count1++;
                    i++;
                }

                while (j < word.length() && c == word.charAt(j)) {
                    count2++;
                    j++;
                }

                //从s[i]开始相同字符c出现的次数小于从word[i]开始相同字符c出现的次数，
                //或者从s[i]开始相同字符c出现的次数大于从word[i]开始相同字符c出现的次数，并且从s[i]开始相同字符c出现的次数小于3，
                //则word无法扩张为s，直接跳出循环
                if (count1 < count2 || (count1 > count2 && count1 < 3)) {
                    flag = false;
                    break;
                }
            }

            //s和word都遍历结束，并且flag为true，则word可扩张为s
            if (i == s.length() && j == word.length() && flag) {
                count++;
            }
        }

        return count;
    }
}
