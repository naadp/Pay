
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<meta name="keywords" content="在线支付案例">
<link rel="stylesheet" href="/Pay/layui/css/layui.css">
<meta name="description" content=" 支付宝微信在线支付案例">
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
    
    	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
		  <legend>在线支付</legend>
		</fieldset>
    
    
    	<form class="layui-form"  method="post">
    	
    		<div class="layui-form-item">
			    <label class="layui-form-label">打赏金额:</label>
			    <div class="layui-input-block">
			      <input type="radio" name="productId" value="1" title="5160元-给naadp买蝴蝶刀">
			    </div>
			    <div class="layui-input-block">
			      <input type="radio" name="productId" value="2" title="22800元-给naadp买咆哮" checked="">
			    </div>
			    <div class="layui-input-block">
			      <input type="radio" name="productId" value="3" title="8550元-给naadp买巨龙传说">
			    </div>
			  </div>
			  
    		<div class="layui-form-item">
			    <label class="layui-form-label">火钳刘明:</label>
			    <div class="layui-input-block">
			      <input type="text" name="nickName" lay-verify="required" placeholder="大佬儿，请留下您的大名" autocomplete="off" class="layui-input">
			    </div>
			</div>
			
			<div class="layui-form-item">
			    <label class="layui-form-label">QQ:</label>
			    <div class="layui-input-block">
			      <input type="text" name="qq" lay-verify="required|number" placeholder="大佬儿，请留下的QQ号，容小弟日后报答" autocomplete="off" class="layui-input">
			    </div>
			</div>
			
			<div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">留言:</label>
			    <div class="layui-input-block">
			      <textarea name="message" placeholder="大佬儿说点儿什么吧！" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  
			  <div class="layui-form-item">
			    <label class="layui-form-label">支付方式:</label>
			    <div class="layui-input-block">
			      <input type="radio" name="way" value="支付宝" title="支付宝" checked="">
			      <input type="radio" name="way" value="微信" title="微信">
			    </div>
			  </div>
    	
    		<div class="layui-form-item">
			    <div class="layui-input-block">
			      <button class="layui-btn" lay-submit="" lay-filter="paySubmit">支付提交</button>
			    </div>
			  </div>
  
    	</form>
    	
    	
      </div>
     </div>

<script src="/Pay/layui/layui.js"></script>

<script>
//注意：导航 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function(){
  var element = layui.element;
});
</script>

<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
layui.use(['form'], function(){
  var form = layui.form;
 
  //监听提交
  form.on('submit(paySubmit)', function(data){
    if(data.field.way=='支付宝'){
    	data.form.action="/Pay/alipay/pay";
    }else if(data.field.way=='微信'){
    	data.form.action="/Pay/weixinpay/pay";
    }
    return true;
  });
});
</script>

</body>