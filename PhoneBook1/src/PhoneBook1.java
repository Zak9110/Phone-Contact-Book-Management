//	ITS350
//	Dr. Chnoor Maheadeen Rahman
// 	Zaid Khudhur Salih - section 1
// 	Kamiran Sulaiman Ilyas - section 2
// 	Date: 4 / 4 / 2024


class Contact {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}

class Node {
    Contact contact;
    Node next;
    Node prev;

    Node(Contact contact) {
        this.contact = contact;
        next = null;
        prev = null;
    }
}

class Stack {
    Node top;

    Stack() {
        top = null;
    }

    // Time Complexity: O(1)
    void push(Contact contact) {
        Node newNode = new Node(contact);
        newNode.next = top;
        if (top != null)
            top.prev = newNode;
        top = newNode;
    }

    // Time Complexity: O(1)
    Contact pop() {
        if (isEmpty()) {
            return null;
        }
        Contact poppedContact = top.contact;
        top = top.next;
        if (top != null)
            top.prev = null;
        return poppedContact;
    }

    // Time Complexity: O(1)
    boolean isEmpty() {
        return top == null;
    }
}

class PhoneBook {
    Node[] contactsArray; // Array to store contacts for direct access
    final int MAX_CONTACTS = 26; // Assuming only lowercase characters (a-z)
    Stack recentCalls;

    public PhoneBook() {
        contactsArray = new Node[MAX_CONTACTS];
        recentCalls = new Stack();
    }

    // Time Complexity: O(1)
    void save(String name, String phoneNumber) {
        char firstChar = Character.toLowerCase(name.charAt(0));
        int index = firstChar - 'a'; // Convert character to index
        Node newNode = new Node(new Contact(name, phoneNumber));
        if (contactsArray[index] == null) {
            contactsArray[index] = newNode;
        } else {
            Node temp = contactsArray[index];
            while (temp.next != null) {
                if (temp.contact.name.equalsIgnoreCase(name)) {
                    System.out.println("A contact under the same name already exists. Please use a different name.");
                    return;
                }
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.prev = temp;
        }
    }

    // Time Complexity: O(n)
    void display() {
        boolean isEmpty = true;
        for (char c = 'a'; c <= 'z'; c++) {
            Node temp = contactsArray[c - 'a'];
            if (temp != null) {
                isEmpty = false;
                System.out.println(c);
                System.out.println("_________________");
                while (temp != null) {
                    System.out.println(temp.contact.name + ": " + temp.contact.phoneNumber);
                    temp = temp.next;
                }
            }
        }
        if (isEmpty) {
            System.out.println("Phone book is empty.");
        }
    }

    // Time Complexity: O(n)
    void call(String name) {
        char firstChar = Character.toLowerCase(name.charAt(0));
        int index = firstChar - 'a'; // Convert character to index
        Node temp = contactsArray[index];
        while (temp != null) {
            if (temp.contact.name.equalsIgnoreCase(name)) {
                System.out.println("Calling " + name + "...... You called " + name + " successfully.");
                // Store entire contact in recent call stack
                recentCalls.push(temp.contact);
                return;
            }
            temp = temp.next;
        }
        System.out.println("No results for " + name);
    }

    // Time Complexity: O(n)
    void recent() {
        System.out.println("List of recent calls:");
        Stack tempStack = new Stack();
        while (!recentCalls.isEmpty()) {
            Contact contact = recentCalls.pop();
            Node foundContact = findContact(contact.name);
            if (foundContact != null) {
                System.out.println(foundContact.contact.name + ": " + contact.phoneNumber);
            } else {
                System.out.println("Unknown user: " + contact.phoneNumber);
            }
            tempStack.push(contact); // push contact back to tempStack
        }
        while (!tempStack.isEmpty()) {
            recentCalls.push(tempStack.pop()); // push contact back to recentCalls stack
        }
    }

    // Time Complexity: O(n)
    Node findContact(String name) {
        char firstChar = Character.toLowerCase(name.charAt(0));
        int index = firstChar - 'a'; // Convert character to index
        Node temp = contactsArray[index];
        while (temp != null) {
            if (temp.contact.name.equalsIgnoreCase(name)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    // Time Complexity: O(1)
    void delete(String name) {
        Node node = findContact(name);
        if (node != null) {
            if (node.prev != null)
                node.prev.next = node.next;
            else
                contactsArray[node.contact.name.toLowerCase().charAt(0) - 'a'] = node.next;
            if (node.next != null)
                node.next.prev = node.prev;
            System.out.println("Contact deleted successfully: " + node.contact.phoneNumber);
        } else {
            System.out.println("Contact not found.");
        }
    }

    // Time Complexity: O(n)
    void modify(String name, String newPhoneNumber) {
        Node node = findContact(name);
        if (node != null) {
            node.contact.phoneNumber = newPhoneNumber;
            System.out.println("Contact modified successfully.");
        } else {
            System.out.println("No results for " + name);
        }
    }
}

public class PhoneBook1 {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();

        // Test operations
        phoneBook.save("Zaid", "111111111");
        phoneBook.save("Kameran", "222222222");
        phoneBook.save("Ali", "123456789");
        phoneBook.save("Bahra", "987654321");
        phoneBook.save("Sara", "456123789");
        phoneBook.save("Baran", "789456123");
        phoneBook.save("ara", "654123987");

        System.out.println("Displaying contacts:");
        phoneBook.display();

        System.out.println("\nCalling Bahra:");
        phoneBook.call("Bahra");
        
        System.out.println("\nCalling Sara:");
        phoneBook.call("Sara");

        System.out.println("\nCalling Zara:");
        phoneBook.call("Zara");

        System.out.println("\nDeleting Sara:");
        phoneBook.delete("Sara");

        System.out.println("\nModifying Ali's number:");
        phoneBook.modify("Ali", "999999999");

        System.out.println("\nDisplaying contacts after modifications:");
        phoneBook.display();

        System.out.println("\nDisplaying recent calls:");
        phoneBook.recent();
    }
}
