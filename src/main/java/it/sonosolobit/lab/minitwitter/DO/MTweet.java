package it.sonosolobit.lab.minitwitter.DO;

import java.util.ArrayList;
import java.util.List;

public class MTweet {

    private String text = "";
    private String user = "";
    private Long timestamp = -1L;
    private List<String> mentions = new ArrayList<>();
    private List<String> hashtags = new ArrayList<>();


    /*
     Getters & setters
     */

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
