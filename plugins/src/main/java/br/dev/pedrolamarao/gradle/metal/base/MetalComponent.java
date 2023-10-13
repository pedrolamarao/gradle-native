package br.dev.pedrolamarao.gradle.metal.base;

import org.gradle.api.Named;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.provider.SetProperty;

/**
 * Gradle Metal component.
 */
public abstract class MetalComponent implements Named
{
    /**
     * Source file collection.
     *
     * @return collection
     */
    public abstract ConfigurableFileCollection getSources ();

    /**
     * Target set.
     *
     * @return property
     */
    public abstract SetProperty<String> getTargets ();
}
