package model;

import lombok.*;
import lombok.experimental.Accessors;
import service.PersonService;

import java.util.Scanner;

//@Getter
//@Setter
//@ToString
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Accessors(chain = true)
public class Person extends User{
    private int id;
    private String fullName;
    private String gender;
    private String email;
    private String address;
    private int user_id;


    public Person addPerson(Scanner input){
        //auto increment from database
        PersonService personService = new PersonService();
        id = personService.getAllPerson().size() + 1;
        System.out.println("Enter fullname: ");
        fullName = input.nextLine();
        System.out.println("Enter gender : ");
        gender = input.nextLine();
        System.out.println("Enter Address : ");
        address = input.nextLine();
        System.out.println("Enter email address : ");
        email = input.nextLine();
        user_id = 1;


        return this;
    }

}
