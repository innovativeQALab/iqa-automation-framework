Feature: Account Overview
  I want to use this template for my feature file

  @High @ScreenValidation @Account
  Scenario: Verify Account Overview screen
    Given user navigate to orange hrm URL
    When uses enters user name as "Admin" and password as "admin123" and click on login button
    Then user should be able to see "Welcome Overview" message

 