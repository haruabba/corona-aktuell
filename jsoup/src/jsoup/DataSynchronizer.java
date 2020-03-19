package jsoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataSynchronizer {
	
	private static List<String> worldCounterValues = new ArrayList<String>();
	private static List<String> germanyCounterValues = new ArrayList<String>();
	private static List<String> worldValueDifferences = new ArrayList<String>();
	private static List<String> germanyValueDifferences = new ArrayList<String>();
	private static List<String> sachsenCounterValues = new ArrayList<String>();
	private static List<String> sachsenValueDifferences = new ArrayList<String>();
	private static List<String> sachsenAnhaltCounterValues = new ArrayList<String>();
	private static List<String> sachsenAnhaltValueDifferences = new ArrayList<String>();

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
	
	public static void updateCounterValues(String fileName, List<String> firstCounter, List<String> secondCounter, List<String> firstDifference, List<String> secondDifference) {
		File in = new File(fileName);
		try {	
			Document doc = Jsoup.parse(in, null);
	        Elements elements = doc.select("font.fh5co-counter"); // a with href
	        Iterator<Element> counterIterator = doc.select("span[data-to]").iterator();
        	int i = 0;
	        while (counterIterator.hasNext()) {
	        	Element row = counterIterator.next();
	        	String difference;
	        	if (i < 3) {
	        		if(i == 2) {
	        			elements.get(0).text(calculateDeathRate(firstCounter.get(0), firstCounter.get(1)));
	        		}
	        		difference = CalculateValueDifference(row.attr("data-to"), firstCounter.get(i));
	        		firstDifference.add(i, difference);
		        	row.attr("data-to", firstCounter.get(i));
	        	} else {
	        		if(i == 4) {
	        			elements.get(1).text(calculateDeathRate(secondCounter.get(0), secondCounter.get(1)));
	        		}
	        		difference = CalculateValueDifference(row.attr("data-to"), secondCounter.get(i - 3));
	        		secondDifference.add(i - 3, difference);
		        	row.attr("data-to", secondCounter.get(i - 3));
	        	}
	        	i++;
	        }
	        updateHtmlDocument(fileName, doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String calculateDeathRate(String value1, String value2) {
		float result = Float.valueOf(value2) / Float.valueOf(value1) * 100;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		return df.format(result).toString().replace(",", ".").concat("%");
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
	
	public static void updateDifferenceValues(String fileName, List<String> firstDifference, List<String> secondDifference) {
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
		        	diff.text(String.format("(+%s)", firstDifference.get(j)));
	        	}
	        	else {
		        	diff.text(String.format("(+%s)", secondDifference.get(j - 3)));
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
	public static List<String> getSachsenAnhaltCounterValues() {
		return sachsenAnhaltCounterValues;
	}

	public static void setSachsenAnhaltCounterValues(List<String> sachsenAnhaltCounterValues) {
		DataSynchronizer.sachsenAnhaltCounterValues = sachsenAnhaltCounterValues;
	}

	public static List<String> getSachsenAnhaltValueDifferences() {
		return sachsenAnhaltValueDifferences;
	}

	public static void setSachsenAnhaltValueDifferences(List<String> sachsenAnhaltValueDifferences) {
		DataSynchronizer.sachsenAnhaltValueDifferences = sachsenAnhaltValueDifferences;
	}

	public static List<String> getSachsenCounterValues() {
		return sachsenCounterValues;
	}

	public static void setSachsenCounterValues(List<String> sachsenCounterValues) {
		DataSynchronizer.sachsenCounterValues = sachsenCounterValues;
	}

	public static List<String> getSachsenValueDifferences() {
		return sachsenValueDifferences;
	}

	public static void setSachsenValueDifferences(List<String> sachsenValueDifferences) {
		DataSynchronizer.sachsenValueDifferences = sachsenValueDifferences;
	}
}
