package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/24 08:50
 * @Author zsy
 * @Description 项目管理 拓扑排序类比 图类比
 * 有 n 个项目，每个项目或者不属于任何小组，或者属于 m 个小组之一。
 * group[i] 表示第 i 个项目所属的小组，如果第 i 个项目不属于任何小组，则 group[i] 等于 -1。
 * 项目和小组都是从零开始编号的。
 * 可能存在小组不负责任何项目，即没有任何项目属于这个小组。
 * 请你帮忙按要求安排这些项目的进度，并返回排序后的项目列表：
 * 同一小组的项目，排序后在列表中彼此相邻。
 * 项目之间存在一定的依赖关系，我们用一个列表 beforeItems 来表示，
 * 其中 beforeItems[i] 表示在进行第 i 个项目前（位于第 i 个项目左侧）应该完成的所有项目。
 * 如果存在多个解决方案，只需要返回其中任意一个即可。
 * 如果没有合适的解决方案，就请返回一个 空列表 。
 * <p>
 * 输入：n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
 * 输出：[6,3,4,1,5,2,0,7]
 * <p>
 * 输入：n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3],[],[4],[]]
 * 输出：[]
 * 解释：与示例 1 大致相同，但是在排序后的列表中，4 必须放在 6 的前面。
 * <p>
 * 1 <= m <= n <= 3 * 10^4
 * group.length == beforeItems.length == n
 * -1 <= group[i] <= m - 1
 * 0 <= beforeItems[i].length <= n - 1
 * 0 <= beforeItems[i][j] <= n - 1
 * i != beforeItems[i][j]
 * beforeItems[i] 不含重复元素
 */
public class Problem1203 {
    /**
     * dfs拓扑排序图中是否有环标志位
     */
    private boolean hasCircle = false;

    /**
     * dfs拓扑排序数组指针，dfs需要倒序
     */
    private int index;

    public static void main(String[] args) {
        Problem1203 problem1203 = new Problem1203();
        int n = 8;
        int m = 2;
        int[] group = {-1, -1, 1, 0, 0, 1, 0, -1};
        List<List<Integer>> beforeItems = new ArrayList<List<Integer>>() {{
            add(new ArrayList<>());
            add(new ArrayList<Integer>() {{
                add(6);
            }});
            add(new ArrayList<Integer>() {{
                add(5);
            }});
            add(new ArrayList<Integer>() {{
                add(6);
            }});
            add(new ArrayList<Integer>() {{
                add(3);
                add(6);
            }});
            add(new ArrayList<>());
            add(new ArrayList<>());
            add(new ArrayList<>());
        }};
        System.out.println(Arrays.toString(problem1203.sortItems(n, m, group, beforeItems)));
        System.out.println(Arrays.toString(problem1203.sortItems2(n, m, group, beforeItems)));
    }

    /**
     * dfs拓扑排序
     * 核心思想：两次拓扑排序，先小组节点拓扑排序，确定小组节点的先后顺序，保证同一小组内节点排序后相邻，
     * 再组内节点拓扑排序，确定组内节点的先后顺序，组内节点拓扑排序按照小组节点拓扑排序扩展，得到最终结果
     * 1、对不属于任何小组的节点，即copyGroup[i]为-1的节点分配小组编号，保证这些不属于任何小组的每个节点单独属于一个小组
     * 2、根据图中节点的依赖关系，建立小组图和组内图，如果存在节点u到节点v的边，则组内图中存在节点u到节点v的边，
     * 如果存在节点u到节点v的边，并且两个节点属于不同小组，则小组图中存在节点u所属小组节点到节点v所属小组节点的边
     * 3、先小组节点拓扑排序，确定小组节点的先后顺序，保证同一小组内节点排序后相邻
     * 4、再组内节点拓扑排序，确定组内节点的先后顺序
     * 5、建立小组节点和组内拓扑排序中属于当前小组节点的组内节点集合映射
     * 6、根据映射关系组内节点拓扑排序按照小组节点拓扑排序扩展，得到最终结果
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param m
     * @param group
     * @param beforeItems
     * @return
     */
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        //图中不同组节点的数量
        int groupCount = m;
        //使用拷贝数组，不对原group数组操作，避免多次调用sortItems，前一次修改group导致本次group数组失真，
        //不能得到正确的groupCount，则不能得到正确结果
        int[] copyGroup = Arrays.copyOf(group, group.length);

        //1、对不属于任何小组的节点，即copyGroup[i]为-1的节点分配小组编号，保证这些不属于任何小组的每个节点单独属于一个小组
        for (int i = 0; i < copyGroup.length; i++) {
            if (copyGroup[i] == -1) {
                copyGroup[i] = groupCount;
                groupCount++;
            }
        }

        //小组邻接表
        List<List<Integer>> groupEdges = new ArrayList<>();
        //组内邻接表
        List<List<Integer>> itemEdges = new ArrayList<>();

        for (int i = 0; i < groupCount; i++) {
            groupEdges.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            itemEdges.add(new ArrayList<>());
        }

