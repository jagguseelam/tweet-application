package com.tweet_app.util;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetConstants {
	public final static String VALIDATE_EMAIL = "SELECT email FROM user WHERE (email = ?)";
	public final static String VALIDATE_LOGIN_DETAILS = "SELECT * FROM user WHERE (email = ? ) AND (password = ?)";

	public final static String FETCH_ALL_TWEETS = "SELECT user.first_name, tweets.tweet FROM user INNER JOIN tweets ON tweets.tweets_user_id=user.user_id";
	public final static String FETCH_USER_TWEETS = "SELECT user.first_name, tweets.tweet FROM user INNER JOIN tweets ON tweets.tweets_user_id=user.user_id WHERE (user.email = ?)";
	public static final String FETCH_USERS = "SELECT * FROM user where (email != ?)";
	public final static String INSERT_USER = "INSERT INTO user (first_name, last_name, gender, date_of_birth, email, password, user_created) VALUES (?, ?, ?, ?, ?, ?, ?)";
	public final static String INSERT_LOGIN_HISTORY = "INSERT INTO login_history (login_time, email, login_history_user_id ) VALUES (?,?, ?)";
	public final static String INSERT_USER_TWEET = "INSERT INTO tweets(tweets_user_id, tweet) VALUES (?, ?)";

	public final static String NEW_REGISTRATION = "REGISTRATION";
	public final static String LOGIN = "LOGIN";
	public final static String LOGOUT = "logout";
	public final static String REGN_AND_LOGIN = "REGN_AND_LOGIN";
	public final static String SUGGESTIONS_TO_THE_USER = "SUGGESTIONS_TO_THE_USER";
	public final static String USER_TWEETS = "USER_TWEETS";
	public static final String ALL_TWEETS = "ALL_TWEETS";
	public static final String UPDATE_USER_LOGIN_OR_LOGOUT = "UPDATE user SET user_logged_in = ? WHERE email = ?";
	public static final String UPDATE_LOGOUT_HISTORY = "UPDATE login_history SET logout_time = ? WHERE (email = ? AND login_history_user_id = ?)";
	public static final String HOME = "HOME";
	public final static String FORGOT_PASSWORD = "FORGOT_PASSWORD";
	public static final String CHANGE_PASSWORD = "CHANGE_PASSWORD";
	public static final String FETCH_USER = "SELECT * FROM user where (email = ?) AND (date_of_birth = ?)";
	public static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE (email = ?) AND (date_of_birth = ?)";

};