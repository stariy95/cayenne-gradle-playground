package org.apache.cayenne.tools.model;

import java.util.Collection;
import java.util.LinkedList;

import groovy.lang.Closure;
import org.gradle.util.ConfigureUtil;

/**
 * @since 4.0
 */
public class FilterContainer {

    private String name;
    private Collection<IncludeTable> includeTables = new LinkedList<>();
    private Collection<PatternParam> excludeTables = new LinkedList<>();
    private Collection<PatternParam> includeColumns = new LinkedList<>();
    private Collection<PatternParam> excludeColumns = new LinkedList<>();
    private Collection<PatternParam> includeProcedures = new LinkedList<>();
    private Collection<PatternParam> excludeProcedures = new LinkedList<>();

    FilterContainer() {
    }

    FilterContainer(String name) {
        this.name = name;
    }

    public void name(String name) {
        this.name = name;
    }

    public void includeTable(String pattern) {
        includeTables.add(new IncludeTable(pattern));
    }

    public void includeTable(Closure<?> closure) {
        includeTables.add(ConfigureUtil.configure(closure, new IncludeTable()));
    }

    public void includeTable(String pattern, Closure<?> closure) {
        includeTables.add(ConfigureUtil.configure(closure, new IncludeTable(pattern)));
    }

    public void includeTables(String... patterns) {
        for(String pattern: patterns) {
            includeTable(pattern);
        }
    }

    public void excludeTable(String pattern) {
        addToCollection(excludeTables, pattern);
    }

    public void excludeTable(Closure<?> closure) {
        addToCollection(excludeTables, closure);
    }

    public void excludeTables(String... patterns) {
        for(String pattern: patterns) {
            excludeTable(pattern);
        }
    }

    public void includeColumn(String pattern) {
        addToCollection(includeColumns, pattern);
    }

    public void includeColumn(Closure<?> closure) {
        addToCollection(includeColumns, closure);
    }

    public void includeColumns(String... patterns) {
        for(String pattern: patterns) {
            includeColumn(pattern);
        }
    }

    public void excludeColumn(String pattern) {
        addToCollection(excludeColumns, pattern);
    }

    public void excludeColumn(Closure<?> closure) {
        addToCollection(excludeColumns, closure);
    }

    public void excludeColumns(String... patterns) {
        for(String pattern: patterns) {
            excludeColumn(pattern);
        }
    }

    public void includeProcedure(String pattern) {
        addToCollection(includeProcedures, pattern);
    }

    public void includeProcedure(Closure<?> closure) {
        addToCollection(includeProcedures, closure);
    }

    public void includeProcedures(String... patterns) {
        for(String pattern: patterns) {
            includeProcedure(pattern);
        }
    }

    public void excludeProcedure(String pattern) {
        addToCollection(excludeProcedures, pattern);
    }

    public void excludeProcedure(Closure<?> closure) {
        addToCollection(excludeProcedures, closure);
    }

    public void excludeProcedures(String... patterns) {
        for(String pattern: patterns) {
            excludeProcedure(pattern);
        }
    }

    private static void addToCollection(Collection<PatternParam> collection, Closure<?> closure) {
        collection.add(ConfigureUtil.configure(closure, new PatternParam()));
    }

    private static void addToCollection(Collection<PatternParam> collection, String name) {
        collection.add(new PatternParam(name));
    }

    <C extends org.apache.cayenne.dbsync.reverse.dbimport.FilterContainer> C fillContainer(final C container) {
        container.setName(name);
        for(IncludeTable table : includeTables) {
            container.addIncludeTable(table.toIncludeTable());
        }
        for(PatternParam table : excludeTables) {
            container.addExcludeTable(table.toExcludeTable());
        }

        for(PatternParam column : includeColumns) {
            container.addIncludeColumn(column.toIncludeColumn());
        }
        for(PatternParam column : excludeColumns) {
            container.addExcludeColumn(column.toExcludeColumn());
        }

        for(PatternParam procedure : includeProcedures) {
            container.addIncludeProcedure(procedure.toIncludeProcedure());
        }
        for(PatternParam procedure : excludeProcedures) {
            container.addExcludeProcedure(procedure.toExcludeProcedure());
        }
        return container;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}