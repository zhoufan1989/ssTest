$(function () {
    $("#jqGrid").jqGrid({
        url: '../sys/role/list',
        datatype: "json",
        colModel: [			
			{ label: '角色名称', name: 'roleName', width: 75 },
			{ label: '备注', name: 'remark', width: 100 },
			{ label: '创建时间', name: 'addDate', width: 80}                   
        ],
		viewrecords: true,
        height: 400,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
        	root: "list",
            page: "nowPage",
            total: "totalPage",
            records: "totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		roleName:null
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'roleName': vm.roleName},
                page:1 
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "role_add.html?id="+id;
		},
		del: function (event) {
			var roleIds = getSelectedRows();
			if(roleIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../sys/role/delete",
				    data: JSON.stringify(roleIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		}
	}
});