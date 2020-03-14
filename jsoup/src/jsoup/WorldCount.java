package jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WorldCount {
	private static final String WORLDOMETER = "https://www.worldometers.info/coronavirus/";

	public static List<String> ParsingValues() {
		List<String> counterValues = new ArrayList<String>();
		try {
	        Document doc = Jsoup.connect(WORLDOMETER).get();
	        Iterator<Element> mainCounterIterator = doc.getElementsByClass("maincounter-number").iterator();
	        while (mainCounterIterator.hasNext()) {
	        	Element row = mainCounterIterator.next();
	        	CoronaDriver.getCounterValues().add(row.text().replace(",", ""));
	        }
	        Iterator<Element> tableIterator = doc.select("table tr").iterator();
	        tableIterator.next();
	        while (tableIterator.hasNext()) {
	        	Element row = tableIterator.next();
	            Elements tds = row.select("td");
	            if(tds.get(0).text().equals("Germany")) {
	            	//total cases
		        	CoronaDriver.getCounterValues().add(tds.get(1).text().replace(",", ""));
		        	//new cases
		        	CoronaDriver.getValueDifferences().add(tds.get(2).text().replace("+", ""));
		        	//total deaths
		        	CoronaDriver.getCounterValues().add(tds.get(3).text());
		        	//new deaths
		        	CoronaDriver.getValueDifferences().add(tds.get(4).text().replace("+", ""));
		        	//total recovered
		        	CoronaDriver.getCounterValues().add(tds.get(5).text());
		        	break;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        return counterValues;
	}
}
