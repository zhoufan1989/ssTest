$(function () {
    $("#jqGrid").jqGrid({
        url: '../file/data/list',
        datatype: "json",
//        postData:{'gridPager':{'currPage':1,'pageSize':10}}, //
        colModel: [			
			{ label: '名称', name: 'name', width: 75 },
			{ label: '年龄', name: 'age', width: 75 },
			{ label: '创建时间', name: 'addTime', width: 80}                   
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
		name:null
	},
	methods: {
		query: function () {
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'name': vm.name},
                page:1 
            }).trigger("reloadGrid");
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			
			location.href = "file_add.html?id="+id;
		},
		excelExport: function (event){
			var url = "../file/data/fileDataExport";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.name),
			    success: function(r){
			    	if(r.code === 0){
						alert('文件上传成功', function(index){
							vm.back();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../file/data/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert("ids" + ids);
							console.log("ids" + ids);
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