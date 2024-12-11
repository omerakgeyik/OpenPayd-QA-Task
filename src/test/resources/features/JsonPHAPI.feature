@API
Feature: API Testing for Blog Posts
  In order to ensure API responses are correct
  As a QA engineer
  I want to validate user post counts and unique IDs

  Scenario Outline: Counting posts for user
    Given I fetch all blog posts from the API
    When I count posts for user <userId>
    Then user <userId> should have <numposts> posts

    Examples:
      | userId | numposts |
      | 5      | 10       |
      | 7      | 10       |
      | 9      | 10       |

  Scenario: Unique ID per post
    Given I fetch all blog posts from the API
    Then each blog post should have a unique ID
