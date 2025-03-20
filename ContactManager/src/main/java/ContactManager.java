import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Phone: " + phoneNumber + " | Email: " + email;
    }
}

public class ContactManager {
    private static final ArrayList<Contact> contacts = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Simple Contact Management System ---");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Search Contact");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    searchContact();
                    break;
                case 6:
                    System.out.println("Exiting Contact Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        } while (choice != 6);

        scanner.close();
    }

    // 1. Add Contact
    private static void addContact() {
        System.out.print("\nEnter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter Email Address: ");
        String email = scanner.nextLine();

        contacts.add(new Contact(name, phoneNumber, email));
        System.out.println("Contact added successfully!");
    }

    // 2. View Contacts (Sorted Alphabetically)
    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found!");
            return;
        }
        // Sorting contacts alphabetically
        Collections.sort(contacts, Comparator.comparing(Contact::getName));

        System.out.println("\n---Contact List (Sorted) ---");
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    // 3. Update Contact
    private static void updateContact() {
        System.out.print("\nEnter the name of the contact to update: ");
        String name = scanner.nextLine();
        Contact contact = findContact(name);

        if (contact == null) {
            System.out.println("Contact not found!");
            return;
        }

        System.out.print("Enter new phone number (Leave blank to keep unchanged): ");
        String newPhone = scanner.nextLine();
        System.out.print("Enter new email (Leave blank to keep unchanged): ");
        String newEmail = scanner.nextLine();

        if (!newPhone.isEmpty()) {
            contact.setPhoneNumber(newPhone);
        }
        if (!newEmail.isEmpty()) {
            contact.setEmail(newEmail);
        }

        System.out.println("Contact updated successfully!");
    }

    // 4. Delete Contact
    private static void deleteContact() {
        System.out.print("\nEnter the name of the contact to delete: ");
        String name = scanner.nextLine();
        Contact contact = findContact(name);

        if (contact == null) {
            System.out.println("Contact not found!");
            return;
        }

        contacts.remove(contact);
        System.out.println("Contact deleted successfully!");
    }

    // 5. Search Contact
    private static void searchContact() {
        System.out.print("\nEnter name or phone number to search: ");
        String query = scanner.nextLine();

        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(query) || contact.getPhoneNumber().contains(query)) {
                System.out.println(contact);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching contact found!");
        }
    }

    // Helper Method: Find Contact by Name
    private static Contact findContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
}
