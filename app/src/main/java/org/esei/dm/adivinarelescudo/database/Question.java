package org.esei.dm.adivinarelescudo.database;

public class Question {
    private Integer id; // Corresponde al campo _id
    private Integer correct; // Índice de la opción correcta
    private String photo; // URL o nombre del archivo de la foto
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String dificult;

    // Constructor vacío
    public Question() {
    }

    public Question(Integer id, Integer correct, String photo, String option1, String option2, String option3, String option4,String dificult) {
        this.id = id;
        this.correct = correct;
        this.photo = photo;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.dificult = dificult;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getDificult() {
        return dificult;
    }

    public void setDificult(String dificult) {
        this.dificult = dificult;
    }
}
