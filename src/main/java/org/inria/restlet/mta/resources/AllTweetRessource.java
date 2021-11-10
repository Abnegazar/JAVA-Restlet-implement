package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handle operations on all tweets
 *
 * @author PADONOU Dieu-Donné
 * @see org.restlet.resource.ServerResource
 * @version 1.0
 */

public class AllTweetRessource extends ServerResource {

    private final Backend backend;

    public AllTweetRessource() {
        backend = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getAll() throws JSONException {
        Collection<Tweet> tweets = backend.getTweetsDataStore().getTweets();
        Collection<JSONObject> jsonTweets = new ArrayList<>();

        for (Tweet tweet : tweets) {

            User user = backend.getDatabase().getUser(tweet.getUserId());

            JSONObject current = new JSONObject();
            current.put("Id", tweet.getTweetId());
            current.put("author", user.getName());
            current.put("content", tweet.getContent());
            current.put("Created date", tweet.getSendDate());
            current.put("Last modified date", tweet.getLastUpdateDate());
            current.put("url", getReference() + "/" + tweet.getTweetId());
            jsonTweets.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonTweets);
        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation createTweet(JsonRepresentation representation) throws Exception {
        JSONObject object = representation.getJsonObject();
        String content = object.getString("content");
        int userId = object.getInt("userId");

        User user = backend.getDatabase().getUser(userId);

        JSONObject resultObject = new JSONObject();
        if (user != null) {
            //Insert new tweet
            Tweet tweet = backend.getTweetsDataStore().createTweet(content, userId);
            user.getTweets().add(tweet);

            // generate result
            resultObject.put("id", tweet.getTweetId());
            resultObject.put("author", user.getName());
            resultObject.put("content", tweet.getContent());
            resultObject.put("Created date", tweet.getSendDate());
            resultObject.put("Last modified date", tweet.getLastUpdateDate());
        } else {
            resultObject.put("Message", "Veuillez vérifier l'identifiant de l'utilisateur.");
        }

        JsonRepresentation result;
        result = new JsonRepresentation(resultObject);
        return result;
    }

    @Delete("json")
    public Representation deleteAll() throws JSONException {
        JSONObject msg;

        if (!backend.getTweetsDataStore().getTweets().isEmpty()) {
            backend.getTweetsDataStore().getTweets().clear();
            msg = new JSONObject().put("Message", "Tous les tweets ont été suprimés.");
        } else {
            msg = new JSONObject().put("Message", "Aucun tweet retrouvé.");
        }

        return new JsonRepresentation(msg);
    }

}
