package data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="formOfEducation")
@XmlEnum
public enum FormOfEducation {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList(){
        String nameList = "";
        for (FormOfEducation form : values()){
            nameList += form.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
