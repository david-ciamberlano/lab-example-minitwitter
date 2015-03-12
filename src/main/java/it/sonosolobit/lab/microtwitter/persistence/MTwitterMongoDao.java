package it.sonosolobit.lab.microtwitter.persistence;

import com.mongodb.*;
import it.sonosolobit.lab.microtwitter.domain.MTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class MTwitterMongoDao implements MTwitterDao {

    @Autowired
    MongoClient mongoClient;

    @Value("${mongodb.dbname}")
    private String dbName;

    @Value("${mongodb.collectionName}")
    private String tweetCollection;

    /**
     * Inserisce un nuovo tweet nel database
     * @param tweet il tweet da inserire
     */
    public void insertTweet (MTweet tweet) {

        DB db = mongoClient.getDB(dbName);

        BasicDBObject newTweet = new BasicDBObject();
        newTweet.put("user", tweet.getUser());
        newTweet.put("text", tweet.getText());

        newTweet.put("timestamp", tweet.getTimestamp());

        newTweet.put("mentions", tweet.getMentions());

        newTweet.put("hashtags", tweet.getHashtags());

        //TODO implementare controllo
        db.getCollection("tweets").insert(newTweet);

    }


    /**
     * Esegue una ricerca per hawshtag
     * @param hashtag
     * @return lista di tweet con il dato hashtag
     */
    public List<MTweet> findTweetsByHashtag (String hashtag) {

        // prepara la query
        BasicDBObject query = new BasicDBObject("hashtags", hashtag).append("$limit",25);
        BasicDBObject fields = new BasicDBObject("text",1).append("timestamp", 1);

        return getMicroTweets(query, fields);

    }

    /**
     * resituisce tutti i tweet di un utente
     * @param user
     * @return
     */
    public List<MTweet> findTweetsByUser (String user) {

        // prepara la query
        BasicDBObject query = new BasicDBObject("user", user);
        BasicDBObject fields = new BasicDBObject("text",1).append("timestamp", 1).append("user",1);

        return getMicroTweets(query, fields);

    }


    public List<MTweet> timeline (String user) {

        // prepara la query
        BasicDBList or = new BasicDBList();

        BasicDBObject queryUser = new BasicDBObject("user", user);
        BasicDBObject queryMention = new BasicDBObject("mentions", user);
        BasicDBObject fields = new BasicDBObject("text",1).append("timestamp",1).append("user",1);


        or.add(queryUser);
        or.add(queryMention);

        DBObject query = new BasicDBObject ("$or", or);

        return getMicroTweets(query, fields);
    }



    private List<MTweet> getMicroTweets(DBObject query, DBObject fields) {

        DB db = mongoClient.getDB(dbName);

        DBCursor cursor = db.getCollection(this.tweetCollection).find(query,fields);

        cursor.sort(new BasicDBObject("timestamp",-1)).limit(25);

        List<MTweet> tweets = new ArrayList<>();

        try {
            while(cursor.hasNext()) {

                BasicDBObject dbo = (BasicDBObject)cursor.next();

                MTweet mTweet = new MTweet();
                mTweet.setText(dbo.getString("text"));
                mTweet.setUser(dbo.getString("user"));
                mTweet.setTimestamp(dbo.getLong("timestamp"));

                tweets.add(mTweet);
            }
        } finally {
            cursor.close();
        }

        return tweets;    }

}

