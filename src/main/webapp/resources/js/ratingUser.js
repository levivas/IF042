/**
 * Created by aivanettc on 5/12/2014.
 */

$(document).ready(function () {
        drawChart();
    });


function drawChart() {
    var categ = [];
    var percents = [];
    var group = +$('#group_select').val();
    var requestUrl = '/rating/getCategoriesJSON';
    var page = $('.active');
    requestUrl += '?page=' + (page.length > 0 ? +page.text() - 1 : 0);
    if (group)
        requestUrl += '&group=' + group;
    $.ajax({
        url: requestUrl,
        success: function (data) {
            var len = data.length;
            for (var i = 0; i < len; ++i) {
                categ.push(data[i].caption);
                percents.push(data[i].val);
            }
            $(function () {
                $('#chartContainer').highcharts({
                    chart: {
                        type: 'bar'
                    },
                    title: {
                        text: 'Rating for categories'
                    },
                    xAxis: {
                        categories: categ,
                        title: {
                            text: null
                        }
                    },
                    yAxis: {
                        min: 0,
                        max:100,
                        title: {
                            text: 'Percents',
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
                                enabled: true
                            }
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'top',
                        x: -40,
                        y: 100,
                        floating: true,
                        borderWidth: 1,
                        backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor || '#FFFFFF'),
                        shadow: true
                    },
                    credits: {
                        enabled: false
                    },
                    series: [
                        {
                            name: 'Mark',
                            data: percents
                        }
                    ]
                });
            });
        }
    });
}





function loadData(page) {
    var gparam = $("#group_select").val();
    var cparam = $("#category_select").val();

    var url1 = 'rating?';

    var url2 = [];

    if(gparam != undefined && gparam != '--')
        url2.push('group=' + gparam);
    if(cparam != '--')
        url2.push('category=' + cparam);

    if(page) {
        url2.push('page=' + page);
    }

    url1 += url2.join('&');

   // document.location = url1;
   $("#container").load(url1 + ' #upd');
}

function loadDataForCategoryRating(page){
    var gparam = $("#group_select").val();

    var url1 = '/rating/categories?';
    var url2 = [];

    if(gparam != undefined && gparam != '--')
        url2.push('group=' + gparam);

    if(page) {
        url2.push('page=' + page);
    }

    url1 += url2.join('&');
    $("#container").load(url1 + ' #upd');
    drawChart();
}