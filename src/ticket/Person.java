package ticket;

import java.io.Serializable;
import java.util.Objects;

public abstract class Person implements Serializable {
    protected String name;
    protected int balance;

    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return(person.name.equals(this.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
