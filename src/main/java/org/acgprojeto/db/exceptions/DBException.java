package org.acgprojeto.db.exceptions;

public class DBException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DBException(String msg) {
        super(msg);
    }

}
