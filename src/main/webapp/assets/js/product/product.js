//初始化方法
$(function () {
    init();
});
function init() {
    $('#my-modal-loading').modal('open');
    initTableData();
    initSelectOption();
    initPage();
    $("input[name='productRs1']").datepicker({format: 'yyyy-mm-dd'});
    initFrom();
    $('#my-modal-loading').modal('close');
}
//初始化表格数据
function initTableData() {
    $.ajax({
        url: "../info/productInfo/1/10",
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
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].productId+"' /></td>";
                html+="<td>"+data[index].productId+"</td>";
                html+="<td>"+data[index].productCode+"</td>";
                html+="<td>"+data[index].productName+"</td>";
                html+="<td>"+data[index].productRs2+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" onclick='editPage(this)' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" onclick='copy(this);addPage();' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
        }
    });
}
//初始化下拉框
function initSelectOption(){
    $.ajax({
        url: "../common/selectOptionList/productList",
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
            var html="";
            for(var index in data){
                html+="<option value=\""+data[index].itemValue+"\">"+data[index].itemName+"</option>";
            }
            $("#selectO").html(html);
        }
    });
    $.ajax({
        url: "../producer/info",
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

            var html = "";
            html+="<option value=\"\"></option>";
            for(var index in data){
                html+="<option value=\""+data[index].producerId+"\">"+data[index].producerName+"</option>";
            }
            $("#productProducer").html(html);
            $("#editProductProducer").html(html);
            $("#productProducer").selected();
            $("#editProductProducer").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#productProducer").trigger('changed.selected.amui');
                $("#editProductProducer").trigger('changed.selected.amui');
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
        url: "../info/productInfo/page/"+showSum,
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
//分页回掉
function callback(api){
    $.ajax({
        url: "../info/productInfo/"+api.getCurrent()+"/10",
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
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].productId+"' /></td>";
                html+="<td>"+data[index].productId+"</td>";
                html+="<td>"+data[index].productCode+"</td>";
                html+="<td>"+data[index].productName+"</td>";
                html+="<td>"+data[index].productRs2+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" onclick='editPage(this)' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" onclick='copy(this);addPage();' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
        }
    });
}
function callback2(api,selectType,selectValue){

    $.ajax({
        url: "../info/productInfo/"+api.getCurrent()+"/10/"+selectType+"/"+selectValue,
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
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].productId+"' /></td>";
                html+="<td>"+data[index].productId+"</td>";
                html+="<td>"+data[index].productCode+"</td>";
                html+="<td>"+data[index].productName+"</td>";
                html+="<td>"+data[index].productRs2+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" onclick='editPage(this)' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" onclick='copy(this);addPage();' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
        }
    });

}
//搜索
function selectAction() {
    $('#my-modal-loading').modal('open');
    var selectType = $("#selectO").val();
    var selectValue = $("#selectV").val();
    $.ajax({
        url: "../info/productInfo/1/10/"+selectType+"/"+selectValue,
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

            //写表
            var html="";
            for(var index in data){
                html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].productId+"' /></td>";
                html+="<td>"+data[index].productId+"</td>";
                html+="<td>"+data[index].productCode+"</td>";
                html+="<td>"+data[index].productName+"</td>";
                html+="<td>"+data[index].productRs2+"</td>";
                html+="<td>" +
                    "<div class=\"am-btn-toolbar\">" +
                    "<div class=\"am-btn-group am-btn-group-xs\"> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" onclick='editPage(this)' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-pencil-square-o\"></span> 编辑</button> " +
                    "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" onclick='copy(this);addPage();' productId='"+data[index].productId+"' productCode='"+data[index].productCode+"' productName='"+data[index].productName+"' producer='"+data[index].productProducer+"'><span class=\"am-icon-copy\"></span> 复制</button> " +
                    "</div> " +
                    "</div> " +
                    "</td>";
                html+="</tr>";
            }
            $("#Table").find("tbody").html(html);
            //写分页
            var listSum=0;
            var showSum=10;
            $.ajax({
                url: "../info/productInfo/page/"+showSum+"/"+selectType+"/"+selectValue,
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
    var producer = "\""+$("#productProducer").attr("name")+"\":\""+$("#productProducer").val()+"\"";
    var formItme = $("#addFORM").find("input");
    var form = tojson(formItme,producer);
    console.log("form"+form);
    $.ajax({
        url: "../info/productInfo/add/"+form,
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

            flushPage('info/product',1);
        }
    });
}
function copy(obj){
    $("#addprompt").modal('open');
    $("#productCode").val($(obj).attr("productCode"));
    $("#productName").val($(obj).attr("productName"));
    $("#productProducer").val($(obj).attr("producer"));
    $("#productProducer").trigger('changed.selected.amui');
}
function editPage(obj){
    $("#editprompt").modal('open');
    $("#editProductId").val($(obj).attr("productId"))
    $("#editProductCode").val($(obj).attr("productCode"));
    $("#editProductName").val($(obj).attr("productName"));
    $("#editProductProducer").val($(obj).attr("producer"));
    $("#editProductProducer").trigger('changed.selected.amui');
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
    var producer = "\""+$("#editProductProducer").attr("name")+"\":\""+$("#editProductProducer").val()+"\"";
    var formItme = $("#editFORM").find("input");
    var form = tojson(formItme,producer);
    $.ajax({
        url: "../info/productInfo/edit/"+form,
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

            flushPage('info/product',1);
        }
    });
}
//删除
function del() {
    var chenked=$("input[name='checkbox']:checked").val([]);
    var array = [];
    for(var i = 0;i<chenked.length;i++){
        array.push({"productId":chenked[i].value});
    }
    $.ajax({
        url: "../info/productInfo/del/"+JSON.stringify(array),
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
function tojson(formItme,addStr){
    var from = "{";
    for(var i= 0;i<formItme.length;i++){
        if(formItme[i].name==""||formItme[i].name==undefined){
            continue;
        }
        from+="\""+formItme[i].name+"\""+":"+formItme[i].value;
        if(i!=formItme.length-1){
            from+=",";
        }else{
            if(addStr==""||addStr==undefined){

            }else{
                from+=","+addStr;
            }
        }
    }
    from+="}";
    return from;
}