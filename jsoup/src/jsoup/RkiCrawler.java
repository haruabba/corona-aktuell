package jsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RkiCrawler {
	private static final String SOURCE = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
	private static final String DATASET = "../germany_dataset.json";
	private static Iterator<Element> tableIterator;
	
    private static final HashMap<String, String> germanyHashMap = new HashMap<>();
    static {
    	germanyHashMap.put("Baden-Württemberg", "DE-BW");
    	germanyHashMap.put("Bayern", "DE-BY");
    	germanyHashMap.put("Berlin", "DE-BE");
    	germanyHashMap.put("Brandenburg", "DE-BB");
    	germanyHashMap.put("Bremen", "DE-HB");
    	germanyHashMap.put("Hamburg", "DE-HH");
    	germanyHashMap.put("Hessen", "DE-HE");
    	germanyHashMap.put("Mecklenburg-Vorpommern", "DE-MV");
    	germanyHashMap.put("Niedersachsen", "DE-NI");
    	germanyHashMap.put("Nordrhein-Westfalen", "DE-NW");
    	germanyHashMap.put("Rheinland-Pfalz", "DE-RP");
    	germanyHashMap.put("Saarland", "DE-SL");
    	germanyHashMap.put("Sachsen", "DE-SN");
    	germanyHashMap.put("Sachsen-Anhalt", "DE-ST");
    	germanyHashMap.put("Schleswig-Holstein", "DE-SH");
    	germanyHashMap.put("Thüringen", "DE-TH");
    }

	public static void crawlData() {
		try {	
	        Document doc = Jsoup.connect(SOURCE).get();
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
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(DATASET), StandardCharsets.UTF_8)) {
        	writer.write(obj.toJSONString());
        	writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray setGoogleFormatJsonColumnArray(JSONArray array) {
        JSONObject bundesland = setGoogleFormatJsonColumnObject(new JSONObject(), "Bundesland", "string");
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
	private static JSONArray setGoogleFormatJsonRowArray(JSONArray array, Iterator<Element> iterator) {
        //remove header row
        while (iterator.hasNext()) {
        	Element row = iterator.next();
            Elements tds = row.select("td");
            if(tds.get(0).text().equals("Gesamt")) break;
            String bundesland = tds.get(0).text();
            String confirmedCount = tds.get(1).text().replace(".", "");
            String deathCount = tds.get(4).text();
            array.add(setGoogleFormatJsonRowObject(new JSONObject(), bundesland, confirmedCount, deathCount));
        }
		return array;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject setGoogleFormatJsonRowObject(JSONObject jsonObject, String bundesland, String confirmedCount, String deathCount) {
        JSONArray jsonArray = new JSONArray();
        JSONObject bundeslandObj = new JSONObject();
        bundeslandObj.put("v", germanyHashMap.get(bundesland));
        bundeslandObj.put("f", bundesland);
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
		RkiCrawler.tableIterator = tableIterator;
	}
}
