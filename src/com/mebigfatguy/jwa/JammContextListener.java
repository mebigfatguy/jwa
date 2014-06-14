package com.mebigfatguy.jwa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(JammContextListener.class.getResourceAsStream("/jamm.location")))) {
    		String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
            int atPos = nameOfRunningVM.indexOf('@');
            String pid = nameOfRunningVM.substring(0, atPos);

            VirtualMachine vm = VirtualMachine.attach(pid);
            String location = br.readLine();
            vm.loadAgent(location, "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}
