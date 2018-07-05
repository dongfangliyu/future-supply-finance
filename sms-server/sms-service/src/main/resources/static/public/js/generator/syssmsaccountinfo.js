$(function () {
    $("#jqGrid").jqGrid({
        url: '../syssmsaccountinfo/list',
        datatype: "json",
        colModel: [			
			{ label: '应用地址', name: 'uri', index: 'uri', width: 200 }, 			
			{ label: '应用账号', name: 'account', index: 'account', width: 150 }, 			
			{ label: '应用密码', name: 'password', index: 'password', width: 150 }, 			
			{ label: '通道编码', name: 'channelCode', index: 'channel_code', width: 70 }, 			
			{ label: '通道备注', name: 'channelRemark', index: 'channel_remark', width: 140 },
			{ label: '账户类型', name: 'type', index: 'type', width: 80,
				formatter: function(cellvalue, options, rowObject){
					if(cellvalue == "1"){
						return "行业账户";
					}else if(cellvalue == "2"){
						return "营销账户";
					}else{
						return "";
					}
				}
			}, 			
			{ label: '短信平台', name: 'flat', index: 'flat', width: 80,
				formatter: function(cellvalue, options, rowObject){
					if(cellvalue == "1"){
						return "点集";
					}else{
						return "";
					}
				}
			},		
			{ label: '状态', name: 'status', index: 'status', width: 60,
				formatter: function(cellvalue, options, rowObject){
					if(cellvalue == "1"){
						return "启用";
					}else if(cellvalue == "2"){
						return "停用";
					}else{
						return "";
					}
				}
			}, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 150 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 150 }		
			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        shrinkToFit:false,
        autoScroll: false,
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
		title: null,
		isReadOnly:true,
		sysSmsAccountInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.isReadOnly = false,
			vm.title = "新增";
			vm.sysSmsAccountInfo = {"status":"1"};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
			vm.isReadOnly = true,
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysSmsAccountInfo.id == null ? "../syssmsaccountinfo/save" : "../syssmsaccountinfo/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysSmsAccountInfo),
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
				    url: "../syssmsaccountinfo/delete",
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
			$.get("../syssmsaccountinfo/info/"+id, function(r){
                vm.sysSmsAccountInfo = r.sysSmsAccountInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			vm.isReadOnly = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});