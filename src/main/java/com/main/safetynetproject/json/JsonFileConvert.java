package com.main.safetynetproject.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.main.safetynetproject.object.FireStations;
import com.main.safetynetproject.object.MedicalRecords;
import com.main.safetynetproject.object.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JsonFileConvert {
    private final Resource dataFile;
    private List<MedicalRecords> medicalRecordsList;
    private List<FireStations>fireStationsList;
    private List<Person> personList;

    public List<FireStations> getFireStationsList() {
        return fireStationsList;
    }
    public List<Person> getPersonList() {
        return personList;
    }

    /**
     * Creation des deux class temporaire pour conversion des types d'objet.
     **/
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

    /**
     * Importation du fichier json.
     * @param inputFile
     */
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

    /**
     * Recuperation des donnees contenu dans le fichier json et conversion en objet java Firestation.
     * @param anyFirestation
     * @return
     */
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

    /**
     * Recuperation des donnees contenu dans le fichier json et conversion en objet java MedicalRecord.
     * @param anyMedicalRecords
     * @return
     */
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

    /**
     * Recuperation des donnees contenu dans le fichier json et conversion en objet java Person.
     * @param anyPersons
     * @param anyMedicalRecords
     * @return
     */
    private List<Person> createPersonListWithMedicalRecords(List<Any> anyPersons, List<Any> anyMedicalRecords){
        List<ConvertMedicalRecords> convertMedicalRecordsList = convertAnyToMedicalRecordList(anyMedicalRecords);
        medicalRecordsList = createMedicalRecordList(anyMedicalRecords);
        personList = anyPersons.stream().map(any ->
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
        personList
                .forEach(person -> {
                    String resultBirthDate = convertMedicalRecordsList.stream()
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversFirstName.equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversLastName.equals(person.getLastName()))
                            .map(convertMedicalRecords -> convertMedicalRecords.conversBirthdate)
                            .collect(Collectors.joining());
                    person.setBirthDate(resultBirthDate);

                    List<List<String>> resultMedications = convertMedicalRecordsList.stream()
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversFirstName.equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversLastName.equals(person.getLastName()))
                            .map(convertMedicalRecords -> convertMedicalRecords.conversMedications)
                            .collect(Collectors.toList());
                    List<String> resultListMedications = resultMedications.stream().flatMap(List::stream).collect(Collectors.toList());

                    List<List<String>> resultAllergies = convertMedicalRecordsList.stream()
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversFirstName.equals(person.getFirstName()))
                            .filter(convertMedicalRecords -> convertMedicalRecords.conversLastName.equals(person.getLastName()))
                            .map(convertMedicalRecords -> convertMedicalRecords.conversAllergies)
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
        return personList;
    }

    /**
     * Conversion de l'objet any en list de any et en list d'objet java.
     * @param jsonData
     */
    private void convertAnyToJsonObject(Any jsonData){
        List<Any> anyMedicalRecords = jsonData.get("medicalrecords").asList();
        medicalRecordsList = createMedicalRecordList(anyMedicalRecords);

        List<Any> anyPersons = jsonData.get("persons").asList();
        personList = createPersonListWithMedicalRecords(anyPersons, anyMedicalRecords);

        List<Any> anyFirestation = jsonData.get("firestations").asList();
        fireStationsList = createFireStationList(anyFirestation);
    }

    /**
     * Conversion du fichier json en objet java.
     */
    public void extractJsonData(){
        Any jsonData = loadJsonData();
        if (jsonData != null){
            convertAnyToJsonObject(jsonData);
            log.info("JsonData load !");
        } else {
            log.error("JsonData null !");
        }
    }
}
