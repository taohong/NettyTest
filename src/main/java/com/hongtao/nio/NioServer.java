package com.hongtao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

	public static void main(String[] args) {
		NioServer server = new NioServer();
		server.service(38080);
	}
	
	public void service(int port){
		try {
			Selector sel = Selector.open();
			
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ServerSocket ss = ssc.socket();
			
			InetSocketAddress addr = new InetSocketAddress(port);
			ss.bind(addr);
			
			ssc.register(sel, SelectionKey.OP_ACCEPT);
			System.out.println("Listen on port: " + port);
			
			while(true){
				int selectedNum = sel.select();
				
				Set<SelectionKey> keys = sel.selectedKeys();
				for(Iterator<SelectionKey> keyIt = keys.iterator();keyIt.hasNext();){
					SelectionKey key = keyIt.next();
					if((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
						ServerSocketChannel serverChannel = (ServerSocketChannel)key.channel();
						SocketChannel socketChannel = serverChannel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(sel, SelectionKey.OP_READ);
						
						keyIt.remove();
						System.out.println("A client is connected!");
					}else if((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
						ByteBuffer buf = ByteBuffer.allocate(1024);
						SocketChannel sc = (SocketChannel)key.channel();
						while(true){
							buf.clear();
							int i = sc.read(buf);
							System.out.println("i is " + i);
							if(i == 0 || i == -1){//no data send from client or client is closed
								try {
									Thread.sleep(50);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							}
							
							buf.flip();
//							buf.put(new byte[]{100});
							sc.write(buf);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						System.out.println("A client request is responsed!");
					}
					
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
