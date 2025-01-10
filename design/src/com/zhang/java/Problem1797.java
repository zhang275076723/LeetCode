package com.zhang.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2024/9/22 08:39
 * @Author zsy
 * @Description 设计一个验证系统 类比Problem146、Problem432、Problem460、Problem895、Problem1756
 * 你需要设计一个包含验证码的验证系统。每一次验证中，用户会收到一个新的验证码，
 * 这个验证码在 currentTime 时刻之后 timeToLive 秒过期。
 * 如果验证码被更新了，那么它会在 currentTime （可能与之前的 currentTime 不同）时刻延长 timeToLive 秒。
 * 请你实现 AuthenticationManager 类：
 * AuthenticationManager(int timeToLive) 构造 AuthenticationManager 并设置 timeToLive 参数。
 * generate(string tokenId, int currentTime) 给定 tokenId ，在当前时间 currentTime 生成一个新的验证码。
 * renew(string tokenId, int currentTime) 将给定 tokenId 且 未过期 的验证码在 currentTime 时刻更新。
 * 如果给定 tokenId 对应的验证码不存在或已过期，请你忽略该操作，不会有任何更新操作发生。
 * countUnexpiredTokens(int currentTime) 请返回在给定 currentTime 时刻，未过期 的验证码数目。
 * 如果一个验证码在时刻 t 过期，且另一个操作恰好在时刻 t 发生（renew 或者 countUnexpiredTokens 操作），
 * 过期事件 优先于 其他操作。
 * <p>
 * 输入：
 * ["AuthenticationManager", "renew", "generate", "countUnexpiredTokens", "generate", "renew", "renew", "countUnexpiredTokens"]
 * [[5], ["aaa", 1], ["aaa", 2], [6], ["bbb", 7], ["aaa", 8], ["bbb", 10], [15]]
 * 输出：
 * [null, null, null, 1, null, null, null, 0]
 * 解释：
 * AuthenticationManager authenticationManager = new AuthenticationManager(5); // 构造 AuthenticationManager ，设置 timeToLive = 5 秒。
 * authenticationManager.renew("aaa", 1); // 时刻 1 时，没有验证码的 tokenId 为 "aaa" ，没有验证码被更新。
 * authenticationManager.generate("aaa", 2); // 时刻 2 时，生成一个 tokenId 为 "aaa" 的新验证码。
 * authenticationManager.countUnexpiredTokens(6); // 时刻 6 时，只有 tokenId 为 "aaa" 的验证码未过期，所以返回 1 。
 * authenticationManager.generate("bbb", 7); // 时刻 7 时，生成一个 tokenId 为 "bbb" 的新验证码。
 * authenticationManager.renew("aaa", 8); // tokenId 为 "aaa" 的验证码在时刻 7 过期，且 8 >= 7 ，所以时刻 8 的renew 操作被忽略，没有验证码被更新。
 * authenticationManager.renew("bbb", 10); // tokenId 为 "bbb" 的验证码在时刻 10 没有过期，所以 renew 操作会执行，该 token 将在时刻 15 过期。
 * authenticationManager.countUnexpiredTokens(15); // tokenId 为 "bbb" 的验证码在时刻 15 过期，tokenId 为 "aaa" 的验证码在时刻 7 过期，所有验证码均已过期，所以返回 0 。
 * <p>
 * 1 <= timeToLive <= 10^8
 * 1 <= currentTime <= 10^8
 * 1 <= tokenId.length <= 5
 * tokenId 只包含小写英文字母。
 * 所有 generate 函数的调用都会包含独一无二的 tokenId 值。
 * 所有函数调用中，currentTime 的值 严格递增 。
 * 所有函数的调用次数总共不超过 2000 次。
 */
public class Problem1797 {
    public static void main(String[] args) {
        // 构造 AuthenticationManager ，设置 timeToLive = 5 秒。
//        AuthenticationManager authenticationManager = new AuthenticationManager(5);
        AuthenticationManager2 authenticationManager = new AuthenticationManager2(5);
        // 时刻 1 时，没有验证码的 tokenId 为 "aaa" ，没有验证码被更新。
        authenticationManager.renew("aaa", 1);
        // 时刻 2 时，生成一个 tokenId 为 "aaa" 的新验证码。
        authenticationManager.generate("aaa", 2);
        // 时刻 6 时，只有 tokenId 为 "aaa" 的验证码未过期，所以返回 1 。
        System.out.println(authenticationManager.countUnexpiredTokens(6));
        // 时刻 7 时，生成一个 tokenId 为 "bbb" 的新验证码。
        authenticationManager.generate("bbb", 7);
        // tokenId 为 "aaa" 的验证码在时刻 7 过期，且 8 >= 7 ，所以时刻 8 的renew 操作被忽略，没有验证码被更新。
        authenticationManager.renew("aaa", 8);
        // tokenId 为 "bbb" 的验证码在时刻 10 没有过期，所以 renew 操作会执行，该 token 将在时刻 15 过期。
        authenticationManager.renew("bbb", 10);
        // tokenId 为 "bbb" 的验证码在时刻 15 过期，tokenId 为 "aaa" 的验证码在时刻 7 过期，所有验证码均已过期，所以返回 0 。
        System.out.println(authenticationManager.countUnexpiredTokens(15));
    }

