package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/5/17 09:02
 * @Author zsy
 * @Description 设计文件分享系统 幂等性类比Problem205、Problem290、Problem291、Problem535 类比Problem71、Problem588、Problem609、Problem642、Problem1166、Problem1233、Problem1268、Problem1948 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem535、Problem554、Problem609、Problem763、Problem1640、Problem2657、Offer50 优先队列类比
 * 我们需要使用一套文件分享系统来分享一个非常大的文件，该文件由 m 个从 1 到 m 编号的文件块组成。
 * 当用户加入系统时，系统应为其注册一个独有的 ID。
 * 这个独有的 ID 应当被相应的用户使用一次，但是当用户离开系统时，其 ID 应可以被（后续新注册的用户）再次使用。
 * 用户可以请求文件中的某个指定的文件块，系统应当返回拥有这个文件块的所有用户的 ID。
 * 如果用户收到 ID 的非空列表，就表示成功接收到请求的文件块。
 * <p>
 * 实现 FileSharing 类：
 * FileSharing(int m) 初始化该对象，文件有 m 个文件块。
 * int join(int[] ownedChunks)：一个新用户加入系统，并拥有文件的一些文件块。
 * 系统应当为该用户注册一个 ID，该 ID 应是未被其他用户占用的最小正整数。返回注册的 ID。
 * void leave(int userID)：ID 为 userID 的用户将离开系统，你不能再从该用户提取文件块了。
 * int[] request(int userID, int chunkID)：ID 为 userID 的用户请求编号为 chunkID 的文件块。
 * 返回拥有这个文件块的所有用户的 ID 所构成的列表或数组，按升序排列。
 * <p>
 * 输入:
 * ["FileSharing","join","join","join","request","request","leave","request","leave","join"]
 * [[4],[[1,2]],[[2,3]],[[4]],[1,3],[2,2],[1],[2,1],[2],[[]]]
 * 输出:
 * [null,1,2,3,[2],[1,2],null,[],null,1]
 * 解释:
 * FileSharing fileSharing = new FileSharing(4); // 我们用该系统分享由 4 个文件块组成的文件。
 * fileSharing.join([1, 2]);    // 一个拥有文件块 [1,2] 的用户加入系统，为其注册 id = 1 并返回 1。
 * fileSharing.join([2, 3]);    // 一个拥有文件块 [2,3] 的用户加入系统，为其注册 id = 2 并返回 2。
 * fileSharing.join([4]);       // 一个拥有文件块 [4] 的用户加入系统，为其注册 id = 3 并返回 3。
 * fileSharing.request(1, 3);   // id = 1 的用户请求第 3 个文件块，只有 id = 2 的用户拥有文件块，返回 [2] 。注意，现在用户 1 现拥有文件块 [1,2,3]。
 * fileSharing.request(2, 2);   // id = 2 的用户请求第 2 个文件块，id 为 [1,2] 的用户拥有该文件块，所以我们返回 [1,2] 。
 * fileSharing.leave(1);        // id = 1 的用户离开系统，其所拥有的所有文件块不再对其他用户可用。
 * fileSharing.request(2, 1);   // id = 2 的用户请求第 1 个文件块，系统中没有用户拥有该文件块，所以我们返回空列表 [] 。
 * fileSharing.leave(2);        // id = 2 的用户离开系统。
 * fileSharing.join([]);        // 一个不拥有任何文件块的用户加入系统，为其注册 id = 1 并返回 1 。注意，id 1 和 2 空闲，可以重新使用。
 * <p>
 * 1 <= m <= 10^5
 * 0 <= ownedChunks.length <= min(100, m)
 * 1 <= ownedChunks[i] <= m
 * ownedChunks 的值是互不相同的。
 * 1 <= chunkID <= m
 * 当你正确地注册用户 ID 时，题目保证 userID 是系统中的一个已注册用户。
 * join、 leave 和 request 最多被调用 10^4 次。
 * 每次对 leave 的调用都有对应的对 join 的调用。
 */
