google.charts.load('current', {'packages':['bar']});
google.charts.setOnLoadCallback(drawStuff);

  function drawStuff() {
    var germanyData = new google.visualization.arrayToDataTable(germanyDataSet);

    var options = {
        chart: {
            title: 'Status',
            subtitle: 'Insgesamt 847 wurden infiziert (Quelle: Robert Koch-Instituts)'
          },
        height: 400,
        chartArea: {left: 0, top: 0, width: "100%", height: "100%"},
        bar: {groupWidth: "100%"},
        legend: {position: 'bottom'},
        bars: 'horizontal' // Required for Material Bar Charts.
      };
    var chart = new google.charts.Bar(document.getElementById('top_x_div'));
    chart.draw(germanyData, options);
    window.addEventListener('resize', drawStuff, false);
  };