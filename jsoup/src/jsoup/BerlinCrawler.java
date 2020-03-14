package jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BerlinCrawler {
	//private static final String BERLIN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
	private static final String BERLINHTML = "../berlin.html";
	
	public static void CrawlData() {
		//UpdateCounterValues();
		//UpdateDifferenceValues();
	}
	
	// todo update counters for berlin
	private static void UpdateCounterValues() {
		File in = new File(BERLINHTML);
		try {	
			Document doc = Jsoup.parse(in, null);
	        Iterator<Element> counterIterator = doc.select("span[data-to]").iterator();
        	int i = 0;
	        while (counterIterator.hasNext()) {
	        	if(i == 3) break;
	        	Element row = counterIterator.next();
	        	row.attr("data-to", CoronaDriver.getCounterValues().get(i++));
	        }
	        CoronaDriver.UpdateHtmlDocument(BERLINHTML, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void UpdateDifferenceValues() {
		File in = new File(BERLINHTML);
		try {	
			Document doc = Jsoup.parse(in, null);	        
	        Iterator<Element> differenceIterator = doc.select("span.fh5co-counter-label").iterator();
	        int j = 0;
	        while (differenceIterator.hasNext()) {
	        	if (j == 3) break;
	        	Element diff = differenceIterator.next();
	        	diff.text(String.format("(+%s)", CoronaDriver.getValueDifferences().get(j++)));
	        }
	        CoronaDriver.UpdateHtmlDocument(BERLINHTML, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
