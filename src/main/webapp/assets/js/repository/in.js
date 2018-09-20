//初始化方法
$(function () {
    init();
});
function init() {
    $('#my-modal-loading').modal('open');
    initTableData();
    initSelectOption();
    initPage();
    $("#addCreateTime").datepicker({format: 'yyyy-mm-dd'});
    initFrom();
    initAddress();
    $('#my-modal-loading').modal('close');
}
//初始化表格数据
function initTableData() {
    $.ajax({
        url: "../rep/in/info/1/10",
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
        url: "../common/selectOptionList/taskList",
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

    GetJson("../info/productInfo",
        "初始化商品下拉框错误","初始化商品下拉框超时",
        function (data) {
            var html = "";
            html+="<option value=\"\"></option>";
            for(var index in data){
                html+="<option value=\""+data[index].productId+"\">"+data[index].productCode+data[index].productName+"("+data[index].productRs2+")"+"</option>";
            }
            $("#addProduct").html(html);
            $("#addProduct").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#addProduct").trigger('changed.selected.amui');
            }
        });
    GetJson("../repository/info",
        "初始化仓库下拉框错误","初始化仓库下拉框超时",
        function (data) {
            var html = "";
            html+="<option value=\"\"></option>";
            for(var index in data){
                html+="<option value=\""+data[index].repositoryId+"\">"+data[index].repositoryName+"("+data[index].repositoryAddress+")"+"</option>";
            }
            $("#addRepositoryId").html(html);
            $("#addRepositoryId").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#addRepositoryId").trigger('changed.selected.amui');
            }
        });
    GetJson("../apiuser/userList/采购",
        "初始化仓库下拉框错误","初始化仓库下拉框超时",
        function (data) {
            var html = "";
            html+="<option value=\"\"></option>";
            for(var index in data){
                html+="<option value=\""+data[index].user.id+"\">"+data[index].user.firstName+data[index].user.lastName+"("+data[index].group.name+")"+"</option>";
            }
            $("#addBatchRs2").html(html);
            $("#addBatchRs2").selected();
            if (!$.AMUI.support.mutationobserver) {
                $("#addBatchRs2").trigger('changed.selected.amui');
            }
        });
}

//初始化分页
function initPage() {
    var listSum=0;
    var showSum=10;
    $.ajax({
        url: "../rep/in/page/"+showSum,
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
        url: "../rep/in/info/"+api.getCurrent()+"/10",
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
    var url = "../rep/in/info/"+api.getCurrent()+"/10";
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
    var url = "../rep/in/info/1/10";
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
            var url = "../rep/in/page/"+showSum;
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
    var form = tojson(formItme,null);
    GetJson("../rep/in/add/"+form,
        "新增错误","新增超时",function(data) {
            $('#my-modal-loading').modal('close');
            $("#alert-message").html("新增成功！新增条数："+data.addSum);
            $("#my-alert").modal('open');

            flushPage('rep/in/page',1);
        });
}
function showInfo(obj) {
    $('#my-modal-loading').modal('open');
    showWorkFlow(obj);
}
//初始化流程图
function showWorkFlow(obj) {
    var taskInfo = $(obj).data("taskData");
    GetJson("../common/workFlowList/采购",
        "初始化流程图错误",
        "初始化流程图超时",function (data) {
            var size = parseInt(100/(getJsonLength(data)-1));
            var html="";
            var leng = 1;
            var tap = 0;
            var point = 0;
            for(var index in data){
                html+="<li>\n" +
                    "<a class=\"order_item ";
                if(tap==0){
                    html+="selected"
                }
                if(taskInfo.taskDefinitionKey==data[index].id){
                    tap=1;
                    point = leng;
                }
                html+="\" style=\"left:"+leng+"%;\" data-am-popover=\"{content: '任务编号："+data[index].id+"\n任务名称:"+data[index].name+"', trigger: 'hover focus'}\">"+data[index].name+"</a>\n" +
                    "</li>";
                leng+=size;
            }
            html+="<span class=\"filling_line\" style=\"transform: scaleX(0."+(parseInt(point)+1)+");\"></span>";
            $("#timeLine").find("ol").html(html);

            // $("#infoprompt").modal('open');
            // $("#timeLine").find("a").popover('open');

        });
    GetJson("../common/taskInfo/"+taskInfo.id,
        "初始化流程详情错误",
        "初始化流程详情超时",function (data) {
        var html = "";
        html+="<p style='text-align:left'>";
            html+="订单号："+data.orderInfo.orderId;
            html+="<br>";
            html+="金额："+data.orderInfo.money+"元";
            html+="<br>";

            for(var index in data.batchInfo){
                html+="批次："+data.batchInfo[index].batchId;
                html+="<br>";
                html+="数量："+data.batchInfo[index].number+"箱";
                html+="<br>";
                html+="商品："+data.batchInfo[index].batchRs9;
                html+="<br>";
                html+="仓库："+data.batchInfo[index].batchRs10;

            }


        html+="</p>";
        $("#taskInfo").html(html);
        });
    $("#infoprompt").modal({
        closeViaDimmer:false,
        width:600,
        height:300
    });
    $('#my-modal-loading').modal('close');
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
    var form = tojson(formItme,null);
    $.ajax({
        url: "../rep/in/edit/"+form,
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

            flushPage('rep/in/page',1);
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
        url: "../rep/in/del/"+JSON.stringify(array),
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
    if(data==undefined){
        alert("data is null");
        return;
    }
    var html="";
    for(var index in data){
        html+="<tr><td><input type=\"checkbox\" name='checkbox' value='"+data[index].id+"' /></td>";
        html+="<td>"+data[index].id+"</td>";
        html+="<td>"+data[index].name+"</td>";
        html+="<td>"+data[index].assignee+"</td>";
        html+="<td>"+data[index].dueDate+"</td>";
        html+="<td>" +
            "<div class=\"am-btn-toolbar\">" +
            "<div class=\"am-btn-group am-btn-group-xs\"> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
            "onclick='showInfo(this)'" +
            "taskid='"+data[index].id+"'>" +
            "<span class=\"am-icon-list-alt\"></span> 详情</button> " +
            "<div class=\"am-btn-group am-btn-group-xs\"> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-text-secondary\" " +
            "onclick='TaskStop(this)'" +
            "taskid='"+data[index].id+"'>" +
            "<span class=\"am-icon-pause\"></span> 暂停流程</button> " +
            "<button class=\"am-btn am-btn-default am-btn-xs am-hide-sm-only\" " +
            "onclick='finish(this);' " +
            "taskid='"+data[index].id+"'>" +
            "<span class=\"am-icon-arrow-right\"></span> 完成</button> " +
            "</div> " +
            "</div> " +
            "</td>";
        html+="</tr>";
    }
    $("#Table").find("tbody").html(html);
    for(var index in data){
        $("button[taskid='"+data[index].id+"']").data("taskData",data[index]);
    }
}
function TaskStop(obj) {

}
function finish(obj) {
    var data = $(obj).data("taskData");
    var array = [];
    array.push({"id":data.id});
    GetJson("../rep/in/finish/"+JSON.stringify(array),"完工失败","完工超时",function(data){
        $("#alert-message").html("操作成功！任务"+data.id+"：已经完成");
        $("#my-alert").modal('open');
        flushPage('rep/in/page',1);
    });

}