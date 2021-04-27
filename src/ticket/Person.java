package ticket;

import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements Serializable {
    protected final String name;

    protected Person(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return(person.name.equalsIgnoreCase(this.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
