# new feature
# Tags: optional
Feature: Add item to order
    Add item to order and check if costs are correct and ingredients are checked

    Scenario: Order cost checked when item added to order
        Given Order exists
        And A Burger costs $5.00
        When Burger is added to order
        Then Orders total cost is $5.00

    Scenario: Item is added to empty order
        Given Order exists
        When Burger is added to order
        Then Order contains a burger

    Scenario: Item is added to order with item
        Given Order exists
        And A Burger costs $5.00
        And Burger is in order
        And Chips cost $5.00
        When Chips are added to order
        Then Orders total cost is $10.00
        And Order contains a burger
        And order contains chips

    Scenario: Item is added to order with lack of ingrediants
        Given Order exists
        And Stock has 0 of Buns
        And A Burger contains buns
        And A Burger costs $5.00
        When Burger is added to order
        Then Receive error
        And Order Does not contain burger

    Scenario: Order is canceled
        Given Order exists
        When Order is canceled
        Then Order doesn’t exist

    Scenario: Item removed from order
        Given Order exists
        And Burger is in order
        When Burger is removed from order
        Then Order Does not contain burger

    Scenario: View Order
        Given Order exists
        And A Burger costs $5.00
        And Burger is in order
        When Order is viewed
        Then Order contains a burger
        And Orders total cost is $5.00

    Scenario: Confirm Order
        Given Order exists
        And A Burger costs $5.00
        And Burger is in order
        When Order is confirmed
        Then Payment is requested



