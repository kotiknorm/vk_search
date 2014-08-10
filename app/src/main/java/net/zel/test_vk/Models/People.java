package net.zel.test_vk.Models;

/**
 * Created by alexey on 03.07.14.
 */
public class People {

    private final String firstName;
    private final String lastName;
    private final String photoUrl;
    private final String city;
    private final String online;
    private final String id;

    private People(String _firstName, String _lastName, String _photoUrl, String _city, String _online, String _id) {
        firstName = _firstName;
        lastName = _lastName;
        photoUrl = _photoUrl;
        city = _city;
        online = _online;
        id = _id;
    }

    public static class BuilderFriends {

        private String firstName;
        private String lastName;
        private String city;
        private String photoUrl;
        private String online;

        public BuilderFriends online(String online) {
            this.online = online;
            return this;
        }

        public BuilderFriends city(String city) {
            this.city = city;
            return this;
        }

        public BuilderFriends lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public BuilderFriends firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public BuilderFriends photoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
            return this;
        }

        public People build(String id) {
            return new People(firstName, lastName, photoUrl, city, online, id);
        }
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getUID() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String fullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public String getOnline() {
        return online;
    }

    public String getCity() {
        return city;
    }


}
