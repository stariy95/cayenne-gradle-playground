package org.apache.cayenne.tools.model;

import java.util.Collection;
import java.util.LinkedList;

import groovy.lang.Closure;
import org.apache.cayenne.dbsync.reverse.dbimport.Catalog;
import org.apache.cayenne.dbsync.reverse.dbimport.Schema;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.gradle.util.ConfigureUtil;

/**
 * @since 4.0
 */
public class SchemaContainer extends FilterContainer {

    Collection<FilterContainer> schemas = new LinkedList<>();

    SchemaContainer() {
    }

    SchemaContainer(String name) {
        this.setName(name);
    }

    public void schema(String name) {
        schemas.add(new FilterContainer(name));
    }

    public void schema(Closure<?> closure) {
        schemas.add(ConfigureUtil.configure(closure, new FilterContainer()));
    }

    Catalog toCatalog() {
        Catalog catalog = fillContainer(new Catalog());
        for(FilterContainer container : schemas) {
            catalog.addSchema(container.fillContainer(new Schema()));
        }
        return catalog;
    }
}
