<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<%@include file="common/head.jsp" %>
<title>秒杀详情页</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="pannel-heading">${seckill.name}</div>
		</div>
		<div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"></span>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
	</div>
	
	 <div id="killPhoneModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title text-center">
                            <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                        </h3>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input type="text" name="killphone" id="killphoneKey" placeholder="填手机号^O^" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <span id="killphoneMessage" class="glyphicon"></span>
                        <button type="button" id="killPhoneBtn" class="btn btn-success">
                            <span class="glyphicon glyphicon-phone"></span>
                            Submit
                        </button>
                    </div>
                </div>
            </div>
        </div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    
     <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
     <script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.js"></script>
     <!-- 交互逻辑 -->
     <script src="/SecKill/resources/js/seckill.js" type="text/javascript"></script>
     <script type="text/javascript">
     	
     	$(function(){
     		//使用EL表达式传入参数
     		seckill.detail.init({
     			seckillId : "${seckill.seckillId}",
     			startTime : "${seckill.startTime.time}",
     			endTime : "${seckill.endTime.time}"
     		});    		
     	});
     </script>	
     
</body>
</html>