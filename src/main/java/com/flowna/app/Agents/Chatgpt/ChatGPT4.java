package com.flowna.app.Agents.Chatgpt;

public enum ChatGPT4 {


    ChatGPT_MINI("gpt-4o-mini");




    private final String model;


    ChatGPT4(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }



}
