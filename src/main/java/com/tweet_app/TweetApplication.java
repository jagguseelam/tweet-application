package com.tweet_app;

import com.tweet_app.controller.TweetController;
import com.tweet_app.util.TweetUtil;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetApplication {

	static TweetUtil tweetUtil = new TweetUtil();
	static TweetController tweetController = new TweetController();

	public static void main(String[] args) {
		System.out.println("---------------------------------------");
		System.out.println("Welcome to Tweet!");
		System.out.println("---------------------------------------");

		tweetController.displayTweetApplicationMainOptions();
	}

}
