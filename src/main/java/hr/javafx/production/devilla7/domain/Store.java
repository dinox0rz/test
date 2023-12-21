package hr.javafx.production.devilla7.domain;

import java.util.Objects;
import java.util.Set;

public class Store extends NamedEntity{
    private String webAddress;

    private Address address;
    private Set<Item> items;

    private boolean twentyfour;

    private String color;

    public Store(Long id, String name, String webAddress, Address address, Set<Item> items, boolean twentyfour, String color) {
        super(id, name);
        this.webAddress = webAddress;
        this.address = address;
        this.items = items;
        this.twentyfour = twentyfour;
        this.color = color;
    }

    public Store(Long id, String name, String webAddress, Address address, Set<Item> items) {
        super(id, name);
        this.webAddress = webAddress;
        this.address = address;
        this.items = items;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Store store = (Store) o;
        return Objects.equals(webAddress, store.webAddress) && Objects.equals(items, store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), webAddress, items);
    }
}
