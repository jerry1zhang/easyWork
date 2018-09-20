<%@ page import="java.util.List" %>
<%@ page import="com.zking.enetity.UserBody" %>

<%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2017/10/25
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<UserBody> userBodies = (List<UserBody>)request.getAttribute("userBodies");

%>
<input hidden id="page" value="<%=(int)request.getAttribute("num")%>">
    <table class="am-table am-table-striped am-table-hover table-main">
        <thead>
        <tr>
            <th class="table-check"><input type="checkbox" /></th>
            <th class="table-id">ID</th>
            <th class="table-title">用户名</th>
            <th class="table-type">用户所属组</th>
            <th class="table-author am-hide-sm-only">用户邮箱</th>
            <th class="table-date am-hide-sm-only">用户姓名</th>
            <th class="table-set">操作</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (userBodies!=null)
                for (int i = 0; i<userBodies.size();i++){
                    UserBody userBody = userBodies.get(i);
        %>
        <tr>
            <td><input type="checkbox" /></td>
            <td><%=i+1%></td>
            <td><a href="#"><%=userBody.getUser().getId()%></a></td>
            <td><%=userBody.getGroup().getId()%></td>
            <td><%=userBody.getUser().getEmail()%></td>
            <td><%=userBody.getUser().getFirstName()%><%=userBody.getUser().getLastName()%></td>
            <td>
                <div class="am-btn-toolbar">
                    <div class="am-btn-group am-btn-group-xs">
                        <button class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 编辑</button>
                        <button class="am-btn am-btn-default am-btn-xs am-hide-sm-only"><span class="am-icon-copy"></span> 复制</button>
                        <button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 删除</button>
                    </div>
                </div>
            </td>
        </tr>
        <%
                }
        %>


        </tbody>
    </table>
