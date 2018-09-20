<%@ page import="java.util.List" %>
<%@ page import="org.activiti.engine.task.Task" %>
<%@ page import="org.activiti.engine.identity.User" %><%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2017/10/20
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>任务完成</title>
    <script src="../js/jquery-1.9.1.min.js"></script>
    <script>
        function SUBMIT() {
            $("#Form").submit();
        }
    </script>
</head>
<%
    List<Task> tasks = (List<Task>)request.getAttribute("tasks");
%>
<body>
<h3>登陆用户：<%=((User)session.getAttribute("User")).getId()%></h3>
<form action="/login/doTask" ID="Form" method="post">
    <table title="任务清单">
        <thead>
        <tr>
            <th>选择</th>
            <th>任务编码</th>
            <th>任务名</th>
            <th>任务拥有人</th>
            <th>Assignee</th>
            <th>任务创建时间</th>
            <th>任务结束时间</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (tasks!=null){
                for (Task e:tasks) {
        %>
        <tr>
            <td><input type="checkbox" name="taskidlist" VALUE="<%=e.getId()%>"></td>
            <td><%=e.getId()%></td>
            <td><%=e.getName()%></td>
            <td><%=e.getOwner()%></td>
            <td><%=e.getAssignee()%></td>
            <td><%=e.getCreateTime()%></td>
            <td><%=e.getDueDate()%></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</form>
    <input type="button" value="提交" onclick="SUBMIT()">
</body>
</html>
