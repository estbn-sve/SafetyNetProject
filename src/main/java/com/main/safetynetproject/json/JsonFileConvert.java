package com.main.safetynetproject.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.main.safetynetproject.object.FireStations;
import com.main.safetynetproject.object.Person;
import lombok.Builder;
import lombok.Data;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
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
class TempsMedicalRecords {
    String tempsFirstName;
    String tempsLastName;
    String tempsBirthdate;
    List<String> tempsMedications;
    List<String> tempsAllergies;
}

@Builder
@Data
class TempsAnyMedicalrecords {
    Any tempsAnyFirstName;
    Any tempsAnyLastName;
    Any tempsAnyBirthdate;
    List<Any> tempsAnyMedications;
    List<Any> tempsAnyAllergies;
}

//TODO Regarder avec Youssef pourquoi GIT n'est pas pris en compte.
public class JsonFileConvert {
    public String pathFile = "J:/IdeaProjects/SafetyNetProject/src/main/resources/JsonSource.json";
    private List<Any> anyPersons;
    private List<Any> anyFirestation;
    private List<Any> anyMedicalrecords;
    List<TempsAnyMedicalrecords>tempsAnyMedicalrecordsList;
    private Any jsonFile;

    private boolean CreateAnyObject(){

        try {
            File dataJsonFileLocal = new File(pathFile);
            byte[] data = Files.readAllBytes(dataJsonFileLocal.toPath());
            jsonFile = JsonIterator.deserialize(data);
        } catch (IOException e) {
            System.err.println("Error in CreateAnyObject on JsonIterator !");
            System.err.println("Error : " + e);
            return false;
        }
        try {
            anyPersons = jsonFile.get("persons").asList();
        } catch (Exception e) {
            System.err.println("Error in CreateAnyObject on anyPersons object !");
            System.err.println("Error : " + e);
            return false;
        }
        try {
            anyMedicalrecords = jsonFile.get("medicalrecords").asList();
            tempsAnyMedicalrecordsList = anyMedicalrecords.stream().map(any ->
                    TempsAnyMedicalrecords.builder()
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
        try {
            anyFirestation = jsonFile.get("firestations").asList();
        } catch (Exception e) {
            System.err.println("Error in CreateAnyObject on anyFirestation object !");
            System.err.println("Error : " + e);
            return false;
        }
        return true;
    }
    private void CreateJavaObject() {
        try {
            List<TempsFireStation>tempsFireStationList = anyFirestation.stream().map(any ->
                    TempsFireStation.builder()
                            .tempsStation(any.toInt("station"))
                            .tempsAddress(any.toString("address"))
                            .build()).collect(Collectors.toList());
            //System.out.println(tempsFireStationList);
            try {
                //TODO incorporer les addresse dans une liste d'address pour chaque station. (lambda)
                List<FireStations>fireStationsList = tempsFireStationList.stream().map(tempsFireStation ->
                        FireStations.builder()
                                .station(tempsFireStation.getTempsStation())
                                .address(new ArrayList<>())
                                .build()).distinct().collect(Collectors.toList());

                tempsFireStationList.stream().forEach(tempsFireStation ->{
                    boolean result = fireStationsList.stream().allMatch(fireStations -> fireStations.equals("Station"));
                        }
                );
                System.out.println(fireStationsList);
                // filter(fireStations ->
                //                        !"address".equals(tempsFireStationList.equals("tempAddress"))).
                //System.out.println(fireStationsList);
                System.out.println("1");
            } catch (Exception e){
                System.err.println("Error in CreateJavaObject on FireStation object !");
                System.err.println("Error : " + e);
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println("Error in CreateJavaObject on TempFireStation object !");
            System.err.println("Error : " + e);
            System.exit(0);
        }
        try {
            //TODO Réussir à crée en String une liste consultable de medication et d'allergies
            //TODO Puis crée medicalRecordsList avec uniquement les medication et allergies
            List<TempsMedicalRecords>tempsMedicalRecordsList = tempsAnyMedicalrecordsList.stream().map(tempsAnyMedicalrecords ->
                    TempsMedicalRecords.builder()
                            .tempsFirstName(tempsAnyMedicalrecords.getTempsAnyFirstName().toString())
                            .tempsLastName(tempsAnyMedicalrecords.getTempsAnyLastName().toString())
                            .tempsBirthdate(tempsAnyMedicalrecords.getTempsAnyBirthdate().toString())
                            //.tempsMedications(tempsAnyMedicalrecords.toString("medication"))
                            //.tempsAllergies(Collections.singletonList(tempsAnyMedicalrecords.getTempsAnyAllergies().toString()))
                            .build()).collect(Collectors.toList());
            System.out.println(anyMedicalrecords);
            System.out.println(tempsMedicalRecordsList);
            System.out.println("3");
        }catch (Exception e) {
            System.err.println("Error in CreateJavaObject on TempMedicalRecords object !");
            System.err.println("Error : " + e);
            System.exit(0);
        }
        try {
            //TODO Ajouter les information contenue dans medicalRecordsList
            List<Person>personsList = anyPersons.stream().map(any ->
                    Person.builder()
                            .firstName(any.toString("firstName"))
                            .lastName(any.toString("lastName"))
                            .address(any.toString("address"))
                            .city(any.toString("city"))
                            .zip(any.toString("zip"))
                            .phone(any.toString("phone"))
                            .email(any.toString("email"))
                            .build()).collect(Collectors.toList());
            //System.out.println(personsList);
        } catch (Exception e){
            System.err.println("Error in CreateJavaObject on Person object !");
            System.err.println("Error : " + e);
            System.exit(0);
        }
    }
    public void ExecuteJsonFileConvert(){
        JsonFileConvert startJsonFileConvert = new JsonFileConvert();
        if (startJsonFileConvert.CreateAnyObject()) {
            startJsonFileConvert.CreateJavaObject();
        }
    }
}
