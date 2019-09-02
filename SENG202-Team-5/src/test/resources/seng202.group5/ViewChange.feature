Feature: View Change
  Scenario: Order uses ingredients:
    Given Order Costs $7.50
    When Payment of $10 if confirmed
    Then $2.50 is displayed to be returned

  Scenario: Order is payed for with notes
    Given till starts with 3 $10 notes
    When an order is payed for with 1 $10 note
    Then till has 4 $10 notes

  Scenario:
    Given Order Costs $10.00
    And Order has already been payed for
    When Orders is refunded
    Then $5 is displayed to be returned
