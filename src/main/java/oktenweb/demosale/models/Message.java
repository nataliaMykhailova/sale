package oktenweb.demosale.models;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String addressee;
    @OneToOne
    private Sale sale;
    @ManyToOne
    User user;
}

