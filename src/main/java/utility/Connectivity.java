package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class Connectivity {
	private static final String USER_AGENT = "Mozilla/5.0";
	private static HttpURLConnection connection = null;

	String server = new PropertyHandler().getProperty("server.Adddress");

	/**
	 * POST HTTP request to server with custom parameters
	 * 
	 * @param params
	 * @throws IOException
	 */
	public JSONObject sendPOST(String params) throws IOException {
		// check the connection
		if (connection == null || HttpURLConnection.HTTP_BAD_GATEWAY == HttpURLConnection.HTTP_BAD_GATEWAY
				|| HttpURLConnection.HTTP_GATEWAY_TIMEOUT == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
			connection = InitializeConnection();
		}

		OutputStream oStream = connection.getOutputStream();
		oStream.write(params.getBytes());
		oStream.flush();
		oStream.close();

		int responseCode = connection.getResponseCode();
		System.out.println("POST RES Code = " + responseCode);

		// Check if Success
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader inReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputline;
			StringBuffer response = new StringBuffer();

			while ((inputline = inReader.readLine()) != null) {
				response.append(inputline);
			}
			inReader.close();
			System.out.println("Response -> " + response.toString());
			return new JSONObject(response.toString());
		} else {
			System.out.println("POST request failed");
			return new JSONObject(HttpURLConnection.HTTP_BAD_REQUEST);
		}
	}

	/**
	 * Initialize connectivity between client and server
	 * 
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection InitializeConnection() throws IOException {
		URL url = new URL(server);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("User-Agent", USER_AGENT);
		connection.setDoOutput(true);

		return connection;
	}
}
