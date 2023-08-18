package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/8/16 08:11
 * @Author zsy
 * @Description 最小基因变化 双向bfs类比Problem126、Problem127、Problem752、Problem1345
 * 基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是 'A'、'C'、'G' 和 'T' 之一。
 * 假设我们需要调查从基因序列 start 变为 end 所发生的基因变化。
 * 一次基因变化就意味着这个基因序列中的一个字符发生了变化。
 * 例如，"AACCGGTT" --> "AACCGGTA" 就是一次基因变化。
 * 另有一个基因库 bank 记录了所有有效的基因变化，只有基因库中的基因才是有效的基因序列。（变化后的基因必须位于基因库 bank 中）
 * 给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出并返回能够使 start 变化为 end 所需的最少变化次数。
 * 如果无法完成此基因变化，返回 -1 。
 * 注意：起始基因序列 start 默认是有效的，但是它并不一定会出现在基因库中。
 * <p>
 * 输入：start = "AACCGGTT", end = "AACCGGTA", bank = ["AACCGGTA"]
 * 输出：1
 * <p>
 * 输入：start = "AACCGGTT", end = "AAACGGTA", bank = ["AACCGGTA","AACCGCTA","AAACGGTA"]
 * 输出：2
 * <p>
 * 输入：start = "AAAAACCC", end = "AACCCCCC", bank = ["AAAACCCC","AAACCCCC","AACCCCCC"]
 * 输出：3
 * <p>
 * start.length == 8
 * end.length == 8
 * 0 <= bank.length <= 10
 * bank[i].length == 8
 * start、end 和 bank[i] 仅由字符 ['A', 'C', 'G', 'T'] 组成
 */
public class Problem433 {
    public static void main(String[] args) {
        Problem433 problem433 = new Problem433();
        String startGene = "AAAAACCC";
        String endGene = "AACCCCCC";
        String[] bank = {"AAAACCCC", "AAACCCCC", "AACCCCCC"};
        System.out.println(problem433.minMutation(startGene, endGene, bank));
        System.out.println(problem433.minMutation2(startGene, endGene, bank));
    }

    /**
     * bfs
     * bfs每次往外扩一层，将当前层中所有基因通过基因库bank变化能够得到的基因全部加入队列中，直至遍历到endGene，
     * 或全部遍历完都没有找到endGene，返回-1
     * 时间复杂度O(Cmn)，空间复杂度O(mn) (m=startGene.length()=endGene.length()，n=bank.length) (C=4，基因变化只能是'A'、'C'、'G'、'T')
     *
     * @param startGene
     * @param endGene
     * @param bank
     * @return
     */
    public int minMutation(String startGene, String endGene, String[] bank) {
        Queue<String> queue = new LinkedList<>();
        //访问集合，存储当前已经访问到的基因
        Set<String> visitedSet = new HashSet<>();
        //基因库集合，存储bank中有效变化基因，O(1)判断当前发生变化的基因是否是基因库中的有效变化基因
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        queue.offer(startGene);
        visitedSet.add(startGene);

        //基因库集合中不包含endGene，则startGene无法变化为endGene，返回-1
        if (!bankSet.contains(endGene)) {
            return -1;
        }

        //bfs向外扩展的次数，startGene变化为endGene的最少次数
        int count = 0;
        //发生基因变化的字符数组
        char[] arr = {'A', 'C', 'G', 'T'};

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                //当前需要变化的基因
                String curGene = queue.poll();

                //当前基因已经变化为了endGene，则找到了startGene变化为endGene的最少次数，直接返回count
                if (endGene.equals(curGene)) {
                    return count;
                }

                //curGene转换为char数组，便于某一位的变化
                char[] geneArr = curGene.toCharArray();

                //变化curGene中的第j位
                for (int j = 0; j < geneArr.length; j++) {
                    //curGene中的第j位
                    char cur = geneArr[j];

                    //变化curGene中的第j位为arr[k]
                    for (int k = 0; k < arr.length; k++) {
                        geneArr[j] = arr[k];
                        //变化curGene中的第j位为arr[k]得到的基因
                        String nextGene = new String(geneArr);

                        //nextGene已经访问过，或者nextGene不是基因库中的有效变化基因，直接进行下次循环
                        if (visitedSet.contains(nextGene) || !bankSet.contains(nextGene)) {
                            continue;
                        }

                        //nextGene加入队列，并且设置nextGene已访问
                        queue.offer(nextGene);
                        visitedSet.add(nextGene);
                    }

                    //curGene中的第j位复原，用于第j+1位变化
                    geneArr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束也没有到达endGene，即startGene无法变化为endGene，返回-1
        return -1;
    }

    /**
     * 双向bfs
     * 从startGene和endGene同时开始bfs，bfs每次往外扩一层，将当前队列当前层中所有基因通过基因库bank变化能够得到的基因全部加入另一个队列中，
     * 直至一个队列中包含了另一个队列中的基因，即双向bfs相交，或者全部遍历完都没有找到endGene，返回-1
     * 注意：双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
     * 时间复杂度O(Cmn)，空间复杂度O(mn) (m=startGene.length()=endGene.length()，n=bank.length) (C=4，基因变化只能是'A'、'C'、'G'、'T')
     *
     * @param startGene
     * @param endGene
     * @param bank
     * @return
     */
    public int minMutation2(String startGene, String endGene, String[] bank) {
        //从前往后遍历的队列，即从startGene开始遍历
        Queue<String> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从endGene开始遍历
        Queue<String> queue2 = new LinkedList<>();
        //从前往后遍历的访问集合，存储queue1已经访问到的基因序列
        Set<String> visitedSet1 = new HashSet<>();
        //从后往前遍历的访问集合，存储queue2已经访问到的基因序列
        Set<String> visitedSet2 = new HashSet<>();
        //基因库集合，存储bank中有效变化基因，O(1)判断当前发生变化的基因是否是基因库中的有效变化基因
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));
        queue1.offer(startGene);
        queue2.offer(endGene);
        //注意：双向bfs，必须先将首尾节点在对应的set中设置为已访问，不能每次出队元素的时候再标记节点已访问
        visitedSet1.add(startGene);
        visitedSet2.add(endGene);

