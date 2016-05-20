package hu.progtech.cd2t100.instructiondoc;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.LifecyclePhase;

@Mojo(name = "generate",
      defaultPhase = LifecyclePhase.PROCESS_RESOURCES)
public class GenerateMojo extends AbstractMojo
{
    @Parameter(defaultValue = "groovy/")
    private File instructionDir;

    public void execute() throws MojoExecutionException
    {
        try {
          new DocumentationCollector(new File("/home/attila/")).collectDocumentation();
        } catch (Exception e) {
          getLog().info(e.getMessage());
        }
    }
}
