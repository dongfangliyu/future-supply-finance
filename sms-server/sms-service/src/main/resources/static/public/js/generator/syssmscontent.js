$(function () {
    $("#jqGrid").jqGrid({
        url: '../syssmscontent/list',
        datatype: "json",
        colModel: [	
            { label: '编号', name: 'number', index: 'number', width: 80 },
			{ label: '模板内容', name: 'content', index: 'content', width: 380 }, 
			{ label: '状态', name: 'status', index: 'status', width: 80,
				formatter: function(cellvalue, options, rowObject){
					if(cellvalue == "1"){
						return "有效";
					}else if(cellvalue == "0"){
						return "无效";
					}else{
						return "";
					}
				}
			}, 				
			{ label: '备注', name: 'remark', index: 'remark', width: 80 },
			{ label: '适用性', name: 'universal', index: 'universal', width: 80, 
				formatter: function(cellvalue, options, rowObject){
					if(cellvalue == "0"){
						return "通用";
					}else if(cellvalue == "-1"){
						return "点集";
					}else{
						return "点集";
					}
				}
			}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        	//$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-y" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		isReadOnly:true,
		title: null,
		q:{
			number:null
		},
		sysSmsContent: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.isReadOnly = false;
			vm.title = "新增";
			vm.sysSmsContent = {"universal":"0", "status":"1"};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
			vm.isReadOnly = true;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysSmsContent.id == null ? "../syssmscontent/save" : "../syssmscontent/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysSmsContent),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
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
				    url: "../syssmscontent/delete",
				    data: JSON.stringify(ids),
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
		},
		getInfo: function(id){
			$.get("../syssmscontent/info/"+id, function(r){
                vm.sysSmsContent = r.sysSmsContent;
            });
		},
		reload: function (event) {
			vm.showList = true;
			vm.isReadOnly = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'number': vm.q.number},
                page:page
            }).trigger("reloadGrid");
		}
	}
});