<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta name="keywords" content="支付宝微信在线支付案例">
<link rel="stylesheet" href="/Pay/layui/css/layui.css">
<meta name="description" content="支付宝微信在线支付在线案例">
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-status-bar-style" content="black"> 
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
</head>
<body>
<div class="layui-container">
	
	<#include "/common/head.ftl">
	
	<div class="layui-row">
	    <div class="layui-col-md12">
	        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
			  <legend>同步地址</legend>
			</fieldset>
	        ${message}
	    </div>
	</div>
	
</div>
<script src="/Pay/layui/layui.js"></script>
</body>
</html>