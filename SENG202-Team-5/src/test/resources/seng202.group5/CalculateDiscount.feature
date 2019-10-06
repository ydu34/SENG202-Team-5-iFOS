Feature: Calculate Discount
  Scenario: Customer uses points to get a discount
    Given Order Costs $7.50
    And Customer has 10 purchase points
    When Customer uses 2 purchase points on an order which costs $7.50
    Then Order now Costs $6.50

#  Scenario: Customer uses points to get a discount
#    Given Order Costs $10.00
#    And Customer has 50 purchase points
#    When Customer uses 25 purchase points on an order which costs $10.00
#    Then Order now Costs $0.0
#    And Customer now has 30 purchase points
#
#  Scenario: Worker confirms an order for a customer which is registered.
#    Given Order Costs $10.00
#    And Customer has 5 purchase points
#    When Order gets payed
#    Then Customer now has 6 purchase points