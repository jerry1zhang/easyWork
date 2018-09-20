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
<script src="../assets/js/info/man.js"></script>
<div class="am-cf am-padding am-padding-bottom-0">
    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">用户信息管理</strong> / <small>UserInfoManager</small></div>
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
                <th>用户账户</th>
                <th>用户姓名</th>
                <th>用户邮箱</th>
                <th>用户所属组</th>
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
            <legend>用户信息新增</legend>
            <fieldset>
                <div class="am-form-group am-g">
                    <label for="addName" class="am-u-sm-4">用户账户：</label>
                    <input type="text" class="am-modal-prompt-input am-u-sm-8" name="name" id="addName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addPassword" class="am-u-sm-4">用户密码：</label>
                    <input type="password" class="am-modal-prompt-input am-u-sm-8" name="password" id="addPassword" required>
                </div>

                <div class="am-form-group am-g">
                    <label for="addPasswordAg" class="am-u-sm-4">用户密码再输入：</label>
                    <input type="password" class="am-modal-prompt-input am-u-sm-8" name="producerAdmin" id="addPasswordAg" required jump="1">
                </div>
                <div class="am-form-group am-g">
                    <label for="addFirstName" class="am-u-sm-4">用户姓氏：</label>
                    <input type="text" class="am-modal-prompt-input am-u-sm-8" name="firstName" id="addFirstName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addLastName" class="am-u-sm-4">用户名字：</label>
                    <input type="text" class="am-modal-prompt-input am-u-sm-8" name="lastName" id="addLastName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addEmail" class="am-u-sm-4">用户邮箱：</label>
                    <input type="email" class="am-modal-prompt-input am-u-sm-8" name="email" id="addEmail" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="addGroup" class="am-u-sm-4">用户组：</label>
                    <select name="groupId" class="am-u-sm-8" id="addGroup" data-am-selected="{searchBox: 1}" required>
                    </select>
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
            <legend>用户信息修改</legend>
            <fieldset>
                <div class="am-form-group am-g">
                    <label for="editPassword" class="am-u-sm-4">用户密码：</label>
                    <input type="password" class="am-modal-prompt-input am-u-sm-8" name="password" id="editPassword" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editPasswordAg" class="am-u-sm-4">用户密码确认：</label>
                    <input type="password" class="am-modal-prompt-input am-u-sm-8" name="producerAdmin" id="editPasswordAg" required jump="1">
                </div>
                <div class="am-form-group am-g">
                    <label for="editFirstName" class="am-u-sm-4">用户姓氏：</label>
                    <input type="text" class="am-modal-prompt-input am-u-sm-8" name="firstName" id="editFirstName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editLastName" class="am-u-sm-4">用户名字：</label>
                    <input type="text" class="am-modal-prompt-input am-u-sm-8" name="lastName" id="editLastName" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editEmail" class="am-u-sm-4">用户邮箱：</label>
                    <input type="email" class="am-modal-prompt-input am-u-sm-8" name="email" id="editEmail" required>
                </div>
                <div class="am-form-group am-g">
                    <label for="editGroup" class="am-u-sm-4">用户组：</label>
                    <select name="groupId" class="am-u-sm-8" id="editGroup" data-am-selected="{searchBox: 1}" required>
                    </select>
                </div>
            </fieldset>
            <input type="text" id="editName" name="name" hidden>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
                <span class="am-modal-btn" data-am-modal-confirm onclick="edit()">提交</span>
            </div>
        </form>
    </div>
</div>
<!-- editfrom end -->
