package org.imp.jvm.tool.cli;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import org.imp.jvm.tool.ExportTable;
import org.imp.jvm.tool.manifest.Manifest;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

@CommandLine.Command(
        name = "new",
        description = "Create a new Imp project."
)
class NewCommand implements Runnable {
    static final boolean DEBUG = true;


    @CommandLine.Parameters(paramLabel = "<projectName>",
            description = "Name of new project. Creates directory if it does not already exist.")
    private String projectName;

    @Override
    public void run() {
        String pwd = System.getProperty("user.dir");
        Path p = Path.of(pwd, projectName);

        if (Files.exists(p) && !DEBUG) {
            System.err.println("The directory you are attempting to create already exists.");
            System.exit(1);
        }

        try {
            // Create the project directory
            if (Files.notExists(p)) Files.createDirectories(p);

            // Create a manifest file
            makeManifest(Path.of(p.toString(), "imp.toml"));

            // Add the sample file content
            Files.writeString(Path.of(p.toString(), "main.imp"), makeSampleFile());

            // Create the `.compile` directory
            Files.createDirectories(Path.of(pwd, projectName, ".compile"));

            // Create the Export Table
            initDB(Path.of(pwd, projectName, ".compile", "imp.db"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Creating new project of name " + p);
    }

    private void initDB(Path dbPath) {
        try {
            // create a database connection
            ExportTable.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath.toString());
            Statement statement = ExportTable.connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

//            statement.executeUpdate("drop table if exists ExportTable");
//            statement.executeUpdate(ExportTable.createTable);

            ExportTable.initDB(dbPath);

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
//            try {
//                if (ExportTable.conn != null)
//                    ExportTable.conn.close();
//            } catch (SQLException e) {
//                // connection close failed.
//                System.err.println(e.getMessage());
//            }
        }
    }

    private void makeManifest(Path p) throws IOException {
        Manifest manifest = Manifest.create(projectName, "0.0.1", "main.imp");
        TomlMapper mapper = new TomlMapper();
        mapper.writeValue(p.toFile(), manifest);
    }

    private String makeSampleFile() {
        String template = """
                log("hello, {0}!")
                """;
        Set<String> randoms = new HashSet<>();
        Object[] objs = {"ree"};
        return new MessageFormat(template).format(objs);
    }
}

