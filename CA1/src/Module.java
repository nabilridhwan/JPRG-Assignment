public class Module {
    private String moduleCode;
    private String moduleName;
    private int creditUnit;
    private double marks;

    public Module(String moduleCode, String moduleName, int creditUnit, double marks){
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.creditUnit = creditUnit;
        this.marks = marks;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setCreditUnit(int creditUnit) {
        this.creditUnit = creditUnit;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getCreditUnit() {
        return creditUnit;
    }

    public double getMarks() {
        return marks;
    }
}
