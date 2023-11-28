package br.dev.pedrolamarao.gradle.metal;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.TaskAction;

@CacheableTask
public abstract class MetalAsmCompile extends MetalCompile
{
    public MetalAsmCompile ()
    {
        getCompiler().convention("clang");
    }

    @Override
    protected final void addLanguageOptions (ListProperty<String> args)
    {
        args.add("--language=assembler");
    }

    @TaskAction
    public void compile ()
    {
        super.compile();
    }
}