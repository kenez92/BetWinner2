package com.kenez92.betwinner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BetWinnerException extends RuntimeException {

    public static final String ERR_USER_WITH_THIS_ID_ALREADY_EXISTS_EXCEPTION = "User with this id already exists! ";
    public static final String ERR_USER_ID_MUST_BE_NULL_OR_0 = "User ID must be null or 0!";
    public static final String ERR_USER_NOT_FOUND_EXCEPTION = "User not found!";
    public static final String ERR_USER_NOT_EXIST_EXCEPTION = "User not exists";
    public static final String ERR_USER_NOT_DELETED = "User not deleted. Cannot delete this User!";
    public static final String ERR_LOGIN_NOT_FOUND_EXCEPTION = "User with this login not found";
    public static final String USER_WITH_LOGIN_OR_EMAIL_ALREADY_EXIST_EXCEPTION = "User with this login or email already exist!";
    public static final String ERR_STRATEGY_NOT_EXIST = "Strategy with this name not exists";


    public static final String ERR_COUPON_NOT_FOUND_EXCEPTION = "Coupon not found!";
    public static final String ERR_COUPON_RATE_IS_LOWER_THAN_1 = "Coupon rate cant be lower than 0!";
    public static final String ERR_COUPON_TYPE_NOT_FOUND_EXCEPTION = "Coupon type not found!";
    public static final String ERR_COUPON_DONT_BELONGS_TO_LOGGED_USER = "This coupon dont belongs to logged user";
    public static final String ERR_COUPON_TYPE_EXISTS_IN_COUPON = "This match already exists in your coupon";
    public static final String ERR_COUPON_IS_CLOSED = "This coupon is already closed!";
    public static final String ERR_COUPON_MATCH_ALREADY_STARTED = "This match already started! ";
    public static final String ERR_COUPON_IS_EMPTY = "Your coupon is empty";

    public static final String ERR_MATCH_NOT_FOUND_EXCEPTION = "Match not found!";
    public static final String ERR_MATCH_DAY_NOT_FOUND_EXCEPTION = "Match day not found!";

    public static final String ERR_SOMETHING_WENT_WRONG_EXCEPTION = "Something went wrong! Contact with the administrator!";

    //football.data
    public static final String ERR_FOOTBALL_SOMETHING_WENT_WRONG_EXCEPTION = "Something went wrong.";
    public static final String ERR_FOOTBALL_MATCH_WE_DONT_HAVE_ACCESS_EXCEPTION = "We don't have access to this match";
    public static final String ERR_FOOTBALL_MATCH_NOT_FOUND_EXCEPTION = "Football match not found!";
    public static final String ERR_FOOTBALL_MATCHES_WE_DONT_HAVE_ACCESS_EXCEPTION = "We don't have access to this matches";
    public static final String ERR_COMPETITION_NOT_FOUND_EXCEPTION = "Competition not found!";
    public static final String ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION = "Competition season not found!";
    public static final String ERR_COMPETITION_SEASON_NOT_EXISTS_EXCEPTION = "This competition season dont exists!";
    public static final String ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION = "Current match day not found!";
    public static final String ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION = "Competition table not found!";
    public static final String ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION = "Competition table element not found!";
    public static final String ERR_FILTERED_LIST_EXCEPTION = "Size of the filtered list is not equal to 1!";
    public static final String ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION = "Match score not found!";
    public static final String ERR_MATCH_STATS_NOT_FOUND_EXCEPTION = "Match stats not found!";
    //openweathermap
    public static final String ERR_WEATHER_NULL_EXCEPTION = "Weather class is equal to null!";
    public static final String ERR_WEATHER_NOT_FOUND_EXCEPTION = "Weather not found!";
    public static final String ERR_DATE_TIME_IS_WRONG_EXCEPTION = "Date time is wrong!";
    public static final String ERR_ILLEGAL_ARGUMENT_EXCEPTION = "Please put correct date in format \"yyyy-mm-dd\"";
    public static final String ERR_USER_DONT_HAVE_ENOUGH_MONEY = "You dont have enough money";


    public BetWinnerException(String message) {
        super(message);
    }
}
