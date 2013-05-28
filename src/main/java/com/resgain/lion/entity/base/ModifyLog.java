package com.resgain.lion.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.resgain.lion.abst.PersistentObject;
import com.resgain.lion.annotation.Desc;
import com.resgain.lion.annotation.Label;

/**
 * 修改记录表
 * @author gyl
 */
@Entity(name="BASE_MODIFY_LOG")
@Desc("员工信息")
public class ModifyLog extends PersistentObject
{
    @Column(name="ML_CLAZZ")
    @Label(name="实体类", nullFlag=false)
    private String clazz;
    
    @Column(name="ML_DATA_ID")
    @Label(name="数据ID", nullFlag=false)    
    private String dataId;
    
    @Column(name="ML_OLD_CONTENT")
    @Label(name="原内容", nullFlag=false)
    private String oldContent;
 
    @Column(name="ML_OLD_EDITOR")
    @Label(name="原编辑人", nullFlag=false)
    private String oldEditor;
    
    @Column(name="ML_OLD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @Label(name="原编辑日期", nullFlag=false)
    private Date oldDate;
    
	public ModifyLog() {
		super();
	}
	
	public ModifyLog(String clazz, String dataId, String oldContent, String oldEditor, Date oldDate) {
		super();
		this.clazz = clazz;
		this.dataId = dataId;
		this.oldContent = oldContent;
		this.oldEditor = oldEditor;
		this.oldDate = oldDate;
	}

	public String getClazz()
	{
		return clazz;
	}
	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}

	public String getDataId()
	{
		return dataId;
	}
	public void setDataId(String dataId)
	{
		this.dataId = dataId;
	}

	public String getOldContent()
	{
		return oldContent;
	}
	public void setOldContent(String oldContent)
	{
		this.oldContent = oldContent;
	}

	public String getOldEditor()
	{
		return oldEditor;
	}
	public void setOldEditor(String oldEditor)
	{
		this.oldEditor = oldEditor;
	}

	public Date getOldDate()
	{
		return oldDate;
	}

	public void setOldDate(Date oldDate)
	{
		this.oldDate = oldDate;
	}
}