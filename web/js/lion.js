function msgbox(msg, ac){
	var dm = $("#_msg_");
	if(dm.length>0)
		dm.remove();
	$("body").append('<div id="_msg_" class="alert" style="position: absolute; width: 300px; float: right; top: 45px; right: 20px; background: black;color: white;"><button type="button" class="close" data-dismiss="alert" style="color:white; opacity: 1">×</button>'+msg+'</div>')
	if(ac)
		ac();
	setTimeout(function(){$("#_msg_").remove();}, 2000);
}

function errmsg(jform, msg){
	var dm = $("#_dialog_msg_");
	if(dm.length>0)
		dm.remove();
	jform.before('<div class="alert alert-error" id="_dialog_msg_"><button type="button" class="close" data-dismiss="alert">×</button>'+msg+'</div>');
	setTimeout(function(){$("#_dialog_msg_").alert('close');}, 4000);
}

function paramUri(uri, param) {
    if (!param) return uri;
    var page = uri.replace(/#$/, "");
	if (page.indexOf("?") == -1) {
		page += "?_t=-1";
	}
    return page + "&" + param;
}

function partRelod(areaId){
	var ao = $("#"+areaId);
	var action = ao.attr("action") || paramUri(location.href, "ALONE_="+areaId);
	$.get(action, function(data){
		ao.html(data).attr("action", action);
	});
}

String.prototype.trim=function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.isValidDate=function()
{
    var result=this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if(result==null) return false;
    var d=new Date(result[1], result[3]-1, result[4]);
    return (d.getFullYear()==result[1]&&d.getMonth()+1==result[3]&&d.getDate()==result[4]);
}

String.prototype.isValidTime=function()
{
    var resule=this.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
    if (result==null) return false;
    if (result[1]>24 || result[3]>60 || result[4]>60) return false;
    return true;
}


String.prototype.isValidDatetime=function()
{
    var result=this.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/);
    if(result==null) return false;
    var d= new Date(result[1], result[3]-1, result[4], result[5], result[6], 0);
    return (d.getFullYear()==result[1]&&(d.getMonth()+1)==result[3]&&d.getDate()==result[4]&&d.getHours()==result[5]&&d.getMinutes()==result[6]);
}

function formValidation(jform)
{
	var isValidation = function(obj){
		piValue=obj.value.replace(/(^\s*)|(\s*$)/g, "");
		switch (obj.getAttribute("dataType")) {
			case "Chinese" : return /^[\u0391-\uFFE5]+$/.test(piValue);
			case "Email" : return piValue.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)!=null;
			case "Integer" : return piValue.match(/^(-|\+)?\d+$/)!=null;
			case "Double" : return /(^[-\+]?)(\d*)(\.?)(\d*)?$/.test(piValue);
			case "Date" : return piValue.isValidDate();
			case "Time" : return piValue.isValidTime();
			case "Datetime" : return piValue.isValidDatetime();
			case "English" : return piValue.match(/^[a-zA-Z]+$/)!=null;
			case "Repeat" : return piValue == document.getElementsByName(obj.getAttribute('to'))[0].value;
			default : return piValue!="";
		}
		return true;
	}
	
	var formObject = jform[0];
	for ( var i = 0; i < formObject.length; i++) {
		var jo = $(formObject.elements[i]);
		with (formObject.elements[i]) {
			if(nodeName=="FIELDSET") continue;
			if(getAttribute("require") != "true" && value.trim() == "") continue;
			if(!isValidation(formObject.elements[i]))
			{
				var msg = getAttribute("msg") || getAttribute("placeholder") || "请输入数据";
				errmsg(jform, msg);
				$(formObject.elements[i]).focus();
				return false;
			}
		}
	}	
	return true;
};

$.fn.formSubmit = function(options) {
    return this.each( function() {
    	$(this).submit(function() { 
        	var o = $(this);
        	if(!formValidation(o))
    			return false;
        	var target_url = o.attr("tu");
        	var cmsg = o.attr("confirm");
        	var bcallback = eval(o.attr("bc"));
        	var acallback = eval(o.attr("ac")|| "partRelod");    	
    		if(bcallback)
    			bcallback();
    		o.ajaxSubmit({
    	    		beforeSubmit: function(formData, jqForm, options) {
    		        	if(cmsg && !confirm(cmsg))
    		        		return false;
    					var ret=true;
    					if(ret) {
    						
    					}
    					return ret;
    	    		},
    		        success: function resuleProcess(responseText, statusText, xhr, jqForm) {
    		        	try{
    		        		var data =  eval("("+responseText+")");
                			var succ = data.success;
                            if(succ){
                            	if(target_url)
                            		location.href = target_url;
                            	msgbox(data.message || "操作成功!", acallback(jqForm.attr("reload-target")));
                            	jqForm.parents("div.modal").modal('toggle');
                            } else {
                            	errmsg(jqForm, data.message || "操作失败！");
                            }
    		        	}catch(e){
    		        		errmsg(jqForm, "操作失败，请仔细检查您输入的数据是否正确。");
    		        	}
    		        }
    	        }); 
    		 return false; 
    	});
    });
}

$(document).ready(function(){
	
	$("div.alone_request").on("click", "a.alone_request", function(){ //单独请求ajax加载处理
		var ao = $(this);
		$.get(ao.attr("href"), function(data){
			$("#"+ao.attr("singleId")).html(data).attr("action", ao.attr("href"));
		});
		
		return false;
	});
	
    $(".selectall").click(function() { //全选处理
        var chd = this.checked;
        $("."+$(this).attr("sub")).each(function() {
            $(this).attr("checked", chd);
        });
    });
    
    $('div.alone_request').on("click", 'a[data-toggle="modal"]', function(){ //页面内表单弹出处理
    	var ao = $(this);
    	$(ao.attr("data-target")).attr("data-action", ao.attr("data-url") || "#");
    	return true;
    }).on("click", '.delete', function(){ //页面内表单弹出处理
    	var ao = $(this);
    	if(confirm("数据一旦删除不可恢复，您确认删除吗？")){
    		$.post(ao.attr("data-url"), null, function(){
    			partRelod(ao.parents("div.alone_request").attr("id"));
    		});
    	}
    	return true;
    });
    
    rivets.configure({
    	  adapter: {
    	    subscribe: function(obj, keypath, callback) {
    	      //obj.on('change:' + keypath, callback)
    	    },
    	    unsubscribe: function(obj, keypath, callback) {
    	      //obj.off('change:' + keypath, callback)
    	    },
    	    read: function(obj, keypath) {
    	    	return obj[keypath];
    	    	//return obj.get(keypath)
    	    },
    	    publish: function(obj, keypath, value) {
    	      //obj.set(keypath, value)
    	    }
    	  }
    	})
    
    $('.modal').on('show', function () { //弹出表单加载数据处理
    	var o = $(this);
    	o.find("form").each(function(i){
    		var ta = o.attr("data-action");
    		if(ta!="#"){
    			$.getJSON(ta, function(data){
    				rivets.bind(o[0], {data: data}).build();
    			});
    		} else {
    			rivets.bind(o[0], {data: {}}).build();
    		}
    	 });
    }).drags({handle: '.modal-header'});
    
    $(".ajaxform").formSubmit();
    $(".date,.datetime,.time").dateinput();
    $(".dict").filter(":text").dictinput();
});