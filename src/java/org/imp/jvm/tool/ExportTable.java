package org.imp.jvm.tool;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Constants;
import org.imp.jvm.Util;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.types.ImpType;

import java.io.*;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ExportTable {

    // Eventually this will be backed by SQLite or something
    private static final MultiKeyMap<String, ImpType> table = new MultiKeyMap<>();
    public static Connection connection;
    static PreparedStatement psAddExport;
    static PreparedStatement psGetExportFromSource;

    public static void initDB(Path path) {
        try {
            var mkdirsResult = new File(path.toString()).getParentFile().mkdirs();
            if (!mkdirsResult) Util.exit("mkdirs command failed due to unknown issue", Constants.EIO);
            connection = DriverManager.getConnection("jdbc:sqlite:" + path);
            Statement statement = ExportTable.connection.createStatement();
            statement.executeUpdate("drop table if exists ExportTable");
            statement.executeUpdate("""
                    create table ExportTable
                    (
                        qualifiedName string primary key not null,
                        name string not null,
                        source string not null,
                        kind string not null,
                        -- serialized
                        objectName string,
                        object blob
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

            psAddExport = connection.prepareStatement("""
                    replace into ExportTable(qualifiedName, name, source, kind, objectName, object)
                    values (?,?,?,?,?,?)
                    """);
            psGetExportFromSource = connection.prepareStatement("select * from ExportTable where source=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new record to the ExportTable.
     *
     * @param file absolute path to file the type is defined in
     * @param name type name
     */
    public static void addSQL(File file, String name, ImpType type) {
        try {
            String filepath = FilenameUtils.separatorsToUnix(file.getPath());
            String qualifiedName = filepath + ":" + name;

            psAddExport.setString(1, qualifiedName);
            psAddExport.setString(2, name);
            psAddExport.setString(3, filepath);
            psAddExport.setString(4, type.kind());

            psAddExport.setString(5, type.getClass().getName());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(type);
            byte[] employeeAsBytes = baos.toByteArray();

            psAddExport.setBytes(6, employeeAsBytes);
            psAddExport.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all exports from a certain source.
     */
    public static List<ExportResult> getExportsFromSource(Path path) {
        String sourcePath = FilenameUtils.separatorsToUnix(path.toString());
        try {
            psGetExportFromSource.setString(1, sourcePath);
            ResultSet rs = psGetExportFromSource.executeQuery();

            List<ExportResult> types = new ArrayList<>();
            while (rs.next()) {
                ByteArrayInputStream bais = new ByteArrayInputStream(rs.getBytes(6));
                ObjectInputStream ois = new ObjectInputStream(bais);
                ImpType o = (ImpType) ois.readObject();

                types.add(new ExportResult(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        o
                ));
            }
            return types;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void add(SourceFile source, String name, ImpType type) {
        String path = FilenameUtils.removeExtension(source.file.getPath());
        path = FilenameUtils.separatorsToUnix(path);
        table.put(path, name, type);
    }

    public static Optional<ImpType> get(String source, String name) {
        String path = FilenameUtils.separatorsToUnix(source);
        return Optional.ofNullable(table.get(path, name));
    }

    public record ExportResult(String qualifiedName, String name, String source, String kind, String objectName
            , Object o) {

    }
}
