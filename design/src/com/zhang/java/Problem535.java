package com.zhang.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Date 2024/2/16 08:20
 * @Author zsy
 * @Description TinyURL 的加密与解密 字节面试题 类比Problem271、Problem297、Problem331、Problem449、Problem625、Problem820、Problem1948、Offer37 哈希表类比Problem1、Problem128、Problem166、Problem187、Problem205、Problem242、Problem290、Problem291、Problem383、Problem387、Problem389、Problem454、Problem532、Problem554、Problem609、Problem763、Problem1500、Problem1640、Problem2657、Offer50
 * TinyURL 是一种 URL 简化服务， 比如：当你输入一个 URL https://leetcode.com/problems/design-tinyurl 时，
 * 它将返回一个简化的URL http://tinyurl.com/4e9iAk 。
 * 请你设计一个类来加密与解密 TinyURL 。
 * 加密和解密算法如何设计和运作是没有限制的，你只需要保证一个 URL 可以被加密成一个 TinyURL ，
 * 并且这个 TinyURL 可以用解密方法恢复成原本的 URL 。
 * <p>
 * 实现 Solution 类：
 * Solution() 初始化 TinyURL 系统对象。
 * String encode(String longUrl) 返回 longUrl 对应的 TinyURL 。
 * String decode(String shortUrl) 返回 shortUrl 原本的 URL 。题目数据保证给定的 shortUrl 是由同一个系统对象加密的。
 * <p>
 * 输入：url = "https://leetcode.com/problems/design-tinyurl"
 * 输出："https://leetcode.com/problems/design-tinyurl"
 * 解释：
 * Solution obj = new Solution();
 * string tiny = obj.encode(url); // 返回加密后得到的 TinyURL 。
 * string ans = obj.decode(tiny); // 返回解密后得到的原本的 URL 。
 * <p>
 * 1 <= url.length <= 10^4
 * 题目数据保证 url 是一个有效的 URL
 */
public class Problem535 {
    public static void main(String[] args) {
        Codec codec = new Codec();
        String url = "https://leetcode.com/problems/design-tinyurl";
        // 返回加密后得到的 TinyURL 。
        String tiny = codec.encode(url);
        // 返回解密后得到的原本的 URL 。
        String ans = codec.decode(tiny);
        System.out.println(tiny);
        System.out.println(ans);
    }

    /**
     * 哈希表
     */
    public static class Codec {
        //自定义tinyURL前缀
        private final String tinyUrlPrefix = "http://tinyurl.com/";
        //长度为62的随机值，6位随机码从randomCode中获取
        private final String randomCode = "0123456789abcdefjhijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXZY";
        //tinyUrl到url的映射map
        private final Map<String, String> tinyUrl2UrlMap;
        //url到tinyUrl的映射map
        //注意：必须使用2个map保证幂等性，即保证url和tinyUrl存在双向一一对应关系
        private final Map<String, String> url2TinyUrlMap;
        private final Random random;

        public Codec() {
            tinyUrl2UrlMap = new HashMap<>();
            url2TinyUrlMap = new HashMap<>();
            random = new Random();
        }

        public String encode(String longUrl) {
            if (url2TinyUrlMap.containsKey(longUrl)) {
                return url2TinyUrlMap.get(longUrl);
            }

            //tinyURL格式：tinyUrlPrefix+6位随机码(例如：4e9iAk)
            while (true) {
                //tinyURL
                StringBuilder sb = new StringBuilder(tinyUrlPrefix);

                for (int i = 0; i < 6; i++) {
                    //随机码长度为62
                    sb.append(randomCode.charAt(random.nextInt(62)));
                }

                if (!tinyUrl2UrlMap.containsKey(sb.toString())) {
                    tinyUrl2UrlMap.put(sb.toString(), longUrl);
                    url2TinyUrlMap.put(longUrl, sb.toString());
                    return sb.toString();
                }
            }
        }

        public String decode(String shortUrl) {
            return tinyUrl2UrlMap.get(shortUrl);
        }
    }
}
