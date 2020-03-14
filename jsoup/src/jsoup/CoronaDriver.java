package jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CoronaDriver {
	private static final String HTMLFILE = "../index.html";
	private static List<String> counterValues = new ArrayList<String>();
	private static List<String> valueDifferences = new ArrayList<String>();


	public static void main(String[] args) {
		//final String BERLIN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSEN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSENANHALT = "https://verbraucherschutz.sachsen-anhalt.de/hygiene/infektionsschutz/infektionskrankheiten/coronavirus/";
		WorldCount.ParsingValues();
		RKI.ParsingValues();
		UpdateCounterValues();
		UpdateDifferenceValues();
		UpdateDatetime();
		System.out.println("Done");
    }
	
	private static void UpdateDatetime() {
		DateTimeFormatter datetime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");  
		LocalDateTime now = LocalDateTime.now();  
		File in = new File(HTMLFILE);
		try {	
			Document doc = Jsoup.parse(in, null);
		    //a with href
		    Element link = doc.select("a").first(); 
		    link.text(String.format("Letztes Update: %s", datetime.format(now)));
	        UpdateHtmlDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void UpdateCounterValues() {
		File in = new File(HTMLFILE);
		try {	
			Document doc = Jsoup.parse(in, null);
	        Iterator<Element> counterIterator = doc.select("span[data-to]").iterator();
        	int i = 0;
	        while (counterIterator.hasNext()) {
	        	Element row = counterIterator.next();
	        	if (i < 3) valueDifferences.add(CalculateValueDifference(row.attr("data-to"), counterValues.get(i)));
	        	if (i == 5) valueDifferences.add(2, CalculateValueDifference(row.attr("data-to"), counterValues.get(i)));
	        	row.attr("data-to", counterValues.get(i++));
	        }
	        UpdateHtmlDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void UpdateDifferenceValues() {
		File in = new File(HTMLFILE);
		try {	
			Document doc = Jsoup.parse(in, null);	        
	        Iterator<Element> differenceIterator = doc.select("span.fh5co-counter-label").iterator();
	        int j = 0;
	        while (differenceIterator.hasNext()) {
	        	Element diff = differenceIterator.next();
	        	if (j == 3) differenceIterator.hasNext();
	        	if (j == 6) break;
	        	diff.text(String.format("(+%s)", valueDifferences.get(j++)));
	        }
	        UpdateHtmlDocument(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void UpdateHtmlDocument(Document doc) {
		try(FileWriter writer = new FileWriter(HTMLFILE, false)){
		    writer.write(doc.html());
		    writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String CalculateValueDifference(String valuePrev, String valueCurr) {
		return Integer.toString(Integer.valueOf(valueCurr) - Integer.valueOf(valuePrev));
	}

	public static List<String> getCounterValues() {
		return counterValues;
	}

	public static void setCounterValues(List<String> counterValues) {
		CoronaDriver.counterValues = counterValues;
	}

	public static List<String> getValueDifferences() {
		return valueDifferences;
	}

	public static void setValueDifferences(List<String> valueDifferences) {
		CoronaDriver.valueDifferences = valueDifferences;
	}
}