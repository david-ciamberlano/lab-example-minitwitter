package it.sonosolobit.lab.minitwitter.service;

import it.sonosolobit.lab.minitwitter.DAO.MTwitterMongoDao;
import it.sonosolobit.lab.minitwitter.DO.MTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MTwitterService {

    @Autowired
    MTwitterMongoDao dao;

    public void parseInsertTweet (MTweet newTweet) {

        List<String> mentions =  this.getMentions(newTweet.getText());
        List<String> hashtags =  this.getHashtags( newTweet.getText());

        // completa l'oggetto con mentions e hashtags
        newTweet.setMentions(mentions);
        newTweet.setHashtags(hashtags);
        newTweet.setTimestamp((new Date()).getTime());

        dao.insertTweet(newTweet);

    }

    public List<MTweet> findByHashtag (String hashtag) {

        return dao.findTweetsByHashtag(hashtag);
    }


    public List<MTweet> findByUser (String user) {

        return dao.findTweetsByUser(user);
    }


    public List<MTweet> findTimeline (String user) {

        return dao.timeline(user);
    }


    //esegue il parsing del testo per individuare le mentions
    private List<String> getMentions (String text) {

        String mentionRegex = "@(\\w+)";
        Pattern pMentions = Pattern.compile(mentionRegex);
        Matcher mMentions  = pMentions.matcher(text);

        List<String> mentions = new ArrayList<String>();
        while (mMentions.find()) {
            mentions.add (mMentions.group(1));
        }

        return mentions;
    }

    //esegue il parsing del testo per individuare gli hashtags
    private List<String> getHashtags (String text) {

        String hashtagsRegex = "#(\\w+)";
        Pattern pHashtag = Pattern.compile(hashtagsRegex);
        Matcher mHashtag = pHashtag.matcher(text);

        List<String> hashtags = new ArrayList<String>();
        while (mHashtag.find()) {
            hashtags.add (mHashtag.group(1));
        }

        return hashtags;
    }

}
