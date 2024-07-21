package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/9/26 08:35
 * @Author zsy
 * @Description 电话目录管理系统
 * 设计一个电话目录管理系统，让它支持以下功能：
 * get: 分配给用户一个未被使用的电话号码，获取失败请返回 -1
 * check: 检查指定的电话号码是否被使用
 * release: 释放掉一个电话号码，使其能够重新被分配
 * <p>
 * // 初始化电话目录，包括 3 个电话号码：0，1 和 2。
 * PhoneDirectory directory = new PhoneDirectory(3);
 * // 可以返回任意未分配的号码，这里我们假设它返回 0。
 * directory.get();
 * // 假设，函数返回 1。
 * directory.get();
 * // 号码 2 未分配，所以返回为 true。
 * directory.check(2);
 * // 返回 2，分配后，只剩一个号码未被分配。
 * directory.get();
 * // 此时，号码 2 已经被分配，所以返回 false。
 * directory.check(2);
 * // 释放号码 2，将该号码变回未分配状态。
 * directory.release(2);
 * // 号码 2 现在是未分配状态，所以返回 true。
 * directory.check(2);
 * <p>
 * 1 <= maxNumbers <= 10^4
 * 0 <= number < maxNumbers
 * 调用方法的总数处于区间 [0 - 20000] 之内
 */
public class Problem379 {
    public static void main(String[] args) {
        // 初始化电话目录，包括 3 个电话号码：0，1 和 2。
        PhoneDirectory directory = new PhoneDirectory(3);
        // 可以返回任意未分配的号码，这里我们假设它返回 0。
        System.out.println(directory.get());
        // 假设，函数返回 1。
        System.out.println(directory.get());
        // 号码 2 未分配，所以返回为 true。
        System.out.println(directory.check(2));
        // 返回 2，分配后，只剩一个号码未被分配。
        System.out.println(directory.get());
        // 此时，号码 2 已经被分配，所以返回 false。
        System.out.println(directory.check(2));
        // 释放号码 2，将该号码变回未分配状态。
        directory.release(2);
        // 号码 2 现在是未分配状态，所以返回 true。
        System.out.println(directory.check(2));
    }

    /**
     * 哈希表
     */
    static class PhoneDirectory {
        //存储未分配的电话号码
        private final Set<Integer> set;

        public PhoneDirectory(int maxNumbers) {
            set = new HashSet<>();

            for (int i = 0; i < maxNumbers; i++) {
                set.add(i);
            }
        }

        public int get() {
            //没有未分配的电话号码，返回-1
            if (set.isEmpty()) {
                return -1;
            }

            int result = set.iterator().next();
            set.remove(result);

            return result;
        }

        public boolean check(int number) {
            return set.contains(number);
        }

        public void release(int number) {
            set.add(number);
        }
    }
}
