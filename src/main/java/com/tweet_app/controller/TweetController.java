package com.tweet_app.controller;

import java.util.Date;
import java.util.Scanner;

import com.tweet_app.dao.TweetDao;
import com.tweet_app.dao.TweetDaoImpl;
import com.tweet_app.model.User;
import com.tweet_app.service.TweetService;
import com.tweet_app.service.TweetServiceImpl;
import com.tweet_app.util.TweetConstants;
import com.tweet_app.util.TweetUtil;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class TweetController {

	static TweetService tweetService = new TweetServiceImpl();
	static TweetDao tweetDao = new TweetDaoImpl();
	static TweetUtil tweetUtil = new TweetUtil();

	public void displayTweetApplicationMainOptions() {
		System.out.println("---------------------------------------");
		System.out.println("1 - Register");
		System.out.println("2 - Login");
		System.out.println("3 - Forgot Password");
		System.out.println("---------------------------------------");

		boolean isUserInputValid = false;
		while (!isUserInputValid) {
			System.out.println("Please select from the above options to proceed...!");

			String userInput = tweetUtil.getInputFromUser();
			boolean validUserInput = tweetUtil.validateDashboardUserInput(userInput, TweetConstants.HOME);

			if (!validUserInput) {
				System.out.println("Invalid User Input.");
			} else {
				switch (Integer.parseInt(userInput)) {
				case 1:
					displayRegistrationOptions();
					break;
				case 2:
					displayLoginOptions();
					break;
				case 3:
					displayForgotPasswordOptions();
					break;
				default:
					break;
				}
			}
		}
	}

	public void displayRegistrationOptions() {
		System.out.println("---------------------------------------");
		System.out.println("User Registration");
		System.out.println("---------------------------------------");

		User user = getUserDetails(TweetConstants.NEW_REGISTRATION);
		boolean isRegistered = tweetService.createUser(user);
		if (isRegistered) {
			System.out.println("User Registration Successfull.");
			displayLoginOptions();
		} else {
			System.out.println("User Registration Failed. Try Again after some time.");
			displayRegistrationOptions();
		}
	}

	public void displayLoginOptions() {
		System.out.println("---------------------------------------");
		System.out.println("User Login");
		System.out.println("---------------------------------------");

		User user = getUserDetails(TweetConstants.LOGIN);
		User loggedInUser = tweetService.validateLoginDetails(user);
		if (null != loggedInUser.getEmail()) {
			System.out.println("Login Successfull..!");
			displayLoggedInUserMenu(loggedInUser);
		} else {
			System.out.println("Login Failed. Password may be wrong..!");
			displayTweetApplicationMainOptions();
		}
		System.out.println("---------------------------------------");
	}

	public void displayForgotPasswordOptions() {
		System.out.println("---------------------------------------");
		System.out.println("Forgot Password");
		System.out.println("---------------------------------------");

		User user = getUserDetails(TweetConstants.FORGOT_PASSWORD);
		boolean passwordChanged = tweetService.changePassword(user);
		if (!passwordChanged) {
			System.out.println("Error While Changing Password.");
		} else {
			System.out.println("Password Changed Successfully.");
			displayLoginOptions();
		}
	}

	public void displayLoggedInUserMenu(User loggedInUser) {
		System.out.println("---------------------------------------");
		System.out.println("1. Post a Tweet");
		System.out.println("2. View my Tweets");
		System.out.println("3. View all Tweets");
		System.out.println("4. View all Users");
		System.out.println("5. Reset Password");
		System.out.println("6. Logout");
		System.out.println("---------------------------------------");

		String userInput = "0";
		boolean validUserInput = false;
		while (!validUserInput) {
			System.out.println("Please choose from the above options.");
			userInput = tweetUtil.getInputFromUser();
			validUserInput = tweetUtil.validateDashboardUserInput(userInput, TweetConstants.LOGIN);
			if (!validUserInput) {
				System.out.println("Invalid User Input");
			}
		}

		if (validUserInput) {
			switch (Integer.parseInt(userInput)) {
			case 1:
				postTweet(loggedInUser);
				break;
			case 2:
				fetchTweets(loggedInUser, TweetConstants.USER_TWEETS);
				break;
			case 3:
				fetchTweets(loggedInUser, TweetConstants.ALL_TWEETS);
				break;
			case 4:
				fetchUsers(loggedInUser);
				break;
			case 5:
				resetPassword(loggedInUser);
			case 6:
				logout(loggedInUser);
				break;
			}

		}

	}

	private void postTweet(User loggedInUser) {
		boolean isPosted = tweetService.postTweet(loggedInUser);
		if (isPosted) {
			System.out.println("Tweet Posted Successfully.");
			boolean validUserInput = false;
			while (!validUserInput) {
				System.out.println("Press 1 to show Menu.");
				String userInput = tweetUtil.getInputFromUser();
				validUserInput = tweetUtil.validateDashboardUserInput(userInput,
						TweetConstants.SUGGESTIONS_TO_THE_USER);
				if (!validUserInput) {
					System.out.println("Invalid User Input");
				}
			}
			if (validUserInput) {
				displayLoggedInUserMenu(loggedInUser);
			}
		} else {
			System.out.println("Tweet Failed to Post. Post Again");
			postTweet(loggedInUser);
		}
	}

	private void fetchTweets(User loggedInUser, String callFrom) {
		tweetService.fetchTweets(loggedInUser, callFrom);
		boolean validUserInput = false;
		while (!validUserInput) {
			System.out.println("Press 1 to show Menu.");
			String userInput = tweetUtil.getInputFromUser();
			validUserInput = tweetUtil.validateDashboardUserInput(userInput, TweetConstants.SUGGESTIONS_TO_THE_USER);
			if (!validUserInput) {
				System.out.println("Invalid User Input");
			}
		}
		if (validUserInput) {
			displayLoggedInUserMenu(loggedInUser);
		}
	}

	private void fetchUsers(User loggedInUser) {
		tweetService.fetchUsers(loggedInUser);
		boolean validUserInput = false;
		while (!validUserInput) {
			System.out.println("Press 1 to show Menu.");
			String userInput = tweetUtil.getInputFromUser();
			validUserInput = tweetUtil.validateDashboardUserInput(userInput, TweetConstants.SUGGESTIONS_TO_THE_USER);
			if (!validUserInput) {
				System.out.println("Invalid User Input");
			}
		}
		if (validUserInput) {
			displayLoggedInUserMenu(loggedInUser);
		}

	}

	private void resetPassword(User loggedInUser) {
		System.out.println("---------------------------------------");
		System.out.println("Reset Password");
		System.out.println("---------------------------------------");

		String password = "";
		String confirmPassword = "";
		boolean isPasswordValid = false;
		boolean isPasswordMatched = false;

		while (!isPasswordValid) {
			System.out.println("Enter Password..!");
			password = tweetUtil.getInputFromUser();
			isPasswordValid = tweetUtil.validatePassword(password);
			if (!isPasswordValid) {
				System.out.println(
						"Invalid Password. Please Enter a valid password(combination of alphabets, special characters and numbers).");
			}
		}

		while (isPasswordValid && !isPasswordMatched) {
			System.out.println("Enter Password Again...!");
			confirmPassword = tweetUtil.getInputFromUser();
			if (confirmPassword.equals(password)) {
				isPasswordMatched = true;
				loggedInUser.setPassword(password);
			} else {
				isPasswordMatched = false;
				System.out.println("Password MisMatch. Please Enter Password Again.");
			}
		}

		boolean passwordChanged = tweetService.changePassword(loggedInUser);
		if (passwordChanged) {
			System.out.println("Password Changed Successfully.");
			displayLoginOptions();
		}

	}

	private void logout(User loggedInUser) {
		System.out.println("---------------------------------------");
		System.out.println("User Signing off...Please wait..!");

		boolean isLoggedOut = tweetService.logout(loggedInUser);
		if (!isLoggedOut) {
			System.out.println("Failed to SignOff..Please try again after sometime.");
			displayLoggedInUserMenu(loggedInUser);
		} else {
			displayTweetApplicationMainOptions();
		}
	}

	private User getUserDetails(String callFrom) {

		boolean isEmailValidAndExists = false;
		boolean isPasswordValid = false;
		boolean isGenderValid = false;
		boolean isPasswordMatched = false;
		boolean isFirstNameValid = false;
		boolean isDobValid = false;

		String email = "";
		String firstName = "";
		String lastName = "";
		String gender = "";
		String password = "";
		String confirmPassword = "";
		String dateOfBirth = "";

		while (((!isEmailValidAndExists || !isPasswordValid || !isPasswordMatched || !isFirstNameValid)
				&& callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION))
				|| ((!isEmailValidAndExists || !isPasswordValid) && callFrom.equalsIgnoreCase(TweetConstants.LOGIN)
						|| !isEmailValidAndExists && callFrom.equalsIgnoreCase(TweetConstants.FORGOT_PASSWORD))) {

			if (!isEmailValidAndExists) {
				System.out.println("Enter Email Id..!");
				email = tweetUtil.getInputFromUser();

				if (callFrom.equalsIgnoreCase(TweetConstants.LOGIN)) {
					boolean validEmail = tweetUtil.validateEmail(email);
					if (validEmail) {
						isEmailValidAndExists = validateAndverifyEmail(email, TweetConstants.LOGIN);
						if (!isEmailValidAndExists) {
							System.out.println("You are not a Registered User, Do you want to Register ?");

							boolean toRegistrationPage = false;
							while (!toRegistrationPage) {
								System.out.println("Press Y to go to Registration Page. Press N to Login Again.");
								String userInput = tweetUtil.getInputFromUser();

								if (userInput.equalsIgnoreCase("Y") || userInput.equalsIgnoreCase("N")) {
									if (userInput.equalsIgnoreCase("Y")) {
										displayRegistrationOptions();
									} else {
										displayLoginOptions();
									}
								} else {
									System.out.println("Invalid User Input.");
								}
							}
						}
					} else {
						System.out.println("Invalid Email. Please Enter a valid Email.");
					}

				} else if (callFrom.equalsIgnoreCase(TweetConstants.FORGOT_PASSWORD)) {
					isEmailValidAndExists = validateAndverifyEmail(email, TweetConstants.FORGOT_PASSWORD);
					if (!isEmailValidAndExists) {
						System.out.println("Email not Exists");
					}
				} else {
					boolean validEmail = tweetUtil.validateEmail(email);
					if (validEmail) {
						isEmailValidAndExists = validateAndverifyEmail(email, TweetConstants.NEW_REGISTRATION);
						if (!isEmailValidAndExists) {
							System.out.println("You are already a Registered User, Do you want to Login ?");
							boolean toLoginPage = false;
							while (!toLoginPage) {
								System.out.println("Press Y to go to Login Page. press N to go Register Again.");
								String userInput = tweetUtil.getInputFromUser();

								if (userInput.equalsIgnoreCase("Y") || userInput.equalsIgnoreCase("N")) {
									if (userInput.equalsIgnoreCase("Y")) {
										displayLoginOptions();
									} else {
										displayRegistrationOptions();
									}
								} else {
									System.out.println("Invalid User Input.");
								}
							}

						}
					} else {
						System.out.println("Invalid Email. Please Enter a valid Email.");
					}

				}
			}

			if (isEmailValidAndExists && !isPasswordValid) {
				System.out.println("Enter Password..!");
				password = tweetUtil.getInputFromUser();
				isPasswordValid = tweetUtil.validatePassword(password);
				if (!isPasswordValid) {
					System.out.println(
							"Invalid Password. Please Enter a valid password(combination of alphabets, special characters and numbers).");
				}
			}

			if (isEmailValidAndExists && isPasswordValid && (callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION)
					|| callFrom.equalsIgnoreCase(TweetConstants.FORGOT_PASSWORD))) {
				System.out.println("Please Enter Password Again..!");
				confirmPassword = tweetUtil.getInputFromUser();
				if (confirmPassword.equals(password)) {
					isPasswordMatched = true;
				} else {
					isPasswordMatched = false;
					System.out.println("Password MisMatch. Please Enter Password Again.");
				}
			}

			if (isEmailValidAndExists && isPasswordValid && isPasswordMatched && !isFirstNameValid
					&& callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION)) {
				Scanner scanner = new Scanner(System.in);
				while (!isFirstNameValid) {
					System.out.println("Enter your first name..!");
					firstName = scanner.nextLine();
					if (null != firstName && firstName != "") {
						isFirstNameValid = true;
						System.out.println("Enter your last name..!");
						lastName = scanner.nextLine();
					} else {
						isFirstNameValid = false;
					}
				}
			}

			if ((isEmailValidAndExists && isPasswordValid && isPasswordMatched && !isDobValid
					&& ((isFirstNameValid && callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION))
							|| (callFrom.equalsIgnoreCase(TweetConstants.FORGOT_PASSWORD))))) {
				while (!isDobValid) {
					System.out.println("Enter your Date of Birth in dd/MM/yyyy format..!");
					dateOfBirth = tweetUtil.getInputFromUser();
					isDobValid = tweetUtil.validateDateOfBirth(dateOfBirth);
					if (!isDobValid) {
						System.out.println("Invaild Date Format");
					}
				}
			}

			if (isEmailValidAndExists && isPasswordValid && isPasswordMatched && isFirstNameValid && isDobValid
					&& !isGenderValid && callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION)) {
				while (!isGenderValid) {
					System.out.println("Please choose your Gender..!");
					System.out.println("1 - Male");
					System.out.println("2 - Female");
					System.out.println("3 - Transgender");
					String userInput = tweetUtil.getInputFromUser();
					boolean validUserInput = tweetUtil.validateDashboardUserInput(userInput, TweetConstants.HOME);
					if (validUserInput) {
						isGenderValid = true;
						switch (Integer.parseInt(userInput)) {
						case 1:
							gender = "M";
							break;
						case 2:
							gender = "F";
							break;
						case 3:
							gender = "T";
							break;
						}
					}
				}
			}
		}

		if (isEmailValidAndExists && isPasswordValid && isPasswordMatched && isFirstNameValid && isDobValid
				&& isGenderValid && callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION)) {
			return setUserDetails(email, password, firstName, lastName, dateOfBirth, gender);
		} else if (isEmailValidAndExists && isPasswordValid && callFrom.equalsIgnoreCase(TweetConstants.LOGIN)) {
			return setUserDetails(email, password, "", "", "", "");
		} else if (isEmailValidAndExists && isPasswordValid && isPasswordMatched && isDobValid
				&& callFrom.equalsIgnoreCase(TweetConstants.FORGOT_PASSWORD)) {
			return setUserDetails(email, password, "", "", dateOfBirth, "");
		} else
			return null;
	}

	private User setUserDetails(String email, String password, String firstName, String lastName, String dateOfBirth,
			String gender) {
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setPassword(password);
		user.setDateOfBirth(tweetUtil.formatStringToDate(dateOfBirth));
		user.setUserCreatedTime(new Date());
		return user;
	}

	private boolean validateAndverifyEmail(String email, String callFrom) {
		boolean isEmailExists = tweetService.verifyEmailExists(email);
		if (isEmailExists && callFrom.equalsIgnoreCase(TweetConstants.LOGIN)) {
			return true;
		} else if (!isEmailExists && callFrom.equalsIgnoreCase(TweetConstants.LOGIN)) {
			return false;
		} else if (isEmailExists && callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION)) {
			return false;
		} else if (!isEmailExists && callFrom.equalsIgnoreCase(TweetConstants.NEW_REGISTRATION)) {
			return true;
		} else if (isEmailExists && callFrom.equalsIgnoreCase(TweetConstants.FORGOT_PASSWORD)) {
			return true;
		} else {
			return false;
		}
	}

}
