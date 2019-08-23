package com.autotests.tests;

import com.rest.autotests.objects.Image;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static com.autotests.tests.Auth.registrationUser;
import static com.autotests.tests.Auth.user;
import static com.rest.autotests.utils.Config.BASE_URL;
import static com.rest.autotests.utils.RandomGenerator.randMail;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;

public class UserActions {

    private static JSONObject changeMailBody = new JSONObject().put("email", randMail());

    @Test
    public static void getMyProfile200() {
        Response response = given().
                header("Authorization", user.getToken()).
                get(BASE_URL + "/user/me");
        response.then().statusCode(200);
        response.then().body("foundUser", notNullValue());
    }

    @Test
    public static void getOtherUserProfile200() {
        Response response = given().
                header("Authorization", user.getToken()).
                get(BASE_URL + "/user?userId=" + user.getId());
        response.then().statusCode(200);
        response.then().body("foundUser", notNullValue());
    }

    @Test
    public static void changeMail200() {
        Response response = given().
                header("Authorization", registrationUser.getToken()).
                header("Content-Type", "multipart/form-data").
                multiPart("data", changeMailBody.toString()).
                put(BASE_URL + "/user");
        response.then().statusCode(200);
    }

    @Test
    public static void changeProfileImage200() {
        Response response = given().
                header("Authorization", registrationUser.getToken()).
                header("Content-Type", "multipart/form-data").
                multiPart("image", new File(ImageInteractions.image.getPath()), "image/jpg").
                put(BASE_URL + "/user");
        response.then().statusCode(200);
    }

    @Test
    public static void follow200() {
        Response response = given().
                header("Authorization", registrationUser.getToken()).
                put(BASE_URL + "/user/follow?userId=" + user.getId());
        response.then().statusCode(200);
        List<Object> followers = response.jsonPath().getList("followedUser.followers");
        assertTrue(followers.contains(registrationUser.getId()));
    }
}
