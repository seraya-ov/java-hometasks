import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class Person {
    public final String firstName;
    public final String lastName;
    public final Address address;
    public final Collection<String> phoneNumbers;

    public Person(String firstName, String lastName, Address address, Collection<String> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
    }
}

class Address {
    public final String city;
    public final String postalCode;

    public Address(String city, String postalCode) {
        this.city = city;
        this.postalCode = postalCode;
    }
}


public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        String[] phoneNumbers = new String[2];
        phoneNumbers[0] = "123123";
        phoneNumbers[1] = "213213";
        Collection<String> strings = Arrays.asList(phoneNumbers);
        String [] str = strings.toArray(new String[]{});
        Person p = new Person("a", "b", new Address("c", "d"), strings);
        System.out.println(new XmlSerializer().serialize(p));
    }
}
