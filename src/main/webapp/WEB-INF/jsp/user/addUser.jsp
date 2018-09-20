<%@ page import="java.util.List" %>
<%@ page import="org.activiti.engine.identity.Group" %>
<%@ page import="org.activiti.engine.identity.User" %><%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2017/10/19
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>增加用户</title>
    <script src="../js/jquery-1.9.1.min.js"></script>
    <link href="../css/bootstrap.css">
    <script>
        function check() {
            //校验
            if($("#username")==null||$("#username")==""){
                return false;
            }
            if($("#group")=="-1"){
                return false;
            }
            if($("#firstname")==null||$("#firstname")==""){
                return false;
            }
            if($("#lastname")==null||$("#lastname")==""){
                return false;
            }
            if($("#email")==null||$("#email")==""){
                return false;
            }
            if($("#password")==null||$("#password")==""){
                return false;
            }

            $("#Form").submit();
        }
    </script>
</head>
<body>
<%
    List<Group> list = (List<Group>) request.getAttribute("grouplist");
    List<User> users = (List<User>)request.getAttribute("userlist");
%>
<table class="table table-bordered">
    <thead>
    <tr>
        <th>id</th>
        <th>Email</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Password</th>
    </tr>
    </thead>
    <tbody>
    <%
        if(users.size()!= 0) {
            for (User a : users) {
    %>
    <tr>
        <td><%=a.getId()%></td>
        <td><%=a.getEmail()%></td>
        <td><%=a.getFirstName()%></td>
        <td><%=a.getLastName()%></td>
        <td><%=a.getPassword()%></td>
    </tr>
    <%
            }
        }else{

        }
    %>
    </tbody>
</table>
<form action="/user/addUser" id="Form" method="post">
    <table>
        <tr>
            <td><label>账户：</label></td>
            <td><input id="username" name="username"></td>
        </tr>
        <tr>
            <td><label>组：</label></td>
            <td><select id="group" name="group">
                <option value="-1">--请选择--</option>
                <%
                    if(list.size()!= 0) {
                        for (Group a : list) {
                %>
                <option value="<%=a.getId()%>"><%=a.getName()%></option>
                <%
                        }
                    }else{

                    }
                %>
            </select></td>
        </tr>
        <tr>
            <td><label>姓氏：</label></td>
            <td><input id="firstname" name="firstname"></td>
        </tr>
        <tr>
            <td><label>名字：</label></td>
            <td><input id="lastname" name="lastname"></td>
        </tr>
        <tr>
            <td><label>邮箱：</label></td>
            <td><input type="email" id="email" name="email"></td>
        </tr>
        <tr>
            <td><label>密码：</label></td>
            <td><input type="password" id="password" name="password"></td>
        </tr>
    </table>
    <input type="button" value="提交" onclick="check()">
</form>
</body>
</html>
