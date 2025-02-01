package com.zhang.java;

import java.util.*;

/**
 * @Date 2024/9/18 18:02
 * @Author zsy
 * @Description 设计视频共享平台 类比Problem379、Problem1500、Problem1845、Problem2336
 * 你有一个视频分享平台，用户可以上传和删除视频。
 * 每个 video 都是 字符串 类型的数字，其中字符串的第 i 位表示视频中第 i 分钟的内容。
 * 例如，第一个数字表示视频中第 0 分钟的内容，第二个数字表示视频中第 1 分钟的内容，以此类推。
 * 视频的观众也可以喜欢和不喜欢视频。
 * 该平台会跟踪每个视频的 观看次数、点赞次数 和 不喜欢次数。
 * 当视频上传时，它与最小可用整数 videoId 相关联，videoId 从 0 开始的。
 * 一旦一个视频被删除，与该视频关联的 videoId 就可以用于另一个视频。
 * 实现 VideoSharingPlatform 类:
 * VideoSharingPlatform() 初始化对象。
 * int upload(String video) 用户上传一个 video. 返回与视频相关联的videoId 。
 * void remove(int videoId) 如果存在与 videoId 相关联的视频，则删除该视频。
 * String watch(int videoId, int startMinute, int endMinute) 如果有一个视频与 videoId 相关联，
 * 则将该视频的观看次数增加 1，并返回视频字符串的子字符串，从 startMinute 开始，
 * 以 min(endMinute, video.length - 1)(含边界) 结束。否则，返回 "-1"。
 * void like(int videoId) 如果存在与 videoId 相关联的视频，则将与 videoId 相关联的视频的点赞数增加 1。
 * void dislike(int videoId) 如果存在与 videoId 相关联的视频，则将与 videoId 相关联的视频上的不喜欢次数增加 1。
 * int[] getLikesAndDislikes(int videoId) 返回一个长度为 2 ，下标从 0 开始 的整型数组，
 * 其中 values[0] 是与 videoId 相关联的视频上的点赞数，values[1] 是不喜欢数。
 * 如果没有与 videoId 相关联的视频，则返回 [-1]。
 * int getViews(int videoId) 返回与 videoId 相关联的视频的观看次数，如果没有与 videoId 相关联的视频，返回 -1。
 * <p>
 * 输入
 * ["VideoSharingPlatform", "upload", "upload", "remove", "remove", "upload", "watch", "watch", "like", "dislike", "dislike", "getLikesAndDislikes", "getViews"]
 * [[], ["123"], ["456"], [4], [0], ["789"], [1, 0, 5], [1, 0, 1], [1], [1], [1], [1], [1]]
 * 输出
 * [null, 0, 1, null, null, 0, "456", "45", null, null, null, [1, 2], 2]
 * 解释
 * VideoSharingPlatform videoSharingPlatform = new VideoSharingPlatform();
 * videoSharingPlatform.upload("123");          // 最小的可用 videoId 是 0，所以返回 0。
 * videoSharingPlatform.upload("456");          // 最小的可用 videoId 是 1，所以返回 1。
 * videoSharingPlatform.remove(4);              // 没有与 videoId 4 相关联的视频，所以什么都不做。
 * videoSharingPlatform.remove(0);              // 删除与 videoId 0 关联的视频。
 * videoSharingPlatform.upload("789");          // 由于与 videoId 0 相关联的视频被删除，
 * <                                            // 0 是最小的可用 videoId，所以返回 0。
 * videoSharingPlatform.watch(1, 0, 5);         // 与 videoId 1 关联的视频为 "456"。
 * <                                            // 从分钟 0 到分钟 min(5,3 - 1)= 2 的视频为 "456"，因此返回 "456"。
 * videoSharingPlatform.watch(1, 0, 1);         // 与 videoId 1 关联的视频为 "456"。
 * <                                            // 从分钟 0 到分钟 min(1,3 - 1)= 1 的视频为 "45"，因此返回 "45"。
 * videoSharingPlatform.like(1);                // 增加与 videoId 1 相关的视频的点赞数。
 * videoSharingPlatform.dislike(1);             // 增加与 videoId 1 相关联的视频的不喜欢的数量。
 * videoSharingPlatform.dislike(1);             // 增加与 videoId 1 相关联的视频的不喜欢的数量。
 * videoSharingPlatform.getLikesAndDislikes(1); // 在与 videoId 1 相关的视频中有 1 个喜欢和 2 个不喜欢，因此返回[1,2]。
 * videoSharingPlatform.getViews(1);            // 与 videoId 1 相关联的视频有 2 个观看数，因此返回2。
 * <p>
 * 输入
 * ["VideoSharingPlatform", "remove", "watch", "like", "dislike", "getLikesAndDislikes", "getViews"]
 * [[], [0], [0, 0, 1], [0], [0], [0], [0]]
 * 输出
 * [null, null, "-1", null, null, [-1], -1]
 * 解释
 * VideoSharingPlatform videoSharingPlatform = new VideoSharingPlatform();
 * videoSharingPlatform.remove(0);              // 没有与 videoId 0 相关联的视频，所以什么都不做。
 * videoSharingPlatform.watch(0, 0, 1);         // 没有与 videoId 0 相关联的视频，因此返回 "-1"。
 * videoSharingPlatform.like(0);                // 没有与 videoId 0 相关联的视频，所以什么都不做。
 * videoSharingPlatform.dislike(0);             // 没有与 videoId 0 相关联的视频，所以什么都不做。
 * videoSharingPlatform.getLikesAndDislikes(0); // 没有与 videoId 0 相关联的视频，因此返回 [-1]。
 * videoSharingPlatform.getViews(0);            // 没有视频与 videoId 0 相关联，因此返回 -1。
 * <p>
 * 1 <= video.length <= 10^5
 * 调用 upload 时所有 video.length 的总和不会超过 10^5
 * video 由数字组成
 * 0 <= videoId <= 10^5
 * 0 <= startMinute < endMinute < 10^5
 * startMinute < video.length
 * 调用  watch 时所有 endMinute - startMinute 的总和不会超过 10^5。
 * 所有函数 总共 最多调用 10^5 次。
 */
