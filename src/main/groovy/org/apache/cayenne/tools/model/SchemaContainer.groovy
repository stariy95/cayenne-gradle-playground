package org.apache.cayenne.tools.model

import org.apache.cayenne.dbsync.reverse.dbimport.Catalog
import org.apache.cayenne.dbsync.reverse.dbimport.Schema
import org.gradle.util.ConfigureUtil

/**
 * @since 4.0
 */
class SchemaContainer extends FilterContainer {
    Collection<FilterContainer> schemas = new LinkedList<>()

    SchemaContainer() {

    }

    SchemaContainer(String name) {
        this.name = name
    }

    void schema(String name) {
        schemas.add(new FilterContainer(name))
    }

    void schema(Closure<?> closure) {
        schemas.add(ConfigureUtil.configure(closure, new FilterContainer()))
    }

    Catalog toCatalog() {
        Catalog catalog = fillContainer(new Catalog())
        schemas.each { catalog.addSchema(it.fillContainer(new Schema())) }
        return catalog
    }
}
