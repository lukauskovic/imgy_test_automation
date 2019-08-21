package com.autotests.tests;

import com.rest.autotests.objects.Image;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;

import static com.autotests.tests.Auth.registrationUser;
import static com.rest.autotests.utils.Config.BASE_URL;
import static io.restassured.RestAssured.given;

public class ImageInteractions {

    private static Image image = new Image("testDescription", System.getProperty("user.dir") + "/src/test/java/test_data/test.jpg");
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
    }

}
