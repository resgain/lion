package com.resgain.lion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)
public @interface Label 
{
	public String name();	 //名称
	public String desc() default ""; //说明
	public String type() default ""; //类型
	public int minLength() default 0; //前台校验：最小长度
	public int maxLength() default 0; //前台校验：最大长度
	public boolean nullFlag() default true; //前台校验：是否可以为空 
}