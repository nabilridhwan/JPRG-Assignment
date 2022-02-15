/*
    Class:          DIT/FT/1B/05
    Admin Number:   P2007421
    Name:           Nabil Ridhwanshah Bin Rosli
 */


public class InternationalStudent extends Student{
    private boolean requirePass;

    public InternationalStudent(String course, String adminNumber, String name, Module[] modules, boolean requirePass) {
        super(course, adminNumber, name, modules);
        this.requirePass = requirePass;
    }

    @Override
    public String print(){
        if(requirePass){
            return "I am an International Student. I need a pass.";
        }else{
            return "I am an International Student. I do not need a pass";
        }
    }

}
