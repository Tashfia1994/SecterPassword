package com.tashfia.secterpassword;

/**
 * Created by USER on 4/11/2018.
 */

public class Constant {


    //access db from device
    //for mobile device cmd>ipconfig>ipv4 address copy

    //for local host-Emulator public static final String SIGNUP_URL = "http://10.0.2.2/contacts/signup.php";

    //main url or address
    public static final String MAIN_URL = "http:// 192.168.0.106";

    //others url
    public static final String SIGNUP_URL = MAIN_URL+"/secretpassword/signup.php";
    public static final String LOGIN_URL = MAIN_URL+"/secretpassword/login.php";
    public static final String SAVE_URL = MAIN_URL+"/secretpassword/save_contact.php";
    public static final String UPDATE_URL = MAIN_URL+"/secretpassword/update_contact.php";
    public static final String DELETE_URL = MAIN_URL+"/secretpassword/delete_contact.php";

    //url for contacts view
    public static final String CONTACT_VIEW_URL = MAIN_URL+"/secretpassword/view_contact.php?cell=";

    //Keys for server communications
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CELL = "cell";
    public static final String KEY_USER_CELL = "user_cell";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //share preference
    //We will use this to store the user cell number into shared preference
    public static final String SHARED_PREF_NAME = "com.tashfia.secterpassword"; //pcakage name+ id

    //This would be used to store the cell of current logged in user
    public static final String CELL_SHARED_PREF = "cell";


    //json array name.We will received data in this array
    public static final String JSON_ARRAY = "result";


}
