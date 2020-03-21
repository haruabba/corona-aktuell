package jsoup;

public class CoronaDriver {
	
	private static final String DEUTSCHLANDHTML = "../deutschland/index.html";
	private static final String BERLINHTML = "../berlin/index.html";
	private static final String SACHSENHTML = "../sachsen/index.html";
	private static final String SACHSENANHALTHTML = "../sachsen-anhalt/index.html";
	private static final String THUERINGENTHTML = "../thueringen/index.html";


	public static void main(String[] args) {
		WorldometerCrawler.crawlData();
		WorldometerCrawler.setWorldAndGermanyCounterValues();
		RkiCrawler.crawlData();
		RkiCrawler.writeJson();
		SachsenCrawler.crawlData();
		SachsenCrawler.writeJson();
		SachsenAnhaltCrawler.crawlData();
		SachsenAnhaltCrawler.writeJson();
		ThueringenCrawler.crawlData();
		ThueringenCrawler.writeJson();
		updateHtmlFiles();
		System.out.println("Done");
    }
	
	private static void updateHtmlFiles() {
		updateCounterValues();
		updateDifferenceValues();
		updateDatetime();
	}
	
	private static void updateCounterValues() {
		DataSynchronizer.updateCounterValues(DEUTSCHLANDHTML, DataSynchronizer.getWorldCounterValues(), DataSynchronizer.getGermanyCounterValues());
		DataSynchronizer.updateGermanyCounterValues(BERLINHTML);
		DataSynchronizer.updateCounterValues(SACHSENHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getSachsenCounterValues());
		DataSynchronizer.updateCounterValues(SACHSENANHALTHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getSachsenAnhaltCounterValues());
		DataSynchronizer.updateCounterValues(THUERINGENTHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getThueringenCounterValues());
	}
	
	private static void updateDifferenceValues() {
		DataSynchronizer.updateDifferenceValues(DEUTSCHLANDHTML, DataSynchronizer.getWorldValueDifferences(), DataSynchronizer.getGermanyValueDifferences());
		DataSynchronizer.updateGermanyDifferenceValues(BERLINHTML);
		DataSynchronizer.updateDifferenceValues(SACHSENHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenValueDifferences());
		DataSynchronizer.updateDifferenceValues(SACHSENANHALTHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenAnhaltValueDifferences());
		DataSynchronizer.updateDifferenceValues(THUERINGENTHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getThueringenValueDifferences());
	}
	
	private static void updateDatetime() {
		DataSynchronizer.updateDatetime(DEUTSCHLANDHTML);
		DataSynchronizer.updateDatetime(BERLINHTML);
		DataSynchronizer.updateDatetime(SACHSENHTML);
		DataSynchronizer.updateDatetime(SACHSENANHALTHTML);
		DataSynchronizer.updateDatetime(THUERINGENTHTML);
	}
}