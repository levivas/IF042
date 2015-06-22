$.urlParam = function(name){
    var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
    return results != null ? results[1] : -1;
};

var chart;

$(document).ready(function() {
    var options = {
        chart: {
            renderTo: 'chartContainer',
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: -10,
                beta: 25,
                depth: 70
            }
        },
        title: {
            text: decodeURI($.urlParam('test'))
        },
        subtitle: {
            text: 'Average percent of correct answers for test'
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: [],
            title: {
                text: 'Groups'
            }
        },
        yAxis: {
            min: 0,
            max: 100,
            title: {
                text: 'Percentage'
            },
            opposite: true,
            tooltip: {
                valueSuffix: '%'
            }
        },
        series: [{
            name: 'Average correct',
            data: [/*2, 3, null, 4, 0, 5, 1, 4, 6, 3*/]
        }]
    };

    $.ajax({
        url: '/getTestStats?testId=' + $.urlParam('testId'),
        success: function(data) {
            $.each(data, function(index, value) {
                options.xAxis.categories.push(value.caption);
                options.series[0].data.push(value.val);
            });
            chart = new Highcharts.Chart(options);
            // Add mouse events for rotation
            $(chart.container).bind('mousedown.hc touchstart.hc', function (e) {
                e = chart.pointer.normalize(e);

                var posX = e.pageX,
                    posY = e.pageY,
                    alpha = chart.options.chart.options3d.alpha,
                    beta = chart.options.chart.options3d.beta,
                    newAlpha,
                    newBeta,
                    sensitivity = 5; // lower is more sensitive

                $(document).bind({
                    'mousemove.hc touchdrag.hc': function (e) {
                        // Run beta
                        newBeta = beta + (posX - e.pageX) / sensitivity;
                        newBeta = Math.min(100, Math.max(-100, newBeta));
                        chart.options.chart.options3d.beta = newBeta;

                        // Run alpha
                        newAlpha = alpha + (e.pageY - posY) / sensitivity;
                        newAlpha = Math.min(100, Math.max(-100, newAlpha));
                        chart.options.chart.options3d.alpha = newAlpha;

                        chart.redraw(false);
                    },
                    'mouseup touchend': function () {
                        $(document).unbind('.hc');
                    }
                });
            });
        }
    });
});