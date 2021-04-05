package com.tweet_app.service;

import java.util.List;
import java.util.Scanner;

import com.tweet_app.dao.TweetDao;
import com.tweet_app.dao.TweetDaoImpl;
import com.tweet_app.model.User;
import com.tweet_app.util.TweetConstants;
import com.tweet_app.util.TweetUtil;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetServiceImpl implements TweetService {

	static TweetDao tweetDao = new TweetDaoImpl();
	static TweetUtil tweetUtil = new TweetUtil();

	@Override
	public boolean createUser(User user) {
		return tweetDao.createUser(user);
	}

	@Override
	public boolean verifyEmailExists(String email) {
		return tweetDao.verifyEmailAlreadyExists(email);
	}

	@Override
	public User validateLoginDetails(User user) {
		return tweetDao.validateLoginDetails(user);
	}

	@Override
	public boolean postTweet(User user) {
		System.out.println("Please write a post to tweet..!");
		String inputTweet = "";
		Scanner scanner = new Scanner(System.in);
		inputTweet += scanner.nextLine();
		return tweetDao.postTweet(user, inputTweet);
	}

	@Override
	public void fetchTweets(User user, String callFrom) {
		System.out.println("---------------------------------------");

		List<String> allTweets = null;
		if (callFrom.equalsIgnoreCase(TweetConstants.USER_TWEETS)) {
			System.out.println("My Tweets...!");
			allTweets = tweetDao.fetchAllTweets(user);
		} else {
			System.out.println("Tweets Feed...!");
			allTweets = tweetDao.fetchAllTweets(null);
		}

		if (null != allTweets) {
			allTweets.stream().forEach(tweet -> {
				System.out.println(tweet);
			});
		} else {
			System.out.println("No Tweets Found.");
		}

		System.out.println("---------------------------------------");
	}

	@Override
	public void fetchUsers(User loggedInUser) {
		System.out.println("---------------------------------------");
		System.out.println("Users Information");

		List<User> usersInformation = tweetDao.fetchUsers(loggedInUser);
		if (null != usersInformation) {
			System.out.println("---------------------------------------");
			System.out.println("Name----------Date of Birth--------Email");
			usersInformation.stream().forEach(user -> {
				System.out.println(user.getFirstName() + user.getLastName() + "     " + user.getDateOfBirth() + "     "
						+ user.getEmail());
			});
		} else {
			System.out.println("No Users Found");
		}

		System.out.println("---------------------------------------");
	}

	@Override
	public boolean logout(User loggedInUser) {
		return tweetDao.logout(loggedInUser);
	}

	@Override
	public boolean changePassword(User loggedInUser) {
		return tweetDao.changePassword(loggedInUser);
	}

}
