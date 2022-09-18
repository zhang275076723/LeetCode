package com.zhang.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @Date 2022/6/5 9:38
 * @Author zsy
 * @Description 根据身高重建队列 中国人寿机试题 类比Problem56、Problem252、Problem253
 * 假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。
 * 每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki 个身高大于或等于 hi 的人。
 * 请你重新构造并返回输入数组 people 所表示的队列。
 * 返回的队列应该格式化为数组 queue ，其中 queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
 * <p>
 * 输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
 * 输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
 * 解释：
 * 编号为 0 的人身高为 5 ，没有身高更高或者相同的人排在他前面。
 * 编号为 1 的人身高为 7 ，没有身高更高或者相同的人排在他前面。
 * 编号为 2 的人身高为 5 ，有 2 个身高更高或者相同的人排在他前面，即编号为 0 和 1 的人。
 * 编号为 3 的人身高为 6 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
 * 编号为 4 的人身高为 4 ，有 4 个身高更高或者相同的人排在他前面，即编号为 0、1、2、3 的人。
 * 编号为 5 的人身高为 7 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
 * 因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新构造后的队列。
 * <p>
 * 输入：people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]
 * 输出：[[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
 * <p>
 * 1 <= people.length <= 2000
 * 0 <= hi <= 10^6
 * 0 <= ki < people.length
 * 题目数据确保队列可以被重建
 */
public class Problem406 {
    public static void main(String[] args) {
        Problem406 problem406 = new Problem406();
        int[][] people = {{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}};
        System.out.println(Arrays.deepToString(problem406.reconstructQueue(people)));
        System.out.println(Arrays.deepToString(problem406.reconstructQueue2(people)));
    }

    /**
     * 涉及二维数组排序，一般都是第一个元素正序排序，第二个元素倒序排序；或者第一个元素倒序排序，第二个元素正序排序
     * 按照第一个元素倒序排序，第二个元素正序排序，
     * 如果当前元素people[i][1]大于等于当前数组长度，则放在数组尾部；
     * 如果当前元素people[i][1]小于当前数组长度，则放在数组索引people[i][1]的位置
     * 时间复杂度O(n^2)，空间复杂度O(logn)
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        if (people == null || people.length <= 1) {
            return people;
        }

        //people第一维降序排序，第二维升序排序
        quickSort(people, 0, people.length - 1);

        int[][] result = new int[people.length][2];

        for (int i = 0; i < people.length; i++) {
            //放在数组尾部
            if (people[i][1] >= i) {
                result[i] = people[i];
            } else {
                //people[i]要放在结果数组的索引下标
                int index = people[i][1];

                //当前索引位置到末尾的元素后移一位
                for (int j = i; j > index; j--) {
                    result[j] = result[j - 1];
                }

                result[index] = people[i];
            }
        }

        return result;
    }

    /**
     * 涉及二维数组排序，一般都是第一个元素正序排序，第二个元素倒序排序；或者第一个元素倒序排序，第二个元素正序排序
     * 按照第一个元素正序排序，第二个元素倒序排序，
     * 当前元素people[i][1]确定当前元素在原数组中的索引位置
     * 例如：[4,4] [5,2] [5,0] [6,1] [7,1] [7,0]
     * [0,1,2,3,(4),5]    [4,4]    在原数组中索引为4
     * [0,1,(2),3,5]      [5,2]    在原数组中索引为2
     * [(0),1,3,5]        [5,0]    在原数组中索引为0
     * [1,(3),5]          [6,1]    在原数组中索引为3
     * [1,(5)]            [7,1]    在原数组中索引为5
     * [(1)]              [7,0]    在原数组中索引为1
     * 时间复杂度O(n^2)，空间复杂度O(logn)
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue2(int[][] people) {
        if (people == null || people.length <= 1) {
            return people;
        }

        //people第一维升序排序，第二维降序排序
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] people1, int[] people2) {
                if (people1[0] != people2[0]) {
                    return people1[0] - people2[0];
                } else {
                    return people2[1] - people1[1];
                }
            }
        });

        //存放数组索引下标
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < people.length; i++) {
            list.add(i);
        }

        int[][] result = new int[people.length][2];

        for (int i = 0; i < people.length; i++) {
            int index = list.get(people[i][1]);
            list.remove(people[i][1]);
            result[index] = people[i];
        }

        return result;
    }

    /**
     * 二维数组快排，第一维降序排序(由大到小)，第二维升序排序(由小到大)
     *
     * @param people
     * @param left
     * @param right
     */
    private void quickSort(int[][] people, int left, int right) {
        if (left < right) {
            int pivot = partition(people, left, right);
            quickSort(people, left, pivot - 1);
            quickSort(people, pivot + 1, right);
        }
    }

    private int partition(int[][] people, int left, int right) {
        int[] temp = people[left];

        while (left < right) {
            while (left < right && (people[right][0] < temp[0] ||
                    (people[right][0] == temp[0] && people[right][1] >= temp[1]))) {
                right--;
            }
            people[left] = people[right];

            while (left < right && (people[left][0] > temp[0] ||
                    (people[left][0] == temp[0] && people[left][1] <= temp[1]))) {
                left++;
            }
            people[right] = people[left];
        }

        people[left] = temp;
        return left;
    }
}
