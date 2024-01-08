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

    // static String findRelationship(Person p1, Person p2) {
    //     if (p1.father != null) {
    //         if (p1.father.equals(p2)) {
    //             return p1.gender.equals("men") ? "Son" : "Daughter";
    //         }
    //         if (p1.father.father != null && p1.father.father.equals(p2)) {
    //             return p1.gender.equals("men") ? "Grandson" : "Granddaughter";
    //         }
    //         if (p1.father.equals(p2.father)) {
    //             return "Sibling";
    //         }
    //     }
    //     if (p1.mother != null) {
    //         if (p1.mother.equals(p2)) {
    //             return p1.gender.equals("men") ? "Son" : "Daughter";
    //         }
    //         if (p1.mother.mother != null && p1.mother.mother.equals(p2)) {
    //             return p1.gender.equals("men") ? "Grandson" : "Granddaughter";
    //         }
    //         if (p1.mother.equals(p2.mother)) {
    //             return "Sibling";
    //         }
    //     }
    //     return "No direct relationship found";
    // }

    static String findRelationship1(Person p1, Person p2) {
        if (p1.father != null) {
            if (p1.father.equals(p2)) {
                return "Father";
            }
            if (p1.father.father != null && p1.father.father.equals(p2)) {
                return "Grandfather";
            }
            if (p1.father.equals(p2.father)) {
                return "Sibling";
            }
            if ((p1.father.father != null && p1.father.father.equals(p2.father)) || 
                (p1.father.mother != null && p1.father.mother.equals(p2.mother))) {
                return "Uncle";
            }
        }
        if (p1.mother != null) {
            if (p1.mother.equals(p2)) {
                return "Mother";
            }
            if (p1.mother.mother != null && p1.mother.mother.equals(p2)) {
                return "Grandmother";
            }
            if (p1.mother.equals(p2.mother)) {
                return "Sibling";
            }
            if ((p1.mother.father != null && p1.mother.father.equals(p2.father)) || 
                (p1.mother.mother != null && p1.mother.mother.equals(p2.mother))) {
                return "Aunt";
            }
        }
        return "No direct relationship found";
    }

    static String findRelationship2(Person p1, Person p2) {
        if (p1.father != null) {
            if (p1.father.equals(p2)) {
                return "Son";
            }
            if (p1.father.father != null && p1.father.father.equals(p2)) {
                return "Grandson";
            }
            if (p1.father.equals(p2.father)) {
                return "Sibling";
            }
            if ((p1.father.father != null && p1.father.father.equals(p2.father)) || 
                (p1.father.mother != null && p1.father.mother.equals(p2.mother))) {
                return "Nephew";
            }
        }
        if (p1.mother != null) {
            if (p1.mother.equals(p2)) {
                return "Daughter";
            }
            if (p1.mother.mother != null && p1.mother.mother.equals(p2)) {
                return "Granddaughter";
            }
            if (p1.mother.equals(p2.mother)) {
                return "Sibling";
            }
            if ((p1.mother.father != null && p1.mother.father.equals(p2.father)) || 
                (p1.mother.mother != null && p1.mother.mother.equals(p2.mother))) {
                return "Niece";
            }
        }
        return "No direct relationship found";
    }

    static void printRelationshipBothSides(Person p1, Person p2) {
        String relationship1 = findRelationship1(p1, p2);
        String relationship2 = findRelationship2(p1, p2);
        System.out.println(p1.name + " is the " + relationship1 + " of " + p2.name);
        System.out.println(p2.name + " is the " + relationship2 + " of " + p1.name);
    }
    public static void main(String[] args) throws IOException {
        parseCSV("input.csv");

        Person p1 = people.get("Shireen Baratheon");
        Person p2 = people.get("Renly Baratheon");
        printRelationshipBothSides(p1, p2);
    }
}
