package de.unistuttgart.iste.pe2.Assignment;

import java.util.List;

public class LetterWithIDs {
    private String letter;
    private List<Integer> ids;

    public LetterWithIDs(String letter, List<Integer> ids) {
        this.letter = letter;
        this.ids = ids;
    }

    public String getLetter() {
        return letter;
    }
    public void setLetter(String letter) {
        this.letter = letter;
    }
    public List<Integer> getIds() {
        return ids;
    }
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }


}
