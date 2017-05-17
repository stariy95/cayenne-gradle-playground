package org.apache.cayenne.tools.model

import org.gradle.util.ConfigureUtil

/**
 * @since 4.0
 */
class IncludeTable extends PatternParam {

    Collection<PatternParam> includeColumns = new LinkedList<>()
    Collection<PatternParam> excludeColumns = new LinkedList<>()

    IncludeTable() {
    }

    IncludeTable(String pattern) {
        super(pattern)
    }

    void name(String name) {
        pattern = name
    }

    void includeColumn(String pattern) {
        includeColumns.add(new PatternParam(pattern))
    }

    void includeColumn(Closure<?> closure) {
        includeColumns.add(ConfigureUtil.configure(closure, new PatternParam()))
    }

    void excludeColumn(String pattern) {
        excludeColumns.add(new PatternParam(pattern))
    }

    void excludeColumn(Closure<?> closure) {
        excludeColumns.add(ConfigureUtil.configure(closure, new PatternParam()))
    }

    org.apache.cayenne.dbsync.reverse.dbimport.IncludeTable toIncludeTable() {
        org.apache.cayenne.dbsync.reverse.dbimport.IncludeTable table =
                new org.apache.cayenne.dbsync.reverse.dbimport.IncludeTable()
        table.pattern = pattern
        includeColumns.each {table.addIncludeColumn(it.toIncludeColumn())}
        excludeColumns.each {table.addExcludeColumn(it.toExcludeColumn())}
        return table
    }
}
