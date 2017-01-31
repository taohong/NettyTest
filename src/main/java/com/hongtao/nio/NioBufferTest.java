package com.hongtao.nio;

import java.nio.ByteBuffer;

public class NioBufferTest {

	public static void main(String[] args) {
		// ����һ������Ϊ10��byte���ݻ�����  
				ByteBuffer buff = ByteBuffer.allocate(10);  
				// ��仺����  
				buff.put((byte)'A');  
				buff.put((byte)'B');  
				buff.put((byte)'C');  
				buff.put((byte)'D');  
				System.out.println("first put : " + new String(buff.array()));  
				//��ת  
				buff.flip();  
				//�ͷ�  
				System.out.println((char)buff.get());  
//				System.out.println((char)buff.get());  
				//ѹ��  
				buff.compact();  
				System.out.println("compact after get : " + new String(buff.array()));  
				//�������  
				buff.put((byte)'E');  
				buff.put((byte)'F');  
				//�������  
				System.out.println("put after compact : " + new String(buff.array())); 
	}

}
