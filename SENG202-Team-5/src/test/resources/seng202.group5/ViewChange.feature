Feature: View Change
  Scenario: Order uses ingredients:
    Given Order Costs $7.50
    When Payment of $10.00 if confirmed
    Then $2.50 is displayed to be returned

  Scenario: Order is payed for with notes
    Given till starts with 3 $10.00 notes
    When an order is payed for with 1 $10.00 note
    Then till has 4 $10.00 notes

  Scenario: Refund order
    Given Order Costs $10.00
    And Order has already been payed for
    When Orders is refunded
    Then $10.00 is displayed to be returned
