$.fn.dictinput = function(options) {
    // 获取一个jQuery对象dom节点的属性列表值
    var getAttrStrs = function(je, except){
        var es = except || {name:1, id:1, "class":1, onchange:1, validate:1, DICT_CLASS:1, DICT_CATE:1, DICT_PCODE:1, DICT_SCODE:1};
        return $.map(je[0].attributes, function(o){
                    return (es[o.name] || es[o.name.toString().toUpperCase()]) ? o.name + "='" + o.value + "'" : "";
                }).join(" ");
    }
    
	var reqDict = [];
	var dos = this
	dos.each(function(){
		var obj = $(this);
		if(!obj.attr("id"))
			obj.attr("id", "");
		reqDict.push({beanId:obj.attr("DICT_CLASS"), category:obj.attr("DICT_CATE"),  pcode:obj.attr("DICT_PCODE")});
	})
	var reqDictCond = JSON.stringify(reqDict);
	$.getJSON(DICT_ACTION, {cond:reqDictCond}, function(datas){
		dos.each(function(){
			var obj = $(this);
			var key = (obj.attr("DICT_CLASS")||"null") + ":"+ (obj.attr("DICT_CATE")||"null") + ":"+ (obj.attr("DICT_PCODE")||"null") + ":null";
			var data = datas[key];
			if(data){
				 var html = "<select " + getAttrStrs(obj) + ">"+$.map(data, function(mbr){
	        			var checked=(mbr.code==obj.val()?" selected ":"");
	                    return "<option value='" + mbr.code + "'" + checked + ">" + mbr.label + "</option>";
	                }).join(" ") + "</select>";
		        obj.replaceWith(html);
		        if(obj.hasClass("chosen"))
		        $("#"+obj.attr("id")).chosen();
			}
		});
	});
    return this;
}

$.fn.dateinput = function(options) {
    return this.each( function() {
			var config={autoclose: true, todayBtn: true, todayHighlight:true, pickerPosition: 'bottom-left', language : 'zh-CN'};
			if($(this).filter(".date").length>0)
				jQuery.extend(config, {format: "yyyy-mm-dd", minView:2});
			if($(this).filter(".datetime").length>0)
				jQuery.extend(config, {format: "yyyy-mm-dd hh:ii"});
			$(this).datetimepicker(config);
    });
}