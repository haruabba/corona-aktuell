google.charts.load('current', {'packages': ['geochart']});
google.charts.setOnLoadCallback(drawMarkersMap);

function drawMarkersMap() {
var data = google.visualization.arrayToDataTable([
['City', 'Collaborator'],
['Detroit', "text here"],
['Cleveland', "text here"],
['Joondalup', "text here"],
['Wanaka', "text here"]
['Liverpool', "text here"],
['Styria', "text here"],
['Edwardsville', "text here"],
['Austin', "text here"],
['Houston', "text here"],
['Stockholm', "text here"],
]);

var options = {
region: 'world',
displayMode: 'markers',
backgroundColor: '#252426',
colorAxis: {colors: ['blue']},
magnifyingGlass: {enable: false},
defaultColor: '#f1910e',
enableRegionInteractivity: 'true',
};

var chart = new google.visualization.GeoChart(document.getElementById('italia_div'));
chart.draw(data, options);
};
