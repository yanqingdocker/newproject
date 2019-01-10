document.writeln(" <div class=\"navbar navbar-default navbar-fixed-top top-main pc_header\" style=\"background:rgba(0,0,0,0.0) ;\" >");
document.writeln("  <div class=\"container-fluid top-bar p0 car_bg f12 bl3\">");
document.writeln(" <div class=\"container row m p0\">");
document.writeln("  <div class=\"col-lg-6\"></div>");
document.writeln("  <div class=\"col-lg-6 tr\">");
document.writeln("  <div  class=\"log_bar_link r gray\" id=\"unlogin\"  >");
document.writeln("     <a class=\" pr10\" href=\"/CN/login\">登录</a><span class=\"white\">|</span>");
document.writeln("      <a class=\" pl10\" href=\"/CN/register\" >注册</a>");
document.writeln("      </div>");
document.writeln(" <div class='language'><a title='CHINESE' href='/CN/index' class='chinese'>中文<i></i></a><a href='/EN/index'class='english' title='ENGLISH'>EN<i></i></a> </div>");
document.writeln("      <div class=\"nav r user_info\" id=\"login\"  style=\"display:none;\">");
document.writeln(" <div class=\" dropdown \">");
document.writeln("   <a class=\"nav-link dropdown-toggle\" id=\"username\"><i class=\"icon_down\"></i></a>");
document.writeln("<div class=\"dropdown-menu \" style=\"display: none\" >");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/my/my_account\">我的账户</a>");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/my/my_money\">我的余额</a>");
document.writeln("<a class=\"dropdown-item\" href=\"/CN/my/authentication\">我的资料</a>");
document.writeln("<a class=\"dropdown-item\" href=\"javascript:;\" onclick=\"logout()\">退出</a>");
document.writeln(" </div>");
document.writeln(" </div>");
document.writeln(" </div>");
document.writeln("  </div>");
document.writeln("</div>");
document.writeln(" </div>");
document.writeln(" <div class=\"navbar_block\">");
document.writeln("  <div class=\"container row p0 m \">");
document.writeln(" <div class=\"col-lg-2 \"><a href=\"/CN/index\"><img class=\"logo\" src=\"/images/logo.png\" alt=\"\" ></a></div>");
document.writeln(" <div class=\"col-lg-10 mt20\">\n ");
document.writeln(" <!--nav--><div class=\"row\">");
document.writeln(" <div class=\"col-lg-12 nav \" >\n ");
document.writeln("   <ul class=\"nav-con navbar-nav\">\n");
document.writeln("  <li ><a href=\"/CN/index\" id=\"nav_index\" >首页</a></li>\n");
document.writeln("  <li class=\" dropdown \">\n");
document.writeln(" <a class=\"nav-link dropdown-toggle\" id=\"nav_my\">我的<i class=\"icon_down\"></i></a>\n ");
document.writeln(" <div class=\"dropdown-menu \" style=\"display: none\" >\n");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/my/my_account\">我的账户</a>\n");
document.writeln("<a class=\"dropdown-item\" href=\"/CN/my/my_money\">我的余额</a>\n ");
document.writeln("<a class=\"dropdown-item\" href=\"/CN/my/authentication\">我的资料</a>\n ");
document.writeln("<a class=\"dropdown-item\" href=\"/CN/my/inbox\">我的消息</a>\n ");
document.writeln(" </div>\n ");
document.writeln("   </li>\n");
document.writeln("   <li ><a href=\"/CN/main/recharge\" id=\"nav_recharge\">充值</a></li>\n");
document.writeln(" <li ><a href=\"/CN/main/trans_cash\" id=\"nav_trans\">提现</a></li>\n ");
document.writeln(" <li ><a href=\"/CN/main/transfer_accounts\"  id=\"nav_transfer\">转账</a></li>\n");
document.writeln("<li><a href=\"/CN/currency_exchange/exchange\" id=\"nav_exchange\">货币兑换</a></li>\n ");
document.writeln("     <li ><a href=\"/CN/nonCardCash/cash_in\" id=\"nav_nocard\">无卡取现</a></li>\n ");
document.writeln("    <li class=\" dropdown \">\n ");
document.writeln("  <a class=\"nav-link dropdown-toggle\" id=\"nav_more\">更多<i class=\"icon_down\"></i></a>\n");
document.writeln("  <div class=\"dropdown-menu \" style=\"display: none\" >\n ");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/more/financial\">智慧理财</a>\n ");
document.writeln("  <a class=\"dropdown-item\" href=\"/CN/more/life_payment\">生活缴费</a>\n");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/more/trade_list\">交易记录</a>\n");
document.writeln("<a class=\"dropdown-item\" href=\"/CN/more/active_list\">活动记录</a>\n ");
document.writeln(" </div>\n ");
document.writeln(" </li>\n ");
document.writeln(" </ul>\n");
document.writeln(" </div>");
document.writeln(" </div> <!--nav-->");
document.writeln("</div>\n ");
document.writeln(" </div>\n ");
document.writeln("  </div>\n");
document.writeln("</div>\n ");
document.writeln(" </div>\n");
document.writeln(" <input type='hidden' id='usertel'>\n");
document.writeln(" <input type='hidden' id='userid'>\n");
document.writeln(" <input type='hidden' id='isauth'>\n");

