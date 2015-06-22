/**
 * Created by vprokotc on 4/14/2014.
 */

function getData(what, insertInto) {
    $.ajax({
        url: 'getdata?whattoget='+what,
        success: function(data) {
            item = $('#'+insertInto);
            item.html(item.html() + data);
        }
    });
}