package jsoup;

public class CoronaDriver {
	
	private static final String HTMLFILE = "../index.html";
	private static final String BERLINHTML = "../berlin.html";
	private static final String SACHSENHTML = "../sachsen.html";
	private static final String SACHSENANHALTHTML = "../sachsen-anhalt.html";

	public static void main(String[] args) {
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
		DataSynchronizer.updateGermanyCounterValues(SACHSENHTML);
		DataSynchronizer.updateGermanyCounterValues(SACHSENANHALTHTML);
	}
	
	private static void updateDifferenceValues() {
		DataSynchronizer.updateDifferenceValues(HTMLFILE);
		DataSynchronizer.updateGermanyDifferenceValues(BERLINHTML);
		DataSynchronizer.updateGermanyDifferenceValues(SACHSENHTML);
		DataSynchronizer.updateGermanyDifferenceValues(SACHSENANHALTHTML);
	}
	
	private static void updateDatetime() {
		DataSynchronizer.updateDatetime(HTMLFILE);
		DataSynchronizer.updateDatetime(BERLINHTML);
		DataSynchronizer.updateDatetime(SACHSENHTML);
		DataSynchronizer.updateDatetime(SACHSENANHALTHTML);
	}
}