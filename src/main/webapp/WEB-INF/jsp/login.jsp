<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>Login Page | Amaze UI Example</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="alternate icon" type="image/png" href="../assets/i/favicon.png">
  <link rel="stylesheet" href="../assets/css/amazeui.min.css"/>
  <script src="../assets/js/jquery.min.js"></script>
  <script>
    function SUBMIT() {

      $("#FORM").submit();
    }
  </script>
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1>保健品销售信息管理系统</h1>
    <p>销售，库存，合同，账务，用户管理</p>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <h3>登录</h3>
    <hr>
    <p style="color: red;"><%
      if (request.getParameter("error_info")!=null){
    	  out.print(request.getParameter("error_info").toString());
      }
    %></p>
    <br>
    <br>
    <form method="post" action="<%=basePath%>login/doLogin" class="am-form" id="FORM">
      <label for="username">账户:</label>
      <input type="text" name="username" id="username" value="" placeholder="输入账户" required>
      <br>
      <label for="password">密码:</label>
      <input type="password" name="password" id="password" value="" placeholder="输入密码" required>
      <br>
      <label for="remember-me">
        <input id="remember-me" type="checkbox">
        记住密码
      </label>
      <br />
      <div class="am-cf">
        <input type="submit" name="sub" value="登 录" class="am-btn am-btn-primary am-btn-sm am-fl">
      </div>
    </form>


    <hr>
    <p class="am-padding-left">© 2017 湖南城市学院 张弘毅 使用界面模板<a href="http://amazeui.org/">Amazeui</a> 遵守<a href="https://opensource.org/licenses/BSD-3-Clause">BSD许可证</a></p>
  </div>
</div>
</body>
</html>
