package com.zhang.java;


/**
 * @Date 2024/9/15 08:10
 * @Author zsy
 * @Description 设计最近使用（MRU）队列 类比Problem2940 类比Problem146、Problem432、Problem460、Problem895、Problem1797 线段树类比
 * 设计一种类似队列的数据结构，该数据结构将最近使用的元素移到队列尾部。
 * 实现 MRUQueue 类：
 * MRUQueue(int n)  使用 n 个元素： [1,2,3,...,n] 构造 MRUQueue 。
 * fetch(int k) 将第 k 个元素（从 1 开始索引）移到队尾，并返回该元素。
 * 进阶：找到每次 fetch 的复杂度为 O(n) 的算法比较简单。你可以找到每次 fetch 的复杂度更佳的算法吗？
 * <p>
 * 输入：
 * ["MRUQueue", "fetch", "fetch", "fetch", "fetch"]
 * [[8], [3], [5], [2], [8]]
 * 输出：
 * [null, 3, 6, 2, 2]
 * 解释：
 * MRUQueue mRUQueue = new MRUQueue(8); // 初始化队列为 [1,2,3,4,5,6,7,8]。
 * mRUQueue.fetch(3); // 将第 3 个元素 (3) 移到队尾，使队列变为 [1,2,4,5,6,7,8,3] 并返回该元素。
 * mRUQueue.fetch(5); // 将第 5 个元素 (6) 移到队尾，使队列变为 [1,2,4,5,7,8,3,6] 并返回该元素。
 * mRUQueue.fetch(2); // 将第 2 个元素 (2) 移到队尾，使队列变为 [1,4,5,7,8,3,6,2] 并返回该元素。
 * mRUQueue.fetch(8); // 第 8 个元素 (2) 已经在队列尾部了，所以直接返回该元素即可。
 * <p>
 * 1 <= n <= 2000
 * 1 <= k <= n
 * 最多调用 2000 次 fetch
 */
public class Problem1756 {
    public static void main(String[] args) {
        // 初始化队列为 [1,2,3,4,5,6,7,8]。
//        MRUQueue mRUQueue = new MRUQueue(8);
        MRUQueue2 mRUQueue = new MRUQueue2(8);
        // 将第 3 个元素 (3) 移到队尾，使队列变为 [1,2,4,5,6,7,8,3] 并返回该元素。
        System.out.println(mRUQueue.fetch(3));
        // 将第 5 个元素 (6) 移到队尾，使队列变为 [1,2,4,5,7,8,3,6] 并返回该元素。
        System.out.println(mRUQueue.fetch(5));
        // 将第 2 个元素 (2) 移到队尾，使队列变为 [1,4,5,7,8,3,6,2] 并返回该元素。
        System.out.println(mRUQueue.fetch(2));
        // 第 8 个元素 (2) 已经在队列尾部了，所以直接返回该元素即可。
        System.out.println(mRUQueue.fetch(8));
    }

    /**
     * 数组模拟
     */
    static class MRUQueue {
        private final int n;
        private final int[] arr;

        public MRUQueue(int n) {
            this.n = n;
            arr = new int[n + 1];

            //1-n元素初始化
            for (int i = 1; i <= n; i++) {
                arr[i] = i;
            }
        }

        /**
         * arr[k+1]-arr[n]左移一位，arr[k]放在末尾
         * 时间复杂度O(n)，空间复杂度O(1)
         *
         * @param k
         * @return
         */
        public int fetch(int k) {
            int result = arr[k];

            for (int i = k; i < n; i++) {
                arr[i] = arr[i + 1];
            }

            arr[n] = result;

            return result;
        }
    }

    /**
     * 二分查找+线段树 (空间换时间)
     * 核心思想：移除第k个元素，并不是删除第k个元素，而是将第k个元素拼接到末尾，将第k个元素加入线段树，标记第k个元素已删除，
     * 通过线段树统计区间内标记为删除的元素个数，这些标记为删除的元素都移动到末尾，
     * 二分查找第k个元素，arr[1]-arr[i]中元素的个数减去arr[1]-arr[i]中标记为删除的元素个数等于k，则arr[i]为第k个元素
     */
    static class MRUQueue2 {
        //队列数组，移除第k个元素，并不是删除第k个元素，而是将第k个元素拼接到末尾
        private final int[] arr;
        //线段树，用于统计arr区间内标记为删除的元素个数
        private final SegmentTree segmentTree;
        //当前队列数组中元素的个数，每次移动一个元素，数组长度加1
        private int count;

