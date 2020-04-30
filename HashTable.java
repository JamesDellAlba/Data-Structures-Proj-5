/**
 * This hash table uses separate chaining to avoid collisions. It creates an array of linked lists to store
 * data, which is in the form of a country name and the gdp per capita of that country.
 *
 * @author James Dell'Alba
 * @version	4/20/2020
 */
public class HashTable {
	private LinkedList[] hashArray;
	private int size;

	/**
	 * This class is the linked list that is used as the hash array.
	 *
	 * @author James Dell'Alba
	 * @version 4/20/2020
	 */
	private class LinkedList {
		public Node first = null;
		public Node last = null;

		/**
		 * This class is each node object that holds one item within the linked list that resides at
		 * a particular index in the hash array.
		 *
		 * @author James Dell'Alba
		 * @version 4/20/2020
		 */
		private class Node {
			String name;
			double gdpPerCapita;
			Node nextNode;

			/**
			 * Constructor for a node object
			 * @param name the name of the country
			 * @param gdpPerCapita the gdp per capita of the country
			 */
			public Node(String name, double gdpPerCapita) {
				this.name = name;
				this.gdpPerCapita = gdpPerCapita;
			}

			/**
			 * Prints out the name of the country followed by the gdp per capita.
			 */
			public void printNode() {
				System.out.printf("%-25s%,-20.2f\n", name, gdpPerCapita);
			}
		}

		/**
		 * Constructor for a linked list that contains no links.
		 */
		public LinkedList() {
		}

		/**
		 * Checks to see if the linked list at an index in the hash array is empty
		 * @return true if empty, false if not empty
		 */
		public boolean isEmpty() {
			return ((this.first == null) && (this.last == null));
		}

		/**
		 * Creates a new node with the given data and inserts it into the linked list which is
		 * at a particular index in the hash array.
		 * @param name the name of the country to be inserted
		 * @param gdpPerCapita the gdp per capita of the country to be inserted
		 */
		public void insert(String name, double gdpPerCapita) {
			if (this.isEmpty()) {
				Node newNode = new Node(name, gdpPerCapita);
				this.first = newNode;
				this.last = newNode;
			} else {
				Node newNode = new Node(name, gdpPerCapita);
				this.last.nextNode = newNode;
				this.last = newNode;
			}
		}

		/**
		 * Searches for the node by name and deletes it from the linked list
		 * @param name the country to delete
		 * @return the name of the country that was deleted
		 */
		public String delete(String name) {
			Node current = this.first;
			Node previous = this.first;
			while (current != null) {
				if (current.name.compareTo(name) == 0) { //node found
					if (this.first == this.last) { //only one node in list
						Node toBeDeleted = current;
						this.first = null;
						this.last = null;
						return toBeDeleted.name;
					} else if (current == this.last) { //delete last node in list
						Node toBeDeleted = current;
						this.last = previous;
						return toBeDeleted.name;
					}else if (current == this.first) { //delete first node in list
						Node toBeDeleted = current;
						this.first = this.first.nextNode;
						return toBeDeleted.name;
					} else {
						Node toBeDeleted = current;
						previous.nextNode = current.nextNode;
						return toBeDeleted.name;
					}
				}
				previous = current;
				current = current.nextNode;
			}
			//node not found
			System.out.println("Country not found");
			return null;
		}

		/**
		 * Searches through an index of the hash array and finds a country
		 * @param name the name of the country to find
		 * @return the node object containing data about country that was found (or null if not found)
		 */
		public Node search(String name) {
			Node current = this.first;
			while (current != null) {
				if (current.name.compareTo(name) == 0) { //node found
					return current;
				}
				current = current.nextNode;
			}
			//node not found
			System.out.println(name + " was not found");
			return null;
		}

		/**
		 * Traverses the linked list, and prints the contents of one index within the hash array.
		 */
		private void print() {
			if (this.isEmpty()) {
				System.out.println("Empty");
			} else {
				Node current = this.first;
				while (current != last.nextNode) {
					current.printNode();
					current = current.nextNode;
				}
			}
		}
	}

	/**
	 * Default constructor for a hash table
	 */
	public HashTable() {
	}

	/**
	 * Constructor that creates a hash table and hash array of a given size, and initializes each index of the
	 * hash array with an empty linked list.
	 * @param size the size of the hash array
	 */
	public HashTable(int size) {
		this.hashArray = new LinkedList[size];
		this.size = size;
		for  (int i = 0; i < size; i++) {
			hashArray[i] = new LinkedList();
		}
	}

	/**
	 * Hashes the name of the country to insert, and inserts it into the appropriate location of the hash array
	 * by calling the insert method of the linked list at that index.
	 * @param country the country to be inserted
	 */
	public void insert(Country country) {
		int index = hashFunction(country.getName());
		hashArray[index].insert(country.getName(), country.getGdpPerCapita());
	}

	/**
	 * Deletes a node from the hash array by calling the delete method of the linked list at the appropriate index.
	 * @param name the name of the country to be deleted
	 */
	public void delete(String name) {
		int index = hashFunction(name);
		String deletedNode = hashArray[index].delete(name);
		System.out.println(deletedNode + " was deleted");
	}

	/**
	 * Searches for a country by name by calling the search method on the appropriate location in the hash array.
	 * @param country the name of the country to find
	 * @return the gdp per capita of the country found (-1 if not found)
	 */
	public double find(String country) {
		int index = hashFunction(country);
		if (hashArray[index].search(country) != null) {
			return hashArray[index].search(country).gdpPerCapita;
		} else {
			return -1;
		}
	}

	/**
	 * Displays the contents of the hash array by calling the print method on every index of the hash array.
	 */
	public void display() {
		for (int i = 0; i < this.size; i++) {
			System.out.printf(i + ". ");
			hashArray[i].print();
		}
	}

	/**
	 * prints the number of empty spaces in the hash array followed by the number of collisions in the hash array.
	 */
	public void printFreeAndCollisions() {
		int numberEmpty = 0;
		int numberCollisions = 0;
		for (int i = 0; i < size; i++) {
			if (hashArray[i].isEmpty()) {
				numberEmpty++;
			} else {
				LinkedList.Node current = hashArray[i].first.nextNode;
				while (current != hashArray[i].last.nextNode) {
					numberCollisions++;
					current = current.nextNode;
				}
			}
		}
		System.out.printf("\n%d empty spaces and %d collisions\n\n", numberEmpty, numberCollisions);
	}

	/**
	 * The function used to hash countries by name and find a place to insert them into the hash array.
	 * @param country the name that will be hashed
	 * @return the index of the array that the name hashes to
	 */
	private int hashFunction(String country) {
		int i = 0;
		int sum = 0;
		while (i < country.length()) {
			sum = sum + country.charAt(i);
			i++;
		}
		int result = sum % this.size;
		return result;
	}
}
