package com.testTaskPoll.dto;

public class OptionCount {

    private	int optionId;
    private	int	counter;
    private String optionValue;
    private int questionId;
    private String questionValue;


    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionValue() {
        return questionValue;
    }

    public void setQuestionValue(String questionValue) {
        this.questionValue = questionValue;
    }
}
