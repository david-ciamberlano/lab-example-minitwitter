package it.sonosolobit.lab.microtwitter.persistence;

import it.sonosolobit.lab.microtwitter.domain.MTweet;

import java.util.List;

public interface MTwitterDao {

    public void insertTweet (MTweet tweet);

    public List<MTweet> findTweetsByHashtag (String hashtag);

    public List<MTweet> findTweetsByUser (String user);

    public List<MTweet> timeline (String user);


}
