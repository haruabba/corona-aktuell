google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawCurveTypes);

function drawCurveTypes() {
	
	var jsonData = $.ajax({
    url: "https://raw.githubusercontent.com/haruabba/corona-aktuell/master/germany_datetime.json",
    dataType: "json",
    async: false,
  }).responseText;

  var data = new google.visualization.DataTable(jsonData);

  var optionsGermany = {
    chart: {
      title: 'Coronavirus im Zeitraum',
      subtitle: 'Quelle: WHO Coronavirus (2019-nCoV) situation reports'
    },
    hAxis: {
      title: 'Datum'
    },
    vAxis: {
      title: 'Fälle'
    },
    height: 400,
    chartArea: {left: 0, top: 0, width: "100%", height: "100%"}
  };


  var chartGermany = new google.charts.Line(document.getElementById('linechart_material'));
  chartGermany.draw(data, google.charts.Line.convertOptions(optionsGermany));
}