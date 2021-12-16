Feature: Login

Scenario: Invalid password, valid username
			Given I am at the login page
			When I type in a username of "JKindle45"
			But I type in a password of "password"
			And I click the login button
			Then I should see a message of "Username and/or password is incorrect. Please try again"
			
Scenario: Invalid password, invalid username
			Given I am at the login page
			When I type in a username of "abcdefg"
			And I type in a password of "password"
			And I click the login button
			Then I should see a message of "Username and/or password is incorrect. Please try again"
			
Scenario: Valid password, invalid username
			Given I am at the login page
			When I type in a username of "abcdefg"
			And I type in a password of "Tiger232"
			And I click the login button
			Then I should see a message of "Username and/or password is incorrect. Please try again"
			
Scenario: Outline Successful Employee login
			Given I am at the login page
			When I type in a username of <username>
			And I type in a password of <password>
			And I click the login button
			Then I should be redirected to the employee homepage
			
			Examples:
					| username | password |
					| "JHunter34" | "John343#" |
					| "Emily678" | "Erogers5$" |
					
Scenario: Outline Successful Finance Manager login
			Given I am at the login page
			When I type in a username of <username>
			And I type in a password of <password>
			And I click the login button
			Then I should be redirected to the finance manager homepage
			
			Examples:
					| username | password |
					| "KJameson432" | "KyleE67*" |
