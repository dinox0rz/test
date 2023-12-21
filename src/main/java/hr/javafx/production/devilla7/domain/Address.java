package hr.javafx.production.devilla7.domain;

import hr.javafx.production.devilla7.enumeration.City;

public class Address {
    private Long id;
    private String street;
    private String houseNumber;
    private City city;
    private Address(){};

    public Address(Long id, String street, String houseNumber, City city) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
    }

    public static class Builder{
        private String street;
        private String houseNumber;
        private City city;

        public Builder(String street, String houseNumber){
            this.street=street;
            this.houseNumber=houseNumber;
        }
        public Builder withCity(City city){
            this.city=city;
            return this;
        }

        public Address build(){
            Address address = new Address();
            address.street=this.street;
            address.houseNumber=this.houseNumber;
            address.city=this.city;
            return address;
        }
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getStreet()+" "+getHouseNumber()+" in: "+getCity().getName();
    }
}
