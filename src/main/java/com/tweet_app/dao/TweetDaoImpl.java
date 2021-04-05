package com.tweet_app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tweet_app.TweetConfig;
import com.tweet_app.model.User;
import com.tweet_app.util.TweetConstants;
import com.tweet_app.util.TweetUtil;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetDaoImpl implements TweetDao {

	static TweetUtil tweetUtil = new TweetUtil();

	private PreparedStatement preparedStatement;

	@Override
	public boolean verifyEmailAlreadyExists(String email) {
		boolean isEmailAlreadyExists = false;
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.VALIDATE_EMAIL);
			preparedStatement.setString(1, email);
			ResultSet result = preparedStatement.executeQuery();
			if (null != result) {
				while (result.next()) {
					if (result.getString("email").equalsIgnoreCase(email))
						isEmailAlreadyExists = true;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Verifing Email.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Verifing Email.");
			}
		}
		return isEmailAlreadyExists;
	}

	@Override
	public boolean createUser(User user) {
		boolean userCreated = false;
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.INSERT_USER);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getGender());
			preparedStatement.setDate(4, tweetUtil.convertUtilDateToSqlDate(user.getDateOfBirth()));
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getPassword());
			preparedStatement.setDate(7, tweetUtil.convertUtilDateToSqlDate(user.getUserCreatedTime()));
			preparedStatement.executeUpdate();
			userCreated = true;
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Creating User.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Creating User.");
			}
		}

		return userCreated;

	}

	@Override
	public User validateLoginDetails(User user) {
		User loggedInUser = new User();
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.VALIDATE_LOGIN_DETAILS);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			ResultSet result = preparedStatement.executeQuery();
			if (null != result) {
				while (result.next()) {
					loggedInUser.setId(result.getString("user_id"));
					loggedInUser.setEmail(result.getString("email"));
					loggedInUser.setIsUserLoggedIn(result.getBoolean("user_logged_in"));
					loggedInUser.setDateOfBirth(result.getDate("date_of_birth"));
					loggedInUser.setFirstName(result.getString("first_name"));
					loggedInUser.setLastName(result.getString("last_name"));
				}
			}
			
			if (null != loggedInUser.getEmail()) {
				boolean isLogInUpdated = updateLoginAndLogout(loggedInUser.getEmail(), TweetConstants.LOGIN);
				if (isLogInUpdated) {
					storeLoginHistory(loggedInUser, TweetConstants.LOGIN);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Validating Login Details.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Validating Login Details.");
			}
		}
		return loggedInUser;
	}

	@Override
	public boolean postTweet(User loggedInUser, String inputTweet) {
		boolean isTweetPosted = false;
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.INSERT_USER_TWEET);
			preparedStatement.setString(1, loggedInUser.getId());
			preparedStatement.setString(2, inputTweet);
			preparedStatement.executeUpdate();
			isTweetPosted = true;
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While posting the tweet.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While posting the tweet.");
			}
		}
		return isTweetPosted;
	}

	@Override
	public List<String> fetchAllTweets(User loggedInUser) {
		List<String> allTweets = new ArrayList<String>();
		Connection connection = TweetConfig.getDbConnection();
		try {
			if (null != loggedInUser) {
				preparedStatement = connection.prepareStatement(TweetConstants.FETCH_USER_TWEETS);
				preparedStatement.setString(1, loggedInUser.getEmail());
			} else {
				preparedStatement = connection.prepareStatement(TweetConstants.FETCH_ALL_TWEETS);
			}

			ResultSet result = preparedStatement.executeQuery();
			if (null != result) {
				while (result.next()) {
					String post = result.getString("tweet");
					allTweets.add(post);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Fetching Tweets.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Fetching Tweets.");
			}
		}
		return allTweets;
	}

	@Override
	public List<User> fetchUsers(User loggedInUser) {
		List<User> usersInformation = new ArrayList<User>();
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.FETCH_USERS);
			preparedStatement.setString(1, loggedInUser.getEmail());
			ResultSet result = preparedStatement.executeQuery();
			if (null != result) {
				while (result.next()) {
					User user = new User();
					user.setEmail(result.getString("email"));
					user.setDateOfBirth(result.getDate("date_of_birth"));
					user.setFirstName(result.getString("first_name"));
					user.setLastName(result.getString("last_name"));
					usersInformation.add(user);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Fetching Tweets.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Fetching Tweets.");
			}
		}
		return usersInformation;
	}

	@Override
	public boolean changePassword(User loggedInUser) {
		boolean passwordChanged = false;
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.UPDATE_PASSWORD);
			preparedStatement.setString(1, loggedInUser.getPassword());
			preparedStatement.setString(2, loggedInUser.getEmail());
			preparedStatement.setDate(3, tweetUtil.convertUtilDateToSqlDate(loggedInUser.getDateOfBirth()));
			preparedStatement.executeUpdate();
			passwordChanged = true;
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Changing Password.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Changing Password.");
			}
		}
		return passwordChanged;
	}

	@Override
	public boolean logout(User loggedInUser) {
		boolean isLogoutUpdated = updateLoginAndLogout(loggedInUser.getEmail(), TweetConstants.LOGOUT);
		if (isLogoutUpdated) {
			storeLoginHistory(loggedInUser, TweetConstants.LOGOUT);
			return true;
		} else {
			return false;
		}

	}

	public boolean updateLoginAndLogout(String loggedInEmail, String callFrom) {
		boolean isUpdated = false;
		Connection connection = TweetConfig.getDbConnection();
		try {
			preparedStatement = connection.prepareStatement(TweetConstants.UPDATE_USER_LOGIN_OR_LOGOUT);
			if (callFrom.equalsIgnoreCase(TweetConstants.LOGIN)) {
				preparedStatement.setBoolean(1, true);
			} else {
				preparedStatement.setBoolean(1, false);
			}
			preparedStatement.setString(2, loggedInEmail);
			preparedStatement.executeUpdate();
			isUpdated = true;
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While Updating Logging In/ Logging Out.");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While Updating Logging In/ Logging Out.");
			}
		}
		return isUpdated;
	}

	private void storeLoginHistory(User loggedInUser, String callFrom) {
		Connection connection = TweetConfig.getDbConnection();
		try {
			if (callFrom.equalsIgnoreCase(TweetConstants.LOGIN)) {
				preparedStatement = connection.prepareStatement(TweetConstants.INSERT_LOGIN_HISTORY);
			} else {
				preparedStatement = connection.prepareStatement(TweetConstants.UPDATE_LOGOUT_HISTORY);
			}
			preparedStatement.setDate(1, tweetUtil.convertUtilDateToSqlDate(new Date()));
			preparedStatement.setString(2, loggedInUser.getEmail());
			preparedStatement.setString(3, loggedInUser.getId());

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQL Exception - Error While storing Login History");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException("SQL Exception - Error While storing Login History");
			}
		}

	}

}
