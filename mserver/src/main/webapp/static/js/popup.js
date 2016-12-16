//替代js自带的弹出框的方法
var Sys = {};
var USERAGENT = navigator.userAgent.toLowerCase();
Sys.ie = window.ActiveXObject && USERAGENT.indexOf('msie') != -1 && USERAGENT.substr(USERAGENT.indexOf('msie') + 5, 3);
Sys.firefox = USERAGENT.indexOf('firefox') != -1 && USERAGENT.substr(USERAGENT.indexOf('firefox') + 8, 3);
Sys.chrome = window.MessageEvent && !document.getBoxObjectFor && USERAGENT.indexOf('chrome') != -1 && USERAGENT.substr(USERAGENT.indexOf('chrome') + 7, 10);
Sys.opera = window.opera && opera.version();
Sys.safari = window.openDatabase && USERAGENT.indexOf('safari') != -1 && USERAGENT.substr(USERAGENT.indexOf('safari') + 7, 8);
Sys.other = !Sys.ie && !Sys.firefox && !Sys.chrome && !Sys.opera && !Sys.safari;
Sys.firefox = Sys.chrome ? 1 : Sys.firefox;
Sys.msgid=[];

var _doc = (document.compatMode != "BackCompat") ? document.documentElement : document.body;

var MessageBox={
	i:0,
	mark:null,
	_obj:[],
	flash:null,
	_ini:function(v)
	{
		if(v==1){
			for(var i=0;i<this._obj.length;i++)
			{
				if(this._obj[i].style.display=="none"){
					this.i=i;
					return this._obj[i];
				}
			}
			this.i=this._obj.length;
			this._obj[this._obj.length]=this._CreateWindow();
			return this._obj[this._obj.length-1];
			
		}else{
			this.i=0;
			if(this._obj[0])
				return this._obj[0];
			else
				this._obj[0]=this._CreateWindow();
				return this._obj[0];
		}
	},

	_CreateWindow:function()
	{
		if(!document.getElementById("ajax"+this.i)){
			var oab = document.createElement("div");
			oab.id = "ajax"+this.i;
			document.body.appendChild(oab);
		}
		//oab.style.overflow="visible";

		return oab;
	},
	//p={title:'',content:'',mark:0,muli:0}
	Msg:function (p)
	{
		var agr=arguments;
		var w,h;
		if(p.muli==1){
			var WD=this._ini(1);
			
		}else {
			var WD=this._ini(0);
		}

		if(p.mark==1||p.mark===undefined)
		{
			this.Mark();
		}else{
			this.classMark();
		}
		this._msg(p,WD);
		return this.i;
		
	},


	_msg:function (p,o)
	{
		var agr=arguments;
		o.style.position="absolute";
		o.style.zIndex=1000;
		o.style.border="0px";
		o.style.backgroundColor="";

		o.style.top="0px";
		o.style.left="0px";
		o.style.padding="0px";
		var m="";
		if(this.i==0){
		m+='<div id="back_ap_div" style="position:absolute;">';
		m+='	<div style="positon:relative">';

		m+='	<div style="position:absolute;left:0px;bottom:0px;width:35px;height:20px;"></div>';
		m+='	<div style="position:absolute;right:0px;bottom:0px;width:35px;height:20px;"></div>';
		m+='	<div style="position:absolute;left:0px;top:0px;width:35px;height:33px;"></div>';
		m+='	<div style="position:absolute;right:0px;top:0px;width:35px;height:33px;"></div>';
			
		m+='	<div id="box_bg_left" style="position:absolute;left:0px;top:33px;width:15px;"></div>';
		m+='	<div id="box_bg_right" style="position:absolute;right:0px;top:33px;width:15px;"></div>';
		m+='	<div id="box_bg_top" style="position:absolute;top:0px;left:35px;height:13px;"></div>';
		m+='	<div id="box_bg_bottom" style="position:absolute;bottom:0px;left:35px;height:20px;"></div>';
			
		m+='	</div>';
		m+='</div>';
		}
		m+="<div id=\"fly_message\" style=\"background:#ffffff;position:absolute;top:13px;left:15px;border:1px solid #cccccc;box-shadow:1px 2px 2px #888888\">";
		if(p.title){
			m+="<div id=\"msg_title\" style=\"border-bottom:1px solid #cccccc;color:#000000;background:#FF7F24;cursor:move;padding:6px 5px;font-size:14px;background: -moz-linear-gradient(#F6F3EE, #F0ECE3);background: -ms-linear-gradient(#F6F3EE, #F0ECE3);background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #F6F3EE), color-stop(100%, #F0ECE3));background: -webkit-linear-gradient(#F6F3EE, #F0ECE3);background: -o-linear-gradient(#F6F3EE, #F0ECE3);\">"+p.title+"</div>";
			m+="<div id=\"msg_close\" onclick=\"MessageBox.hid("+this.i+");\" style=\"position:absolute;top:7px;right:7px;background:url(../static/images/box_close.png);width:15px;height:15px;cursor:pointer;z-index:1000;\"></div>";
		}
		m+="<div id=\"message\"  >"+p.content+"</div>";
		m+="</div>";

		o.style.display="block";
		o.innerHTML=m;


		if(p.width){
			mw=p.width;
		}else{
			mw=document.getElementById('message').offsetWidth;
		}
		if(p.height){
			mh=p.height;
		}else{
			mh=document.getElementById('message').offsetHeight;
		}
		o.style.width=mw+12+"px";
		o.style.height=mh+28+12+"px";
		
		document.getElementById('fly_message').style.width=mw+"px";
		document.getElementById('back_ap_div').style.width=mw+30+"px";
		if(this.i==0){
			if(p.title){
				document.getElementById('back_ap_div').style.height=mh+31+26+"px";
				document.getElementById('box_bg_left').style.height=mh+4+'px';
				document.getElementById('box_bg_top').style.width=mw-40+'px';
				document.getElementById('box_bg_right').style.height=mh+4+'px';
				document.getElementById('box_bg_bottom').style.width=mw-40+'px';
			}else{
				document.getElementById('back_ap_div').style.height=mh+26+"px";
				document.getElementById('box_bg_left').style.height=mh-31+4+'px';
				document.getElementById('box_bg_top').style.width=mw-40+'px';
				document.getElementById('box_bg_right').style.height=mh-31+4+'px';
				document.getElementById('box_bg_bottom').style.width=mw-40+'px';
			}
			
		}
		var sctop=	document.documentElement.scrollTop||document.body.scrollTop;
		fly_top=sctop+_doc.clientHeight/2-o.offsetHeight/2;
		if ((_doc.clientHeight/2-o.offsetHeight/2)<0) fly_top=sctop;
		fly_left=_doc.scrollLeft+_doc.clientWidth/2-o.offsetWidth/2;
		if (fly_left<0) fly_left="0"+"px";
		o.style.top=fly_top+"px";
		o.style.left=fly_left+"px";

		//隐藏flash
		this.flash=document.getElementsByTagName('object');

		for(i=0;i<this.flash.length;i++){
			this.flash[i].className='displaynone';
		}

	},
	Mark:function ()
	{
		if(!this.mark){
			this.mark = document.createElement("div");
			this.mark.id="mark_all";
			document.body.appendChild(this.mark);
		}

		this.mark.style.display="block";
		this.mark.style.background="#000";
		if(_doc.scrollWidth>_doc.clientWidth){
			this.mark.style.width=_doc.scrollWidth+"px";
		}else{
			this.mark.style.width=_doc.clientWidth+"px";
		}
		if(_doc.scrollHeight>_doc.clientHeight){
			this.mark.style.height=_doc.scrollHeight+"px";
		}else{
			this.mark.style.height=_doc.clientHeight+"px";
		}
		this.mark.style.position="absolute";
		this.mark.style.zIndex=999;
		this.mark.style.filter="alpha";
		this.mark.style.top=0;
		this.mark.style.left=0;

		if(document.all){
			this.mark.filters.alpha.opacity=20;
		}else{
			this.mark.style.MozOpacity=0.2;
			this.mark.style.opacity=0.2;
		}
	},
	classMark:function()
	{
		if(this.mark){
			this.mark.style.display="none";
		}
	},
	hid:function (v)
	{	

		for(i=0;i<this.flash.length;i++){
			this.flash[i].className='';
		}
		
		try {
			document.getElementById('treebg').style.display="none";
		}catch(e) {}
		
		this._obj[v].style.display="none";
		//o.parentNode.parentNode.style.display="none";
		if(this.mark&&this.mark.style.display!="none")
			this.mark.style.display="none"
	}
};

