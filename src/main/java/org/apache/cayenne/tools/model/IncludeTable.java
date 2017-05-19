package org.apache.cayenne.tools.model;

import java.util.Collection;
import java.util.LinkedList;

import groovy.lang.Closure;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.gradle.util.ConfigureUtil;

/**
 * @since 4.0
 */
public class IncludeTable extends PatternParam {

    private Collection<PatternParam> includeColumns = new LinkedList<>();
    private Collection<PatternParam> excludeColumns = new LinkedList<>();

    IncludeTable() {
    }

    IncludeTable(String pattern) {
        super(pattern);
    }

    public void name(String name) {
        setPattern(name);
    }

    public void includeColumn(String pattern) {
        includeColumns.add(new PatternParam(pattern));
    }

    public void includeColumns(String... patterns) {
        for(String pattern: patterns) {
            includeColumn(pattern);
        }
    }

    public void excludeColumn(String pattern) {
        excludeColumns.add(new PatternParam(pattern));
    }

    public void excludeColumns(String... patterns) {
        for(String pattern: patterns) {
            excludeColumn(pattern);
        }
    }

    org.apache.cayenne.dbsync.reverse.dbimport.IncludeTable toIncludeTable() {
        org.apache.cayenne.dbsync.reverse.dbimport.IncludeTable table
                = new org.apache.cayenne.dbsync.reverse.dbimport.IncludeTable();
        table.setPattern(getPattern());

        for(PatternParam includeColumn : includeColumns) {
            table.addIncludeColumn(includeColumn.toIncludeColumn());
        }
        for(PatternParam excludeColumn : excludeColumns) {
            table.addExcludeColumn(excludeColumn.toExcludeColumn());
        }

        return table;
    }
}
