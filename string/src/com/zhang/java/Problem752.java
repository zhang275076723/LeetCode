package com.zhang.java;

import java.util.*;

/**
 * @Date 2023/8/15 08:59
 * @Author zsy
 * @Description 打开转盘锁 双向bfs类比Problem126、Problem127、Problem433、Problem1345
 * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
 * 每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
 * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
 * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
 * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
 * <p>
 * 输入：deadends = ["0201","0101","0102","1212","2002"], target = "0202"
 * 输出：6
 * 解释：
 * 可能的移动序列为 "0000" -> "1000" -> "1100" -> "1200" -> "1201" -> "1202" -> "0202"。
 * 注意 "0000" -> "0001" -> "0002" -> "0102" -> "0202" 这样的序列是不能解锁的，
 * 因为当拨动到 "0102" 时这个锁就会被锁定。
 * <p>
 * 输入: deadends = ["8888"], target = "0009"
 * 输出：1
 * 解释：把最后一位反向旋转一次即可 "0000" -> "0009"。
 * <p>
 * 输入: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target = "8888"
 * 输出：-1
 * 解释：无法旋转到目标数字且不被锁定。
 * <p>
 * 1 <= deadends.length <= 500
 * deadends[i].length == 4
 * target.length == 4
 * target 不在 deadends 之中
 * target 和 deadends[i] 仅由若干位数字组成
 */
public class Problem752 {
    public static void main(String[] args) {
        Problem752 problem752 = new Problem752();
        String[] deadends = {"0201", "0101", "0102", "1212", "2002"};
        String target = "0202";
        System.out.println(problem752.openLock(deadends, target));
        System.out.println(problem752.openLock2(deadends, target));
    }

    /**
     * bfs
     * bfs每次往外扩一层，将当前层中所有数字通过旋转能够得到的数字全部加入队列中，直至遍历到target，
     * 或者全部遍历完都没有找到target，返回-1
     * 时间复杂度O(m^2*10^m)，空间复杂度O(m*10^m) (m=target.length()，n=deadends.length)
     *
     * @param deadends
     * @param target
     * @return
     */
    public int openLock(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 0;
        }

        Queue<String> queue = new LinkedList<>();
        //访问集合，存储当前已经访问到的数字
        Set<String> visitedSet = new HashSet<>();
        //死亡数字集合，存储deadends中死亡数字，O(1)判断当前数字是否是死亡数字
        Set<String> deadendsSet = new HashSet<>(Arrays.asList(deadends));
        queue.offer("0000");
        visitedSet.add("0000");

        //"0000"或target为死亡数字，则无法旋转到target，返回-1
        if (deadendsSet.contains("0000") || deadendsSet.contains(target)) {
            return -1;
        }

        //bfs向外扩展的次数，"0000"旋转为target的最少次数
        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                //当前需要旋转的数字
                String num = queue.poll();

                //当前数字已经旋转为了target，则找到了"0000"旋转为target的最少次数，直接返回count
                if (target.equals(num)) {
                    return count;
                }

                //num转换为char数组，便于某一位的旋转
                char[] arr = num.toCharArray();

                //旋转num中的第j位数字
                for (int j = 0; j < arr.length; j++) {
                    //num中第j位数字
                    char cur = arr[j];
                    //cur向前旋转后的数字
                    char pre;
                    //cur向后旋转后的数字
                    char next;

                    if (cur == '0') {
                        pre = '9';
                        next = '1';
                    } else if (cur == '9') {
                        pre = '8';
                        next = '0';
                    } else {
                        pre = (char) (cur - 1);
                        next = (char) (cur + 1);
                    }

                    arr[j] = pre;
                    //num第j位数字向前旋转后的数字
                    String preNum = new String(arr);
                    arr[j] = next;
                    //num第j位数字向后旋转后的数字
                    String nextNum = new String(arr);

                    //num第j位数字向前旋转后的数字preNum没有访问，并且preNum不是死亡数字，则加入队列，并且设置preNum已访问
                    if (!visitedSet.contains(preNum) && !deadendsSet.contains(preNum)) {
                        queue.offer(preNum);
                        visitedSet.add(preNum);
                    }

                    //num第j位数字向后旋转后的数字nextNum没有访问，并且nextNum不是死亡数字，则加入队列，并且设置nextNum已访问
                    if (!visitedSet.contains(nextNum) && !deadendsSet.contains(nextNum)) {
                        queue.offer(nextNum);
                        visitedSet.add(nextNum);
                    }

                    //num第j位数字复原，用于第j+1位数字旋转
                    arr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束也没有到达target，即"0000"无法到达target，则无法解锁，返回-1
        return -1;
    }

