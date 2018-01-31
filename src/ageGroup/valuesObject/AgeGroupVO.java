package ageGroup.valuesObject;

/**
 * AgeGroup class is used to store the details of age group
 * @author DThanh
 */
public class AgeGroupVO {

    /**
     * Creates a new instance of AgeGroupVO
     * @param ageCode to pass the code age group
     * @param childAge to pass the child age group
     * @param charges to pass the charges as per the group
     */
    public AgeGroupVO(int ageCode, String childAge, Double charges) {
        this.ageCode = ageCode;
        this.childAge = childAge;
        this.charges = charges;
    }

    /**
     * Creates a new instance of AgeGroupVO
     * @param childAge to pass the child age group
     * @param charges to pass the charges as per the group
     */
    public AgeGroupVO(String childAge, Double charges) {
        this.childAge = childAge;
        this.charges = charges;
    }

    public int getAgeCode() {
        return ageCode;
    }

    public void setAgeCode(int ageCode) {
        this.ageCode = ageCode;
    }

    public Double getCharges() {
        return charges;
    }

    public void setCharges(Double charges) {
        this.charges = charges;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }
    /**
     * Integer variable to store the age group code
     */
    private int ageCode;
    /**
     * String variable to store the child age group
     */
    private String childAge;
    /**
     * Double variable to store the charges as per the group
     */
    private Double charges;
}
