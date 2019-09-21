# new feature
# Tags: optional
Feature: Add item to order
    Add item to order and check if costs are correct and ingredients are checked

    Scenario: Order's cost checked after a Burger added to it
        Given Order exists
        And A Burger costs $5.00
        When Burger is added to order
        Then Orders total cost is $5.00

    Scenario: A Burger is added to empty order
        Given Order exists
        When Burger is added to order
        Then Order contains a burger

    Scenario: A Burger is added to order that contains chips
        Given Order exists
        And A Burger costs $5.00
        And Burger is in order
        And Chips cost $5.00
        When Chips are added to order
        Then Orders total cost is $10.00
        And Order contains a burger
        And order contains chips

    Scenario: A Burger is added to an order without the necessary ingredients
        Given Stock has 0 of Buns
        And Order exists
        And A Burger contains buns
        And A Burger costs $5.00
        When Burger is added to order
        Then Receive error
        And Order Does not contain burger

    Scenario: Order is cancelled
        Given Order exists
        And Burger is in order
        When Order is cancelled
        Then Order Does not contain burger

    Scenario: New order is created
        When New Order is created
        Then Order is empty
        And Orders total cost is $0.00

    Scenario: A Burger is removed from an order
        Given Order exists
        And Burger is in order
        When Burger is removed from order
        Then Order Does not contain burger

    Scenario: View an Order
        Given Order exists
        And A Burger costs $5.00
        And Burger is in order
        When Order is viewed
        Then Order contains a burger
        And Orders total cost is $5.00

    Scenario: Create new recipe
        Given There is a menu
        When A new recipe is created
        And A new item is created for that recipe
        Then The new recipe is in the menu

#    Scenario: Confirm an Order
#        Given Order exists
#        And A Burger costs $5.00
#        And Burger is in order
#        When Order is confirmed
#        Then Payment is requested



