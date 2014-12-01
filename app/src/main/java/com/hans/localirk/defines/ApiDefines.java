package com.hans.localirk.defines;

public class ApiDefines {
    public static final boolean USE_MSGPACK = true;

    // Api url
    //public static final String URL = "http://192.168.0.104:999/api/";
    public static final String URL = "http://root.localikr.com/api/";

    // Api directories
    public static final String PATH_LOGIN = "login";
    public static final String PATH_LOGOUT = "logout";
    public static final String PATH_LOGIN_SESSION = "login/session";
    public static final String PATH_ENTER_AS_GUEST = "signup/guest";
    public static final String PATH_SING_UP = "signup";
    public static final String PATH_LOCATION = "user/location";

    // Registration constant
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String AGE = "age";
    public static final String AGE_GROUP = "ageGroup";
    public static final String GENDER = "gender";
    public static final String IMAGE_ID = "imageID";

    //Server response constants
    public static final String ERROR = "error";
    public static final String SESSION_ID = "sessionID";

    //Location constant
    public static final String LOCATION = "L";
    public static final String FETCH_PLACES = "fetchPlaces";
    public static final String REASON = "reason";

    //Server errors
    public static final String CLIENT_TOO_OLD = "CLIENT_TOO_OLD";
    public static final String DEMERGE_REQUEST_ALREADY_EXISTS = "DEMERGE_REQUEST_ALREADY_EXISTS";
    public static final String MERGE_REQUEST_ALREADY_EXISTS = "MERGE_REQUEST_ALREADY_EXISTS";
    public static final String TRY_AGAIN_LATER = "TRY_AGAIN_LATER";

    public static final String USERNAME_ALREADY_TAKEN = "USERNAME_ALREADY_TAKEN";
    public static final String USERNAME_TOO_LONG = "USERNAME_TOO_LONG";
    public static final String USERNAME_INVALID_CHARS = "USERNAME_TOO_LONG";
    public static final String EMAIL_ALREADY_TAKEN = "EMAIL_ALREADY_TAKEN";
    public static final String USER_NOTIFICATION_NOT_DELETEABLE = "USER_NOTIFICATION_NOT_DELETEABLE";
    public static final String USER_NOTIFICATION_OBSOLETE = "USER_NOTIFICATION_OBSOLETE";
    public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    public static final String INVALID_PASSWORD = "INVALID_PASSWORD";

    public static final String CHAT_ACTION_UNAUTHORIZED = "CHAT_ACTION_UNAUTHORIZED";
    public static final String CHAT_ACTIVE_NOT_DELETEABLE = "CHAT_ACTIVE_NOT_DELETEABLE";
    public static final String CHAT_BANNED = "CHAT_BANNED";
    public static final String CHAT_DELETED = "CHAT_DELETED";
    public static final String CHAT_LOCKED = "CHAT_LOCKED";
    public static final String CHAT_LOCK_NEED_2_USERS = "CHAT_LOCK_NEED_2_USERS";
    public static final String CHAT_USER_ALREADY_BANNED = "CHAT_USER_ALREADY_BANNED";
    public static final String CHAT_USER_NOT_FOUND = "CHAT_USER_NOT_FOUND";

    public static final String CHECKIN_CHILD_PLACE = "CHECKIN_CHILD_PLACE";
    public static final String CHECKIN_TOO_FAR_AWAY = "CHECKIN_TOO_FAR_AWAY";
    public static final String CHILD_PLACE_NOT_MERGEABLE = "CHILD_PLACE_NOT_MERGEABLE";
    public static final String PLACE_MUST_BE_EMPTY_BEFORE_DELETE = "PLACE_MUST_BE_EMPTY_BEFORE_DELETE";
    public static final String PLACE_MUST_CHECKOUT_BEFORE_DELETE = "PLACE_MUST_CHECKOUT_BEFORE_DELETE";
    public static final String PLACE_NOT_FOUND = "PLACE_NOT_FOUND";
}
