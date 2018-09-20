<%@ page import="org.activiti.engine.identity.User" %>
<%@ page import="org.activiti.engine.identity.Group" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="com.zking.enetity.MenuBody" %>
<%@ page import="com.zking.pojo.Menu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js fixed-layout">
<%
  request.getAttribute("data");
%>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title><%=request.getAttribute("title1")%><%=request.getAttribute("title2")%></title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
  <script src="../assets/js/amazeui.ie8polyfill.min.js"></script>

  <script src="../assets/js/jquery.min.js"></script>
  <script src="../assets/js/amazeui.min.js"></script>
  <script src="../assets/js/app.js"></script>
  <script src="../assets/js/jquery.pagination.js"></script>
  <script src="../assets/js/common.js"></script>
  <script src="../assets/js/iscroll.min.js"></script>
  <script src="../assets/js/address.min.js"></script>
  <link rel="icon" type="image/png" href="../assets/i/favicon.png">
  <link rel="apple-touch-icon-precomposed" href="../assets/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content=<%=request.getAttribute("apple_title")%> />
  <link rel="stylesheet" href="../assets/css/amazeui.min.css"/>
  <link rel="stylesheet" href="../assets/css/admin.css">
  <link rel="stylesheet" href="../assets/css/amazeui.address.min.css">
  <link rel="stylesheet" href="../assets/css/timeLine.css">
  <script src="https://gw.alipayobjects.com/os/antv/assets/g2/3.0.12/g2.min.js"></script>
  <script src="https://gw.alipayobjects.com/os/antv/assets/g6/1.2.8/g6.min.js"></script>
</head>
<body>
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="https://www.google.cn/chrome/browser/desktop/index.html" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->

<header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand" onclick="flushPage('common/home')">
    <strong><%=request.getAttribute("title1")%></strong> <small><%=request.getAttribute("title2")%></small>
  </div>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">

    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:">
          <span class="am-icon-users"></span> <%=((User)session.getAttribute("User")).getFirstName()+((User)session.getAttribute("User")).getLastName()+"("+((Group)session.getAttribute("Group")).getName()+")"%> <span class="am-icon-caret-down"></span>
        </a>
        <ul class="am-dropdown-content">
          <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
          <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
          <li><a href="../login/over"><span class="am-icon-power-off"></span> 退出</a></li>
        </ul>
      </li>
      <li class="am-hide-sm-only"><a href="javascript:" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
    </ul>
  </div>
</header>

<div class="am-cf admin-main">
  <!-- sidebar start -->
  <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
      <ul class="am-list admin-sidebar-list">
        <%
          List<MenuBody> menu = (List<MenuBody>) request.getAttribute("menu");
          if (menu!=null)
          for (MenuBody e:menu) {
              List<Menu> small = e.getSmallMenu();
              if(small!=null){
        %>
        <li class="admin-parent">
          <a class="am-cf" data-am-collapse="{target: '#collapse-nav'}"><span class="<%=e.getBigMenu().getIcon()%>"></span> <%=e.getBigMenu().getName()%> <span class="am-icon-angle-right am-fr am-margin-right"></span></a>
          <ul class="am-list am-collapse admin-sidebar-sub am-in" id="<%=e.getBigMenu().getId()%>">
            <%
              for (Menu m:small) {
            %>
            <li><a href="javascript: void(0)" onclick="flushPage('<%=m.getUri()%>',1)"><span class="<%=m.getIcon()%>"></span> <%=m.getName()%></a></li>
            <%
              }
            %>
          </ul>
        </li>
        <%
              }else{
        %>
        <li><a href="javascript: void(0)" onclick="flushPage('<%=e.getBigMenu().getUri()%>',1)"><span class="<%=e.getBigMenu().getIcon()%>"></span> <%=e.getBigMenu().getName()%></a></li>
        <%
              }
          }
        %>
      </ul>

      <div class="am-panel am-panel-default admin-sidebar-panel">
        <div class="am-panel-bd">
          <p><span class="am-icon-bookmark"></span> 公告</p>
          <p>欢迎使用MSM系统。—— 作者</p>
        </div>
      </div>

      <div class="am-panel am-panel-default admin-sidebar-panel">
        <div class="am-panel-bd">
          <p><span class="am-icon-tag"></span> wiki</p>
          <p>Welcome to MSM系统</p>
        </div>
      </div>
    </div>
  </div>
  <!-- sidebar end -->
  <!-- loading start -->
  <div class="am-modal am-modal-loading am-modal-no-btn" tabindex="-1" id="my-modal-loading">
    <div class="am-modal-dialog">
      <div class="am-modal-hd">正在载入...</div>
      <div class="am-modal-bd">
        <span class="am-icon-spinner am-icon-spin"></span>
      </div>
    </div>
  </div>
  <!-- loading end -->

  <!-- alert start -->
  <div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
    <div class="am-modal-dialog">
      <div class="am-modal-hd" id="alert-title">系统提示</div>
      <div class="am-modal-bd" id="alert-message">
        Hello world！
      </div>
      <div class="am-modal-footer">
        <span class="am-modal-btn">确定</span>
      </div>
    </div>
  </div>
  <!-- alert end -->
  <!-- content start -->
  <div class="admin-content">
    <div class="admin-content-body" id="admin-content-body">



    </div>


    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">© 2017 湖南城市学院 张弘毅 使用界面模板<a href="http://amazeui.org/">Amazeui</a> 遵守<a href="javascript: void(0)" onclick="flushPage('common/license',1)">BSD许可证</a></p>
    </footer>
  </div>
  <!-- content end -->

</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>


<script>

</script>
</body>
</html>
