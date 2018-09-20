<%@ page import="org.activiti.engine.identity.Group" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2017/10/19
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/bootstrap.css">
    <title>addGroup</title>
</head>
<%
    List<Group> list = (List<Group>) request.getAttribute("grouplist");
%>
<body>
现有的组：
<table class="table table-bordered">
    <thead>
        <th>id</th>
        <th>name</th>
        <th>type</th>
    </thead>
    <tbody>
    <%
        if(list.size()!= 0) {
            for (Group a : list) {
    %>
        <tr>
            <td><%=a.getId()%></td>
            <td><%=a.getName()%></td>
            <td><%=a.getType()%></td>
        </tr>
    <%
            }
        }else{
    %>
        暂无数据
    <%
        }
    %>
    </tbody>
</table>
<form action="/test/addGroup" METHOD="post">
    <table>
        <tr>
            <td><label>组名：</label></td>
            <td><input type="text" name="groupname"></td>
        </tr>
        <tr>
            <td><label>组类别：</label></td>
            <td><input type="text" name="grouptype"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
