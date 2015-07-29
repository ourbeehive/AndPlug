package com.lean56.andplug.daogen;

import de.greenrobot.daogenerator.DBServiceGenerator;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates DBService
 *
 * Run it as a Java application (not Android).
 *
 * @author Charles
 */
public class AppDBServiceGen {

    private final static int version = 1;
    private final static String packageEntityName = "com.lean56.andplug.service";
    private final static String genPath = "./App/src/main/java";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(version, packageEntityName);

        schema.addEntity("Msg");
        schema.addEntity("Article");

        new DBServiceGenerator().generateAll(schema, genPath);
    }

}
