google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawCurveTypes);

function drawCurveTypes() {
      var berlinData = new google.visualization.arrayToDataTable(berlinDataSetProDay);

      var optionsBerlin = {
        chart: {
          title: 'Zeitraum',
          subtitle: '02.03.2020 - 08.03.2020'
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
      
      var chartBerlin = new google.charts.Line(document.getElementById('linechart_material_berlin'));
      chartBerlin.draw(berlinData, google.charts.Line.convertOptions(optionsBerlin));
}