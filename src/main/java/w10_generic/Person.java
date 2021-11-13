package w10_generic;

import java.util.ArrayList;
import java.util.List;

public class Person implements Comparable<Person> {
    private String name;
    private int age;
    private String address;

    public Person() {
    }

    /**
     * constructor.
     *
     * @param name    .
     * @param age     .
     * @param address .
     */
    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(Person o) {
        if (!this.name.equals(o.name)) {
            return name.compareTo(o.name);
        }
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return "(name=\"" + name + '\"'
                + ", age=" + age
                + ", address=" + address
                + ')';
    }

    /**
     * test client.
     *
     * @param args .
     */
    public static void main(String[] args) {
        Person person1 = new Person("Nguyen A", 22, "...");
        Person person2 = new Person("Nguyen A", 20, "...");
        Person person3 = new Person("Le B", 20, "...");

        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);

        Week11.sortGeneric(personList);
        Week11.printArr(personList);
    }
}
