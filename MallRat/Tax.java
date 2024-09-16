public class Tax {
    /**
     * The name of the tax
     */
    private String name;
    /**
     * The rate of the tax
     */
    private double rate;
    
    public Tax(String name,double rate){
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    
}