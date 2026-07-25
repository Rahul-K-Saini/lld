package problems.smart_home_automation_system;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SmartHomeController {
  private final Map<String, Device> devices;
  private final Map<String, DeviceFactory> factories;
  private final Map<String, List<Device>> rooms;
  private final Map<String, String> deviceToRoom;

  public SmartHomeController() {
    this.rooms = new ConcurrentHashMap<>();
    this.devices = new ConcurrentHashMap<>();
    this.deviceToRoom = new ConcurrentHashMap<>();
    this.factories = new ConcurrentHashMap<>();
    registerFactories();
  }

  private void registerFactories() {
    factories.put("light.room",
        new LightFactory(LightFactory.LightType.ROOM_LIGHT));
    factories.put("light.rgb",
        new LightFactory(LightFactory.LightType.SMART_RGB_LIGHT));

    factories.put("music", new MusicFactory());

    factories.put("fan", new FanFactory());
  }

  public synchronized void createRoom(String roomName) {
    if (rooms.containsKey(roomName)) {
      return;
    }
    // CopyOnWriteArrayList: room device lists are read far more often
    // (turnOnRoom/turnOffRoom/listDevicesByRoom) than written (createDevice),
    // and it's safe to iterate concurrently with adds.
    List<Device> roomDevices = new CopyOnWriteArrayList<>();
    rooms.put(roomName, roomDevices);
  }

  public synchronized void createDevice(String name, String factoryKey, String roomName) {
    if (!factories.containsKey(factoryKey)) {
      return;
    }

    if (devices.containsKey(name)) {
      return;
    }

    if (roomName != null && !rooms.containsKey(roomName)) {
      return;
    }

    DeviceFactory factory = factories.get(factoryKey);
    Device device;

    if (factory instanceof LightFactory lightFactory) {
      device = lightFactory.createDevice();
    } else {
      device = factory.createDevice();
    }

    devices.put(name, device);

    if (roomName != null) {
      rooms.get(roomName).add(device);
      deviceToRoom.put(name, roomName);
      System.out.println("✓ Created device: " + name + " in room: " + roomName + " (" + factoryKey + ")");
    } else {
      System.out.println("✓ Created device: " + name + " (" + factoryKey + ")");
    }

  }

  public synchronized void deleteDeviceFromRoom(String deviceName, String room) {
    if (!devices.containsKey(deviceName)) {
      System.out.println("✗ Device does not exist: " + deviceName);
      return;
    }
    if (!rooms.containsKey(room)) {
      System.out.println("✗ Room does not exist: " + room);
      return;
    }

    Device device = devices.get(deviceName);

    List<Device> roomDevices = rooms.get(room);

    roomDevices.remove(device);

    devices.remove(deviceName);
    deviceToRoom.remove(deviceName);

    System.out.println("Deleted device: " + deviceName + " from room: " + room);
  }

  public synchronized void turnOn(String deviceName) {
    Device device = getDevice(deviceName);
    if (device != null) {
      device.on();
      System.out.println("✓ Turned ON: " + deviceName + " - Status: " + device.getStatus());
    }
  }

  public synchronized void turnOff(String deviceName) {
    Device device = getDevice(deviceName);
    if (device != null) {
      device.off();
      System.out.println("Turned OFF: " + deviceName + " - Status: " + device.getStatus());
    }
  }

  public synchronized void getDeviceStatus(String deviceName) {
    Device device = getDevice(deviceName);
    if (device != null) {
      System.out.println("Status of " + deviceName + ": " + device.getStatus());
    }
  }

  public synchronized void setLightBrightness(String lightName, int brightness) {
    Device device = getDevice(lightName);
    if (device instanceof Light light) {
      try {
        light.setBrightness(brightness);
        System.out.println("✓ Set brightness of " + lightName + " to " + brightness);
      } catch (IllegalStateException e) {
        System.out.println("✗ Error: " + e.getMessage());
      }
    } else {
      System.out.println("✗ Device is not a Light");
    }
  }

  public synchronized void setMusicVolume(String musicName, int volume) {
    Device device = getDevice(musicName);
    if (device instanceof Music music) {
      try {
        music.setVolume(volume);
        System.out.println("✓ Set volume of " + musicName + " to " + volume);
      } catch (IllegalStateException e) {
        System.out.println("✗ Error: " + e.getMessage());
      }
    } else {
      System.out.println("✗ Device is not Music");
    }
  }

  public synchronized void playSong(String musicName, String songName) {
    Device device = getDevice(musicName);
    if (device instanceof Music music) {
      try {
        music.playSong(songName);
      } catch (IllegalStateException e) {
        System.out.println("✗ Error: " + e.getMessage());
      }
    } else {
      System.out.println("✗ Device is not Music");
    }
  }

  public synchronized void setFanSpeed(String fanName, int speed) {
    Device device = getDevice(fanName);
    if (device instanceof Fan fan) {
      try {
        fan.setSpeed(speed);
        System.out.println("✓ Set fan speed of " + fanName + " to " + speed +
            " (" + fan.getSpeedName() + ")");
      } catch (IllegalStateException e) {
        System.out.println("✗ Error: " + e.getMessage());
      }
    } else {
      System.out.println("✗ Device is not a Fan");
    }
  }

  public synchronized void listAllDevices() {
    if (devices.isEmpty()) {
      return;
    }
    for (String name : devices.keySet()) {
      Device device = devices.get(name);
      String room = deviceToRoom.getOrDefault(name, "No Room");
      System.out.println("  • " + name + " (" + device.getClass().getSimpleName() +
          ") - Room: " + room + " - Status: " + device.getStatus());
    }
  }

  public synchronized void listDevicesByRoom(String roomName) {
    if (!rooms.containsKey(roomName)) {
      return;
    }

    List<Device> roomDevices = rooms.get(roomName);
    if (roomDevices.isEmpty()) {
      return;
    }

    for (Device device : roomDevices) {
      String deviceName = getDeviceNameByInstance(device);
      System.out.println("  • " + deviceName + " (" + device.getClass().getSimpleName() +
          ") - Status: " + device.getStatus());
    }
  }

  public synchronized void listAllRooms() {
    if (rooms.isEmpty()) {
      return;
    }
    for (String roomName : rooms.keySet()) {
      System.out.println("  • " + roomName + " (" + rooms.get(roomName).size() + " devices)");
    }
  }

  public synchronized void turnOnRoom(String roomName) {
    if (!rooms.containsKey(roomName)) {
      return;
    }

    System.out.println("\n--- Turning ON all devices in " + roomName + " ---");
    for (Device device : rooms.get(roomName)) {
      device.on();
    }
  }

  public synchronized void turnOffRoom(String roomName) {
    if (!rooms.containsKey(roomName)) {
      return;
    }

    for (Device device : rooms.get(roomName)) {
      device.off();
    }
  }

  private synchronized String getDeviceNameByInstance(Device device) {
    for (Map.Entry<String, Device> entry : devices.entrySet()) {
      if (entry.getValue() == device) {
        return entry.getKey();
      }
    }
    return "Unknown";
  }

  public synchronized void turnOnAll() {
    System.out.println("\n--- Turning ON all devices ---");
    for (String name : devices.keySet()) {
      turnOn(name);
    }
  }

  public synchronized void turnOffAll() {
    System.out.println("\n--- Turning OFF all devices ---");
    for (String name : devices.keySet()) {
      turnOff(name);
    }
  }

  private Device getDevice(String deviceName) {
    if (!devices.containsKey(deviceName)) {
      return null;
    }
    return devices.get(deviceName);
  }

  static void main(String[] args) throws Exception {

    SmartHomeController home = new SmartHomeController();

    home.createRoom("living_room");
    home.createRoom("bedroom");
    home.createRoom("kitchen");

    home.createDevice("living_room_light", "light.room", "living_room");
    home.createDevice("music_system", "music", "living_room");

    home.createDevice("ceiling_fan", "fan", "living_room");

    home.createDevice("kitchen_light", "light.room", "kitchen");

    Thread thread1 = new Thread(() -> {
      home.turnOnRoom("living_room");
      home.setLightBrightness("living_room_light", 75);
      home.setFanSpeed("ceiling_fan", 2);
      home.deleteDeviceFromRoom("ceiling_fan", "living_room");
      home.turnOffRoom("living_room");
    });

    Thread thread2 = new Thread(() -> {
      home.turnOnRoom("living_room");
      home.setLightBrightness("living_room_light", 75);
      home.setFanSpeed("ceiling_fan", 2);
      home.turnOffRoom("living_room");
    });

    Thread thread3 = new Thread(() -> {
      home.setLightBrightness("living_room_light", 75);
    });

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join();
    thread2.join();
    thread3.join();

    home.listDevicesByRoom("living_room");
    home.listAllRooms();
    home.listAllDevices();
  }
}
