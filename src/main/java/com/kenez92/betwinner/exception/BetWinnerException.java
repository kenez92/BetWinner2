package com.kenez92.betwinner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BetWinnerException extends RuntimeException {
    public static final String ERR_MATCHES_NOT_FOUND_EXCEPTION = "We dont have any matches!";
    public static final String ERR_MATCH_NOT_FOUND_EXCEPTION = "Match not found!";
    public static final String ERR_MATCH_ID_MUST_BE_NULL_OR_0_EXCEPTION = "Match ID must be null or 0!";
    public static final String ERR_MATCH_NOT_EXIST_EXCEPTION = "This match not exist!";
    public static final String ERR_MATCH_ID_MUST_BE_NOT_NULL_EXCEPTION = "Match ID must be not null!";

    public static final String ERR_USERS_NOT_FOUND_EXCEPTION = "We dont have any user!";
    public static final String ERR_USER_NOT_FOUND_EXCEPTION = "User not found!";
    public static final String ERR_USER_ID_MUST_BE_NULL_OR_0_EXCEPTION = "User ID must be null or 0!";
    public static final String ERR_USER_NOT_EXIST_EXCEPTION = "This user not exist!";
    public static final String ERR_USER_ID_MUST_BE_NOT_NULL_EXCEPTION = "User ID must be not null!";
    public static final String ERR_LOGIN_NOT_FOUND_EXCEPTION = "User with this login not found";

    public static final String ERR_ORDERS_NOT_FOUND_EXCEPTION = "We dont have any order!";
    public static final String ERR_ORDER_NOT_FOUND_EXCEPTION = "Order not found!";
    public static final String ERR_ORDER_ID_MUST_BE_NULL_OR_0_EXCEPTION = "Order ID must be null or 0!";
    public static final String ERR_ORDER_NOT_EXIST_EXCEPTION = "This order not exist!";
    public static final String ERR_ORDER_ID_MUST_BE_NOT_NULL_EXCEPTION = "Order ID must be not null!";


    //for api
    public static final String ERR_FOOTBALL_SOMETHING_WENT_WRONG_EXCEPTION = "Something went wrong.";

    public static final String ERR_FOOTBALL_MATCH_WE_DONT_HAVE_ACCESS_EXCEPTION = "We don't have access to this match";
    public static final String ERR_FOOTBALL_MATCH_NOT_FOUND_EXCEPTION = "Football match not found!";

    public static final String ERR_FOOTBALL_MATCHES_WE_DONT_HAVE_ACCESS_EXCEPTION = "We don't have access to this matches";
    public static final String ERR_FOOTBALL_MATCH_IS_FINISHED = "This match is finished!";


    public static final String ERR_COUPONS_NOT_FOUND_EXCEPTION = "We dont have any coupons!";
    public static final String ERR_COUPON_NOT_FOUND_EXCEPTION = "Coupon not found!";
    public static final String ERR_COUPON_ID_MUST_BE_NOT_NULL_EXCEPTION = "Coupon ID must be null or 0!";

    //betwinner2
    public static final String ERR_COMPETITION_NOT_FOUND_EXCEPTION = "Competition not found!";


    public BetWinnerException(String message) {
        super(message);
    }
}
