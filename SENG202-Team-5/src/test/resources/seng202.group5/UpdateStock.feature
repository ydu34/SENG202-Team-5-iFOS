Feature: Update Stock
  Scenario: Order uses ingredients:
    Given Stock has 10 of Buns
    When 6 Buns are used
    Then Stock now has 4 of Buns