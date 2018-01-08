<#include "/layout/mainLayout.ftl" encoding="utf8">
<@base baseTitle="404 页面不存在" 
		baseCss=[
		"/lib/bootstrap/dist/css/bootstrap.min.css",
		"/lib/font-awesome/css/font-awesome.min.css",
		"/lib/Ionicons/css/ionicons.min.css",
		"/lib/admin-lte/dist/css/AdminLTE.min.css",
		"/lib/admin-lte/dist/css/skins/_all-skins.min.css",
		"/lib/morris.js/morris.css",
		"/lib/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css",
		"/lib/bootstrap-daterangepicker/daterangepicker.css",
		"/lib/bootstrap-wysihtml5/dist/bootstrap-wysihtml5-0.0.2.css"
		]
		baseJs=[
		"/lib/jquery/dist/jquery.min.js",
		"/lib/jquery-ui/jquery-ui.min.js",
		"/lib/bootstrap/dist/js/bootstrap.min.js",
		"/lib/raphael/raphael.min.js",
		"/lib/morris.js/morris.min.js",
		"/lib/jquery-sparkline/dist/jquery.sparkline.min.js",
		"/lib/jquery-knob/dist/jquery.knob.min.js",
		"/lib/moment/min/moment.min.js",
		"/lib/bootstrap-daterangepicker/daterangepicker.js",
		"/lib/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js",
		"/lib/bootstrap-wysihtml5/dist/bootstrap-wysihtml5-0.0.2.min.js",
		"/lib/jquery-slimscroll/jquery.slimscroll.min.js",
		"/lib/fastclick/lib/fastclick.js",
		"/lib/admin-lte/dist/js/adminlte.min.js",
		"/lib/admin-lte/dist/js/pages/dashboard.js",
		"/lib/admin-lte/dist/js/demo.js"
		] 
		>
<div class="error-page">
	<h2 class="headline text-yellow">404</h2>

	<div class="error-content">
		<h3>
			<i class="fa fa-warning text-yellow"></i> 哎哟！！！页面被大风吹跑了！
		</h3>

		<p>
			无法找到您访问的页面。您可以点击 <a href="${ctx}/index">返回首页</a> 或尝试进行页面搜索。
		</p>

		<form class="search-form">
			<div class="input-group">
				<input type="text" name="search" class="form-control" placeholder="搜索">

				<div class="input-group-btn">
					<button type="submit" name="submit" class="btn btn-warning btn-flat">
						<i class="fa fa-search"></i>
					</button>
				</div>
			</div>
			<!-- /.input-group -->
		</form>
	</div>
	<!-- /.error-content -->
</div>
</@base>
