package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/10/8 08:02
 * @Author zsy
 * @Description 快照数组 类比Problem981 有序集合类比Problem220、Problem352、Problem363、Problem855、Problem981、Problem1348、Problem1912、Problem2034、Problem2071、Problem2349、Problem2353、Problem2502、Problem2590
 * 实现支持下列接口的「快照数组」- SnapshotArray：
 * SnapshotArray(int length) - 初始化一个与指定长度相等的 类数组 的数据结构。初始时，每个元素都等于 0。
 * void set(index, val) - 会将指定索引 index 处的元素设置为 val。
 * int snap() - 获取该数组的快照，并返回快照的编号 snap_id（快照号是调用 snap() 的总次数减去 1）。
 * int get(index, snap_id) - 根据指定的 snap_id 选择快照，并返回该快照指定索引 index 的值。
 * <p>
 * 输入：
 * ["SnapshotArray","set","snap","set","get"]
 * [[3],[0,5],[],[0,6],[0,0]]
 * 输出：
 * [null,null,0,null,5]
 * 解释：
 * SnapshotArray snapshotArr = new SnapshotArray(3); // 初始化一个长度为 3 的快照数组
 * snapshotArr.set(0,5);  // 令 array[0] = 5
 * snapshotArr.snap();  // 获取快照，返回 snap_id = 0
 * snapshotArr.set(0,6);
 * snapshotArr.get(0,0);  // 获取 snap_id = 0 的快照中 array[0] 的值，返回 5
 * <p>
 * 1 <= length <= 50000
 * 题目最多进行50000 次set，snap，和 get的调用 。
 * 0 <= index < length
 * 0 <= snap_id < 我们调用 snap() 的总次数
 * 0 <= val <= 10^9
 */
public class Problem1146 {
    public static void main(String[] args) {
//        // 初始化一个长度为 3 的快照数组
//        SnapshotArray snapshotArr = new SnapshotArray(3);
////        SnapshotArray2 snapshotArr = new SnapshotArray2(3);
//        // 令 array[0] = 5
//        snapshotArr.set(0, 5);
//        // 获取快照，返回 snap_id = 0
//        System.out.println(snapshotArr.snap());
//        snapshotArr.set(0, 6);
//        // 获取 snap_id = 0 的快照中 array[0] 的值，返回 5
//        System.out.println(snapshotArr.get(0, 0));

        SnapshotArray2 snapshotArr = new SnapshotArray2(3);
        snapshotArr.set(1, 18);
        snapshotArr.set(1, 4);
        System.out.println(snapshotArr.snap());
        System.out.println(snapshotArr.get(0, 0));
        snapshotArr.set(0, 20);
        System.out.println(snapshotArr.snap());
        snapshotArr.set(0, 2);
        snapshotArr.set(1, 1);
        System.out.println(snapshotArr.get(1, 1));
        System.out.println(snapshotArr.get(1, 0));
    }

    /**
     * 二分查找 (MVCC)
     * 记录每一个位置快照编号和修改元素的值，即每个位置都保存了每个历史快照编号进行的操作，
     * 通过二分查找查找小于等于当前快照编号的最大快照编号，即为当前快照编号在当前位置的值
     * 注意：如果保存每个历史快照编号中数组元素，则会空间溢出
     */
    static class SnapshotArray {
        //每一个位置快照编号和修改元素的值的集合
        //arr[0]：快照编号，arr[1]：修改元素的值
        private final List<List<int[]>> list;
        //当前快照编号
        private int snapId;

        public SnapshotArray(int length) {
            list = new ArrayList<>();
            snapId = 0;

            for (int i = 0; i < length; i++) {
                list.add(new ArrayList<>());
            }
        }

        public void set(int index, int val) {
            list.get(index).add(new int[]{snapId, val});
        }

        public int snap() {
            int result = snapId;
            snapId++;
            return result;
        }

        public int get(int index, int snap_id) {
            if (snap_id >= snapId) {
                return 0;
            }

            List<int[]> snapList = list.get(index);

            //小于等于snap_id的最大快照编号在snapList中的下标索引
            //初始化为-1，表示不存在小于等于snap_id的快照编号
            int result = -1;
            int left = 0;
            int right = snapList.size() - 1;
            int mid;

            //通过二分查找查找小于等于snap_id的最大快照编号在snapList中的下标索引
            while (left <= right) {
                mid = left + ((right - left) >> 1);

                if (snapList.get(mid)[0] <= snap_id) {
                    result = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return result == -1 ? 0 : snapList.get(result)[1];
        }
    }

    /**
     * 有序集合 (MVCC)
     * 记录每一个位置快照编号和修改元素的值，即每个位置都保存了每个历史快照编号进行的操作，
     * 通过有序集合查找小于等于当前快照编号的最大快照编号，即为当前快照编号在当前位置的值
     * 注意：如果保存每个历史快照编号中数组元素，则会空间溢出
     */
    static class SnapshotArray2 {
        //按照快照编号由小到大存储每一个位置快照编号和修改元素的值的集合
        //注意：基于树的集合，例如TreeSet和TreeMap不需要重写equals()和hashCode()
        private final List<TreeSet<int[]>> list;
        //当前快照编号
        private int snapId;

        public SnapshotArray2(int length) {
            list = new ArrayList<>();
            snapId = 0;

            for (int i = 0; i < length; i++) {
                list.add(new TreeSet<>(new Comparator<int[]>() {
                    @Override
                    public int compare(int[] arr1, int[] arr2) {
                        //按照快照编号由小到大排序
                        return arr1[0] - arr2[0];
                    }
                }));
            }
        }

        public void set(int index, int val) {
            TreeSet<int[]> treeSet = list.get(index);

            //注意：如果treeSet中包含有当前snapId对应的值，则需要先删除，在添加
            //注意：因为int[]只比较arr[0]，所以不需要关注arr[1]的值
            if (treeSet.contains(new int[]{snapId, 0})) {
                treeSet.remove(new int[]{snapId, 0});
            }

            treeSet.add(new int[]{snapId, val});
        }

        public int snap() {
            int result = snapId;
            snapId++;
            return result;
        }

        public int get(int index, int snap_id) {
            if (snap_id >= snapId) {
                return 0;
            }

            TreeSet<int[]> treeSet = list.get(index);

            //注意：因为int[]只比较arr[0]，所以不需要关注arr[1]的值
            int[] arr = new int[]{snap_id, 0};
            //treeSet中小于等于snap_id的最大快照编号的preArr
            int[] preArr = treeSet.floor(arr);

            return preArr == null ? 0 : preArr[1];
        }
    }
}
