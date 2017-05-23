package org.apache.cayenne.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 * @since 4.0
 */
public class BaseTaskIT {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    protected File projectDir;

    @Before
    public void createProjectDir() throws IOException {
        projectDir = tempFolder.newFolder();
    }

    protected GradleRunner createRunner(String projectName, String... args) throws IOException {
        prepareBuildScript(projectName);

        List<String> gradleArguments = new ArrayList<>();
        gradleArguments.addAll(Arrays.asList(args));
        gradleArguments.add("--stacktrace");

        return GradleRunner.create()
                .withProjectDir(projectDir)
                .withPluginClasspath()
                .withArguments(gradleArguments);
    }

    private void prepareBuildScript(String name) throws IOException {
        String projectFileSrc = getClass().getResource(name + ".gradle").getFile();
        Path src = FileSystems.getDefault().getPath(projectFileSrc);
        Path dst = FileSystems.getDefault().getPath(projectDir.getAbsolutePath(), "build.gradle");
        Files.copy(src, dst);
    }
}
