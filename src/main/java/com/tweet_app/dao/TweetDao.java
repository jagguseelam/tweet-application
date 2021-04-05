package com.tweet_app.dao;

import java.util.List;

import com.tweet_app.model.User;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public interface TweetDao {

	boolean verifyEmailAlreadyExists(String email);

	boolean createUser(User user);

	User validateLoginDetails(User user);

	boolean postTweet(User user, String inputTweet);

	List<String> fetchAllTweets(User user);

	List<User> fetchUsers(User user);

	boolean logout(User loggedInUser);

	boolean changePassword(User user);

}
