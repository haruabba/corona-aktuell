google.charts.load('current', {
  'packages':['geochart']
});
google.charts.setOnLoadCallback(drawMarkersMap);


function drawMarkersMap() {
  
  var jsonData = $.ajax({
    url: "https://raw.githubusercontent.com/haruabba/corona-aktuell/master/germany_dataset.json",
    dataType: "json",
    async: false,
  }).responseText;

  var germanyMap = new google.visualization.DataTable(jsonData);

   var germanyOptions = {
    displayMode: 'regions',
	  region: 'DE',
	  resolution: 'provinces',
    colorAxis: {minValue: 0, maxValue: 300, colors: ['#fff', '#FFE4E1', '#A52A2A']},
    legend: 'none'
  };

  var countryChart = new google.visualization.GeoChart(document.getElementById('regions_div'));
  countryChart.draw(germanyMap, germanyOptions);
}