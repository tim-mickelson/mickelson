package eu.mickelson.test.rest;

import java.util.Arrays;

import org.junit.Test;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

public class TestRestClient {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testContactList(){
		//final String url = "http://192.168.2.111:8090/mickelson/rest/contacts/contactList"; //getString(R.string.base_uri) + "/getmessage";
		final String url = "http://192.168.1.130:8090/mickelson/rest/contacts/contactList"; //getString(R.string.base_uri) + "/getmessage";
		HttpHeaders headers =getHeaders("pippo:pluto");
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
/*		
		 HttpEntity<String> requestEntity = new HttpEntity<String>(
			        RestDataFixture.standardOrderJSON(),
			        getHeaders("letsnosh" + ":" + "noshing"));
*/		
		
		RestTemplate restTemplate = new RestTemplate();
		try{
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
			//ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, requestEntity);
	
			
			assertNotNull(response);
			logger.debug(response.getBody());
		}catch(Exception e){
			e.printStackTrace();
		}
	}  // end public function testContactList
	
	static HttpHeaders getHeaders(String auth) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	    byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
	    headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

	    return headers;
	  }
	
} // end class TestRestClient