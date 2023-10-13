package br.dev.pedrolamarao.gradle.metal.asm;

import br.dev.pedrolamarao.gradle.metal.base.MetalBasePlugin;
import br.dev.pedrolamarao.gradle.metal.base.MetalExtension;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.Directory;

import static br.dev.pedrolamarao.gradle.metal.base.Metal.COMMANDS_ELEMENTS;
import static br.dev.pedrolamarao.gradle.metal.base.Metal.INCLUDABLE_DEPENDENCIES;

/**
 * Assembler language support plugin.
 */
public class MetalAsmPlugin implements Plugin<Project>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public void apply (Project project)
    {
        project.getPluginManager().apply(MetalBasePlugin.class);

        final var metal = project.getExtensions().getByType(MetalExtension.class);

        final var asm = project.getObjects().domainObjectContainer(MetalAsmSources.class, name -> createSources(project,name));
        metal.getExtensions().add("asm", asm);
    }

    static MetalAsmSources createSources (Project project, String name)
    {
        final var configurations = project.getConfigurations();
        final var layout = project.getLayout();
        final var metal = project.getExtensions().getByType(MetalExtension.class);
        final var objects = project.getObjects();
        final var tasks = project.getTasks();

        final var commandsTask = tasks.register("commands-%s-asm".formatted(name),MetalAsmCommandsTask.class);
        final var compileTask = tasks.register("compile-%s-asm".formatted(name),MetalAsmCompileTask.class);
        final var sourceSet = objects.newInstance(MetalAsmSources.class,commandsTask,compileTask,name);
        sourceSet.getCompileOptions().convention(metal.getCompileOptions());
        sourceSet.getIncludes().from(configurations.named(INCLUDABLE_DEPENDENCIES));
        sourceSet.getSources().from(layout.getProjectDirectory().dir("src/%s/asm".formatted(name)));
        sourceSet.getTargets().convention(metal.getTargets());

        final var commandsDirectory = layout.getBuildDirectory().dir("db/%s/asm".formatted(name));
        final var objectDirectory = layout.getBuildDirectory().dir("obj/%s/asm".formatted(name));

        commandsTask.configure(task ->
        {
            task.getCompileOptions().convention(sourceSet.getCompileOptions());
            task.getIncludables().from(sourceSet.getIncludes());
            task.getObjectDirectory().convention(objectDirectory.map(Directory::getAsFile));
            task.getOutputDirectory().convention(commandsDirectory);
            task.setSource(sourceSet.getSources());
            task.getTarget().convention(metal.getTarget());
        });

        compileTask.configure(task ->
        {
            task.onlyIf(it -> sourceSet.getTargets().zip(task.getTarget(),(targets,target) -> targets.isEmpty() || targets.contains(target)).get());
            task.getCompileOptions().convention(sourceSet.getCompileOptions());
            task.getIncludables().from(sourceSet.getSources());
            task.getOutputDirectory().convention(objectDirectory);
            task.setSource(sourceSet.getSources());
            task.getTarget().convention(metal.getTarget());
        });

        configurations.named(COMMANDS_ELEMENTS).configure(it -> it.getOutgoing().artifact(commandsTask));

        tasks.named("compile").configure(it -> it.dependsOn(compileTask));

        return sourceSet;
    }
}
