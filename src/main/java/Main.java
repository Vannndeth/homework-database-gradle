import com.github.javafaker.Faker;
import model.Person;
import model.User;
import repository.PersonRepository;
import repository.UserRepository;
import service.PersonService;
import utils.TableUtils;
import view.MainView;

import java.util.*;


public class Main {
    private static PersonService personService =
            new PersonService(new PersonRepository());
    private static UserRepository userRepository =
            new UserRepository();
    private static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        int option;
        User user = new User();
        user.checkUser(input);
        do {
            option = MainView.renderMain(input);
            switch (option) {
                case 1: {
                    input.nextLine(); // clear buffer
                    System.out.println(
                            personService.createPerson(input) > 0 ?
                                    "Successfully Created a New Person"
                                    : ""
                    );

                }
                break;
                case 2: {
                    System.out.println(
                            personService
                                    .updatePerson(input) > 0 ?
                                    "Successfully Update Person Info"
                                    : ""
                    );
                }
                break;
                case 3: {
                    System.out.println(
                            personService
                                    .deletePersonByID(input) > 0 ?
                                    "Successfully Remove the Person"
                                    : "");
                    ;
                }
                break;
                case 4: {
                    int showOption;
                    List<String> showMenu = new ArrayList<>(List.of(
                            "Show Original Order",
                            "Show Descending Order (ID)",
                            "Show Descending Order (name) ",
                            "Exit"));
                    do {
                        TableUtils.renderMenu(showMenu, "Show Person Information");
                        System.out.print("Choose your option: ");
                        showOption = input.nextInt();


                        switch (showOption) {
                            case 1:

                                TableUtils.renderPersonTable(personService.getAllPerson());
                                break;
                            case 2:
                                // descending id
                                TableUtils.renderPersonTable(
                                        personService.getAllPersonDescendingByID()
                                );
                                break;
                            case 3:
                                // descending name
                                TableUtils.renderPersonTable(
                                        personService.getAllPersonDescendingByName()
                                );
                                break;
                            default:
                                System.out.println("Invalid option ...!!!!");
                                break;
                        }
                    } while (showOption != showMenu.size());
                }
                break;
                case 5: {
                    int searchOption;
                    List<String> searchMenu = new ArrayList<>(Arrays.asList(
                            "Search By ID",
                            "Search By Gender",
                            "Search By Country",
                            "Exit"));
                    do {
                        TableUtils.renderMenu(searchMenu, "Search for Person");
                        System.out.print("Choose your option:");
                        searchOption = input.nextInt();
                        switch (searchOption) {
                            case 1:
                                int searchID = 0;
                                System.out.println("Enter Person ID to search:");
                                searchID = input.nextInt();
                                int finalSearchID = searchID;
                                try {
                                    Person optionalPerson =
                                            personService.getAllPerson()
                                                    .stream()
                                                    .filter(person -> person.getId() == finalSearchID)
                                                    .findFirst()
                                                    .orElseThrow(() -> new ArithmeticException("Whatever exception!! "));
                                    TableUtils.renderPersonTable(
                                            Collections.singletonList(optionalPerson));
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    System.out.println("There is no element with ID=" + searchID);
                                }

                                break;
                            case 2:
                                System.out.println("Enter Person Gender to search:");
                                String searchGender = input.next();
                                List<Person> personList = personService.getAllPerson()
                                        .stream()
                                        .filter(person -> person.getGender().equalsIgnoreCase(searchGender))
                                        .toList();
                                TableUtils.renderPersonTable(personList);
                                break;
                            case 3:
                                System.out.println("Enter Person Country to search:");
                                String searchCountry = input.next();
                                List<Person> personListByCountry = personService.getAllPerson()
                                        .stream()
                                        .filter(person -> person.getAddress().equalsIgnoreCase(searchCountry))
                                        .toList();
                                TableUtils.renderPersonTable(personListByCountry);
                                break;
                        }

                    } while (searchOption != searchMenu.size());

                }
                break;
                case 6:
                    System.out.println("Generating Random Person Data");
                    System.out.println("Enter the number of person to generate: ");
                    int n = input.nextInt();
                    personService.generateRandomPerson(n);
                    personService.addNewPerson(personService.generateRandomPerson(n));
                    break;
                case 7:
                    System.out.println("Good Bye");
                    break;
                default:
                    System.out.println("Invalid Option!!!!!! ");
                    break;
            }
        } while (option != 7);


    }
}
