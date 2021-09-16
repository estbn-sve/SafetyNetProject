package com.main.safetynetproject.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.main.safetynetproject.object.FireStations;
import com.main.safetynetproject.object.MedicalRecords;
import com.main.safetynetproject.object.Person;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonFileConvert {
    private Resource dataFile;

    private List<MedicalRecords> medicalRecordsList;
    private List<FireStations>fireStationsList;
    private List<Person>personsList;

    private class TempsFireStation {
        public int tempsStation;
        public String tempsAddress;
    }

    private class ConvertMedicalRecords {
        public String conversFirstName;
        public String conversLastName;
        public String conversBirthdate;
        public List<String> conversMedications;
        public List<String> conversAllergies;
    }

    public JsonFileConvert(Resource inputFile) {
        dataFile = inputFile;
    }

    private Any loadJsonData(){
        try{
            File dataJsonFileLocal = dataFile.getFile();
            byte[] data = Files.readAllBytes(dataJsonFileLocal.toPath());
            return JsonIterator.deserialize(data);
        } catch (IOException e){
            System.err.println("Error : " + e);
            return null;
        }
    }

    private List<TempsFireStation> convertAnyToTempsFireStationList(List<Any> anyFirestation){
        return anyFirestation.stream().map(any -> {
            TempsFireStation tps = new TempsFireStation();
            tps.tempsStation = any.toInt("station");
            tps.tempsAddress = any.toString("address");
            return tps;
        }).collect(Collectors.toList());
    }
    private List<FireStations> createFireStationList(List<Any> anyFirestation){
        List<TempsFireStation> tempsFireStationList = convertAnyToTempsFireStationList(anyFirestation);
        List<FireStations> fireStationsList = tempsFireStationList.stream()
                .map(tempsFireStation -> FireStations.builder()
                        .station(tempsFireStation.tempsStation)
                        .address(new ArrayList<>())
                        .build())
                .distinct()
                .collect(Collectors.toList());
        fireStationsList
                .forEach(fireStations -> {
                    List<String> resultTempsFireStation = tempsFireStationList.stream()
                            .filter(tempsFireStation -> tempsFireStation.tempsStation == fireStations.getStation())
                            .map(tempsFireStation -> tempsFireStation.tempsAddress)
                            .collect(Collectors.toList());
                    fireStations.setAddress(resultTempsFireStation);
                });
        return fireStationsList;
    }

    private List<ConvertMedicalRecords> convertAnyToMedicalRecordList (List<Any> anyMedicalRecords){
        return anyMedicalRecords.stream().map(any -> {
            ConvertMedicalRecords cmr = new ConvertMedicalRecords();
            cmr.conversFirstName = any.toString("firstName");
            cmr.conversLastName = any.toString("lastName");
            cmr.conversBirthdate = any.toString("birthdate");
            cmr.conversAllergies = any.get("allergies").asList().stream().map(Any::toString).collect(Collectors.toList());
            cmr.conversMedications = any.get("medications").asList().stream().map(Any::toString).collect(Collectors.toList());
            return cmr;
        }).collect(Collectors.toList());
    }
    private List<MedicalRecords> createMedicalRecordList (List<Any> anyMedicalRecords){
        List<ConvertMedicalRecords> convertMedicalRecordsList = convertAnyToMedicalRecordList(anyMedicalRecords);
        return convertMedicalRecordsList.stream()
                .map(convertMedicalRecords -> MedicalRecords.builder()
                        .allergies(convertMedicalRecords.conversAllergies)
                        .medications(convertMedicalRecords.conversMedications)
                        .build()).collect(Collectors.toList());
    }

    private List<Person> createPersonListWithMedicalRecords(List<Any> anyPersons, List<Any> anyMedicalRecords){
        List<ConvertMedicalRecords> convertMedicalRecordsList = convertAnyToMedicalRecordList(anyMedicalRecords);
        medicalRecordsList = createMedicalRecordList(anyMedicalRecords);
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
        personsList
                .forEach(person -> {
                    String resultBirthDate = convertMedicalRecordsList.stream()
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversFirstName.equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversLastName.equals(person.getLastName()))
                            .map(convertMedicalRecords -> convertMedicalRecords.conversBirthdate)
                            /*.filter(convertMedicalRecords -> convertMedicalRecords.getConversFirstName().equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.getConversLastName().equals(person.getLastName()))
                            .map(ConvertMedicalRecords::getConversBirthdate)*/
                            .collect(Collectors.joining());
                    person.setBirthDate(resultBirthDate);

                    List<List<String>> resultMedications = convertMedicalRecordsList.stream()
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversFirstName.equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversLastName.equals(person.getLastName()))
                            .map(convertMedicalRecords -> convertMedicalRecords.conversMedications)
                            /*.filter(convertMedicalRecords -> convertMedicalRecords.getConversFirstName().equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.getConversLastName().equals(person.getLastName()))
                            .map(ConvertMedicalRecords::getConversMedications)*/
                            .collect(Collectors.toList());
                    List<String> resultListMedications = resultMedications.stream().flatMap(List::stream).collect(Collectors.toList());

                    List<List<String>> resultAllergies = convertMedicalRecordsList.stream()
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversFirstName.equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversLastName.equals(person.getLastName()))
                            .map(convertMedicalRecords -> convertMedicalRecords.conversAllergies)
                            /*.filter(convertMedicalRecords -> convertMedicalRecords.getConversFirstName().equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.getConversLastName().equals(person.getLastName()))
                            .map(ConvertMedicalRecords::getConversAllergies)*/
                            .collect(Collectors.toList());
                    List<String> resultListAllergies = resultAllergies.stream().flatMap(List::stream).collect(Collectors.toList());

                    List<MedicalRecords> resultMedicalRecords = medicalRecordsList.stream().map(medicalRecords ->
                                    MedicalRecords.builder()
                                            .medications(resultListMedications)
                                            .allergies(resultListAllergies)
                                            .build())
                            .distinct()
                            .collect(Collectors.toList());
                    person.setMedicalRecords(resultMedicalRecords);
                });
        return personsList;
    }

    private void convertAnyToJsonObject(Any jsonData){
        List<Any> anyMedicalRecords = jsonData.get("medicalrecords").asList();
        medicalRecordsList = createMedicalRecordList(anyMedicalRecords);

        List<Any> anyPersons = jsonData.get("persons").asList();
        personsList = createPersonListWithMedicalRecords(anyPersons, anyMedicalRecords);

        List<Any> anyFirestation = jsonData.get("firestations").asList();
        fireStationsList = createFireStationList(anyFirestation);

        System.out.println(medicalRecordsList);
        System.out.println(personsList);
        System.out.println(fireStationsList);
        System.out.println();
    }

    public void extractJsonData(){
        Any jsonData = loadJsonData();
        if (jsonData != null){
            convertAnyToJsonObject(jsonData);
            System.out.println("JsonData load !");
        } else {
            System.err.println("JsonData null !");
        }
    }
}
