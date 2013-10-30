package edu.business;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 10/20/13
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Credentials implements Serializable {
    private final String name;
    private final String secondName;
    private final String email;

    public Credentials(final String name, final String secondName, final String email) {
        assert name != null;
        assert secondName != null;
        assert email != null;

        this.name = name;
        this.secondName = secondName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

}
