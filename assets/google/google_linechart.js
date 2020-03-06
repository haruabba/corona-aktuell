google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawCurveTypes);

function drawCurveTypes() {
      var data = new google.visualization.arrayToDataTable(germanyDataSetProDay);

      var options = {
        chart: {
          title: 'Zeitraum',
          subtitle: '28.01.2020 - 05.03.2020'
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

      var chart = new google.charts.Line(document.getElementById('linechart_material'));
      chart.draw(data, google.charts.Line.convertOptions(options));
}