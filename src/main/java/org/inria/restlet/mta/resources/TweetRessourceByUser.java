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
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handle tweets by according to their authors
 *
 * @author PADONOU Dieu-Donné
 * @see org.restlet.resource.ServerResource
 * @version 1.0
 */
public class TweetRessourceByUser extends ServerResource {

    private Backend backend;

    public TweetRessourceByUser() {
        backend = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getAllByUserId() throws JSONException {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.parseInt(userIdString);
        Collection<JSONObject> jsonTweets = new ArrayList<>();
        Collection<Tweet> tweets;
        JSONObject msg;

        if (backend.getDatabase().getUser(userId) != null) {
            //We could do it by querying the tweet's store directly
            //Collection<Tweet> tweets = backend.getTweetsDataStore().getTweetsByUserId(userId);
            tweets = backend.getDatabase().getUser(userId).getTweets();

            if (tweets.isEmpty()) {
                msg = new JSONObject().put("Message", "Aucun tweet retrouvé pour ce utilisateur.");
                jsonTweets.add(msg);
            } else {
                for (Tweet tweet : tweets) {
                    User user = backend.getDatabase().getUser(userId);

                    JSONObject current = new JSONObject();
                    current.put("author", user.getName());
                    current.put("Id", tweet.getTweetId());
                    current.put("content", tweet.getContent());
                    current.put("url", getReference() + "/" + tweet.getTweetId());
                    jsonTweets.add(current);
                }
            }
        } else {
            msg = new JSONObject().put("Message", "Veuillez vérifier l'identifiant de l'utilisateur.");
            jsonTweets.add(msg);
        }

        JSONArray jsonArray = new JSONArray(jsonTweets);
        return new JsonRepresentation(jsonArray);
    }

    @Delete("json")
    public Representation deleteAll() throws JSONException {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.parseInt(userIdString);
        JSONObject msg;
        Collection<Tweet> deleted;

        if (backend.getDatabase().getUser(userId) != null) {
            //We could do it by querying the tweet's store directly
            //deleted = backend.getTweetsDataStore().getTweets().stream().filter(tweet1 -> tweet1.getUserId() == userId).collect(Collectors.toList());
            deleted = backend.getDatabase().getUser(userId).getTweets();
            backend.getTweetsDataStore().getTweets().removeAll(deleted);

            if (deleted.isEmpty()) {
                msg = new JSONObject().put("Message", "Aucun tweet retrouvé pour ce utilisateur.");
            } else {
                msg = new JSONObject().put("Message", "Les tweets suivant ont été suprimés.");
            }
        } else {
            msg = new JSONObject().put("Message", "Veuillez vérifier l'identifiant de l'utilisateur.");
            deleted = new ArrayList<>();
        }
        Collection<JSONObject> jsonTweets = new ArrayList<>();
        jsonTweets.add(msg);

        for (Tweet tweet : deleted) {
            JSONObject current = new JSONObject();
            current.put("Id", tweet.getTweetId());
            current.put("content", tweet.getContent());
            current.put("url", getReference() + "/" + tweet.getTweetId());
            jsonTweets.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonTweets);
        return new JsonRepresentation(jsonArray);
    }
}
