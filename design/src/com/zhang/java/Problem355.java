package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/2/8 00:54
 * @Author zsy
 * @Description 设计推特 类比Problem1348 优先队列类比
 * 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近 10 条推文。
 * 实现 Twitter 类：
 * Twitter() 初始化简易版推特对象
 * void postTweet(int userId, int tweetId) 根据给定的 tweetId 和 userId 创建一条新推文。
 * 每次调用此函数都会使用一个不同的 tweetId 。
 * List<Integer> getNewsFeed(int userId) 检索当前用户新闻推送中最近  10 条推文的 ID 。
 * 新闻推送中的每一项都必须是由用户关注的人或者是用户自己发布的推文。推文必须 按照时间顺序由最近到最远排序 。
 * void follow(int followerId, int followeeId) ID 为 followerId 的用户开始关注 ID 为 followeeId 的用户。
 * void unfollow(int followerId, int followeeId) ID 为 followerId 的用户不再关注 ID 为 followeeId 的用户。
 * <p>
 * 输入
 * ["Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed"]
 * [[], [1, 5], [1], [1, 2], [2, 6], [1], [1, 2], [1]]
 * 输出
 * [null, null, [5], null, null, [6, 5], null, [5]]
 * 解释
 * Twitter twitter = new Twitter();
 * twitter.postTweet(1, 5); // 用户 1 发送了一条新推文 (用户 id = 1, 推文 id = 5)
 * twitter.getNewsFeed(1);  // 用户 1 的获取推文应当返回一个列表，其中包含一个 id 为 5 的推文
 * twitter.follow(1, 2);    // 用户 1 关注了用户 2
 * twitter.postTweet(2, 6); // 用户 2 发送了一个新推文 (推文 id = 6)
 * twitter.getNewsFeed(1);  // 用户 1 的获取推文应当返回一个列表，其中包含两个推文，id 分别为 -> [6, 5] 。推文 id 6 应当在推文 id 5 之前，因为它是在 5 之后发送的
 * twitter.unfollow(1, 2);  // 用户 1 取消关注了用户 2
 * twitter.getNewsFeed(1);  // 用户 1 获取推文应当返回一个列表，其中包含一个 id 为 5 的推文。因为用户 1 已经不再关注用户 2
 * <p>
 * 1 <= userId, followerId, followeeId <= 500
 * 0 <= tweetId <= 10^4
 * 所有推特的 ID 都互不相同
 * postTweet、getNewsFeed、follow 和 unfollow 方法最多调用 3 * 10^4 次
 */
public class Problem355 {
    public static void main(String[] args) {
        Twitter twitter = new Twitter();
        // 用户 1 发送了一条新推文 (用户 id = 1, 推文 id = 5)
        twitter.postTweet(1, 5);
        // 用户 1 的获取推文应当返回一个列表，其中包含一个 id 为 5 的推文
        System.out.println(twitter.getNewsFeed(1));
        // 用户 1 关注了用户 2
        twitter.follow(1, 2);
        // 用户 2 发送了一个新推文 (推文 id = 6)
        twitter.postTweet(2, 6);
        // 用户 1 的获取推文应当返回一个列表，其中包含两个推文，id 分别为 -> [6, 5] 。推文 id 6 应当在推文 id 5 之前，因为它是在 5 之后发送的
        System.out.println(twitter.getNewsFeed(1));
        // 用户 1 取消关注了用户 2
        twitter.unfollow(1, 2);
        // 用户 1 获取推文应当返回一个列表，其中包含一个 id 为 5 的推文。因为用户 1 已经不再关注用户 2
        System.out.println(twitter.getNewsFeed(1));
    }

    /**
     * 双向链表+优先队列，大根堆
     */
    static class Twitter {
        //key：用户id，value：用户节点
        private final Map<Integer, User> userMap;
        //下一条推文时间，每发一条推文，time++
        private static int time;
        //检索用户推文的最大值，即每个用户只需要检索当前用户和关注的用户的最近maxCount条推文
        private final int maxCount;

        public Twitter() {
            userMap = new HashMap<>();
            time = 0;
            maxCount = 10;
        }

        /**
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param userId
         * @param tweetId
         */
        public void postTweet(int userId, int tweetId) {
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, new User(userId));
            }

            //当前用户节点
            User user = userMap.get(userId);
            //当前用户节点的推文链表首添加，表示当前推文是最新的推文
            user.tweetLinkedList.addFirst(tweetId);
            //每发一条推文，time++
            time++;

