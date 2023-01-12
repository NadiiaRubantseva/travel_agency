package ua.epam.travelagencyms.model.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photo {
    private String path;

    public Photo(String path) {
        this.path = path;
    }
}
