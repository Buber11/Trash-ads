package pl.pwr.trash;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class ReadReplicaRoutingDataSource implements DataSource {

    private final DataSource master;
    private final List<DataSource> replicas;
    private final Random random = new Random();

    public ReadReplicaRoutingDataSource(DataSource master, List<DataSource> replicas) {
        this.master = master;
        this.replicas = replicas;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return master.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return master.getConnection(username, password);
    }

    public Connection getReadOnlyConnection() throws SQLException {
        DataSource replica = replicas.get(random.nextInt(replicas.size()));
        return replica.getConnection();
    }


    @Override public <T> T unwrap(Class<T> iface) throws SQLException { return master.unwrap(iface); }
    @Override public boolean isWrapperFor(Class<?> iface) throws SQLException { return master.isWrapperFor(iface); }
    @Override public java.io.PrintWriter getLogWriter() throws SQLException { return master.getLogWriter(); }
    @Override public void setLogWriter(java.io.PrintWriter out) throws SQLException { master.setLogWriter(out); }
    @Override public void setLoginTimeout(int seconds) throws SQLException { master.setLoginTimeout(seconds); }
    @Override public int getLoginTimeout() throws SQLException { return master.getLoginTimeout(); }
    @Override public java.util.logging.Logger getParentLogger() { throw new UnsupportedOperationException(); }
}
