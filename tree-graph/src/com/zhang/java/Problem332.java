package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/12 08:50
 * @Author zsy
 * @Description 重新安排行程 欧拉回路类比Problem753 图类比
 * 给你一份航线列表 tickets ，其中 tickets[i] = [fromi, toi] 表示飞机出发和降落的机场地点。
 * 请你对该行程进行重新规划排序。
 * 所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，所以该行程必须从 JFK 开始。
 * 如果存在多种有效的行程，请你按字典排序返回最小的行程组合。
 * 例如，行程 ["JFK", "LGA"] 与 ["JFK", "LGB"] 相比就更小，排序更靠前。
 * 假定所有机票至少存在一种合理的行程。且所有的机票 必须都用一次 且 只能用一次。
 * <p>
 * 输入：tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
 * 输出：["JFK","MUC","LHR","SFO","SJC"]
 * <p>
 * 输入：tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * 输出：["JFK","ATL","JFK","SFO","ATL","SFO"]
 * 解释：另一种有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"] ，但是它字典排序更大更靠后。
 * <p>
 * 1 <= tickets.length <= 300
 * tickets[i].length == 2
 * fromi.length == 3
 * toi.length == 3
 * fromi 和 toi 由大写英文字母组成
 * fromi != toi
 */
public class Problem332 {
    public static void main(String[] args) {
        Problem332 problem332 = new Problem332();
        List<List<String>> tickets = new ArrayList<List<String>>() {{
            add(new ArrayList<String>() {{
                add("JFK");
                add("SFO");
            }});
            add(new ArrayList<String>() {{
                add("JFK");
                add("ATL");
            }});
            add(new ArrayList<String>() {{
                add("SFO");
                add("ATL");
            }});
            add(new ArrayList<String>() {{
                add("ATL");
                add("JFK");
            }});
            add(new ArrayList<String>() {{
                add("ATL");
                add("SFO");
            }});
        }};
        System.out.println(problem332.findItinerary(tickets));
    }

    /**
     * dfs欧拉回路
     * 核心思想：从起始节点出发，经过图中所有边恰好一次(优先遍历字典序小的边)，即一笔画问题，得到欧拉回路
     * 欧拉回路：从一个节点发出，经过图中所有边恰好一次，并且能够遍历所有节点的回路(图中所有节点的入度和出度相等，则存在欧拉回路)
     * 从起始节点出发dfs，从当前节点到下一个节点时，删除当前边，如果当前节点不存在可达的边，则将当前节点倒序加入结果路径中
     * 时间复杂度O(m+n+mlogm)，空间复杂度O(m) (m：图中边的数量，n：图中节点的数量)
     * (边需要按照字典序排序，时间复杂度O(mlogm)) (dfs栈的最大深度O(m))
     *
     * @param tickets
     * @return
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        //邻接表
        Map<String, List<String>> edges = new HashMap<>();

        for (List<String> edge : tickets) {
            String str1 = edge.get(0);
            String str2 = edge.get(1);

            if (!edges.containsKey(str1)) {
                //使用LinkedList，在O(1)删除首元素
                edges.put(str1, new LinkedList<>());
            }

            List<String> list = edges.get(str1);
            list.add(str2);
        }

        //邻接表中节点按照字典序排序，保证每次先访问字典序小的节点
        for (Map.Entry<String, List<String>> entry : edges.entrySet()) {
            entry.getValue().sort(new Comparator<String>() {
                @Override
                public int compare(String str1, String str2) {
                    return str1.compareTo(str2);
                }
            });
        }

        LinkedList<String> list = new LinkedList<>();

        dfs("JFK", edges, list);

        return list;
    }

    private void dfs(String str, Map<String, List<String>> edges, LinkedList<String> list) {
        List<String> edge = edges.get(str);

        while (edge != null && !edge.isEmpty()) {
            //每次删除首元素，得到和str相连边中字典序最小的节点
            //使用LinkedList，在O(1)删除首元素
            String str2 = edge.remove(0);
            dfs(str2, edges, list);
        }

        //str首添加，保证最后访问到的节点在list最后，得到欧拉回路
        list.addFirst(str);
    }
}
