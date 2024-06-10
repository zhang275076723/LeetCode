package com.zhang.java;

/**
 * @Date 2024/1/2 08:50
 * @Author zsy
 * @Description 最长特殊序列 II 类比Problem521 类比Problem392、Problem524、Problem792、Problem1023 双指针类比 子序列和子数组类比
 * 给定字符串列表 strs ，返回其中 最长的特殊序列 的长度。如果最长特殊序列不存在，返回 -1 。
 * 特殊序列 定义如下：该序列为某字符串 独有的子序列（即不能是其他字符串的子序列）。
 * s 的 子序列可以通过删去字符串 s 中的某些字符实现。
 * 例如，"abc" 是 "aebdc" 的子序列，因为您可以删除"aebdc"中的下划线字符来得到 "abc" 。
 * "aebdc"的子序列还包括"aebdc"、 "aeb" 和 "" (空字符串)。
 * <p>
 * 输入: strs = ["aba","cdc","eae"]
 * 输出: 3
 * <p>
 * 输入: strs = ["aaa","aaa","aa"]
 * 输出: -1
 * <p>
 * 2 <= strs.length <= 50
 * 1 <= strs[i].length <= 10
 * strs[i] 只包含小写英文字母
 */
public class Problem522 {
    public static void main(String[] args) {
        Problem522 problem522 = new Problem522();
        String[] strs = {"aba", "cdc", "eae"};
        System.out.println(problem522.findLUSlength(strs));
    }

    /**
     * 双指针
     * 字符串s不是其他字符串的子序列，则字符串s是一个特殊序列
     * 时间复杂度O(n^2*m)，空间复杂度O(1) (n=strs.length，m=max(strs[i].length()))
     *
     * @param strs
     * @return
     */
    public int findLUSlength(String[] strs) {
        //初始化最长特殊序列长度为-1，表示不存在最长特殊序列
        int len = -1;

        //strs[i]不是其他字符串的子序列，则字符串strs[i]是一个特殊序列
        for (int i = 0; i < strs.length; i++) {
            //strs[i]是否是其他字符串的子序列的标志位
            boolean flag = false;

            for (int j = 0; j < strs.length; j++) {
                if (i != j && isSubSequence(strs[i], strs[j])) {
                    flag = true;
                    break;
                }
            }

            //strs[i]不是其他字符串的子序列，则字符串strs[i]是一个特殊序列，更新最长特殊序列长度
            if (!flag) {
                len = Math.max(len, strs[i].length());
            }
        }

        return len;
    }

    /**
     * 双指针判断str1是否是str2的子序列
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param str1
     * @param str2
     * @return
     */
    private boolean isSubSequence(String str1, String str2) {
        if (str1.length() > str2.length()) {
            return false;
        }

        //字符串str1的下标索引
        int i = 0;

        for (int j = 0; j < str2.length(); j++) {
            if (str1.charAt(i) == str2.charAt(j)) {
                i++;
            }

            //index已经遍历完，则str1是str2的子序列，返回true
            if (i == str1.length()) {
                return true;
            }
        }

        //遍历结束，则str1不是str2的子序列，返回false
        return false;
    }
}
