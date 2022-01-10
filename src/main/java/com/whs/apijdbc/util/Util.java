package com.whs.apijdbc.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Util {

	public static String toJSON(Object value) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		return gson.toJson(value);
	}

	public static String getStackTraceInformation(Throwable ex, boolean displayAll) {
		StringBuffer ret = new StringBuffer();
		if (null == ex) {
			ret.append("Null stack trace reference! Bailing...\n");
			return "";
		}
		// Display the entire blob using printStackTrace().
		// ret.append("The stack according to printStackTrace():\n");
		// ex.printStackTrace();
		ret.append("\n");

		ret.append("Error message: ");
		ret.append(ex.getMessage());
		ret.append("\n");

		// Get the list of StackTraceElements from the throwable.
		StackTraceElement[] stackElements = ex.getStackTrace();

		// Display each of the elements using the various capabilitiies of
		// the StackTraceElements class.
		// Or, just display the top element of the stack (to cut down on
		// all of the repetition.
		if (displayAll) {
			ret.append("The " + stackElements.length + " element" + ((stackElements.length == 1) ? "" : "s")
					+ " of the stack trace:\n");
		} else {
			ret.append("The top element of a " + stackElements.length + " element stack trace:\n");
		}

		for (int lcv = 0; lcv < stackElements.length; lcv++) {
			if (displayAll) {
				ret.append("File name: " + stackElements[lcv].getFileName() + "\n");
				ret.append("Line number: " + stackElements[lcv].getLineNumber() + "\n");
				String className = stackElements[lcv].getClassName();
				String packageName = extractPackageName(className);
				String simpleClassName = extractSimpleClassName(className);
				ret.append("Package name: " + ("".equals(packageName) ? "[default package]" : packageName) + "\n");
				ret.append("Full class name: " + className + "\n");
				ret.append("Simple class name: " + simpleClassName + "\n");
				ret.append("Unmunged class name: " + unmungeSimpleClassName(simpleClassName) + "\n");
				ret.append("Direct class name: " + extractDirectClassName(simpleClassName) + "\n");
				ret.append("Method name: " + stackElements[lcv].getMethodName() + "\n");
				ret.append("Native method?: " + stackElements[lcv].isNativeMethod() + "\n");
			}
			ret.append("toString(): " + stackElements[lcv].toString() + "\n");
		}
		return ret.toString();
	} // End of getStackTraceInformation().

	public static String extractPackageName(String fullClassName) {
		if ((null == fullClassName) || ("".equals(fullClassName)))
			return "";

		// The package name is everything preceding the last dot.
		// Is there a dot in the name?
		int lastDot = fullClassName.lastIndexOf('.');

		// Note that by fiat, I declare that any class name that has been
		// passed in which starts with a dot doesn't have a package name.
		if (0 >= lastDot)
			return "";

		// Otherwise, extract the package name.
		return fullClassName.substring(0, lastDot);
	}

	public static String extractSimpleClassName(String fullClassName) {
		if ((null == fullClassName) || ("".equals(fullClassName)))
			return "";

		// The simple class name is everything after the last dot.
		// If there's no dot then the whole thing is the class name.
		int lastDot = fullClassName.lastIndexOf('.');
		if (0 > lastDot)
			return fullClassName;

		// Otherwise, extract the class name.
		return fullClassName.substring(++lastDot);
	}

	public static String extractDirectClassName(String simpleClassName) {
		if ((null == simpleClassName) || ("".equals(simpleClassName)))
			return "";

		// The direct class name is everything after the last '$', if there
		// are any '$'s in the simple class name. Otherwise, it's just
		// the simple class name.
		int lastSign = simpleClassName.lastIndexOf('$');
		if (0 > lastSign)
			return simpleClassName;

		// Otherwise, extract the last class name.
		// Note that if you have a multiply-nested class, that this
		// will only extract the very last one. Extracting the stack of
		// nestings is left as an exercise for the reader.
		return simpleClassName.substring(++lastSign);
	}

	public static String unmungeSimpleClassName(String simpleClassName) {
		if ((null == simpleClassName) || ("".equals(simpleClassName)))
			return "";

		// Nested classes are set apart from top-level classes by using
		// the dollar sign '$' instead of a period '.' as the separator
		// between them and the top-level class that they sit
		// underneath. Let's undo that.
		return simpleClassName.replace('$', '.');
	}

	public static <T> List<T> toList( T array[] ){
		List<T> list = new ArrayList<>();
		Collections.addAll(list, array);
		return list;
	}
	
	public static <T> T[] toArray( List<T> list, Class<T> klass) {
		if( list == null )
			return null;
		T[] arr = (T[])Array.newInstance(klass, list.size());
		list.toArray(arr);
		return arr;
	}
}
