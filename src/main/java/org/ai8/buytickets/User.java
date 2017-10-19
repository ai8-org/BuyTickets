package org.ai8.buytickets;

import javafx.beans.DefaultProperty;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    public String mail;

    public String nickname;
    public String password;
    public String tickets;
    public String role;
    public String session;
}
