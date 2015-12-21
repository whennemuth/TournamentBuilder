package com.warren.tournament.util;

import java.io.PrintWriter;
import java.io.StringWriter;

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
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		String trace = sw.getBuffer().toString();
		return trace;
	}

}
