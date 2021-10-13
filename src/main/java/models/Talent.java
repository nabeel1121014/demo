package models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Talent {
    private String title;
    private String name;
    private String overview;
    private List<String> skills;


    /**
     *  Check if this talent applies to certain search key
     *
     * @param key
     * @return true if the one of the fields contains the search key
     */
    public boolean isMatchFilterKey(String key) {
        boolean match = false;

        // Validate the title contains the key
        if (title.contains(key)) {
            System.out.println(String.format("Title for Talent %s contains the search key %s ", name, key));
            match = true;
        }else System.out.println(String.format("Title for Talent %s dose not contains the search key %s ", name, key));

        // Validate the Name contains the key
        if (name.contains(key)) {
            System.out.println(String.format("Name for Talent %s contains the search key %s ", name, key));
            match = true;
        }else System.out.println(String.format("Name for Talent %s dose not contains the search key %s ", name, key));

        // Validate the overview contains the key
        if (overview.contains(key)) {
            System.out.println(String.format("Overview for Talent %s contains the search key %s ", name, key));
            match = true;
        }else System.out.println(String.format("Overview for Talent %s dose not contains the search key %s ", name, key));

        // Validate the skills contains the key
        if (skills.contains(key)) {
            System.out.println(String.format("Skills for Talent %s contains the search key %s ", name, key));
            match = true;
        }else System.out.println(String.format("Skills for Talent %s dose not contains the search key %s ", name, key));

        return match;
    }
}
