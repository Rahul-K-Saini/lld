public class Music extends AbstractDevice {

  private int volume;
  private String currentSong;
  private static final int MAX_VOLUME = 100;

  public Music() {
    super();
    this.volume = 0;
    this.currentSong = "None";
  }

  public void playSong(String songName) {
    if (this.status == DeviceStatus.OFF) {
      throw new IllegalStateException("Cannot play song when device is OFF");
    }
    this.currentSong = songName;
    System.out.println("Now playing: " + songName);
  }

  public void setVolume(int volume) {
    if (this.status == DeviceStatus.OFF) {
      throw new IllegalStateException("Cannot set volume when device is OFF");
    }
    if (volume < 0 || volume > MAX_VOLUME) {
      throw new IllegalStateException("Volume must be between 0 and " + MAX_VOLUME);
    }
    this.volume = volume;
  }

  public int getVolume() {
    return this.volume;
  }

  public String getCurrentSong() {
    return this.currentSong;
  }
}
