public class Student {
    private String course;
    private String adminNumber;
    private String name;
    private double gpa;
    private Module[] modules;

    public Student(){
        this("", "", "", 0, null);
    }

    public Student(String course, String adminNumber, String name, double gpa, Module[] modules){
        this.course = course;
        this.adminNumber = adminNumber;
        this.name = name;
        this.gpa = gpa;
        this.modules = modules;
    }

    public String getCourse() {
        return course;
    }

    public String getAdminNumber() {
        return adminNumber;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public Module[] getModules() {
        return modules;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setAdminNumber(String adminNumber) {
        this.adminNumber = adminNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setModules(Module[] modules) {
        this.modules = modules;
    }
}
