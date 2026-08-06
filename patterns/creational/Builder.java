package creational;

public class Builder {
  public static void main(String[] args) {
    FluentBuilder obj = new FluentBuilder.Builder()
            .setName("rowl")
            .build();

  }
}

class FluentBuilder {
  private final String name;
  private final int age;
  private final String address;
  private final String phone;
  private final String email;
  private final String gender;
  private final String occupation;
  private final String nationality;
  private final String maritalStatus;
  private final String education;
  private final String hobbies;
  private final String skills;

  private FluentBuilder(Builder builder) {
    this.name = builder.name;
    this.age = builder.age;
    this.address = builder.address;
    this.phone = builder.phone;
    this.email = builder.email;
    this.gender = builder.gender;
    this.occupation = builder.occupation;
    this.nationality = builder.nationality;
    this.maritalStatus = builder.maritalStatus;
    this.education = builder.education;
    this.hobbies = builder.hobbies;
    this.skills = builder.skills;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getAddress() {
    return address;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getGender() {
    return gender;
  }

  public String getOccupation() {
    return occupation;
  }

  public String getNationality() {
    return nationality;
  }

  public String getMaritalStatus() {
    return maritalStatus;
  }

  public String getEducation() {
    return education;
  }

  public String getHobbies() {
    return hobbies;
  }

  public String getSkills() {
    return skills;
  }

  static class Builder {
    private String name;
    private int age;
    private String address;
    private String phone;
    private String email;
    private String gender;
    private String occupation;
    private String nationality;
    private String maritalStatus;
    private String education;
    private String hobbies;
    private String skills;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setAge(int age) {
      this.age = age;
      return this;
    }

    public Builder setAddress(String address) {
      this.address = address;
      return this;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setGender(String gender) {
      this.gender = gender;
      return this;
    }

    public Builder setOccupation(String occupation) {
      this.occupation = occupation;
      return this;
    }

    public Builder setNationality(String nationality) {
      this.nationality = nationality;
      return this;
    }

    public Builder setMaritalStatus(String maritalStatus) {
      this.maritalStatus = maritalStatus;
      return this;
    }

    public Builder setEducation(String education) {
      this.education = education;
      return this;
    }

    public Builder setHobbies(String hobbies) {
      this.hobbies = hobbies;
      return this;
    }

    public Builder setSkills(String skills) {
      this.skills = skills;
      return this;
    }

    public FluentBuilder build() {
      return new FluentBuilder(this);
    }

  }
}
