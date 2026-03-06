package fr.neri.brumelame.dao;


import fr.neri.brumelame.db.DatabaseConnection;

public abstract class DAO<T> {
    protected DatabaseConnection conn = null;

    public DAO(DatabaseConnection conn) {
        this.conn = conn;
    }

    public DAO() {
        this(DatabaseConnection.getInstance());
    }

}


