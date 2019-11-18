package nl.michelbijnen;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestEndPoint {

    private final String restUrl = "http://localhost:1234";

    /**
     * Send a REST API request
     * @param privateKey Whether or not it should use the private key
     * @param restMethod Via which method we send the rest api request (GET, POST, PUT, DELETE)
     * @param controller What the controller is where we should go
     * @param method What the method is where we should go
     * @param inlineParameters add inline parameters (null if there aren't any)
     * @param headParameters add header parameters (null if there aren't any)
     * @param bodyParameters add body parameters (null if there aren't any)
     * @return The value it gets from sending the request. Null if there isn't any response
     */
    public String sendRequest(String privateKey, RestMethod restMethod, String controller, String method, ArrayList<String> inlineParameters, HashMap<String, String> headParameters, HashMap<String, String> bodyParameters) throws RequestException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            String inlineParametersString = "";
            if (!privateKey.equals("")) {
                inlineParametersString += "/" + privateKey;
            }

            if (inlineParameters != null) {
                inlineParametersString += this.buildInlineString(inlineParameters);
            }

            String headParametersString = "";
            if (headParameters != null) {
                headParametersString = this.buildHeaderString(headParameters);
            }

            String bodyParametersString = "";
            if (bodyParameters != null) {
                bodyParametersString = this.buildBodyString(bodyParameters);
            }

            String url = this.restUrl + "/" + controller + "/" + method + inlineParametersString + headParametersString;

            HttpResponse httpResponse;
            switch (restMethod) {
                case GET:
                    httpResponse = this.sendGetRequest(httpClient, url);
                    break;
                case POST:
                    httpResponse = this.sendPostRequest(httpClient, url, bodyParametersString);
                    break;
                case PUT:
                    httpResponse = this.sendPutRequest(httpClient, url, bodyParametersString);
                    break;
                case DELETE:
                    httpResponse = this.sendDeleteRequest(httpClient, url);
                    break;
                default:
                    httpResponse = this.sendGetRequest(httpClient, url);
                    break;
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                String errorMessage = "Failed : HTTP error code : " + statusCode;
                System.out.println(errorMessage);
                throw new RequestException("" + statusCode);
            }

            String content = EntityUtils.toString(httpResponse.getEntity());
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpResponse sendGetRequest(HttpClient httpClient, String url) throws IOException {
        HttpGet request = new HttpGet(url);
        request.addHeader("content-type", "application/form-data");
        return httpClient.execute(request);
    }

    private HttpResponse sendPostRequest(HttpClient httpClient, String url, String bodyParams) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity params = new StringEntity(bodyParams);
        params.setContentType("application/json");
        request.addHeader("content-type", "application/json");
        request.setEntity(params);
        return httpClient.execute(request);
    }

    private HttpResponse sendPutRequest(HttpClient httpClient, String url, String bodyParams) throws IOException {
        HttpPut request = new HttpPut(url);
        StringEntity params = new StringEntity(bodyParams);
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);
        return httpClient.execute(request);
    }

    private HttpResponse sendDeleteRequest(HttpClient httpClient, String url) throws IOException {
        HttpDelete request = new HttpDelete(url);
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        return httpClient.execute(request);
    }

    private String buildHeaderString(HashMap<String, String> map) {
        StringBuilder parameters = new StringBuilder("?");
        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            i++;
            parameters.append(entry.getKey());
            parameters.append("=");
            parameters.append(entry.getValue());
            if (i != map.size()) {
                parameters.append("&");
            }
        }

        return parameters.toString();
    }

    private String buildBodyString(HashMap<String, String> map) {
        return (new JSONObject(map)).toString();
    }

    private String buildInlineString(ArrayList<String> list) {
        StringBuilder parameters = new StringBuilder();
        for (String string : list) {
            parameters.append("/");
            parameters.append(string);
        }

        return parameters.toString();
    }
}
