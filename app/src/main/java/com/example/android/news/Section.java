package com.example.android.news;

import java.io.Serializable;

public class Section implements Serializable {
    private String sectionName;
    private String sectionId;

    public Section(String sectionName, String sectionId) {
        this.sectionName = sectionName;
        this.sectionId = sectionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        if (getSectionName() != null ? !getSectionName().equals(section.getSectionName()) : section.getSectionName() != null)
            return false;
        return getSectionId() != null ? getSectionId().equals(section.getSectionId()) : section.getSectionId() == null;
    }

    @Override
    public int hashCode() {
        int result = getSectionName() != null ? getSectionName().hashCode() : 0;
        result = 31 * result + (getSectionId() != null ? getSectionId().hashCode() : 0);
        return result;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
}
