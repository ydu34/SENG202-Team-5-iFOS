# new feature
# Tags: optional
Feature: Add item to order
    Add item to order and check if costs are correct and ingredients are checked

    Scenario: Item is added to empty order
        Given Order exists
        And A Burger costs $5.00
        When Burger is added to order
        Then Orders total cost is $5.00

    Scenario: Item is added to order with item
        Given Order exists
        And A Burger costs $5.00
        And Order contains burger
        When Burger is added to order
        Then Orders total cost is $10.00

    Scenario: Item is added to order with lack of ingrediants
        Given Order exists
        And there are no buns
        And A Burger costs $5.00
        When Burger is added to order
        Then Receive error
