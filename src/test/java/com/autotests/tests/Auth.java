package com.autotests.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.rest.autotests.utils.Config.*;
import static com.rest.autotests.utils.RandomGenerator.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.rest.autotests.objects.*;

public class Auth {

    private static User user = new User(USER_EMAIL, USER_PASSWORD, USER_USERNAME);
    static User registrationUser = new User(randMail(), randPass(), randUsername());
    private static JSONObject loginBody = new JSONObject().put("email", user.getEmail()).put("password", user.getPassword());
    private static JSONObject registrationBody = new JSONObject().
            put("email", registrationUser.getEmail()).
            put("password", registrationUser.getPassword()).
            put("username", registrationUser.getUsername());

    @BeforeTest
    public static void takeToken() {
        Response response = given().
                header("authorization-static", STATIC_TOKEN).
                header("Content-Type", "application/json").
                body(loginBody.toString()).
                post(BASE_URL + "/auth/sign-in");
        String token = response.then().extract().jsonPath().getString("token");
        String id = response.then().extract().jsonPath().getString("_id");
        user.setToken(token);
        user.setId(id);
    }

    @Test
    public static void userLogin200() {
        Response response = given().
                header("Content-Type", "application/json").
                header("authorization-static", STATIC_TOKEN).
                body(loginBody.toString()).
                post(BASE_URL + "/auth/sign-in");
        response.then().statusCode(200);
        response.then().body("token", notNullValue());
    }

    @Test
    public static void userLogin401() {
        Response response = given().
                header("Content-Type", "application/json").
                body(loginBody.toString()).
                post(BASE_URL + "/auth/sign-in");
        response.then().statusCode(401);
    }

    @Test
    public static void userRegistration200() {
        Response response = given().
                header("Content-Type", "multipart/form-data").
                header("authorization-static", STATIC_TOKEN).
                multiPart("data", registrationBody.toString()).
                post(BASE_URL + "/auth/sign-up");
        response.then().statusCode(200);
        response.then().body("token", notNullValue());
        registrationUser.setToken(response.then().extract().path("token").toString());
    }

    @Test
    public static void userRegistration409() {
        Response response = given().
                header("Content-Type", "multipart/form-data").
                header("authorization-static", STATIC_TOKEN).
                multiPart("data", registrationBody.toString()).
                post(BASE_URL + "/auth/sign-up");
        response.then().statusCode(409);
    }

    @Test(dependsOnMethods = {"userRegistration200"})
    public static void changePassword200() {
        JSONObject changePasswordBody = new JSONObject().
                put("oldPassword", registrationUser.getPassword());
        registrationUser.setPassword(randPass());
        changePasswordBody.put("newPassword", registrationUser.getPassword());
        Response response = given().
                header("Content-Type", "application/json").
                header("Authorization", registrationUser.getToken()).
                body(changePasswordBody.toString()).
                post(BASE_URL + "/auth/change-password");
        response.then().statusCode(200);
    }

    @Test(dependsOnMethods = {"changePassword200"})
    public static void userLoginAfterPasswordChange200() {
        Response response = given().
                header("Content-Type", "application/json").
                header("Authorization-static", STATIC_TOKEN).
                body(loginBody.toString()).
                post(BASE_URL + "/auth/sign-in");
        response.then().statusCode(200);
    }
}
