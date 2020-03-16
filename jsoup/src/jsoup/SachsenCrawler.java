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

public class SachsenCrawler {
	private static final String SMSSACHSEN = "https://www.sms.sachsen.de/coronavirus.html";
	private static final String SACHSENDATASET = "../sachsen_dataset.json";
	private static Iterator<Element> tableIterator;
	
	public static void crawlData() {
		try {	
	        Document doc = Jsoup.connect(SMSSACHSEN).get();
	        setTableIterator(doc.select("tbody tr").iterator());
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
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(SACHSENDATASET), StandardCharsets.UTF_8)) {
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
        array.add(stadt);
        array.add(bestätiger);
        array.add(tod);
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
        while (iterator.hasNext()) {
        	Element row = iterator.next();
            Elements tds = row.select("td");
            if (tds.get(0).text().contains("Gesamtzahl")) {
            	setSachsenCounter(tds.get(1).html());
            	break;
            }
            String stadt = setStadtName(tds.get(0).text());
            String confirmedCount = setConfirmedCount(tds.get(1).text(), tds.get(2).text());
            String deathCount = setDeathCount(tds.get(1).text());
            array.add(setGoogleFormatJsonRowObject(new JSONObject(), stadt, confirmedCount, deathCount));
        }
		return array;
	}
	
	private static void setSachsenCounter(String tableRow) {
        String[] tableElements = tableRow.split("<strong>");
        String newCounter = tableElements[1].replace("</strong>", "");
        DataSynchronizer.getSachsenCounterValues().add(newCounter);
        DataSynchronizer.getSachsenCounterValues().add("0");
        DataSynchronizer.getSachsenCounterValues().add("0");
	}
	
	private static String setStadtName(String tableRow) {
		return tableRow.replace("Landeshauptstadt ", "").replace("Stadt ", "").replace("Landkreis", "LK");
	}
	
	private static String setConfirmedCount(String firstElement, String secondElement) {
		if (!firstElement.isBlank() && secondElement.isBlank())
			return firstElement;
		if (firstElement.isBlank() && !secondElement.isBlank())
			return secondElement;
		/*
		if (!firstElement.isBlank() && !secondElement.isBlank())
			return Integer.toString(Integer.valueOf(firstElement) + Integer.valueOf(secondElement));
		*/
		return "0";
	}
	
	private static String setDeathCount(String value) {
        if(value.split(" ").length == 2) 
        	return value.split(" ")[1].replace("(", "").replace(")", "");
        return "0";
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject setGoogleFormatJsonRowObject(JSONObject jsonObject, String stadt, String confirmedCount, String deathCount) {
        JSONArray jsonArray = new JSONArray();
        JSONObject bundeslandObj = new JSONObject();
        bundeslandObj.put("v", stadt);
        bundeslandObj.put("f", stadt);
        JSONObject confirmedCountObj = new JSONObject();
        confirmedCountObj.put("v", confirmedCount);
        JSONObject deathCountObj = new JSONObject();
        deathCountObj.put("v", deathCount);
        jsonObject.put("c", jsonArray);
        jsonArray.add(bundeslandObj);
        jsonArray.add(confirmedCountObj);
        jsonArray.add(deathCountObj);
		return jsonObject;
	}
	
	public static Iterator<Element> getTableIterator() {
		return tableIterator;
	}
	public static void setTableIterator(Iterator<Element> tableIterator) {
		SachsenCrawler.tableIterator = tableIterator;
	}
}
