google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(drawTable);

function drawTable() {
	
  var jsonData = $.ajax({
    url: "https://raw.githubusercontent.com/haruabba/corona-aktuell/master/berlin_dataset.json",
    dataType: "json",
    async: false,
  }).responseText;

  var sachsenanhaltData = new google.visualization.DataTable(jsonData);
  var sachsenanhaltTable = new google.visualization.Table(document.getElementById('berlin_Table_div'));

  sachsenanhaltTable.draw(sachsenanhaltData, {showRowNumber: true});
}