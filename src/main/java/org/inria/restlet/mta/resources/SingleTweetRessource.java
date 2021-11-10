package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.time.LocalDateTime;

/**
 * Handle operations on each tweet
 *
 * @author PADONOU Dieu-Donné
 * @see org.restlet.resource.ServerResource
 * @version 1.0
 */
public class SingleTweetRessource extends ServerResource {

    private final Backend backend;
    private Tweet tweet;
    private User author;

    public SingleTweetRessource() {
        backend = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getTweet() throws Exception {
        String tweetIdString = (String) getRequest().getAttributes().get("id");
        int id = Integer.parseInt(tweetIdString);
        tweet = backend.getTweetsDataStore().getTweetById(id);

        JSONObject userObject = new JSONObject();
        if (tweet != null) {
            author = backend.getDatabase().getUser(tweet.getUserId());

            userObject.put("Id", tweet.getTweetId());
            userObject.put("Author", author);
            userObject.put("Content", tweet.getContent());
            userObject.put("Adding date", tweet.getSendDate());
        } else {
            userObject.put("Message", "Aucun tweet portant ce identifiant n'a été retrouvé.");
        }

        return new JsonRepresentation(userObject);
    }

    @Put("json")
    public Representation updateTweet(JsonRepresentation representation) throws Exception {
        String tweetIdString = (String) getRequest().getAttributes().get("id");
        int tweetId = Integer.parseInt(tweetIdString);
        tweet = backend.getTweetsDataStore().getTweetById(tweetId);

        JSONObject resultObject = new JSONObject();
        if (tweet != null) {
            author = backend.getDatabase().getUser(tweet.getUserId());
            JSONObject object = representation.getJsonObject();
            String content = object.getString("content");
            tweet.setContent(content);
            tweet.setLastUpdateDate(LocalDateTime.now());

            // generate result
            resultObject.put("id", tweet.getTweetId());
            resultObject.put("author", author.getName());
            resultObject.put("content", tweet.getContent());
            resultObject.put("Created date", tweet.getSendDate());
            resultObject.put("Last modified date", tweet.getLastUpdateDate());
        } else {
            resultObject.put("Message", "Aucun tweet portant ce identifiant n'a été retrouvé.");
        }

        JsonRepresentation result;
        result = new JsonRepresentation(resultObject);
        return result;
    }

    @Delete("json")
    public Representation deleteTweet() {
        String tweetIdString = (String) getRequest().getAttributes().get("id");
        int id = Integer.parseInt(tweetIdString);
        backend.getTweetsDataStore().getTweets().removeIf(tweet1 -> tweet1.getTweetId() == id);

        JSONObject userObject = new JSONObject();
        userObject.put("Message", "Tweet supprimé avec succès.");

        return new JsonRepresentation(userObject);
    }

}
