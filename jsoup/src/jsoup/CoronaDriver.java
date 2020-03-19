package jsoup;

public class CoronaDriver {
	
	private static final String HTMLFILE = "../deutschland/index.html";
	private static final String BERLINHTML = "../berlin/index.html";
	private static final String SACHSENHTML = "../sachsen/index.html";
	private static final String SACHSENANHALTHTML = "../sachsen-anhalt/index.html";

	public static void main(String[] args) {
		WorldometerCrawler.crawlData();
		WorldometerCrawler.setCounterValues();
		RkiCrawler.crawlData();
		RkiCrawler.writeJson();
		SachsenCrawler.crawlData();
		SachsenCrawler.writeJson();
		SachsenAnhaltCrawler.crawlData();
		SachsenAnhaltCrawler.writeJson();
		updateHtmlFiles();
		System.out.println("Done");
    }
	
	private static void updateHtmlFiles() {
		updateCounterValues();
		updateDifferenceValues();
		updateDatetime();
	}
	
	private static void updateCounterValues() {
		DataSynchronizer.updateCounterValues(HTMLFILE, DataSynchronizer.getWorldCounterValues(), DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getWorldValueDifferences(), DataSynchronizer.getGermanyValueDifferences());
		DataSynchronizer.updateGermanyCounterValues(BERLINHTML);
		DataSynchronizer.updateCounterValues(SACHSENHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getSachsenCounterValues(), DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenValueDifferences());
		DataSynchronizer.updateCounterValues(SACHSENANHALTHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getSachsenAnhaltCounterValues(), DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenAnhaltValueDifferences());
	}
	
	private static void updateDifferenceValues() {
		DataSynchronizer.updateDifferenceValues(HTMLFILE, DataSynchronizer.getWorldValueDifferences(), DataSynchronizer.getGermanyValueDifferences());
		DataSynchronizer.updateGermanyDifferenceValues(BERLINHTML);
		DataSynchronizer.updateDifferenceValues(SACHSENHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenValueDifferences());
		DataSynchronizer.updateDifferenceValues(SACHSENANHALTHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenAnhaltValueDifferences());
	}
	
	private static void updateDatetime() {
		DataSynchronizer.updateDatetime(HTMLFILE);
		DataSynchronizer.updateDatetime(BERLINHTML);
		DataSynchronizer.updateDatetime(SACHSENHTML);
		DataSynchronizer.updateDatetime(SACHSENANHALTHTML);
	}
}