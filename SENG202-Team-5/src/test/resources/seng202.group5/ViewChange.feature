Feature: View Change
  Scenario: Order uses ingredients:
    Given Order Costs $7.50
    When Payment of $10 if confirmed
    Then The change $2.50 is displayed and removed from till


  Scenario:
    Given till starts with 3 $10 notes
    When an order is payed for with 1 $10 note
    Then till has 4 $10 notes