public class Problem1500 {
    public static void main(String[] args) {
        // 我们用该系统分享由 4 个文件块组成的文件。
        FileSharing fileSharing = new FileSharing(4);
        // 一个拥有文件块 [1,2] 的用户加入系统，为其注册 id = 1 并返回 1。
        System.out.println(fileSharing.join(new ArrayList<Integer>() {{
            add(1);
            add(2);
        }}));
        // 一个拥有文件块 [2,3] 的用户加入系统，为其注册 id = 2 并返回 2。
        System.out.println(fileSharing.join(new ArrayList<Integer>() {{
            add(2);
            add(3);
        }}));
        // 一个拥有文件块 [4] 的用户加入系统，为其注册 id = 3 并返回 3。
        System.out.println(fileSharing.join(new ArrayList<Integer>() {{
            add(4);
        }}));
        // id = 1 的用户请求第 3 个文件块，只有 id = 2 的用户拥有文件块，返回 [2] 。注意，现在用户 1 现拥有文件块 [1,2,3]。
        System.out.println(fileSharing.request(1, 3));
        // id = 2 的用户请求第 2 个文件块，id 为 [1,2] 的用户拥有该文件块，所以我们返回 [1,2] 。
        System.out.println(fileSharing.request(2, 2));
        // id = 1 的用户离开系统，其所拥有的所有文件块不再对其他用户可用。
        fileSharing.leave(1);
        // id = 2 的用户请求第 1 个文件块，系统中没有用户拥有该文件块，所以我们返回空列表 [] 。
        System.out.println(fileSharing.request(2, 1));
        // id = 2 的用户离开系统。
        fileSharing.leave(2);
        // 一个不拥有任何文件块的用户加入系统，为其注册 id = 1 并返回 1 。注意，id 1 和 2 空闲，可以重新使用。
        System.out.println(fileSharing.join(new ArrayList<>()));
    }

    /**
     * 哈希表+优先队列
     */
    static class FileSharing {
        //文件块的个数
        private final int chunkCapacity;
        //下一个用户id
        private int nextUserId;
        //优先队列，小根堆，存放可以重复使用的用户id
        //小根堆不为空，则优先使用小根堆堆顶的用户id
        private final PriorityQueue<Integer> priorityQueue;
        //key：用户id，value：当前用户拥有的文件块集合
        //使用2个map保证幂等性
        private final Map<Integer, Set<Integer>> user2ChunkMap;
        //key：文件块，value：拥有当前文件块的用户集合
        private final Map<Integer, Set<Integer>> chunk2UserMap;

        public FileSharing(int m) {
            chunkCapacity = m;
            nextUserId = 1;
            priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
            user2ChunkMap = new HashMap<>();
            chunk2UserMap = new HashMap<>();
        }

        /**
         * 拥有文件块ownedChunks的新用户加入系统
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param ownedChunks
         * @return
         */
        public int join(List<Integer> ownedChunks) {
            int userId;

            //小根堆为空，则优先使用nextUserId的用户id
            if (priorityQueue.isEmpty()) {
                userId = nextUserId;
                nextUserId++;
            } else {
                //小根堆不为空，则优先使用小根堆堆顶的用户id
                userId = priorityQueue.poll();
            }

            user2ChunkMap.put(userId, new HashSet<>());
            Set<Integer> chunkSet = user2ChunkMap.get(userId);

            //user2ChunkMap中加入userId
            for (int chunkId : ownedChunks) {
                //当前文件块chunkId不合法，直接进行下次循环
                if (chunkId < 1 || chunkId > chunkCapacity) {
                    continue;
                }

                chunkSet.add(chunkId);
            }

            //chunk2UserMap中加入ownedChunks
            for (int chunkId : ownedChunks) {
                if (!chunk2UserMap.containsKey(chunkId)) {
                    chunk2UserMap.put(chunkId, new HashSet<>());
                }

                Set<Integer> userSet = chunk2UserMap.get(chunkId);
                userSet.add(userId);
            }

            return userId;
        }

        /**
         * 删除用户userID
         * 时间复杂度O(chunk2UserMap.size()+log(priorityQueue.size()))，空间复杂度O(1)
         *
         * @param userID
         */
        public void leave(int userID) {
            //系统中不存在userID，直接返回
            if (!user2ChunkMap.containsKey(userID)) {
                return;
            }

            //user2ChunkMap中删除userID
            user2ChunkMap.remove(userID);
            //userID加入小根堆，作为可以重复使用的用户id
            priorityQueue.offer(userID);

            //chunk2UserMap中删除拥有userID的文件块
            for (Integer chunkId : chunk2UserMap.keySet()) {
                chunk2UserMap.get(chunkId).remove(userID);
            }
        }

        /**
         * 用户userID请求文件块chunkID，按升序返回拥有文件块chunkID的用户id集合(注意：不包含当前用户userID)
         * 时间复杂度O()，空间复杂度O(1)
         *
         * @param userID
         * @param chunkID
         * @return
         */
        public List<Integer> request(int userID, int chunkID) {
            //系统中不存在userID或chunkID，直接返回空集合
            if (!user2ChunkMap.containsKey(userID) || !chunk2UserMap.containsKey(chunkID)) {
                return new ArrayList<>();
            }

            Set<Integer> userSet = chunk2UserMap.get(chunkID);
            //注意：不包含当前用户userID
            List<Integer> list = new ArrayList<>(userSet);

            //更新user2ChunkMap
            user2ChunkMap.get(userID).add(chunkID);
            //更新chunk2UserMap
            chunk2UserMap.get(chunkID).add(userID);

            return list;
        }
    }
}
