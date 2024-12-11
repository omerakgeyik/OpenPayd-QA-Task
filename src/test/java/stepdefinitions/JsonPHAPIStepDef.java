package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.ConfigReader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonPHAPIStepDef {

    private static final String baseURL = ConfigReader.getProperty("baseURL");
    private List<Map<String, Object>> posts;

    @Given("I fetch all blog posts from the API")
    public void iFetchAllBlogPostsFromTheAPI() {
        Response response = RestAssured.get(baseURL + "/posts");
        assertEquals(200, response.getStatusCode(), "API call failed!");
        posts = response.jsonPath().getList("$");
    }

    @When("I count posts for user {int}")
    public void iCountPostsForUserUserId(int userId) {
        long count = posts.stream()
                .filter(post -> post.get("userId").equals(userId))
                .count();
    }

    @Then("user {int} should have {int} posts")
    public void userUserIdShouldHaveNumpostsPosts(int userId, int expectedCount) {
        long count = posts.stream()
                .filter(post -> post.get("userId").equals(userId))
                .count();
        assertEquals(expectedCount, count, "User: " + userId + " the number of posts is incorrect!");
    }

    @Then("each blog post should have a unique ID")
    public void eachBlogPostShouldHaveAUniqueID() {
        List<Integer> ids = posts.stream()
                .map(post -> (Integer) post.get("id"))
                .collect(Collectors.toList());

        assertEquals(ids.size(), ids.stream().distinct().count(), "Unique ID not found!");
    }
}
