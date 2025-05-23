/* 
 *	===================================
 * 
 * 	MODIFIED ==========================
 * 
 * 	===================================
*/

package com.topicexchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletUtil {

	/** Ensure that we will not create an instance of this class */
	private ServletUtil() {
		
	}
	
	public static <T> T getRequestData(Class<T> cls, HttpServletRequest request) throws IOException {
		// Get HTTP Request Body as (JSON) String
		final StringBuilder sb = new StringBuilder();
	    try(BufferedReader reader = request.getReader()){
	        String line;
	        while ((line = reader.readLine()) != null){
	        	sb.append(line);
	        }
	    }
	    final String payload = sb.toString();
	    // Convert (JSON) String to Object
	    final T t = jsonStrToObj(cls, payload);
		return t;
	}
	
	public static void sendResponseData(Object obj, HttpServletResponse response) throws IOException {
		// Set HTTP Response Content-Type
		response.setContentType("application/json"); // MODIFIED <===============================
		// Write JSON (String) in the HTTP Response Body
        PrintWriter out = response.getWriter();
        out.println(objToJsonStr(obj));
        out.flush();
        out.close();
	}
	
	public static String getPathFirstParam(HttpServletRequest request) {
		final String path = request.getPathInfo();
		if (path == null) return null;
		String[] tokens = path.split("/");
		for(String token : tokens) {
			if (token.trim().equals("")) continue;
			return token;
		}
		return null;
	}
	
	/* The following two methods use Gson Library */
	
	private static <T> T jsonStrToObj(Class<T> cls, String str) {
		return new Gson().fromJson(str, cls);
	}
	
	private static String objToJsonStr(Object obj) {
		return new Gson().toJson(obj);
	}

	/* ================== Code for debugging purposes has been removed ================== */
		
}
