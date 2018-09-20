<%@ page import="org.activiti.engine.repository.ProcessDefinition" %>
<%@ page import="java.util.List" %>
<%@ page import="org.activiti.engine.runtime.ProcessInstance" %><%--
  Created by IntelliJ IDEA.
  User: Jerry
  Date: 2017/10/19
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%
    List<ProcessDefinition> processDefinitions = (List<ProcessDefinition>)request.getAttribute("processDefinitions");
//		processDefinitions.get(0).getId();
//		processDefinitions.get(0).getKey();
//		processDefinitions.get(0).getName();
//		processDefinitions.get(0).getResourceName();
//		processDefinitions.get(0).getDiagramResourceName();
//		processDefinitions.get(0).getCategory();
//		processDefinitions.get(0).getDeploymentId();
//		processDefinitions.get(0).getDescription();
//		processDefinitions.get(0).getTenantId();
//		processDefinitions.get(0).getVersion();
//		processDefinitions.get(0).getEngineVersion();
    List<ProcessInstance> processInstances = (List<ProcessInstance>)request.getAttribute("processInstances");
//        ProcessDefinitionId
//		Name
//		BusinessKey
//		DeploymentId
//		Description
//		LocalizedDescription
//		LocalizedName
//		ProcessDefinitionKey
//		ProcessDefinitionId
//		ProcessDefinitionName
//		ProcessDefinitionVersion
//		StartTime
//		StartUserId
//		TenantId
%>
<head>
    <script src="../js/jquery-1.9.1.min.js"></script>
    <link href="../css/bootstrap.css">
    <title>任务管理</title>
</head>
<body>
<form action="/test/addTask" ID="Form">
    <table class="table table-bordered" title="流程实例表（已经部署）">
        <thead>
            <tr>
                <th>选择</th>
                <th>Id</th>
                <th>Key</th>
                <th>Name</th>
                <th>ResourceName</th>
                <th>DiagramResourceName</th>
                <th>Category</th>
                <th>DeploymentId</th>
                <th>Description</th>
                <th>TenantId</th>
                <th>Version</th>
                <th>EngineVersion</th>
            </tr>
        </thead>
        <tbody>
        <% if (processDefinitions!=null){
            for (ProcessDefinition e: processDefinitions ) {
        %>
            <tr>
                <td><input type="checkbox" name="taskidlist" VALUE="<%=e.getKey()%>"></td>
                <td><%=e.getId()%></td>
                <td><%=e.getKey()%></td>
                <td><%=e.getName()%></td>
                <td><%=e.getResourceName()%></td>
                <td><img src="../<%=e.getDiagramResourceName()%>"></td>
                <td><%=e.getCategory()%></td>
                <td><%=e.getDeploymentId()%></td>
                <td><%=e.getDescription()%></td>
                <td><%=e.getTenantId()%></td>
                <td><%=e.getVersion()%></td>
                <td><%=e.getEngineVersion()%></td>
            </tr>
        <%
            }
        }
        %>
        </tbody>
    </table>
</form>
<input type="button" value="提交" onclick="SUBMIT()">
    <table class="table table-bordered" title="任务实例表（已经挂载）">
        <thead>
        <tr>

            <th>ProcessDefinitionId</th>
            <th>Name</th>
            <th>BusinessKey</th>
            <th>DeploymentId</th>
            <th>Description</th>
            <th>LocalizedDescription</th>
            <th>LocalizedName</th>
            <th>ProcessDefinitionKey</th>
            <th>ProcessDefinitionId</th>
            <th>ProcessDefinitionName</th>
            <th>ProcessDefinitionVersion</th>
            <th>StartTime</th>
            <th>StartUserId</th>
            <th>TenantId</th>
            <th>BusinessKey</th>
        </tr>
        </thead>
        <tbody>

        <% if (processInstances!=null){
            for (ProcessInstance e: processInstances ) {
        %>
        <tr>

            <td><%=e.getProcessDefinitionId()%></td>
            <td><%=e.getName()%></td>
            <td><%=e.getBusinessKey()%></td>
            <td><%=e.getDeploymentId()%></td>
            <td><%=e.getDescription()%></td>
            <td><%=e.getLocalizedDescription()%></td>
            <td><%=e.getLocalizedName()%></td>
            <td><%=e.getProcessDefinitionKey()%></td>
            <td><%=e.getProcessDefinitionId()%></td>
            <td><%=e.getProcessDefinitionName()%></td>
            <td><%=e.getProcessDefinitionVersion()%></td>
            <td><%=e.getStartTime()%></td>
            <td><%=e.getStartUserId()%></td>
            <td><%=e.getTenantId()%></td>
            <td><%=e.getBusinessKey()%></td>
        </tr>
        <%
                }
            }
        %>

        </tbody>
    </table>

<script>
    function SUBMIT() {
        $("#Form").submit();
    }
</script>
</body>
</html>
