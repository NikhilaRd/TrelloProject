package trello;

import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HttpURLConnectionExample {
	private static final String USER_AGENT = "Mozilla/5.0";
	/*Get the API key and generate token values and pass them as parameters to sendGet method
	 * 
	 */
	public static void main(String[] args) throws Exception {
		String key = "79ee671e7d2d59ee6d8ef468d14dafec";
		String token = "33c622eb4ec04a5a8f9027c3e40005b60cc5ace27ac941ec9d572b97b44eec90";
		sendGet(key,token);
	}

	/** HTTP GET request
	 * 
	 * @throws Exception
	 */
	/* This method accepts the key and token parameters and uses the URL s to extract the information from Trello API
	 * The output from extracted information is printed into results.txt file
	 */
	private static void sendGet(String key, String token) throws Exception {

		List<Card> cards = new ArrayList<Card>();
		FileWriter  writer  = new FileWriter("result.txt");
		PrintWriter pw = new PrintWriter(writer);
		// optional default is GET
		//add request header
		//URL1
		/*
		 * using url1 we are extracting the information of all the members of the board
		 * response1 has the list of all the members of the board
		 * jsonObj1 has all the members of the board in json format
		 * from the json Object we can extract the information of full names of all the members of the board
		 * from the full names, using split function we can get the first names of the board members
		 */
		String url1 = String.format("https://api.trello.com/1/boards/iy6JW6CT/members/?key=%s&token=%s", key, token);
		URL obj1 = new URL(url1);
		HttpURLConnection con1 = (HttpURLConnection) obj1.openConnection();
		con1.setRequestMethod("GET");
		con1.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader in1 = new BufferedReader(
		        new InputStreamReader(con1.getInputStream()));
		String inputLine1;
		StringBuilder jsonString1 = new StringBuilder();
		while ((inputLine1 = in1.readLine()) != null) {
			jsonString1.append(inputLine1);	
		}
		JSONObject jsonObj1 = new JSONObject("{ \"jsonList1\": "+jsonString1.toString()+"}");
		
		List<String> firstName = new ArrayList<>();
		pw.println("set Emp/");
		JSONArray jsonArray1 = (org.json.JSONArray)jsonObj1.get("jsonList1");
		for(int ij = 0;ij<jsonArray1.length();ij++) {
			JSONObject jsonObject1 = (JSONObject)jsonArray1.get(ij);		
			String fullName = (String)jsonObject1.get("fullName");
			String[] fName = fullName.split(" ");
			System.out.println('"' + fName[0] + '"');
			pw.println('"' + fName[0] + '"');
			firstName.add(fName[0]);
		}
		displayField(pw);
		
		
		//URL2
		/*
		 * using url2 we can extract the information of board names
		 *  jsonObj2 has all the members of the board in json format
		 * from the json Object we can extract the information of names of all the boards
		 */
		String url2 = String.format("https://api.trello.com/1/boards/iy6JW6CT?fields=name,url&key=%s&token=%s",key,token);
		URL obj2 = new URL(url2);
		HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();
		con2.setRequestMethod("GET");
		con2.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader in2 = new BufferedReader(
		        new InputStreamReader(con2.getInputStream()));
		String inputLine2;
		StringBuilder jsonString2 = new StringBuilder();
		while ((inputLine2 = in2.readLine()) != null) {
			jsonString2.append(inputLine2);	
		}
		String jString2 = jsonString2.toString().replace("[","").replace("]", "");
		JSONObject jsonObj2 = new JSONObject(jString2);
		String jsonObj2String2 = jsonObj2.get("name").toString();
		String [] tempArray2;
		tempArray2 = jsonObj2String2.split(" -");
		System.out.println('"' + tempArray2[0] + '"');
		pw.println("set Project/");
		pw.println('"' + tempArray2[0] + '"');
		displayField(pw);
		
		
		//URL3
		/*
		* using url3 we can extract the information of running time, actual time, card number
		* create instances of class type Card
		* create a list of type Card and all these instances to this list
		* Iterate through this list and extract the required information of required fields from this list 
		  instead of repeatedly hitting the URL
		*/
		String url = String.format("https://api.trello.com/1/boards/iy6JW6CT/cards?fields=name,url,due&key=%s&token=%s",key,token);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		StringBuilder jsonString = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			jsonString.append(inputLine);	
		}
		jsonString.toString().replace("[","").replace("]", "");
		JSONObject jsonObj = new JSONObject("{ \"jsonList\": "+jsonString.toString()+"}");
		int lengthOfJson = ((org.json.JSONArray) jsonObj.get("jsonList")).length();
		String q = "";
		String [] cardNum = new String[lengthOfJson];
		String [] cardNameArray = new String[lengthOfJson];
		Date [] dateArray = new Date[lengthOfJson];
		Calendar tday = Calendar.getInstance();
		DateUtils c = new DateUtils();
		Date tdayDate = c.convertDate(tday);
		Date maxDate = new Date();
		DateFormat dFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
		String dd = dFormat.format(tdayDate);
		String[] startDate = new String[2];
		startDate = dd.split(",");
		
		
		for(int i = 0;i<((org.json.JSONArray)jsonObj.get("jsonList")).length();i++) {
			Object o = ((org.json.JSONArray)jsonObj.get("jsonList")).get(i);
			JSONObject jsonObject = (JSONObject)o;
			String name = (String)jsonObject.get("name");
			String due = (String)jsonObject.get("due");
			String string = due;
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);
			Date date = format.parse(string);
			dateArray[i] = date;
			if(maxDate.compareTo(date)<1) {
				maxDate = date;
			}
			int j = name.indexOf("(");
			int k = name.indexOf(")");
			String s = name.substring(j+1, k);
			int l = name.indexOf("-");
			String cardName = name.substring(k+2, l);
			cardNameArray[i] = cardName;
			String p = name.substring(l+2);
	        String[] tempArray;
	        String delimiter = " ";
	        tempArray = p.split(delimiter);
			q = tempArray[1];
			cardNum[i] = q;
			String r = tempArray[3];
			int t = p.indexOf("[");
			int u = p.indexOf("]");
			String v = "running";
			if(t > 0 && u > 0) {
				v = p.substring(t+1, u);
			}
			double esTime = Double.parseDouble(s);
			if(t > 0 && u > 0) {
				double acTime = Double.parseDouble(v);
				cards.add(new Card(esTime,q,r,acTime,date));	
			}
			else {
			//	
				cards.add(new Card(esTime,q,r,date));		
			}	
		}
		
		/*
		 * Extract the information of the Card number by iterating through the cards List using getter method
		 */
		
		pw.println("set Cards/C1*C5/");
		pw.println("alias(Cards, DepCards)");
		for(int cardNumIterator = 0; cardNumIterator < cardNum.length; cardNumIterator++) {
			System.out.println("Card " + cards.get(cardNumIterator).getCardNumber());
			pw.println("Card " + cards.get(cardNumIterator).getCardNumber());
		}
		displayField(pw);
		
		/*
		 * set the period values from P1 to P16 for 16 weeks
		 */
		
		ArrayList<String> dateList = c.printDates(tdayDate,maxDate);
		pw.println("set Period/P1*P4/");
		
		for(int i = 1; i < dateList.size()+2; i ++) {
			System.out.println("P" + i + "\t" + "-" + "\t" + "week" + i);
			pw.println("P" + i + "\t" + "-" + "\t" + "week" + i);
		}
		displayField(pw);
		
		/*
		 * get the dates in required format from printDates method
		 */
		
		pw.println("set		WkEnding/");
		System.out.println('"' + startDate[0] + '"');
		pw.println('"' + startDate[0] + '"');
		//ArrayList<String> dateList = c.printDates(tdayDate,maxDate);
		
		for(int i = 0; i < dateList.size(); i ++) {
			System.out.println('"' + dateList.get(i) + '"');
			pw.println('"' + dateList.get(i) + '"');
		}
		displayField(pw);
		
		/*
		 * This set defines all tuples of period to week. P1 should be the first week in the set. In this case, 
		   P1 is the first Friday from the current date. Then get the list of all dates from dateList array and 
		   display the period and its corresponding date
		 */
		
		pw.println("set		Per2WkEnding(Period, WkEnding)/");
		System.out.println('"' + "P" + 1 + '"' + "." + '"' + startDate[0] + '"');
		pw.println('"' + "P" + 1 + '"' + "." + '"' + startDate[0] + '"');
		for(int i = 0; i < dateList.size(); i++) {
			System.out.println('"' + "P" + (i+2) + '"' + "." + '"' + dateList.get(i) + '"');	
			pw.println('"' + "P" + (i+2) + '"' + "." + '"' + dateList.get(i) + '"');
		}
		
		displayField(pw);
		
		/*
		 * display the period from p1 to p16 and their period value
		 */
		pw.println("parameter PerValue(Period)/");
		for(int i = 1; i <= dateList.size() + 1; i++) {
			System.out.println('"' + "P" + i + '"' + "=" + i);	
			pw.println('"' + "P" + i + '"' + "=" + i);
		}
		displayField(pw);	
			
		/*
		 * Extract the information of title of the board, cardNumber and names of the members of the board by iterating through the cards List using getter method
		 */
		
		pw.println("set ProjAssign2Emp(Project,Cards,Emp)/");
		
		for(int iterator = 0; iterator < cards.size(); iterator++) {
			System.out.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + firstName.get(0) + '"');
			pw.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + firstName.get(0) + '"');
		}
		
		displayField(pw);
		/*
		 * Extract the information of title of the board, cardNumber and estimated time by iterating through the cards List using getter method
		 */
		pw.println("parameter ProjAssign2Hrs(Project,Cards)/");
		
		for(int iterator = 0; iterator < cards.size(); iterator++) {
			System.out.println('"' +tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + cards.get(iterator).getEstimatedTime());
			pw.println('"' +tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + cards.get(iterator).getEstimatedTime());
		}
		displayField(pw);
		
		/*
		 * Initialize number of hours as 40 and allocate these hours to all the periods
		 */
		
		int hours = 40;
		System.out.println('"' + firstName.get(0) + '"'+ '"' + "P" + 1 + '"' + "=" + hours);
		pw.println("parameter EmpMaxHrs2Per(Emp,Period)/");
		
		for(int i = 0; i < dateList.size()+1; i++) {
			int j = i + 1;
			System.out.println('"' + firstName.get(0) + '"' + '"' + "P" + j + '"' + "=" + hours);	
			pw.println('"' + firstName.get(0) + '"' + '"' + "P" + j + '"' + "=" + hours);
		}
		for(int i = 0; i < dateList.size()+1; i++) {
			int j = i + 1;
			System.out.println('"' + firstName.get(1) + '"' + '"' + "P" + j + '"' + "=" + hours);	
			pw.println('"' + firstName.get(1) + '"' + '"' + "P" + j + '"' + "=" + hours);
		}
		
		displayField(pw);
		
		/*
		 * Extract the information of title of the board, cardNumber and by iterating through the cards List using getter method
		 * Check for the Dependent cards, if a card depends on some other card then the dependency is one otherwise the dependency is 0
		 */
		pw.println("parameter DueDate(Project,Cards)/");
		for(Card card: cards) {
			Date cardDueDate = new Date(card.getDueDate().getTime());
			Calendar cardDueDateCal = Calendar. getInstance();
			cardDueDateCal.setTime(cardDueDate);
			c.convertDate(cardDueDateCal);
			
			tday.set(Calendar.HOUR_OF_DAY, 0);
			tday.set(Calendar.MINUTE, 0);
			tday.set(Calendar.SECOND, 0);
			tday.set(Calendar.MILLISECOND, 0);
			cardDueDateCal.set(Calendar.HOUR_OF_DAY, 0);
			cardDueDateCal.set(Calendar.MINUTE, 0);
			cardDueDateCal.set(Calendar.SECOND, 0);
			cardDueDateCal.set(Calendar.MILLISECOND, 0);
			//System.out.println(tday.getTime()+"tday");
			//System.out.println(cardDueDateCal.getTime()+"cal");
			if(cardDueDateCal.equals(tday)) {
				System.out.println('"' + tempArray2[0] + '"' + "." + '"' + card.getCardNumber() + '"' + "=" + "1");
				pw.println('"' + tempArray2[0] + '"' + "." + '"' + card.getCardNumber() + '"' + "=" + "1");
			}else {
				System.out.println('"' + tempArray2[0] + '"' + "." + '"' + card.getCardNumber() + '"' + "=" + "0");
				pw.println('"' + tempArray2[0] + '"' + "." + '"' + card.getCardNumber() + '"' + "=" + "0");
			}
		}
		/*for(int iterator = 0; iterator < cards.size(); iterator++) {
			if(cards.get(iterator).getDependsOn() != null) {
			System.out.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "1");
			pw.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "1");
			}
			else {
				System.out.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "0");
				pw.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "0");
			}
		}*/
		displayField(pw);
		
		/*
		 * Extract the information of cardNumber by iterating through the cards List using getter method
		 * Check for the Dependent cards, if a card depends on some other card then the dependency is one otherwise the dependency is 0
		 */
		pw.println("parameter PossDepend(Cards,DepCards)/");
		for(int iterator = 0; iterator < cards.size(); iterator++) {
			if(cards.get(iterator).getDependsOn() != null) {
			System.out.println('"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "1");
			pw.println('"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "1");
			}
			else {
				System.out.println('"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "0");
				pw.println('"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "0");
			}
			}
		displayField(pw);
		/*
		 * Extract the information of board title, cardNumber by iterating through the cards List using getter method
		 * Check for the Dependent cards, if a card depends on some other card then the dependency is one otherwise the dependency is 0
		 */
		pw.println("parameter ProjDepend(Project,Cards,DepCards)/");
		for(int iterator = 0; iterator < cards.size(); iterator++) {
			if(cards.get(iterator).getDependsOn() != null) {
			System.out.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "1");
			pw.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "1");
			}
			else {
				System.out.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "0");
				pw.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "=" + "0");		
			}
			}
		displayField(pw);
		/*
		 * Extract the cardName by iterating through the cardNameArray
		 */
		
		pw.println("set CardName/");
		
		for(int iterator = 0; iterator < cards.size(); iterator++) {
			System.out.println('"' + cardNameArray[iterator] + '"');
			pw.println('"' + cardNameArray[iterator] + '"');
		}
		displayField(pw);
		/*
		 * Extract the information of board title, cardNumber by iterating through the cards List using getter method and cardName by 
		   iterating through the cardNameArray
		 * Check for the Dependent cards, if a card depends on some other card then the dependency is one otherwise the dependency is 0
		 */
		
		pw.println("set ProjCard2Name(Project,Cards,CardName)/");
		for(int iterator = 0; iterator < cards.size(); iterator++) {
			System.out.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cardNameArray[iterator] + '"');
			pw.println('"' + tempArray2[0] + '"' + "." + '"' + cards.get(iterator).getCardNumber() + '"' + "." + '"' + cardNameArray[iterator] + '"');
		}	
		pw.close();
		in.close();
	}
	
	/*
	 * method to print the pattern
	 * called this method as a separator to improve the readability 
	 */
	public static void displayField(PrintWriter pw) {
		System.out.println("************************************");
		pw.println("************************************");	
	}
}