        //2、根据图中节点的依赖关系，建立小组图和组内图，如果存在组内节点u到组内节点v的边，则组内图中存在组内节点u到组内节点v的边，
        //如果存在组内节点u到组内节点v的边，并且两个节点属于不同小组，则小组图中存在组内节点u所属小组节点到组内节点v所属小组节点的边
        for (int i = 0; i < beforeItems.size(); i++) {
            //组内节点u
            int u = i;

            for (int j = 0; j < beforeItems.get(i).size(); j++) {
                //组内节点v
                int v = beforeItems.get(i).get(j);

                //存在组内节点v到组内节点u的边
                itemEdges.get(v).add(u);

                //组内节点u和组内节点v属于不同小组，则存在组内节点v所属小组节点到组内节点u所属小组节点的边
                if (copyGroup[u] != copyGroup[v]) {
                    groupEdges.get(copyGroup[v]).add(copyGroup[u]);
                }
            }
        }

        //小组节点拓扑排序数组
        int[] groupResult = new int[groupCount];
        //组内节点拓扑排序数组
        int[] itemResult = new int[n];
        //小组节点拓扑排序访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited1 = new int[groupCount];
        //组内节点拓扑排序访问数组，0-未访问，1-正在访问，2-已访问
        int[] visited2 = new int[n];
        //dfs拓扑排序数组指针，dfs需要倒序
        index = groupCount - 1;

        //3、先小组节点拓扑排序，确定小组节点的先后顺序，保证同一小组内节点排序后相邻
        for (int i = 0; i < groupCount; i++) {
            //从未访问的节点开始dfs
            if (visited1[i] == 0) {
                dfs(i, groupResult, groupEdges, visited1);
            }

            //有环说明不存在拓扑排序，返回空数组
            if (hasCircle) {
                return new int[0];
            }
        }

        //dfs拓扑排序数组指针重新赋值，用于组内节点拓扑排序
        index = n - 1;

        //4、再组内节点拓扑排序，确定组内节点的先后顺序
        for (int i = 0; i < n; i++) {
            //从未访问的节点开始dfs
            if (visited2[i] == 0) {
                dfs(i, itemResult, itemEdges, visited2);
            }

            //有环说明不存在拓扑排序，返回空数组
            if (hasCircle) {
                return new int[0];
            }
        }

        //key：小组节点，value：属于当前小组节点的组内节点集合
        Map<Integer, List<Integer>> map = new HashMap<>();

        //5、建立小组节点和组内拓扑排序中属于当前小组节点的组内节点集合映射
        for (int i = 0; i < n; i++) {
            List<Integer> list = map.getOrDefault(copyGroup[itemResult[i]], new ArrayList<>());
            list.add(itemResult[i]);
            map.put(copyGroup[itemResult[i]], list);
        }

        //结果数组
        int[] result = new int[n];
        int k = 0;

        //6、根据映射关系组内节点拓扑排序按照小组节点拓扑排序扩展，得到最终结果
        for (int i = 0; i < groupCount; i++) {
            List<Integer> list = map.get(groupResult[i]);

            //当前小组节点groupResult[i]中不存在对应组内节点，直接进行下次循环
            if (list == null) {
                continue;
            }

            for (int j = 0; j < list.size(); j++) {
                result[k] = list.get(j);
                k++;
            }
        }