var MoveObject={
	ac:true,
	abc:null,
	move:function (e)
	{
		if (ac)
		{
			e=e?e:window.event;
			l1=e.clientX+temp1-x;
			t1=e.clientY+temp2-y;
			if(t1<0){
				t1=0;
			}
			this.abc.style.left=l1+"px";
			this.abc.style.top=t1+"px";
			return false;
		}
		
	},
	down:function (e)
	{
		e=e?e:window.event;
		
		if (document.all)
		{
			eventsrc=e.srcElement
		}
		else
		{
			eventsrc=e.target;
		}
		if (eventsrc.id=="msg_title")
		{
					
			ac=true;
			this.abc=eventsrc.parentNode.parentNode;
			this.abc.style.position="absolute";
			temp1=this.abc.offsetLeft;
			temp2=this.abc.offsetTop;
			x=e.clientX;
			y=e.clientY;


			document.onmousemove=MoveObject.move;
			return false;
		}
	},
	st:function ()
	{
		ac=false;
		document.onmousemove=null;
	}
};
function get_obj_position(e)
{ 
	var t=e.offsetTop; 
	var l=e.offsetLeft; 
	var w=e.offsetWidth;
	var h=e.offsetHeight;
	while(e=e.offsetParent)
	{ 
		t+=e.offsetTop; 
		l+=e.offsetLeft; 
	}
	var re=new Array();
	re[0]=t;
	re[1]=l;
	re[2]=w;
	re[3]=h;
	return re;
}
window.onload=function (){
	document.onmousedown=MoveObject.down;
	document.onmouseup=MoveObject.st;
}