package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/9/16 08:37
 * @Author zsy
 * @Description 设计内存分配器 有序集合类比
 * 给你一个整数 n ，表示下标从 0 开始的内存数组的大小。
 * 所有内存单元开始都是空闲的。
 * 请你设计一个具备以下功能的内存分配器：
 * 分配 一块大小为 size 的连续空闲内存单元并赋 id mID 。
 * 释放 给定 id mID 对应的所有内存单元。
 * 注意：
 * 多个块可以被分配到同一个 mID 。
 * 你必须释放 mID 对应的所有内存单元，即便这些内存单元被分配在不同的块中。
 * 实现 Allocator 类：
 * Allocator(int n) 使用一个大小为 n 的内存数组初始化 Allocator 对象。
 * int allocate(int size, int mID) 找出大小为 size 个连续空闲内存单元且位于  最左侧 的块，分配并赋 id mID 。
 * 返回块的第一个下标。如果不存在这样的块，返回 -1 。
 * int free(int mID) 释放 id mID 对应的所有内存单元。返回释放的内存单元数目。
 * <p>
 * 输入
 * ["Allocator", "allocate", "allocate", "allocate", "free", "allocate", "allocate", "allocate", "free", "allocate", "free"]
 * [[10], [1, 1], [1, 2], [1, 3], [2], [3, 4], [1, 1], [1, 1], [1], [10, 2], [7]]
 * 输出
 * [null, 0, 1, 2, 1, 3, 1, 6, 3, -1, 0]
 * 解释
 * Allocator loc = new Allocator(10); // 初始化一个大小为 10 的内存数组，所有内存单元都是空闲的。
 * loc.allocate(1, 1); // 最左侧的块的第一个下标是 0 。内存数组变为 [1, , , , , , , , , ]。返回 0 。
 * loc.allocate(1, 2); // 最左侧的块的第一个下标是 1 。内存数组变为 [1,2, , , , , , , , ]。返回 1 。
 * loc.allocate(1, 3); // 最左侧的块的第一个下标是 2 。内存数组变为 [1,2,3, , , , , , , ]。返回 2 。
 * loc.free(2); // 释放 mID 为 2 的所有内存单元。内存数组变为 [1, ,3, , , , , , , ] 。返回 1 ，因为只有 1 个 mID 为 2 的内存单元。
 * loc.allocate(3, 4); // 最左侧的块的第一个下标是 3 。内存数组变为 [1, ,3,4,4,4, , , , ]。返回 3 。
 * loc.allocate(1, 1); // 最左侧的块的第一个下标是 1 。内存数组变为 [1,1,3,4,4,4, , , , ]。返回 1 。
 * loc.allocate(1, 1); // 最左侧的块的第一个下标是 6 。内存数组变为 [1,1,3,4,4,4,1, , , ]。返回 6 。
 * loc.free(1); // 释放 mID 为 1 的所有内存单元。内存数组变为 [ , ,3,4,4,4, , , , ] 。返回 3 ，因为有 3 个 mID 为 1 的内存单元。
 * loc.allocate(10, 2); // 无法找出长度为 10 个连续空闲内存单元的空闲块，所有返回 -1 。
 * loc.free(7); // 释放 mID 为 7 的所有内存单元。内存数组保持原状，因为不存在 mID 为 7 的内存单元。返回 0 。
 * <p>
 * 1 <= n, size, mID <= 1000
 * 最多调用 allocate 和 free 方法 1000 次
 */
public class Problem2502 {
    public static void main(String[] args) {
        // 初始化一个大小为 10 的内存数组，所有内存单元都是空闲的。
//        Allocator loc = new Allocator(10);
        Allocator2 loc = new Allocator2(10);
        // 最左侧的块的第一个下标是 0 。内存数组变为 [1, , , , , , , , , ]。返回 0 。
        System.out.println(loc.allocate(1, 1));
        // 最左侧的块的第一个下标是 1 。内存数组变为 [1,2, , , , , , , , ]。返回 1 。
        System.out.println(loc.allocate(1, 2));
        // 最左侧的块的第一个下标是 2 。内存数组变为 [1,2,3, , , , , , , ]。返回 2 。
        System.out.println(loc.allocate(1, 3));
        // 释放 mID 为 2 的所有内存单元。内存数组变为 [1, ,3, , , , , , , ] 。返回 1 ，因为只有 1 个 mID 为 2 的内存单元。
        System.out.println(loc.free(2));
        // 最左侧的块的第一个下标是 3 。内存数组变为 [1, ,3,4,4,4, , , , ]。返回 3 。
        System.out.println(loc.allocate(3, 4));
        // 最左侧的块的第一个下标是 1 。内存数组变为 [1,1,3,4,4,4, , , , ]。返回 1 。
        System.out.println(loc.allocate(1, 1));
        // 最左侧的块的第一个下标是 6 。内存数组变为 [1,1,3,4,4,4,1, , , ]。返回 6 。
        System.out.println(loc.allocate(1, 1));
        // 释放 mID 为 1 的所有内存单元。内存数组变为 [ , ,3,4,4,4, , , , ] 。返回 3 ，因为有 3 个 mID 为 1 的内存单元。
        System.out.println(loc.free(1));
        // 无法找出长度为 10 个连续空闲内存单元的空闲块，所有返回 -1 。
        System.out.println(loc.allocate(10, 2));
        // 释放 mID 为 7 的所有内存单元。内存数组保持原状，因为不存在 mID 为 7 的内存单元。返回 0 。
        System.out.println(loc.free(7));
    }

