package org.apache.cayenne.tools;

import java.io.File;

import org.apache.cayenne.gen.ClassGenerationAction;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

/**
 * @since 4.0
 */
public class CgenTest {

    public CgenTask createCgenTaskMock(ClassGenerationAction action) {
        CgenTask mock = mock(CgenTask.class);

        doCallRealMethod().when(mock).setClient(anyBoolean());
        doCallRealMethod().when(mock).setAdditionalMaps(any(File.class));
        doCallRealMethod().when(mock).setCreatePropertyNames(anyBoolean());
        doCallRealMethod().when(mock).setEmbeddableSuperTemplate(anyString());
        doCallRealMethod().when(mock).setEmbeddableTemplate(anyString());
        doCallRealMethod().when(mock).setEncoding(anyString());
        doCallRealMethod().when(mock).setExcludeEntities(anyString());
        doCallRealMethod().when(mock).setIncludeEntities(anyString());
        doCallRealMethod().when(mock).setMakePairs(anyBoolean());
        doCallRealMethod().when(mock).setMode(anyString());
        doCallRealMethod().when(mock).setOutputPattern(anyString());
        doCallRealMethod().when(mock).setSuperPkg(anyString());
        doCallRealMethod().when(mock).setSuperTemplate(anyString());
        doCallRealMethod().when(mock).setOverwrite(anyBoolean());
        doCallRealMethod().when(mock).setUsePkgPath(anyBoolean());
        when(mock.newGeneratorInstance()).thenReturn(action);
        when(mock.createGenerator()).thenCallRealMethod();

        return mock;
    }

    @Test
    public void testGeneratorCreation() {
        ClassGenerationAction action = mock(ClassGenerationAction.class);
        CgenTask task = createCgenTaskMock(action);
        task.setCreatePropertyNames(true);
        task.setMode("some_mode");


        ClassGenerationAction createdAction = task.createGenerator();
        assertSame(action, createdAction);

        verify(action).setCreatePropertyNames(true);
        verify(action).setArtifactsGenerationMode("some_mode");
    }

}
