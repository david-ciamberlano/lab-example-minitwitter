package it.sonosolobit.lab.minitwitter.DAO;

import it.sonosolobit.lab.minitwitter.DO.MiniTweet;

import java.util.List;

public interface MiniTwitterDao {

    public void insertTweet (MiniTweet tweet);

    public List<String> findTweetsByHashtag (String hashtag);

    public List<String> findTweetsByUser (String user);

    public List<String> timeline (String user);


}
