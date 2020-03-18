google.charts.load('current', {'packages':['bar']});
google.charts.setOnLoadCallback(drawStuff);

  function drawStuff() {

    var jsonData = $.ajax({
      url: "https://raw.githubusercontent.com/haruabba/corona-aktuell/master/sachsen_dataset.json",
      dataType: "json",
      async: false,
    }).responseText;

    var sachsenData = new google.visualization.DataTable(jsonData);

    var options = {
        chart: {
            title: 'Status',
            subtitle: 'Quelle: SMS Sachsen'
          },
        height: 400,
        chartArea: {left: 0, top: 0, width: "100%", height: "100%"},
        bar: {groupWidth: "100%"},
        legend: {position: 'bottom'},
        bars: 'horizontal' // Required for Material Bar Charts.
      };
    var chart = new google.charts.Bar(document.getElementById('sachsen_div'));
    chart.draw(sachsenData, options);
    window.addEventListener('resize', drawStuff, false);
  };