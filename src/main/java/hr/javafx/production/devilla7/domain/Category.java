package hr.javafx.production.devilla7.domain;

import java.util.Objects;

/** Klasa koja predstavlja kategoriju proizvoda.
 *  Ima podatke o imenu i opisu kategorije.
 */
public class Category extends NamedEntity{

    /**
     * Opis kategorije
     */
    private String description;

    //private HandleWithCare handle;

    /*public HandleWithCare getHandle() {
        return handle;
    }*/

    public Category(Long id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    /**
     * Konstruktor za kategoriju
     * @param name predstavlja ime kategorije
     * @param description predstavlja opis kategorije
     */


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}