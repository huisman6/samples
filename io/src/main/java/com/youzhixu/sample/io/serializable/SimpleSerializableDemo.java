package com.youzhixu.sample.io.serializable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author huiman
 * @createAt 2015年5月21日 上午9:29:03
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class SimpleSerializableDemo {
	public static void main(String[] args) {
		String dataFile = "perser.dat";
		// Person person = new Person("James", "男");
		// CustomizeSerialPerson person = new CustomizeSerialPerson("James", "男");
		CustomizeExternalizePerson person = new CustomizeExternalizePerson("James", "男");
		writeObject(person, dataFile);
		System.out.println(readObject(dataFile));
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


class Person implements Serializable {
	private String name;
	private String sex;

	public Person(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}

	public Person() {
		this.sex = "None";
	}


	@Override
	public String toString() {
		return "Person [name=" + name + ", sex=" + sex + "]";
	}

}


class CustomizeSerialPerson implements Serializable {
	private String name;
	private String sex;

	public CustomizeSerialPerson() {
		super();
		this.sex = "None";
	}

	public CustomizeSerialPerson(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		// 可以调用默认序列化机制。
		// out.defaultWriteObject();
		out.writeObject(this.name);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// 先读取默认机制保存的对象
		// in.defaultReadObject();
		this.name = (String) in.readObject();
	}

	@Override
	public String toString() {
		return "CustomizeSerialPerson [name=" + name + ", sex=" + sex + "]";
	}

}


class CustomizeExternalizePerson implements Externalizable {
	private String name;
	private String sex;

	public CustomizeExternalizePerson() {
		super();
		this.sex = "None";
	}

	public CustomizeExternalizePerson(String name, String sex) {
		super();
		this.name = name;
		this.sex = sex;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(this.name);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		this.name = (String) in.readObject();
	}

	@Override
	public String toString() {
		return "CustomizeExternalizePerson [name=" + name + ", sex=" + sex + "]";
	}

}
