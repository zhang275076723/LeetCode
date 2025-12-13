package com.zhang.java;

import java.util.HashSet;
import java.util.Set;

/**
 * @Date 2024/1/1 08:42
 * @Author zsy
 * @Description 环形数组是否存在循环 虾皮机试题 类比Problem141、Problem142、Problem160、Problem202、Problem565、Offer52 跳跃问题类比
 * 存在一个不含 0 的 环形 数组 nums ，每个 nums[i] 都表示位于下标 i 的角色应该向前或向后移动的下标个数：
 * 如果 nums[i] 是正数，向前（下标递增方向）移动 |nums[i]| 步
 * 如果 nums[i] 是负数，向后（下标递减方向）移动 |nums[i]| 步
 * 因为数组是 环形 的，所以可以假设从最后一个元素向前移动一步会到达第一个元素，而第一个元素向后移动一步会到达最后一个元素。
 * 数组中的 循环 由长度为 k 的下标序列 seq 标识：
 * 遵循上述移动规则将导致一组重复下标序列 seq[0] -> seq[1] -> ... -> seq[k - 1] -> seq[0] -> ...
 * 所有 nums[seq[j]] 应当不是 全正 就是 全负
 * k > 1
 * 如果 nums 中存在循环，返回 true ；否则，返回 false 。
 * <p>
 * 输入：nums = [2,-1,1,2,2]
 * 输出：true
 * 解释：存在循环，按下标 0 -> 2 -> 3 -> 0 。循环长度为 3 。
 * <p>
 * 输入：nums = [-1,2]
 * 输出：false
 * 解释：按下标 1 -> 1 -> 1 ... 的运动无法构成循环，因为循环的长度为 1 。根据定义，循环的长度必须大于 1 。
 * <p>
 * 输入：nums = [-2,1,-1,-2,-2]
 * 输出：false
 * 解释：按下标 1 -> 2 -> 1 -> ... 的运动无法构成循环，因为 nums[1] 是正数，而 nums[2] 是负数。
 * 所有 nums[seq[j]] 应当不是全正就是全负。
 * <p>
 * 1 <= nums.length <= 5000
 * -1000 <= nums[i] <= 1000
 * nums[i] != 0
 */
public class Problem457 {
    public static void main(String[] args) {
        Problem457 problem457 = new Problem457();
//        int[] nums = {2, -1, 1, 2, 2};
        int[] nums = {-1, -2, -3, -4, -5};
        System.out.println(problem457.circularArrayLoop(nums));
        System.out.println(problem457.circularArrayLoop2(nums));
    }

    /**
     * 哈希表
     * 合法环的2个条件：
     * 1、环中值nums[i]不是全正就是全负，环只能沿着同一个方向，不能一会向前一会向后
     * 2、k>1，环的大小大于1，不存在自己到自己的环
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param nums
     * @return
     */
    public boolean circularArrayLoop(int[] nums) {
        boolean[] visited = new boolean[nums.length];

        for (int i = 0; i < nums.length; i++) {
            //当前节点i已访问，则当前节点i不是环中节点，直接进行下次循环
            if (visited[i]) {
                continue;
            }

            Set<Integer> set = new HashSet<>();

            //当前下标索引
            int index = i;
            visited[index] = true;
            set.add(index);

            while (true) {
                //index的下一个位置的下标索引
                int nextIndex = getNextIndex(nums, index);

                //存在自己到自己的环，或者环没有沿着同一个方向，则不是合法环，直接跳出循环
                //nums[index]*nums[nextIndex]<0，则说明环没有沿着同一个方向
                if (index == nextIndex || nums[index] * nums[nextIndex] < 0) {
                    break;
                }

                //set中存在nextIndex，则存在环，返回true
                if (set.contains(nextIndex)) {
                    return true;
                }

                //设置nextIndex节点已访问，避免重复遍历
                visited[nextIndex] = true;
                set.add(nextIndex);
                index = nextIndex;
            }
        }

        //遍历结束，没有找到环，返回false
        return false;
    }

    /**
     * 快慢指针+原地标记
     * 合法环的2个条件：
     * 1、环中值nums[i]不是全正就是全负，环只能沿着同一个方向，不能一会向前一会向后
     * 2、k>1，环的大小大于1，不存在自己到自己的环
     * nums[i]都不为0，原数组可以作为访问数组，nums[i]访问过，则设置nums[i]为0
     * 时间复杂度O(n)，空间复杂度O(1)
     *
     * @param nums
     * @return
     */
    public boolean circularArrayLoop2(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            //当前节点i已访问，则当前节点i不是环中节点，直接进行下次循环
            if (nums[i] == 0) {
                continue;
            }

            int slow = i;
            int fast = i;

            while (true) {
                int nextSlow = getNextIndex(nums, slow);
                int nextFast = getNextIndex(nums, getNextIndex(nums, fast));

                //存在自己到自己的环，或者环没有沿着同一个方向，则不是合法环，直接跳出循环
                //nums[slow]*nums[nextIndex(nums, slow)]<0，则说明环没有沿着同一个方向
                if (slow == nextSlow ||
                        nums[slow] * nums[nextSlow] < 0 ||
                        nums[fast] * nums[getNextIndex(nums, fast)] < 0 ||
                        nums[getNextIndex(nums, fast)] * nums[nextFast] < 0) {
                    break;
                }

                slow = nextSlow;
                fast = nextFast;

                //快慢指针相遇，并且不存在自己到自己的环，则返回true
                if (slow == fast && slow != getNextIndex(nums, slow)) {
                    return true;
                }
            }

            int index = i;

            //从节点index发出不能成环，设置访问到的节点为0，避免重复遍历
            //注意：不能在上面快慢指针循环过程中修改节点为0，会导致快慢指针无法找到下一个快慢指针
            while (true) {
                //index的下一个位置的下标索引
                //先记录index的下一个位置的下标索引，避免nums[index]置为0之后无法得到下一个位置
                int nextIndex = getNextIndex(nums, index);

                //存在自己到自己的环，或者环没有沿着同一个方向，则不是合法环，直接跳出循环
                //nums[index]*nums[nextIndex]<0，则说明环没有沿着同一个方向
                if (index == nextIndex || nums[index] * nums[nextIndex] < 0) {
                    break;
                }

                //设置nums[index]为0，表示index节点已访问，避免重复遍历
                nums[index] = 0;
                index = nextIndex;
            }
        }

        //遍历结束，没有找到环，返回false
        return false;
    }

    /**
     * 得到下标索引i的下一个位置的下标索引
     *
     * @param nums
     * @param i
     * @return
     */
    private int getNextIndex(int[] nums, int i) {
        int n = nums.length;
        //((i + nums[i]) % n)此时有可能为负数，加上n再模n，保证下标索引i的下一个位置的下标索引在[0,n)范围内
        //注意：不能写成return (i+nums[i]+n)%n;，因为i+nums[i]+n有可能仍为负数
        return (((i + nums[i]) % n) + n) % n;
    }
}