document.writeln(" <div class=\"navbar navbar-default navbar-fixed-top top-main phone_header\">");
document.writeln("   <div class=\"navbar_block navbar navbar-expand-lg navbar-dark\">");
document.writeln("  <div class=\"inblock w100 fix_header\">");
document.writeln("<a class=\"navbar-brand\" href=\"/CN/index\"></a>");
document.writeln("  <button class=\"navbar-toggler \" type=\"button\"  style=\"\"> </button>");
document.writeln("  </div> ");
document.writeln("  <div class=\"w100 nav phonenav\" id=\"navbarblock\" style=\"display: none\">");
document.writeln(" <ul class=\"nav-con navbar-nav mr-auto\" >");
document.writeln(" <li class=\"nav-item\"><a href=\"/CN/index\" class='l_language'><i class='l_chinese'></i>中文</a></li>");
document.writeln(" <li class=\"nav-item\"><a href=\"/EN/index\" class='l_language'><i class='l_english '></i>ENGLISH</a></li><hr>");
document.writeln(" <li class=\"nav-item\"><a href=\"/CN/index\" id=\"nav_index\">首页</a></li>");
document.writeln("  <li class=\" dropdown nav-item\">");
document.writeln("<a class=\"nav-link dropdown-toggle\" id=\"nav_my\">我的<i class=\"icon_down\"></i></a>");
document.writeln(" <div class=\"dropdown-menu \" style=\"display: none\"> ");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/my/my_account\">我的账户</a>");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/my/my_money\">我的余额</a>");
document.writeln("   <a class=\"dropdown-item\" href=\"/CN/my/authentication\">我的资料</a>");
document.writeln("  <a class=\"dropdown-item\" href=\"/CN/my/inbox\">我的消息</a>");
document.writeln("  </div> ");
document.writeln("  </li>");
document.writeln("<li class=\"nav-item\"><a href=\"/CN/main/recharge\" id=\"nav_recharge\">充值</a></li>");
document.writeln(" <li class=\"nav-item\"><a href=\"/CN/main/trans_cash\" id=\"nav_trans\">提现</a></li>");
document.writeln("  <li class=\"nav-item\"><a href=\"/CN/main/transfer_accounts\" id=\"nav_transfer\">转账</a></li>");
document.writeln("<li class=\"nav-item\"><a href=\"/CN/currency_exchange/exchange\" id=\"nav_exchange\">货币兑换</a></li>");
document.writeln(" <li class=\"nav-item\"><a href=\"/CN/nonCardCash/cash_in\" id=\"nav_nocard\">无卡取现</a></li>");
document.writeln(" <li class=\" dropdown nav-item\">");
document.writeln(" <a class=\"nav-link dropdown-toggle\" id=\"nav_more\">更多<i class=\"icon_down\"></i></a>");
document.writeln(" <div class=\"dropdown-menu \" style=\"display: none\">");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/more/financial\">智慧理财</a>");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/more/life_payment\">生活缴费</a> ");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/more/trade_list\">交易记录</a> ");
document.writeln(" <a class=\"dropdown-item\" href=\"/CN/more/active_list\">活动记录</a> ");
document.writeln(" </div> ");
document.writeln(" </li> ");
document.writeln(" </ul> ");
document.writeln("  </div> ");
document.writeln(" </div> </div> </div> ");

$(function(){
    $(".phone_header .nav .dropdown").click(function(){
        $(this).find(".dropdown-menu").toggle();
    });
    // $(".phone_header .navbar-toggler").click(function(){
    //     $("#navbarblock").toggle();
    // });
    $.ajax({
        url: "/user/getuser",
        type: "get",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            $("#unlogin").hide();
            $("#login").show();
            if (data!= null && data != undefined && data != "") {
                var html = "";
                if (data.username != "") {
                    html += "" + data.username + "";
                    if(data.leavel==1){
                        $("#username").addClass("icon_vip");
                    }
                }
                else{    html += "" + data.phone + "";}
                $("#username").html(html+"<i class=\"icon_down\"></i>");
                $("#usertel").val(data.phone);//隐藏域使用
                $("#userid").val(data.userid);//隐藏域使用
                $("#isauth").val(data.isauthentication);//隐藏域使用
            }
            if(data.isauthentication==1){
                $("#changetext").text("基本信息");
            }
            else{
                $("#changetext").text("会员认证");
            }
        },
        error: function (data) {
            $("#unlogin").show();
            $("#login").hide();
            window.location.href = "/CN/login";
        },
    });
});
//右上角菜单
$(".phone_header .navbar-toggler").on("click", function(e){
    $("#navbarblock").toggle();
    $(document).one("click", function(){
        $("#navbarblock").hide();
    });

    e.stopPropagation();
});
$("#navbarblock").on("click", function(e){
    e.stopPropagation();
});

//退出
function logout() {
    $.ajax({
        url: "/user/logout",
        type: "post",
        data: null,
        dataType: 'json',
        async: false,
        contentType: 'application/json',
        success: function (data) {
            if (data.code=="success") {
                sweetAlert("", "退出成功！", "success");
                setTimeout(function(){ window.location.href = "/CN/login";},1500);
            }
            else {
            }
        },
        error: function () {
            window.location.href = "/CN/login";
        },
    });

}





