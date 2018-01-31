/*
 * Child.java
 *
 * Created on June 7, 2009, 10:15 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package InformationOfChild;

/**
 *
 * @author Tuong Huy
 */
public class Child {
    private String childCode="";            //save the child code
    private String groupageCode="";         //save the group age code
    private String lastName="";             //lastname of the child
    private String firstName="";            //firstname of the child
    private String middleName="";           //middlename of the child
    private String dateofBirth="";          //date of birth of the child
    private String currentMedication="";    //medication that the child using
    private String passIllness="";          //passIllness of the child
    private String doctorName="";           //doctor name for the child
    private String parentName="";           //parent name of the child
    private String parentworkNumber="";
    private String parentworkMobile="";
    private String parentemailAddress="";
    private String nextofkinContact="";
    private String noteaboutChild="";
    private String registrationDate="";     //the date that the child was add into system
    private String dateReceived="";         //the date that the child was received
    private String nannyName="";            //save the nanny name
    private String nannycode="";
    /** Creates a new instance of Child */
    public Child() {
    }
    //set and get the child code
    public void setChildCode(String _childCode){
        this.childCode = _childCode;        
    }
    public String getChildCode(){
        return this.childCode;
        //Integer.valueOf(this.childCode).intValue();
    }
    
    //set and get the group age code
    public void setGroupAgeCode(String _groupageCode){
        this.groupageCode=_groupageCode;
    }
    public String getGroupAgeCode(){
        return this.groupageCode;
    }
    
    //set and get Last name of the child
    public void setLastName(String _lastName){
        this.lastName=_lastName.trim().replaceAll("'", "&#39;");
    }
    public String getLastName(){
        return this.lastName.replaceAll("&#39;", "'");
    }
    
    //set and get Firstname of the child
    public void setFirstName(String _firstName){
        this.firstName = _firstName.trim().replaceAll("'", "&#39;");
    }
    public String getFirstName(){
        return this.firstName;
    }
    
    //set and get Middlname of the child
    public void setMiddleName(String _middleName){
        this.middleName=_middleName.trim().replaceAll("'", "&#39;");
    }
    public String getMiddleName(){
        return this.middleName;
    }
    
    //set and get Date of birth of the child
    public void setDateOfBirth(String _dateOfBirh){
        this.dateofBirth=_dateOfBirh.trim().replaceAll("'", "&#39;");
    }
    public String getDateOfBirth(){
        return dateofBirth.replaceAll("&#39;", "'");
    }
    
    //set and get Current Medication of the child
    public void setCurrentMedication(String _currentMedication){
        this.currentMedication=_currentMedication.trim().replaceAll("'", "&#39;");        
    }
    
    public String getCurrentMedication(){
        return this.currentMedication.replaceAll("&#39;", "'");
    }
    
    //set and get PassIllness of the child
    public void setPassIllness(String _passIllness){
        this.passIllness=_passIllness.trim().replaceAll("'", "&#39;");
    }
    public String getPassIllness(){
        return this.passIllness.replaceAll("&#39;", "'");
    }
    
    //set and get Doctorname of the child
    public void setDoctorName(String _doctorname){
        this.doctorName=_doctorname.trim().replaceAll("'", "&#39;");        
    }
    public String getDoctorName(){
        return this.doctorName.replaceAll("&#39;", "'");
    }
    
    //set and get parent name of the child
    public void setParentName(String _parentName){
        this.parentName=_parentName.trim().replaceAll("'", "&#39;");
    }    
    public String getParentName(){
        return this.parentName.replaceAll("&#39;", "'");
    }
    
    //set and get parent work number of the child
    public void setParentWorkNumber(String _parentWorkNumber){
        this.parentworkNumber=_parentWorkNumber.trim().replaceAll("'", "&#39;");
    }    
    public String getParentWorkNumber(){
        return this.parentworkNumber;
        //Integer.parseInt(this.parentworkNumber, 16);
    }
    
    //set and get parent work mobile of the child
    public void setParentMobileNumber(String _parentMobileNumber){
        this.parentworkMobile=_parentMobileNumber.trim().replaceAll("'", "&#39;");
    }    
    public String getParentMobileNumber(){
        return this.parentworkMobile;
    }
    
    //set and get Parent email of the child
    public void setParentEmailAddress(String _parentEmail){
        this.parentemailAddress=_parentEmail.trim().replaceAll("'", "&#39;");
    }
    public String getParentEmailAddress(){
        return this.parentemailAddress.replaceAll("&#39;", "'");
    }
    
    //set and get next of kin contact
    public void setnextOfKinContact(String _nextOfKinContact){
        this.nextofkinContact=_nextOfKinContact.trim().replaceAll("'", "&#39;");
    }
    public String getnextOfKinContact(){
        return nextofkinContact.replaceAll("&#39;", "'");
    }
    
    //set and get Note about the child
    public void setnoteAboutChild(String _noteAboutChild){
        this.noteaboutChild=_noteAboutChild.trim().replaceAll("'", "&#39;");
    }
    public String getnoteAboutChild(){
        return this.noteaboutChild.replaceAll("&#39;", "'");
    }
    
    //set and get Date registration of the child
    public void setDateRegistration(String _dateRegistration){
        this.registrationDate=_dateRegistration.trim().replaceAll("'", "&#39;");
    }
    public String getDateRegistration(){
        return this.registrationDate.replaceAll("&#39;", "'");
    }
    
    //set and get Date received of the child
    public void setDateReceived(String _dateReceived){
        this.dateReceived=_dateReceived.trim().replaceAll("'", "&#39;");
    }
    public String getDateReceived(){
        return this.dateReceived.replaceAll("&#39;", "'");
    }
    
    public void setNannyName(String Name){
        this.nannyName = Name.trim().replaceAll("'", "&#39;");
    }
    public String getNannyName(){
        return this.nannyName.replaceAll("&#39;", "'");
    }
    
    public void setNannyCode(String _code){
        this.nannycode=_code;
    }
    public String getNannyCode(){
        return this.nannycode;
    }
}
