package org.inria.restlet.mta.application;

import org.inria.restlet.mta.resources.*;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * Application.
 *
 * @author msimonin
 */
public class MyTwitterApplication extends Application {

    public MyTwitterApplication(Context context) {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/users", UsersResource.class);
        router.attach("/users/many", BatchRessources.class);
        router.attach("/users/{userId}", UserResource.class);

        router.attach("/users/{userId}/tweets", TweetRessourceByUser.class);
        router.attach("/tweets", AllTweetRessource.class);
        router.attach("/tweets/{id}", SingleTweetRessource.class);
        return router;
    }
}
