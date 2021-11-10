package org.inria.restlet.mta.backend;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.database.TweetsDataStore;

/**
 * Backend for all resources.
 *
 * @author ctedeschi
 * @author msimonin
 */
public class Backend {
    /**
     * Database.
     */
    private InMemoryDatabase database_;
    private TweetsDataStore tweetsDataStore;

    public Backend() {
        database_ = new InMemoryDatabase();
        tweetsDataStore = new TweetsDataStore();
    }

    public InMemoryDatabase getDatabase() {
        return database_;
    }

    public TweetsDataStore getTweetsDataStore() {
        return tweetsDataStore;
    }
}
