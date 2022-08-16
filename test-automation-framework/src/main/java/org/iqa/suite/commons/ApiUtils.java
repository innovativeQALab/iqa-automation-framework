package org.iqa.suite.commons;

import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {

	/**
	 * This method will set RestAssured baseURI
	 *
	 * @param String baseURI
	 */
	protected void setBaseURI(String baseURI) {
		RestAssured.baseURI = baseURI;
	}

	/**
	 * This method will get RestAssured baseURI
	 *
	 * @return String baseURI
	 */
	protected String getBaseURI() {
		return RestAssured.baseURI;
	}

	/**
	 * This method will set RestAssured basePath
	 *
	 * @param String basePath
	 */
	protected void setBasePath(String basePath) {
		RestAssured.basePath = basePath;
	}

	/**
	 * This method will get RestAssured basePath
	 *
	 * @return String basePath
	 */
	protected String getBasePath() {
		return RestAssured.basePath;
	}

	/**
	 * This method will return Http Request
	 */
	protected RequestSpecification httpRequest() {
		return RestAssured.given();
	}

	/**
	 * This method will return Http Request
	 */
	protected RequestSpecification httpRequest(JSONObject headerParams) {
		return RestAssured.given().headers(headerParams.toMap());
	}
	
	/**
	 * This method will return Http Request
	 */
	protected RequestSpecification httpRequest(JSONObject headerParams,String request) {
		return RestAssured.given().headers(headerParams.toMap()).body(request);
	}

	/**
	 * This method will return Http Request set with headers and queryparams
	 *
	 * @return RequestSpecification request
	 */
	protected RequestSpecification httpRequest(JSONObject headerParams, JSONObject queryParams) {
		return RestAssured.given().headers(headerParams.toMap()).queryParams(queryParams.toMap());
	}

	/**
	 * This method will return Http Request set with headers,queryparams and request
	 *
	 * @return RequestSpecification request
	 */
	protected RequestSpecification httpRequest(JSONObject headerParams, JSONObject queryParams, String request) {
		return RestAssured.given().headers(headerParams.toMap()).queryParams(queryParams.toMap()).body(request);
	}

	/**
	 * This method will execute REST Http GET type method
	 *
	 * @param String path/endpoint
	 * @return Response response
	 */
	protected Response httpGet(String path) {
		return httpRequest().request(Method.GET, path);
	}

	/**
	 * This method will execute REST Http GET type method request level headers
	 *
	 * @param String path/endpoint
	 * @return Response response
	 */
	protected Response httpGet(JSONObject headerParams, String path) {
		return httpRequest(headerParams).request(Method.GET, path);
	}

	/**
	 * This method will execute REST Http GET type method headers and query params
	 *
	 * @param String path/endpoint
	 * @return Response response
	 */
	protected Response httpGet(JSONObject headerParams, JSONObject queryParams, String path) {
		return httpRequest(headerParams, queryParams).request(Method.GET, path);
	}

	/**
	 * This method will execute REST Http POST type method
	 *
	 * @param String path/endpoint
	 * @return Response response
	 */
	protected Response httpPost(String path) {
		return httpRequest().request(Method.POST, path);
	}

	/**
	 * This method will execute REST Http POST type method
	 *
	 * @param JSONObject headerParams
	 * @param JSONObject queryParams
	 * @param String     path/endpoint
	 * @return Response response
	 */
	protected Response httpPost(JSONObject headerParams, JSONObject queryParams, String path) {
		return httpRequest(headerParams, queryParams).request(Method.POST, path);
	}

	/**
	 * This method will execute REST Http POST type method
	 *
	 * @param JSONObject headerParams
	 * @param JSONObject queryParams
	 * @param String     request
	 * @param String     path/endpoint
	 * @return Response response
	 */
	protected Response httpPost(JSONObject headerParams, JSONObject queryParams, String request, String path) {
		return httpRequest(headerParams, queryParams, request).request(Method.POST, path);
	}
	
	/**
	 * This method will execute REST Http POST type method
	 *
	 * @param JSONObject headerParams
	 * @param JSONObject queryParams
	 * @param String     request
	 * @param String     path/endpoint
	 * @return Response response
	 */
	protected Response httpPost(JSONObject headerParams, String request, String path) {
		return httpRequest(headerParams, request).request(Method.POST, path);
	}

	/**
	 * http delete
	 *
	 * @param params params
	 * @param path   endpoint
	 * @return response
	 */
	protected Response httpDelete(JSONObject params, String path) {
		return httpRequest(params).request(Method.DELETE, path);
	}

	/**
	 * http put
	 *
	 * @param params params
	 * @param path   endpoint
	 * @return response
	 */
	protected Response httpPut(JSONObject params, String path) {
		return httpRequest(params).request(Method.PUT, path);
	}

	/**
	 * Get Status code
	 *
	 * @param response response
	 * @return status code
	 */
	protected int getStatusCode(Response response) {
		return response.getStatusCode();
	}

	/**
	 * This method evaluates Json Path and returns node value
	 *
	 * @param response response
	 * @return jsonPath
	 */
	protected String jsonPathEvaluator(Response response, String expectedNode) {
		return response.jsonPath().get(expectedNode);
	}

}
