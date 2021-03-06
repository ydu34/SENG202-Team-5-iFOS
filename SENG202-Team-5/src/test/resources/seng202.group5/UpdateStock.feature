Feature: Update Stock

  Scenario: Order uses ingredients
    Given Stock has 10 of Buns
    When 6 Buns are used
    Then Stock now has 4 of Buns

  Scenario: Buns are added to burger Recipe
    Given Stock has 10 of Buns
    And New Order is created
    When burger Recipe is modified to contain 1 buns
    And Burger is added to order
    Then Burger in the order contains 1 buns
