package data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="nationality")
@XmlEnum
public enum Country {
    USA,
    FRANCE,
    SOUTH_KOREA,
    NORTH_KOREA,
    JAPAN;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */

    public static String nameList(){
        String nameList = "";
        for (Country country : values()){
            nameList += country.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
