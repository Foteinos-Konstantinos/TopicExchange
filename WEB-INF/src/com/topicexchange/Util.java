/* 
 *	===================================
 * 
 * 	MODIFIED ==========================
 * 
 * 	===================================
 * 	
 * 	Original code by prof.
 * 	Efthymios Chondrogiannis
*/

package com.topicexchange;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	/** 
	 * Ensure that we will not create an instance of this class 
	 */
	private Util() {
		
	}
	
	/**
	 * SHA (Secure Hash Algorithm) 256
	 * @see <a href="https://www.baeldung.com/sha-256-hashing-java">Read more...</a>
	 */
	public static String getHash256(final String str) {
		if (str == null) return null;
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(encodedhash);
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException("getHash256() problem !", e);
		}
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	/* ================== Code for debugging purposes has been removed ================== */
	
	public static String operationFailed(String msg){
		return operationReport("Operation failed",msg, "failure");
	}

	public static String operationFailed(){
		return operationFailed("");
	}

	public static String operationSucceeded(String msg){
		return operationReport("Operation succeeded",msg, "success");
	}

	public static String operationSucceeded(){
		return operationSucceeded("");
	}

	private static String operationReport(String msg_large, String msg_small, String type){
		return """
<!DOCTYPE html>
<html lang="en">
    <head>
        <script type="text/javascript" src="./scripts/rendering.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded",()=>{
                renderElements(".");
            })
        </script>
    </head>
    <body>
        
        <div id="main-content">
            <div class="%s">
				<h1>%s</h1>
				<span>%s<span>
            </div>
        </div>

    </body>
</html>				
				""".formatted(type, msg_large, msg_small);
	}
	
}
