//字符串json转数组json
function strToJson(str){
    return JSON.parse(str);
}

// 此处调用的就是我们写好的引入的方法，会将带有name的input，全部转为JSON提交表单
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


// 获取地址栏参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}


function currency_type(types) {
    switch(types)
    {
        case "CNY":
            return "人民币"
            break;
        case "USD":
            return "美元"
            break;
        default:
            break;
    }
}
function income_type(types) {
    switch(types)
    {
        case 0:
            return "支出"
            break;
        case 1:
            return "收入"
            break;
        default:
            break;
    }
}
//接口返回异常，超时登录
function handleAjaxError() {
    sweetAlert({
            title: "",
            text: "登录超时，点击确定返回登录页面！",
            type: "warning",
            showCancelButton: false,
            cancelButtonText: "取消",
            confirmButtonText: "确定",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                window.location.href = "/login";
            }
        });

}
//对多位小数进行四舍五入
//num是要处理的数字  v为要保留的小数位数
function decimal(num,v){
    var vv = Math.pow(10,v);
    return Math.round(num*vv)/vv;
}

// 获取当前年月日
function showdate(){
    var mydate = new Date();
    var str = "" + mydate.getFullYear() + "-";
    str += (mydate.getMonth()+1) + "-";
    str += mydate.getDate() ;
    return str;
}

// 实时更新未处理任务
setInterval(function(){
    task_nodeal();
    task_dealed()
},10000);

var tastnum=0;
// 显示未处理任务条数
function task_nodeal() {
    $.ajax({
        url: "/task/queryUndo",
        type: "get",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            if(data.length>0){
                if($("#servicebranch").html()=="总部"){
                    $("#hide_tast").val(data.length);
                    $("#nodeal_num").html(data.length);
                    $("#msg_status").addClass("danger");
                    $("#tast_num").html(data.length);
                    $("#indexAlert").show();
                    if( data.length>tastnum){
                        $.notifySetup({sound: '../audio/notify.mp3'});
                        $("#indexAlert").notify({sticky: true});
                        tastnum=data.length;
                    }
                }

            }
            else if (data.code=="403") {
                $("#msg_status").hide();
                $("#indexAlert").hide();
            }
            else {
                $("#msg_status").removeClass("danger");
                $("#indexAlert").hide();
            }

        }
    });

}

// 显示已处理任务条数
var nodeal=0;
function taskend() {
    $.ajax({
        url: "/task/queryDone",
        type: "get",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            if(data.length>0){
                nodeal=data.length;
            }

        }
    });

}


function task_dealed() {
    $.ajax({
        url: "/task/queryDone",
        type: "get",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            if(data.length>0){
                if($("#servicebranch").html()!="总部"){
                    if( data.length>nodeal){
                        $("#networkAlert").show();
                        $.notifySetup({sound: '../audio/netnotice.mp3'});
                        $("#networkAlert").notify({sticky: true});
                        nodeal=data.length;
                    }


                }
            }

        }
    });

}