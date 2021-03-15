package util;

import java.time.ZonedDateTime;
import java.util.*;

import data.Person;
import data.StudyGroup;

public class CollectionManager {
    private LinkedHashSet<StudyGroup> myCollection = new LinkedHashSet<>();
    private ZonedDateTime lastInitTime;
    private ZonedDateTime lastSaveTime;
    private FileManager fileManager;
    private int lastId;

    public CollectionManager(FileManager fileManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;

        loadCollection();
    }

    /**
     * @return The collection itself.
     */
    public LinkedHashSet<StudyGroup> getCollection() {
        return myCollection;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public ZonedDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public ZonedDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return myCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return myCollection.size();
    }

/*
    /**
     * @return The first element of the collection or null if collection is empty.
     *
    public StudyGroup getFirst() {
        if (myCollection.isEmpty()) return null;
        return myCollection.first();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     *
    public StudyGroup getLast() {
        if (myCollection.isEmpty()) return null;
        return myCollection.last();
    }
*/
    /**
     * Remove marines greater than the selected one.
     * @param studyGroup A studyGroup to compare with.
     */
    public void removeGreater(StudyGroup studyGroup) {
        myCollection.removeIf(studyGroup1 -> studyGroup1.compareTo(studyGroup) > 0);
    }

    public void removeLower(StudyGroup studyGroup) {
        myCollection.removeIf(studyGroup1-> studyGroup1.compareTo(studyGroup) < 0);
    }

    /**
     * @param id ID of the studyGroup.
     * @return A studyGroup by his ID or null if studyGroup isn't found.
     */
    public StudyGroup getById(Integer id) {
        for (StudyGroup studyGroup : myCollection) {
            if (studyGroup.getId() == id) return studyGroup;
        }
        return null;
    }

    /**
     * @param studyGroupToFind A studyGroup who's value will be found.
     * @return A studyGroup by his value or null if studyGroup isn't found.
     */
    public StudyGroup getByValue(StudyGroup studyGroupToFind) {
        for (StudyGroup studyGroup : myCollection) {
            if (studyGroup.similar(studyGroupToFind)) return studyGroup;
        }
        return null;
    }

    public StudyGroup getByFormOfEducation(String form) {
        for (StudyGroup studyGroup : myCollection) {
            if (studyGroup.getFormOfEducation().name() == form) return studyGroup;
        }
        return null;
    }

    /**
     * Adds a new studyGroup to collection.
     * @param studyGroup A studyGroup to add.
     */
    public void addToCollection(StudyGroup studyGroup) {
        myCollection.add(studyGroup);
    }

    /**
     * Removes a new studyGroup to collection.
     * @param studyGroup A studyGroup to remove.
     */
    public void removeFromCollection(StudyGroup studyGroup) {
        myCollection.remove(studyGroup);
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        myCollection.clear();
    }

    /**
     * Generates next ID. It will be (the bigger one + 1).
     * @return Next ID.
     */
    public Integer generateNextId() {
        if (myCollection.isEmpty()) return lastId;
        return lastId + 1;
    }

    /**
     * Saves the collection to file.
     */
    public void saveCollection() {
        fileManager.writeCollection(myCollection);
        lastSaveTime = ZonedDateTime.now();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        myCollection = fileManager.readCollection();
        lastInitTime = ZonedDateTime.now();
    }

    public ArrayList<Long> sortedByShouldBeExpelled(){
        ArrayList<Long> arrayList = new ArrayList<Long>();
        for(StudyGroup sg : myCollection){
            arrayList.add(sg.getShouldBeExpelled());
        }
        Collections.sort(arrayList);
        Collections.reverse(arrayList);
        return arrayList;
    }

    public Set<Person> uniqueGroupAdmin() {
        Set<Person> set = new HashSet<Person>();
        for(StudyGroup sg : myCollection){
            set.add(sg.getGroupAdmin());
        }
        return set;
    }

    @Override
    public String toString() {
        if (myCollection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (StudyGroup studyGroup : myCollection) {
            info.append(studyGroup);
            info.append("\n\n");
        }
        return info.toString();
    }
}