        public MRUQueue2(int n) {
            //最多调用fetch()2000次，每次需要将第k个元素拼接到末尾，所以数组长度要加上2000
            arr = new int[n + 1 + 2000];
            //最多调用fetch()2000次，每次需要将第k个元素拼接到末尾，所以数组长度要加上2000
            segmentTree = new SegmentTree(n + 2000);
            count = n;

            //1-n元素初始化
            for (int i = 1; i <= n; i++) {
                arr[i] = i;
            }
        }

        /**
         * 二分查找第k个元素，arr[1]-arr[i]中元素的个数减去arr[1]-arr[i]中标记为删除的元素个数等于k，则arr[i]为第k个元素
         * 时间复杂度O((logn)^2)，空间复杂度O(1)
         *
         * @param k
         * @return
         */
        public int fetch(int k) {
            //第k个元素在arr中的下标索引
            int index = 0;
            int left = 1;
            int right = count;
            int mid;

            while (left <= right) {
                mid = left + ((right - left) >> 1);

                //arr[1]-arr[mid]中元素的个数减去arr[1]-arr[mid]中标记为删除的元素个数等于k，则第k个元素为arr[mid]
                if (mid - segmentTree.query(segmentTree.root, 1, mid) == k) {
                    index = mid;
                    break;
                } else if (mid - segmentTree.query(segmentTree.root, 1, mid) < k) {
                    //arr[1]-arr[mid]中元素的个数减去arr[1]-arr[mid]中标记为删除的元素个数小于k，则第k个元素在arr[mid]右边
                    left = mid + 1;
                } else {
                    //arr[1]-arr[mid]中元素的个数减去arr[1]-arr[mid]中标记为删除的元素个数大于k，则第k个元素在arr[mid]左边
                    right = mid - 1;
                }
            }

            //第k个元素
            int result = arr[index];
            //arr中元素个数加1
            count++;
            //第k个元素拼接到末尾
            arr[count] = result;
            //第k个元素加入线段树，即标记arr中index位置元素已删除
            segmentTree.update(segmentTree.root, index, index, 1);

            return result;
        }

        /**
         * 线段树，动态开点
         */
        private static class SegmentTree {
            private final SegmentTreeNode root;

            public SegmentTree(int n) {
                root = new SegmentTreeNode(1, n);
            }

            public int query(SegmentTreeNode node, int queryLeft, int queryRight) {
                if (queryLeft > node.rightBound || queryRight < node.leftBound) {
                    return 0;
                }

                if (queryLeft <= node.leftBound && node.rightBound <= queryRight) {
                    return node.value;
                }

                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                if (node.lazyValue != 0) {
                    node.leftNode.value = node.leftNode.value + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.value = node.rightNode.value + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                int leftValue = query(node.leftNode, queryLeft, queryRight);
                int rightValue = query(node.rightNode, queryLeft, queryRight);

                return leftValue + rightValue;
            }

            public void update(SegmentTreeNode node, int updateLeft, int updateRight, int value) {
                if (updateLeft > node.rightBound || updateRight < node.leftBound) {
                    return;
                }

                if (updateLeft <= node.leftBound && node.rightBound <= updateRight) {
                    node.value = node.value + (node.rightBound - node.leftBound + 1) * value;
                    node.lazyValue = node.lazyValue + value;
                    return;
                }

                int mid = node.leftBound + ((node.rightBound - node.leftBound) >> 1);

                if (node.leftNode == null) {
                    node.leftNode = new SegmentTreeNode(node.leftBound, mid);
                }

                if (node.rightNode == null) {
                    node.rightNode = new SegmentTreeNode(mid + 1, node.rightBound);
                }

                if (node.lazyValue != 0) {
                    node.leftNode.value = node.leftNode.value + (mid - node.leftBound + 1) * node.lazyValue;
                    node.rightNode.value = node.rightNode.value + (node.rightBound - mid) * node.lazyValue;
                    node.leftNode.lazyValue = node.leftNode.lazyValue + node.lazyValue;
                    node.rightNode.lazyValue = node.rightNode.lazyValue + node.lazyValue;

                    node.lazyValue = 0;
                }

                update(node.leftNode, updateLeft, updateRight, value);
                update(node.rightNode, updateLeft, updateRight, value);

                node.value = node.leftNode.value + node.rightNode.value;
            }

            /**
             * 线段树节点
             */
            private static class SegmentTreeNode {
                //区间[leftBound,rightBound]元素发生移动的次数之和
                private int value;
                private int lazyValue;
                private int leftBound;
                private int rightBound;
                private SegmentTreeNode leftNode;
                private SegmentTreeNode rightNode;

                public SegmentTreeNode(int leftBound, int rightBound) {
                    this.leftBound = leftBound;
                    this.rightBound = rightBound;
                }
            }
        }
    }
}
