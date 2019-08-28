Feature: View Change
  Scenario: Order uses ingredients:
    Given Order Costs $7.50
    When Payment of $10 if confirmed
    Then The change $2.50 is is displayed and removed from till