    /**
     * 双向bfs
     * 从"0000"和target同时开始bfs，bfs每次往外扩一层，将当前队列当前层中所有数字通过旋转能够得到的数字全部加入另一个队列中，
     * 直至一个队列中包含了另一个队列中的数字，即双向bfs相交，或者全部遍历完都没有找到target，返回-1
     * 注意：双向bfs优先遍历两个队列中较少的队列，因为较少的队列，扩展一层得到的元素少，能够加快查询速度
     * 时间复杂度O(m^2*10^m)，空间复杂度O(m*10^m) (m=target.length()，n=deadends.length)
     *
     * @param deadends
     * @param target
     * @return
     */
    public int openLock2(String[] deadends, String target) {
        if ("0000".equals(target)) {
            return 0;
        }

        //从前往后遍历的队列，即从"0000"开始遍历
        Queue<String> queue1 = new LinkedList<>();
        //从后往前遍历的队列，即从target开始遍历
        Queue<String> queue2 = new LinkedList<>();
        //从前往后遍历的访问集合，存储queue1已经访问到的数字
        Set<String> visitedSet1 = new HashSet<>();
        //从后往前遍历的访问集合，存储queue2已经访问到的数字
        Set<String> visitedSet2 = new HashSet<>();
        //死亡数字集合，存储deadends中死亡数字，O(1)判断当前数字是否是死亡数字
        Set<String> deadendsSet = new HashSet<>(Arrays.asList(deadends));
        queue1.offer("0000");
        queue2.offer(target);
        //注意：双向bfs，必须先将首尾节点在对应的set中设置为已访问，不能每次出队元素的时候再标记节点已访问
        visitedSet1.add("0000");
        visitedSet2.add(target);

        //"0000"或target为死亡数字，则无法旋转到target，返回-1
        if (deadendsSet.contains("0000") || deadendsSet.contains(target)) {
            return -1;
        }

        //双向bfs向外扩展的次数，两个队列相交，即"0000"旋转为target的最少次数
        int count = 0;

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
                //当前需要旋转的数字
                String num = queue1.poll();

                //num已经存在visitedSet2中，即双向bfs相交，则找到了"0000"旋转为target的最少次数，直接返回count
                if (visitedSet2.contains(num)) {
                    return count;
                }

                //num转换为char数组，便于某一位的旋转
                char[] arr = num.toCharArray();

                //旋转num中的第j位数字
                for (int j = 0; j < arr.length; j++) {
                    //num中第j位数字
                    char cur = arr[j];
                    //cur向前旋转后的数字
                    char pre;
                    //cur向后旋转后的数字
                    char next;

                    if (cur == '0') {
                        pre = '9';
                        next = '1';
                    } else if (cur == '9') {
                        pre = '8';
                        next = '0';
                    } else {
                        pre = (char) (cur - 1);
                        next = (char) (cur + 1);
                    }

                    arr[j] = pre;
                    //num第j位数字向前旋转后的数字
                    String preNum = new String(arr);
                    arr[j] = next;
                    //num第j位数字向后旋转后的数字
                    String nextNum = new String(arr);

                    //num第j位数字向前旋转后的数字preNum没有访问，并且preNum不是死亡数字，则加入队列queue1，并设置preNum已访问
                    if (!visitedSet1.contains(preNum) && !deadendsSet.contains(preNum)) {
                        queue1.offer(preNum);
                        visitedSet1.add(preNum);
                    }

                    //num第j位数字向后旋转后的数字nextNum没有访问，并且nextNum不是死亡数字，则加入队列queue1，并设置preNum已访问
                    if (!visitedSet1.contains(nextNum) && !deadendsSet.contains(nextNum)) {
                        queue1.offer(nextNum);
                        visitedSet1.add(nextNum);
                    }

                    //num第j位数字复原，用于第j+1位数字旋转
                    arr[j] = cur;
                }
            }

            //count加1，表示bfs每次往外扩一层
            count++;
        }

        //bfs结束也没有到达target，即"0000"无法到达target，则无法解锁，返回-1
        return -1;
    }
}
