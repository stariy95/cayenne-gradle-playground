package org.apache.cayenne.tools;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        // Add DSL extension
        project.getExtensions().create("cayenne", GradleCayenneExtension.class, project);

        // Register tasks
        Map<String, Object> map = new HashMap<>(1);
        map.put("type", CgenTask.class);
        project.task(map, "cgen");

        Map<String, Object> map1 = new HashMap<>(1);
        map1.put("type", DbImportTask.class);
        project.task(map1, "cdbimport");

        Map<String, Object> map2 = new HashMap<>(1);
        map2.put("type", CdbgenTask.class);
        project.task(map2, "cdbgen");
    }
}
