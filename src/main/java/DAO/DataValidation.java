package DAO;

public class DataValidation {
    private static DataValidation instance = new DataValidation();

    public static DataValidation getInstance() {
        if (instance == null)
            instance = new DataValidation();
        return instance;
    }

    
}
