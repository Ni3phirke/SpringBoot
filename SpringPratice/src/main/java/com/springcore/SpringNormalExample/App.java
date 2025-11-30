package com.springcore.SpringNormalExample;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        Alian a=(Alian) context.getBean("Alian");
        System.out.println(  a.getName());
        a.age=15;
        System.out.println(  a.age);
        a.comp.compiling();
             
     
    }
}
