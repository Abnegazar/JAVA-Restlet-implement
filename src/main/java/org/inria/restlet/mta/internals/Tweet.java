package org.inria.restlet.mta.internals;

import java.time.LocalDateTime;

/**
 * Manage tweets
 *
 * @author PADONOU Dieu-Donné
 * @see org.restlet.resource.ServerResource
 * @version 1.0
 */

public class Tweet {
    private final LocalDateTime sendDate;
    int userId;
    private String content;
    private LocalDateTime lastUpdateDate;
    private int tweetId;

    public Tweet(String content, int userId) {
        this.content = content;
        this.sendDate = LocalDateTime.now();
        this.lastUpdateDate = LocalDateTime.now();
        this.userId = userId;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getUserId() {
        return userId;
    }

}
