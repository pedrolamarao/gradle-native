// Copyright (c) Pedro Lamarão <pedro.lamarao@gmail.com>. All rights reserved.

package br.dev.pedrolamarao.gradle.metal.cpp;

import br.dev.pedrolamarao.gradle.metal.base.MetalExtension;
import br.dev.pedrolamarao.gradle.metal.base.NativeCapability;
import br.dev.pedrolamarao.gradle.metal.base.NativeBasePlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CppPlugin implements Plugin<Project>
{
    @Override
    public void apply (Project project)
    {
        project.getPluginManager().apply(NativeBasePlugin.class);

        final var metal = project.getExtensions().getByType(MetalExtension.class);
        metal.getExtensions().create("cpp",CppExtension.class);

        final var nativeImplementation = project.getConfigurations().named("nativeImplementation");

        project.getConfigurations().create("cppIncludeDependencies", configuration -> {
            configuration.setCanBeConsumed(false);
            configuration.setCanBeResolved(true);
            configuration.attributes(it -> it.attribute(NativeCapability.ATTRIBUTE,NativeCapability.INCLUDABLE));
            configuration.extendsFrom(nativeImplementation.get());
        });

        project.getConfigurations().create("cppIncludeElements", configuration -> {
            configuration.setCanBeConsumed(true);
            configuration.setCanBeResolved(false);
            configuration.attributes(it -> it.attribute(NativeCapability.ATTRIBUTE,NativeCapability.INCLUDABLE));
        });
    }
}
