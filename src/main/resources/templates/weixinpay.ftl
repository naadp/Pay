<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta name="keywords" content="支付宝微信在线支付案例">
<link rel="stylesheet" href="/layui/css/layui.css">
<meta name="description" content="支付宝微信在线支付">
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
			  <legend>微信扫码在线支付</legend>
			</fieldset>
	        <table>
				<tr>
					<td>商品名称：</td>
					<td>
						${order.subject}
					</td>
				</tr>
				<tr>
					<td>商品描述：</td>
					<td>
						${order.body}
					</td>
				</tr>
				<tr>
					<td>交易金额：</td>
					<td>
						${order.totalAmount}(元)
					</td>
				</tr>
			</table>
	        <img src="/weixinpay/loadPayImage?id=${order.id?c}"></img>
	    </div>
	</div>
	
</div>
<script src="/layui/layui.js"></script>
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript">

	function orderStatus(){
      	$.post("/weixinpay/loadPayState",{  
            id:${order.id?c}
        },  
        function(data,status){ 
            if(data==1){  
                location.href = "/weixinpay/returnUrl";  
            }  
        });
      }
	      
	  setInterval("orderStatus()",3000);
	  
</script>

</body>
</html>