google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(drawTable);

function drawTable() {
	
  var jsonData = $.ajax({
    url: "https://raw.githubusercontent.com/haruabba/corona-aktuell/master/germany_dataset.json",
    dataType: "json",
    async: false,
  }).responseText;

  var worldData = google.visualization.arrayToDataTable(worldDataSet);
  var germanyData = new google.visualization.DataTable(jsonData);

  var worldTable = new google.visualization.Table(document.getElementById('worldTable_div'));
  var germanyTable = new google.visualization.Table(document.getElementById('germanyTable_div'));

  worldTable.draw(worldData, {showRowNumber: true});
  germanyTable.draw(germanyData, {showRowNumber: true});
}