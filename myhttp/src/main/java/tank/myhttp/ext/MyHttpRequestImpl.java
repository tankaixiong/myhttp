/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package tank.myhttp.ext;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.http.HttpRequestImpl;
import org.apache.mina.http.api.HttpRequest;

import tank.myhttp.api.IHttpRequestWrapper;

/**
 * 对原生的请求进行封装
 * 
 * @author tank
 *
 */
public class MyHttpRequestImpl implements IHttpRequestWrapper {

	private HttpRequestImpl reqeust;

	public MyHttpRequestImpl(HttpRequestImpl reqeust) {
		this.reqeust = reqeust;
	}

	public Map<String, String> getSimpleParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		String[] params = reqeust.getQueryString().split("&");
		if (params.length == 1) {
			return parameters;
		}
		for (int i = 0; i < params.length; i++) {
			String[] param = params[i].split("=");
			String name = param[0];
			String value = param.length == 2 ? param[1] : "";

			parameters.put(name, value);
		}
		return parameters;
	}

	public HttpRequestImpl getReqeust() {
		return reqeust;
	}

	public void setReqeust(HttpRequestImpl reqeust) {
		this.reqeust = reqeust;
	}

}
