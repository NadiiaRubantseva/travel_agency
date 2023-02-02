package ua.epam.travelagencyms.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStream;

import static ua.epam.travelagencyms.controller.actions.constants.Parameters.IMAGE;

/**
 * {@code ImageEncoder} util class to help encode image
 */
public class ImageEncoder {

    private static final String CODE_TYPE = "data:image/jpeg;base64,";

    /**
     * {@code encode} method to encode image as byte array to string
     * @param image - image as byte array
     * @return encoded image
     */
    public static String encode(byte[] image) {
        byte[] imageBase64 = Base64.encodeBase64(image, false);
        String imageAsString = StringUtils.newStringUtf8(imageBase64);
        StringBuilder encodedImage = new StringBuilder(CODE_TYPE);
        encodedImage.append(imageAsString);
        return encodedImage.toString();
    }

    /**
     * {@code getImage} method to decode image from string to byte array
     * @param request - HttpServletRequest for retrieving image part
     * @return decoded image
     */
    public static byte[] getImage(HttpServletRequest request) {
        byte[] image = null;
        try {
            Part part = request.getPart(IMAGE);
            try (InputStream inputStream = part.getInputStream()) {
                image = inputStream.readAllBytes();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}
