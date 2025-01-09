package com.zhang.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2024/9/17 08:58
 * @Author zsy
 * @Description 设计浏览器历史记录 类比Problem622、Problem641、Problem2296
 * 你有一个只支持单个标签页的 浏览器 ，最开始你浏览的网页是 homepage ，你可以访问其他的网站 url ，
 * 也可以在浏览历史中后退 steps 步或前进 steps 步。
 * 请你实现 BrowserHistory 类：
 * BrowserHistory(string homepage) ，用 homepage 初始化浏览器类。
 * void visit(string url) 从当前页跳转访问 url 对应的页面  。执行此操作会把浏览历史前进的记录全部删除。
 * string back(int steps) 在浏览历史中后退 steps 步。如果你只能在浏览历史中后退至多 x 步且 steps > x ，那么你只后退 x 步。
 * 请返回后退 至多 steps 步以后的 url 。
 * string forward(int steps) 在浏览历史中前进 steps 步。如果你只能在浏览历史中前进至多 x 步且 steps > x ，那么你只前进 x 步。
 * 请返回前进 至多 steps步以后的 url 。
 * <p>
 * 输入：
 * ["BrowserHistory","visit","visit","visit","back","back","forward","visit","forward","back","back"]
 * [["leetcode.com"],["google.com"],["facebook.com"],["youtube.com"],[1],[1],[1],["linkedin.com"],[2],[2],[7]]
 * 输出：
 * [null,null,null,null,"facebook.com","google.com","facebook.com",null,"linkedin.com","google.com","leetcode.com"]
 * 解释：
 * BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
 * browserHistory.visit("google.com");       // 你原本在浏览 "leetcode.com" 。访问 "google.com"
 * browserHistory.visit("facebook.com");     // 你原本在浏览 "google.com" 。访问 "facebook.com"
 * browserHistory.visit("youtube.com");      // 你原本在浏览 "facebook.com" 。访问 "youtube.com"
 * browserHistory.back(1);                   // 你原本在浏览 "youtube.com" ，后退到 "facebook.com" 并返回 "facebook.com"
 * browserHistory.back(1);                   // 你原本在浏览 "facebook.com" ，后退到 "google.com" 并返回 "google.com"
 * browserHistory.forward(1);                // 你原本在浏览 "google.com" ，前进到 "facebook.com" 并返回 "facebook.com"
 * browserHistory.visit("linkedin.com");     // 你原本在浏览 "facebook.com" 。 访问 "linkedin.com"
 * browserHistory.forward(2);                // 你原本在浏览 "linkedin.com" ，你无法前进任何步数。
 * browserHistory.back(2);                   // 你原本在浏览 "linkedin.com" ，后退两步依次先到 "facebook.com" ，然后到 "google.com" ，并返回 "google.com"
 * browserHistory.back(7);                   // 你原本在浏览 "google.com"， 你只能后退一步到 "leetcode.com" ，并返回 "leetcode.com"
 * <p>
 * 1 <= homepage.length <= 20
 * 1 <= url.length <= 20
 * 1 <= steps <= 100
 * homepage 和 url 都只包含 '.' 或者小写英文字母。
 * 最多调用 5000 次 visit， back 和 forward 函数。
 */
public class Problem1472 {
    public static void main(String[] args) {
        BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
//        BrowserHistory2 browserHistory = new BrowserHistory2("leetcode.com");
        // 你原本在浏览 "leetcode.com" 。访问 "google.com"
        browserHistory.visit("google.com");
        // 你原本在浏览 "google.com" 。访问 "facebook.com"
        browserHistory.visit("facebook.com");
        // 你原本在浏览 "facebook.com" 。访问 "youtube.com"
        browserHistory.visit("youtube.com");
        // 你原本在浏览 "youtube.com" ，后退到 "facebook.com" 并返回 "facebook.com"
        System.out.println(browserHistory.back(1));
        // 你原本在浏览 "facebook.com" ，后退到 "google.com" 并返回 "google.com"
        System.out.println(browserHistory.back(1));
        // 你原本在浏览 "google.com" ，前进到 "facebook.com" 并返回 "facebook.com"
        System.out.println(browserHistory.forward(1));
        // 你原本在浏览 "facebook.com" 。 访问 "linkedin.com"
        browserHistory.visit("linkedin.com");
        // 你原本在浏览 "linkedin.com" ，你无法前进任何步数。
        System.out.println(browserHistory.forward(2));
        // 你原本在浏览 "linkedin.com" ，后退两步依次先到 "facebook.com" ，然后到 "google.com" ，并返回 "google.com"
        System.out.println(browserHistory.back(2));
        // 你原本在浏览 "google.com"， 你只能后退一步到 "leetcode.com" ，并返回 "leetcode.com"
        System.out.println(browserHistory.back(7));
    }

    /**
     * 数组+双指针
     */
    static class BrowserHistory {
        //存储url的集合
        private final List<String> list;
        //当前访问到的list中下标索引
        private int curIndex;
        //可以访问到的list中最大下标索引，注意不是list的最大下标索引
        private int maxIndex;

        public BrowserHistory(String homepage) {
            list = new ArrayList<>();
            list.add(homepage);
            curIndex = 0;
            maxIndex = 0;
        }

        public void visit(String url) {
            curIndex++;

            //当前访问的下标索引小于list的大小，即list中从curIndex往后的元素都无效，需要重新赋值
            if (curIndex < list.size()) {
                list.set(curIndex, url);
            } else {
                list.add(url);
            }

            maxIndex = curIndex;
        }

        public String back(int steps) {
            curIndex = Math.max(curIndex - steps, 0);

            return list.get(curIndex);
        }

        public String forward(int steps) {
            curIndex = Math.min(curIndex + steps, maxIndex);

            return list.get(curIndex);
        }
    }

    /**
     * 双向链表
     */
    static class BrowserHistory2 {
        private final LinkedList linkedList;
        //当前所在节点的位置
        private Node curNode;

        public BrowserHistory2(String homepage) {
            linkedList = new LinkedList();
            linkedList.addLast(homepage);
            curNode = linkedList.tail.pre;
        }

        public void visit(String url) {
            //删除当前节点之后的所有节点，即当前节点作为尾结点
            curNode.next = linkedList.tail;
            linkedList.tail.pre = curNode;
            linkedList.addLast(url);
            curNode = linkedList.tail.pre;
        }

        public String back(int steps) {
            //最多只能往左移动到第一个节点
            while (curNode != linkedList.head.next && steps > 0) {
                curNode = curNode.pre;
                steps--;
            }

            return curNode.url;
        }

        public String forward(int steps) {
            //最多只能往右移动到最后一个节点
            while (curNode != linkedList.tail.pre && steps > 0) {
                curNode = curNode.next;
                steps--;
            }

            return curNode.url;
        }

        /**
         * 双向链表
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

            public void addLast(String value) {
                Node addNode = new Node(value);
                Node preNode = tail.pre;
                preNode.next = addNode;
                addNode.pre = preNode;
                addNode.next = tail;
                tail.pre = addNode;
            }
        }

        /**
         * 双向链表节点
         */
        private static class Node {
            private String url;
            private Node pre;
            private Node next;

            public Node() {

            }

            public Node(String url) {
                this.url = url;
            }
        }
    }
}
