package org.apache.cayenne.tools.model

import org.apache.cayenne.dbsync.reverse.dbimport.ExcludeColumn
import org.apache.cayenne.dbsync.reverse.dbimport.ExcludeProcedure
import org.apache.cayenne.dbsync.reverse.dbimport.ExcludeTable
import org.apache.cayenne.dbsync.reverse.dbimport.IncludeColumn
import org.apache.cayenne.dbsync.reverse.dbimport.IncludeProcedure

/**
 * @since 4.0
 */
class PatternParam {
    String pattern

    PatternParam() {
    }

    PatternParam(String pattern) {
        this.pattern = pattern
    }

    ExcludeTable toExcludeTable() {
        ExcludeTable table = new ExcludeTable()
        table.pattern = pattern
        return table
    }

    IncludeColumn toIncludeColumn() {
        IncludeColumn column = new IncludeColumn()
        column.pattern = pattern
        return column
    }

    ExcludeColumn toExcludeColumn() {
        ExcludeColumn column = new ExcludeColumn()
        column.pattern = pattern
        return column
    }

    IncludeProcedure toIncludeProcedure() {
        IncludeProcedure procedure = new IncludeProcedure()
        procedure.pattern = pattern
        return procedure
    }

    ExcludeProcedure toExcludeProcedure() {
        ExcludeProcedure procedure = new ExcludeProcedure()
        procedure.pattern = pattern
        return procedure
    }
}
