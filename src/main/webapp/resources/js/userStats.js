$.urlParam = function(name){
    var results = new RegExp('[\\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    return results != null ? results[1] : -1;
};

$(document).ready(function(){
    var answersChartOptions = {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Percent of correct answers for questions with different difficulty'
        },
        xAxis: {
            categories: [],
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            max: 100,
            title: {
                text: 'Total percentage',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: '%'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true,
                    format: '{y}%'
                }
            }
        },

        credits: {
            enabled: false
        },
        series: [{
            name: 'Correct',
            data: [],
            color: '#1ab832'
        }]
    };
    var timeChartOptions = {
        title: {
            text: 'User results for test',
            x: -20 //center
        },
        xAxis: {
            categories: []
        },
        yAxis: {
            title: {
                text: 'Result'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '%'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            //name: 'Percentage',
            data: []
        }]
    };

    $.ajax({
        url: '/getstats?resultId=' + $.urlParam('resultId') + '&userId=' + $.urlParam('userId'),
        success: function(data) {
            $.each(data[0], function(index, value) {
                answersChartOptions.xAxis.categories.push(value.caption);
                answersChartOptions.series[0].data.push(value.val);
            });
            $('#answersChartContainer').highcharts(answersChartOptions);

            if(data[1].length > 1) {
                $.each(data[1], function(index, value) {
                    var dateStr = new Date(value.caption).toDateString();
                    timeChartOptions.xAxis.categories.push(dateStr);
                    timeChartOptions.series[0].data.push(value.val);
                });
                $('#timeChartContainer').highcharts(timeChartOptions);
            }
        }
    });
});
