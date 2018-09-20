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
<script src="../assets/js/info/producer.js"></script>
<div class="am-cf am-padding am-padding-bottom-0">
    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">厂家信息管理</strong> / <small>ProducerInfoManager</small></div>
</div>
<div class="am-g">
    <div class="am-u-sm-12 am-u-md-6">
        <div class="am-btn-toolbar">
            <div class="am-btn-group am-btn-group-xs">
                <button type="button" class="am-btn am-btn-default" onclick="addPage()"><span class="am-icon-plus"></span> 新增</button>
                <button type="button" class="am-btn am-btn-default" onclick="del()"><span class="am-icon-trash-o"></span> 删除</button>
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
                            <th>厂家名称</th>
                            <th>厂家地址</th>
                            <th>厂家联系人</th>
                            <th>厂家联系电话</th>
                            <th>记录提交</th>
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
            <legend>厂家信息新增</legend>
            <fieldset>
            <div class="am-form-group">
                <label for="addProducerName">厂家名称：</label>
                <input type="text" class="am-modal-prompt-input" name="producerName" id="addProducerName" required>
            </div>
            <div class="am-form-group">
                <label for="addProducerAddress">厂家地址：</label>
                <input type="text" class="am-modal-prompt-input" name="producerAddress" id="addProducerAddress" required>
            </div>
            <div class="am-form-group">
                <label for="addProducerAdmin">厂家联系人：</label>
                <input type="text" class="am-modal-prompt-input" name="producerAdmin" id="addProducerAdmin" required>
            </div>
            <div class="am-form-group">
                <label for="addProducerTel">厂家联系电话：</label>
                <input type="text" class="am-modal-prompt-input" name="producerTel" id="addProducerTel" required>
            </div>
            <div class="am-form-group">
                <label for="addProducerRs9">提交时间：</label>
                <input type="text" class="am-modal-prompt-input" name="producerRs9" id="addProducerRs9" data-am-datepicker readonly required>
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
                    <label for="editProducerName">厂家名称：</label>
                    <input type="text" class="am-modal-prompt-input" name="producerName" id="editProducerName" required>
                </div>
                <div class="am-form-group">
                    <label for="editProducerAddress">厂家地址：</label>
                    <input type="text" class="am-modal-prompt-input" name="producerAddress" id="editProducerAddress" required>
                </div>
                <div class="am-form-group">
                    <label for="editProducerAdmin">厂家联系人：</label>
                    <input type="text" class="am-modal-prompt-input" name="producerAdmin" id="editProducerAdmin" required>
                </div>
                <div class="am-form-group">
                    <label for="editProducerTel">厂家联系电话：</label>
                    <input type="text" class="am-modal-prompt-input" name="producerTel" id="editProducerTel" required>
                </div>
                <div class="am-form-group">
                    <label for="editProducerRs9">提交时间：</label>
                    <input type="text" class="am-modal-prompt-input" name="producerRs9" id="editProducerRs9" data-am-datepicker readonly required>
                </div>
            </fieldset>
            <input type="text" id="editProducerId" name="producerId" hidden>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm onclick="edit()">提交</span>
            </div>
        </form>
    </div>
</div>
<!-- editfrom end -->
