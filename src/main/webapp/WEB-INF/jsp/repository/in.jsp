<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2018/4/17
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="../assets/common.js"></script>
<script src="../assets/js/repository/in.js"></script>
<div class="am-cf am-padding am-padding-bottom-0">
    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">商品入库管理</strong> / <small>GoodStorageManager</small></div>
</div>
<div class="am-g">
    <div class="am-u-sm-12 am-u-md-6">
        <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
                <button type="button" class="am-btn am-btn-default" onclick="addPage()"><span class="am-icon-plus"></span> 新增</button>
            </div>
        </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
        <div class="am-form-group">
            搜索类别：
            <select data-am-selected="{btnSize: 'sm'}" id="selectO">

            </select>
        </div>
    </div>
    <div class="am-u-sm-12 am-u-md-3">
        <div class="am-input-group am-input-group-sm">
            <input type="text" class="am-form-field" id="selectV">
            <span class="am-input-group-btn">
            <button class="am-btn am-btn-default" type="button" onclick="selectAction();" data-url="" data-port="">搜索</button>
          </span>
        </div>
    </div>
</div>

<div class="am-g">
    <div class="am-u-sm-12">

        <table id="Table" class="am-table am-table-striped am-table-hover table-main">
            <thead>
            <tr>
                <th class="table-check"><input type="checkbox" /></th>
                <th>ID</th>
                <th>任务名称</th>
                <th>任务执行人</th>
                <th>任务自动结束时间</th>
                <th class="table-set">操作</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

        <div class="am-cf">
            共有 <span id="listSum"></span> 记录
            <div class="am-fr">
                <ul class="am-pagination">
                </ul>
            </div>
        </div>

        <hr />
        <p></p>
    </div>

</div>
</div>
<!-- addfrom start -->
<div class="am-modal am-modal-prompt" tabindex="-1" id="addprompt">
    <div class="am-modal-dialog">
        <%--<div class="am-modal-hd" id="from-title">商品信息新增</div>--%>
        <form id="addFORM" data-am-validator>
            <legend>采购流程新增</legend>
            <fieldset>
                <div class="am-form-group am-g">
                    <label for="addProduct" class="am-u-sm-4">采购商品：</label>
                    <select name="productId" class="am-u-sm-8" id="addProduct" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
                <div class="am-form-group am-g">
                    <label for="addRepositoryId" class="am-u-sm-4">存放仓库：</label>
                    <select name="repositoryId" class="am-u-sm-8" id="addRepositoryId" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
                <div class="am-form-group am-g">
                    <label for="addBatchRs2" class="am-u-sm-4">负责采购员工：</label>
                    <select name="batchRs2" class="am-u-sm-8" id="addBatchRs2" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
                <div class="am-form-group am-g">
                    <label for="addNumber" class="am-u-sm-4">数量(单位/箱)：</label>
                    <input type="number" class="am-modal-prompt-input" name="number" id="addNumber" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addBatchRs1" class="am-u-sm-4">金额(单位/元)：</label>
                    <input type="number" class="am-modal-prompt-input" name="batchRs1" id="addBatchRs1" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addCreateTime" class="am-u-sm-4">提交时间：</label>
                    <input type="text" class="am-modal-prompt-input" name="createTime" id="addCreateTime" data-am-datepicker readonly required>
                </div>
            </fieldset>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm onclick="add()">提交</span>
            </div>
        </form>
    </div>
</div>
<!-- addfrom end -->
<!-- editfrom start -->
<div class="am-modal am-modal-prompt" tabindex="-1" id="editprompt">
    <div class="am-modal-dialog">
        <%--<div class="am-modal-hd" id="from-title">商品信息新增</div>--%>
        <form id="editFORM" data-am-validator>
            <legend>厂家信息修改</legend>
            <fieldset>
                <div class="am-form-group">
                    <label for="editName">销售点名称：</label>
                    <input type="text" class="am-modal-prompt-input" name="shopName" id="editName" required>
                </div>
                <div class="am-form-group">
                    <label for="editAddress">销售点地址：</label>
                    <input type="text" class="am-modal-prompt-input" name="shopAddress" id="editAddress" required>
                </div>
                <div class="am-form-group">
                    <label for="editAdmin">销售点负责人：</label>
                    <select name="shopAdmin" class="am-u-sm-8" id="editAdmin" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
                <div class="am-form-group">
                    <label for="editRs1">备注：</label>
                    <input type="text" class="am-modal-prompt-input" name="shopRs1" id="editRs1" required>
                </div>
                <div class="am-form-group">
                    <label for="editRs2">提交时间：</label>
                    <input type="text" class="am-modal-prompt-input" name="shopRs2" id="editRs2" data-am-datepicker readonly required>
                </div>
            </fieldset>
            <input type="text" id="editId" name="shopId" hidden>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm onclick="edit()">提交</span>
            </div>
        </form>
    </div>
</div>
<!-- editfrom end -->
<!-- 详情页start -->
<div class="am-modal am-modal-no-btn" tabindex="-1" id="infoprompt">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">业务详情
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <div class="time_line_box">
                <div class="time_line" id="timeLine" style="width:90%;">
                    <ol></ol>
                </div>
            </div>
            <div id="taskInfo">

            </div>

        </div>
    </div>
</div>
<!-- 详情页end -->
