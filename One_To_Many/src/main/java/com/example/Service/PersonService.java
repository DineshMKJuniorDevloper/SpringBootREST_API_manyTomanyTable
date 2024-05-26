package com.example.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.DTO.AddressDTO;
import com.example.DTO.PersonDTO;
import com.example.Entity.Address;
import com.example.Entity.Person;
import com.example.Exception.ResourceNotFoundException;
import com.example.Repo.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
    private PersonRepository personRepository;

	//post
	  public PersonDTO createPerson(PersonDTO personDTO) {
	        Person person = convertToEntity(personDTO);
	        Person savedPerson = personRepository.save(person);
	        return convertToDTO(savedPerson);
	    }
//postmapping submethod
	    private Person convertToEntity(PersonDTO personDTO) {
	        Person person = new Person();
	        person.setName(personDTO.getName());
	        person.setAge(personDTO.getAge());

	        List<Address> addresses = personDTO.getAddresses().stream()
	            .map(addressDTO -> {
	                Address address = new Address();
	                address.setStreet(addressDTO.getStreet());
	                address.setCity(addressDTO.getCity());
	                address.setPerson(person); // Link address to the person
	                return address;
	            })
	            .collect(Collectors.toList());

	        person.setAddresses(addresses);
	        return person;
	    }
	
	
	    
	    
	
	
	//get mapping by id
    public PersonDTO getPerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found"));
        return convertToDTO(person);
    }
    
    // getmapping by id
    private PersonDTO convertToDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setAge(person.getAge());

        List<AddressDTO> addressDTOs = person.getAddresses().stream()
            .map(address -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setStreet(address.getStreet());
                addressDTO.setCity(address.getCity());
                return addressDTO;
            })
            .collect(Collectors.toList());

        personDTO.setAddresses(addressDTOs);
        return personDTO;
    }
    
    
    // getmapping find All
    public List<PersonDTO> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return persons.stream().map(this::convertToALLDTO).collect(Collectors.toList());
    }
    
    private PersonDTO convertToALLDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setAge(person.getAge());

        List<AddressDTO> addressDTOs = person.getAddresses().stream()
            .map(address -> {
                AddressDTO addressDTO = new AddressDTO();
                addressDTO.setStreet(address.getStreet());
                addressDTO.setCity(address.getCity());
                return addressDTO;
            })
            .collect(Collectors.toList());

        personDTO.setAddresses(addressDTOs);
        return personDTO;
    }
	
    
    //deletemapping
    public void deletePerson(Long id) {
        if (personRepository.existsById(id)) 
        {
           personRepository.deleteById(id);
        } else 
        {
            throw new ResourceNotFoundException("Person not found with id: " + id);
        }
        
       
    }
    

}
