$(function () {
    $("#jqGrid").jqGrid({
        url: '../syssmsrecorddetail/list',
        datatype: "json",
        colModel: [	
			{ label: '手机号', name: 'mobile', index: 'mobile', width: 100 }, 
			{ label: '状态', name: 'status', index: 'status', width: 50,
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
			{ label: '短信ID', name: 'msgId', index: 'msg_id', width: 160 }, 	
			{ label: '短信内容', name: 'content', index: 'content', width: 140 },
			{ label: '账号编码', name: 'channelCode', index: 'channel_code', width: 70 }, 	
			{ label: '短信记录ID', name: 'recordId', index: 'record_id', width: 70 },
			{ label: '重试次数', name: 'retryNumber', index: 'retry_number', width: 80 }, 			
			{ label: '调用接口', name: 'type', index: 'type', width: 80, 
				formatter:function(cellvalue, options, rowObject){
					if(cellvalue == "CL"){
						return "创蓝";
					}else if(cellvalue == "QJT"){
						return "乾景通";
					}else if(cellvalue == "1"){
						return "语音";
					}else{
						return "";
					}
				}
			}, 			
			{ label: '模板编码', name: 'number', index: 'number', width: 80 }, 			
			{ label: '响应状态码', name: 'statusCode', index: 'status_code', width: 90 }, 			
			{ label: '响应信息', name: 'meseage', index: 'meseage', width: 80 },			
			{ label: '耗时(毫秒)', name: 'timeConsuming', index: 'time_consuming', width: 80 }, 			
			{ label: '短信状态', name: 'smsState', index: 'sms_state', width: 80 },
			{ label: '发送时间', name: 'sendTime', index: 'send_time', width: 150 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        autowidth:true,
        shrinkToFit:false,
        autoScroll: false,
        rownumWidth: 25,
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
			msgid:null,
			recordId:null,
			status:"",
			channel_code: null,
			type: "",
			number: null
		},
		sysSmsRecordDetail: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.sysSmsRecordDetail = {};
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
			var url = vm.sysSmsRecordDetail.id == null ? "../syssmsrecorddetail/save" : "../syssmsrecorddetail/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.sysSmsRecordDetail),
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
				    url: "../syssmsrecorddetail/delete",
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
			$.get("../syssmsrecorddetail/info/"+id, function(r){
                vm.sysSmsRecordDetail = r.sysSmsRecordDetail;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'mobile': vm.q.mobile, 'msgId': vm.q.msgid, 'status': vm.q.status, 'recordId':vm.q.recordId,
					'channel_code': vm.q.channel_code, 'type': vm.q.type, 'number': vm.q.number},
                page:page
            }).trigger("reloadGrid");
		}
	}
});