import java.io.File;
import java.util.Scanner;

/**
 * This class creates a hash table with 311 slots and fills it with countries.
 * It then displays the table, deletes several countries, and displays the table again.
 *
 * @author James Dell'Alba
 * @version 4/20/2020
 */
public class Project5 {
	public static void main(String[] args) throws java.io.FileNotFoundException {
		Scanner input = new Scanner(System.in);
		String fileName = "";
		do {
			System.out.println("enter the name of the file");
			fileName = input.next();
		} while (!fileName.equals("Countries5.csv"));

		File countries = new File(fileName);
		Scanner fileInput = new Scanner(countries);

		fileInput.useDelimiter(",|\n"); //set the delimiter to comma or newline

		String firstLine = fileInput.nextLine(); //move the cursor past the first line of the file

		//create an array of countries
		Country[] countryArray = new Country[155];
		for (int i = 0; i < 155; i++) {
			//fill the array with Country objects
			String name = fileInput.next();
			String code = fileInput.next();
			String capitol = fileInput.next();
			long population = Double.valueOf(fileInput.next()).longValue();
			long gdp = Double.valueOf(fileInput.next()).longValue();
			long happiness = Double.valueOf(fileInput.next()).longValue();
			countryArray[i] = new Country(name, code, capitol, population, gdp, happiness);
		}

		HashTable countryTable = new HashTable(311);

		for (int i = 0; i < 155; i++) {
			countryTable.insert(countryArray[i]);
		}

		countryTable.display();
		countryTable.delete("Cyprus");
		countryTable.delete("Kazakhstan");
		countryTable.delete("Hungary");
		countryTable.delete("Japan");

		System.out.println("gdp/capita of Costa Rica: " + countryTable.find("Costa Rica"));
		System.out.println("gdp/capita of North Cyprus: " + countryTable.find("North Cyprus"));
		System.out.println("gdp/capita of Hungary: " + countryTable.find("Hungary"));

		countryTable.delete("Zambia");
		countryTable.delete("Canada");
		countryTable.delete("Egypt");
		countryTable.delete("Yemen");
		countryTable.delete("India");
		countryTable.display();


		countryTable.printFreeAndCollisions();
	}
}