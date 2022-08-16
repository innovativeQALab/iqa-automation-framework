package com.orangehrm.step_def.api;

import org.iqa.suite.commons.ApiUtils;
import org.iqa.suite.commons.ExtentLogger;
import org.json.JSONObject;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class ApiSteps extends ApiUtils {

	private static final ExtentLogger extentLogger = new ExtentLogger();
	private static JSONObject queryParams = new JSONObject();
	private static JSONObject headerParams = new JSONObject();
	private static ThreadLocal<Response> response = new ThreadLocal<>();
	private static ThreadLocal<String> strRequest = new ThreadLocal<>();

	@Given("Test case id is {string}")
	public void test_case_id_is(String tc_id) {

		extentLogger.info("*********INFO : Test case ID: " + tc_id);
	}

	@Given("base URI is {string}")
	public void base_URI_is(String baseURI) {
		setBaseURI(baseURI);
		extentLogger.info("*********INFO : Base URI set as : " + baseURI);
	}

	@Given("request query params are {string}")
	public void request_query_params_are(String strQueryParams) {
		String[] strQueryParamsArr = strQueryParams.split("=");
		queryParams.put(strQueryParamsArr[0], strQueryParamsArr[1]);
		extentLogger.info(
				"*********INFO : Request query param added : " + strQueryParamsArr[0] + "=" + strQueryParamsArr[1]);
	}

	@Given("request header key is {string} and value is {string}")
	public void request_header_key_is_and_value_is(String headerKey, String headerValue) {
		headerParams.put(headerKey, headerValue);
		extentLogger.info("*********INFO : Request header key :" + headerKey + " and header value : " + headerValue);
	}

	@Given("request is {string}")
	public void request_is(String request) {
		request = "{\"name\": \"Prashant\",\"job\": \"Tester\"}";
		strRequest.set(request);
		extentLogger.info("*********INFO : Request body is : " + strRequest.get());
	}

	@When("user executes http {string} method with endpoint {string}")
	public void user_executes_http_method_with_endpoint(String method, String path) {

		if (method.equalsIgnoreCase("GET")) {
			response.set(httpGet(queryParams, path));
		} else if (method.equalsIgnoreCase("POST")) {
			// response.set(httpPost(headerParams, queryParams, method, path));
			response.set(httpPost(headerParams, strRequest.get(), path));
			extentLogger.info("*********INFO : Post Http Method executed for endpoint- " + path);
		}
	}

	@Then("user verifies status code as {int}")
	public void user_verifies_status_code_as(int expectedStatusCode) {
		Assert.assertEquals(getStatusCode(response.get()), expectedStatusCode, getBasePath());
		int actualStatusCode = getStatusCode(response.get());
		if (actualStatusCode == expectedStatusCode) {
			extentLogger.info("*********INFO : Verified Response status code as expected - " + actualStatusCode);
		} else {
			extentLogger.fail("*********ERROR : Status code mismatch!!! Expected : " + expectedStatusCode + " Actual: "
					+ actualStatusCode);
			Assert.fail("Status code mismatch!!!");
		}
	}

	@Then("user verifies response as {string}")
	public void user_verifies_response_as(String expectedResponse) {

	}

	@Then("user verifies response value of node {string} as {string}")
	public void user_verifies_response_value_of_node_as(String string, String string2) {

	}

}
