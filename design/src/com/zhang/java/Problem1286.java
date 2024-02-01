package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2023/11/14 08:09
 * @Author zsy
 * @Description 字母组合迭代器 类比Problem38、Problem60、Problem481、Problem667 迭代器类比Problem173、Problem251、Problem281、Problem284、Problem341、Problem604、Problem900、Problem1586
 * 请你设计一个迭代器类 CombinationIterator ，包括以下内容：
 * CombinationIterator(string characters, int combinationLength) 一个构造函数，
 * 输入参数包括：用一个 有序且字符唯一 的字符串 characters（该字符串只包含小写英文字母）和一个数字 combinationLength 。
 * 函数 next() ，按 字典序 返回长度为 combinationLength 的下一个字母组合。
 * 函数 hasNext() ，只有存在长度为 combinationLength 的下一个字母组合时，才返回 true
 * <p>
 * 输入:
 * ["CombinationIterator", "next", "hasNext", "next", "hasNext", "next", "hasNext"]
 * [["abc", 2], [], [], [], [], [], []]
 * 输出：
 * [null, "ab", true, "ac", true, "bc", false]
 * 解释：
 * CombinationIterator iterator = new CombinationIterator("abc", 2); // 创建迭代器 iterator
 * iterator.next(); // 返回 "ab"
 * iterator.hasNext(); // 返回 true
 * iterator.next(); // 返回 "ac"
 * iterator.hasNext(); // 返回 true
 * iterator.next(); // 返回 "bc"
 * iterator.hasNext(); // 返回 false
 * <p>
 * 1 <= combinationLength <= characters.length <= 15
 * characters 中每个字符都 不同
 * 每组测试数据最多对 next 和 hasNext 调用 10^4次
 * 题目保证每次调用函数 next 时都存在下一个字母组合。
 */
public class Problem1286 {
    public static void main(String[] args) {
        // 创建迭代器 iterator
//        CombinationIterator iterator = new CombinationIterator("abc", 2);
        CombinationIterator2 iterator = new CombinationIterator2("abc", 2);
        // 返回 "ab"
        System.out.println(iterator.next());
        // 返回 true
        System.out.println(iterator.hasNext());
        // 返回 "ac"
        System.out.println(iterator.next());
        // 返回 true
        System.out.println(iterator.hasNext());
        // 返回 "bc"
        System.out.println(iterator.next());
        // 返回 false
        System.out.println(iterator.hasNext());
    }

    /**
     * 回溯+剪枝
     * 时间复杂度O(C(n,k)*k)，空间复杂度O(C(n,k)*k) (n=characters.length()，k=combinationLength)
     * (n个元素中取k个元素，有C(n,k)种取法，每种取法需要O(k)将长度为k的字母组合添加到结果list中)
     */
    static class CombinationIterator {
        //存储长度为k的字母组合的各种情况的集合
        private final List<String> list;
        //当前list遍历到的下标索引
        private int index;

        public CombinationIterator(String characters, int combinationLength) {
            list = new ArrayList<>();
            index = 0;
            backtrack(0, characters, combinationLength, new StringBuilder(), list);
        }

        private void backtrack(int t, String s, int k, StringBuilder sb, List<String> list) {
            if (sb.length() == k) {
                list.add(sb.toString());
                return;
            }

            for (int i = t; i < s.length(); i++) {
                char c = s.charAt(i);
                sb.append(c);
                backtrack(i + 1, s, k, sb, list);
                sb.delete(sb.length() - 1, sb.length());
            }
        }

        public String next() {
            String str = list.get(index);
            index++;
            return str;
        }

        public boolean hasNext() {
            return index < list.size();
        }
    }

    /**
     * 记录当前遍历到的长度为k的字母组合在characters中的下标索引数组，在获取下一个长度为k的字母组合时，从后往前遍历数组，
     * 如果数组当前位置arr[i]可以右移，则arr[i]右移一位，变为arr[i]+1，arr[i]后面所有arr都需要沿着arr[i]右移1位，
     * 得到下一个长度为k的字母组合；如果数组所有位置都不能移动，则不存在下一个长度为k的字母组合，标志位flag置为false
     * 时间复杂度O(k)，空间复杂度O(k) (n=characters.length()，k=combinationLength)
     * <p>
     * 例如：characters="abcdef"，combinationLength=4，此时arr=[1,2,3,5]，即当前遍历到的长度为k的字母组合为bcdf，
     * 从后往前遍历arr，arr[3]=5，不能右移，arr[2]=3，可以右移，则arr[2]右移一位，变为4，
     * 则arr[2]后面所有arr都需要沿着arr[2]右移1位，arr[3]右移为5，此时arr=[1,2,4,5]，得到下一个长度为k的字母组合为bcef
     */
    static class CombinationIterator2 {
        //存储字符串characters
        private final String str;
        //当前遍历到的长度为k的字母组合在characters中的下标索引数组
        private final int[] arr;
        //是否存在下一个长度为k的字母组合标志位
        private boolean flag;

        public CombinationIterator2(String characters, int combinationLength) {
            str = characters;
            arr = new int[combinationLength];
            flag = true;

            //初始化第一个长度为k的字母组合
            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }
        }

        public String next() {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < arr.length; i++) {
                sb.append(str.charAt(arr[i]));
            }

            //从后往前可以右移的arr[j]，初始化为-1，表示不存在下一个长度为k的字母组合
            int j = -1;

            //从后往前找arr中可以右移的arr[i]，使arr指向下一个长度为k的字母组合在characters中的下标索引
            for (int i = arr.length - 1; i >= 0; i--) {
                //arr[i]最多能到达的下标索引为str.length()-arr.length+i，
                //如果当前位置arr[i]可以右移，则找到了可以右移的arr[i]，直接跳出循环
                if (arr[i] != str.length() - arr.length + i) {
                    j = i;
                    break;
                }
            }

            //不存在下一个长度为k的字母组合，flag置为false
            if (j == -1) {
                flag = false;
                return sb.toString();
            } else {
                //存在下一个长度为k的字母组合，arr[i]右移一位，变为arr[i]+1，
                //arr[i]后面所有arr都需要沿着arr[i]右移1位，得到下一个长度为k的字母组合

                arr[j]++;

                for (int i = j + 1; i < arr.length; i++) {
                    arr[i] = arr[i - 1] + 1;
                }

                return sb.toString();
            }
        }

        public boolean hasNext() {
            return flag;
        }
    }
}
