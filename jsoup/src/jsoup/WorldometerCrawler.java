package jsoup;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WorldometerCrawler {
	private static final String SOURCE = "https://www.worldometers.info/coronavirus/";
	private static Iterator<Element> mainCounterIterator;
	private static Iterator<Element> tableIterator;

	public static void crawlData() {
		try {
	        Document doc = Jsoup.connect(SOURCE).get();
	        setMainCounterIterator(doc.getElementsByClass("maincounter-number").iterator());
	        setTableIterator(doc.select("table tr").iterator());
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void setWorldAndGermanyCounterValues() {
		getTableIterator().next();
        while (getTableIterator().hasNext()) {
        	Element row = getTableIterator().next();
            Elements tds = row.select("td");
            if(tds.get(0).text().equals("Germany")) {
            	//total cases
            	DataSynchronizer.getGermanyCounterValues().add(tds.get(1).text().replace(",", ""));
	        	//new cases
            	DataSynchronizer.getGermanyValueDifferences().add(tds.get(2).text());
	        	//total deaths
            	DataSynchronizer.getGermanyCounterValues().add(tds.get(3).text());
	        	//new deaths
            	DataSynchronizer.getGermanyValueDifferences().add(tds.get(4).text());
	        	//total recovered
            	DataSynchronizer.getGermanyCounterValues().add(tds.get(5).text());
            }
            if(tds.get(0).text().equals("Total:")) {
            	//total cases
            	DataSynchronizer.getWorldCounterValues().add(tds.get(1).text().replace(",", ""));
	        	//new cases
            	DataSynchronizer.getWorldValueDifferences().add("+".concat(tds.get(2).text()));
	        	//total deaths
            	DataSynchronizer.getWorldCounterValues().add(tds.get(3).text().replace(",", ""));
	        	//new deaths
            	DataSynchronizer.getWorldValueDifferences().add("+".concat(tds.get(4).text()));
	        	//total recovered
            	DataSynchronizer.getWorldCounterValues().add(tds.get(5).text().replace(",", ""));
            	break;
            }
        }
	}

	public static Iterator<Element> getMainCounterIterator() {
		return mainCounterIterator;
	}

	public static void setMainCounterIterator(Iterator<Element> mainCounterIterator) {
		WorldometerCrawler.mainCounterIterator = mainCounterIterator;
	}

	public static Iterator<Element> getTableIterator() {
		return tableIterator;
	}

	public static void setTableIterator(Iterator<Element> tableIterator) {
		WorldometerCrawler.tableIterator = tableIterator;
	}
}