    /**
     * 数组模拟
     */
    static class Allocator {
        //内存分配数组，未分配的内存为0
        private final int[] arr;

        public Allocator(int n) {
            arr = new int[n];
        }

        /**
         * 注意：本次分配大小为size的mID内存块必须连续，如果多次分配同一个mID，多次分配的mID之间可以不连续
         *
         * @param size
         * @param mID
         * @return
         */
        public int allocate(int size, int mID) {
            int index = 0;

            while (index < arr.length) {
                if (arr[index] != 0) {
                    index++;
                    continue;
                }

                int index2 = index;

                //从index开始找连续为0的数组长度，即找连续未分配的内存长度
                while (index2 < arr.length && arr[index2] == 0) {
                    index2++;
                }

                //存在大小大于等于size的内存块
                if (index2 - index >= size) {
                    for (int i = index; i < index + size; i++) {
                        arr[i] = mID;
                    }

                    //返回分配内存的起始下标索引
                    return index;
                }

                index = index2;
            }

            //遍历结束，则不存在大小为size的连续内存，返回-1
            return -1;
        }

        public int free(int mID) {
            //mID分配的内存大小
            int count = 0;

            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == mID) {
                    arr[i] = 0;
                    count++;
                }
            }

            return count;
        }
    }

    /**
     * 有序集合
     */
    static class Allocator2 {
        //有序集合，按照左边界由小到大排序存储未分配的内存区间集合
        private final TreeSet<int[]> unAllocatedSet;
        //key：已经分配内存的id，value：当前内存被分配的内存区间集合
        private final Map<Integer, List<int[]>> allocatedMap;

        public Allocator2(int n) {
            unAllocatedSet = new TreeSet<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] arr1, int[] arr2) {
                    //按照左边界由小到大排序
                    return arr1[0] - arr2[0];
                }
            });
            allocatedMap = new HashMap<>();
            //初始化，[0,n-1]为未分配的内存
            unAllocatedSet.add(new int[]{0, n - 1});
        }

        /**
         * 注意：本次分配大小为size的mID内存块必须连续，如果多次分配同一个mID，多次分配的mID之间可以不连续
         *
         * @param size
         * @param mID
         * @return
         */
        public int allocate(int size, int mID) {
            //没有空闲内存块，返回-1
            if (unAllocatedSet.isEmpty()) {
                return -1;
            }

            //有序集合中第一个空闲区间
            int[] arr = unAllocatedSet.first();

            while (arr != null) {
                if (arr[1] - arr[0] + 1 >= size) {
                    //arr从unAllocatedSet中移除
                    unAllocatedSet.remove(arr);

                    //arr剩余空闲内存重新加入unAllocatedSet
                    if (arr[1] - arr[0] + 1 > size) {
                        unAllocatedSet.add(new int[]{arr[0] + size, arr[1]});
                    }

                    if (!allocatedMap.containsKey(mID)) {
                        allocatedMap.put(mID, new ArrayList<>());
                    }

                    //大小为size的连续内存加入allocatedMap
                    //注意：大小为size的内存加入allocatedMap，而不是arr直接加入allocatedMap，因为有可能arr的大小大于size
                    allocatedMap.get(mID).add(new int[]{arr[0], arr[0] + size - 1});

                    return arr[0];
                }

                //higher(x)：返回set中大于x的最小元素，如果不存在返回null
                //lower(x)：返回set中小于x的最大元素，如果不存在返回null
                arr = unAllocatedSet.higher(arr);
            }

            //遍历结束，则不存在大小为size的连续内存，返回-1
            return -1;
        }

        public int free(int mID) {
            //不存在mID的内存，直接返回0
            if (!allocatedMap.containsKey(mID)) {
                return 0;
            }

            List<int[]> list = allocatedMap.get(mID);
            //mID从allocatedMap中移除
            allocatedMap.remove(mID);

            //mID分配的内存大小
            int count = 0;

            //mID分配的内存释放，加入unAllocatedSet中
            //注意：arr有可能会和前后区间合并
            for (int[] arr : list) {
                count = count + (arr[1] - arr[0] + 1);

                //arr[0]左边区间，即区间左边界小于arr[0]的最大区间，如果不存在该区间，则返回null
                int[] leftArr = unAllocatedSet.lower(arr);
                //arr[0]右边区间，即区间左边界大于arr[0]的最小区间，如果不存在该区间，则返回null
                int[] rightArr = unAllocatedSet.higher(arr);

                if (leftArr == null && rightArr == null) {
                    unAllocatedSet.add(arr);
                } else if (leftArr == null) {
                    if (arr[1] + 1 == rightArr[0]) {
                        rightArr[0] = arr[0];
                    } else {
                        unAllocatedSet.add(arr);
                    }
                } else if (rightArr == null) {
                    if (leftArr[1] + 1 == arr[0]) {
                        leftArr[1] = arr[1];
                    } else {
                        unAllocatedSet.add(arr);
                    }
                } else {
                    if (leftArr[1] + 1 == arr[0] && arr[1] + 1 == rightArr[0]) {
                        leftArr[1] = rightArr[1];
                        unAllocatedSet.remove(rightArr);
                    } else if (leftArr[1] + 1 == arr[0] && arr[1] + 1 != rightArr[0]) {
                        leftArr[1] = arr[1];
                    } else if (leftArr[1] + 1 != arr[0] && arr[1] + 1 == rightArr[0]) {
                        rightArr[0] = arr[0];
                    } else {
                        unAllocatedSet.add(arr);
                    }
                }
            }

            return count;
        }
    }
}
