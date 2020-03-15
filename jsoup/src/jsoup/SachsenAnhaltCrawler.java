package jsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.StringJoiner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SachsenAnhaltCrawler {
	
	private static final String MSSACHSENANHALT = "https://ms.sachsen-anhalt.de/themen/gesundheit/aktuell/coronavirus/";
	private static final String SACHSENANHALTDATASET = "../sachsen_anhalt_dataset.json";
	private static Iterator<Element> tableIterator;


	public static void crawlData() {
		try {	
	        Document doc = Jsoup.connect(MSSACHSENANHALT).get();
	        setTableIterator(doc.select(".MsoNormal").iterator());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@SuppressWarnings("unchecked")
	public static void writeJson() {
		// JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject obj = new JSONObject();
        JSONArray columnArray = setGoogleFormatJsonColumnArray(new JSONArray());
        JSONArray rowArray = setGoogleFormatJsonRowArray(new JSONArray());
        obj.put("cols", columnArray);
        obj.put("rows", rowArray);
        //Write JSON file
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(SACHSENANHALTDATASET), StandardCharsets.UTF_8)) {
        	writer.write(obj.toJSONString());
        	writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray setGoogleFormatJsonColumnArray(JSONArray array) {
        JSONObject bundesland = setGoogleFormatJsonColumnObject(new JSONObject(), "Stadt", "string");
        JSONObject bestätiger = setGoogleFormatJsonColumnObject(new JSONObject(), "Bestätiger", "number");
        JSONObject tod = setGoogleFormatJsonColumnObject(new JSONObject(), "Tod", "number");
        array.add(bundesland);
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
	private static JSONArray setGoogleFormatJsonRowArray(JSONArray array) {
		String[] tableRows;
		try {
			tableRows = parsingHtmlString();
	        for(String row : tableRows) {
	            if (row.contains("Gesamtergebnis")) {
	            	setSachsenAnhaltCounter(row);
	            	break;
	            }
	            String[] tableElements = row.split(" ");
	            String stadt = setStadtName(tableElements);
	            String confirmedCount = setConfirmedCount(tableElements);
	            String deathCount = setDeathCount(tableElements);
	            array.add(setGoogleFormatJsonRowObject(new JSONObject(), stadt, confirmedCount, deathCount));
	        }
		} catch (NullPointerException e) {
            e.printStackTrace();
        }
		return array;
	}
	
	private static String[] parsingHtmlString() {
		String[] tableRows = null;
        while (getTableIterator().hasNext()) {
        	Element row = getTableIterator().next();
            if(!row.text().contains("LK")) continue;
            else{
            	tableRows = row.html().split("<br>");
            	break;
            }
        }
        return tableRows;
	}
	
	private static void setSachsenAnhaltCounter(String tableRow) {
        String[] tableElements = tableRow.split(" ");
        String newCounter = tableElements[1].replace("<b>", "").replace("</b>", "");
        DataSynchronizer.getSachsenAnhaltCounterValues().add(newCounter);
        DataSynchronizer.getSachsenAnhaltCounterValues().add("0");
        DataSynchronizer.getSachsenAnhaltCounterValues().add("0");
	}
	
	private static String setStadtName(String[] rowElements) {
		StringJoiner  joiner = new StringJoiner(" ");
		for (int i = 1; i < rowElements.length - 1; i++) {
			joiner.add(rowElements[i]);
		}
		return joiner.toString();
	}
	
	private static String setConfirmedCount(String[] rowElements) {
		return rowElements[rowElements.length - 1];
	}
	
	private static String setDeathCount(String[] rowElements) {
		return "0";
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject setGoogleFormatJsonRowObject(JSONObject jsonObject, String stadt, String confirmedCount, String deathCount) {
        JSONArray jsonArray = new JSONArray();
        JSONObject stadtObj = new JSONObject();
        stadtObj.put("v", stadt);
        stadtObj.put("f", stadt);
        JSONObject confirmedCountObj = new JSONObject();
        confirmedCountObj.put("v", confirmedCount);
        JSONObject deathCountObj = new JSONObject();
        deathCountObj.put("v", deathCount);
        jsonObject.put("c", jsonArray);
        jsonArray.add(stadtObj);
        jsonArray.add(confirmedCountObj);
        jsonArray.add(deathCountObj);
		return jsonObject;
	}

	public static Iterator<Element> getTableIterator() {
		return tableIterator;
	}

	public static void setTableIterator(Iterator<Element> tableIterator) {
		SachsenAnhaltCrawler.tableIterator = tableIterator;
	}
}
