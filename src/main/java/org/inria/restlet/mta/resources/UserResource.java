package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing a user.
 *
 * @author PADONOU Dieu-Donné
 */
public class UserResource extends ServerResource {

    /**
     * Backend.
     */
    private final Backend backend_;

    /**
     * Utilisateur géré par cette resource.
     */
    private User user_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UserResource() {
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Get("json")
    public Representation getUser() throws Exception {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.parseInt(userIdString);
        user_ = backend_.getDatabase().getUser(userId);

        JSONObject userObject = new JSONObject();
        userObject.put("id", user_.getId());
        userObject.put("name", user_.getName());
        userObject.put("age", user_.getAge());
        userObject.put("Tweets", new JSONArray(user_.getTweets()));

        return new JsonRepresentation(userObject);
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Delete("json")
    public Representation deleteUser() throws Exception {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.parseInt(userIdString);
        user_ = backend_.getDatabase().getUser(userId);
        backend_.getDatabase().getUsers().remove(user_);

        JSONObject msg = new JSONObject();
        msg.put("Message", "L'utilisateur " + user_.getName() + " a été supprimé avec succès.");

        return new JsonRepresentation(msg);
    }

}
