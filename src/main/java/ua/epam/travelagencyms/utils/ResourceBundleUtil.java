package ua.epam.travelagencyms.utils;

import java.util.ResourceBundle;

public class ResourceBundleUtil {

    public static String getValue(ResourceBundle resourceBundle, String key) {

        return resourceBundle.getString(key);

    }

}
