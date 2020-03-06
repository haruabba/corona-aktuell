google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(drawTable);

function drawTable() {
  var worldData = google.visualization.arrayToDataTable(worldDataSet);
  var germanyData = google.visualization.arrayToDataTable(germanyDataSet);

  var worldTable = new google.visualization.Table(document.getElementById('worldTable_div'));
  var germanyTable = new google.visualization.Table(document.getElementById('germanyTable_div'));

  worldTable.draw(worldData, {showRowNumber: true});
  germanyTable.draw(germanyData, {showRowNumber: true});
}