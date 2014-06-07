package cn.jerry.mini_mvc.aop.example;

public class StudentImplForCglib {
	public void action() {
		System.out.println("[StudentImpl]Enter Student action");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("[StudentImpl]Leave Student action");
	}
}