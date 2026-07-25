package problems.smart_home_automation_system;

public class DeviceFactoryClient {

    static void main(String[] args) {

        DeviceFactory roomLightFactory = new LightFactory(LightFactory.LightType.ROOM_LIGHT);
        Device roomLight = roomLightFactory.createDevice();
        demonstrateLight((Light) roomLight, "ROOM LIGHT");

        DeviceFactory smartRGBFactory = new LightFactory(LightFactory.LightType.SMART_RGB_LIGHT);
        Device smartRGBLight = smartRGBFactory.createDevice();
        demonstrateLight((Light) smartRGBLight, "SMART RGB LIGHT");

        DeviceFactory musicFactory = new MusicFactory();
        Device musicDevice = musicFactory.createDevice();
        demonstrateMusic((Music) musicDevice);

        LightFactory roomLightWithColorFactory = new LightFactory(LightFactory.LightType.ROOM_LIGHT);
        Light redRoomLight = roomLightWithColorFactory.createLight("RED");
        demonstrateLight(redRoomLight, "RED ROOM LIGHT");

        LightFactory smartRGBWithHexFactory = new LightFactory(LightFactory.LightType.SMART_RGB_LIGHT);
        Light blueRGBLight = smartRGBWithHexFactory.createLight("#0000FF");
        demonstrateLight(blueRGBLight, "BLUE RGB LIGHT");
    }

    private static void demonstrateLight(Light light, String lightName) {
        System.out.println(">> " + lightName);
        System.out.println("Initial status: " + light.getStatus());

        light.on();
        System.out.println("After on(): " + light.getStatus());

        try {
            light.setBrightness(75);
            System.out.println("Brightness set to: " + light.getBrightness());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Color: " + light.getColor());

        light.off();
        System.out.println("After off(): " + light.getStatus());

        try {
            light.setBrightness(50);
        } catch (Exception e) {
            System.out.println("Error when turning off: " + e.getMessage());
        }
    }

    private static void demonstrateMusic(Music music) {
        System.out.println(">> MUSIC DEVICE");
        System.out.println("Initial status: " + music.getStatus());

        music.on();
        System.out.println("After on(): " + music.getStatus());

        try {
            music.setVolume(80);
            System.out.println("Volume set to: " + music.getVolume());

            music.playSong("Bohemian Rhapsody");
            System.out.println("Now playing: " + music.getCurrentSong());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        music.off();
        System.out.println("After off(): " + music.getStatus());

        try {
            music.playSong("Another Song");
        } catch (Exception e) {
            System.out.println("Error when turning off: " + e.getMessage());
        }
    }
}

