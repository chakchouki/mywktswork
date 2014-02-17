package com.wkts.freight;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;

/**
 * @author WKTS
 * @version 0.1
 * @since 06/02/2014
 * 
 * This class is used to demo the freight posting and deletion operation
 *
 */
public class FreightPoster {

	private static final String ETC_ENDPOINT = "https://liv-connect.teleroute.com/easytconnect/MyFreightOffers/";
	private static final String ETC_LOGIN = "FXXW045928";
	private static final String ETC_PWD = "TELEROUTE";

	private static final String ETC_CONTENT_TYPE = "application/json";
	private static final String ETC_OFFER_ID_BASE = "TLR_";
	private static final String ETC_JSON_FILE_PATH = "src/post-test5.js";
	private static final Logger LOGGER = Logger.getLogger(FreightPoster.class
			.getName());
	private static final String LOG_FILEPATH = "src/LOGFILE.log";
	private static final Integer LOG_SIZE = 1000000;
	private static final Integer LOG_ROTATION_COUNT = 2;
	private static final String HTTP_PUT = "PUT";
	private static final String HTTP_DELETE = "DELETE";

	public static void main(String[] args) throws Exception {

		// LOGGER Set-up
		FileHandler fh = new FileHandler(LOG_FILEPATH, LOG_SIZE,
				LOG_ROTATION_COUNT);
		fh.setFormatter(new SimpleFormatter());
		LOGGER.addHandler(fh);

		FreightPoster https = new FreightPoster();

		String url = ETC_ENDPOINT + ETC_OFFER_ID_BASE
				+ System.currentTimeMillis() / 1000;

		https.sendPut(url);
		https.sendDelete(url);
	}

	/**
	 * Utility to send a PUT request
	 * 
	 * @param url
	 * @return
	 */

	private void sendPut(String url) {
		LOGGER.entering(FreightPoster.class.getName(), "sendPost");

		try {

			URL obj = new URL(url);
			HttpsURLConnection con;

			con = (HttpsURLConnection) obj.openConnection();

			// add request header
			con.setRequestMethod(HTTP_PUT);
			String encoding = Base64
					.encodeBase64String((ETC_LOGIN + ":" + ETC_PWD).getBytes());

			con.setRequestProperty("Authorization", "Basic " + encoding);
			con.setRequestProperty("Content-Type", ETC_CONTENT_TYPE);

			// Load payload into string
			String payLoad;
			payLoad = readFile(ETC_JSON_FILE_PATH, Charset.defaultCharset());
			LOGGER.log(Level.INFO, payLoad);

			// Send PUT request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(payLoad);
			wr.flush();
			wr.close();
			int responseCode = 0;
			LOGGER.log(Level.INFO, "Sending: " + con.getRequestMethod()
					+ " request to URL : " + url);

			responseCode = con.getResponseCode();

			LOGGER.log(Level.INFO, "responseCode: " + responseCode
					+ " | responseMessage: " + con.getResponseMessage());

			String inputLine;
			StringBuffer response = new StringBuffer();

			BufferedReader in;
			if ((responseCode == 200) || (responseCode == 201)) {
				in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

			}

			// print result
			LOGGER.log(Level.INFO, "Response: " + response.toString());

			// delete offer
			con.disconnect();
		} catch (MalformedURLException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		} catch (ProtocolException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		}

	}

	/**
	 * Utility to send a delete request
	 * 
	 * @param url
	 * @return
	 */

	private void sendDelete(String url) {
		try {

			URL obj = new URL(url);
			HttpsURLConnection con;

			con = (HttpsURLConnection) obj.openConnection();
			// add request header
			con.setRequestMethod(HTTP_DELETE);
			String encoding = Base64
					.encodeBase64String((ETC_LOGIN + ":" + ETC_PWD).getBytes());

			con.setRequestProperty("Authorization", "Basic " + encoding);
			con.setRequestProperty("Content-Type", ETC_CONTENT_TYPE);

			int responseCode = 0;
			LOGGER.log(Level.INFO, "Sending: " + con.getRequestMethod()
					+ " request to URL : " + url);
			responseCode = con.getResponseCode();

			LOGGER.log(Level.INFO, "responseCode: " + responseCode
					+ " | responseMessage: " + con.getResponseMessage());

		} catch (MalformedURLException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		} catch (ProtocolException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.getMessage());
		}
	}

	/**
	 * Utility to read a text file content into a string
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	private String readFile(String filePath, Charset encoding)
			throws IOException {
		LOGGER.entering(FreightPoster.class.getName(), "readFile");
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		LOGGER.exiting(FreightPoster.class.getName(), "readFile");

		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

}