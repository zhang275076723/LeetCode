package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/10/14 08:52
 * @Author zsy
 * @Description 序列重建 拓扑排序类比
 * 给定一个长度为 n 的整数数组 nums ，其中 nums 是范围为 [1，n] 的整数的排列。
 * 还提供了一个 2D 整数数组 sequences ，其中 sequences[i] 是 nums 的子序列。
 * 检查 nums 是否是唯一的最短 超序列 。
 * 最短 超序列 是 长度最短 的序列，并且所有序列 sequences[i] 都是它的子序列。
 * 对于给定的数组 sequences ，可能存在多个有效的 超序列 。
 * 例如，对于 sequences = [[1,2],[1,3]] ，有两个最短的 超序列 ，[1,2,3] 和 [1,3,2] 。
 * 而对于 sequences = [[1,2],[1,3],[1,2,3]] ，唯一可能的最短 超序列 是 [1,2,3] 。
 * [1,2,3,4] 是可能的超序列，但不是最短的。
 * 如果 nums 是序列的唯一最短 超序列 ，则返回 true ，否则返回 false 。
 * 子序列 是一个可以通过从另一个序列中删除一些元素或不删除任何元素，而不改变其余元素的顺序的序列。
 * <p>
 * 输入：nums = [1,2,3], sequences = [[1,2],[1,3]]
 * 输出：false
 * 解释：有两种可能的超序列：[1,2,3]和[1,3,2]。
 * 序列 [1,2] 是[1,2,3]和[1,3,2]的子序列。
 * 序列 [1,3] 是[1,2,3]和[1,3,2]的子序列。
 * 因为 nums 不是唯一最短的超序列，所以返回false。
 * <p>
 * 输入：nums = [1,2,3], sequences = [[1,2]]
 * 输出：false
 * 解释：最短可能的超序列为 [1,2]。
 * 序列 [1,2] 是它的子序列：[1,2]。
 * 因为 nums 不是最短的超序列，所以返回false。
 * <p>
 * 输入：nums = [1,2,3], sequences = [[1,2],[1,3],[2,3]]
 * 输出：true
 * 解释：最短可能的超序列为[1,2,3]。
 * 序列 [1,2] 是它的一个子序列：[1,2,3]。
 * 序列 [1,3] 是它的一个子序列：[1,2,3]。
 * 序列 [2,3] 是它的一个子序列：[1,2,3]。
 * 因为 nums 是唯一最短的超序列，所以返回true。
 * <p>
 * n == nums.length
 * 1 <= n <= 10^4
 * nums 是 [1, n] 范围内所有整数的排列
 * 1 <= sequences.length <= 10^4
 * 1 <= sequences[i].length <= 10^4
 * 1 <= sum(sequences[i].length) <= 10^5
 * 1 <= sequences[i][j] <= n
 * sequences 的所有数组都是 唯一 的
 * sequences[i] 是 nums 的一个子序列
 */
public class Problem444 {
    public static void main(String[] args) {
        Problem444 problem444 = new Problem444();
        int[] nums = {1, 2, 3};
        List<List<Integer>> sequences = new ArrayList<>();
        List<Integer> list1 = new ArrayList<Integer>() {{
            add(1);
            add(3);
        }};
        List<Integer> list2 = new ArrayList<Integer>() {{
            add(2);
            add(3);
        }};
        sequences.add(list1);
        sequences.add(list2);
        System.out.println(problem444.sequenceReconstruction(nums, sequences));
    }

    /**
     * bfs拓扑排序
     * 核心思想：图的拓扑排序唯一且为nums，则nums是唯一的最短超序列
     * 图中入度为0的节点入队，队列中节点出队，当前节点的邻接节点的入度减1，邻接节点入度为0的节点入队，
     * 如果队列中节点个数超过1个，则存在多个拓扑排序，返回false，
     * 遍历结束判断是否能够遍历到所有的节点，如果能遍历到所有的节点，则存在唯一拓扑排序，返回true；否则返回false
     * 时间复杂度O(m+n)，空间复杂度O(n^2) (m=sequences.length()，m即为图中边的数量)
     *
     * @param nums
     * @param sequences
     * @return
     */
    public boolean sequenceReconstruction(int[] nums, List<List<Integer>> sequences) {
        int n = nums.length;
        //邻接矩阵，节点范围为1-n
        int[][] edges = new int[n + 1][n + 1];
        //图中节点的集合
        Set<Integer> set = new HashSet<>();
        int[] inDegree = new int[n + 1];

        for (List<Integer> list : sequences) {
            edges[list.get(0)][list.get(1)] = 1;
            set.add(list.get(0));
            set.add(list.get(1));
            inDegree[list.get(1)]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        //bfs遍历到的节点数量
        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (set.contains(i) && inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            //队列中节点个数超过1个，则存在多个拓扑排序，返回false
            if (queue.size() > 1) {
                return false;
            }

            int u = queue.poll();
            count++;

            for (int v = 1; v <= n; v++) {
                if (edges[u][v] != 0) {
                    inDegree[v]--;

                    //邻接节点v的入度为0，则入队
                    if (inDegree[v] == 0) {
                        queue.offer(v);
                    }
                }
            }
        }

        //能遍历到所有的节点，则存在唯一拓扑排序
        return count == n;
    }
}
