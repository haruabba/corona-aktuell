package jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DataSynchronizer {
	
	private static List<String> worldCounterValues = new ArrayList<String>();
	private static List<String> germanyCounterValues = new ArrayList<String>();
	private static List<String> worldValueDifferences = new ArrayList<String>();
	private static List<String> germanyValueDifferences = new ArrayList<String>();
	
	public static void updateDatetime(String fileName) {
		DateTimeFormatter datetime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");  
		LocalDateTime now = LocalDateTime.now();  
		File in = new File(fileName);
		try {	
			Document doc = Jsoup.parse(in, null);
		    //a with href
		    Element link = doc.select("a").first(); 
		    link.text(String.format("Letztes Update: %s", datetime.format(now)));
	        updateHtmlDocument(fileName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateGermanyCounterValues(String fileName) {
		File in = new File(fileName);
		try {	
			Document doc = Jsoup.parse(in, null);
	        Iterator<Element> counterIterator = doc.select("span[data-to]").iterator();
        	int i = 0;
	        while (counterIterator.hasNext()) {
	        	Element row = counterIterator.next();
	        	if (i == 3) break;
	        	row.attr("data-to", getGermanyCounterValues().get(i));
	        	i++;
	        }
	        updateHtmlDocument(fileName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateCounterValues(String fileName) {
		File in = new File(fileName);
		try {	
			Document doc = Jsoup.parse(in, null);
	        Iterator<Element> counterIterator = doc.select("span[data-to]").iterator();
        	int i = 0;
	        while (counterIterator.hasNext()) {
	        	Element row = counterIterator.next();
	        	String difference;
	        	if (i < 3) {
	        		difference = CalculateValueDifference(row.attr("data-to"), getWorldCounterValues().get(i));
		        	getWorldValueDifferences().add(i, difference);
		        	row.attr("data-to", getWorldCounterValues().get(i));
	        	} else {
	        		difference = CalculateValueDifference(row.attr("data-to"), getGermanyCounterValues().get(i - 3));
		        	getGermanyValueDifferences().add(i - 3, difference);
		        	row.attr("data-to", getGermanyCounterValues().get(i - 3));
	        	}
	        	i++;
	        }
	        updateHtmlDocument(fileName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateGermanyDifferenceValues(String fileName) {
		File in = new File(fileName);
		try {	
			Document doc = Jsoup.parse(in, null);	        
	        Iterator<Element> differenceIterator = doc.select("span.fh5co-counter-label").iterator();
	        int j = 0;
	        while (differenceIterator.hasNext()) {
	        	Element diff = differenceIterator.next();
	        	if (j == 3) break;
	        	diff.text(String.format("(+%s)", getGermanyValueDifferences().get(j)));
	        	j++;
	        }
	        updateHtmlDocument(fileName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateDifferenceValues(String fileName) {
		File in = new File(fileName);
		try {	
			Document doc = Jsoup.parse(in, null);	        
	        Iterator<Element> differenceIterator = doc.select("span.fh5co-counter-label").iterator();
	        int j = 0;
	        while (differenceIterator.hasNext()) {
	        	Element diff = differenceIterator.next();
	        	if (j == 3) differenceIterator.hasNext();
	        	if (j == 6) break;
	        	if (j < 3) {
		        	diff.text(String.format("(+%s)", getWorldValueDifferences().get(j)));
	        	}
	        	else {
		        	diff.text(String.format("(+%s)", getGermanyValueDifferences().get(j - 3)));
	        	}
	        	j++;
	        }
	        updateHtmlDocument(fileName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateHtmlDocument(String fileName, Document doc) {
		try(Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8)){
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

	public static List<String> getWorldCounterValues() {
		return worldCounterValues;
	}

	public static void setWorldCounterValues(List<String> worldCounterValues) {
		DataSynchronizer.worldCounterValues = worldCounterValues;
	}

	public static List<String> getGermanyCounterValues() {
		return germanyCounterValues;
	}

	public static void setGermanyCounterValues(List<String> germanyCounterValues) {
		DataSynchronizer.germanyCounterValues = germanyCounterValues;
	}

	public static List<String> getGermanyValueDifferences() {
		return germanyValueDifferences;
	}

	public static void setGermanyValueDifferences(List<String> germanyValueDifferences) {
		DataSynchronizer.germanyValueDifferences = germanyValueDifferences;
	}

	public static List<String> getWorldValueDifferences() {
		return worldValueDifferences;
	}

	public static void setWorldValueDifferences(List<String> worldValueDifferences) {
		DataSynchronizer.worldValueDifferences = worldValueDifferences;
	}
}
