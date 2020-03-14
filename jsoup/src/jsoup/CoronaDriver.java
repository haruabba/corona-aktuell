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
	private static final String BERLINHTML = "../berlin.html";

	public static void main(String[] args) {
		//final String BERLIN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSEN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSENANHALT = "https://verbraucherschutz.sachsen-anhalt.de/hygiene/infektionsschutz/infektionskrankheiten/coronavirus/";
		WorldometerCrawler.crawlData();
		WorldometerCrawler.setCounterValues();
		RkiCrawler.crawlData();
		RkiCrawler.writeJson();
		updateHtmlFiles();
		System.out.println("Done");
    }
	
	private static void updateHtmlFiles() {
		updateCounterValues();
		updateDifferenceValues();
		updateDatetime();
	}
	
	private static void updateCounterValues() {
		DataSynchronizer.updateCounterValues(HTMLFILE);
		DataSynchronizer.updateGermanyCounterValues(BERLINHTML);
	}
	
	private static void updateDifferenceValues() {
		DataSynchronizer.updateDifferenceValues(HTMLFILE);
		DataSynchronizer.updateGermanyDifferenceValues(BERLINHTML);
	}
	
	private static void updateDatetime() {
		DataSynchronizer.updateDatetime(HTMLFILE);
		DataSynchronizer.updateDatetime(BERLINHTML);
	}
}