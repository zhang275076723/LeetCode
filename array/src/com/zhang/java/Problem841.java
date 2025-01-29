package com.zhang.java;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Date 2025/3/19 08:37
 * @Author zsy
 * @Description 钥匙和房间
 * 有 n 个房间，房间按从 0 到 n - 1 编号。
 * 最初，除 0 号房间外的其余所有房间都被锁住。
 * 你的目标是进入所有的房间。
 * 然而，你不能在没有获得钥匙的时候进入锁住的房间。
 * 当你进入一个房间，你可能会在里面找到一套 不同的钥匙，每把钥匙上都有对应的房间号，即表示钥匙可以打开的房间。
 * 你可以拿上所有钥匙去解锁其他房间。
 * 给你一个数组 rooms 其中 rooms[i] 是你进入 i 号房间可以获得的钥匙集合。
 * 如果能进入 所有 房间返回 true，否则返回 false。
 * <p>
 * 输入：rooms = [[1],[2],[3],[]]
 * 输出：true
 * 解释：
 * 我们从 0 号房间开始，拿到钥匙 1。
 * 之后我们去 1 号房间，拿到钥匙 2。
 * 然后我们去 2 号房间，拿到钥匙 3。
 * 最后我们去了 3 号房间。
 * 由于我们能够进入每个房间，我们返回 true。
 * <p>
 * 输入：rooms = [[1,3],[3,0,1],[2],[0]]
 * 输出：false
 * 解释：我们不能进入 2 号房间。
 * <p>
 * n == rooms.length
 * 2 <= n <= 1000
 * 0 <= rooms[i].length <= 1000
 * 1 <= sum(rooms[i].length) <= 3000
 * 0 <= rooms[i][j] < n
 * 所有 rooms[i] 的值 互不相同
 */
public class Problem841 {
    public static void main(String[] args) {
        Problem841 problem841 = new Problem841();
        List<List<Integer>> rooms = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(1);
                add(3);
            }});
            add(new ArrayList<Integer>() {{
                add(3);
                add(0);
                add(1);
            }});
            add(new ArrayList<Integer>() {{
                add(2);
            }});
            add(new ArrayList<Integer>() {{
                add(0);
            }});
        }};
        System.out.println(problem841.canVisitAllRooms(rooms));
        System.out.println(problem841.canVisitAllRooms2(rooms));
    }

    /**
     * dfs
     * 时间复杂度O(n+m)，空间复杂度O(n) (n=rooms.size()，m=sum(rooms.get(i).size())，即rooms中钥匙的总数)
     *
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        //房间的个数
        int n = rooms.size();

        //从房间0开始dfs，能够访问到的房间个数
        int count = dfs(0, rooms, new boolean[n]);

        return count == n;
    }

    /**
     * bfs
     * 时间复杂度O(n+m)，空间复杂度O(n) (n=rooms.size()，m=sum(rooms.get(i).size())，即rooms中钥匙的总数)
     *
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms2(List<List<Integer>> rooms) {
        //房间的个数
        int n = rooms.size();
        //从房间0开始bfs，能够访问到的房间个数
        int count = 0;

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int curRoom = queue.poll();
            count++;

            for (int room : rooms.get(curRoom)) {
                if (visited[room]) {
                    continue;
                }

                queue.offer(room);
                visited[room] = true;
            }
        }

        return count == n;
    }

    private int dfs(int t, List<List<Integer>> rooms, boolean[] visited) {
        if (visited[t]) {
            return 0;
        }

        int count = 1;
        visited[t] = true;

        for (int room : rooms.get(t)) {
            count = count + dfs(room, rooms, visited);
        }

        return count;
    }
}
