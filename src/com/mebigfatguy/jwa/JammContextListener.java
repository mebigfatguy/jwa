package com.mebigfatguy.jwa;

import java.lang.management.ManagementFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.github.jamm.MemoryMeter;

import com.sun.tools.attach.VirtualMachine;



public class JammContextListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		installJavaAgent();
		
		MemoryMeter mm = new MemoryMeter();
		
		long size = mm.measureDeep(event);
		System.out.println(size);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
	private void installJavaAgent() {
        
        try {
    		String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
            int atPos = nameOfRunningVM.indexOf('@');
            String pid = nameOfRunningVM.substring(0, atPos);

            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent("/home/dave/dev/jwa/lib/jamm-0.2.7-SNAPSHOT.jar", "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}
