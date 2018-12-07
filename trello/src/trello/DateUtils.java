package trello;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import java.io.IOException;



public class DateUtils {
	
	public Date convertDate(Calendar today) {
	int day = today.get(Calendar.DAY_OF_WEEK);
	if(day == 6){
		return today.getTime();		
	}else if(day < 6) {
		today.add(Calendar.DATE, 6-day);  // number of days to add
	} else {
		today.add(Calendar.DATE, 6);
	}
	
	//System.out.println(today.getTime());
	return today.getTime();
	}
	
	
	public Calendar addDates(Calendar startDate) {
		startDate.add(Calendar.DATE, 7);
		return startDate;	
	}
	
	public ArrayList<String> printDates(Date startDate, Date maxDate) throws IOException {
		Calendar cal = Calendar.getInstance();
		DateFormat dFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);
		String[] sDate = new String[2];
		ArrayList<String> dateList=new ArrayList<String>();
		while(startDate.compareTo(maxDate) < 0) {
			cal.setTime(startDate);		
			Calendar a = addDates(cal);
			String dd = dFormat.format(a.getTime());
			sDate = dd.split(",");
			System.out.println('"' + sDate[0] + '"');
			dateList.add(sDate[0]);
			startDate = a.getTime();
		}	
		return dateList;
	}	
}
