/*
 * Copyright 2010-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.android.basicauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import eu.mickelson.web.contact.beans.ContactBean;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author Roy Clarkson
 */
public class MainActivity extends AbstractAsyncActivity {

	protected static final String TAG = MainActivity.class.getSimpleName();

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);

		// Initiate the request to the protected service
		final Button submitButton = (Button) findViewById(R.id.submit);
		submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new FetchSecuredResourceTask().execute();
			}
		});
	}
	
	// ***************************************
	// Private methods
	// ***************************************
	private void displayResponse(ContactBean response) {
		Toast.makeText(this, response.getName(), Toast.LENGTH_LONG).show();
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class FetchSecuredResourceTask extends AsyncTask<Void, Void, ContactBean> {

		private String username;

		private String password;

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();

			// build the message object
			EditText editText = (EditText) findViewById(R.id.username);
			this.username = editText.getText().toString();

			editText = (EditText) findViewById(R.id.password);
			this.password = editText.getText().toString();
		}

		protected ContactBean doInBackground(Void... params) {
			final String url = "http://192.168.1.130:8090/mickelson/rest/contacts/contactList"; //getString(R.string.base_uri) + "/getmessage";
			//final String url = "http://192.168.2.111:8090/mickelson/rest/contacts/contactList"; //getString(R.string.base_uri) + "/getmessage";
			System.out.println("MainActivity.doInBackground - url: "+url);
			// Populate the HTTP Basic Authentitcation header with the username and password
			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAuthorization(authHeader);
			requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
			ContactBean bean = new ContactBean();
			try {
				// Make the network request
				Log.d(TAG, "MainActivity.doInBackground - url: "+url);
				//ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Object.class);
				ResponseEntity<ContactBean> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(requestHeaders), ContactBean.class);
				if(response==null)
					Log.d(TAG, "response is null");
				else{
					Log.d(TAG, "response is NOT null");
					Log.d(TAG, response.toString());
					bean.setName(response.getBody().getName()+" "+response.getBody().getSurname());
				}
				//ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Message.class);
				return bean;
			} catch (HttpClientErrorException e) {
				Log.e(TAG, "HttpClientErrorException: "+e.getLocalizedMessage(), e);
				return bean;//new Message(0, e.getStatusText(), e.getLocalizedMessage());
			} catch (ResourceAccessException e) {
				Log.e(TAG, "ResourceAccessException"+e.getLocalizedMessage(), e);
				return bean; //new Message(0, e.getClass().getSimpleName(), e.getLocalizedMessage());
			}
		}
		
/*		
		
		@Override
		protected TaskBean doInBackgroundNoAuth(Void... params){
			final String url = "http://192.168.1.128:8090/wsbullguard/controller/tasks/test";
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			
			
			
			try {
				// Make the network request
				Log.d(TAG, url);
				ResponseEntity<TaskBean> response = restTemplate.exchange(url, HttpMethod.GET, null, TaskBean.class);
				return response.getBody();
			} catch (HttpClientErrorException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return null;
			} catch (ResourceAccessException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return null;
			}
		}  // end function doInBackground

		protected Message doInBackgroundOld(Void... params) {
			final String url = getString(R.string.base_uri) + "/getmessage";
			System.out.println("MainActivity.doInBackground - url: "+url);
			// Populate the HTTP Basic Authentitcation header with the username and password
			HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setAuthorization(authHeader);
			requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			// Create a new RestTemplate instance
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());

			try {
				// Make the network request
				Log.d(TAG, url);
				ResponseEntity<Message> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(requestHeaders), Message.class);
				return response.getBody();
			} catch (HttpClientErrorException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return new Message(0, e.getStatusText(), e.getLocalizedMessage());
			} catch (ResourceAccessException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
				return new Message(0, e.getClass().getSimpleName(), e.getLocalizedMessage());
			}
		}
*/		

		@Override
		protected void onPostExecute(ContactBean result) {
			dismissProgressDialog();
			displayResponse(result);
		}

	}
	
}
