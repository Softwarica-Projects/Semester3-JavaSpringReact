Feature: Poll
    Scenario Outline: Fetch all
      Given find all poll
      And find poll by id
      Then can vote
      Examples:
        | id  | question |
        | 1   | My question is this |

  Scenario Outline: Fetch Poll of User
    Given Poll Data
    And User Detail
    Then Fetch Poll of the user
    Examples:
      | Id  | question |
      | 1   | My question is this |