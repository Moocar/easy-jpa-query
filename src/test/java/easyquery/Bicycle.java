package easyquery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bicycle {
    
    private int id;
    
    private String model;
    
    private long timestamp;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bicycle other = (Bicycle) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.model == null) ? (other.model != null) : !this.model.equals(other.model)) {
            return false;
        }
        if (this.timestamp != other.timestamp) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + (this.model != null ? this.model.hashCode() : 0);
        hash = 37 * hash + (int) (this.timestamp ^ (this.timestamp >>> 32));
        return hash;
    }
}
