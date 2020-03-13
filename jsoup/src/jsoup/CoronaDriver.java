package jsoup;

public class CoronaDriver {
	public static void main(String[] args) {
		final String ROBERTKOCHINSTITUT = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String BERLIN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSEN = "https://www.rki.de/DE/Content/InfAZ/N/Neuartiges_Coronavirus/Fallzahlen.html";
		//final String SACHSENANHALT = "https://verbraucherschutz.sachsen-anhalt.de/hygiene/infektionsschutz/infektionskrankheiten/coronavirus/";
		RKI.ParsingTable(ROBERTKOCHINSTITUT);
    }
}