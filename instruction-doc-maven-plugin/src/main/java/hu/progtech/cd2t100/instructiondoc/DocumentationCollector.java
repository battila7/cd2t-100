package hu.progtech.cd2t100.instructiondoc;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import groovyjarjarantlr.RecognitionException;
import groovyjarjarantlr.TokenStreamException;

import org.codehaus.groovy.groovydoc.GroovyClassDoc;
import org.codehaus.groovy.groovydoc.GroovyAnnotationRef;

import org.codehaus.groovy.tools.groovydoc.GroovyDocTool;

public class DocumentationCollector {
  private final File instructionDir;

  private final Pattern quotePattern = Pattern.compile("\"(.+?)\"");

  private List<GroovyClassDoc> classDocs;

  public DocumentationCollector(File instructionDir)
    throws IllegalArgumentException
  {
    if ((!instructionDir.exists()) || (!instructionDir.isDirectory())) {
      throw new IllegalArgumentException("The specified file does not exist or not a directory");
    }

    this.instructionDir = instructionDir;
  }

  public void collectDocumentation()
    throws RecognitionException, TokenStreamException, IOException
  {
    File[] instructionFiles =
      instructionDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".groovy"));

    GroovyDocTool groovyDocTool = new GroovyDocTool(null);

    List<String> paths = Arrays.asList(instructionFiles).stream()
                               .map(File::getAbsolutePath)
                               .collect(Collectors.toList());

    groovyDocTool.add(paths);

    classDocs = Arrays.asList(groovyDocTool.getRootDoc().classes());

    for (GroovyClassDoc doc : classDocs) {
      for (GroovyAnnotationRef ref : doc.annotations()) {
        if (ref.name().equals("Opcode")) {
          System.out.println(getAnnotationValue(ref));
        } else if (ref.name().equals("Rules")) {
          System.out.println(getRules(ref.description()));
        }
      }
    }
  }

  public Map<String,List<GroovyClassDoc>> getDocsByCategory() {
    return classDocs.stream()
           .collect(Collectors.groupingBy(
            (classDoc) -> {
              for (GroovyAnnotationRef ref : classDoc.annotations) {
                if (ref.name().equals("Category")) {
                  return getAnnotationValue(ref);
                }
              }

              return "";
            }
           );
  }

  private String getAnnotationValue(GroovyAnnotationRef annotationRef)
  {
    String desc = annotationRef.description();

    Matcher matcher = quotePattern.matcher(desc);

    return (matcher.find() ? matcher.group(1) : null);
  }

  private List<String> getRules(String ruleString) {
    List<String> ruleNames = new ArrayList<>();

    Matcher matcher = quotePattern.matcher(ruleString);

    while (matcher.find()) {
      ruleNames.add(matcher.group(1));
    }

    return ruleNames;
  }
}
