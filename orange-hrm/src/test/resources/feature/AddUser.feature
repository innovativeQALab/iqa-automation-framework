Feature: Add User
  I want to use this template for my feature file

  Scenario: Verify admin login successful
    Given user navigate to orange hrm URL
    When uses enters user name as "Admin" and password as "admin123" and click on login button
    Then user should be able to see "Welcome Admin" message

  @tag2
  Scenario Outline: Verify admin login successful with examples
    Given user navigate to orange hrm URL
    When uses enters user name as "<name>" and password as "<password>" and click on login button
    Then user should be able to see "<welcome_text>" message

    Examples: 
      | name  | password | welcome_text  |
      | Admin | admin123 | Welcome Admi |
      | Admin | admin123 | Welcome Admin |
