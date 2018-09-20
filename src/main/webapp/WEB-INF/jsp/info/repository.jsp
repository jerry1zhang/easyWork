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
<script src="../assets/js/info/repository.js"></script>
<div class="am-cf am-padding am-padding-bottom-0">
    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">仓库信息管理</strong> / <small>RepositoryInfoManager</small></div>
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
                <th>仓库名称</th>
                <th>仓库地址</th>
                <th>仓库负责人</th>
                <th>备注</th>
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
            <legend>仓库信息新增</legend>
            <fieldset>
                <div class="am-form-group am-g">
                    <label for="addRepositoryName" class="am-u-sm-4">仓库名称：</label>
                    <input type="text" class="am-u-sm-8" name="repositoryName" id="addRepositoryName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addRepositoryAddress" class="am-u-sm-4">仓库地址：</label>
                    <input type="text" class="am-u-sm-8" placeholder="请选择地址" name="repositoryAddress" id="addRepositoryAddress" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addRepositoryAdmin" class="am-u-sm-4">仓库负责人：</label>
                    <select name="repositoryAdmin" class="am-u-sm-8" id="addRepositoryAdmin" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
                <div class="am-form-group am-g">
                    <label for="addRepositoryRs1" class="am-u-sm-4">备注：</label>
                    <input type="text" class="am-u-sm-8" name="repositoryRs1" id="addRepositoryRs1" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addRepositoryRs9" class="am-u-sm-4">提交时间：</label>
                    <input type="text" class="am-u-sm-8" name="repositoryRs9" id="addRepositoryRs9" data-am-datepicker="{format: 'yyyy-mm-dd'}" readonly required>
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
            <legend>仓库信息修改</legend>
            <fieldset>
                <div class="am-form-group am-g">
                    <label for="editRepositoryName" class="am-u-sm-4">仓库名称：</label>
                    <input type="text" class="am-u-sm-8" name="repositoryName" id="editRepositoryName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editRepositoryAddress" class="am-u-sm-4">仓库地址：</label>
                    <input type="text" class="am-u-sm-8" placeholder="请选择地址" name="repositoryAddress" id="editRepositoryAddress" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editRepositoryAdmin" class="am-u-sm-4">仓库负责人：</label>
                    <select name="repositoryAdmin" class="am-u-sm-8" id="editRepositoryAdmin" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
                <div class="am-form-group am-g">
                    <label for="editRepositoryRs1" class="am-u-sm-4">备注：</label>
                    <input type="text" class="am-u-sm-8" name="repositoryRs1" id="editRepositoryRs1" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editRepositoryRs9" class="am-u-sm-4">提交时间：</label>
                    <input type="text" class="am-u-sm-8" name="repositoryRs9" id="editRepositoryRs9" data-am-datepicker="{format: 'yyyy-mm-dd'}" readonly required>
                </div>
            </fieldset>
            <input type="text" id="editRepositoryId" name="repositoryId" hidden>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm onclick="edit()">提交</span>
            </div>
        </form>
    </div>
</div>
<!-- editfrom end -->
