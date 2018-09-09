//用户ID
var id = T.p("id");
var vm = new Vue({
	el:'#rrapp',
	data:{
		title:"新增数据",
		file:{}
	},
	created: function() {
		if(id != null){
			this.title = "修改数据";
			this.getInfo(id)
		}
		
    },
	methods: {
		getInfo: function(id){
			$.get("../file/data/info/"+id, function(r){
				vm.file = r.file;
			});
		},
		saveOrUpdate: function (event) {
			var url = "../file/data/save";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.file),
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