package util;


import data.StudyGroup;
import exceptions.NoAccessToFileException;
import xml.StudyGroups;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

/**
 * Operates the file for saving/loading collection.
 */
public class FileManager {
    private final String envVariable;

    public FileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    private InputStreamReader getInputStreamReader() throws FileNotFoundException, NoAccessToFileException {
        File file = new File(System.getenv(envVariable));
        if (file.exists() && !file.canRead()) throw new NoAccessToFileException();
        return new InputStreamReader(new FileInputStream(file));
    }

    private BufferedWriter getBufferedWriter() throws IOException, NoAccessToFileException {
        File file = new File(System.getenv(envVariable));
        if (file.exists() && !file.canWrite()) throw new NoAccessToFileException();
        return new BufferedWriter(new FileWriter(new File(System.getenv(envVariable))));
    }

    /**
     * Writes collection to a file.
     * @param collection Collection to write.
     */
    public void writeCollection(LinkedHashSet<StudyGroup> collection) {
        if (System.getenv().get(envVariable) != null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(StudyGroups.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                StudyGroups result = new StudyGroups();
                result.setStudyGroups(collection);
                marshaller.marshal(result, getBufferedWriter());

                Console.println("Коллекция успешна сохранена в файл!");
            } catch (JAXBException | IOException e) {
                Console.printerror("Ошибка сохранения в файл!");
            } catch (NoAccessToFileException e) {
                Console.printerror("Нет доступа к файлу!");
            }
        } else Console.printerror("Системная переменная с загрузочным файлом не найдена!");
    }

    /**
     * Reads collection from a file.
     * @return Readed collection.
     */
    public LinkedHashSet<StudyGroup> readCollection() {
        if (System.getenv().get(envVariable) != null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(StudyGroups.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                unmarshaller.unmarshal(getInputStreamReader());
                StudyGroups studyGroups = (StudyGroups) unmarshaller.unmarshal(new File(System.getenv(envVariable)));
                LinkedHashSet<StudyGroup> collection = studyGroups.getStudyGroups();
                Console.println("Коллекция успешно загружена!");
                return collection;
            } catch (NoSuchElementException exception) {
                Console.printerror("Загрузочный файл пуст!");
            } catch (NullPointerException exception) {
                Console.printerror("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
            } catch (JAXBException e) {
                Console.printerror("Ошибка прочтения XML-файла");
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                Console.printerror("Файл не найден");
            } catch (NoAccessToFileException e) {
                Console.printerror("Нет доступа к файлу!");
            }
        } else Console.printerror("Системная переменная с загрузочным файлом не найдена!");
        Console.println("Проверьте переменную окружения " + envVariable + " и запустите заново.");
        System.exit(0);
        return new LinkedHashSet<>();
    }
}
