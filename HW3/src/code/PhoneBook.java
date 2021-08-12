package code;

import java.util.ArrayList;
import java.util.List;

import given.ContactInfo;
import given.DefaultComparator;

/*
 * A phonebook class that should:
 * - Be efficiently (O(log n)) searchable by contact name
 * - Be efficiently (O(log n)) searchable by contact number
 * - Be searchable by e-mail (can be O(n)) 
 * 
 * The phonebook assumes that names and numbers will be unique
 * Extra exercise (not to be submitted): Think about how to remove this assumption
 * 
 * You need to use your own data structures to implement this
 * 
 * Hint: You need to implement a multi-map! 
 * 
 */
public class PhoneBook {

	BinarySearchTree<String, ContactInfo> treeByName = new BinarySearchTree<String, ContactInfo>();
	BinarySearchTree<String, ContactInfo> treeByNumber = new BinarySearchTree<String, ContactInfo>();
	
	public PhoneBook() {
		treeByName.setComparator(new DefaultComparator<String>());
		treeByNumber.setComparator(new DefaultComparator<String>());
	}

	// Returns the number of contacts in the phone book
	public int size() {
		return treeByName.size();
	}

	// Returns true if the phone book is empty, false otherwise
	public boolean isEmpty() {
		return treeByName.isEmpty();
	}

	//Adds a new contact or overwrites an existing contact with the given info
	// Args should be given in the order of e-mail and address which is handled for
	// you
	// Note that it can also be empty. If you do not want to update a field pass
	// null
	public void addContact(String name, String number, String... args) {
		int numArgs = args.length;
		String email = null;
		String address = null;
		/*
		 * Add stuff here if needed
		 */

		if (numArgs > 0)
			if (args[0] != null)
				email = args[0];
		if (numArgs > 1)
			if (args[1] != null)
				address = args[1];
		if (numArgs > 2)
			System.out.println("Ignoring extra arguments");
		
		ContactInfo newContact = new ContactInfo(name,number);
		newContact.setAddress(address);
		newContact.setEmail(email);
		
		treeByName.put(name, newContact);
		treeByNumber.put(number,newContact);
		
	}

	// Return the contact info with the given name
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByName(String name) {
		BinaryTreeNode<String,ContactInfo> node = treeByName.getNode(name);
		if(treeByName.isExternal(node)) return null;
		else return node.getValue();

	}

	// Return the contact info with the given phone number
	// Return null if it does not exists
	// Should be O(log n)!
	public ContactInfo searchByPhone(String phoneNumber) {
		BinaryTreeNode<String,ContactInfo> node = treeByNumber.getNode(phoneNumber);
		if(treeByNumber.isExternal(node) || node.getValue().getName()==null) return null;
		else return node.getValue();
		

	}

	// Return the contact info with the given e-mail
	// Return null if it does not exists
	// Can be O(n)
	public ContactInfo searchByEmail(String email) {
		List<ContactInfo> contacts = getContacts();
		ContactInfo emailContact = null;
		for(ContactInfo contact : contacts) {
			if(contact.getEmail() != null) {
				if(email != null && contact.getEmail().equals(email))  {
				emailContact =contact; 
				return emailContact;
			}
			}
		}
		
		return null;

	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given name to delete, false otherwise
	public boolean removeByName(String name) {
		ContactInfo contact = searchByName(name);
		if(contact==null || name==null) return false;
		else {
			treeByName.remove(contact.getName());
			treeByNumber.remove(contact.getNumber());
			return true;
		}
		
	}

	// Removes the contact with the given name
	// Returns true if there is a contact with the given number to delete, false otherwise
	public boolean removeByNumber(String phoneNumber) {
		ContactInfo contact = searchByPhone(phoneNumber);
		if(contact==null || phoneNumber==null) return false;
		else {
			treeByName.remove(contact.getName());
			treeByNumber.remove(contact.getNumber());
			return true;
		}
	}

	// Returns the number associated with the name
	public String getNumber(String name) {
		ContactInfo contact = searchByName(name);
		if(contact==null) return null;else 
			return contact.getNumber();
	}

	// Returns the name associated with the number
	public String getName(String number) {
		ContactInfo contact = searchByPhone(number);
		if(contact==null) return null;else 
		return contact.getName();
	}

	// Update the email of the contact with the given name
	// Returns true if there is a contact with the given name to update, false otherwise
	public boolean updateEmail(String name, String email) {
		ContactInfo contact = searchByName(name);
		if(contact==null) return false;
		contact.setEmail(email); 
		return true;
		
	}

	// Update the address of the contact with the given name
	// Returns true if there is a contact with the given name to update, false otherwise
	public boolean updateAddress(String name, String address) {
		ContactInfo contact = searchByName(name);
		if(contact==null) return false;
		contact.setAddress(address); 
		return true;
		
	}

	// Returns a list containing the contacts in sorted order by name
	public List<ContactInfo> getContacts() {
		List<BinaryTreeNode<String, ContactInfo>> contacts = treeByName.getNodesInOrder();
		List<ContactInfo> list = new ArrayList<ContactInfo>();
		
		for(BinaryTreeNode<String, ContactInfo> contact : contacts) {
			if(contact.getValue().getName() != null)
				list.add(contact.getValue());
		}
		
		return list;
	}

	// Prints the contacts in sorted order by name
	public void printContacts() {
		List<ContactInfo> contacts = getContacts();
		for(ContactInfo contact : contacts) {
			System.out.println(contact.toString());
		} 
	}
}
