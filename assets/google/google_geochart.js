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

  var worldMap = google.visualization.arrayToDataTable(worldDataSet);
  var germanyMap = new google.visualization.DataTable(jsonData);
   var worldOptions = {
    colorAxis: {minValue: 0, maxValue: 1000, colors: ['#FFE4E1', '#A52A2A']},
    legend: 'none'
   };

   var germanyOptions = {
    displayMode: 'regions',
	  region: 'DE',
	  resolution: 'provinces',
    colorAxis: {minValue: 0, maxValue: 150, colors: ['#fff', '#FFE4E1', '#A52A2A']},
    legend: 'none'
  };

  var worldChart = new google.visualization.GeoChart(document.getElementById('welt_div'));
  var countryChart = new google.visualization.GeoChart(document.getElementById('regions_div'));
  worldChart.draw(worldMap, worldOptions);
  countryChart.draw(germanyMap, germanyOptions);
}