            //当前用户节点的推文链表长度超过maxCount，则删除推文链表尾节点
            //因为只检索maxCount条用户推文，超过maxCount的推文不会被检索，可以直接删除
            if (user.tweetLinkedList.count > maxCount) {
                user.tweetLinkedList.removeLast();
            }
        }

        /**
         * 优先队列，大根堆
         * 时间复杂度O(maxCount*logn)，空间复杂度O(maxCount) (n=当前用户加上当前用户关注的用户的总数)
         *
         * @param userId
         * @return
         */
        public List<Integer> getNewsFeed(int userId) {
            //userMap中没有当前用户，即当前用户没有推文，直接返回空集合
            if (!userMap.containsKey(userId)) {
                return new ArrayList<>();
            }

            //当前用户
            User user = userMap.get(userId);

            //优先队列，大根堆，存储用户发布的推文节点，按照推文节点的发布时间由大到小排序
            PriorityQueue<TweetNode> priorityQueue = new PriorityQueue<>(new Comparator<TweetNode>() {
                @Override
                public int compare(TweetNode tweetNode1, TweetNode tweetNode2) {
                    return tweetNode2.time - tweetNode1.time;
                }
            });

            //当前用户有推文才入堆
            if (user.tweetLinkedList.count != 0) {
                priorityQueue.offer(user.tweetLinkedList.head.next);
            }

            //当前用户关注的用户有推文才入堆
            for (int followeeId : user.followeeSet) {
                //当前用户关注的用户
                User followeeUser = userMap.get(followeeId);

                if (followeeUser.tweetLinkedList.count != 0) {
                    priorityQueue.offer(followeeUser.tweetLinkedList.head.next);
                }
            }

            List<Integer> list = new ArrayList<>();

            //最多检索当前用户maxCount条推文
            while (!priorityQueue.isEmpty() && list.size() < maxCount) {
                //当前推文
                TweetNode tweetNode = priorityQueue.poll();
                list.add(tweetNode.id);

                //当前推文链表没有遍历到尾结点，则下一个推文节点入堆
                if (tweetNode.next.next != null) {
                    priorityQueue.offer(tweetNode.next);
                }
            }

            return list;
        }

        /**
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param followerId
         * @param followeeId
         */
        public void follow(int followerId, int followeeId) {
            if (!userMap.containsKey(followerId)) {
                userMap.put(followerId, new User(followerId));
            }

            if (!userMap.containsKey(followeeId)) {
                userMap.put(followeeId, new User(followeeId));
            }

            //自己不能关注自己
            if (followerId == followeeId) {
                return;
            }

            //followerId已经关注了followeeId，直接返回
            if (userMap.get(followerId).followeeSet.contains(followeeId)) {
                return;
            }

            userMap.get(followerId).followeeSet.add(followeeId);
        }

        /**
         * 时间复杂度O(1)，空间复杂度O(1)
         *
         * @param followerId
         * @param followeeId
         */
        public void unfollow(int followerId, int followeeId) {
            //followerId不存在，则不能取关
            if (!userMap.containsKey(followerId)) {
                return;
            }

            //followeeId不存在，则不能取关
            if (!userMap.containsKey(followeeId)) {
                return;
            }

            userMap.get(followerId).followeeSet.remove(followeeId);
        }

        /**
         * 用户节点
         */
        private static class User {
            //用户id
            private final int id;
            //用户发布的推文链表
            private final TweetLinkedList tweetLinkedList;
            //用户关注的用户哈希集合
            private final Set<Integer> followeeSet;

            public User(int userId) {
                this.id = userId;
                tweetLinkedList = new TweetLinkedList();
                followeeSet = new HashSet<>();
            }
        }

        /**
         * 推文双向链表
         */
        private static class TweetLinkedList {
            //头结点
            private final TweetNode head;
            //尾结点
            private final TweetNode tail;
            //推文链表的大小，即推文链表中推文的个数
            private int count;

            public TweetLinkedList() {
                head = new TweetNode();
                tail = new TweetNode();
                count = 0;
                head.next = tail;
                tail.pre = head;
            }

            public void addFirst(int tweetId) {
                TweetNode tweetNode = new TweetNode(tweetId);
                TweetNode nextTweetNode = head.next;
                tweetNode.next = nextTweetNode;
                nextTweetNode.pre = tweetNode;
                head.next = tweetNode;
                tweetNode.pre = head;
                count++;
            }

            public void removeLast() {
                //要删除的推文节点
                TweetNode removeTweetNode = tail.pre;
                //要删除的推文节点的前驱节点
                TweetNode preTweetNode = removeTweetNode.pre;
                preTweetNode.next = tail;
                tail.pre = preTweetNode;
                removeTweetNode.pre = null;
                removeTweetNode.next = null;
                count--;
            }
        }

        /**
         * 推文节点，即双向链表节点
         */
        private static class TweetNode {
            //推文id
            private int id;
            //推文时间
            private int time;
            private TweetNode pre;
            private TweetNode next;

            public TweetNode() {
            }

            public TweetNode(int id) {
                this.id = id;
                this.time = Twitter.time;
            }
        }
    }
}
