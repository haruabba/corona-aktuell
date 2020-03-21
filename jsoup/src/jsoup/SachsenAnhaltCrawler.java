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

public class SachsenAnhaltCrawler {
	
	private static final String SOURCE = "https://verbraucherschutz.sachsen-anhalt.de/hygiene/infektionsschutz/infektionskrankheiten/coronavirus/";
	private static final String DATASET = "../sachsen_anhalt_dataset.json";
	private static final String[] PREVVALUES = new String[] {"217","0","0"};
	private static Iterator<Element> tableIterator;

	public static void crawlData() {
		try {	
	        Document doc = Jsoup.connect(SOURCE).get();
	        setTableIterator(doc.select("table tr").iterator());
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
        //remove header row
        iterator.next();
        while (iterator.hasNext()) {
        	Element row = iterator.next();
            Elements tds = row.select("td");
            if(tds.get(0).text().equals("Gesamtzahl ST")) {
            	setSachsenAnhaltCounter(tds.get(1).text());
            	DataSynchronizer.setDifferenceValues(PREVVALUES, DataSynchronizer.getSachsenAnhaltCounterValues(), DataSynchronizer.getSachsenAnhaltValueDifferences());
            	break;
            }
            String stadt = setStadtName(tds.get(0).text());
            String confirmedCount = tds.get(1).text();
            String deathCount = setDeathCount(tds.get(1).text());
            array.add(setGoogleFormatJsonRowObject(new JSONObject(), stadt, confirmedCount, deathCount));
        }
		return array;
	}
	
	private static String setDeathCount(String value) {
        if(value.split(" ").length == 2) 
        	return value.split(" ")[1].replace("(", "").replace(")", "");
        return "0";
	}
	
	private static void setSachsenAnhaltCounter(String tableRow) {
        String newCounter = tableRow;
        DataSynchronizer.getSachsenAnhaltCounterValues().add(newCounter);
        DataSynchronizer.getSachsenAnhaltCounterValues().add("1");
        DataSynchronizer.getSachsenAnhaltCounterValues().add("0");
	}
	
	private static String setStadtName(String rowElement) {
		return rowElement.substring(3);
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