package com.main.safetynetproject.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.main.safetynetproject.object.FireStations;
import com.main.safetynetproject.object.MedicalRecords;
import com.main.safetynetproject.object.Person;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
class TempsFireStation {
    int tempsStation;
    String tempsAddress;
}

@Builder
@Data
class ConversMedicalRecords {
    String conversFirstName;
    String conversLastName;
    String conversBirthdate;
    List<String> conversMedications;
    List<String> conversAllergies;
}

@Builder
@Data
class TempsMedicalRecords {
    List<String>tempsMedications;
    List<String>tempsAllergies;
}

@Builder
@Data
class TempsAnyMedicalRecords {
    Any tempsAnyFirstName;
    Any tempsAnyLastName;
    Any tempsAnyBirthdate;
    List<Any> tempsAnyMedications;
    List<Any> tempsAnyAllergies;
}

public class JsonFileConvert{
    //JsonFileConvert startJsonFileConvert = new JsonFileConvert();
    public String pathFile = "J:/IdeaProjects/SafetyNetProject/src/main/resources/JsonSource.json";
    private List<Any> anyPersons;
    private List<Any> anyFirestation;
    private List<Any> anyMedicalrecords;
    private List<TempsAnyMedicalRecords> tempsAnyMedicalRecordsList;
    private Any jsonFile;
    private List<TempsFireStation>tempsFireStationList;
    private List<FireStations>fireStationsList;
    private List<ConversMedicalRecords> conversMedicalRecordsList;
    private List<TempsMedicalRecords> tempsMedicalRecordsList;
    private List<Person>personsList;
    private List<MedicalRecords>medicalRecordsList;

    //TODO parler des bonnes pratiques de nommage de variable!!

    private boolean CreateMedicalTempsAnyObject(){
        try {
            tempsAnyMedicalRecordsList = anyMedicalrecords.stream().map(any ->
                    TempsAnyMedicalRecords.builder()
                            .tempsAnyFirstName(any.get("firstName"))
                            .tempsAnyLastName(any.get("lastName"))
                            .tempsAnyBirthdate(any.get("birthdate"))
                            .tempsAnyMedications(any.get("medications").asList())
                            .tempsAnyAllergies(any.get("allergies").asList())
                            .build()).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in CreateAnyObject on anyMedicalrecords object !");
            System.err.println("Error : " + e);
            return false;
        }
        return true;
    }

    private boolean CreateAnyObject(){
        try {
            File dataJsonFileLocal = new File(pathFile);
            byte[] data = Files.readAllBytes(dataJsonFileLocal.toPath());
            jsonFile = JsonIterator.deserialize(data);
            anyPersons = jsonFile.get("persons").asList();
            anyFirestation = jsonFile.get("firestations").asList();
            anyMedicalrecords = jsonFile.get("medicalrecords").asList();
            return CreateMedicalTempsAnyObject();
        } catch (Exception e) {
            System.err.println("Error in CreateAnyObject on anyFirestation object !");
            System.err.println("Error : " + e);
            System.exit(0);
            return false;
        }
    }

    private boolean CreateTempsListFireStation(){
        try {
            tempsFireStationList = anyFirestation.stream().map(any ->
                    TempsFireStation.builder()
                            .tempsStation(any.toInt("station"))
                            .tempsAddress(any.toString("address"))
                            .build()).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error in CreateJavaObject on TempFireStation object !");
            System.err.println("Error : " + e);
            System.exit(0);
            return false;
        }
        return true;
    }

    private boolean CreateListFireStation(){
        if (CreateTempsListFireStation()){
            fireStationsList = tempsFireStationList.stream()
                    .map(tempsFireStation -> FireStations.builder()
                            .station(tempsFireStation.getTempsStation())
                            .address(new ArrayList<>())
                            .build())
                    .distinct()
                    .collect(Collectors.toList());
            fireStationsList
                    .forEach(fireStations -> {
                        List<String> resultTempsFireStation = tempsFireStationList.stream()
                                .filter(tempsFireStation -> tempsFireStation.getTempsStation() == fireStations.getStation())
                                .map(TempsFireStation::getTempsAddress)
                                .collect(Collectors.toList());
                        fireStations.setAddress(resultTempsFireStation);
                    });
            return true;
        } else  {
            return false;
        }
    }

    private boolean CreateListConversMedicalRecords(){
        try {
           conversMedicalRecordsList = tempsAnyMedicalRecordsList.stream().map(tempsAnyMedicalRecords ->
                    ConversMedicalRecords.builder()
                            .conversFirstName(tempsAnyMedicalRecords.getTempsAnyFirstName().toString())
                            .conversLastName(tempsAnyMedicalRecords.getTempsAnyLastName().toString())
                            .conversBirthdate(tempsAnyMedicalRecords.getTempsAnyBirthdate().toString())
                            .conversMedications(tempsAnyMedicalRecords.getTempsAnyMedications().stream().map(Any::toString).collect(Collectors.toList()))
                            .conversAllergies(tempsAnyMedicalRecords.getTempsAnyAllergies().stream().map(Any::toString).collect(Collectors.toList()))
                            .build())
                   .collect(Collectors.toList()
                   );
            return true;
        }catch (Exception e) {
            System.err.println("Error in CreateJavaObject on TempMedicalRecords object !");
            System.err.println("Error : " + e);
            System.exit(0);
            return false;
        }
    }

