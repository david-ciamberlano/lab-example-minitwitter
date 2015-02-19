package it.sonosolobit.lab.minitwitter.DAO;

import com.mongodb.*;
import it.sonosolobit.lab.minitwitter.DO.MiniTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class MiniTwitterMongoDao implements MiniTwitterDao {

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
    public void insertTweet (MiniTweet tweet) {

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
    public List<String> findTweetsByHashtag (String hashtag) {

        DB db = mongoClient.getDB(dbName);

        // prepara la query
        BasicDBObject query = new BasicDBObject("hashtags", hashtag);
        BasicDBObject field = new BasicDBObject("text",1);

        Cursor cursor = db.getCollection(this.tweetCollection).find(query,field);

        List<String> tweets = new ArrayList<String>();

        try {
            while(cursor.hasNext()) {

                BasicDBObject dbo = (BasicDBObject)cursor.next();
                tweets.add(dbo.getString("text"));
            }
        } finally {
            cursor.close();
        }

        return tweets;

    }

    /**
     * resituisce tutti i tweet di un utente
     * @param user
     * @return
     */
    public List<String> findTweetsByUser (String user) {

        DB db = mongoClient.getDB(dbName);

        // prepara la query
        BasicDBObject query = new BasicDBObject("user", user);
        BasicDBObject field = new BasicDBObject("text",1);

        Cursor cursor = db.getCollection(this.tweetCollection).find(query,field);

        List<String> tweets = new ArrayList<String>();

        try {
            while(cursor.hasNext()) {

                BasicDBObject dbo = (BasicDBObject)cursor.next();
                tweets.add(dbo.getString("text"));
            }
        } finally {
            cursor.close();
        }

        return tweets;

    }


    public List<String> timeline (String user) {

        DB db = mongoClient.getDB(dbName);

        // prepara la query
        BasicDBList or = new BasicDBList();

        BasicDBObject queryUser = new BasicDBObject("user", user);
        BasicDBObject queryMention = new BasicDBObject("mentions", user);
        BasicDBObject field = new BasicDBObject("text",1);

        or.add(queryUser);
        or.add(queryMention);

        DBObject query = new BasicDBObject ("$or", or);

        DBCursor cursor = db.getCollection(this.tweetCollection).find(query,field);

        cursor.sort(new BasicDBObject("timestamp",-1));

        List<String> tweets = new ArrayList<String>();

        try {
            while(cursor.hasNext()) {

                BasicDBObject dbo = (BasicDBObject)cursor.next();
                tweets.add(dbo.getString("text"));
            }
        } finally {
            cursor.close();
        }

        return tweets;
    }

}
