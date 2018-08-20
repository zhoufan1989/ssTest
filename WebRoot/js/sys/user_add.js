//用户ID
var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增管理员",
		roleList:{},
		user:{
			status:1,
			roleIdList:[]
		}
	},
	created: function() {
		if(id != null){
			this.title = "修改管理员";
			this.getUser(id)
		}
		//获取角色信息
		this.getRoleList();
    },
	methods: {
		getUser: function(id){
			$.get("../sys/user/info/"+id, function(r){
				vm.user = r.user;
			});
		},
		getRoleList: function(){
			$.get("../sys/role/select", function(r){
				vm.roleList = r.list;
			});
		},
		saveOrUpdate: function (event) {
			var url = vm.user.id == null ? "../sys/user/save" : "../sys/user/update" ;
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.user),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.back();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		back: function (event) {
			history.go(-1);
		}
	}
});