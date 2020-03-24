package jsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ThueringenCrawler {
	private static final String SOURCE = "https://www.landesregierung-thueringen.de/corona-bulletin/";
	private static final String DATASET = "../thueringen_dataset.json";
	private static final String[] PREVVALUES = new String[] {"263","1"};
	private static Iterator<Element> tableIterator;
	
	public static void crawlData() {
		try {	
	        Document doc = Jsoup.connect(SOURCE).get();
	        setTableIterator(doc.select("table>tbody").iterator());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@SuppressWarnings("unchecked")
	public static void writeJson() {
		// JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject obj = new JSONObject();
        JSONArray columnArray = setGoogleFormatJsonColumnArray(new JSONArray());
        JSONArray rowArray = setGoogleFormatJsonRowArray(new JSONArray(), getTableIterator());
        obj.put("cols", columnArray);
        obj.put("rows", rowArray);
         
        //Write JSON file
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(DATASET), StandardCharsets.UTF_8)) {
        	writer.write(obj.toJSONString());
        	writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray setGoogleFormatJsonColumnArray(JSONArray array) {
        JSONObject stadt = setGoogleFormatJsonColumnObject(new JSONObject(), "Stadt", "string");
        JSONObject bestätiger = setGoogleFormatJsonColumnObject(new JSONObject(), "Bestätiger", "number");
        JSONObject tod = setGoogleFormatJsonColumnObject(new JSONObject(), "Tod", "number");
        JSONObject genesen = setGoogleFormatJsonColumnObject(new JSONObject(), "Genesen", "number");
        array.add(stadt);
        array.add(bestätiger);
        array.add(tod);
        array.add(genesen);
		return array;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject setGoogleFormatJsonColumnObject(JSONObject obj, String label, String type) {
		obj.put("id", "");
		obj.put("label", label);
		obj.put("pattern", "");
		obj.put("type", type);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray setGoogleFormatJsonRowArray(JSONArray array, Iterator<Element> iterator) {
    		iterator.next();
        	Element element = iterator.next();
            Elements rows = element.select("tr");
            for(Element row : rows) {
            	Element th = row.select("th").first();
            	Elements tds = row.select("td");
            	if(th.text().equals("Summe:")) {
            		setThueringenCounter(tds);
                	DataSynchronizer.setDifferenceValues(PREVVALUES, DataSynchronizer.getThueringenCounterValues(), DataSynchronizer.getThueringenValueDifferences());
                	break;
            	}
                String stadt = th.text();
                String confirmedCount = setConfirmedCount(tds.get(1).text());
                String deathCount = setDeathCount(tds.get(5).text());
                String genesenCount = setGenesenCount(tds.get(6).text());
                array.add(setGoogleFormatJsonRowObject(new JSONObject(), stadt, confirmedCount, deathCount, genesenCount));
            }
		return array;
	}
	
	private static void setThueringenCounter(Elements tableDatas) {
        DataSynchronizer.getThueringenCounterValues().add(tableDatas.get(1).text());
        DataSynchronizer.getThueringenCounterValues().add(tableDatas.get(5).text());
        DataSynchronizer.getThueringenCounterValues().add(tableDatas.get(6).text());
	}
	
	
	private static String setConfirmedCount(String value) {
		if(value.isBlank()) return "0";
        return value;
	}
	
	private static String setDeathCount(String value) {
		if(value.isBlank()) return "0";
        return value;
	}
	
	private static String setGenesenCount(String value) {
		if(value.isBlank()) return "0";
        return value;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject setGoogleFormatJsonRowObject(JSONObject jsonObject, String stadt, String confirmedCount, String deathCount, String genesenCount) {
        JSONArray jsonArray = new JSONArray();
        JSONObject bundeslandObj = new JSONObject();
        bundeslandObj.put("v", stadt);
        bundeslandObj.put("f", stadt);
        JSONObject confirmedCountObj = new JSONObject();
        confirmedCountObj.put("v", confirmedCount);
        JSONObject deathCountObj = new JSONObject();
        deathCountObj.put("v", deathCount);
        JSONObject genesenCountObj = new JSONObject();
        genesenCountObj.put("v", genesenCount);
        jsonObject.put("c", jsonArray);
        jsonArray.add(bundeslandObj);
        jsonArray.add(confirmedCountObj);
        jsonArray.add(deathCountObj);
        jsonArray.add(genesenCountObj);
		return jsonObject;
	}
	
	public static Iterator<Element> getTableIterator() {
		return tableIterator;
	}
	public static void setTableIterator(Iterator<Element> tableIterator) {
		ThueringenCrawler.tableIterator = tableIterator;
	}
}
