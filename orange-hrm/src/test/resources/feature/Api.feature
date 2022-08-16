Feature: API Test

@SmokeTest @GET @RUN
Scenario Outline: To verify user is able to execute HTTP POST call for given API
	Given Test case id is "TC_ID_01"
	And base URI is "https://reqres.in/"
	And request query params are "page=2"
	When user executes http "GET" method with endpoint "api/users"
	Then user verifies status code as 200
	
	Examples:
	||
	||
	
	
	@SmokeTest @POST @RUN

Scenario Outline: To verify user is able to execute HTTP POST call for given API
	Given Test case id is "TC_ID_01"
	And base URI is "https://reqres.in/"
	And request header key is "content-type" and value is "application/json"
	And request is ""
	When user executes http "POST" method with endpoint "api/users"
	Then user verifies status code as 201
	#And user verifies response as ""
	#And user verifies response value of node "" as ""
	
	Examples:
	||
	||