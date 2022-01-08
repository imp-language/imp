package org.imp.jvm.tool;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.types.Type;

import java.nio.file.Path;
import java.sql.*;
import java.util.Optional;

public class ExportTable {

    // Eventually this will be backed by SQLite or something
    private static final MultiKeyMap<String, Type> table = new MultiKeyMap<>();
    public static Connection connection;
    static PreparedStatement psAddExport;
    static PreparedStatement psGetExportFromSource;

    public static void initDB(Path path) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path.toString());
            Statement statement = ExportTable.connection.createStatement();
            statement.executeUpdate("drop table if exists ExportTable");
            statement.executeUpdate("""
                    create table ExportTable
                    (
                        qualifiedName string primary key not null,
                        source string not null
                    );
                    """);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect to the project database and check that the proper tables exist.
     * Also, set up PreparedStatements.
     *
     * @param path pointing to imp.db
     */
    public static void connectDB(Path path) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + path.toString());

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet resultSet = meta.getTables(null, null, "ExportTable", new String[]{"TABLE"});
            boolean tableExists = resultSet.next();
            if (!tableExists) {
                throw new SQLException("ExportTable does not exist in the database.");
            }

            psAddExport = connection.prepareStatement("replace into ExportTable(qualifiedName, source) values (?, ?)");
            psGetExportFromSource = connection.prepareStatement("select * from ExportTable where source=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new record to the ExportTable.
     *
     * @param source unix-separated path
     * @param name   type name
     */
    public static void addSQL(String source, String name) {
        try {
            String qualifiedName = source + ":" + name;
            psAddExport.setString(1, qualifiedName);
            psAddExport.setString(2, source);
            psAddExport.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all exports from a certain source.
     */
    public static void getExportsFromSource(Path sourcePath) {
        String source = FilenameUtils.separatorsToUnix(sourcePath.toString());
        try {
            psGetExportFromSource.setString(1, source);
            ResultSet rs = psGetExportFromSource.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + ", " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void add(SourceFile source, String name, Type type) {
        String path = FilenameUtils.removeExtension(source.file.getPath());
        path = FilenameUtils.separatorsToUnix(path);
        table.put(path, name, type);
    }

    public static Optional<Type> get(String source, String name) {
        String path = FilenameUtils.separatorsToUnix(source);
        return Optional.ofNullable(table.get(path, name));
    }

    public static String dump() {
        StringBuilder s = new StringBuilder();
        table.forEach((k1, k2) -> {
            s.append(StringUtils.rightPad(k1.toString(), 35)).append("\t").append(k2).append("\n");
        });
        return s.toString();
    }
}
