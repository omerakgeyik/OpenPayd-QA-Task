Feature: Amazon Shop By Department Crawler

  @crawl
  Scenario: Verify all department links are valid
    Given go to the Amazon homepage
    When navigate to the dropdown menu
    Then see all department links
    And verify each link is not dead
    And verify the links