    private boolean CreateListMedicalRecords(){
        try {
            tempsMedicalRecordsList = conversMedicalRecordsList.stream().map(conversMedicalRecords ->
                    TempsMedicalRecords.builder()
                            .tempsAllergies(conversMedicalRecords.conversAllergies)
                            .tempsMedications(conversMedicalRecords.conversMedications)
                            .build())
                    .collect(Collectors.toList());
            medicalRecordsList = tempsMedicalRecordsList.stream().map(medicalRecords ->
                    MedicalRecords.builder()
                            .allergies(new ArrayList<>())
                            .medications(new ArrayList<>())
                            .build()
            ).collect(Collectors.toList());
            return true;
        } catch (Exception e){
            System.err.println("Error : "+ e);
            return false;
        }
    }

    private void CreateListPersonAndMedicalRecords(){
        if (CreateListConversMedicalRecords()) {
            if(CreateListMedicalRecords()){
            personsList = anyPersons.stream().map(any ->
                    Person.builder()
                            .firstName(any.toString("firstName"))
                            .lastName(any.toString("lastName"))
                            .address(any.toString("address"))
                            .city(any.toString("city"))
                            .zip(any.toString("zip"))
                            .phone(any.toString("phone"))
                            .email(any.toString("email"))
                            .birthDate(null)
                            .medicalRecords(new ArrayList<>())
                            .build()).collect(Collectors.toList()
            );
            System.out.println("1");
            personsList
                    .forEach(person -> {
                        String resultBirthDate = conversMedicalRecordsList.stream()
                                .filter(conversMedicalRecords -> conversMedicalRecords.getConversFirstName().equals(person.getFirstName()))
                                .filter(conversMedicalRecords -> conversMedicalRecords.getConversLastName().equals(person.getLastName()))
                                .map(ConversMedicalRecords::getConversBirthdate)
                                .collect(Collectors.joining());
                        person.setBirthDate(resultBirthDate);

                        List<List<String>> resultMedications = conversMedicalRecordsList.stream()
                                .filter(conversMedicalRecords -> conversMedicalRecords.getConversFirstName().equals(person.getFirstName()))
                                .filter(conversMedicalRecords -> conversMedicalRecords.getConversLastName().equals(person.getLastName()))
                                .map(ConversMedicalRecords::getConversMedications)
                                .collect(Collectors.toList());
                        List<String> resultListMedications = resultMedications.stream().flatMap(List::stream).collect(Collectors.toList());

                        List<List<String>> resultAllergies = conversMedicalRecordsList.stream()
                                .filter(conversMedicalRecords -> conversMedicalRecords.getConversFirstName().equals(person.getFirstName()))
                                .filter(conversMedicalRecords -> conversMedicalRecords.getConversLastName().equals(person.getLastName()))
                                .map(ConversMedicalRecords::getConversAllergies)
                                .collect(Collectors.toList());
                        List<String> resultListAllergies = resultAllergies.stream().flatMap(List::stream).collect(Collectors.toList());

                        List<MedicalRecords> resultMedicalRecords = medicalRecordsList.stream().map(medicalRecords ->
                                MedicalRecords.builder()
                                        .medications(resultListMedications)
                                        .allergies(resultListAllergies)
                                        .build())
                                //.distinct()
                                .collect(Collectors.toList());

                        person.setMedicalRecords(resultMedicalRecords);

                        System.out.println(personsList);
                        System.out.println(resultMedicalRecords);
                        System.out.println(medicalRecordsList);
                        System.out.println("1");
                    });
            } else {
                System.err.println("Error create CreateListPersonAndMedicalRecords,\nCreateListMedicalRecords it's false !");
                System.exit(0);
            }
        } else {
            System.err.println("Error create CreateListPersonAndMedicalRecords,\nCreateListConversMedicalRecords it's false !");
            System.exit(0);
        }
    }

    private void CreateJavaObject() {
        if (CreateListFireStation()){
            CreateListPersonAndMedicalRecords();
        }
    }
    public void ExecuteJsonFileConvert(){
        if (CreateAnyObject()) {
            CreateJavaObject();
            System.out.println(tempsAnyMedicalRecordsList);
            System.out.println(fireStationsList);
            System.out.println(conversMedicalRecordsList);
            System.out.println(tempsMedicalRecordsList);
            System.out.println(medicalRecordsList);
            System.out.println(personsList);
            System.out.println("1");
        }
    }
}
