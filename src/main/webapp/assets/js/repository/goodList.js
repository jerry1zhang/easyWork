//初始化方法
$(function () {
    init();
});
function init() {
    $('#my-modal-loading').modal('open');
    initTableData();
    initSelectOption();
    initPage();
    $("input[name='shopRs2']").datepicker({format: 'yyyy-mm-dd'});
    initFrom();
    initAddress();
    $('#my-modal-loading').modal('close');
}
//初始化表格数据
function initTableData() {
    $.ajax({
        url: "../goodlist/info/1/10",
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            drawTable(data);
        }
    });
}
//初始化下拉框
function initSelectOption(){
    $.ajax({
        url: "../common/selectOptionList/shopList",
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            var html="";
            for(var index in data){
                html+="<option value=\""+data[index].itemValue+"\">"+data[index].itemName+"</option>";
            }
            $("#selectO").html(html);
        }
    });
    $.ajax({
        url: "../apiuser/userList/7bbd201fb70c4300add6a1cd9f1d7558",
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("初始化下拉框错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("初始化下拉框超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            var html = "";
            html+="<option value=\"\"></option>";
            for(var index in data){
                html+="<option value=\""+data[index].user.id+"\">"+data[index].user.firstName+data[index].user.lastName+"("+data[index].group.name+")"+"</option>";
            }
            $("#addAdmin").html(html);
            $("#editAdmin").html(html);
            $("#addAdmin").selected();
            $("#editAdmin").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#addAdmin").trigger('changed.selected.amui');
                $("#editAdmin").trigger('changed.selected.amui');
            }
            // $("#productProducer").trigger('chosen:updated');
            // $("#producer_chosen").css("width","atom");
        }
    });
}
//初始化分页
function initPage() {
    var listSum=0;
    var showSum=10;
    $.ajax({
        url: "../goodlist/page/"+showSum,
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            listSum = data.listSum;
            $("#listSum").html(data.listSum);

        }
    });
    $("ul[class='am-pagination']").pagination({
        coping: true,
        homePage: '首页',
        endPage: '末页',
        prevContent: '上页',
        nextContent: '下页',
        totalData: listSum,
        showData: showSum,
        activeCls: 'am-active',
        keepShowPN: true,
        callback: function(api) {
            callback(api);
        }
    }, function(api) {
    });

}
//初始化表单验证
function initFrom(){
    $('#FORM').validator({
        validateOnSubmit: true,
        validClass: 'am-field-valid',
        inValidClass: 'am-field-error',
    });
}
//初始化地址信息
function initAddress() {
    $('#addAddress').address({
        scrollToCenter: true,
        footer: true,
        selectEnd: function(json) {
            $('#addAddress').val(json.prov+json.city+json.district);
        }
    });
    $('#editAddress').address({
        scrollToCenter: true,
        footer: true,
        selectEnd: function(json) {
            $('#editAddress').val(json.prov+json.city+json.district);
        }
    });
}
//分页回掉
function callback(api){
    $.ajax({
        url: "../goodlist/info/"+api.getCurrent()+"/10",
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            drawTable(data);
        }
    });
}
function callback2(api,selectType,selectValue){
    var url = "../goodlist/info/"+api.getCurrent()+"/10";
    if(typeof(selectValue)==undefined||selectValue==undefined||selectValue==""){

    }else{
        url+="/"+selectType+"/"+selectValue;
    }
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("分页回调请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("分页回调请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            drawTable(data);
        }
    });

}
//搜索
function selectAction() {
    $('#my-modal-loading').modal('open');
    var selectType = $("#selectO").val();
    var selectValue = $("#selectV").val();
    var url = "../goodlist/info/1/10";
    if(typeof(selectValue)==undefined||selectValue==undefined||selectValue==""){

    }else{
        url+="/"+selectType+"/"+selectValue;
    }
    $.ajax({
        url: url,
        dataType: 'json',
        type: 'get',
        error: function() {
            $("#alert-message").html("搜索请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("搜索请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            //写表
            drawTable(data);
            //写分页
            var listSum=0;
            var showSum=10;
            var url = "../goodlist/page/"+showSum;
            if(typeof(selectValue)==undefined||selectValue==undefined||selectValue==""){

            }else{
                url+="/"+selectType+"/"+selectValue;
            }
            $.ajax({
                url: url,
                dataType: 'json',
                type: 'get',
                async: false,
                error: function() {
                    $("#alert-message").html("请求错误");
                    $("#my-alert").modal('open');
                },
                timeout: function() {
                    $("#alert-message").html("请求超时");
                    $("#my-alert").modal('open');
                },
                success: function(data) {
                    listSum = data.listSum;
                    $("#listSum").html(data.listSum);

                }
            });
            $("ul[class='am-pagination']").pagination({
                coping: true,
                homePage: '首页',
                endPage: '末页',
                prevContent: '上页',
                nextContent: '下页',
                totalData: listSum,
                showData: showSum,
                activeCls: 'am-active',
                keepShowPN: true,
                callback: function(api) {
                    callback2(api,selectType,selectValue);
                }
            }, function(api) {
            });
            $("#selectV").val("");

        }
    });
    $('#my-modal-loading').modal('close');
}
//新增
function addPage() {
    $("#addprompt").modal('open');
}
function add() {
    if($("#addFORM").find(".am-form-error").length!=0){
        $("#alert-message").html("表单验证失败");
        $("#my-alert").modal('open');
        return false;
    }
    $("#addprompt").modal('close');
    $('#my-modal-loading').modal('open');

    //下拉框特殊处理
    var formItme = $("#addFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../goodlist/add/"+form,
        dataType: 'json',
        type: 'GET',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            $('#my-modal-loading').modal('close');
            $("#alert-message").html("新增成功！新增条数："+data.addSum);
            $("#my-alert").modal('open');

            flushPage('goodlist/page',1);
        }
    });
}
function copy(obj){
    $("#addprompt").modal('open');
    $("#addName").val($(obj).attr("shopName"));
    $("#addAddress").val($(obj).attr("shopAddress"));
    $("#addAdmin").val($(obj).attr("shopAdmin"));
    $("#addAdmin").trigger('changed.selected.amui');
    $("#addRs1").val($(obj).attr("shopRs1"));
    $("#addRs2").datepicker('setValue', $(obj).attr("shopRs2"));
}
function editPage(obj){
    $("#editprompt").modal('open');
    $("#editId").val($(obj).attr("shopId"));
    $("#editName").val($(obj).attr("shopName"));
    $("#editAddress").val($(obj).attr("shopAddress"));
    $("#editAdmin").val($(obj).attr("shopAdmin"));
    $("#editAdmin").trigger('changed.selected.amui');
    $("#editRs1").val($(obj).attr("shopRs1"));
    $("#editRs2").datepicker('setValue', $(obj).attr("shopRs2"));
}
function edit() {
    if($("#editFORM").find(".am-form-error").length!=0){
        $("#alert-message").html("表单验证失败");
        $("#my-alert").modal('open');
        return false;
    }
    $("#editprompt").modal('close');
    $('#my-modal-loading').modal('open');

    //下拉框特殊处理
    var formItme = $("#editFORM").find("input,select");
    var form = tojson(formItme);
    $.ajax({
        url: "../goodlist/edit/"+form,
        dataType: 'json',
        type: 'GET',
        async: false,
        error: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {

            $('#my-modal-loading').modal('close');
            $("#alert-message").html("修改成功！修改条数："+data.changeSum);
            $("#my-alert").modal('open');

            flushPage('goodlist/page',1);
        }
    });
}
//删除
function del() {
    var chenked=$("input[name='checkbox']:checked").val([]);
    var array = [];
    for(var i = 0;i<chenked.length;i++){
        array.push({"shopId":chenked[i].value});
    }
    $.ajax({
        url: "../goodlist/del/"+JSON.stringify(array),
        dataType: 'json',
        type: 'get',
        async: false,
        error: function() {
            $("#alert-message").html("请求错误");
            $("#my-alert").modal('open');
        },
        timeout: function() {
            $("#alert-message").html("请求超时");
            $("#my-alert").modal('open');
        },
        success: function(data) {
            $("#alert-message").html("操作成功！操作结果数："+data.delSum);
            $("#my-alert").modal('open');
            init();
        }
    });
}
function drawTable(data) {
    var html="";
    for(var index in data){
        html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].r_s_id+"' /></td>";
        html+="<td>"+data[index].rs1+"</td>";
        html+="<td>"+data[index].rs2+"</td>";
        html+="<td>"+data[index].number+"</td>";
    }
    $("#Table").find("tbody").html(html);
}