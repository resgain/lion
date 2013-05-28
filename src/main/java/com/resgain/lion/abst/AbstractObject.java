package com.resgain.lion.abst;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务实体类父类,一般业务实体类均要继承该类 该类主要定义了一些公共属性：id、创建人、创建日期、是否删除标记
 * @author memphis
 */
@MappedSuperclass
public class AbstractObject
{
	@Id
	@Column(name = "ID", length = 32, nullable = false)
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	@GeneratedValue(generator = "system-uuid")
	protected String id; // ID

	@Column(name = "DEL_FLAG", length = 1, nullable = false)
	protected boolean delFlag = false; // 是否删除标记

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isDelFlag()
	{
		return delFlag;
	}
	public void setDelFlag(boolean delFlag)
	{
		this.delFlag = delFlag;
	}
}