    /**
     * 哈希表
     */
    static class AuthenticationManager {
        //key：验证码tokenId，value：验证码过期时间，有效时间不包含当前过期时间
        private final Map<String, Integer> map;
        //验证码存活时间
        private final int timeToLive;

        public AuthenticationManager(int timeToLive) {
            map = new HashMap<>();
            this.timeToLive = timeToLive;
        }

        public void generate(String tokenId, int currentTime) {
            map.put(tokenId, currentTime + timeToLive);
        }

        public void renew(String tokenId, int currentTime) {
            //不存在tokenId，则直接返回
            if (!map.containsKey(tokenId)) {
                return;
            }

            //tokenId已过期，则直接返回
            if (map.get(tokenId) <= currentTime) {
                return;
            }

            //更新tokenId
            map.put(tokenId, currentTime + timeToLive);
        }

        /**
         * 时间复杂度O(n)，空间复杂度O(1) (n=map.size())
         *
         * @param currentTime
         * @return
         */
        public int countUnexpiredTokens(int currentTime) {
            //currentTime未过期的验证码个数
            int count = 0;

            for (int expireTime : map.values()) {
                if (expireTime > currentTime) {
                    count++;
                }
            }

            return count;
        }
    }

    /**
     * 哈希表+双向链表 (lru)
     * 注意：调用所有方法中的currentTime严格递增，才能使用当前方法，因为currentTime严格递增保证链表中节点的验证码过期时间由小到大排序
     */
    static class AuthenticationManager2 {
        //key：验证码tokenId，value：验证码节点
        private final Map<String, Node> map;
        //验证码节点双向链表，验证码节点按照过期时间由小到大排序
        private final LinkedList linkedList;
        //验证码存活时间
        private final int timeToLive;

        public AuthenticationManager2(int timeToLive) {
            map = new HashMap<>();
            linkedList = new LinkedList();
            this.timeToLive = timeToLive;
        }

        public void generate(String tokenId, int currentTime) {
            Node node = new Node(tokenId, currentTime + timeToLive);
            map.put(tokenId, node);
            //currentTime严格递增，所以新建验证码节点加入链表尾
            linkedList.addLast(node);
        }

        public void renew(String tokenId, int currentTime) {
            //不存在tokenId，则直接返回
            if (!map.containsKey(tokenId)) {
                return;
            }

            //tokenId已过期，则直接返回
            if (map.get(tokenId).expireTime <= currentTime) {
                return;
            }

            Node node = map.get(tokenId);
            node.expireTime = currentTime + timeToLive;
            //currentTime严格递增，所以node从链表中删除，加入链表尾
            linkedList.remove(node);
            linkedList.addLast(node);
        }

        /**
         * currentTime严格递增，所以将链表中验证码过期时间小于等于currentTime的节点全部删除，剩余节点即为未过期的节点
         * 时间复杂度O(n)，空间复杂度O(1) (n=map.size())
         *
         * @param currentTime
         * @return
         */
        public int countUnexpiredTokens(int currentTime) {
            //当前节点验证码过期时间小于等于currentTime，则当前节点从map中删除，链表中删除当前节点
            while (linkedList.head.next != linkedList.tail && linkedList.head.next.expireTime <= currentTime) {
                map.remove(linkedList.head.next.tokenId);
                linkedList.remove(linkedList.head.next);
            }

            //剩余节点即为未过期的节点
            return map.size();
        }

        /**
         * 验证码节点双向链表，验证码节点按照过期时间由小到大排序
         */
        private static class LinkedList {
            private final Node head;
            private final Node tail;

            public LinkedList() {
                head = new Node();
                tail = new Node();
                head.next = tail;
                tail.pre = head;
            }

            public void addLast(Node node) {
                Node preNode = tail.pre;
                preNode.next = node;
                node.pre = preNode;
                node.next = tail;
                tail.pre = node;
            }

            public void remove(Node node) {
                Node preNode = node.pre;
                Node nextNode = node.next;
                preNode.next = nextNode;
                nextNode.pre = preNode;
                node.pre = null;
                node.next = null;
            }
        }

        /**
         * 验证码节点
         */
        private static class Node {
            //验证码tokenId
            private String tokenId;
            //验证码过期时间，有效时间不包含当前过期时间
            private int expireTime;
            private Node pre;
            private Node next;

            public Node() {

            }

            public Node(String tokenId, int expireTime) {
                this.tokenId = tokenId;
                this.expireTime = expireTime;
            }
        }
    }
}
