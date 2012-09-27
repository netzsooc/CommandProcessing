package sentencetranslator;

/**
 *
 * @author vieyra
 */
public class SentenceSimilarElement {
    private String SubsetSimilar;
    private String ElementSimilar;
    private double similarity;
    
    public SentenceSimilarElement(String SubsetSimilar, String ElementSimilar, double similarity) {
        this.SubsetSimilar = SubsetSimilar;
        this.ElementSimilar = ElementSimilar;
        this.similarity = similarity;
    }

    public String getSubsetSimilar() {
        return SubsetSimilar;
    }

    public String getElementSimilar() {
        return ElementSimilar;
    }

    public double getSimilarity() {
        return similarity;
    }
    
    
    
}
