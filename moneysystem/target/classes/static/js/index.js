/**
 * Created by john on 2017/8/18.
 */
(function($){
   var tool={
        isIE:function(){
            var myNav = navigator.userAgent.toLowerCase();
            return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
        },
       isFunction:function(fn){  /*判断是否是函数*/
           return Object.prototype.toString.call(fn)=== '[object Function]';
       },
       fmoney:function(s, n) {

           var myn = n > 0 && n <= 20 ? n : 2;
           s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(myn) + "";
           var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
           var t = "";
           for (var i = 0; i < l.length; i++) {
               t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
           }
           if(!n){
               return t.split("").reverse().join("");
           }else{
               return t.split("").reverse().join("") + "." + r;
           }

       },
       geturlParam:function(name, url){
           if (!url) url = window.location.href;
           name = name.replace(/[\[\]]/g, "\\$&");
           var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
               results = regex.exec(url);
           if (!results) return null;
           if (!results[2]) return '';
           return decodeURIComponent(results[2].replace(/\+/g, " "));
       },
       rmoney:function(s) {
           return parseFloat(s.replace(/[^\d\.-]/g, ""));
       },
       randnum:function(min,max){
           return Math.floor(Math.random()*(max-min))+min;
       },
       numMulti:function(num1, num2) {
           var baseNum = 0;
           try {
               baseNum += num1.toString().split(".")[1].length;
           } catch (e) {
           }
           try {
               baseNum += num2.toString().split(".")[1].length;
           } catch (e) {
           }
           return Number(num1.toString().replace(".", "")) * Number(num2.toString().replace(".", "")) / Math.pow(10, baseNum);
       },
       numAdd:function(num1, num2){//+
           var baseNum, baseNum1, baseNum2;
           try {
               baseNum1 = num1.toString().split(".")[1].length;
           } catch (e) {
               baseNum1 = 0;
           }
           try {
               baseNum2 = num2.toString().split(".")[1].length;
           } catch (e) {
               baseNum2 = 0;
           }
           baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
           return (num1 * baseNum + num2 * baseNum) / baseNum;
       },
       numSub:function(num1, num2) {
           var baseNum, baseNum1, baseNum2;
           var precision;// 精度
           try {
               baseNum1 = num1.toString().split(".")[1].length;
           } catch (e) {
               baseNum1 = 0;
           }
           try {
               baseNum2 = num2.toString().split(".")[1].length;
           } catch (e) {
               baseNum2 = 0;
           }
           baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
           precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
           return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
       }

   };

    /*turn*/
    $.fn.turnx = function(options){
        var defaults={
            _turn:$(this),//容器
            _box:"turnbox",//UL容器名
            _dotclass:"dotcon",//快捷点容器样式名
            _turntime:3000,//轮播时间
            _cuttime:200,//切换时间
            _doton:"cur",//圆点获得当前时候的样式
            _cur:"cur",
            _leftb:"lbtn",//左边按钮样式名
            _rightb:"rbtn"//右边按钮样式
        };
        var opt = $.extend({},defaults,options);
        var _box =$("."+opt._box);//获取ul容器 _box样式必须设置高度和宽度
        var _picnum =_box.find("li").length;//获取图片数量
        var _boxH =_box.height();//获取高度 必须设置高度
        var _boxW =_box.width();//获取高度
        _box.find("li").height(_boxH);
        // _box.find("li").width(_boxW);
        return this.each(function(){//JQUERY链式支持
            if(_picnum==0) return this;
            cinit();//生成html 元素
            var _lbtn = $("."+opt._leftb);//获取生成的元素
            var _rbtn = $("."+opt._rightb);//获取生成的元素
            var ons=$("."+opt._dotclass).find("."+opt._doton).val();//获得当前序列
            var _dotclass =$("."+opt._dotclass);
            _rbtn.on("click",goright).hide();
            _lbtn.on("click",goleft).hide();
            //生成快捷小圆点,按钮
            function cinit(){
                var dotcon="<ul class='"+opt._dotclass+"'>";
                for(var i=0;i<_picnum;i++){
                    dotcon+="<li value="+i+"></li>";
                }
                dotcon+="</ul>"
                opt._turn.append(dotcon);
                //第一个图片选中状态
                $("."+opt._dotclass).find("li").eq(0).addClass(opt._doton);
                _box.children("li").eq(0).css({"opacity":"1"}).addClass(opt._cur).siblings().css({"opacity":"0"}).removeClass(opt._cur);
                //添加按钮
                opt._turn.append("<a href='javascript:;' class='"+opt._leftb+"'></a><a href='javascript:;' class='"+opt._rightb+"'></a>");
            };
            //右边
            function goright(){
                _box.children("li").eq(ons).stop(false,false).animate({ opacity:0},opt._cuttime);
                ons=ons+1;
                if((ons)>_picnum-1){
                    ons=0
                }
                _dotclass.find("li").eq(ons).addClass(opt._doton).siblings().removeClass(opt._doton);
                _box.children("li").eq(ons).addClass(opt._cur).stop(false,false).animate({ opacity:1},opt._cuttime).siblings().removeClass(opt._cur);
            };
            //左边
            function goleft(){
                _box.children("li").eq(ons).stop(false,false).animate({ opacity:0},opt._cuttime);
                ons=ons-1;
                if((ons)<0){
                    ons=_picnum-1;
                }
                _dotclass.find("li").eq(ons).addClass(opt._doton).siblings().removeClass(opt._doton);
                _box.children("li").eq(ons).addClass(opt._cur).stop(false,false).animate({ opacity:1},opt._cuttime).siblings().removeClass(opt._cur);
            };
            //点绑定hover事件
            _dotclass.find("li").bind("mouseover",function(){
                _box.children("li").eq(ons).stop(false,false).animate({ opacity:0},opt._cuttime);
                var dodex= $(this).val();
                _dotclass.find("li").eq(dodex).addClass(opt._doton).siblings().removeClass(opt._doton);
                _box.children("li").eq(dodex).addClass(opt._cur).stop(false,false).animate({ opacity:1},opt._cuttime).siblings().removeClass(opt._cur);
            });
            //定时器
            var tint=setInterval(goright,opt._turntime);
            opt._turn.on({
                mouseenter:function(){
                    clearTimeout(tint);
                    _lbtn.clearQueue().stop(false,false).fadeIn();
                    _rbtn.clearQueue().stop(false,false).fadeIn();
                },
                mouseleave:function(){
                    tint=setInterval(goright,opt._turntime);
                    _lbtn.clearQueue().stop(false,false).fadeOut();
                    _rbtn.clearQueue().stop(false,false).fadeOut();
                }
            });
        });
    };
    //turnx end
    // 扩展serialize为JSON
    $.fn.serializeJson=function(){
        var serializeObj={};
        var array=this.serializeArray();
        // var str=this.serialize();
        $(array).each(function(){ // 遍历数组的每个元素
            if(serializeObj[this.name]){ // 判断对象中是否已经存在 name，如果存在name
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value); // 追加一个值 hobby : ['音乐','体育']
                }else{
                    // 将元素变为 数组 ，hobby : ['音乐','体育']
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value; // 如果元素name不存在，添加一个属性 name:value
            }
        });
        return serializeObj;
    };
   window.tool = tool;
})(jQuery)

