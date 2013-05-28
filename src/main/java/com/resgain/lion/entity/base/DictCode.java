package com.resgain.lion.entity.base;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.resgain.lion.abst.PersistentObject;
import com.resgain.lion.annotation.Desc;
import com.resgain.lion.annotation.Label;

/**
 * 字典信息实体类
 * @author gyl
 */
@Entity(name="BASE_DICT_CODE")
@Desc("字典信息")
public class DictCode extends PersistentObject
{
    @Column(name="DICT_CATEGORY")
    @Label(name="字典类别", nullFlag=false)
    private String category;
	
    @Column(name="DICT_CODE")
    @Label(name="代码", nullFlag=false)
    private String code;
    
    @Column(name="DICT_PCODE")
    @Label(name="父代码")
    private String pcode;
    
    @Column(name="DICT_LABEL")
    @Label(name="标签", nullFlag=false)
    private String label;

    @Column(name="DICT_SORT")
    @Label(name="排序", nullFlag=false)    
    private long sort;

	public DictCode() {
		super();
	}
	
	public DictCode(String category, String code, String pcode, String label) {
		super();
		this.category = category;
		this.code = code;
		this.pcode = pcode;
		this.label = label;
		this.sort = System.currentTimeMillis();
	}
	
	public String getCategory()
	{
		return category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getPcode()
	{
		return pcode;
	}
	public void setPcode(String pcode)
	{
		this.pcode = pcode;
	}

	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}

	public long getSort()
	{
		return sort;
	}
	public void setSort(long sort)
	{
		this.sort = sort;
	}
}