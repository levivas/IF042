/**
 * Created by vprokotc on 4/17/2014.
 */

function spanMessage(){
    var tparam = $("#test_select").val();
    var gparam = $("#group_select").val();
    var cparam = $("#category_select").val();
    var dateA = new Date($('#dateFrom').val());
    var dateB = new Date($('#dateTo').val());

    if((tparam != '--')|(gparam != '--')|(cparam != '--')|(!dateA )||(!dateB)){
        document.getElementById('messageForButton').style.display='none';
        generateSheet();
    }else {
        document.getElementById('messageForButton').style.display = 'block';
    }

}

function loadData(page) {
    var tparam = $("#test_select").val();
    var gparam = $("#group_select").val();
    var cparam = $("#category_select").val();
    var dateA = new Date($('#dateFrom').val());
    var dateB = new Date($('#dateTo').val());

    var url1 = 'results?';

    var url2 = [];

    if(tparam != '--')
        url2.push('test=' + tparam);
    if(gparam != undefined && gparam != '--')
        url2.push('group=' + gparam);
    if(cparam != '--')
        url2.push('category=' + cparam);

    if(!isNaN(dateA.getTime()))
        url2.push('dateA=' + dateA.getTime());
    if(!isNaN(dateB.getTime()))
        url2.push('&dateB=' + dateB.getTime());
    if(page) {
        url2.push('&page=' + page);
    }

    url1 += url2.join('&');

    $("#container").load(url1 + ' #upd');
}

 function generateSheet(){
    var tparam  = $("#test_select").val();
    var gparam  = $("#group_select").val();
    var cparam  = $("#category_select").val();
    var cparamN = $('#category_select option:selected').text();
    var dateA   = new Date($('#dateFrom').val());
    var dateB   = new Date($('#dateTo').val());


    var url1 = '/results/results.pdf?';
    var url2 = [];


    if(tparam != '--'){
        url2.push('test=' + tparam);
    } else return;
    if(gparam != undefined && gparam != '--'){
        url2.push('group=' + gparam);

    } else return;
    if(cparam != '--'){
        url2.push('category=' + cparam);
        url2.push('categoryName='+cparamN);
    } else return;
    if(!isNaN(dateA.getTime())){
        url2.push('dateA=' + dateA.getTime());
    } else return;
    if(!isNaN(dateB.getTime())) {
        url2.push('&dateB=' + dateB.getTime());
    } else return;

    url1 += url2.join('&');

    document.location = url1;
}

function removeResult(id) {
    $.ajax({
        type: "POST",
        url: "/results/remove?id="+id,
        success: function(data) {
            loadData(1);
        }
    });
}
