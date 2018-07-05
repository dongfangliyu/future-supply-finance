$(function () {
    $("#jqGrid").jqGrid({
        url: '../syssmsrecord/list',
        datatype: "json",
        colModel: [		
            { label: 'id', name: 'id', index: 'id', width: 80 },
			{ label: '手机号', name: 'mobile', index: 'mobile', width: 100 },
			{ label: '状态', name: 'status', index: 'status', width: 60,
				formatter: function(cellvalue, options, rowObject){
					if(cellvalue == "0"){
						return "<font color='red'>失败</font>";
					}else if(cellvalue == "1"){
						return "成功";
					}else{
						return "";
					}
				}
			},
			{ label: '发送IP', name: 'send_ip', index: 'send_ip', width: 80 }, 			
			{ label: '短信ID', name: 'msgid', index: 'msgid', width: 150 }, 	
			{ label: '短信内容', name: 'content', index: 'content', width: 140 },
			{ label: '通道编号', name: 'channel_code', index: 'channel_code', width: 80 }, 			
			{ label: '重试次数', name: 'retryNumber', index: 'retry_number', width: 80 }, 			
			{ label: '调用接口', name: 'type', index: 'type', width: 80,
				formatter:function(cellvalue, options, rowObject){
					if(cellvalue == "DJ"){
						return "点集";
					}else if(cellvalue == "QJT"){
						return "乾景通";
					}else if(cellvalue == "1"){
						return "语音";
					}else{
						return "";
					}
				}
			}, 			
			{ label: '模板编号', name: 'number', index: 'number', width: 80 }, 			
			{ label: '耗时(毫秒)', name: 'timeConsuming', index: 'time_consuming', width: 80 }, 			
			/*{ label: '回调状态', name: 'receiveState', index: 'receive_state', width: 80 },*/	
			{ label: '发送时间', name: 'send_time', index: 'send_time', width: 150 },	
			{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 150 }
        ],
		viewrecords: true,
        height: 385,
        shrinkToFit:false,
        autoScroll: false,
        autowidth:true,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
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
		q:{
			mobile: null,
			msgid: null,
			status: "",
			channel_code: null,
			type: "",
			number: null
		},
		sysSmsRecord: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysSmsRecord = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.sysSmsRecord.id == null ? "../syssmsrecord/save" : "../syssmsrecord/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysSmsRecord),
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
				    url: "../syssmsrecord/delete",
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
			$.get("../syssmsrecord/info/"+id, function(r){
                vm.sysSmsRecord = r.sysSmsRecord;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'mobile': vm.q.mobile, 'msgid': vm.q.msgid, 'status': vm.q.status,
							'channel_code': vm.q.channel_code, 'type': vm.q.type, 'number': vm.q.number},
                page:page
            }).trigger("reloadGrid");
		},
		retryBatchSend: function(){
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要重发选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../retryBatchSend",
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
	}
});