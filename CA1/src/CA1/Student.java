package CA1;

public class Student {
    private String course;
    private String adminNumber;
    private String name;
    private double gpa;
    private Module[] modules;

    public Student() {
        this("", "", "", new Module[]{});
    }

    public Student(String course, String adminNumber, String name, Module[] modules) {
        this.course = course;
        this.adminNumber = adminNumber;
        this.name = name;
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

    //    Proper calculation of GPA.
    private void calculateGpa() {
        //        Get all the modules
        int totalCreditUnits = 0;
        double numerator = 0;

        for (Module module : modules) {
            double studentMarks = module.getMarks();
            if (studentMarks >= 80) {
                numerator += (4 * module.getCreditUnit());
            } else if (studentMarks >= 70) {
                numerator += (3 * module.getCreditUnit());
            } else if (studentMarks >= 60) {
                numerator += (2 * module.getCreditUnit());
            } else if (studentMarks >= 50) {
                numerator += (module.getCreditUnit());
            }

            totalCreditUnits += module.getCreditUnit();
        }

        double gpa = numerator / totalCreditUnits;
        setGpa(gpa);
    }

    public double getGpa() {
        if(modules.length > 0){
            calculateGpa();
        }

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
