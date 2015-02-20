package it.sonosolobit.lab.minitwitter.controller;

import it.sonosolobit.lab.minitwitter.DO.MTweet;
import it.sonosolobit.lab.minitwitter.service.MTwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MainController {

    @Autowired
    private MTwitterService service;

    // timeline
    @RequestMapping (value = {"/{user}","/{user}/timeline"}, method = RequestMethod.GET)
    public String getUserTimeline (Model model, @PathVariable("user") String user, @ModelAttribute("newTweet") MTweet newTweet ) {

        newTweet.setUser(user);

        List<MTweet> tweets = service.findTimeline(user);
        model.addAttribute("tweets", tweets);
        model.addAttribute("user", user);
        model.addAttribute("pagetype", "timeline");

        return "home";
    }

    // Aggiunta di un nuovo tweet
    @RequestMapping (value = "/{user}/add", method = RequestMethod.POST)
    public String addNewTweet ( @ModelAttribute("newTweet") MTweet newTweet, @PathVariable("user") String user ) {

        service.parseInsertTweet(newTweet);

        return "redirect:/"+user;
    }

    // Trova i tweet postati da un utente
    @RequestMapping (value = "/{user}/tweets", method = RequestMethod.GET)
    public String getUserTweets (Model model, @PathVariable("user") String user, @ModelAttribute("newTweet") MTweet newTweet ) {

        List<MTweet> tweets = service.findByUser(user);

        model.addAttribute("tweets", tweets);
        model.addAttribute("pagetype", "Tweets");

        return "home";
    }


    // Trova i tweet che contengono un certo hashtag
    @RequestMapping (value = "/hashtag/{hashtag}", method = RequestMethod.GET)
    public String getTweetsByHashTag (Model model, @PathVariable("hashtag") String hashtag, @ModelAttribute("newTweet") MTweet newTweet) {

        List<MTweet> tweets  = service.findByHashtag(hashtag);

        model.addAttribute("tweets", tweets);
        model.addAttribute("pagetype", "hashtag: "+hashtag);

        return "home";
    }


    public void setService(MTwitterService service) {
        this.service = service;
    }
}