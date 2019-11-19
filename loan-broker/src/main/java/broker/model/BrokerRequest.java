package broker.model;

import java.util.Objects;

/**
 *
 * This class stores all information about a
 * request that a client submits to get a loan.
 *
 */
public class BrokerRequest {

    private String id;

    public BrokerRequest() {
        super();
        this.id = "";
    }

    public BrokerRequest(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrokerRequest that = (BrokerRequest) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "id=" + String.valueOf(id);
    }
}
