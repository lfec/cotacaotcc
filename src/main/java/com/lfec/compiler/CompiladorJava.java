package com.lfec.compiler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class CompiladorJava {

	private static final String UTF_8 = "UTF-8";
	
	public static String readCode(String sourcePath) throws FileNotFoundException {
        InputStream stream = new FileInputStream(sourcePath);
        String separator = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().collect(Collectors.joining(separator));
    }

	public static Path saveSource(String source, String javaName,String destPath) throws IOException {
        Path sourcePath = Paths.get(destPath, javaName);
        Files.write(sourcePath, source.getBytes(UTF_8));
        return sourcePath;
    }

	public static Path compileSource(Path javaFile, String className) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFile.toFile().getAbsolutePath());
        return javaFile.getParent().resolve(className);
    }

	public static  void runClass(Path javaClass,String name, String methodName, Object... args)
            throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        URL classUrl = javaClass.getParent().toFile().toURI().toURL();
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        Class<?> clazz = Class.forName(name, true, classLoader);
        
        Method[] methods = clazz.getMethods();
        Method realMethod = null;
        for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(name)) {
				realMethod = method;
				break;
			}
		}
        
        try {
        	if (realMethod!=null) {
        		realMethod.invoke(null, args);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
        
    }

}
