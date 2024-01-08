import java.util.*;
import java.io.*;

class Person {
    String name;
    String gender;
    Person father;
    Person mother;

    Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }
}

public class GenealogyApp {
    static Map<String, Person> people = new HashMap<>();

    static void parseCSV(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        List<String[]> relationships = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            if (parts.length == 2) {
                people.put(parts[0], new Person(parts[0], parts[1]));
            } else if (parts.length == 3) {
                relationships.add(parts);
            }
        }
        for (String[] parts : relationships) {
            Person parent = people.get(parts[0]);
            Person child = people.get(parts[2]);
            if (parts[1].equals("father")) {
                child.father = parent;
            } else if (parts[1].equals("mother")) {
                child.mother = parent;
            }
        }
        reader.close();
    }

    static String findRelationship(Person p1, Person p2) {
        if (p1.father != null) {
            if (p1.father.equals(p2)) {
                return p1.gender.equals("men") ? "Son" : "Daughter";
            }
            if (p1.father.father != null && p1.father.father.equals(p2)) {
                return p1.gender.equals("men") ? "Grandson" : "Granddaughter";
            }
            if (p1.father.equals(p2.father)) {
                return "Sibling";
            }
        }
        if (p1.mother != null) {
            if (p1.mother.equals(p2)) {
                return p1.gender.equals("men") ? "Son" : "Daughter";
            }
            if (p1.mother.mother != null && p1.mother.mother.equals(p2)) {
                return p1.gender.equals("men") ? "Grandson" : "Granddaughter";
            }
            if (p1.mother.equals(p2.mother)) {
                return "Sibling";
            }
        }
        return "No direct relationship found";
    }
    public static void main(String[] args) throws IOException {
        parseCSV("input.csv");

        Person p1 = people.get("Gendry");
        Person p2 = people.get("Steffon Baratheon");
        System.out.println(findRelationship(p1, p2));
    }
}
