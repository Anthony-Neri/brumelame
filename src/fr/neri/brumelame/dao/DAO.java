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

    /**
     * Méthode de création
     *
     * @param obj
     * @return boolean
     */
    public abstract boolean create(T obj);

    /**
     * Méthode pour effacer
     *
     * @param obj
     * @return boolean
     */
    public abstract boolean delete(T obj);

    /**
     * Méthode de mise à jour
     *
     * @param obj
     * @return boolean
     */
    public abstract boolean update(T obj);

    /**
     * Méthode de recherche des informations
     *
     * @param id
     * @return T
     */
    public abstract T find(int id);
}


