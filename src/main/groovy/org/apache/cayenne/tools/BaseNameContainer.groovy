package org.apache.cayenne.tools

/**
 * @since 4.0
 */
class BaseNameContainer {

    Collection<String> names

    Collection<String> getNames() {
        return names
    }

    void setNames(Collection<String> names) {
        this.names = names
    }

    String toString() {
        return names?.join(',')
    }
}
