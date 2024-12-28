package com.zhang.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2024/9/27 09:00
 * @Author zsy
 * @Description 最流行的视频创作者 哈希表类比
 * 给你两个字符串数组 creators 和 ids ，和一个整数数组 views ，所有数组的长度都是 n 。
 * 平台上第 i 个视频者是 creator[i] ，视频分配的 id 是 ids[i] ，且播放量为 views[i] 。
 * 视频创作者的 流行度 是该创作者的 所有 视频的播放量的 总和 。请找出流行度 最高 创作者以及该创作者播放量 最大 的视频的 id 。
 * 如果存在多个创作者流行度都最高，则需要找出所有符合条件的创作者。
 * 如果某个创作者存在多个播放量最高的视频，则只需要找出字典序最小的 id 。
 * 返回一个二维字符串数组 answer ，其中 answer[i] = [creatori, idi] 表示 creatori 的流行度 最高 且其最流行的视频 id 是 idi ，
 * 可以按任何顺序返回该结果。
 * <p>
 * 输入：creators = ["alice","bob","alice","chris"], ids = ["one","two","three","four"], views = [5,10,5,4]
 * 输出：[["alice","one"],["bob","two"]]
 * 解释：
 * alice 的流行度是 5 + 5 = 10 。
 * bob 的流行度是 10 。
 * chris 的流行度是 4 。
 * alice 和 bob 是流行度最高的创作者。
 * bob 播放量最高的视频 id 为 "two" 。
 * alice 播放量最高的视频 id 是 "one" 和 "three" 。由于 "one" 的字典序比 "three" 更小，所以结果中返回的 id 是 "one" 。
 * <p>
 * 输入：creators = ["alice","alice","alice"], ids = ["a","b","c"], views = [1,2,2]
 * 输出：[["alice","b"]]
 * 解释：
 * id 为 "b" 和 "c" 的视频都满足播放量最高的条件。
 * 由于 "b" 的字典序比 "c" 更小，所以结果中返回的 id 是 "b" 。
 * <p>
 * n == creators.length == ids.length == views.length
 * 1 <= n <= 10^5
 * 1 <= creators[i].length, ids[i].length <= 5
 * creators[i] 和 ids[i] 仅由小写英文字母组成
 * 0 <= views[i] <= 10^5
 */
public class Problem2456 {
    public static void main(String[] args) {
        Problem2456 problem2456 = new Problem2456();
        String[] creators = {"alice", "bob", "alice", "chris"};
        String[] ids = {"one", "two", "three", "four"};
        int[] views = {5, 10, 5, 4};
        System.out.println(problem2456.mostPopularCreator(creators, ids, views));
    }

    /**
     * 哈希表+模拟
     * 时间复杂度O(n)，空间复杂度O(n)
     *
     * @param creators
     * @param ids
     * @param views
     * @return
     */
    public List<List<String>> mostPopularCreator(String[] creators, String[] ids, int[] views) {
        //key：视频创作者，value：视频创作者节点
        Map<String, Creator> map = new HashMap<>();
        //创作者所有视频播放量之和的最大值
        //使用long，避免int相加溢出
        long maxSumView = 0;

        for (int i = 0; i < creators.length; i++) {
            //map中不存在creators[i]，则当前创作者加入map中
            if (!map.containsKey(creators[i])) {
                Creator creator = new Creator(creators[i], ids[i], views[i]);
                map.put(creators[i], creator);
                maxSumView = Math.max(maxSumView, views[i]);
            } else {
                //当前创作者
                Creator creator = map.get(creators[i]);

                //当前视频播放量大于creator的最大播放量，或者两者播放量相等的情况下，当前视频id字典序小于creator.videoId，
                //则更新creator的视频id和最大播放量
                if (views[i] > creator.maxView || (views[i] == creator.maxView && ids[i].compareTo(creator.videoId) < 0)) {
                    creator.videoId = ids[i];
                    creator.maxView = views[i];
                }

                creator.sumView = creator.sumView + views[i];
                maxSumView = Math.max(maxSumView, creator.sumView);
            }
        }

        List<List<String>> result = new ArrayList<>();

        for (Creator creator : map.values()) {
            if (creator.sumView == maxSumView) {
                List<String> list = new ArrayList<>();
                list.add(creator.name);
                list.add(creator.videoId);
                result.add(list);
            }
        }

        return result;
    }

    /**
     * 视频创作者节点
     */
    private static class Creator {
        //当前作者名
        private String name;
        //当前作者最大播放量的视频id，如果存在多个相同的最大播放量视频，则为字典序小的id
        private String videoId;
        //当前作者最大播放量
        private int maxView;
        //当前作者视频播放量之和
        //使用long，避免int相加溢出
        private long sumView;

        public Creator(String name, String videoId, int maxView) {
            this.name = name;
            this.videoId = videoId;
            this.maxView = maxView;
            this.sumView = maxView;
        }
    }
}
