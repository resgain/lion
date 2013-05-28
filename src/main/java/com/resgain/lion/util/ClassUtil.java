package com.resgain.lion.util;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.apache.commons.lang.ClassUtils;
import org.mvel2.DataConversion;

@SuppressWarnings("rawtypes")
public class ClassUtil
{
    public static void main(String[] args) throws Exception
    {
        // 匹配静态方法
        String[] paramNames = getMethodParamNames(ClassUtil.class, "main", String[].class);
        System.out.println(Arrays.toString(paramNames));
        // 匹配实例方法
        paramNames = getMethodParamNames(ClassUtil.class, "foo", String.class);
        System.out.println(Arrays.toString(paramNames));
        // 自由匹配任一个重名方法
        paramNames = getMethodParamNames(ClassUtil.class, "getMethodParamNames");
        System.out.println(Arrays.toString(paramNames));
        // 匹配特定签名的方法
        paramNames = getMethodParamNames(ClassUtil.class, "getMethodParamNames", Class.class, String.class);
        System.out.println(Arrays.toString(paramNames));
        
        System.out.println(getMethodParamNames("Try01", "getMethodParamNames"));
    }
    
    public static Map<String, String> getMethodParamNames(String c, String m) throws NotFoundException, MissingLVException
    {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(c);
        CtMethod cm = cc.getDeclaredMethod(m);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null)
            throw new MissingLVException(cc.getName());
        Map<String, String> ret = new LinkedHashMap<String, String>();
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        
        for (int i = 0; i < cm.getParameterTypes().length; i++)
        {
            ret.put(attr.variableName(i + pos), cm.getParameterTypes()[i].getName());
        }
        return ret;
    }   

    /**
     * 获取方法参数名称，按给定的参数类型匹配方法
     * 
     * @param clazz
     * @param method
     * @param paramTypes
     * @return
     * @throws NotFoundException
     *             如果类或者方法不存在
     * @throws MissingLVException
     *             如果最终编译的class文件不包含局部变量表信息
     */
    public static String[] getMethodParamNames(Class clazz, String method, Class... paramTypes) throws NotFoundException, MissingLVException
    {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(clazz.getName());
        String[] paramTypeNames = new String[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++)
            paramTypeNames[i] = paramTypes[i].getName();
        CtMethod cm = cc.getDeclaredMethod(method, pool.get(paramTypeNames));
        return getMethodParamNames(cm);
    }

    /**
     * 获取方法参数名称，匹配同名的某一个方法
     * 
     * @param clazz
     * @param method
     * @return
     * @throws NotFoundException
     *             如果类或者方法不存在
     * @throws MissingLVException
     *             如果最终编译的class文件不包含局部变量表信息
     */
	public static String[] getMethodParamNames(Class clazz, String method) throws NotFoundException, MissingLVException
    {

        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(clazz.getName());
        CtMethod cm = cc.getDeclaredMethod(method);
        return getMethodParamNames(cm);
    }

    /**
     * 获取方法参数名称
     * 
     * @param cm
     * @return
     * @throws NotFoundException
     * @throws MissingLVException
     *             如果最终编译的class文件不包含局部变量表信息
     */
    protected static String[] getMethodParamNames(CtMethod cm) throws NotFoundException, MissingLVException
    {
        CtClass cc = cm.getDeclaringClass();
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null)
            throw new MissingLVException(cc.getName());

        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attr.variableName(i + pos);
        return paramNames;
    }

    /**
     * 在class中未找到局部变量表信息<br>
     * 使用编译器选项 javac -g:{vars}来编译源文件
     * 
     * @author Administrator
     * 
     */
    @SuppressWarnings("serial")
	public static class MissingLVException extends Exception
    {
        static String msg = "class:%s 不包含局部变量表信息，请使用编译器选项 javac -g:{vars}来编译源文件。";

        public MissingLVException(String clazzName) {
            super(String.format(msg, clazzName));
        }
    }
    
    @SuppressWarnings("unchecked")
	public static Object getValue(String name, String type, Map<String, Object> context) throws ClassNotFoundException
    {
    	Object value = context.get(name);
    	if(value==null || value.toString().trim().length()<1)
    		value = null;
    	if("int".equals(type)){
    		return value==null?0:Integer.valueOf(value.toString()).intValue();
    	} else if("short".equals(type)){
    		return value==null?0:Short.valueOf(value.toString()).shortValue();
    	} else if("long".equals(type)){
    		return value==null?0:Long.valueOf(value.toString()).longValue();
    	} else if("byte".equals(type)){
    		return value==null?0:Byte.valueOf(value.toString()).byteValue();
    	} else if("float".equals(type)){
    		return value==null?0.0:Float.valueOf(value.toString()).floatValue();
    	} else if("double".equals(type)){
    		return value==null?0.0:Double.valueOf(value.toString()).doubleValue();
    	} else if("char".equals(type)){
    		return value==null?0:value.toString().charAt(0);
    	} else if("boolean".equals(type)){
    		return value==null?false:Boolean.valueOf(value.toString()).booleanValue();
    	} else {
    		return value==null?null:DataConversion.convert(value, ClassUtils.getClass(type));
    	}
    }

    static void foo()
    {
    }

    void foo(String bar)
    {
    }
}
