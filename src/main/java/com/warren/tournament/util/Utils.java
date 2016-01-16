package com.warren.tournament.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

public class Utils {

	/**
	 * Find 2 to the n power, where the result does not exceed the value of y and:
	 *    a) y == args[0]
	 *    and...
	 *    b) n == args[1] or args[1] + 1
	 *    
	 * @param args
	 * @return
	 */
	public static int closestLog2(int... args) {
		
		if(args.length == 0)
			return 0;
		if(args.length == 1) 
			return closestLog2(args[0], 0);
		if(args[0] == 0)
			return 0;
		
		int result = (int) Math.pow(2, (double)args[1] + 1d);
		
		if(result > args[0])
			return args[1];
		else if(result == args[0])
			return args[1] + 1;
		else
			return closestLog2(args[0], args[1]+1);
	}
	
	public static String stackTraceToString(Throwable e) {
		if(e == null)
			return null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String trace = sw.getBuffer().toString();
		return trace;
	}
	
	/**
	 * Convert a field name to the standard mutator method name, ie: "name" becomes "setName"
	 * @param fldName
	 * @return
	 */
	public static String getMutatorName(String fldName) {
		if(fldName == null)
			return null;
		String setter = new String(fldName);
		if(!fldName.startsWith("set")) {
			setter = "set" + setter.substring(0, 1).toUpperCase() + setter.substring(1);
		}
		return setter;
	}
	
	/**
	 * From the name of an accessor or a mutator, get the name of the target field (remove "set" or "get" and lowercase the first character of the result)
	 * @param methodName
	 * @return
	 */
	public static String getFieldName(String methodName) {
		return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
	}
		
	public static String getAccessorName(String fldName) {
		if(fldName == null)
			return null;
		return getMutatorName(fldName).replaceFirst("set", "get");
	}
	
	public static Object getAccessorValue(Object o, String fldName) throws Exception {
		return getAccessorValue(o, fldName, false);
	}
	
	/**
	 * Get the value of one of the accessors of an object identified by its private field name.
	 * IE: fldName = "firstName" returns o.getFirstName().
	 * 
	 * @param o
	 * @param fldName
	 * @param ignoreFldCase
	 * @return
	 * @throws Exception
	 */
	public static Object getAccessorValue(Object o, String fldName, boolean ignoreFldCase) throws Exception {
		if(fldName == null || o == null)
			return null;
		Object val = null;
		String methodName = getAccessorName(fldName);
		Method method = getMethod(methodName, o.getClass(), true);
		if(method != null) {
			val = method.invoke(o);
		}
		return val;
	}	
	
	/**
	 * This method is an alternative to using the getMethod(String name, Class... parameterTypes) method
	 * if the name is known, only one parameter is expected but its Class is unknown.
	 * 
	 * @param name
	 * @param mutatorClass
	 * @return
	 */
	public static Method getMutatorMethod(String name, @SuppressWarnings("rawtypes") Class mutatorClass) {
		Method m = getMethod(name, mutatorClass);
		if(m.getParameterTypes().length == 1) {
			return m;
		}
		return null;
	}

	/**
	 * This method is an alternative to using the getMethod(String name, Class... parameterTypes) method
	 * 
	 * @param methodName
	 * @param clazz
	 * @return
	 */
	public static Method getMethod(String methodName, @SuppressWarnings("rawtypes") Class clazz) {
		return getMethod(methodName, clazz, false);
	}
	
	public static Method getMethod(String methodName, @SuppressWarnings("rawtypes") Class clazz, boolean ignorecase) {
		Method[] methods = clazz.getMethods();
		for(Method m : methods) {
			if(m.getName().equals(methodName) || (ignorecase && m.getName().equalsIgnoreCase(methodName))) {
				return m;
			}
		}
		return null;
	}

}
