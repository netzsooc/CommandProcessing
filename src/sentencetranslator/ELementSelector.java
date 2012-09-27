/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sentencetranslator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author vieyra
 */
public class ELementSelector {

    private List<String> elements = new ArrayList<String>();
    private SentenceSimilarElement selectedElement;
    private String selectedSentence;

    public ELementSelector(String[] speechSentence) {
        addAllElements();
        this.selectedElement = getSelectedSpeechSentence(speechSentence);
    }

    public SentenceSimilarElement getSelectedElement() {
        return selectedElement;
    }

    public String getSelectedSentence() {
        return selectedSentence;
    }
    
    private void addAllElements() {
        String[] e = {
            "foco",
            "foco de la cocina",
            "foco de la sala",
            "foco dos de la cocina",
            "foco de la habitacion",
            "foco dos de la cocina",
            "foco de la habitacion de samuel",
            "foco del baño",
            "foco dos de la cocina"
        };
        for (String element : e)
            elements.add(element.replace(" ", ""));
//        elements = Arrays.asList(e);
    }

    private SentenceSimilarElement getSelectedSpeechSentence(String[] speechSentence) {
        double greaterSimilarity = 0.0;
        SentenceSimilarElement ElementsSentence = null;
        for(String sentence : speechSentence){
            SentenceSimilarElement SSE = getElementbySetence(sentence);
            if(SSE.getSimilarity() > greaterSimilarity){
                greaterSimilarity = SSE.getSimilarity();
                ElementsSentence = SSE;
                selectedSentence = sentence;
            }
        }
        return ElementsSentence;
    }

    private SentenceSimilarElement getElementbySetence(String Sentence){
        String moreSimilar = null;
        String elementSelected = null;
        double JWDistance = 0.0;
        double jwd;
        for(String subset :getSubsetString(Sentence) ){
            for(String element : elements){
                if((jwd = Utils.string.JaroWinklerDistanceM(subset, element, elements.size())) > JWDistance){
                    JWDistance = jwd;
                    elementSelected = element;
                    moreSimilar = subset;
                }
            }
        }
        return new SentenceSimilarElement(moreSimilar, elementSelected, JWDistance);
    }
    
    private String[] getSubsetString(String sentence) {
        ArrayList<String> subsets = new ArrayList<String>();
        String[] words = sentence.split(" ");
        for (int i = 0; i < words.length; i++) {
            subsets.add(words[i]);
            String ant = words[i]; 
            for (int j = i + 1; j < words.length; j++) {
                ant = ant +  words[j];
                subsets.add(ant);
            }
        }
        return subsets.toArray(new String[]{});
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] sentences = {
          "enciende foco de la habitacion e samuel",  
          "el 100 del poco de habitacionde samuel",  
          "1.100 de un foco de la habitacion desamuel",  
          "enciende el foco de habitacion de zamoel",  
          "Enciende el foco de habitacion de zanoel",  
          "Enciende focos de habitacion de sauel",  
          "Enciende el focus de habitacion de samoel"  
        };
        ELementSelector selectedSentence = new ELementSelector(sentences);
        System.out.println(selectedSentence.getSelectedSentence());
        System.out.println(selectedSentence.getSelectedElement().getElementSimilar());
        System.out.println(selectedSentence.getSelectedElement().getSubsetSimilar());
        System.out.println(selectedSentence.getSelectedElement().getSimilarity());
    }
}
