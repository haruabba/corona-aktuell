package jsoup;

public class CoronaDriver {
	
	private static final String DEUTSCHLANDHTML = "../deutschland/index.html";
	private static final String BERLINHTML = "../berlin/index.html";
	private static final String SACHSENHTML = "../sachsen/index.html";
	private static final String SACHSENANHALTHTML = "../sachsen-anhalt/index.html";
	private static final String THUERINGENHTML = "../thueringen/index.html";
	private static final String MECKLENBURGVORPOMMERNHTML = "../mecklenburg-vorpommern/index.html";


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
		MecklenburgVorpommernCrawler.crawlData();
		MecklenburgVorpommernCrawler.writeJson();
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
		DataSynchronizer.updateCounterValues(THUERINGENHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getThueringenCounterValues());
		DataSynchronizer.updateCounterValues(MECKLENBURGVORPOMMERNHTML, DataSynchronizer.getGermanyCounterValues(), DataSynchronizer.getMecklenburgvorpommernCounterValues());
	}
	
	private static void updateDifferenceValues() {
		DataSynchronizer.updateDifferenceValues(DEUTSCHLANDHTML, DataSynchronizer.getWorldValueDifferences(), DataSynchronizer.getGermanyValueDifferences());
		DataSynchronizer.updateGermanyDifferenceValues(BERLINHTML);
		DataSynchronizer.updateDifferenceValues(SACHSENHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenValueDifferences());
		DataSynchronizer.updateDifferenceValues(SACHSENANHALTHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getSachsenAnhaltValueDifferences());
		DataSynchronizer.updateDifferenceValues(MECKLENBURGVORPOMMERNHTML, DataSynchronizer.getGermanyValueDifferences(), DataSynchronizer.getMecklenburgvorpommernValueDifferences());
	}
	
	private static void updateDatetime() {
		DataSynchronizer.updateDatetime(DEUTSCHLANDHTML);
		DataSynchronizer.updateDatetime(BERLINHTML);
		DataSynchronizer.updateDatetime(SACHSENHTML);
		DataSynchronizer.updateDatetime(SACHSENANHALTHTML);
		DataSynchronizer.updateDatetime(THUERINGENHTML);
		DataSynchronizer.updateDatetime(MECKLENBURGVORPOMMERNHTML);
	}
}