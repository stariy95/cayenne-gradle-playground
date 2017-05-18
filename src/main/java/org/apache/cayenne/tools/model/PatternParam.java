package org.apache.cayenne.tools.model;

import org.apache.cayenne.dbsync.reverse.dbimport.ExcludeColumn;
import org.apache.cayenne.dbsync.reverse.dbimport.ExcludeProcedure;
import org.apache.cayenne.dbsync.reverse.dbimport.ExcludeTable;
import org.apache.cayenne.dbsync.reverse.dbimport.IncludeColumn;
import org.apache.cayenne.dbsync.reverse.dbimport.IncludeProcedure;

/**
 * @since 4.0
 */
public class PatternParam {

    private String pattern;

    PatternParam() {
    }

    PatternParam(String pattern) {
        this.pattern = pattern;
    }

    public void pattern(String pattern) {
        this.pattern = pattern;
    }

    ExcludeTable toExcludeTable() {
        ExcludeTable table = new ExcludeTable();
        table.setPattern(pattern);
        return table;
    }

    IncludeColumn toIncludeColumn() {
        IncludeColumn column = new IncludeColumn();
        column.setPattern(pattern);
        return column;
    }

    ExcludeColumn toExcludeColumn() {
        ExcludeColumn column = new ExcludeColumn();
        column.setPattern(pattern);
        return column;
    }

    IncludeProcedure toIncludeProcedure() {
        IncludeProcedure procedure = new IncludeProcedure();
        procedure.setPattern(pattern);
        return procedure;
    }

    ExcludeProcedure toExcludeProcedure() {
        ExcludeProcedure procedure = new ExcludeProcedure();
        procedure.setPattern(pattern);
        return procedure;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
