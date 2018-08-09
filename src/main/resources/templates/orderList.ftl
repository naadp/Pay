<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta name="keywords" content="在线支付案例">
<link rel="stylesheet" href="/Pay/layui/css/layui.css">
<meta name="description" content="支付宝微信在线支付案例">
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
			  <legend>订单列表</legend>
			</fieldset>
	    </div> 
	</div>
	
	<div class="layui-row">
	    <div class="layui-col-md12">
	        <table class="layui-hide" id="orderListTable"></table>
	    </div> 
	</div>
	
</div>
<script src="/Pay/layui/layui.js"></script>

<script>
layui.use('table', function(){
  var table = layui.table;
  
  table.render({
    elem: '#orderListTable'
    ,url:'/Pay/order/orderList'
    ,cols: [[
       {field:'nickName', width:100, title: '大佬儿昵称'}
      ,{field:'qq', width:120, title: '大佬儿QQ'}
      ,{field:'buyTime', width:180, title: '大佬儿支付时间', sort: true,align:'center'}
      ,{field:'way', width:100, title: '大佬儿支付方式',align:'center'}
      ,{field:'body',width:200, title: '订单详情'}
      ,{field:'message', title: '大佬儿寄语', minWidth: 150}
    ]]
    ,page: true
  });
});
</script>

</body>
</html>