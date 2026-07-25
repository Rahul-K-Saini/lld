package problems.smart_home_automation_system;

public class SmartRGBLight extends AbstractColorableLight {

    public SmartRGBLight(){
        this("#FFFFFF");
    }

    public SmartRGBLight(String hex) {
        super(hex);
    }

}
