<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@include file="common/tag.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@include file="common/head.jsp" %>
    <title>秒杀列表页</title>
  </head>
  <body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀列表</h2>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情页</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="sk" items="${list}">
							<tr>
								<td>${sk.name}</td>
								<td>${sk.number}</td>
								<td>
									<fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
								</td>
								<td>
									<a class="btn btn-info" href="/SecKill/seckill/${sk.seckillId}/detail" target="_blank">link</a>
								</td>
							</tr>						
						</c:forEach>
					</tbody>
				</table>
			</div>				
		</div>
	</div>    

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  </body>
</html>