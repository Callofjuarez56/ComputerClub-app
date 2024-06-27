package ru.kurochkin.computerclub.ComputerClubBoot.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "consoles")
public class Consoles {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "console_number")
    private int consoleNumber;

    @Column(name = "console_name")
    private String consoleName;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "is_busy")
    private boolean isBusy;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Consoles(){}

    public Consoles(int consoleNumber, String consoleName, boolean isBusy) {
        this.consoleNumber = consoleNumber;
        this.consoleName = consoleName;
        this.isBusy = isBusy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsoleNumber() {
        return consoleNumber;
    }

    public void setConsoleNumber(int consoleNumber) {
        this.consoleNumber = consoleNumber;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean getIsBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Consoles{" +
                "id=" + id +
                ", consoleNumber=" + consoleNumber +
                ", consoleName='" + consoleName + '\'' +
                ", person=" + person +
                ", isBusy=" + isBusy +
                '}';
    }
}
