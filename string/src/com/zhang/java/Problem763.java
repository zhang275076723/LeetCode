package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2022/11/19 17:20
 * @Author zsy
 * @Description 划分字母区间 美团机试题 类比Problem56、Problem57、Problem252、Problem253、Problem406、Problem435
 * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
 * 返回一个表示每个字符串片段的长度的列表。
 * <p>
 * 输入：S = "ababcbacadefegdehijhklij"
 * 输出：[9,7,8]
 * 解释：
 * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
 * 每个字母最多出现在一个片段中。
 * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
 * <p>
 * S的长度在[1, 500]之间。
 * S只包含小写字母 'a' 到 'z' 。
 */
public class Problem763 {
    public static void main(String[] args) {
        Problem763 problem763 = new Problem763();
        String s = "ababcbacadefegdehijhklij";
        System.out.println(problem763.partitionLabels(s));
    }

    /**
     * 统计字符最后一次出现下标索引，作为一个区间的右边界，遍历区间内字符，更新右边界，得到可以合并的最大区间
     * 时间复杂度O(n)，空间复杂度O(|Σ|) (|Σ| = 26，s只包含小写字母，共26个字符)
     *
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        //key：字符，value：字符最后一次出现下标索引
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, i);
        }

        List<Integer> list = new ArrayList<>();
        int start = 0;
        int end = map.get(s.charAt(0));

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            end = Math.max(end, map.get(c));

            //到达当前区间的右边界
            if (end == i) {
                list.add(end - start + 1);
                start = end + 1;
            }
        }

        return list;
    }
}
