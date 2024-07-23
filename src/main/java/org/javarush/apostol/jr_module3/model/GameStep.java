package org.javarush.apostol.jr_module3.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
public class GameStep {
    private String id;
    private String question;
    private Map<String, String> options;
    private String end;
}
