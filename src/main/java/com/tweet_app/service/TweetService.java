package com.tweet_app.service;

import com.tweet_app.model.User;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public interface TweetService {

	boolean verifyEmailExists(String email);

	boolean createUser(User user);

	User validateLoginDetails(User user);

	boolean postTweet(User user);

	void fetchTweets(User loggedInUser, String callFrom);

	void fetchUsers(User loggedInUser);

	boolean logout(User loggedInUser);

	boolean changePassword(User user);

}
