package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/7/10 08:55
 * @Author zsy
 * @Description 金字塔转换矩阵 类比Problem397、Problem1908 记忆化搜索类比
 * 你正在把积木堆成金字塔。每个块都有一个颜色，用一个字母表示。
 * 每一行的块比它下面的行 少一个块 ，并且居中。
 * 为了使金字塔美观，只有特定的 三角形图案 是允许的。
 * 一个三角形的图案由 两个块 和叠在上面的 单个块 组成。
 * 模式是以三个字母字符串的列表形式 allowed 给出的，其中模式的前两个字符分别表示左右底部块，第三个字符表示顶部块。
 * 例如，"ABC" 表示一个三角形图案，其中一个 “C” 块堆叠在一个 'A' 块(左)和一个 'B' 块(右)之上。
 * 请注意，这与 "BAC" 不同，"B" 在左下角，"A" 在右下角。
 * 你从底部的一排积木 bottom 开始，作为一个单一的字符串，你 必须 使用作为金字塔的底部。
 * 在给定 bottom 和 allowed 的情况下，如果你能一直构建到金字塔顶部，
 * 使金字塔中的 每个三角形图案 都是允许的，则返回 true ，否则返回 false 。
 * <p>
 * 输入：bottom = "BCD", allowed = ["BCC","CDE","CEA","FFF"]
 * 输出：true
 * 解释：允许的三角形模式显示在右边。
 * 从最底层(第3层)开始，我们可以在第2层构建“CE”，然后在第1层构建“E”。
 * 金字塔中有三种三角形图案，分别是“BCC”、“CDE”和“CEA”。都是允许的。
 * <p>
 * 输入：bottom = "AAAA", allowed = ["AAB","AAC","BCD","BBE","DEF"]
 * 输出：false
 * 解释：允许的三角形模式显示在右边。
 * 从最底层(游戏邦注:即第4个关卡)开始，创造第3个关卡有多种方法，但如果尝试所有可能性，你便会在创造第1个关卡前陷入困境。
 * <p>
 * 2 <= bottom.length <= 6
 * 0 <= allowed.length <= 216
 * allowed[i].length == 3
 * 所有输入字符串中的字母来自集合 {'A', 'B', 'C', 'D', 'E', 'F', 'G'}。
 * allowed 中所有值都是 唯一的
 */
public class Problem756 {
    public static void main(String[] args) {
        Problem756 problem756 = new Problem756();
        String bottom = "BCD";
        List<String> allowed = new ArrayList<String>() {{
            add("BCC");
            add("CDE");
            add("CEA");
            add("FFF");
        }};
//        String bottom = "AAAA";
//        List<String> allowed = new ArrayList<String>() {{
//            add("AAB");
//            add("AAC");
//            add("BCD");
//            add("BBE");
//            add("DEF");
//        }};
        System.out.println(problem756.pyramidTransition(bottom, allowed));
    }

    /**
     * 记忆化搜索
     * bottom作为最底层的字符串逐层生成上一层字符串，判断能否构建到金字塔顶部
     * 时间复杂度O(7^2n)，空间复杂度O(n^2) (n=bottom.length())
     * (最底层字符串长度为n，每个位置共有7种字母可以选择，生成上一层共7^(n-1)种不同的字符串，则需要总时间复杂度7^(n-1)*7^(n-2)*...*7^0=O(7^2n))
     * (最底层字符串长度为n，生成上一层字符串长度为n-1，最上层字符串长度为1，则需要总空间复杂度n+n-1+...+1=O(n^2))
     *
     * @param bottom
     * @param allowed
     * @return
     */
    public boolean pyramidTransition(String bottom, List<String> allowed) {
        //key：当前层作为最底层的字符串，value：key作为最底层的字符串能否构建到金字塔顶部
        Map<String, Boolean> bottomMap = new HashMap<>();
        //key：allowed[i][0]-allowed[i][1]，value：allowed[i][2]集合
        Map<String, List<String>> convertMap = new HashMap<>();

        for (String s : allowed) {
            String key = s.substring(0, 2);
            String value = s.substring(2);

            if (!convertMap.containsKey(key)) {
                convertMap.put(key, new ArrayList<>());
            }

            convertMap.get(key).add(value);
        }

        return dfs(bottom, bottomMap, convertMap);
    }

    /**
     * 返回bottom作为最底层的字符串能否构建到金字塔顶部
     *
     * @param bottom
     * @param bottomMap
     * @param convertMap
     * @return
     */
    private boolean dfs(String bottom, Map<String, Boolean> bottomMap, Map<String, List<String>> convertMap) {
        //之前已经得到bottom作为最底层的字符串能否构建到金字塔顶部，直接返回bottomMap.get(bottom)
        if (bottomMap.containsKey(bottom)) {
            return bottomMap.get(bottom);
        }

        //bottom长度为1，则能构建到金字塔顶部，返回true
        if (bottom.length() == 1) {
            bottomMap.put(bottom, true);
            return true;
        }

        //bottom上一层中每个位置可以生成的字母集合
        List<List<String>> list = new ArrayList<>();

        //根据当前层bottom得到上一层中的字母集合
        for (int i = 0; i < bottom.length() - 1; i++) {
            String s = bottom.substring(i, i + 2);

            //当前层无法得到上一层字母，则不能构建到金字塔顶部，返回false
            if (!convertMap.containsKey(s)) {
                bottomMap.put(s, false);
                return false;
            }

            list.add(convertMap.get(s));
        }

        //bottom上一层字符串集合
        List<String> bottomList = new ArrayList<>();

        buildBottomStr(0, new StringBuilder(), bottomList, list);

        for (String nextBottom : bottomList) {
            //nextBottom作为最底层的字符串能构建到金字塔顶部，返回true
            if (dfs(nextBottom, bottomMap, convertMap)) {
                bottomMap.put(nextBottom, true);
                return true;
            }
        }

        //遍历结束，则bottom作为最底层的字符串不能构建到金字塔顶部，返回false
        bottomMap.put(bottom, false);
        return false;
    }

    /**
     * 根据bottom上一层中每个位置可以生成的字母集合list，生成bottom上一层字符串集合bottomList
     *
     * @param t
     * @param sb
     * @param bottomList
     * @param list
     */
    private void buildBottomStr(int t, StringBuilder sb, List<String> bottomList, List<List<String>> list) {
        if (t == list.size()) {
            bottomList.add(sb.toString());
            return;
        }

        for (String str : list.get(t)) {
            sb.append(str);
            buildBottomStr(t + 1, sb, bottomList, list);
            //str的长度为1
            sb.delete(sb.length() - str.length(), sb.length());
        }
    }
}
