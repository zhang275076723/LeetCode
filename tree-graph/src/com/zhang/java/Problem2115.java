package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/27 08:17
 * @Author zsy
 * @Description 从给定原材料中找到所有可以做出的菜 拓扑排序类比 图类比
 * 你有 n 道不同菜的信息。给你一个字符串数组 recipes 和一个二维字符串数组 ingredients 。
 * 第 i 道菜的名字为 recipes[i] ，如果你有它 所有 的原材料 ingredients[i] ，那么你可以 做出 这道菜。
 * 一道菜的原材料可能是 另一道 菜，也就是说 ingredients[i] 可能包含 recipes 中另一个字符串。
 * 同时给你一个字符串数组 supplies ，它包含你初始时拥有的所有原材料，每一种原材料你都有无限多。
 * 请你返回你可以做出的所有菜。你可以以 任意顺序 返回它们。
 * 注意两道菜在它们的原材料中可能互相包含。
 * <p>
 * 输入：recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast","flour","corn"]
 * 输出：["bread"]
 * 解释：
 * 我们可以做出 "bread" ，因为我们有原材料 "yeast" 和 "flour" 。
 * <p>
 * 输入：recipes = ["bread","sandwich"], ingredients = [["yeast","flour"],["bread","meat"]], supplies = ["yeast","flour","meat"]
 * 输出：["bread","sandwich"]
 * 解释：
 * 我们可以做出 "bread" ，因为我们有原材料 "yeast" 和 "flour" 。
 * 我们可以做出 "sandwich" ，因为我们有原材料 "meat" 且可以做出原材料 "bread" 。
 * <p>
 * 输入：recipes = ["bread","sandwich","burger"], ingredients = [["yeast","flour"],["bread","meat"],["sandwich","meat","bread"]], supplies = ["yeast","flour","meat"]
 * 输出：["bread","sandwich","burger"]
 * 解释：
 * 我们可以做出 "bread" ，因为我们有原材料 "yeast" 和 "flour" 。
 * 我们可以做出 "sandwich" ，因为我们有原材料 "meat" 且可以做出原材料 "bread" 。
 * 我们可以做出 "burger" ，因为我们有原材料 "meat" 且可以做出原材料 "bread" 和 "sandwich" 。
 * <p>
 * 输入：recipes = ["bread"], ingredients = [["yeast","flour"]], supplies = ["yeast"]
 * 输出：[]
 * 解释：
 * 我们没法做出任何菜，因为我们只有原材料 "yeast" 。
 * <p>
 * n == recipes.length == ingredients.length
 * 1 <= n <= 100
 * 1 <= ingredients[i].length, supplies.length <= 100
 * 1 <= recipes[i].length, ingredients[i][j].length, supplies[k].length <= 10
 * recipes[i], ingredients[i][j] 和 supplies[k] 只包含小写英文字母。
 * 所有 recipes 和 supplies 中的值互不相同。
 * ingredients[i] 中的字符串互不相同。
 */
public class Problem2115 {
    public static void main(String[] args) {
        Problem2115 problem2115 = new Problem2115();
        String[] recipes = {"bread", "sandwich", "burger"};
        List<List<String>> ingredients = new ArrayList<List<String>>() {{
            add(new ArrayList<String>() {{
                add("yeast");
                add("flour");
            }});
            add(new ArrayList<String>() {{
                add("bread");
                add("meat");
            }});
            add(new ArrayList<String>() {{
                add("sandwich");
                add("meat");
                add("bread");
            }});
        }};
        String[] supplies = {"yeast", "flour", "meat"};
        System.out.println(problem2115.findAllRecipes(recipes, ingredients, supplies));
    }

    /**
     * bfs拓扑排序
     * 核心思想：supplies中供应的原材料作为入度为0的节点入队，而不能像之前bfs拓扑排序中遍历图中节点，将入度为0的节点入队
     * supplies中节点作为入度为0的节点入队，队列中节点出队，当前节点的邻接节点的入度减1，邻接节点入度为0的节点入队，
     * 遍历结束判断是否能够遍历到所有的节点，如果能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (n=recipes.length，m为ingredients[i]之和，即图中边的个数)
     *
     * @param recipes
     * @param ingredients
     * @param supplies
     * @return
     */
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        //邻接表
        Map<String, List<String>> edges = new HashMap<>();
        //key：图中节点，value：当前节点入度
        Map<String, Integer> inDegreeMap = new HashMap<>();

        for (int i = 0; i < ingredients.size(); i++) {
            for (int j = 0; j < ingredients.get(i).size(); j++) {
                if (!edges.containsKey(ingredients.get(i).get(j))) {
                    edges.put(ingredients.get(i).get(j), new ArrayList<>());
                }

                //图中添加边
                edges.get(ingredients.get(i).get(j)).add(recipes[i]);
                //recipes[i]入度加1
                inDegreeMap.put(recipes[i], inDegreeMap.getOrDefault(recipes[i], 0) + 1);
            }
        }

        //存放入度为0节点的队列
        Queue<String> queue = new LinkedList<>();

        //supplies中节点作为入度为0的节点入队
        for (int i = 0; i < supplies.length; i++) {
            queue.offer(supplies[i]);
        }

        List<String> list = new ArrayList<>();

        while (!queue.isEmpty()) {
            String str1 = queue.poll();

            //当前节点str1不存在邻接节点，直接进行下次循环，避免edges.get(str1)空指针异常
            if (!edges.containsKey(str1)) {
                continue;
            }

            //遍历节点str1的邻接节点str2
            for (String str2 : edges.get(str1)) {
                inDegreeMap.put(str2, inDegreeMap.get(str2) - 1);

                //邻接节点str2的入度为0，则入队，并且可以做出菜str2
                if (inDegreeMap.get(str2) == 0) {
                    queue.offer(str2);
                    list.add(str2);
                }
            }
        }

        return list;
    }
}
