google.charts.load('current', {'packages':['table']});
google.charts.setOnLoadCallback(drawTable);

function drawTable() {
	
  var jsonData = $.ajax({
    url: "https://raw.githubusercontent.com/haruabba/corona-aktuell/master/thueringen_dataset.json",
    dataType: "json",
    async: false,
  }).responseText;

  var thueringenData = new google.visualization.DataTable(jsonData);
  var thueringenTable = new google.visualization.Table(document.getElementById('thueringen_Table_div'));

  thueringenTable.draw(thueringenData, {showRowNumber: true});
}