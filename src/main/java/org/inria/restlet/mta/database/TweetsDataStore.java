package org.inria.restlet.mta.database;

import org.inria.restlet.mta.internals.Tweet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Simulate an inmerory database to store tweets
 *
 * @author PADONOU Dieu-Donné
 * @see org.restlet.resource.ServerResource
 * @version 1.0
 */

public class TweetsDataStore {

    Map<Integer, Tweet> tweetsMap;
    private int tweetCount;

    public TweetsDataStore() {
        this.tweetsMap = new HashMap<>();
    }

    public synchronized Tweet createTweet(String content, int userId) {
        Tweet tweet = new Tweet(content, userId);
        tweet.setTweetId(tweetCount);
        tweetsMap.put(tweetCount, tweet);
        tweetCount++;
        return tweet;
    }

    public Collection<Tweet> getTweets() {
        return tweetsMap.values();
    }

    public Tweet getTweetById(int id) {
        return tweetsMap.get(id);
    }

    public Collection<Tweet> getTweetsByUserId(int userId) {
        Collection<Tweet> tweetCollection;
        tweetCollection = new ArrayList<>();
        tweetsMap.values().stream().filter(tweet -> tweet.getUserId() == userId).forEach(tweetCollection::add);
        return tweetCollection;
    }
}
