package com.youzhixu.sample.io.serializable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author liuhui
 * @createAt 2015年5月21日 下午12:56:31
 * @since 1.0.0
 * @Copyright (c) 2015, Lianjia Group All Rights Reserved.
 */
public class SerializeProxyDemo {
	public static void main(String[] args) {
		File path = new File("");
		System.out.println("path=" + path.getAbsolutePath());
		String dataFile = new File("protectedData.ser").getAbsolutePath();
		// 工程的根目录下生成文件
		System.out.println("data path=" + dataFile);
		ProtectedPerson person = new ProtectedPerson("James", "男", "023565");
		writeObject(person, dataFile);

		System.out.println("我们实际返回的数据为：" + readObject(dataFile));
	}

	private static void writeObject(Object obj, String dataFile) {
		try (OutputStream f = new BufferedOutputStream(new FileOutputStream(dataFile));
				ObjectOutput s = new ObjectOutputStream(f);) {
			s.writeObject(obj);
			s.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T readObject(String dataFile) {
		try (InputStream f = new BufferedInputStream(new FileInputStream(dataFile));
				ObjectInput s = new ObjectInputStream(f);) {
			return (T) s.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}


/**
 * 1,Person和PersonProxy实现了序列化接口; <br>
 * 2，Person提供了一个的writeReplace方法，将序列化的对象指定为PersonProxy <br>
 * 3，PersonProxy是一个内部的私有静态类，因此其他类无法访问它。
 * 4，PersonProxy应该实现readResolve()方法，返回Person对象，这样当Person类被反序列化时，在内部其实是PersonProxy类被反序列化了，
 * 之后它的readResolve()方法被调用，我们得到了Person对象。<br>
 * 5，在Person类中实现readObject()方法，直接抛出InvalidObjectException异常，防止黑客通过伪造Person对象的流并对其进行解析，继而执行攻击。
 */
class ProtectedPerson implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String sex;
	private String password;

	public ProtectedPerson(String name, String sex, String password) {
		super();
		this.name = name;
		this.sex = sex;
		this.password = password;
	}

	/**
	 * <p>
	 * 私有构造setter，静态内部类可以访问，不对外暴露
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param password
	 */
	private void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 一个内部的私有静态类，因此其他类无法访问它。
	 * DataProxy应该实现readResolve()方法，返回Data对象，这样当Data类被反序列化时，在内部其实是DataProxy类被反序列化了
	 * ，之后它的readResolve()方法被调用，我们得到了Data对象。
	 */
	private static class PersonProxy implements Serializable {
		private static final long serialVersionUID = 1L;
		private static final String PREFIX = "start";
		private static final String SUFFIX = "end";

		private ProtectedPerson person;

		public PersonProxy(ProtectedPerson person) {
			super();
			this.person = person;
		}

		private void writeObject(ObjectOutputStream out) throws IOException {
			System.out.println("====================>开始调用PersonProxy的writeObject输出数据");
			// 可以调用默认序列化机制。
			out.writeObject(PREFIX + person.getPassword() + SUFFIX);
			out.writeObject(person.getName());
			out.writeObject(person.getSex());
		}

		private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
			System.out.println("====================>开始调用PersonProxy的readObject读取数据");
			String password = (String) in.readObject();
			String name = (String) in.readObject();
			String sex = (String) in.readObject();
			this.person = new ProtectedPerson(name, sex, password);
			System.out.println("====================>PersonProxy读取到：" + this.person);
		}

		private Object readResolve() throws InvalidObjectException {
			System.out.println("====================>开始调用PersonProxy提供的readResolve方法，替换返回的对象");
			String encryptPassword = person.getPassword();
			if (encryptPassword.startsWith(PREFIX) && encryptPassword.endsWith(SUFFIX)) {
				// 解密
				String decryptPassword =
						encryptPassword.substring(PREFIX.length(), encryptPassword.length()
								- SUFFIX.length());

				person.setPassword(decryptPassword);
				return person;
			} else
				throw new InvalidObjectException("对象不完整");
		}

	}

	public String getName() {
		return name;
	}

	public String getSex() {
		return sex;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * <p>
	 * 当开始序列化向Stream写入时，将Person替换为它的代理类PersonProxy
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @return
	 */
	private Object writeReplace() {
		System.out
				.println("====================>写入Stream之前将待序列化的ProtectedPerson对象替换为其代理类PersonProxy");
		return new PersonProxy(this);
	}

	/**
	 * <p>
	 * 这个方法会在伪造的序列化数据被反序列化时调用 因为我们实际序列化的是PersonProxy，相应的反序列化则会执行它的反序列化方法
	 * </p>
	 * 
	 * @since: 1.0.0
	 * @param in
	 * @throws InvalidObjectException
	 */
	private void readObject(ObjectInputStream in) throws InvalidObjectException {
		System.out.println("====================>猜猜ProtectedPerson提供的readObject会不会调用");
		throw new InvalidObjectException("不支持反序列化");
	}

	@Override
	public String toString() {
		return "ProtecedPerson [name=" + name + ", sex=" + sex + ", password=" + password + "]";
	}
}
