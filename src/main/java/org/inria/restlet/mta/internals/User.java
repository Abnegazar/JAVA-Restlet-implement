package org.inria.restlet.mta.internals;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User
 *
 * @author ctedeschi
 * @author msimonin
 */
public class User {

    /**
     * Collection of tweets of the user.
     */
    Collection<Tweet> tweets_;
    /**
     * Internal id of the user.
     */
    private int id_;
    /**
     * Name of the user.
     */
    private String name_;
    /**
     * Age of the user.
     */
    private int age_;

    public User(String name, int age) {
        name_ = name;
        age_ = age;
        tweets_ = new ArrayList<>();
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }

    public int getAge() {
        return age_;
    }

    public void setAge(int age) {
        age_ = age;
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        id_ = id;
    }

    public Collection<Tweet> getTweets() {
        return tweets_;
    }
}
