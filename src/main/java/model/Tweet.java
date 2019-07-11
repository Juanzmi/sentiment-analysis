package model;

import java.util.Date;

/**
 * @author Wan Hazmi WAN MOHD NOR
 * on 11/7/2019
 * @project SentimentAnalysis
 */
public class Tweet {
    private String tweet;
    private int retweetCount;
    private boolean isRetweeted;
    private int likeCount;
    private Date tweetDate;
    private String date;
    private String userName;
    private int followerCount;
    private int friendsCount;
    private String location;

    public Tweet() {

    }

    public Tweet(String tweet, int retweetCount, boolean isRetweeted, int likeCount, Date tweetDate, String userName, int followerCount, int friendsCount, String location) {
        this.tweet = tweet;
        this.retweetCount = retweetCount;
        this.isRetweeted = isRetweeted;
        this.likeCount = likeCount;
        this.tweetDate = tweetDate;
        this.userName = userName;
        this.followerCount = followerCount;
        this.friendsCount = friendsCount;
        this.location = location;
    }

    public Tweet(String tweetText, int retweetCount, boolean retweeted, int likeCount, String date, String userName, int followerCount, int friendsCount, String loc) {
        this.tweet = tweetText;
        this.retweetCount = retweetCount;
        this.isRetweeted = retweeted;
        this.likeCount = likeCount;
        this.date = date;
        this.userName = userName;
        this.followerCount = followerCount;
        this.friendsCount = friendsCount;
        this.location = loc;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Date getTweetDate() {
        return tweetDate;
    }

    public void setTweetDate(Date tweetDate) {
        this.tweetDate = tweetDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public boolean isRetweeted() {
        return isRetweeted;
    }

    public void setRetweeted(boolean retweeted) {
        isRetweeted = retweeted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
