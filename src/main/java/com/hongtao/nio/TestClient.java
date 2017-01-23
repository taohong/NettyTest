package com.hongtao.nio;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {

	public static void main(String[] args) {
		TestClient client = new TestClient();
		client.testNioClient();

	}
	
	public void testNioClient(){
		byte[] data = new byte[]{0,22,54,76,43,32,95,4};
		
		Socket socket = null;
		try {
			socket = new Socket("127.0.0.1", 38080);
			socket.getOutputStream().write(data);
			System.out.println("Data is sent to server!");
			Thread.sleep(10000L);
			socket.getOutputStream().write(data);
			System.out.println("Data is sent to server again!");
			Thread.sleep(10000L);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(socket != null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

}