        //基因库集合中不包含endGene，则startGene无法变化为endGene，返回-1
        if (!bankSet.contains(endGene)) {
            return -1;
        }

        //双向bfs向外扩展的次数，两个队列相交，即startGene变化为endGene的最少次数
        int count = 0;
        //发生基因变化的字符数组
        char[] arr = {'A', 'C', 'G', 'T'};

        while (!queue1.isEmpty() && !queue2.isEmpty()) {
            //双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
            if (queue1.size() > queue2.size()) {
                Queue<String> tempQueue = queue1;
                queue1 = queue2;
                queue2 = tempQueue;
                Set<String> tempSet = visitedSet1;
                visitedSet1 = visitedSet2;
                visitedSet2 = tempSet;
            }

            int size = queue1.size();

            for (int i = 0; i < size; i++) {
                //当前需要变化的基因
                String curGene = queue1.poll();

                //curGene已经存在visitedSet2中，即双向bfs相交，则找到了startGene变化为endGene的最少次数，直接返回count
                if (visitedSet2.contains(curGene)) {
                    return count;
                }

                //curGene转换为char数组，便于某一位的变化
                char[] geneArr = curGene.toCharArray();

                //变化curGene中的第j位
                for (int j = 0; j < geneArr.length; j++) {
                    //curGene中的第j位
                    char cur = geneArr[j];

                    //变化curGene中的第j位为arr[k]
                    for (int k = 0; k < arr.length; k++) {
                        geneArr[j] = arr[k];
                        //变化curGene中的第j位为arr[k]得到的基因
                        String nextGene = new String(geneArr);

                        //nextGene已经访问过，或者nextGene不是基因库中的有效变化基因，直接进行下次循环
                        if (visitedSet1.contains(nextGene) || !bankSet.contains(nextGene)) {
                            continue;
                        }

                        //nextGene加入队列queue1，并且设置nextGene已访问
                        queue1.offer(nextGene);
                        visitedSet1.add(nextGene);
                    }

                    //curGene中的第j位复原，用于第j+1位变化
                    geneArr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束也没有到达endGene，即startGene无法变化为endGene，返回-1
        return -1;
    }
}