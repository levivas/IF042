/**
 * Created by vprokotc on 02.05.14.
 */

function deleteQuestion(id) {
    $.ajax({
        url: '/tests/delete?what=question&id='+id,
        type: 'post',
        success: function(data) {
            $("#q"+id).hide();
        }
    });
}

function deleteTest(id) {
    $.ajax({
        url: '/tests/delete?what=test&id='+id,
        type: 'post',
        success: function(data) {
            $("#t"+id).hide();
        }
    });
}

