package jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CoronaDriver {
	public static void main(String[] args) {
		final String HTMLFILE = "../index.html";
		final String ROBERTKOCHINSTITUT = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String BERLIN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSEN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSENANHALT = "https://verbraucherschutz.sachsen-anhalt.de/hygiene/infektionsschutz/infektionskrankheiten/coronavirus/";
		RKI.ParsingTable(ROBERTKOCHINSTITUT);
		UpdateDatetime(HTMLFILE);
		System.out.println("Done");
    }
	
	private static void UpdateDatetime(String url) {
		DateTimeFormatter datetime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");  
		LocalDateTime now = LocalDateTime.now();  
		File in = new File(url);
		try {
			Document doc = Jsoup.parse(in, null);
		    //a with href
		    Element link = doc.select("a").first(); 
		    link.text(String.format("Letztes Update: %s", datetime.format(now)));
		    FileWriter writer = new FileWriter(url, false);
		    writer.write(doc.html());
		    writer.flush();
		    writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}