package com.youzhixu.sample.io.serializable;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huisman
 * @createAt 2015年5月21日 下午2:04:47
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class OmitSerializeFieldDemo {
	public static void main(String[] args) {
		String dataFile = new File("omitSerialize.ser").getAbsolutePath();
		SerializablePerson person = new SerializablePerson("James", "023565", "男", 1);
		System.out.println("序列化之前====================>" + person);
		writeObject(person, dataFile);

		System.out.println("我们实际读到的数据为：" + readObject(dataFile));
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


class SerializablePerson implements Serializable {
	private static final long serialVersionUID = 1L;
	private static ObjectStreamField[] serializeFields = new ObjectStreamField[] {
			new ObjectStreamField("name", String.class),
			new ObjectStreamField("sex", String.class), new ObjectStreamField("status", int.class)};
	private String name;
	private String password;
	private String sex;
	private transient Date createdAt;
	private transient Date updatedAt;
	private transient int status;

	public SerializablePerson(String name, String password, String sex, int status) {
		super();
		this.name = name;
		this.password = password;
		this.sex = sex;
		this.status = status;
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	@Override
	public String toString() {
		return "SerializablePerson [name=" + name + ", password=" + password + ", sex=" + sex
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status
				+ "]";
	}
}
