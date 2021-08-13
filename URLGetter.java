package nets150_hw5;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLGetter {
	private URL url;
	private HttpURLConnection httpConnection;
	
	/**
	 * Creates a URL from a string.
	 * Opens the connection to be used later.
	 * @param url the url to get information from
	 */
	public URLGetter(String url) {
		try {
			this.url = new URL(url);
						
			URLConnection connection = this.url.openConnection();
			httpConnection = (HttpURLConnection) connection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will print the status codes from the connection.
	 */
	public void printStatusCode() {
		try {
			int code = httpConnection.getResponseCode();
			String message = httpConnection.getResponseMessage();
			
			System.out.println(code + " : " + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method will get the HTML contents and returns an arraylist
	 * @return the arraylist of strings from the HTML page.
	 */
	public ArrayList<String> getContents() {
		ArrayList<String> contents = new ArrayList<String>();
		
		Scanner in;
		
		try {
			in = new Scanner(httpConnection.getInputStream());
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				contents.add(line);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
	
	/**
     * Read the html of the webpage with a scanner, store in a variable
     * @return
     */
	public String getContentsString() {
		String contents = "";
		
		Scanner in;
		
		try {
			in = new Scanner(httpConnection.getInputStream());
			
			while (in.hasNextLine()) {
				String line = in.nextLine();
				contents = contents + line;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
}