public class Problem2254 {
    public static void main(String[] args) {
        VideoSharingPlatform videoSharingPlatform = new VideoSharingPlatform();
        // 最小的可用 videoId 是 0，所以返回 0。
        System.out.println(videoSharingPlatform.upload("123"));
        // 最小的可用 videoId 是 1，所以返回 1。
        System.out.println(videoSharingPlatform.upload("456"));
        // 没有与 videoId 4 相关联的视频，所以什么都不做。
        videoSharingPlatform.remove(4);
        // 删除与 videoId 0 关联的视频。
        videoSharingPlatform.remove(0);
        // 由于与 videoId 0 相关联的视频被删除，
        // 0 是最小的可用 videoId，所以返回 0。
        System.out.println(videoSharingPlatform.upload("789"));
        // 与 videoId 1 关联的视频为 "456"。
        // 从分钟 0 到分钟 min(5,3 - 1)= 2 的视频为 "456"，因此返回 "456"。
        System.out.println(videoSharingPlatform.watch(1, 0, 5));
        // 与 videoId 1 关联的视频为 "456"。
        // 从分钟 0 到分钟 min(1,3 - 1)= 1 的视频为 "45"，因此返回 "45"。
        System.out.println(videoSharingPlatform.watch(1, 0, 1));
        // 增加与 videoId 1 相关的视频的点赞数。
        videoSharingPlatform.like(1);
        // 增加与 videoId 1 相关联的视频的不喜欢的数量。
        videoSharingPlatform.dislike(1);
        // 增加与 videoId 1 相关联的视频的不喜欢的数量。
        videoSharingPlatform.dislike(1);
        // 在与 videoId 1 相关的视频中有 1 个喜欢和 2 个不喜欢，因此返回[1,2]。
        System.out.println(Arrays.toString(videoSharingPlatform.getLikesAndDislikes(1)));
        // 与 videoId 1 相关联的视频有 2 个观看数，因此返回2。
        System.out.println(videoSharingPlatform.getViews(1));
    }

    /**
     * 哈希表+优先队列
     */
    static class VideoSharingPlatform {
        //下一个视频id
        private int nextVideoId;
        //优先队列，小根堆，存放可以重复使用的视频id
        //小根堆不为空，则优先使用小根堆堆顶的视频id
        private final PriorityQueue<Integer> priorityQueue;
        //key：视频id，value：视频节点
        private final Map<Integer, Video> videoMap;

        public VideoSharingPlatform() {
            nextVideoId = 0;
            priorityQueue = new PriorityQueue<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer a, Integer b) {
                    return a - b;
                }
            });
            videoMap = new HashMap<>();
        }

        public int upload(String video) {
            int videoId;

            //小根堆为空，则优先使用nextVideoId的视频id
            if (priorityQueue.isEmpty()) {
                videoId = nextVideoId;
                nextVideoId++;
            } else {
                //小根堆不为空，则优先使用小根堆堆顶的视频id
                videoId = priorityQueue.poll();
            }

            videoMap.put(videoId, new Video(videoId, video));

            return videoId;
        }

        public void remove(int videoId) {
            //不存在videoId，直接返回
            if (!videoMap.containsKey(videoId)) {
                return;
            }

            priorityQueue.offer(videoId);
            videoMap.remove(videoId);
        }

        public String watch(int videoId, int startMinute, int endMinute) {
            //不存在videoId，返回"-1"
            if (!videoMap.containsKey(videoId)) {
                return "-1";
            }

            Video video = videoMap.get(videoId);
            //当前视频观看次数加1
            video.views++;

            return video.content.substring(startMinute, Math.min(endMinute + 1, video.content.length()));
        }

        public void like(int videoId) {
            //不存在videoId，直接返回
            if (!videoMap.containsKey(videoId)) {
                return;
            }

            videoMap.get(videoId).likes++;
        }

        public void dislike(int videoId) {
            //不存在videoId，直接返回
            if (!videoMap.containsKey(videoId)) {
                return;
            }

            videoMap.get(videoId).dislikes++;
        }

        public int[] getLikesAndDislikes(int videoId) {
            //不存在videoId，返回[-1]
            if (!videoMap.containsKey(videoId)) {
                return new int[]{-1};
            }

            Video video = videoMap.get(videoId);

            return new int[]{video.likes, video.dislikes};
        }

        public int getViews(int videoId) {
            //不存在videoId，返回-1
            if (!videoMap.containsKey(videoId)) {
                return -1;
            }

            return videoMap.get(videoId).views;
        }

        /**
         * 视频节点
         */
        private static class Video {
            //视频id
            private final int videoId;
            //视频内容
            private final String content;
            //点赞数
            private int likes;
            //不喜欢数
            private int dislikes;
            //观看数
            private int views;

            public Video(int videoId, String content) {
                this.videoId = videoId;
                this.content = content;
                likes = 0;
                dislikes = 0;
                views = 0;
            }
        }
    }
}
