Feature: API Test

@Api @SmokeTest @GET
Scenario Outline: To verify user is able to execute HTTP POST call for given API
	Given Test case id is "TC_ID_01"
	And base URI is "https://reqres.in/"
	And request query params are "page=2"
	When user executes http "GET" method with endpoint "api/users"
	Then user verifies status code as 200

	
	
	@Api @SmokeTest @POST

Scenario Outline: To verify user is able to execute HTTP POST call for given API
	Given Test case id is "TC_ID_01"
	And base URI is "https://reqres.in/"
	And request header key is "content-type" and value is "application/json"
	And request is ""
	When user executes http "POST" method with endpoint "api/users"
	Then user verifies status code as 202
