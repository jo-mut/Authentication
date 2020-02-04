package com.cs4sample.authentication.models;

import java.util.List;

public class GenderList {
    List<Gender> genders;

    public GenderList(List<Gender> genders) {
        this.genders = genders;
    }

    public List<Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Gender> genders) {
        this.genders = genders;
    }

    public class Masculin extends Gender {

        public Masculin() {
        
        }
    }

    public class Feminin extends Gender {

        public Feminin() {
        }
    }
}