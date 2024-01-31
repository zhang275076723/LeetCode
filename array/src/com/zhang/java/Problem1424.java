package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/1/23 08:22
 * @Author zsy
 * @Description 对角线遍历 II 对角线类比Problem51、Problem52、Problem498、Problem1001、Problem1329、Problem1572、Problem2614、Problem2711、Problem3000 优先队列类比
 * 给你一个列表 nums ，里面每一个元素都是一个整数列表。
 * 请你依照下面各图的规则，按顺序返回 nums 中对角线上的整数。
 * <p>
 * 输入：nums = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,4,2,7,5,3,8,6,9]
 * <p>
 * 输入：nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
 * 输出：[1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
 * <p>
 * 输入：nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
 * 输出：[1,4,2,5,3,8,6,9,7,10,11]
 * <p>
 * 输入：nums = [[1,2,3,4,5,6]]
 * 输出：[1,2,3,4,5,6]
 * <p>
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i].length <= 10^5
 * 1 <= nums[i][j] <= 10^9
 * nums 中最多有 10^5 个数字。
 */
public class Problem1424 {
    public static void main(String[] args) {
        Problem1424 problem1424 = new Problem1424();
        List<List<Integer>> nums = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
            }});
            add(new ArrayList<Integer>() {{
                add(6);
                add(7);
            }});
            add(new ArrayList<Integer>() {{
                add(8);
            }});
            add(new ArrayList<Integer>() {{
                add(9);
                add(10);
                add(11);
            }});
            add(new ArrayList<Integer>() {{
                add(12);
                add(13);
                add(14);
                add(15);
                add(16);
            }});
        }};
        System.out.println(Arrays.toString(problem1424.findDiagonalOrder(nums)));
        System.out.println(Arrays.toString(problem1424.findDiagonalOrder2(nums)));
        System.out.println(Arrays.toString(problem1424.findDiagonalOrder3(nums)));
    }

    /**
     * 模拟 (超时)
     * 左下到右上对角线上的元素下标索引i+j相等
     * 时间复杂度O(mn)，空间复杂度O(1) (m=nums.size()，n=max(nums.get(i).size()))
     *
     * @param nums
     * @return
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        //相当于二维数组的m行n列
        int m = nums.size();
        int n = 0;
        //nums中元素的个数
        int total = 0;

        for (int i = 0; i < m; i++) {
            n = Math.max(n, nums.get(i).size());
            total = total + nums.get(i).size();
        }

        int[] result = new int[total];
        //result数组下标索引
        int index = 0;

        for (int i = 0; i < m + n - 1; i++) {
            //起始行为i和最大行m-1中的最小值
            int x = Math.min(i, m - 1);
            int y = i - x;

            while (x >= 0 && y < n) {
                //(x,y)合法，则加入result
                if (y < nums.get(x).size()) {
                    result[index] = nums.get(x).get(y);
                    index++;
                }

                x--;
                y++;
            }
        }

        return result;
    }

    /**
     * 模拟优化
     * 左下到右上对角线上的元素下标索引i+j相等
     * 顺序遍历nums中元素，下标索引i+j相等的元素中先遍历到的元素出现在后遍历到的元素之后，
     * 所以下标索引i+j相等的元素存放到同一个list中，当前元素在list中首添加
     * 时间复杂度O(m+n+total)，空间复杂度O(total) (m=nums.size()，n=max(nums.get(i).size())，total=nums中元素的个数)
     *
     * @param nums
     * @return
     */
    public int[] findDiagonalOrder2(List<List<Integer>> nums) {
        //相当于二维数组的m行n列
        int m = nums.size();
        int n = 0;
        //nums中元素的个数
        int total = 0;

        for (int i = 0; i < m; i++) {
            n = Math.max(n, nums.get(i).size());
            total = total + nums.get(i).size();
        }

        //lists.get(k)：第k个对角线上的元素的集合，下标索引i+j=k
        List<List<Integer>> lists = new ArrayList<>();

        //对角线初始化，共m+n-1条对角线
        for (int i = 0; i < m + n - 1; i++) {
            //下标索引i+j相等的点中先遍历到的元素出现在后遍历到的元素之后，即需要使用LinkedList首添加当前元素
            lists.add(new LinkedList<>());
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < nums.get(i).size(); j++) {
                LinkedList<Integer> list = (LinkedList<Integer>) lists.get(i + j);
                //下标索引i+j相等的点中先遍历到的元素出现在后遍历到的元素之后，即当前元素在list中首添加
                list.addFirst(nums.get(i).get(j));
            }
        }

        int[] result = new int[total];
        //result数组下标索引
        int index = 0;

        //按照下标索引i+j的顺序，将lists中元素加入result
        for (int i = 0; i < m + n - 1; i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                result[index] = lists.get(i).get(j);
                index++;
            }
        }

        return result;
    }

    /**
     * 优先队列，小根堆
     * 时间复杂度O(total*logm)，空间复杂度O(m) (m=nums.size()，n=max(nums.get(i).size())，total=nums中元素的个数)
     *
     * @param nums
     * @return
     */
    public int[] findDiagonalOrder3(List<List<Integer>> nums) {
        //相当于二维数组的m行n列
        int m = nums.size();
        int n = 0;
        //nums中元素的个数
        int total = 0;

        for (int i = 0; i < m; i++) {
            n = Math.max(n, nums.get(i).size());
            total = total + nums.get(i).size();
        }

        int[] result = new int[total];
        //result数组下标索引
        int index = 0;

        //小根堆，arr[0]：当前元素，arr[1]：当前元素在nums中所在行的下标索引，arr[2]：当前元素在nums中所在列的下标索引
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] arr1, int[] arr2) {
                //下标索引i+j不相等，则按照下标索引i+j由小到大排序
                if (arr1[1] + arr1[2] != arr2[1] + arr2[2]) {
                    return (arr1[1] + arr1[2]) - (arr2[1] + arr2[2]);
                } else {
                    //下标索引i+j相等，则按照横坐标i由大到小排序，纵坐标j由小到大排序
                    if (arr1[1] != arr2[1]) {
                        return arr2[1] - arr1[1];
                    } else {
                        return arr1[2] - arr2[2];
                    }
                }
            }
        });

        //每行第一个元素加入小根堆
        for (int i = 0; i < m; i++) {
            priorityQueue.offer(new int[]{nums.get(i).get(0), i, 0});
        }

        while (!priorityQueue.isEmpty()) {
            int[] arr = priorityQueue.poll();

            result[index] = arr[0];
            index++;

            //当前元素所在行存在下一个元素时，该行下一个元素加入小根堆
            if (arr[2] + 1 < nums.get(arr[1]).size()) {
                priorityQueue.offer(new int[]{nums.get(arr[1]).get(arr[2] + 1), arr[1], arr[2] + 1});
            }
        }

        return result;
    }
}
