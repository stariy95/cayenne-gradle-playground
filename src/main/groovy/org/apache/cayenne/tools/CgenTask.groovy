package org.apache.cayenne.tools

import org.apache.cayenne.dbsync.filter.NamePatternMatcher
import org.apache.cayenne.gen.ClassGenerationAction
import org.apache.cayenne.gen.ClientClassGenerationAction
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * @since 4.0
 */
class CgenTask extends DefaultTask {

    private static final File[] NO_FILES = new File[0]

    private File additionalMaps

    private boolean client

    private File destDir

    private String encoding

    private String excludeEntities

    private String includeEntities

    private boolean makePairs

    private File map

    private String mode

    private String outputPattern

    private boolean overwrite

    private String superPkg

    private String superTemplate

    private String template

    private String embeddableSuperTemplate

    private String embeddableTemplate

    private boolean usePkgPath

    private boolean createPropertyNames

    CgenTask() {
        destDir = getProject().projectDir
        client = false
        makePairs = true
        overwrite = false
        usePkgPath = true
        createPropertyNames = false
        mode = "entity"
        outputPattern = "*.java"
    }

    @TaskAction
    def generate() {

        if (!destDir.exists()) {
            destDir.mkdirs()
        }

        def loaderAction = new CayenneGeneratorMapLoaderAction()
        loaderAction.setMainDataMapFile(map)

        def filterAction = new CayenneGeneratorEntityFilterAction()
        filterAction.setClient(client)
        filterAction.setNameFilter(NamePatternMatcher.build(logger, includeEntities, excludeEntities))

        try {
            loaderAction.setAdditionalDataMapFiles(convertAdditionalDataMaps())

            def dataMap = loaderAction.getMainDataMap()
            def generator = this.createGenerator()

            generator.setLogger(logger)
            generator.setTimestamp(map.lastModified())
            generator.setDataMap(dataMap)
            generator.addEntities(filterAction.getFilteredEntities(dataMap))
            generator.addEmbeddables(dataMap.getEmbeddables())
            generator.addQueries(dataMap.getQueryDescriptors())
            generator.execute()
        } catch (Exception exception) {
            throw new GradleException("Error generating classes: ", exception)
        }
    }

    private def convertAdditionalDataMaps() throws Exception {
        if (additionalMaps == null) {
            return NO_FILES
        }

        if (!additionalMaps.isDirectory()) {
            throw new GradleException("'additionalMaps' must be a directory.")
        }

        FilenameFilter mapFilter = new FilenameFilter() {
            boolean accept(File dir, String name) {
                return name != null && name.toLowerCase().endsWith(".map.xml")
            }
        }
        return additionalMaps.listFiles(mapFilter)
    }

    private def createGenerator() {
        def action
        if (client) {
            action = new ClientClassGenerationAction()
        } else {
            action = new ClassGenerationAction()
        }

        if(destDir == null) {
            throw new GradleException("destDir is null")
        }

        action.setDestDir(destDir)
        action.setEncoding(encoding)
        action.setMakePairs(makePairs)
        action.setArtifactsGenerationMode(mode)
        action.setOutputPattern(outputPattern)
        action.setOverwrite(overwrite)
        action.setSuperPkg(superPkg)
        action.setSuperTemplate(superTemplate)
        action.setTemplate(template)
        action.setEmbeddableSuperTemplate(embeddableSuperTemplate)
        action.setEmbeddableTemplate(embeddableTemplate)
        action.setUsePkgPath(usePkgPath)
        action.setCreatePropertyNames(createPropertyNames)

        return action
    }

    File getAdditionalMaps() {
        return additionalMaps
    }

    void setAdditionalMaps(File additionalMaps) {
        this.additionalMaps = additionalMaps
    }

    boolean getClient() {
        return client
    }

    void setClient(boolean client) {
        this.client = client
    }

    File getDestDir() {
        return destDir
    }

    void setDestDir(File destDir) {
        this.destDir = destDir
    }

    void setDestDir(String destDir) {
        this.destDir = new File(destDir)
    }

    String getEncoding() {
        return encoding
    }

    void setEncoding(String encoding) {
        this.encoding = encoding
    }

    String getExcludeEntities() {
        return excludeEntities
    }

    void setExcludeEntities(String excludeEntities) {
        this.excludeEntities = excludeEntities
    }

    String getIncludeEntities() {
        return includeEntities
    }

    void setIncludeEntities(String includeEntities) {
        this.includeEntities = includeEntities
    }

    boolean getMakePairs() {
        return makePairs
    }

    void setMakePairs(boolean makePairs) {
        this.makePairs = makePairs
    }

    File getMap() {
        return map
    }

    void setMap(File map) {
        this.map = map
    }

    void setMap(String map) {
        this.map = new File(map)
    }

    String getMode() {
        return mode
    }

    void setMode(String mode) {
        this.mode = mode
    }

    String getOutputPattern() {
        return outputPattern
    }

    void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern
    }

    boolean getOverwrite() {
        return overwrite
    }

    void setOverwrite(boolean overwrite) {
        this.overwrite = overwrite
    }

    String getSuperPkg() {
        return superPkg
    }

    void setSuperPkg(String superPkg) {
        this.superPkg = superPkg
    }

    String getSuperTemplate() {
        return superTemplate
    }

    void setSuperTemplate(String superTemplate) {
        this.superTemplate = superTemplate
    }

    String getTemplate() {
        return template
    }

    void setTemplate(String template) {
        this.template = template
    }

    String getEmbeddableSuperTemplate() {
        return embeddableSuperTemplate
    }

    void setEmbeddableSuperTemplate(String embeddableSuperTemplate) {
        this.embeddableSuperTemplate = embeddableSuperTemplate
    }

    String getEmbeddableTemplate() {
        return embeddableTemplate
    }

    void setEmbeddableTemplate(String embeddableTemplate) {
        this.embeddableTemplate = embeddableTemplate
    }

    boolean getUsePkgPath() {
        return usePkgPath
    }

    void setUsePkgPath(boolean usePkgPath) {
        this.usePkgPath = usePkgPath
    }

    boolean getCreatePropertyNames() {
        return createPropertyNames
    }

    void setCreatePropertyNames(boolean createPropertyNames) {
        this.createPropertyNames = createPropertyNames
    }
}
