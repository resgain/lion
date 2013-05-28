package com.resgain.lion.velocity;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.resgain.lion.Constants;

public class VelocityResult extends org.apache.struts2.dispatcher.VelocityResult
{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(VelocityResult.class);
	
	private static String WEB_ROOT = ServletActionContext.getServletContext().getRealPath(String.valueOf(File.separatorChar));
	private static Map<String, List<String>> expectMap = getExceptMap();

	@Override
	public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception
	{
		String tfilename = finalLocation.replaceFirst("views", "vtemp");
		File of = new File(WEB_ROOT+finalLocation);
		File tf = new File(WEB_ROOT+tfilename);
		
		if(!tf.exists() || tf.lastModified()<of.lastModified() || true)
		{
			String style = getStyleFile(of.getParent());
			if(style==null || (expectMap.containsKey(of.getParent()) && expectMap.get(of.getParent()).contains(of.getName()))){
				FileUtil.copyFile(of, tf);
			} else {
				FileUtil.writeAsString(tf, FileUtil.readAsString(new File(style)).replace("{MAIN_BODY}", FileUtil.readAsString(of)));
			}
			Elements es = Jsoup.parse(tf, "UTF-8").select(".alone_request");
			if(!es.isEmpty()) {
				for (Element e : es) {
					if(!StringUtil.isBlank(e.attr("id")))
						FileUtil.writeAsString(new File(tf+"."+e.attr("id")), e.html());
					else
						logger.warn("文件{}里有单独的请求模板设置，但是没有为这个块设置ID，这将导致单独请求无效。", of);
				}
			}
		}
		if(!StringUtil.isBlank(ServletActionContext.getRequest().getParameter(Constants.ALONE_REQUEST)))
			tfilename = tfilename + "." +ServletActionContext.getRequest().getParameter(Constants.ALONE_REQUEST);
		super.doExecute(tfilename, invocation);
	}
	
	private static Map<String, List<String>> getExceptMap(){
		Map<String, List<String>> ret = new HashMap<String, List<String>>();
		Collection<File> files = FileUtils.listFiles(new File(WEB_ROOT), FileFilterUtils.nameFileFilter("except.txt"), TrueFileFilter.INSTANCE);
		for (File file : files) {
			try {
				List<String> lines = FileUtils.readLines(file);
				ret.put(file.getParent(), lines);
			} catch (IOException e) {
				logger.warn("文件{}读取错误", file.getPath(), e);
			}
		}
		return ret;
	}
	
	private static String getStyleFile(String name){
		File ret = new File(name+File.separatorChar+"style.vm");
		if(ret.exists())
			return ret.getPath();
		if(name.startsWith(WEB_ROOT))
			return getStyleFile(ret.getParentFile().getParent());
		return null;
	}
}