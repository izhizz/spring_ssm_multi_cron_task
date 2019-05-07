<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>

<html>
 <body style="background: beige; margin: 300px 600px 200px;" >
	<div class="container-fluid">
		<div class="page-header">
			<div class="pull-left">
				<h1>${opName }</h1>
			</div>
		</div>
		
		<div class="box box-bordered bordered-top">
			<div class="box-content nopadding">																
				
				<h2>${message }!!!</h2>
				<p/>
				<a href="${pageContext.request.contextPath }/quartz/listJob">查看任务的列表</a>
			
			</div>
		</div>
	</div>
</body>

</html>