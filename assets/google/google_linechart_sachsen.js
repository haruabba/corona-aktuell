google.charts.load('current', {packages: ['corechart', 'line']});
google.charts.setOnLoadCallback(drawCurveTypes);

function drawCurveTypes() {
      var data = new google.visualization.arrayToDataTable(sachsenDataSetProDay);

      var optionsBerlin = {
        chart: {
          title: 'Zeitraum',
          subtitle: '03.03.2020 - 10.03.2020'
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
      
      var chartSachsen = new google.charts.Line(document.getElementById('linechart_material_sachsen'));
      chartSachsen.draw(data, google.charts.Line.convertOptions(optionsBerlin));
}