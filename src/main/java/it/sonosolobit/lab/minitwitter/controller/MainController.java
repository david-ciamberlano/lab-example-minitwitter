package it.sonosolobit.lab.minitwitter.controller;

import it.sonosolobit.lab.minitwitter.DO.MiniTweet;
import it.sonosolobit.lab.minitwitter.exceptions.TweetNotFoundException;
import it.sonosolobit.lab.minitwitter.service.MiniTwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MiniTwitterService service;




    @RequestMapping (value = "{user}/add", method = RequestMethod.GET)
    public String addNewTweetForm (Model model, @PathVariable("user") String user) throws TweetNotFoundException {

        MiniTweet tweet = new MiniTweet();
        tweet.setUser(user);

        model.addAttribute("newTweet", tweet);
        return "addNew";
    }


    @RequestMapping (value = "{user}/add", method = RequestMethod.POST)
    public String addNewTweet ( @ModelAttribute("newTweet") MiniTweet newTweet, @PathVariable("user") String user ) {

        service.parseInsertTweet(newTweet);

        return "redirect:/"+user+"/timeline";
    }

    @RequestMapping (value = "{user}/timeline", method = RequestMethod.GET)
    public String getUserTimeline (Model model, @PathVariable("user") String user) {

        List<String> tweets = service.findTimeline(user);

        model.addAttribute("tweets", tweets);

        return "tweetList";
    }


    @RequestMapping (value = "{user}/tweets", method = RequestMethod.GET)
    public String getUserTweets (Model model, @PathVariable("user") String user) {

        List<String> tweets = service.findByUser(user);

        model.addAttribute("tweets", tweets);

        return "tweetList";
    }


    @RequestMapping (value = "hashtag/{hashtag}", method = RequestMethod.GET)
    public String getTweetsByHashTag (Model model, @PathVariable("hashtag") String hashtag) {

        List<String> tweets  = service.findByHashtag(hashtag);

        model.addAttribute("tweets", tweets);

        return "tweetList";
    }


    public void setService(MiniTwitterService service) {
        this.service = service;
    }
}