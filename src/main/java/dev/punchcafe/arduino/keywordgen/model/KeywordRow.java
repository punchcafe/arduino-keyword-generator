package dev.punchcafe.arduino.keywordgen.model;

public class KeywordRow {
    private final KeyWordType keywordType;
    private final String name;

    public KeywordRow(final KeyWordType keywordType, final String name){
        this.keywordType = keywordType;
        this.name = name;
    }

    public KeyWordType getKeywordType(){
        return this.keywordType;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return String.format("Name: %s, Keyword Type: %s", this.name, this.keywordType);
    }
}
