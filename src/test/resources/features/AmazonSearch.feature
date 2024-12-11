Feature: Amazon Laptop Search and Cart Validation
  To search for laptops on Amazon
  To add non-discounted products in stock to the cart and validate them

  @search
  Scenario: Add non-discounted laptops in stock to cart and validate
    Given go to the Amazon homepage
    When search for "laptop" is performed
    Then search results should be displayed
    When all non-discounted products in stock on the first page are added to the cart
    And the cart is opened
    Then the correct products should be listed in the cart