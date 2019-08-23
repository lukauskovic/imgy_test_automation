package com.autotests.tests;

import com.rest.autotests.objects.Image;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static com.autotests.tests.Auth.registrationUser;
import static com.autotests.tests.Auth.user;
import static com.rest.autotests.utils.Config.BASE_URL;
import static com.rest.autotests.utils.Config.STATIC_TOKEN;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImageInteractions {

    static Image image = new Image("testDescription", System.getProperty("user.dir") + "/src/test/java/test_data/test.jpg");
    private static JSONObject imageBody = new JSONObject().put("description", image.getDescription());

    @Test
    public static void addFeedItem200() {
        Response response = given().
                header("Authorization", registrationUser.getToken()).
                header("Content-Type", "multipart/form-data").
                multiPart("data", imageBody.toString()).
                multiPart("images", new File(image.getPath()), "image/jpg").
                post(BASE_URL + "/feed");
        response.then().statusCode(200);
        image.setId(response.jsonPath().getString("newItem._id"));
    }

    @Test
    public static void getAllItems200() {
        Response response = given().
                header("Authorization-static", STATIC_TOKEN).
                get(BASE_URL + "/feed");
        response.then().statusCode(200);
        response.then().body("feedItemCount", notNullValue());
    }

    @Test
    public static void likeImage200() {
        Response response = given().
                header("Authorization", user.getToken()).
                put(BASE_URL + "/feed/item/like?feedItemId=" + image.getId());
        response.then().statusCode(200);
        List<Object> likes = response.jsonPath().getList("likes");
        assertTrue(likes.contains(user.getId()));
    }

}
