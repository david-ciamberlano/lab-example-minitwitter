package it.sonosolobit.lab.microtwitter.service;

import it.sonosolobit.lab.microtwitter.persistence.MTwitterMongoDao;
import it.sonosolobit.lab.microtwitter.domain.MTweet;
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

        List<String> mentions =  this.getSpecialText(newTweet.getText(), 'm');
        List<String> hashtags =  this.getSpecialText(newTweet.getText(), 'h');

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




    //esegue il parsing del testo per individuare le mentions o gli hashtags
    private List<String> getSpecialText (String text, char type) {

        List<String> specialText = new ArrayList<>();
        String regex;

        switch (type) {
            case 'm':
                regex = "@(\\w+)";
                break;
            case 'h':
                regex = "#(\\w+)";
                break;
            default:
                return specialText;
        }

        Pattern p = Pattern.compile(regex);
        Matcher m  = p.matcher(text);

        while (m.find()) {
            specialText.add (m.group(1));
        }

        return specialText;
    }


}
