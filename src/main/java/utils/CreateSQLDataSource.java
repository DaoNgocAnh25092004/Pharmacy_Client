package utils;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class CreateSQLDataSource {
    private SQLServerDataSource ds;
    private static CreateSQLDataSource instance;

    public static synchronized CreateSQLDataSource getInstance() {
        if (instance == null) {
            instance = new CreateSQLDataSource();
        }
        return instance;
    }

    private CreateSQLDataSource() {
        ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("sapassword");
        ds.setPortNumber(1433);
        ds.setServerName("localhost");
        ds.setDatabaseName("Pharmacy");
        ds.setTrustServerCertificate(true);
        ds.setHostNameInCertificate("*.database.windows.net");
        ds.setTrustManagerClass("com.microsoft.sqlserver.jdbc.SQLServerTrustManager");
        ds.setTrustManagerConstructorArg("TLSv1.2");
        ds.setSSLProtocol("TLSv1.2");
    }

    public SQLServerDataSource getDataSource() {
        if (ds != null){
            return ds;
        } else {
            System.out.println("DataSource is null");
            throw new NullPointerException("DataSource is null");
        }
    }
}
