
flushPage('common/home');

function flushPage(src,time) {
    var src1 = '../' + src;
    var data = "type=" + $("#selectV").val() + "&value=" + $("input[selectId='selectV']").val();
    $.ajax({
        url: src1,
        dataType: 'html',
        data: data,
        beforeSend: function() {
            $('#my-modal-loading').modal('open');
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("#alert-message").html("菜单地址设置错误/n错误信息："+jqXHR.responseText);
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("页面获取超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            $("#admin-content-body").html(data);
        },
        complete: function() {
            // if(time==1) {
            //     flushPagination(parseInt($("#Form").data("page")), parseInt($("#Form").data("max")), time);
            // }
            $('#my-modal-loading').modal('close');
        }
    });

}


function flushFrom(src,time) {
    var src1 = '../' + src;
    var data = "type=" + $("#selectV").val() + "&value=" + $("input[selectId='selectV']").val();
    $.ajax({
        url: src1,
        dataType: 'html',
        type: 'post',
        data: data,
        beforeSend: function() {
            $('#my-modal-loading').modal('open');
        },
        error: function() {
            $("#alert-message").html("菜单地址设置错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("页面获取超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            $("#Form").html(data);
        },
        complete: function() {
            if(time==2){
                flushPagination(parseInt($("#Form").data("page")), parseInt($("#Form").data("max")), time);
            }
            $('#my-modal-loading').modal('close');
        }
    });

}

function flushPagination(totalData, showData,time) {
    $('.am-pagination').pagination({
        coping: true,
        homePage: '首页',
        endPage: '末页',
        prevContent: '上页',
        nextContent: '下页',
        totalData: totalData,
        showData: showData,
        activeCls: 'am-active',
        keepShowPN: true,
        callback: function(api) {
            if(time!=2) {
                flushFrom($("#Form").attr("action") + "/" + api.getCurrent(),time);
            }
        }
    }, function(api) {
        if(time!=2) {
            flushFrom($("#Form").attr("action") + "/" + 1);
        }

    });
}

var parserDate = function (date) {
    var t = Date.parse(date);
    if (!isNaN(t)) {
        return new Date(Date.parse(date.replace(/-/g, "/")));
    } else {
        return new Date();
    }
}

var FormatData = function (data,fmt) { //author: meizz
    var o = {
        "M+": data.getMonth() + 1, //月份
        "d+": data.getDate(), //日
        "h+": data.getHours(), //小时
        "m+": data.getMinutes(), //分
        "s+": data.getSeconds(), //秒
        "q+": Math.floor((data.getMonth() + 3) / 3), //季度
        "S": data.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
function getJsonLength(jsonData){

    var jsonLength = 0;

    for(var item in jsonData){

        jsonLength++;

    }

    return jsonLength;

}

function GetJson(url,errorInfo,timeoutInfo,successFunction) {
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html(errorInfo);
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html(timeoutInfo);
            $("#my-alert").modal('open');
        },
        success: successFunction,
    });
}

function tojson(formItme){
    var from = "{";
    for(var i= 0;i<formItme.length;i++){
        if(formItme[i].name==""||formItme[i].name==undefined){
            continue;
        }
        from+="\""+formItme[i].name+"\""+":"+formItme[i].value+",";

    }
    from=from.substring(0,from.length-1);
    from+="}";
    return from;
}

