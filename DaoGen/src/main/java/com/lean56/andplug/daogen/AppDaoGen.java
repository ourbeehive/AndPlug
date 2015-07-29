package com.lean56.andplug.daogen;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Generates entities and DAOs for the project baton.
 *
 * @author Charles
 */
public class AppDaoGen {

    private final static int version = 1;
    private final static String packageEntityName = "com.lean56.andplug.app.dao";
    private final static String genPath = "./App/src/main/java";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(version, packageEntityName);

        addMsg(schema);

        new DaoGenerator().generateAll(schema, genPath);
    }

    /**
     * Msg
     *
     * @param schema
     */
    private static void addMsg(Schema schema) {
        Entity entity = schema.addEntity("Msg");
        entity.implementsSerializable();
        entity.setHasKeepSections(true);
        entity.addLongProperty("id").primaryKey();
        entity.addStringProperty("title");
        entity.addStringProperty("user");
        entity.addStringProperty("content");
    }

}