        return result;
    }

    /**
     * bfs拓扑排序
     * 核心思想：两次拓扑排序，先小组节点拓扑排序，确定小组节点的先后顺序，保证同一小组内节点排序后相邻，
     * 再组内节点拓扑排序，确定组内节点的先后顺序，组内节点拓扑排序按照小组节点拓扑排序扩展，得到最终结果
     * 1、对不属于任何小组的节点，即copyGroup[i]为-1的节点分配小组编号，保证这些不属于任何小组的每个节点单独属于一个小组
     * 2、根据图中节点的依赖关系，建立小组图和组内图，如果存在节点u到节点v的边，则组内图中存在节点u到节点v的边，
     * 如果存在节点u到节点v的边，并且两个节点属于不同小组，则小组图中存在节点u所属小组节点到节点v所属小组节点的边
     * 3、先小组节点拓扑排序，确定小组节点的先后顺序，保证同一小组内节点排序后相邻
     * 4、再组内节点拓扑排序，确定组内节点的先后顺序
     * 5、建立小组节点和组内拓扑排序中属于当前小组节点的组内节点集合映射
     * 6、根据映射关系组内节点拓扑排序按照小组节点拓扑排序扩展，得到最终结果
     * 时间复杂度O(m+n)，空间复杂度O(m+n) (如果使用邻接矩阵，空间复杂度O(n^2))
     *
     * @param n
     * @param m
     * @param group
     * @param beforeItems
     * @return
     */
    public int[] sortItems2(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        //图中不同组节点的数量
        int groupCount = m;
        //使用拷贝数组，不对原group数组操作，避免多次调用sortItems，前一次修改group导致本次group数组失真，
        //不能得到正确的groupCount，则不能得到正确结果
        int[] copyGroup = Arrays.copyOf(group, group.length);

        //1、对不属于任何小组的节点，即copyGroup[i]为-1的节点分配小组编号，保证这些不属于任何小组的每个节点单独属于一个小组
        for (int i = 0; i < copyGroup.length; i++) {
            if (copyGroup[i] == -1) {
                copyGroup[i] = groupCount;
                groupCount++;
            }
        }

        //小组邻接表
        List<List<Integer>> groupEdges = new ArrayList<>();
        //组内邻接表
        List<List<Integer>> itemEdges = new ArrayList<>();

        for (int i = 0; i < groupCount; i++) {
            groupEdges.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            itemEdges.add(new ArrayList<>());
        }

        //2、根据图中节点的依赖关系，建立小组图和组内图，如果存在组内节点u到组内节点v的边，则组内图中存在组内节点u到组内节点v的边，
        //如果存在组内节点u到组内节点v的边，并且两个节点属于不同小组，则小组图中存在组内节点u所属小组节点到组内节点v所属小组节点的边
        for (int i = 0; i < beforeItems.size(); i++) {
            //组内节点u
            int u = i;

            for (int j = 0; j < beforeItems.get(i).size(); j++) {
                //组内节点v
                int v = beforeItems.get(i).get(j);

                //存在组内节点v到组内节点u的边
                itemEdges.get(v).add(u);

                //组内节点u和组内节点v属于不同小组，则存在组内节点v所属小组节点到组内节点u所属小组节点的边
                if (copyGroup[u] != copyGroup[v]) {
                    groupEdges.get(copyGroup[v]).add(copyGroup[u]);
                }
            }
        }

        //3、先小组节点拓扑排序，确定小组节点的先后顺序，保证同一小组内节点排序后相邻
        int[] groupResult = bfs(groupEdges, groupCount);

        //小组节点存在环，则不存在拓扑排序，返回空数组
        if (groupResult.length == 0) {
            return new int[0];
        }

        //4、再组内节点拓扑排序，确定组内节点的先后顺序
        int[] itemResult = bfs(itemEdges, n);

        //组内节点存在环，则不存在拓扑排序，返回空数组
        if (itemResult.length == 0) {
            return new int[0];
        }

        //key：小组节点，value：属于当前小组节点的组内节点集合
        Map<Integer, List<Integer>> map = new HashMap<>();

        //5、建立小组节点和组内拓扑排序中属于当前小组节点的组内节点集合映射
        for (int i = 0; i < n; i++) {
            List<Integer> list = map.getOrDefault(copyGroup[itemResult[i]], new ArrayList<>());
            list.add(itemResult[i]);
            map.put(copyGroup[itemResult[i]], list);
        }

        //结果数组
        int[] result = new int[n];
        int k = 0;

        //6、根据映射关系组内节点拓扑排序按照小组节点拓扑排序扩展，得到最终结果
        for (int i = 0; i < groupCount; i++) {
            List<Integer> list = map.get(groupResult[i]);

            //当前小组节点groupResult[i]中不存在对应组内节点，直接进行下次循环
            if (list == null) {
                continue;
            }

            for (int j = 0; j < list.size(); j++) {
                result[k] = list.get(j);
                k++;
            }
        }

        return result;
    }

    private void dfs(int u, int[] result, List<List<Integer>> edges, int[] visited) {
        //已经存在环，或者当前节点u已经访问，直接返回
        if (hasCircle || visited[u] == 2) {
            return;
        }

        //当前节点u正在访问，说明有环，不存在拓扑排序，直接返回
        if (visited[u] == 1) {
            hasCircle = true;
            return;
        }

        //当前节点u正在访问
        visited[u] = 1;

        //遍历节点u的邻接节点v
        for (int v : edges.get(u)) {
            dfs(v, result, edges, visited);

            if (hasCircle) {
                return;
            }
        }

        //当前节点u已经访问
        visited[u] = 2;
        //当前节点u倒序插入拓扑排序数组中
        result[index] = u;
        index--;
    }

    /**
     * bfs拓扑排序
     * 时间复杂度O(m+n)，空间复杂度O(n) (n：图中节点的个数，m：图中边的个数)
     *
     * @param edges 邻接表
     * @param n     图中节点的个数
     * @return
     */
    private int[] bfs(List<List<Integer>> edges, int n) {
        //入度数组
        int[] inDegree = new int[n];

        for (int i = 0; i < edges.size(); i++) {
            for (int j = 0; j < edges.get(i).size(); j++) {
                inDegree[edges.get(i).get(j)]++;
            }
        }

        //存放入度为0节点的队列
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] result = new int[n];
        int index = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            result[index] = u;
            index++;

            //遍历节点u的邻接节点v
            for (int v : edges.get(u)) {
                inDegree[v]--;

                //邻接节点v的入度为0，则入队
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        //能遍历到所有的节点，则存在拓扑排序；否则不存在拓扑排序
        return index == n ? result : new int[0];
    }
}
