package SorterExample;

public class Person{
	private int age;
	private String name;
	
	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "SorterExample.Person [age=" + age + ", name=" + name + "]";
	}
}
