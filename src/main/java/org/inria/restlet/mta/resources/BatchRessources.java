package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.backend.Backend;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Handle many tweets adding
 *
 * @author PADONOU Dieu-Donné
 * @see org.restlet.resource.ServerResource
 * @version 1.0
 */

public class BatchRessources extends ServerResource {

    private final Backend backend_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public BatchRessources() {
        super();
        backend_ = (Backend) getApplication().getContext().getAttributes().get("backend");
    }

    @Post
    public Representation addMany(JsonRepresentation representation) throws Exception {
        JSONObject objects = representation.getJsonObject();
        JSONArray users = objects.getJSONArray("users");

        Collection<JSONObject> objectCollection = new ArrayList<>();

        JSONObject msg = new JSONObject().put("Resultat", users.length() + " utilisateurs ajoutés.");
        objectCollection.add(msg);
        JSONObject user_ = new JSONObject();
        for (int i = 0; i < users.length(); i++) {
            user_ = users.getJSONObject(i);
            String name = user_.getString("name");
            int age = user_.getInt("age");

            backend_.getDatabase().createUser(name, age);

            objectCollection.add(user_);
            // Save the user
        }

        // generate result
        JSONArray output = new JSONArray(objectCollection);
        return new JsonRepresentation(output);
    }

}
