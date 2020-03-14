package jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RkiCrawler {
	private static final String ROBERTKOCHINSTITUT = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
	
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

	public static void CrawlData() {
		try {
	        Document doc = Jsoup.connect(ROBERTKOCHINSTITUT).get();
	        Iterator<Element> iterator = doc.select("table tr").iterator();
	        WriteJson(iterator);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@SuppressWarnings("unchecked")
	private static void WriteJson(Iterator<Element> iterator) {
		// JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
        JSONObject obj = new JSONObject();
        JSONArray columnArray = SetGoogleFormatJsonColumnArray(new JSONArray());
        JSONArray rowArray = SetGoogleFormatJsonRowArray(new JSONArray(), iterator);
        obj.put("cols", columnArray);
        obj.put("rows", rowArray);
         
        //Write JSON file
        try (FileWriter file = new FileWriter("../germany_dataset.json")) {
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray SetGoogleFormatJsonColumnArray(JSONArray array) {
        JSONObject bundesland = SetGoogleFormatJsonColumnObject(new JSONObject(), "Bundesland", "string");
        JSONObject bestätiger = SetGoogleFormatJsonColumnObject(new JSONObject(), "Bestaetiger", "number");
        JSONObject tod = SetGoogleFormatJsonColumnObject(new JSONObject(), "Tod", "number");
        array.add(bundesland);
        array.add(bestätiger);
        array.add(tod);
		return array;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject SetGoogleFormatJsonColumnObject(JSONObject obj, String label, String type) {
		obj.put("id", "");
		obj.put("label", label);
		obj.put("pattern", "");
		obj.put("type", type);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	private static JSONArray SetGoogleFormatJsonRowArray(JSONArray array, Iterator<Element> iterator) {
        //remove header row
        iterator.next();
        while (iterator.hasNext()) {
        	Element row = iterator.next();
            Elements tds = row.select("td");
            if(tds.get(0).text().equals("Gesamt")) break;
            String bundesland = tds.get(0).text();
            String confirmedCount = tds.get(1).text().split(" ")[0];
            String deathCount = SetDeathCount(tds.get(1).text());
            array.add(SetGoogleFormatJsonRowObject(new JSONObject(), bundesland, confirmedCount, deathCount));
        }
		return array;
	}
	
	private static String SetDeathCount(String value) {
        if(value.split(" ").length == 2) 
        	return value.split(" ")[1].replace("(", "").replace(")", "");
        return "0";
	}
	
	@SuppressWarnings("unchecked")
	private static JSONObject SetGoogleFormatJsonRowObject(JSONObject jsonObject, String bundesland, String confirmedCount, String deathCount) {
        JSONArray jsonArray = new JSONArray();
        JSONObject bundeslandObj = new JSONObject();
        bundeslandObj.put("v", germanyHashMap.get(bundesland));
        bundeslandObj.put("f", ConfigureUmlaute(bundesland));
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
	
	private static String ConfigureUmlaute(String value) {
		return value.replace("ü", "ue").replace("ö", "oe").replace("ä", "ae").replace("ß", "sz");
	}
}
