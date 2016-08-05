package java8;

import java.util.HashSet;
import java.util.Set;

/*
 * 无用的的对象无法被回收
 */
public class MemoryLeakExample {
	
	static class Person {
		
		private String name ;
		private int age;
		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		
		//重写hashcode和equals 下面的实验才能成功
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (age != other.age)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		
		
	}
	
	public static void test1() {
		Person person1 = new Person("jyy",27);
		Person person2 = new Person("zll",28);
		
		Set<Person> set = new HashSet<Person>();
		set.add(person1);
		set.add(person2);
		person1.setAge(28);
		set.remove(person1);
		set.add(person1);
		System.out.println(set.size());//3
	}
	
	public static void main(String[] args) {
		MemoryLeakExample.test1();
	}

}
